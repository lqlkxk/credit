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

import com.sun.credit.service.CreditService;
import com.sun.credit.vo.CreditVo;
import com.sun.cususer.service.CusUserService;
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.sun.xinyan.vo.ZhiXingVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.util.HttpUtils;

import net.sf.json.JSONObject;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/ZhiXingData")
public class ZhiXingDataController {
	public static Log _log = LogFactory.getLog(ZhiXingDataController.class);
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private CreditService creditService;
	@RequestMapping(value="/zhixinginfoUrl/{cusId}",method=RequestMethod.GET)
	public Object doZhixinginfoUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 5L);
		if(userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.zhixing.zhixinginfoUrl", "")+userAttestVo.getMessageId();
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			_log.info("根据订单号查询法院被执行人信息=订单号="+userAttestVo.getMessageId());
			String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
			if(PostString != null && !PostString.equals("")) {
				JSONObject jsonObject = JSONObject.fromObject(PostString);
				respMessage.setSuccess(true);
				respMessage.setErrorCode(200);
				respMessage.setData(jsonObject.get("data"));
				CreditVo creditVo = new CreditVo();
				creditVo.setCusId(cusId);
				creditVo.setCreditType("ZXD");
				creditVo.setData(jsonObject.get("data").toString());
				creditService.insertCreditVo(creditVo);
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("根据订单号查询法院被执行人信息失败");
			}
		}else {
			_log.info("查询法院被执行人信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}	
		return respMessage;
	}
	@RequestMapping(value="/zhixinginfoUrl/user",method=RequestMethod.POST)
	public Object doZhixinginfoUrl(@RequestBody ZhiXingVo zhiXingVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.zhixing.zhixinginfoUrl", "")+zhiXingVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		_log.info("根据订单号查询法院被执行人信息=订单号="+zhiXingVo.getTradeNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
			CreditVo creditVo = new CreditVo();
			creditVo.setId_name(zhiXingVo.getUser_name());
			creditVo.setId_no(zhiXingVo.getId_card());
			creditVo.setCreditType("CDE");
			creditVo.setData(jsonObject.get("data").toString());
			creditService.insertCreditVo(creditVo);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("根据订单号查询法院被执行人信息失败");
		}	
		return respMessage;
	}
}
