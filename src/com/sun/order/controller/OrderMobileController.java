package com.sun.order.controller;

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
import com.sun.order.service.OrderService;
import com.sun.order.vo.OrderReturnMessage;
import com.sun.order.vo.OrderVo;
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;

@RestController
@RequestMapping(value="/api/mobile/order")
public class OrderMobileController {
	@Resource
	private OrderService orderService;
	@Resource
	private CusUserService cusUserService;
	@Resource
	private CusUserAddService cusUserAddService;
	@Resource
	private UserAttestService userAttestService;
	/**
	 * 
	 * @author SUNCHANGQING
	 * 2018年5月7日下午4:50:37
	 *
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Object insert(HttpServletRequest arg0,@RequestBody OrderVo orderVo){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		CusUserVo cusUserVo = cusUserService.selectById(Long.valueOf(jedis.get(token)));
		CusUserAddVo cusUserAddVo = cusUserAddService.selectByCusId(Long.valueOf(jedis.get(token)));
		RespMessage respMessage = new RespMessage();
		if(cusUserVo != null && cusUserAddVo != null) {
			orderService.insert(cusUserVo,cusUserAddVo,orderVo);
			respMessage.setErrorCode(200);
			respMessage.setData(orderVo);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("用户基本信息获取失败");
			respMessage.setData(orderVo);
		}
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/{orderId}",method=RequestMethod.GET)
	public Object selectById(@PathVariable("orderId")Long orderId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.selectById(orderId));
		return respMessage;	
	}
	@RequestMapping(value="/getCount",method=RequestMethod.GET)
	public Object getOrderCount(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		Long cusUserId = Long.valueOf(jedis.get(token));
		CusUserVo cusUserVo = cusUserService.selectById(cusUserId);
		int orderCount = orderService.getOrderCount(cusUserId);
		int attestCount = userAttestService.getUserAttestCount(cusUserId);
		OrderReturnMessage orderReturnMessage = new OrderReturnMessage(cusUserVo.getName(),cusUserVo.getMobileNumber(),cusUserVo.getSelfPhoto(),orderCount,attestCount);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderReturnMessage);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/approveFirst",method=RequestMethod.GET)
	public Object getApproveFirst(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getApproveFirst(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/approveFinal",method=RequestMethod.GET)
	public Object getApproveFinal(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getApproveFinal(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/grantFirst",method=RequestMethod.GET)
	public Object getGrantFirst(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getGrantFirst(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/grantFinal",method=RequestMethod.GET)
	public Object getGrantFinal(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getGrantFinal(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/returnApply",method=RequestMethod.GET)
	public Object getReturnApply(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getReturnApply(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/notOverFlag",method=RequestMethod.GET)
	public Object getNotOverFlag(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getNotOverFlag(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
	@RequestMapping(value="/overFlag",method=RequestMethod.GET)
	public Object getOverFlag(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getOverFlag(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
}
