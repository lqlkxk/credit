package com.sun.cususer.dao;

import java.util.List;

import com.sun.cususer.vo.CusUserPo;
import com.sun.cususer.vo.CusUserVo;

public interface CusUserDao {
	public CusUserPo selectCusUserPoById(Long cusId);
	public CusUserVo selectById(Long cusId);
	public List<CusUserVo> selectSome(CusUserVo cusUserVo);
	public List<CusUserVo> selectAll();
	public void insertOne(CusUserVo cusUserVo);
	public void updateOne(CusUserVo cusUserVo);
	public void deleteById(Long cusId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
