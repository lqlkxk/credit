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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.cususer.service.CusUserService;
import com.sun.cususer.vo.CusUserVo;
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.sun.xinyan.vo.CarrierDataVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.util.HttpUtils;

/**@author SUNCHANGQING
 * @date 2018年6月3日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/CarrierData")
public class CarrierDataController {
	public static Log _log = LogFactory.getLog(CarrierDataController.class);
	@Resource
	private CusUserService cusUserService;
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: doCarrierUserUrl</p>  
	 * <p>Description: 获取用户信息</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/carrierUserUrl",method=RequestMethod.POST)
	public Object doCarrierUserUrl(@RequestBody CarrierDataVo carrierDataVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.carrierUserUrl", "")+carrierDataVo.getOrderNo()+"?mobile="+carrierDataVo.getMobile();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		_log.info("获取用户信息,手机号码="+carrierDataVo.getMobile()+",订单号="+carrierDataVo.getOrderNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
//			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(PostString);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取用户信息失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doCarrierMobileBillUrl</p>  
	 * <p>Description: 获取帐号账单记录</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/carrierMobileBillUrl",method=RequestMethod.POST)
	public Object doCarrierMobileBillUrl(@RequestBody CarrierDataVo carrierDataVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.carrierMobileBillUrl", "")+carrierDataVo.getOrderNo()+"?mobile="+carrierDataVo.getMobile()+"&from_month="+carrierDataVo.getFrom_month()+"&to_month="+carrierDataVo.getTo_month();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		_log.info("获取帐号账单记录,手机号码="+carrierDataVo.getMobile()+",订单号="+carrierDataVo.getOrderNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
//			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(PostString);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取帐号账单记录失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doCarrierMobileCallUrl</p>  
	 * <p>Description: 获取帐号通话详情</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/carrierMobileCallUrl",method=RequestMethod.POST)
	public Object doCarrierMobileCallUrl(@RequestBody CarrierDataVo carrierDataVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.carrierMobileCallUrl", "")+carrierDataVo.getOrderNo()+"?mobile="+carrierDataVo.getMobile()+"&month="+carrierDataVo.getMonth();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		_log.info("获取帐号通话详情,手机号码="+carrierDataVo.getMobile()+",订单号="+carrierDataVo.getOrderNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
//			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(PostString);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取帐号通话详情失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doCarrierMobileSmsUrl</p>  
	 * <p>Description: 获取短信通话详情</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/carrierMobileSmsUrl",method=RequestMethod.POST)
	public Object doCarrierMobileSmsUrl(@RequestBody CarrierDataVo carrierDataVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.carrierMobileSmsUrl", "")+carrierDataVo.getOrderNo()+"?mobile="+carrierDataVo.getMobile()+"&month="+carrierDataVo.getMonth();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		_log.info("获取短信通话详情,手机号码="+carrierDataVo.getMobile()+",订单号="+carrierDataVo.getOrderNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
//			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(PostString);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取短信通话详情失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doCarrierMobileUrl</p>  
	 * <p>Description: 获取订单的完整信息</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/carrierMobileUrl",method=RequestMethod.POST)
	public Object doCarrierMobileUrl(@RequestBody CarrierDataVo carrierDataVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.carrier.carrierMobileUrl", "")+carrierDataVo.getOrderNo()+"?mobile="+carrierDataVo.getMobile();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		_log.info("获取订单的完整信息,手机号码="+carrierDataVo.getMobile()+",订单号="+carrierDataVo.getOrderNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
//			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(PostString);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取订单的完整信息情失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doCarrierReportUrl</p>  
	 * <p>Description: 获取完整的运营商报告</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/carrierReportUrl/{cusId}",method=RequestMethod.GET)
	public Object doCarrierReportUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		CusUserVo cusUserVo = cusUserService.selectById(cusId);
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 4L);
		if(cusUserVo != null && userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.carrier.carrierReportUrl", "")+userAttestVo.getMessageId()+"?mobile="+cusUserVo.getMobileNumber()+"&name="+cusUserVo.getName()+"&idcard="+cusUserVo.getIdentityCard();
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			_log.info("获取完整的运营商报告,手机号码="+cusUserVo.getMobileNumber()+",订单号="+userAttestVo.getMessageId());
			String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
//			_log.info("PostString="+PostString);
			if(PostString != null && !PostString.equals("")) {
//				JSONObject jsonObject = JSONObject.fromObject(PostString);
				respMessage.setSuccess(true);
				respMessage.setErrorCode(200);
				respMessage.setData(PostString);
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("获取完整的运营商报告失败");
			}
		}else {
			_log.info("获取完整的运营商报告=cusUserVo="+cusUserVo+",userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doMobileMonthUrl</p>  
	 * <p>Description: 获取订单的完整信息(含每月通话情况检测)</p>  
	 * @param carrierDataVo
	 * @return
	 */
	@RequestMapping(value="/mobileMonthUrl/{cusId}",method=RequestMethod.GET)
	public Object doMobileMonthUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		CusUserVo cusUserVo = cusUserService.selectById(cusId);
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 4L);
		if(cusUserVo != null && userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.carrier.mobileMonthUrl", "")+userAttestVo.getMessageId()+"?mobile="+cusUserVo.getMobileNumber();
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			_log.info("获取订单的完整信息(含每月通话情况检测),手机号码="+cusUserVo.getMobileNumber()+",订单号="+userAttestVo.getMessageId());
			String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
			if(PostString != null && !PostString.equals("")) {
//				JSONObject jsonObject = JSONObject.fromObject(PostString);
				respMessage.setSuccess(true);
				respMessage.setErrorCode(200);
				respMessage.setData(PostString);
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("获取订单的完整信息(含每月通话情况检测)失败");
			}
		}else {
			_log.info("获取订单的完整信息(含每月通话情况检测)失败=cusUserVo="+cusUserVo+",userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		
		return respMessage;
	}
}
