/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.cususer.vo.CusUserVo;
import com.sun.javafx.collections.MappingChange.Map;
import com.sun.order.vo.OrderReturnMessage;
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@RestController
@RequestMapping(value="/api/mobile/userAttest")
public class UserAttestMobileControllser {
	@Resource
	private UserAttestService userAttestService;
	/**
	 * <p>Title: getByUserFouCount</p>  
	 * <p>Description: 获取已完成的认证</p>  
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public Object getByUserFouCount(HttpServletRequest arg0) {
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(userAttestService.getUserAttest(Long.valueOf(jedis.get(token))));
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	@RequestMapping(value="/getCount",method=RequestMethod.GET)
	public Object getUserAttestCount(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		Long cusUserId = Long.valueOf(jedis.get(token));
		int attestCount = userAttestService.getUserAttestCount(cusUserId);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		HashMap<String, Integer> map =new HashMap<String,Integer>();
		map.put("count", attestCount);
		respMessage.setData(map);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
}
