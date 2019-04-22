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

import com.sun.system.message.RespMessage;
import com.sun.trans.service.TransService;
import com.sun.trans.vo.TransVo;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.xinyan.vo.SecurityVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.rsa.RsaCodingUtil;
import com.xinyan.util.HttpUtils;
import com.xinyan.util.JXMConvertUtil;
import com.xinyan.util.SecurityUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/security")
public class SecurityController {
	public static Log _log = LogFactory.getLog(SecurityController.class);
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private TransService transService;
	/**
	 * <p>Title: doArealistUrl</p>  
	 * <p>Description: 获取社保支持地区信息</p>  
	 * @return
	 */
	@RequestMapping(value="/arealistUrl",method=RequestMethod.GET)
	public Object doArealistUrl() {
		_log.info("获取社保支持地区信息");
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.security.arealistUrl", "");
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data").toString());
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取社保支持地区信息");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doLoginUrl</p>  
	 * <p>Description: 获取社保登录信息</p>  
	 * @param fundVo
	 * @return
	 */
	@RequestMapping(value="/loginUrl/{areaCode}",method=RequestMethod.GET)
	public Object doLoginUrl(@PathVariable("areaCode")String areaCode) {
		_log.info("获取社保登录信息="+areaCode);
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.security.loginUrl", "")+areaCode;
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data").toString());
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取社保登录信息");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskCreateUrl</p>  
	 * <p>Description: 创建社保订单</p>  
	 * @param arg0
	 * @param securityVo
	 * @return
	 */
	@RequestMapping(value="/taskCreateUrl",method=RequestMethod.POST)
	public Object doTaskCreateUrl(HttpServletRequest arg0,@RequestBody SecurityVo securityVo) {
		String token = arg0.getHeader("token");
		_log.info("创建社保订单====获取客户基本信息====token="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.security.taskCreateUrl", "");
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
		ArrayData.put("area_code", securityVo.getArea_code());
		ArrayData.put("account", securityVo.getAccount());
		ArrayData.put("password", securityVo.getPassword());
		ArrayData.put("login_type", securityVo.getLogin_type());
		ArrayData.put("id_card", securityVo.getId_card());
		ArrayData.put("mobile", securityVo.getMobile());
		ArrayData.put("real_name", securityVo.getReal_name());
		ArrayData.put("user_id", Long.valueOf(jedis.get(token)));
		ArrayData.put("origin", "2");
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		_log.info("创建社保订单====请求明文:" + XmlOrJson);
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			_log.info("base64:"+base64str);
			/** rsa加密 **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			_log.info("pfxpath:"+pfxpath);
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("创建社保订单====私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
				_log.info("创建社保订单====加密串:" + data_content);
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
				transVo.setTypeId(6L);
				transVo.setTypeName("法人社保");
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("创建社保订单=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("创建社保订单====返回数据为空");
					transVo.setSuccess("N");
				}else {
					Object tradeNo = ArrayDataString.get("tradeNo");
					_log.info("创建社保订单======新颜订单号====="+tradeNo);
					userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 6L,tradeNo.toString());
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString);
					transVo.setSuccess("Y");
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("创建社保订单====请求失败:"+e);
		} finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskStatusUrl</p>  
	 * <p>Description: 获取社保订单执行状态</p>  
	 * @param securityVo
	 * @return
	 */
	@RequestMapping(value="/taskStatusUrl",method=RequestMethod.POST)
	public Object doTaskStatusUrl(@RequestBody SecurityVo securityVo) {
		_log.info("获取社保订单执行状态="+securityVo.getTradeNo());
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.security.taskStatusUrl", "")+"/"+securityVo.getTradeNo();
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
			respMessage.setErrorMsg("获取社保订单执行状态失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskinputUrl</p>  
	 * <p>Description: 输入图片验证码/短信</p>  
	 * @param securityVo
	 * @return
	 */
	@RequestMapping(value="/taskinputUrl",method=RequestMethod.POST)
	public Object doTaskinputUrl(@RequestBody SecurityVo securityVo) {
		_log.info("输入图片验证码/短信="+securityVo.getTradeNo());
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.security.taskinputUrl", "")+"/"+securityVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		JSONObject object = new JSONObject();
		object.put("input", securityVo.getInput());
		String PostString = HttpUtils.doPostObject(url, headers, object);
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("输入图片验证码/短信失败");
		}
		return respMessage;
	}
}
