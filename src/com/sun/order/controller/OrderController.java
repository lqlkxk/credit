package com.sun.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

import com.sun.order.service.OrderService;
import com.sun.order.vo.OrderVo;
import com.sun.system.message.RespMessage;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.view.RequestPage;

@RestController
@RequestMapping(value="/api/order")
public class OrderController {
	public static Log _log = LogFactory.getLog(OrderController.class);
	@Resource
	private OrderService orderService;
	@RequestMapping(value="/{orderId}",method=RequestMethod.GET)
	public Object selectById(@PathVariable("orderId")Long orderId){
		_log.info("获取订单数据="+orderId);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.selectById(orderId));
		return respMessage;	
	}
	
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public Object selectByWhere(@RequestBody RequestPage requestPage){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.selectByWhere(requestPage));
		requestPage.setTotalCount(orderService.getRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	
	@RequestMapping(value="/selectByCusId",method=RequestMethod.POST)
	public Object selectByCusId(@RequestBody RequestPage requestPage){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.selectByCusId(requestPage));
		requestPage.setTotalCount(orderService.getRowCountByCusid(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	/**
	 * 待审批
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/approveFirst",method=RequestMethod.POST)
	public Object getApproveFirst(@RequestBody RequestPage requestPage){
		_log.info("待审批列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getApproveFirst(requestPage));
		requestPage.setTotalCount(orderService.getApproveFirstRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	@RequestMapping(value="/approveFirst",method=RequestMethod.POST)
	public Object approveFirst(HttpServletRequest arg0,@RequestBody OrderVo orderVo){
		String token = arg0.getHeader("token");
		_log.info("提交初级审批="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		orderService.approveFirst(orderVo,Long.valueOf(jedis.get(token)));
		respMessage.setErrorCode(200);
		respMessage.setData(orderVo);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	/**
	 * 审批中
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/approveFinal",method=RequestMethod.POST)
	public Object getApproveFinal(@RequestBody RequestPage requestPage){
		_log.info("获取初级审批列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getApproveFinal(requestPage));
		requestPage.setTotalCount(orderService.getApproveFinalRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	@RequestMapping(value="/approveFinal",method=RequestMethod.POST)
	public Object approveFinal(HttpServletRequest arg0,@RequestBody OrderVo orderVo){
		String token = arg0.getHeader("token");
		_log.info("提交终级审批="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		orderService.approveFinal(orderVo,Long.valueOf(jedis.get(token)));
		respMessage.setErrorCode(200);
		respMessage.setData(orderVo);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	/**
	 * 待放款
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/grantFirst",method=RequestMethod.POST)
	public Object getGrantFirst(@RequestBody RequestPage requestPage){
		_log.info("获取待放款列表=");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getGrantFirst(requestPage));
		requestPage.setTotalCount(orderService.getGrantFirstRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	@RequestMapping(value="/grantFirst",method=RequestMethod.POST)
	public Object grantFirst(HttpServletRequest arg0,@RequestBody OrderVo orderVo){
		String token = arg0.getHeader("token");
		_log.info("提交待放款="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		orderService.grantFirst(orderVo,Long.valueOf(jedis.get(token)));
		respMessage.setErrorCode(200);
		respMessage.setData(orderVo);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	/**
	 * 已放款
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/grantFinal",method=RequestMethod.POST)
	public Object getGrantFinal(@RequestBody RequestPage requestPage){
		_log.info("获取已放款列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getGrantFinal(requestPage));
		requestPage.setTotalCount(orderService.getGrantFinalRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	/**
	 * 请求退回
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/returnApply",method=RequestMethod.POST)
	public Object getReturnApply(@RequestBody RequestPage requestPage){
		_log.info("获取请求退回列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getReturnApply(requestPage));
		requestPage.setTotalCount(orderService.getReturnApplyRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	/**
	 * 还款列表
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/planOverFlag",method=RequestMethod.POST)
	public Object getPlanOverFlag(@RequestBody RequestPage requestPage){
		_log.info("获取还款列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getPlanOverFlag(requestPage));
		requestPage.setTotalCount(orderService.getPlanOverFlagRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}

	/**
	 * 逾期
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/notOverFlag",method=RequestMethod.POST)
	public Object getNotOverFlag(@RequestBody RequestPage requestPage){
		_log.info("获取逾期列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getNotOverFlag(requestPage));
		requestPage.setTotalCount(orderService.getNotOverFlagRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	/**
	 * 结清
	 * @param requestPage
	 * @return
	 */
	@RequestMapping(value="/get/overFlag",method=RequestMethod.POST)
	public Object getOverFlag(@RequestBody RequestPage requestPage){
		_log.info("获取结清列表");
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(orderService.getOverFlag(requestPage));
		requestPage.setTotalCount(orderService.getOverFlagRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
}
