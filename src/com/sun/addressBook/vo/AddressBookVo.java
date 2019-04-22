/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.addressBook.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@Entity
@Table(name="sun_address_book")
public class AddressBookVo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="table_id")
	private Long tableId;
	@Column(name="cus_id")
	private Long cusId;
	@Column(name="name")
	private String name;
	@Column(name="phone")
	private String phone;
	@Column(name="type")
	private String type;
	@Column(name="address")
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
