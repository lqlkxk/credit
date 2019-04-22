package com.sun.cususer.service;

import java.util.List;

import com.sun.cususer.vo.CusUserPo;
import com.sun.cususer.vo.CusUserVo;
import com.sun.util.view.RequestPage;

public interface CusUserService {
	public CusUserPo selectCusUserPoById(Long cusId);
	public CusUserVo selectById(Long cusId);
	public List<CusUserVo> selectByWhere(RequestPage requestPage);
	public List<CusUserVo> selectAll();
	public void insertOne(CusUserVo cusUserVo);
	public void updateOne(CusUserVo cusUserVo);
	public void deleteById(Long cusId);
	public int getRowCount(RequestPage requestPage);
	public int updateWhere(String name,Long cusId);
	public CusUserVo selectByPhone(String phone);
	public int update(CusUserVo cusUserVo);
	public int updateSelfPhoto(String selfPhoto,Long cusId);
}
