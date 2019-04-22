package com.sun.comuser.dao;

import java.util.List;

import com.sun.comuser.vo.ComUserVo;

public interface ComUserDao {
	public ComUserVo selectById(Long userId);
	public List<ComUserVo> selectSome(ComUserVo comUserVo);
	public List<ComUserVo> selectAll();
	public void insertOne(ComUserVo comUserVo);
	public void updateOne(ComUserVo comUserVo);
	public void deleteById(Long userId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
