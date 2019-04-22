package com.sun.system.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sun.system.message.RespMessage;
/**
 * 添加异常处理
 * @author SUNCHANGQING
 * 2018年5月1日上午8:50:19
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
	/**
	 * 400 - Bad Request
	 * @author SUNCHANGQING
	 * 2018年5月1日上午8:49:58
	 *
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {  
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(400);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("参数解析失败");
		respMessage.setData(e);
		return respMessage;
    }
	/**
	 * <p>Title: handleDefaultHandlerExceptionResolver</p>  
	 * <p>Description: </p>  
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public Object handleDefaultHandlerExceptionResolver(IllegalStateException e) {  
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(401);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("参数类型不符");
		respMessage.setData(e);
		return respMessage;
    }
	/**
	 * 405 - Method Not Allowed
	 * @author SUNCHANGQING
	 * 2018年5月1日上午8:52:27
	 *
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Object handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {  
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(405);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("不支持当前请求方法");
		respMessage.setData(e);
		return respMessage;
    } 
	/**
	 * 415 - Unsupported Media Type
	 * @author SUNCHANGQING
	 * 2018年5月1日上午8:54:04
	 *
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)  
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class) 
	public Object handleHttpMediaTypeNotSupportedException(Exception e) {  
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(415);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("不支持当前媒体类型");
		respMessage.setData(e);
		return respMessage;
    }
	/**
	 * 500 - Internal Server Error
	 * @author SUNCHANGQING
	 * 2018年5月1日上午8:55:29
	 *
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e) { 
		System.out.println(e);
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(500);
		respMessage.setSuccess(false);
		respMessage.setErrorMsg("服务运行异常");
		respMessage.setData(e);
		return respMessage;
    }  
}
