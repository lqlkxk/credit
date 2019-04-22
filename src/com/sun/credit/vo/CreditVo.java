/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.credit.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**@author SUNCHANGQING
 * @date 2018年6月12日 
 *
 */
@Entity
@Table(name="sun_xinyan_credit")
public class CreditVo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="table_id")
	private Long tableId;
	@Column(name="cus_id")
	private Long cusId;
	@Column(name="phone_no")
	private String phone_no;
	@Column(name="id_name")
	private String id_name;
	@Column(name="id_no")
	private String id_no;
	@Column(name="credit_type")
	private String creditType;
	@Column(name="data")
	private String data;
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
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getId_name() {
		return id_name;
	}
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	
}
