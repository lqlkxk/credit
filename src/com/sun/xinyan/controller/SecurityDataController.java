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

import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.sun.xinyan.vo.SecurityDataVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.util.HttpUtils;

import net.sf.json.JSONObject;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/securityData")
public class SecurityDataController {
	public static Log _log = LogFactory.getLog(SecurityDataController.class);
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: doInfoUrl</p>  
	 * <p>Description: 根据订单号查询社保信息</p>  
	 * @param securityDataVo
	 * @return
	 */
	@RequestMapping(value="/infoUrl/{cusId}",method=RequestMethod.GET)
	public Object doInfoUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 7L);
		if(userAttestVo != null) {
			_log.info("根据订单号查询社保信息="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.security.infoUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("根据订单号查询社保信息失败");
			}
		}else {
			_log.info("根据订单号查询社保信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doInsuranceUrl</p>  
	 * <p>Description: 根据订单号和险种编号查询社保信息</p>  
	 * @param securityDataVo
	 * @return
	 */
	@RequestMapping(value="/insuranceUrl/{cusId}/{insuranceCode}",method=RequestMethod.POST)
	public Object doInsuranceUrl(@PathVariable("cusId")Long cusId,@PathVariable("insuranceCode")String insuranceCode) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 7L);
		if(userAttestVo != null) {
			_log.info("根据订单号和险种编号查询社保信息="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.security.insuranceUrl", "")+userAttestVo.getMessageId()+"?insuranceCode="+insuranceCode;
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
				respMessage.setErrorMsg("根据订单号和险种编号查询社保信息失败");
			}
		}else {
			_log.info("根据订单号和险种编号查询社保信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
}
