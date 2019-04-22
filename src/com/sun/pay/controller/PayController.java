package com.sun.pay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.pay.service.PayService;
import com.sun.pay.vo.PayVo;
import com.sun.system.message.RespMessage;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.view.RequestPage;

import redis.clients.jedis.Jedis;

@RestController
@RequestMapping(value="/api/pay")
public class PayController {
	@Resource
	private PayService payService;
	/**
	 * 还款提交
	 * @param payVo
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Object saveFirstPay(HttpServletRequest arg0,@RequestBody PayVo payVo){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		payService.insertOne(payVo,Long.valueOf(jedis.get(token)));
		respMessage.setErrorCode(200);
		respMessage.setData(payVo);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	/**
	 * 催收提交
	 * @param arg0
	 * @param payVo
	 * @return
	 */
	@RequestMapping(value="/urgePay",method=RequestMethod.POST)
	public Object saveUrgePay(HttpServletRequest arg0,@RequestBody PayVo payVo){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		payService.insertUrgePay(payVo,Long.valueOf(jedis.get(token)));
		respMessage.setErrorCode(200);
		respMessage.setData(payVo);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	/**
	 * 获取payId对应的支付信息
	 * @param payId
	 * @return
	 */
	@RequestMapping(value="/{payId}",method=RequestMethod.GET)
	public Object selectById(@PathVariable("payId")Long payId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(payService.selectById(payId));
		return respMessage;	
	}
	/**
	 * 获取订单下的支付列表
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/getByOrder",method=RequestMethod.POST)
	public Object getByOrder(@RequestBody RequestPage requestPage){
		System.out.println(requestPage);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(payService.getByOrder(requestPage));
		requestPage.setTotalCount(payService.getByOrderRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	/**
	 * 多条件组合查询
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public Object selectByWhere(@RequestBody RequestPage requestPage){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(payService.selectByWhere(requestPage));
		requestPage.setTotalCount(payService.getRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
}
