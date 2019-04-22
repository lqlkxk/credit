/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@RestController
@RequestMapping(value="/api/userAttest")
public class UserAttestControllser {
	@Resource
	private UserAttestService userAttestService;
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public Object getByUserFouCount(@PathVariable("userId")Long userId) {
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(userAttestService.getUserAttest(userId));
		return respMessage;
	}
}
