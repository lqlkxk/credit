/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.xinyan.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.system.message.RespMessage;
import com.sun.trans.service.TransService;
import com.sun.trans.vo.TransVo;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.properties.SystemProperties;
import com.xinyan.config.XinyanProperties;
import com.xinyan.http.HttpMethod;
import com.xinyan.rsa.RsaCodingUtil;
import com.xinyan.util.HttpUtil;
import com.xinyan.util.JXMConvertUtil;
import com.xinyan.util.SecurityUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;


/**@author SUNCHANGQING
 * @date 2018年6月2日 
 * 获取订单号
 */
@RestController
@RequestMapping(value="/api/xinyan/CreditSpider/PreOrderRsa")
public class PreOrderRsaController {
	public static Log _log = LogFactory.getLog(PreOrderRsaController.class);
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private TransService transService;
	@RequestMapping(value="/alipay",method=RequestMethod.GET)
	public Object getPreOrderRsaForAlipay(HttpServletRequest arg0) {
		String token = arg0.getHeader("token");
		_log.info("支付宝采集订单====获取客户基本信息====token="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/** 2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/** 3、加密数据 **/
		/** 组装参数 (7) **/
		String trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String transId = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("txnType", "alipay");//交易类型
		ArrayData.put("memberId", member_id);
		ArrayData.put("terminalId", terminal_id);
		ArrayData.put("transId", transId);//商户订单号
		String XmlOrJson = "";
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();	
		_log.info("支付宝====请求明文:" + XmlOrJson);
		/** base64 编码 **/
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
				_log.info("支付宝====加密串:" + data_content);
				/** ============== http 请求==================== **/
				String request_url = XinyanProperties.get("xinyan.preOrderAuthUrl", "");
				Map<String, String> HeadPostParam = new HashMap<String, String>();
				HeadPostParam.put("memberId", member_id);
				HeadPostParam.put("terminalId", terminal_id);
				HeadPostParam.put("dataContent", data_content);
				String PostString = HttpUtil.RequestForm(request_url, HeadPostParam,HttpMethod.POST);
				_log.info("支付宝======请求返回：" + PostString);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				TransVo transVo = new TransVo();
				transVo.setTransId(transId);
				transVo.setTransDate(trans_date);
				transVo.setCusId(Long.valueOf(jedis.get(token)));
				transVo.setTypeId(2L);
				transVo.setTypeName("支付宝");
				/** ================处理返回结果============= **/
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("支付宝=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("返回数据为空");
					transVo.setSuccess("N");
				}else {
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString.get("data"));
					userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 2L,ArrayDataString.get("data").toString());
					transVo.setSuccess("Y");
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("请求失败:"+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;	
	}
	@RequestMapping(value="/taobao",method=RequestMethod.GET)
	public Object getPreOrderRsaForTaobao(HttpServletRequest arg0) {
		String token = arg0.getHeader("token");
		_log.info("淘宝采集订单====获取客户基本信息====token="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/** 2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/** 3、加密数据 **/
		/** 组装参数 (7) **/
		String trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String transId = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("txnType", "taobao");//交易类型
		ArrayData.put("memberId", member_id);
		ArrayData.put("terminalId", terminal_id);
		ArrayData.put("transId", transId);//商户订单号
		String XmlOrJson = "";
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();	
		_log.info("淘宝====请求明文:" + XmlOrJson);
		/** base64 编码 **/
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			_log.info("base64:"+base64str);
			/** rsa加密 **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			_log.info("pfxpath:"+pfxpath);
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("淘宝====私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
				_log.info("淘宝====加密串:" + data_content);
				/** ============== http 请求==================== **/
				String request_url = XinyanProperties.get("xinyan.preOrderAuthUrl", "");
				Map<String, String> HeadPostParam = new HashMap<String, String>();
				HeadPostParam.put("memberId", member_id);
				HeadPostParam.put("terminalId", terminal_id);
				HeadPostParam.put("dataContent", data_content);
				String PostString = HttpUtil.RequestForm(request_url, HeadPostParam,HttpMethod.POST);
				_log.info("淘宝====请求返回：" + PostString);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				TransVo transVo = new TransVo();
				transVo.setTransId(transId);
				transVo.setTransDate(trans_date);
				transVo.setCusId(Long.valueOf(jedis.get(token)));
				transVo.setTypeId(3L);
				transVo.setTypeName("淘宝");
				/** ================处理返回结果============= **/
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("淘宝=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("返回数据为空");
					transVo.setSuccess("N");
				}else {
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString.get("data"));
					userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 3L,ArrayDataString.get("data").toString());
					transVo.setSuccess("Y");
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("淘宝====请求失败:"+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;	
	}
	@RequestMapping(value="/{txnType}",method=RequestMethod.GET)
	public Object getPreOrderRsa(HttpServletRequest arg0,@PathVariable("txnType") String txnType) {
		String token = arg0.getHeader("token");
		_log.info(txnType+"集订单====获取客户基本信息====token="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/** 2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/** 3、加密数据 **/
		/** 组装参数 (7) **/
		String trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String transId = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("txnType", txnType);//交易类型
		ArrayData.put("memberId", member_id);
		ArrayData.put("terminalId", terminal_id);
		ArrayData.put("transId", transId);//商户订单号
		String XmlOrJson = "";
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();	
		_log.info("====请求明文:" + XmlOrJson);
		/** base64 编码 **/
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
				String request_url = XinyanProperties.get("xinyan.preOrderAuthUrl", "");
				Map<String, String> HeadPostParam = new HashMap<String, String>();
				HeadPostParam.put("memberId", member_id);
				HeadPostParam.put("terminalId", terminal_id);
				HeadPostParam.put("dataContent", data_content);
				String PostString = HttpUtil.RequestForm(request_url, HeadPostParam,HttpMethod.POST);
				_log.info("请求返回：" + PostString);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				TransVo transVo = new TransVo();
				transVo.setTransId(transId);
				transVo.setTransDate(trans_date);
				transVo.setCusId(Long.valueOf(jedis.get(token)));
				transVo.setTypeName(txnType);
				/** ================处理返回结果============= **/
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info(txnType+"集订单=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("返回数据为空");
					transVo.setSuccess("N");
				}else {
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString.get("data"));
					transVo.setSuccess("Y");
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg(txnType+"集订单请求失败:"+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;	
	}

}
