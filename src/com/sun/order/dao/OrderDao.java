package com.sun.order.dao;

import java.util.List;

import com.sun.order.vo.OrderVo;

public interface OrderDao {
	public OrderVo selectById(Long orderId);
	public List<OrderVo> selectAll();
	public void insertOne(OrderVo orderVo);
	public void updateOne(OrderVo orderVo);
	public void deleteById(Long orderId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
