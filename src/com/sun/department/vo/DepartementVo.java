package com.sun.department.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sun_department")
public class DepartementVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="depa_id")
	private Long depaId;
	@Column(name="depa_code")
	private String depaCode;
	@Column(name="name")
	private String name;
	@Column(name="grade")
	private Long grade;
	@Column(name="parent_id")
	private Long parentId;
	@Column(name="display_order")
	private Long displayOrder;
	public Long getDepaId() {
		return depaId;
	}
	public void setDepaId(Long depaId) {
		this.depaId = depaId;
	}
	public String getDepaCode() {
		return depaCode;
	}
	public void setDepaCode(String depaCode) {
		this.depaCode = depaCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	
}
