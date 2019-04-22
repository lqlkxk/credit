package com.sun.cususeradd.service;

import java.util.List;

import com.sun.cususeradd.vo.CusUserAddVo;
import com.sun.util.view.RequestPage;

public interface CusUserAddService {
	public CusUserAddVo selectById(Long cusAddId);
	public List<CusUserAddVo> selectByWhere(RequestPage requestPage);
	public List<CusUserAddVo> selectAll();
	public void insertOne(CusUserAddVo cusUserAddVo);
	public void updateOne(CusUserAddVo cusUserAddVo);
	public void deleteById(Long cusAddId);
	public int getRowCount(RequestPage requestPage);
	public CusUserAddVo selectByCusId(Long cusId);
	public int update(CusUserAddVo cusUserAddVo);
}
