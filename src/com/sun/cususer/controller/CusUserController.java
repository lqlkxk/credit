package com.sun.cususer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.view.RequestPage;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping(value="/api/cususer")
public class CusUserController {
	public static Log _log = LogFactory.getLog(CusUserController.class);
	@Resource
	private CusUserService cusUserService;
	@Resource
	private UserAttestService userAttestService;
	/**
	 * 客户自己管理个人基本信息
	 * @param arg0
	 * @return
	 */
	@RequestMapping(value="/selectPo",method=RequestMethod.GET)
	public Object selectPoByToken(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		_log.info("获取客户基本信息"+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserService.selectCusUserPoById(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(method=RequestMethod.GET)
	public Object selectByToken(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		_log.info("获取客户基本信息"+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserService.selectById(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/updateByToken",method=RequestMethod.POST)
	public Object updateByToken(HttpServletRequest arg0,@RequestBody  CusUserVo cusUserVo){
		String token = arg0.getHeader("token");
		_log.info("修改客户基本信息"+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		cusUserVo.setCusId(Long.valueOf(jedis.get(token)));
		System.out.println(cusUserVo);
		RespMessage respMessage = new RespMessage();
		int count = cusUserService.update(cusUserVo);
		if(count == 1) {
			userAttestService.updateWhere(cusUserVo.getCusId(), 1L, cusUserVo.getCusId()+"");
			_log.info("修改客户基本信息"+token+"成功");
			respMessage.setErrorCode(200);
			respMessage.setData(cusUserVo);
		}else {
			_log.info("修改客户基本信息"+token+"失败");
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("数据修改失败");
		}
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	@RequestMapping(value="/updateSelfPhoto",method=RequestMethod.POST)
	public Object updateSelfPhoto(HttpServletRequest arg0,@RequestBody  CusUserVo cusUserVo){
		String token = arg0.getHeader("token");
		_log.info("修改客户头像信息"+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		int count = cusUserService.updateSelfPhoto(cusUserVo.getSelfPhoto(), Long.valueOf(jedis.get(token)));
		if(count == 1) {
			_log.info("修改客户头像信息"+token+"成功");
			respMessage.setErrorCode(200);
			respMessage.setData(cusUserVo);
		}else {
			_log.info("修改客户头像信息"+token+"失败");
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("客户头像数据修改失败");
		}
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	/**
	 * 公司管理客户基本信息
	 * @param cusId
	 * @return
	 */
	@RequestMapping(value="/{cusId}",method=RequestMethod.GET)
	public Object selectById(@PathVariable("cusId")Long cusId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserService.selectById(cusId));
		return respMessage;	
	}
	@RequestMapping(method=RequestMethod.POST)
	public Object saveCusUserVo(@RequestBody CusUserVo cusUserVo){
		RespMessage respMessage = new RespMessage();
		cusUserService.insertOne(cusUserVo);
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserVo);
		return respMessage;
	}
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public Object selectByWhere(@RequestBody RequestPage requestPage){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserService.selectByWhere(requestPage));
		requestPage.setTotalCount(cusUserService.getRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	@RequestMapping(value="/updateById",method=RequestMethod.POST)
	public Object updateById(@RequestBody CusUserVo cusUserVo){
		int count = cusUserService.update(cusUserVo);
		RespMessage respMessage = new RespMessage();
		if(count == 1){
			respMessage.setErrorCode(200);
			respMessage.setData(cusUserService.selectById(cusUserVo.getCusId()));
		}else{
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("数据修改失败");
		}
		return respMessage;
	}
}
