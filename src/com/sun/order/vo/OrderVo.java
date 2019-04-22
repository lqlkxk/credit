package com.sun.order.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.comuser.vo.ComUserPo;

@Entity
@Table(name="sun_order")
public class OrderVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderId;
	@Column(name="order_number")
	private String orderNum;
	@Column(name="cus_id")
	private Long cusId;
	@Column(name="name")
	private String name;
	@Column(name="mobile_number")
	private String mobileNum;
	@Column(name="identity_card")
	private String identityCard;
	@Column(name="gender")
	private String gender;
	@Column(name="salary")
	private BigDecimal salary;
	@Column(name="apply_money")
	private BigDecimal applyMoney;
	@Column(name="date_count")
	private Long dateCount;
	@Column(name="pay_count")
	private Long payCount;
	@Column(name="apply_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date applyDate;
	@Column(name="sesame_seed")
	private Long sesameSeed;
	@Column(name="approve_money")
	private BigDecimal approveMoney;
	@Column(name="approve_first_flag")
	private String firstFlag;
	@Column(name="approve_first_comment")
	private String firstComment;
	@ManyToOne(targetEntity=ComUserPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="approve_first_user")
	private ComUserPo firstComUser;
	@Column(name="approve_first_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date firstDate;
	@Column(name="approve_final_flag")
	private String finalFlag;
	@Column(name="approve_final_comment")
	private String finalCommment;
	@ManyToOne(targetEntity=ComUserPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="approve_final_user")
	private ComUserPo finalComUser;
	@Column(name="approve_final_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date finalDate;
	@Column(name="grant_flag")
	private String grantFlag;
	@Column(name="grant_money")
	private BigDecimal grantMoney;
	@Column(name="grant_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date grantDate;
	@Column(name="return_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date returnDate;
	@Column(name="comment")
	private String comment;
	@ManyToOne(targetEntity=ComUserPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="action_user")
	private ComUserPo actionComUser;
	@Column(name="action_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date actionDate;
	@Column(name="remain_return_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date remainReturnDate;
	@Column(name="punish_money")
	private BigDecimal punishMoney;
	@Column(name="pay_money")
	private BigDecimal payMoney; 
	@Column(name="over_flag")
	private String overFlag;
	@Column(name="degree")
	private int count;
	@Transient
	private List payList;
	@Transient
	private String actionType;
	public List getPayList() {
		return payList;
	}
	public void setPayList(List payList) {
		this.payList = payList;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public BigDecimal getApplyMoney() {
		return applyMoney;
	}
	public void setApplyMoney(BigDecimal applyMoney) {
		this.applyMoney = applyMoney;
	}
	public Long getPayCount() {
		return payCount;
	}
	public void setPayCount(Long payCount) {
		this.payCount = payCount;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Long getSesameSeed() {
		return sesameSeed;
	}
	public void setSesameSeed(Long sesameSeed) {
		this.sesameSeed = sesameSeed;
	}
	public BigDecimal getApproveMoney() {
		return approveMoney;
	}
	public void setApproveMoney(BigDecimal approveMoney) {
		this.approveMoney = approveMoney;
	}
	
	public ComUserPo getFirstComUser() {
		return firstComUser;
	}
	public void setFirstComUser(ComUserPo firstComUser) {
		this.firstComUser = firstComUser;
	}
	public ComUserPo getFinalComUser() {
		return finalComUser;
	}
	public void setFinalComUser(ComUserPo finalComUser) {
		this.finalComUser = finalComUser;
	}
	public ComUserPo getActionComUser() {
		return actionComUser;
	}
	public void setActionComUser(ComUserPo actionComUser) {
		this.actionComUser = actionComUser;
	}
	public String getFirstFlag() {
		return firstFlag;
	}
	public void setFirstFlag(String firstFlag) {
		this.firstFlag = firstFlag;
	}
	public String getFirstComment() {
		return firstComment;
	}
	public void setFirstComment(String firstComment) {
		this.firstComment = firstComment;
	}
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	public String getFinalFlag() {
		return finalFlag;
	}
	public void setFinalFlag(String finalFlag) {
		this.finalFlag = finalFlag;
	}
	public String getFinalCommment() {
		return finalCommment;
	}
	public void setFinalCommment(String finalCommment) {
		this.finalCommment = finalCommment;
	}
	public Date getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
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
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public String getOverFlag() {
		return overFlag;
	}
	public void setOverFlag(String overFlag) {
		this.overFlag = overFlag;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getGrantFlag() {
		return grantFlag;
	}
	public void setGrantFlag(String grantFlag) {
		this.grantFlag = grantFlag;
	}
	public Long getDateCount() {
		return dateCount;
	}
	public void setDateCount(Long dateCount) {
		this.dateCount = dateCount;
	}
	public BigDecimal getPunishMoney() {
		return punishMoney;
	}
	public void setPunishMoney(BigDecimal punishMoney) {
		this.punishMoney = punishMoney;
	}
	public Date getRemainReturnDate() {
		return remainReturnDate;
	}
	public void setRemainReturnDate(Date remainReturnDate) {
		this.remainReturnDate = remainReturnDate;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
}
