/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.order.vo;

import java.math.BigDecimal;
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
 * @date 2018年5月26日 
 *
 */
@Entity
@Table(name="sun_order")
public class OrderPo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	@Column(name="apply_money")
	private BigDecimal applyMoney;
	@Column(name="date_count")
	private Long dateCount;
	@Column(name="apply_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date applyDate;
	@Column(name="grant_money")
	private BigDecimal grantMoney;
	@Column(name="grant_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date grantDate;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getApplyMoney() {
		return applyMoney;
	}
	public void setApplyMoney(BigDecimal applyMoney) {
		this.applyMoney = applyMoney;
	}
	public Long getDateCount() {
		return dateCount;
	}
	public void setDateCount(Long dateCount) {
		this.dateCount = dateCount;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public BigDecimal getGrantMoney() {
		return grantMoney;
	}
	public void setGrantMoney(BigDecimal grantMoney) {
		this.grantMoney = grantMoney;
	}
	public Date getGrantDate() {
		return grantDate;
	}
	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}
	
}
