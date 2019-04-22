package com.sun.error.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.system.message.RespMessage;
/**
 * @author SUNCHANGQING
 * @date 2018年5月17日 
 * 各种拦截器跳转请求
 */
@RestController
@RequestMapping(value="/api/error")
public class ErrorController {
	/**
	 * <p>Title: interceptorUrl1</p>  
	 * <p>Description:请求路径非法跳转 </p>  
	 * @return
	 */
	@RequestMapping(value="/interceptorurl1",method=RequestMethod.GET)
	public Object interceptorUrl1(){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(401);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("请求路径非法");
		return respMessage;
	}
	/**
	 * <p>Title: interceptorUrl2</p>  
	 * <p>Description: token失效跳转</p>  
	 * @return
	 */
	@RequestMapping(value="/interceptorurl2",method=RequestMethod.GET)
	public Object interceptorUrl2(){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(402);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("token已失效");
		return respMessage;
	}
	/**
	 * <p>Title: interceptorUrl3</p>  
	 * <p>Description: 未添加token跳转</p>  
	 * @return
	 */
	@RequestMapping(value="/interceptorurl3",method=RequestMethod.GET)
	public Object interceptorUrl3(){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(402);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("未添加token");
		return respMessage;
	}
}
