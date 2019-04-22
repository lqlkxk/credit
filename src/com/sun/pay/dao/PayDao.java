package com.sun.pay.dao;

import java.util.List;


import com.sun.pay.vo.PayVo;

public interface PayDao {
	public PayVo selectById(Long payId);
	public List<PayVo> selectAll();
	public void insertOne(PayVo payVo);
	public void updateOne(PayVo payVo);
	public void deleteById(Long payId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
