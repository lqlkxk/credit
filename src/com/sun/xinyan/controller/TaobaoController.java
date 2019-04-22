/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.xinyan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.util.HttpUtils;

import net.sf.json.JSONObject;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/taobao")
public class TaobaoController {
	public static Log _log = LogFactory.getLog(TaobaoController.class);
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: doUser</p>  
	 * <p>Description: 查询用户的基本信息</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/user/{cusId}",method=RequestMethod.GET)
	public Object doUser(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询用户的基本信息="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.userUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的基本信息失败");
			}
		}else {
			_log.info("查询用户的基本信息=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doDeliverAddress</p>  
	 * <p>Description: 查询用户的淘宝收货地址</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/deliverAddress/{cusId}",method=RequestMethod.GET)
	public Object doDeliverAddress(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询用户的淘宝收货地址="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.deliverAddressUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的淘宝收货地址失败");
			}
		}else {
			_log.info("查询用户的淘宝收货地址=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doRecentAddress</p>  
	 * <p>Description: 查询用户的最近几笔订单的收货地址</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/recentAddress/{cusId}",method=RequestMethod.GET)
	public Object doRecentAddress(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询用户的最近几笔订单的收货地址="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.recentAddresssUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的最近几笔订单的收货地址失败");
			}
		}else {
			_log.info("查询用户的最近几笔订单的收货地址=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doDetails</p>  
	 * <p>Description: 查询用户的淘宝订单信息（含商品）</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/details/{cusId}",method=RequestMethod.GET)
	public Object doDetails(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询用户的淘宝订单信息（含商品）="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.detailsUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的淘宝订单信息（含商品）失败");
			}
		}else {
			_log.info("查询用户的淘宝订单信息（含商品）=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doInfo</p>  
	 * <p>Description: 查询用户的淘宝订单信息（不含商品）</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/info/{cusId}",method=RequestMethod.GET)
	public Object doInfo(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询用户的淘宝订单信息（不含商品）="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.infoUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的淘宝订单信息（不含商品）失败");
			}
		}else {
			_log.info("查询用户的淘宝订单信息（不含商品）=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doUserAlipay</p>  
	 * <p>Description: 查询淘宝帐号关联的支付宝帐号资产信息</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/userAlipay/{cusId}",method=RequestMethod.GET)
	public Object doUserAlipay(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询淘宝帐号关联的支付宝帐号资产信息 ="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.userAlipayUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询淘宝帐号关联的支付宝帐号资产信息失败");
			}
		}else {
			_log.info("查询淘宝帐号关联的支付宝帐号资产信息=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doUserData</p>  
	 * <p>Description: 查询淘宝用户全部信息</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/userData/{cusId}",method=RequestMethod.GET)
	public Object doUserData(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 3L);
		if(userAttestVo != null) {
			_log.info("查询淘宝用户全部信息 ="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.taobao.userDataUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询淘宝用户全部信息失败");
			}
		}else {
			_log.info("查询淘宝用户全部信息=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
}
