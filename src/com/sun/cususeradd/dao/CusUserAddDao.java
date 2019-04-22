package com.sun.cususeradd.dao;

import java.util.List;

import com.sun.cususeradd.vo.CusUserAddVo;

public interface CusUserAddDao {
	public CusUserAddVo selectById(Long cusAddId);
	public List<CusUserAddVo> selectSome(CusUserAddVo cusUserAddVo);
	public List<CusUserAddVo> selectAll();
	public void insertOne(CusUserAddVo cusUserAddVo);
	public void updateOne(CusUserAddVo cusUserAddVo);
	public void deleteById(Long cusAddId);
	public List find(String hql, int pageStart, int rowCount);
	public int getRowCount(String hql);
	public int update(String fromAndWhere);
}
