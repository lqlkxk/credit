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
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.xinyan.vo.CarrierVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.rsa.RsaCodingUtil;
import com.xinyan.util.HttpUtils;
import com.xinyan.util.JXMConvertUtil;
import com.xinyan.util.SecurityUtil;
import com.xinyan.util.UUIDGenerator;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年6月2日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/Carrier")
public class CarrierController {
	public static Log _log = LogFactory.getLog(CarrierController.class);
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: doChannelsUrl</p>  
	 * <p>Description: 获取运营商支持的渠道信息</p>  
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/channelsUrl",method=RequestMethod.POST)
	public Object doChannelsUrl(@RequestBody CarrierVo carrierVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.channelsUrl", "");
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		JSONObject obj = new JSONObject();
		obj.put("account", carrierVo.getAccount());
		_log.info("获取运营商支持的渠道信息,手机号码="+carrierVo.getAccount());
		String PostString = HttpUtils.doPostObject(url, headers, obj);
		if(PostString != null && !PostString.equals("")) {
			//保存运营商支持的渠道信息到数据库
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取运营商支持的渠道信息失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskCreateUrl</p>  
	 * <p>Description: 创建运营商订单</p>  
	 * @param taskCreateUrlVo
	 * @return
	 */
	@RequestMapping(value="/taskCreateUrl",method=RequestMethod.POST)
	public Object doTaskCreateUrl(HttpServletRequest arg0,@RequestBody CarrierVo carrierVo) {
		String token = arg0.getHeader("token");
		_log.info("获取客户基本信息"+token);
//		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.taskCreateUrl", "");
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String member_trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String member_trans_id = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
		String notify_url="";
		String user_id=UUIDGenerator.getUUID();
        String login_type="pwd";
		String XmlOrJson = "";
		/** 组装参数  **/
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("member_trans_date", member_trans_date);
		ArrayData.put("member_trans_id", member_trans_id);
		ArrayData.put("notify_url", notify_url);
		ArrayData.put("account", carrierVo.getAccount());
		ArrayData.put("password", carrierVo.getPassword());
		ArrayData.put("user_id", user_id);
		ArrayData.put("origin", carrierVo.getOrigin());
		ArrayData.put("real_name", carrierVo.getReal_name());
		ArrayData.put("id_card", carrierVo.getId_card());
		ArrayData.put("login_type", login_type);
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		_log.info("创建运营商订单====请求明文:" + XmlOrJson);
		/** base64 编码 **/
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			/** rsa加密  **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);//加密数据
				_log.info("====加密串:"+data_content);
				JSONObject obj2 = new JSONObject();
				obj2.put("member_id", member_id);
				obj2.put("terminal_id", terminal_id);
				obj2.put("data_content", data_content);
			    String PostString = HttpUtils.doPostObject(url, headers,obj2);
				if(PostString != null && !PostString.equals("")) {
					//保存创建运营商订单信息到数据库
					JSONObject jsonObject = JSONObject.fromObject(PostString);
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(jsonObject.get("data"));
				}else {
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("创建运营商订单失败");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("创建运营商订单:"+e);
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskStatusUrl</p>  
	 * <p>Description: 获取运营商订单执行状态</p>  
	 * @param tradeNo
	 * @return
	 */
	@RequestMapping(value="/taskStatusUrl",method=RequestMethod.POST)
	public Object doTaskStatusUrl(@RequestBody CarrierVo carrierVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.taskStatusUrl", "")+"/"+carrierVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			//保存获取运营商订单执行状态信息到数据库
//			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(PostString);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取运营商订单执行状态失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doVerifycodeUrl</p>  
	 * <p>Description: 重新获取详单验证码    短信验证</p>  
	 * @param tradeNo
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/verifycodeUrl/sms",method=RequestMethod.POST)
	public Object doVerifycodeUrl(@RequestBody CarrierVo carrierVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.verifycodeUrl", "")+"/"+carrierVo.getTradeNo()+"/sms";
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("重新获取详单验证码    短信验证失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskinputUrl</p>  
	 * <p>Description: 验证码输入提交</p>  
	 * @param taskCreateUrlVo
	 * @return
	 */
	@RequestMapping(value="/taskinputUrl",method=RequestMethod.POST)
	public Object doTaskinputUrl(@RequestBody CarrierVo carrierVo) {
		_log.info("验证码输入提交tradeNo="+carrierVo.getTradeNo());
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.taskinputUrl", "")+"/"+carrierVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		JSONObject object = new JSONObject();
		object.put("input", carrierVo.getCode());
		String PostString = HttpUtils.doPostObject(url, headers, object);
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("验证码输入提交失败");
		}
		return respMessage;
	}
	
	@RequestMapping(value="/taskSuccess",method=RequestMethod.POST)
	public Object doTaskSuccess(HttpServletRequest arg0,@RequestBody CarrierVo carrierVo) {
		String token = arg0.getHeader("token");
		_log.info("保存运营商订单="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		int count = userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 4L,carrierVo.getTradeNo());
		if(count == 1) {
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(carrierVo.getTradeNo());
			
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("保存运营商订单失败");
		}
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
}
