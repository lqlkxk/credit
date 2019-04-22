package com.sun.order.service;

import java.util.List;

import com.sun.cususer.vo.CusUserVo;
import com.sun.cususeradd.vo.CusUserAddVo;
import com.sun.order.vo.OrderVo;
import com.sun.util.view.RequestPage;

public interface OrderService {
	public OrderVo selectById(Long orderId);
	public List<OrderVo> selectAll();
	public void insertOne(OrderVo orderVo);
	public void updateOne(OrderVo orderVo);
	public void deleteById(Long orderId);
	public List<OrderVo> selectByWhere(RequestPage requestPage);
	public int getRowCount(RequestPage requestPage);
	public List<OrderVo> selectByCusId(RequestPage requestPage);
	public int getRowCountByCusid(RequestPage requestPage);
	public List<OrderVo> getApproveFirst(Long orderId);
	public List<OrderVo> getApproveFinal(Long orderId);
	public List<OrderVo> getGrantFirst(Long orderId);
	public List<OrderVo> getGrantFinal(Long orderId);
	public List<OrderVo> getReturnApply(Long orderId);
	public List<OrderVo> getNotOverFlag(Long orderId);
	public List<OrderVo> getOverFlag(Long orderId);
	public List<OrderVo> getApproveFirst();
	public List<OrderVo> getApproveFinal();
	public List<OrderVo> getGrantFirst();
	public List<OrderVo> getGrantFinal();
	public List<OrderVo> getReturnApply();
	public List<OrderVo> getNotOverFlag();
	public List<OrderVo> getOverFlag();
	public void approveFirst(OrderVo orderVo,Long comUserId);
	public void approveFinal(OrderVo orderVo,Long comUserId);
	public void grantFirst(OrderVo orderVo,Long comUserId);
	public void insert(CusUserVo cusUserVo,CusUserAddVo cusUserAddVo,OrderVo orderVo);
	public List<OrderVo> getApproveFirst(RequestPage requestPage);
	public int getApproveFirstRowCount(RequestPage requestPage);
	public List<OrderVo> getApproveFinal(RequestPage requestPage);
	public int getApproveFinalRowCount(RequestPage requestPage);
	public List<OrderVo> getGrantFirst(RequestPage requestPage);
	public int getGrantFirstRowCount(RequestPage requestPage);
	public List<OrderVo> getGrantFinal(RequestPage requestPage);
	public int getGrantFinalRowCount(RequestPage requestPage);
	public List<OrderVo> getReturnApply(RequestPage requestPage);
	public int getReturnApplyRowCount(RequestPage requestPage);
	public List<OrderVo> getNotOverFlag(RequestPage requestPage);
	public int getNotOverFlagRowCount(RequestPage requestPage);
	public List<OrderVo> getOverFlag(RequestPage requestPage);
	public int getOverFlagRowCount(RequestPage requestPage);
	public List<OrderVo> getPlanOverFlag(RequestPage requestPage);
	public int getPlanOverFlagRowCount(RequestPage requestPage);
	/** <p>Title: getOrderCount</p>  
	 * <p>Description: </p>  
	 * @param cusUserId
	 * @return  
	 */ 
	public int getOrderCount(Long cusUserId);
	
}
