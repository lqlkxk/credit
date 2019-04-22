/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.order.vo;

/**@author SUNCHANGQING
 * @date 2018年5月28日 
 *
 */
public class OrderReturnMessage {
	private String name;
	private String phone;
	private String photo;
	private int orderCount;
	private int attestCount;
	public OrderReturnMessage() {}
	public OrderReturnMessage(String name,String phone,String photo,int orderCount,int attestCount) {
		this.name = name;
		this.phone = phone;
		this.photo = photo;
		this.orderCount = orderCount;
		this.attestCount = attestCount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getAttestCount() {
		return attestCount;
	}
	public void setAttestCount(int attestCount) {
		this.attestCount = attestCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
