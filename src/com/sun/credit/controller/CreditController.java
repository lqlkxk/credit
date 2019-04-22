/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.credit.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.credit.service.CreditService;
import com.sun.credit.vo.CreditVo;
import com.sun.cususer.controller.CusUserController;
import com.sun.system.message.RespMessage;
import com.sun.util.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年6月12日 
 *
 */
@RestController
@RequestMapping(value="/api/credit")
public class CreditController {
	public static Log _log = LogFactory.getLog(CreditController.class);
	@Resource
	private CreditService creditService;
	@RequestMapping(value="/creditData",method=RequestMethod.POST)
	public Object getCreditData(@RequestBody CreditVo creditVo) {
		_log.info("查看数据库中的信用信息="+creditVo.getCreditType());
		RespMessage respMessage = new RespMessage();
		CreditVo vo = creditService.getCreditVo(creditVo);
		if(vo != null) {
			_log.info("查看数据库中的信用信息成功");
			respMessage.setErrorCode(200);
			respMessage.setData(vo);
		}else {
			_log.info("查看数据库中的信用信息失败");
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("查看数据库中的信用信息失败");
		}
		return respMessage;
	}
}
