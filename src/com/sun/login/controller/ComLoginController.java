package com.sun.login.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.login.service.ComLoginService;
import com.sun.login.vo.LoginVo;
import com.sun.system.message.RespMessage;

@RestController
@RequestMapping(value="/api/comlogin")
public class ComLoginController {
	@Resource
	private ComLoginService comLoginService;
	
	public Object doLoginCode(){
		return null;
	}
	@RequestMapping(value="/code",method=RequestMethod.POST)
	public Object doLoginPassCode(@RequestBody LoginVo loginVo){
		RespMessage respMessage = new RespMessage();
		comLoginService.doLoginPassCode(loginVo);
		respMessage.setErrorCode(200);
		respMessage.setData(loginVo);
		return respMessage;
	}
	@RequestMapping(method=RequestMethod.POST)
	public Object doLoginPass(@RequestBody LoginVo loginVo){
		RespMessage respMessage = new RespMessage();
		comLoginService.doLoginPass(loginVo);
		respMessage.setErrorCode(200);
		respMessage.setData(loginVo);
		return respMessage;
	}
	@RequestMapping(value="/code",method=RequestMethod.GET)
	public Object smsCode(){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(comLoginService.code());
		return respMessage;
	}
}
