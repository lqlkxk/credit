package com.sun.comuser.vo;

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
import com.sun.department.vo.DepartementPo;

@Entity
@Table(name="sun_com_user")
public class ComUserVo implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	@Column(name="emp_id")
	private String empId;
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
	@Column(name="phone")
	private String phone;
	@Column(name="telephone")
	private String telePhone;
	@Column(name="email")
	private String email;
	@Column(name="qq")
	private String qq;
	@Column(name="wechat")
	private String wechat;
	@ManyToOne(targetEntity=DepartementPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="department1")
	private DepartementPo depa1;
	@ManyToOne(targetEntity=DepartementPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="department2")
	private DepartementPo depa2;
	@ManyToOne(targetEntity=DepartementPo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="department3")
	private DepartementPo depa3;
	@Column(name="status")
	private String status;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelePhone() {
		return telePhone;
	}
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}	
	public DepartementPo getDepa1() {
		return depa1;
	}
	public void setDepa1(DepartementPo depa1) {
		this.depa1 = depa1;
	}
	public DepartementPo getDepa2() {
		return depa2;
	}
	public void setDepa2(DepartementPo depa2) {
		this.depa2 = depa2;
	}
	public DepartementPo getDepa3() {
		return depa3;
	}
	public void setDepa3(DepartementPo depa3) {
		this.depa3 = depa3;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
