/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.logout.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sun.system.message.RespMessage;
import com.sun.util.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年5月29日 
 *
 */
@RestController
@RequestMapping(value="/api/cuslogout")
public class LogoutController {
	@RequestMapping(method=RequestMethod.GET)
	public Object getOrderCount(HttpServletRequest arg0){
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		jedis.del(token);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;	
	}
}
