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
@RequestMapping(value="/api/xinyan/alipay")
public class AlipayController {
	public static Log _log = LogFactory.getLog(AlipayController.class);
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: doInfo</p>  
	 * <p>Description: 查询用户支付宝基本信息</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/info/{cusId}",method=RequestMethod.GET)
	public Object doInfo(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 2L);
		if(userAttestVo != null) {
			_log.info("查询用户支付宝基本信息="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.aliyun.infoUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户支付宝基本信息失败");
			}
		}else {
			_log.info("查询用户支付宝基本信息=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTradeInfo</p>  
	 * <p>Description: 查询用户的支付宝交易记录信息</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/tradeInfo/{cusId}",method=RequestMethod.GET)
	public Object doTradeInfo(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 2L);
		if(userAttestVo != null) {
			_log.info("查询用户的支付宝交易记录信息="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.aliyun.tradeInfoUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的支付宝交易记录信息失败");
			}
		}else {
			_log.info("查询用户的支付宝交易记录信息=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doWealth</p>  
	 * <p>Description: 查询用户的支付宝资产状况</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/wealth/{cusId}",method=RequestMethod.GET)
	public Object doWealth(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 2L);
		if(userAttestVo != null) {
			_log.info("查询用户的支付宝资产状况="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.aliyun.wealthUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户的支付宝资产状况失败");
			}
		}else {
			_log.info("查询用户的支付宝资产状况=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doAssetInfo</p>  
	 * <p>Description: 查询用户花呗消费记录和银行卡消费记录</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/assetinfo/{cusId}",method=RequestMethod.GET)
	public Object doAssetInfo(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 2L);
		if(userAttestVo != null) {
			_log.info("查询用户花呗消费记录和银行卡消费记录="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.aliyun.assetInfoUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户花呗消费记录和银行卡消费记录失败");
			}
		}else {
			_log.info("查询用户花呗消费记录和银行卡消费记录=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doData</p>  
	 * <p>Description: 查询用户所有数据</p>  
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/data/{cusId}",method=RequestMethod.GET)
	public Object doData(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 2L);
		if(userAttestVo != null) {
			_log.info("查询用户所有数据="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.aliyun.dataUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("查询用户所有数据失败");
			}
		}else {
			_log.info("查询用户所有数据=userAttestVo="+cusId);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
}
