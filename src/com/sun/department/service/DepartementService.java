package com.sun.department.service;

import java.util.List;

import com.sun.department.vo.DepartementVo;

public interface DepartementService {
	public DepartementVo selectById(Long depaId);
	public List<DepartementVo> selectSome(DepartementVo departementVo);
	public void insertOne(DepartementVo departementVo);
	public void updateOne(DepartementVo departementVo);
	public void deleteById(Long depaId);
}
