package com.sun.cususeradd.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

import com.sun.cususer.service.CusUserService;
import com.sun.cususer.vo.CusUserVo;
import com.sun.cususeradd.service.CusUserAddService;
import com.sun.cususeradd.vo.CusUserAddVo;
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.view.RequestPage;

@RestController
@RequestMapping(value="/api/cususeradd")
public class CusUserAddController {
	@Resource
	private CusUserAddService cusUserAddService;
	@Resource
	private CusUserService cusUserService;
	@Resource
	private UserAttestService userAttestService;
	/**
	 * 客户自己管理个人补充信息
	 * @param arg0
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public Object selectByToken(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserAddService.selectByCusId(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/updateByToken",method=RequestMethod.POST)
	public Object updateByToken(HttpServletRequest arg0,@RequestBody CusUserAddVo cusUserAddVo){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		CusUserAddVo sysCusUserAddVo = cusUserAddService.selectByCusId(Long.valueOf(jedis.get(token)));
		RespMessage respMessage = new RespMessage();
		if(sysCusUserAddVo != null) {
			cusUserAddVo.setCusAddId(sysCusUserAddVo.getCusAddId());
			int count = cusUserAddService.update(cusUserAddVo);
			if(count == 1) {
				respMessage.setErrorCode(200);
				respMessage.setData(cusUserAddVo);
			}else {
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setData("数据修改失败");
			}
		}else {
			CusUserVo cusUserVo = new CusUserVo();
			cusUserVo.setCusId(Long.valueOf(jedis.get(token)));
			cusUserAddVo.setCusUserVo(cusUserVo);
			cusUserAddService.insertOne(cusUserAddVo);
			userAttestService.updateWhere(cusUserVo.getCusId(), 9L, cusUserAddVo.getCusAddId()+"");
			respMessage.setErrorCode(200);
			respMessage.setData(cusUserAddVo);
		}
		return respMessage;
	}
	/**
	 * 公司管理客户补充信息
	 * @param cusAddId
	 * @return
	 */
	@RequestMapping(value="/{cusAddId}",method=RequestMethod.GET)
	public Object selectById(@PathVariable("cusAddId")Long cusAddId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserAddService.selectById(cusAddId));
		return respMessage;	
	}
	@RequestMapping(method=RequestMethod.POST)
	public Object saveCusUserAddVo(HttpServletRequest arg0,@RequestBody CusUserAddVo cusUserAddVo){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		CusUserVo cusUserVo = cusUserService.selectById(Long.valueOf(jedis.get(token)));
		cusUserAddVo.setCusUserVo(cusUserVo);
		RespMessage respMessage = new RespMessage();
		cusUserAddService.insertOne(cusUserAddVo);
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserAddVo);
		return respMessage;
	}
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public Object selectByWhere(@RequestBody RequestPage requestPage){
		System.out.println(requestPage);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserAddService.selectByWhere(requestPage));
		requestPage.setTotalCount(cusUserAddService.getRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	@RequestMapping(value="/byCustomer/{cusId}",method=RequestMethod.GET)
	public Object selectByCusId(@PathVariable("cusId")Long cusId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusUserAddService.selectByCusId(cusId));
		return respMessage;	
	}
}
