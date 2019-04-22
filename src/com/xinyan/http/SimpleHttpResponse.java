package com.xinyan.http;

import java.io.Serializable;

import com.xinyan.util.HttpUtil;

/**
 * @version
 */
public class SimpleHttpResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String entityString;
	private String errorMessage;

	/**
	 * @param statusCode
	 * @param entityString
	 */
	public SimpleHttpResponse(int statusCode, String entityString, String errorMessage) {
		super();
		this.statusCode = statusCode;
		this.entityString = entityString;
		this.errorMessage = errorMessage;
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isRequestSuccess() {
		return HttpUtil.isRequestSuccess(statusCode);
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the entityString
	 */
	public String getEntityString() {
		return entityString;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
