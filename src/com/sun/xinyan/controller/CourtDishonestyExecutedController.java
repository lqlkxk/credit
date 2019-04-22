/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.xinyan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.credit.service.CreditService;
import com.sun.credit.vo.CreditVo;
import com.sun.cususer.service.CusUserService;
import com.sun.cususer.vo.CusUserVo;
import com.sun.system.message.RespMessage;
import com.sun.trans.service.TransService;
import com.sun.trans.vo.TransVo;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.sun.xinyan.vo.CourtDishonestyExecutedVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.rsa.RsaCodingUtil;
import com.xinyan.util.HttpUtils;
import com.xinyan.util.JXMConvertUtil;
import com.xinyan.util.SecurityUtil;

import net.sf.json.JSONObject;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/CourtDishonestyExecuted")
public class CourtDishonestyExecutedController {
	public static Log _log = LogFactory.getLog(CourtDishonestyExecutedController.class);
	@Resource
	private CusUserService cusUserService;
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private TransService transService;
	@Resource
	private CreditService creditService;
	
	@RequestMapping(value="/courtDishonestyExecuted/{cusId}",method=RequestMethod.GET)
	public Object doCourtDishonestyExecuted(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		CusUserVo cusUserVo = cusUserService.selectById(cusId);
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 5L);
		if(cusUserVo != null && userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.zhixing.courtDishonestyExecuted", "");
			String trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
			String trans_id = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			String XmlOrJson = "";
			/** 组装参数  **/
			Map<Object, Object> ArrayData = new HashMap<Object, Object>();
			ArrayData.put("member_id", member_id);
			ArrayData.put("terminal_id", terminal_id);
			ArrayData.put("trade_date", trans_date);
			ArrayData.put("trans_id", trans_id);
			ArrayData.put("idCard", cusUserVo.getIdentityCard());
			ArrayData.put("idHolder", cusUserVo.getName());
			ArrayData.put("industry_type",userAttestVo.getMessage1());
			
			JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
			XmlOrJson = jsonObjectFromMap.toString();
			_log.info("综合诉讼核查-失信====请求明文:" + XmlOrJson);
			String base64str = null;
			try {
				base64str = SecurityUtil.Base64Encode(XmlOrJson);
				_log.info("base64:"+base64str);
				/** rsa加密 **/
				String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
				File pfxfile = new File(pfxpath);
				if (!pfxfile.exists()) {
					_log.info("综合诉讼核查-失信====私钥文件不存在！");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("私钥文件不存在");
				}else {
					String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
					String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
					_log.info("综合诉讼核查-失信====加密串:" + data_content);
					/** ============== http 请求==================== **/
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("member_id", member_id);
					params.put("terminal_id", terminal_id);
					params.put("data_type", "json");
					params.put("data_content", data_content);

					String PostString = HttpUtils.doPost(url, headers,params);
//					Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
//					_log.info("ArrayDataString：" + ArrayDataString);
					/** ================处理返回结果============= **/
					TransVo transVo = new TransVo();
					transVo.setTransId(trans_id);
					transVo.setTransDate(trans_date);
					transVo.setCusId(cusUserVo.getCusId());
					transVo.setTypeId(5L);
					transVo.setTypeName("综合诉讼核查-失信");
					if (PostString.isEmpty()) {// 判断参数是否为空
						_log.info("综合诉讼核查-失信=====返回数据为空");
						respMessage.setSuccess(false);
						respMessage.setErrorCode(201);
						respMessage.setErrorMsg("返回数据为空");
						transVo.setSuccess("N");
					}else {
						respMessage.setSuccess(true);
						respMessage.setErrorCode(200);
						respMessage.setData(PostString);
						transVo.setSuccess("Y");
						CreditVo creditVo = new CreditVo();
						creditVo.setCusId(cusId);
						creditVo.setCreditType("CDE");
						creditVo.setData(PostString);
						creditService.insertCreditVo(creditVo);
					}
					transService.insertOne(transVo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				_log.error(e);
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("请求失败:"+e);
			}
		}else {
			_log.info("综合诉讼核查-失信=cusUserVo="+cusUserVo+",userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	
	@RequestMapping(value="/courtDishonestyExecuted/user",method=RequestMethod.POST)
	public Object doCourtDishonestyExecutedUser(@RequestBody CourtDishonestyExecutedVo courtDishonestyExecutedVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.zhixing.courtDishonestyExecuted", "");
		String trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String trans_id = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String XmlOrJson = "";
		/** 组装参数  **/
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("trade_date", trans_date);
		ArrayData.put("trans_id", trans_id);
		ArrayData.put("idCard", courtDishonestyExecutedVo.getIdCard());
		ArrayData.put("idHolder", courtDishonestyExecutedVo.getIdHolder());
		ArrayData.put("industry_type",courtDishonestyExecutedVo.getIndustry_type());
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		_log.info("综合诉讼核查-失信====请求明文:" + XmlOrJson);
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			_log.info("base64:"+base64str);
			/** rsa加密 **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("综合诉讼核查-失信====私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
				_log.info("综合诉讼核查-失信====加密串:" + data_content);
				/** ============== http 请求==================== **/
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("member_id", member_id);
				params.put("terminal_id", terminal_id);
				params.put("data_type", "json");
				params.put("data_content", data_content);

				String PostString = HttpUtils.doPost(url, headers,params);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				/** ================处理返回结果============= **/
				TransVo transVo = new TransVo();
				transVo.setTransId(trans_id);
				transVo.setTransDate(trans_date);
				transVo.setTypeId(5L);
				transVo.setTypeName("综合诉讼核查-失信");
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("综合诉讼核查-失信=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("返回数据为空");
					transVo.setSuccess("N");
				}else {
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString);
					transVo.setSuccess("Y");
					CreditVo creditVo = new CreditVo();
					creditVo.setId_name(courtDishonestyExecutedVo.getIdHolder());
					creditVo.setId_no(courtDishonestyExecutedVo.getIdCard());
					creditVo.setCreditType("CDE");
					creditVo.setData(PostString);
					creditService.insertCreditVo(creditVo);
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("请求失败:"+e);
		}
		return respMessage;
	}
}
