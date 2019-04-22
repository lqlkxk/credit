package com.sun.system.message;

import java.io.Serializable;

import com.sun.util.view.RequestPage;

/**
 * API响应信息类
 * @author SUNCHANGQING
 * 2018年4月27日下午7:58:44
 */
public class RespMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	//响应状态:true/false,此字段是通信及请求权限标识，非交易标识，交易是否成功需要查看data来判断
	private boolean success = true;
	//错误码:success为false为接口响应错误代码
	private Integer errorCode;
	//错误信息:success为false为接口响应错误描述
	private String errorMsg;
	//业务成功数据:success为true时有值，详见业务响应参数
	private Object data;
	//页面数据
	private RequestPage requestPage;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public RequestPage getRequestPage() {
		return requestPage;
	}
	public void setRequestPage(RequestPage requestPage) {
		this.requestPage = requestPage;
	}
	
}
