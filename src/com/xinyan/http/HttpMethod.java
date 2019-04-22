package com.xinyan.http;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @version
 */
@XmlType(name = "httpMethod")
@XmlEnum
public enum HttpMethod {
	GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS;

	/**
	 * @return
	 */
	public String value() {
		return name();
	}

	/**
	 * @param v
	 * @return
	 */
	public static HttpMethod fromValue(String v) {
		return valueOf(v);
	}

	/**
	 * @return
	 */
	public static HttpMethod getDefault() {
		return POST;
	}
}
