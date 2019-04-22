package com.sun.login.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.login.service.CusLoginService;
import com.sun.login.vo.LoginVo;
import com.sun.system.message.RespMessage;
import com.sun.util.properties.SystemProperties;


@RestController
@RequestMapping(value="/api/cuslogin")
public class CusLoginController {
	public static Log _log = LogFactory.getLog(CusLoginController.class);
	@Resource
	private CusLoginService cusLoginService;
	@RequestMapping(value="/smscode/{phone}",method=RequestMethod.GET)
	public Object smsCode(@PathVariable("phone")String phone){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusLoginService.smsCode(phone));
		return respMessage;
	}
	@RequestMapping(method=RequestMethod.POST)
	public Object doLogin(@RequestBody LoginVo loginVo){
		_log.info("用户登录"+loginVo.getPhone());
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(cusLoginService.doLoginCode(loginVo));
		return respMessage;
	}
	@RequestMapping(value="/{phone}/{smscode}",method=RequestMethod.GET)
	public Object doLoginCode(@PathVariable("phone")String phone,@PathVariable("smscode")String smscode){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		LoginVo loginVo = new LoginVo();
		loginVo.setPhone(phone);
		loginVo.setSmscode(smscode);
		respMessage.setData(cusLoginService.doLoginCode(loginVo));
		return respMessage;
	}
}
