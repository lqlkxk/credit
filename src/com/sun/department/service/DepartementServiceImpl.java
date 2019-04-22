package com.sun.department.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.department.dao.DepartementDao;
import com.sun.department.vo.DepartementVo;
@Service
public class DepartementServiceImpl implements DepartementService {
	@Resource
	private DepartementDao departementDao;
	@Override
	public DepartementVo selectById(Long depaId) {
		// TODO Auto-generated method stub
		return departementDao.selectById(depaId);
	}

	@Override
	public List<DepartementVo> selectSome(DepartementVo departementVo) {
		// TODO Auto-generated method stub
		return departementDao.selectSome(departementVo);
	}

	@Override
	public void insertOne(DepartementVo departementVo) {
		// TODO Auto-generated method stub
		departementDao.insertOne(departementVo);
	}

	@Override
	public void updateOne(DepartementVo departementVo) {
		// TODO Auto-generated method stub
		departementDao.updateOne(departementVo);
	}

	@Override
	public void deleteById(Long depaId) {
		// TODO Auto-generated method stub
		departementDao.deleteById(depaId);
	}

}
