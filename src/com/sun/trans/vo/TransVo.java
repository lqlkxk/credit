/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.trans.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**@author SUNCHANGQING
 * @date 2018年6月5日 
 *
 */
@Entity
@Table(name="sun_xinyan_trans")
public class TransVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="table_id")
	private Long tableId;
	@Column(name="trans_id")
	private String transId;
	@Column(name="trans_date")
	private String transDate;
	@Column(name="cus_id")
	private Long cusId;
	@Column(name="type_id")
	private Long typeId;
	@Column(name="type_name")
	private String typeName;
	@Column(name="success")
	private String success;
	@Column(name="create_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date createDate;
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
