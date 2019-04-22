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
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.sun.xinyan.vo.FundDataVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.util.HttpUtils;

import net.sf.json.JSONObject;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/fundData")
public class FundDataController {
	public static Log _log = LogFactory.getLog(FundDataController.class);
	@Resource
	private CusUserService cusUserService;
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: doFundResultUrl</p>  
	 * <p>Description: 获取所有公积金信息</p>  
	 * @param fundDataVo
	 * @return
	 */
	@RequestMapping(value="/fundResultUrl/{cusId}",method=RequestMethod.GET)
	public Object doFundResultUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 7L);
		if(userAttestVo != null) {
			_log.info("获取所有公积金信息="+userAttestVo.getMessageId());
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.fund.fundResultUrl", "")+userAttestVo.getMessageId();
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
				respMessage.setErrorMsg("获取所有公积金信息失败");
			}
		}else {
			_log.info("获取所有公积金信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doFundBillsUrl</p>  
	 * <p>Description:根据订单号、年份分页查询公积金缴纳信息</p>  
	 * @param fundDataVo
	 * @return
	 */
	@RequestMapping(value="/fundBillsUrl",method=RequestMethod.POST)
	public Object doFundBillsUrl(@RequestBody FundDataVo fundDataVo) {
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.fund.fundBillsUrl", "")+fundDataVo.getOrderNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("year", fundDataVo.getYear());
		params.put("page", fundDataVo.getPage());
		params.put("size", fundDataVo.getSize());
		_log.info("根据订单号、年份分页查询公积金缴纳信息="+fundDataVo.getOrderNo());
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data").toString());
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("根据订单号、年份分页查询公积金缴纳信息失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: dofundUsersUrl</p>  
	 * <p>Description: 根据订单号查询公积金账户信息</p>  
	 * @param fundDataVo
	 * @return
	 */
	@RequestMapping(value="/fundUsersUrl/{cusId}",method=RequestMethod.GET)
	public Object doFundUsersUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 7L);
		if(userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.fund.fundUsersUrl", "")+userAttestVo.getMessageId();
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			_log.info("根据订单号查询公积金账户信息="+userAttestVo.getMessageId());
			String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
			if(PostString != null && !PostString.equals("")) {
				JSONObject jsonObject = JSONObject.fromObject(PostString);
				respMessage.setSuccess(true);
				respMessage.setErrorCode(200);
				respMessage.setData(jsonObject.get("data").toString());
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("根据订单号查询公积金账户信息失败");
			}
		}else {
			_log.info("根据订单号查询公积金账户信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doFundLoanUrl</p>  
	 * <p>Description: 根据订单号查询公积金贷款信息</p>  
	 * @param fundDataVo
	 * @return
	 */
	@RequestMapping(value="/fundLoanUrl/{cusId}",method=RequestMethod.POST)
	public Object doFundLoanUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 7L);
		if(userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.fund.fundLoanUrl", "")+userAttestVo.getMessageId();
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			_log.info("根据订单号查询公积金贷款信息="+userAttestVo.getMessageId());
			String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
			if(PostString != null && !PostString.equals("")) {
				JSONObject jsonObject = JSONObject.fromObject(PostString);
				respMessage.setSuccess(true);
				respMessage.setErrorCode(200);
				respMessage.setData(jsonObject.get("data").toString());
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("根据订单号查询公积金贷款信息失败");
			}
		}else {
			_log.info("根据订单号查询公积金贷款信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doFundRepayUrl</p>  
	 * <p>Description: 根据订单号查询公积金还款信息</p>  
	 * @param fundDataVo
	 * @return
	 */
	@RequestMapping(value="/fundRepayUrl/{cusId}",method=RequestMethod.POST)
	public Object doFundRepayUrl(@PathVariable("cusId")Long cusId) {
		RespMessage respMessage = new RespMessage();
		UserAttestVo userAttestVo = userAttestService.getOneUserAttestVo(cusId, 7L);
		if(userAttestVo != null) {
			/** 1、 商户号 **/
			String member_id = XinyanProperties.get("xinyan.member.id", "");
			/**2、终端号 **/
			String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
			/**3、请求地址 **/
			String url = XinyanProperties.get("xinyan.fund.fundRepayUrl", "")+userAttestVo.getMessageId();
			Map<String, String> headers =new HashMap<>();
			headers.put("memberId", member_id);
			headers.put("terminalId",terminal_id);
			_log.info("根据订单号查询公积金还款信息="+userAttestVo.getMessageId());
			String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
			if(PostString != null && !PostString.equals("")) {
				JSONObject jsonObject = JSONObject.fromObject(PostString);
				respMessage.setSuccess(true);
				respMessage.setErrorCode(200);
				respMessage.setData(jsonObject.get("data").toString());
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("根据订单号查询公积金还款信息失败");
			}
		}else {
			_log.info("根据订单号查询公积金还款信息=userAttestVo="+userAttestVo);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户信息获取失败");
		}
		return respMessage;
	}
}
