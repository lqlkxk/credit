package com.sun.pay.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.comuser.vo.ComUserPo;
import com.sun.comuser.vo.ComUserVo;
import com.sun.order.vo.OrderPo;
import com.sun.order.vo.OrderVo;
@Entity
@Table(name="sun_pay")
public class PayVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pay_id")
	private Long payId;
	@Column(name="pay_number")
	private String payNum;
	@ManyToOne(targetEntity=OrderPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="order_id")
	private OrderPo orderVo;
	@Column(name="pay_money")
	private BigDecimal payMoney;
	@Column(name="punish_money")
	private BigDecimal punishMoney;
	@Column(name="pay_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date payDate;
	@Column(name="remain_money")
	private BigDecimal remainMoney;
	@Column(name="remain_return_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date returnDate;
	@Column(name="over_flag")
	private String overFlag;
	@Column(name="pay_count")
	private int payCount;
	@Column(name="repay_comment")
	private String repayComment;
	@ManyToOne(targetEntity=ComUserPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="action_user")
	private ComUserPo comUserPo;
	@Column(name="action_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date actionDate;
	public Long getPayId() {
		return payId;
	}
	public void setPayId(Long payId) {
		this.payId = payId;
	}
	public String getPayNum() {
		return payNum;
	}
	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	
	public BigDecimal getPunishMoney() {
		return punishMoney;
	}
	public void setPunishMoney(BigDecimal punishMoney) {
		this.punishMoney = punishMoney;
	}
	public OrderPo getOrderVo() {
		return orderVo;
	}
	public void setOrderVo(OrderPo orderVo) {
		this.orderVo = orderVo;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public BigDecimal getRemainMoney() {
		return remainMoney;
	}
	public void setRemainMoney(BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getOverFlag() {
		return overFlag;
	}
	public void setOverFlag(String overFlag) {
		this.overFlag = overFlag;
	}
	public int getPayCount() {
		return payCount;
	}
	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}
	
	
	public String getRepayComment() {
		return repayComment;
	}
	public void setRepayComment(String repayComment) {
		this.repayComment = repayComment;
	}
	public ComUserPo getComUserPo() {
		return comUserPo;
	}
	public void setComUserPo(ComUserPo comUserPo) {
		this.comUserPo = comUserPo;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
}
