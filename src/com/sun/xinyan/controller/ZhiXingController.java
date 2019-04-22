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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.system.message.RespMessage;
import com.sun.trans.service.TransService;
import com.sun.trans.vo.TransVo;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.xinyan.vo.ZhiXingVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.rsa.RsaCodingUtil;
import com.xinyan.util.HttpUtils;
import com.xinyan.util.JXMConvertUtil;
import com.xinyan.util.SecurityUtil;
import com.xinyan.util.UUIDGenerator;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/ZhiXing")
public class ZhiXingController {
	public static Log _log = LogFactory.getLog(ZhiXingController.class);
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private TransService transService;
	
	@RequestMapping(value="/taskCreateUrl",method=RequestMethod.POST)
	public Object doTaskCreateUrl(HttpServletRequest arg0,@RequestBody ZhiXingVo zhiXingVo) {
		String token = arg0.getHeader("token");
		_log.info("获取客户基本信息"+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.zhixing.taskCreateUrl", "");
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
		ArrayData.put("member_trans_date", trans_date);
		ArrayData.put("member_trans_id", trans_id);
		ArrayData.put("id_card", zhiXingVo.getId_card());
		ArrayData.put("user_name", zhiXingVo.getUser_name());
		ArrayData.put("user_id", Long.valueOf(jedis.get(token)));
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		_log.info("====请求明文:" + XmlOrJson);
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			_log.info("base64:"+base64str);
			/** rsa加密 **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			_log.info("pfxpath:"+pfxpath);
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
				_log.info("====加密串:" + data_content);
				/** ============== http 请求==================== **/
				JSONObject object = new JSONObject();
				object.put("member_id", member_id);
				object.put("terminal_id", terminal_id);
				object.put("data_content", data_content);

				String PostString = HttpUtils.doPostObject(url, headers,object);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				/** ================处理返回结果============= **/
				TransVo transVo = new TransVo();
				transVo.setTransId(trans_id);
				transVo.setTransDate(trans_date);
				transVo.setCusId(Long.valueOf(jedis.get(token)));
				transVo.setTypeId(5L);
				transVo.setTypeName("被执行人");
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("返回数据为空");
					transVo.setSuccess("N");
				}else {
					Object tradeNo = ArrayDataString.get("tradeNo");
					_log.info("新颜订单号====="+tradeNo);
					transVo.setSuccess("Y");
					userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 5L,tradeNo.toString(),zhiXingVo.getIndustry_type());
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString);
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("请求失败:"+e);
		} finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	@RequestMapping(value="/taskStatusUrl",method=RequestMethod.POST)
	public Object doTaskStatusUrl(@RequestBody ZhiXingVo zhiXingVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.zhixing.taskStatusUrl", "")+"/"+zhiXingVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		JSONObject jsonObject = JSONObject.fromObject(PostString);
		respMessage.setSuccess(true);
		respMessage.setErrorCode(200);
		respMessage.setData(jsonObject.get("data"));
		return respMessage;
	}
	
	@RequestMapping(value="/taskinputUrl",method=RequestMethod.POST)
	public Object doTaskinputUrl(@RequestBody ZhiXingVo zhiXingVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.zhixing.taskinputUrl", "")+"/"+zhiXingVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		JSONObject object = new JSONObject();
		object.put("input", zhiXingVo.getInput());
	    String PostString = HttpUtils.doPostObject(url, headers, object);
	    JSONObject jsonObject = JSONObject.fromObject(PostString);
		respMessage.setSuccess(true);
		respMessage.setErrorCode(200);
		respMessage.setData(jsonObject.get("data"));
		return respMessage;
	}
	
	@RequestMapping(value="/taskCreateUrl/user",method=RequestMethod.POST)
	public Object doTaskCreateUrlUser(@RequestBody ZhiXingVo zhiXingVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.zhixing.taskCreateUrl", "");
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
		ArrayData.put("member_trans_date", trans_date);
		ArrayData.put("member_trans_id", trans_id);
		ArrayData.put("id_card", zhiXingVo.getId_card());
		ArrayData.put("user_name", zhiXingVo.getUser_name());
		ArrayData.put("user_id", "0");
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		_log.info("====请求明文:" + XmlOrJson);
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			_log.info("base64:"+base64str);
			/** rsa加密 **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			_log.info("pfxpath:"+pfxpath);
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
				_log.info("====加密串:" + data_content);
				/** ============== http 请求==================== **/
				JSONObject object = new JSONObject();
				object.put("member_id", member_id);
				object.put("terminal_id", terminal_id);
				object.put("data_content", data_content);

				String PostString = HttpUtils.doPostObject(url, headers,object);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				/** ================处理返回结果============= **/
				TransVo transVo = new TransVo();
				transVo.setTransId(trans_id);
				transVo.setTransDate(trans_date);
				transVo.setTypeId(5L);
				transVo.setTypeName("被执行人");
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("返回数据为空");
					transVo.setSuccess("N");
				}else {
					Object tradeNo = ArrayDataString.get("tradeNo");
					_log.info("新颜订单号====="+tradeNo);
					transVo.setSuccess("Y");
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString);
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
