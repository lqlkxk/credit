package com.sun.cususeradd.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.cususer.vo.CusUserVo;

@Entity
@Table(name="sun_cus_user_add")
public class CusUserAddVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cus_add_id")
	private Long cusAddId;
	@OneToOne(optional = false,cascade = CascadeType.REFRESH)
	@JoinColumn(name = "cus_id", referencedColumnName = "cus_id", unique = true)
	private CusUserVo cusUserVo;
	@Column(name="salary")
	private BigDecimal salary;
	@Column(name="debt")
	private BigDecimal debt;
	@Column(name="company_name")
	private String companyName;
	@Column(name="company_post")
	private String companyPost;
	@Column(name="company_tele")
	private String companyTele;
	@Column(name="company_place")
	private String companyPlace;
	@Column(name="company_address")
	private String companyAddress;
	@Column(name="contact1_name")
	private String contact1Name;
	@Column(name="contact1_mobile")
	private String contact1Mobile;
	@Column(name="contact2_name")
	private String contact2Name;
	@Column(name="contact2_mobile")
	private String contact2Mobile;
	@Column(name="reserve_mobile")
	private String reserveMobile;
	
	
	@Column(name="wife_name")
	private String wifeName;
	@Column(name="wife_tel")
	private String wifeTel;
	@Column(name="friend_name")
	private String friendName;
	@Column(name="friend_tel")
	private String friendTel;
	@Column(name="collegue_name")
	private String collegueName;
	@Column(name="collegue_tel")
	private String collegueTel;
	@Column(name="asset")
	private String asset;
	@Column(name="insurance")
	private String insurance;
	@Column(name="mobile_type")
	private String mobileType;
	
	public Long getCusAddId() {
		return cusAddId;
	}
	public void setCusAddId(Long cusAddId) {
		this.cusAddId = cusAddId;
	}
	public CusUserVo getCusUserVo() {
		return cusUserVo;
	}
	public void setCusUserVo(CusUserVo cusUserVo) {
		this.cusUserVo = cusUserVo;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public BigDecimal getDebt() {
		return debt;
	}
	public void setDebt(BigDecimal debt) {
		this.debt = debt;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyPost() {
		return companyPost;
	}
	public void setCompanyPost(String companyPost) {
		this.companyPost = companyPost;
	}
	public String getCompanyTele() {
		return companyTele;
	}
	public void setCompanyTele(String companyTele) {
		this.companyTele = companyTele;
	}
	public String getCompanyPlace() {
		return companyPlace;
	}
	public void setCompanyPlace(String companyPlace) {
		this.companyPlace = companyPlace;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getContact1Name() {
		return contact1Name;
	}
	public void setContact1Name(String contact1Name) {
		this.contact1Name = contact1Name;
	}
	public String getContact1Mobile() {
		return contact1Mobile;
	}
	public void setContact1Mobile(String contact1Mobile) {
		this.contact1Mobile = contact1Mobile;
	}
	public String getContact2Name() {
		return contact2Name;
	}
	public void setContact2Name(String contact2Name) {
		this.contact2Name = contact2Name;
	}
	public String getContact2Mobile() {
		return contact2Mobile;
	}
	public void setContact2Mobile(String contact2Mobile) {
		this.contact2Mobile = contact2Mobile;
	}
	public String getReserveMobile() {
		return reserveMobile;
	}
	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
	public String getWifeName() {
		return wifeName;
	}
	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}
	public String getWifeTel() {
		return wifeTel;
	}
	public void setWifeTel(String wifeTel) {
		this.wifeTel = wifeTel;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendTel() {
		return friendTel;
	}
	public void setFriendTel(String friendTel) {
		this.friendTel = friendTel;
	}
	public String getCollegueName() {
		return collegueName;
	}
	public void setCollegueName(String collegueName) {
		this.collegueName = collegueName;
	}
	public String getCollegueTel() {
		return collegueTel;
	}
	public void setCollegueTel(String collegueTel) {
		this.collegueTel = collegueTel;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	
}
