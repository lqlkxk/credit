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
public class DepartementPo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="depa_id")
	private Long depaId;
	@Column(name="name")
	private String name;
	public Long getDepaId() {
		return depaId;
	}
	public void setDepaId(Long depaId) {
		this.depaId = depaId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
