package com.sun.cususer.vo;

import java.io.Serializable;
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

@Entity
@Table(name="sun_cus_user")
public class CusUserVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cus_id")
	private Long cusId;
	@Column(name="sys_id")
	private String sysId;
	@Column(name="name")
	private String name;
	@Column(name="password")
	private String password;
	@Column(name="gender")
	private String gender;
	@Column(name="birthday")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date birthday;
	@Column(name="identity_card")
	private String identityCard;
	@Column(name="mobile_number")
	private String mobileNumber;
	@Column(name="tele_number")
	private String teleNumber;
	@Column(name="email")
	private String email;
	@Column(name="qq_number")
	private String qqNumber;
	@Column(name="wechat_number")
	private String wechatNumber;
	@Column(name="create_date")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")  //取日期时使用 
	@DateTimeFormat(pattern="yyyy-MM-dd")//存日期使用
	private Date createDate;
	@ManyToOne(targetEntity=ComUserPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="manager_id")
	private ComUserPo comUserPo;
	@Column(name="comment")
	private String comment;
	@Column(name="sesame_seed")
	private Long sesameSeed;
	@Column(name="place")
	private String place;
	@Column(name="address")
	private String address;
	@Column(name="photo_right")
	private String photoRight;
	@Column(name="photo_left")
	private String photoLeft;
	@Column(name="photo")
	private String photo;
	@Column(name="self_photo")
	private String selfPhoto;
	@Column(name="status")
	private String status;
	@Column(name="pay_count")
	private Long payCount;
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getTeleNumber() {
		return teleNumber;
	}
	public void setTeleNumber(String teleNumber) {
		this.teleNumber = teleNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public String getWechatNumber() {
		return wechatNumber;
	}
	public void setWechatNumber(String wechatNumber) {
		this.wechatNumber = wechatNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public ComUserPo getComUserPo() {
		return comUserPo;
	}
	public void setComUserPo(ComUserPo comUserPo) {
		this.comUserPo = comUserPo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getSesameSeed() {
		return sesameSeed;
	}
	public void setSesameSeed(Long sesameSeed) {
		this.sesameSeed = sesameSeed;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhotoRight() {
		return photoRight;
	}
	public void setPhotoRight(String photoRight) {
		this.photoRight = photoRight;
	}
	public String getPhotoLeft() {
		return photoLeft;
	}
	public void setPhotoLeft(String photoLeft) {
		this.photoLeft = photoLeft;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getPayCount() {
		return payCount;
	}
	public void setPayCount(Long payCount) {
		this.payCount = payCount;
	}
	public String getSelfPhoto() {
		return selfPhoto;
	}
	public void setSelfPhoto(String selfPhoto) {
		this.selfPhoto = selfPhoto;
	}
	
}
