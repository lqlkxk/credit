package com.sun.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.cususer.vo.CusUserVo;
import com.sun.cususeradd.vo.CusUserAddVo;
import com.sun.order.dao.OrderDao;
import com.sun.order.vo.OrderVo;
import com.sun.pay.dao.PayDao;
import com.sun.system.time.Time;
import com.sun.util.view.RequestPage;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	public static Log _log = LogFactory.getLog(OrderServiceImpl.class);
	@Resource
	private OrderDao orderDao;
	@Resource
	private PayDao payDao;
	@Override
	public void insert(CusUserVo cusUserVo,CusUserAddVo cusUserAddVo,OrderVo orderVo) {
		_log.info("新增订单");
		// TODO Auto-generated method stub
		String mobileNumber = cusUserVo.getMobileNumber();
		orderVo.setOrderNum(Time.getTimeString()+mobileNumber.substring(7));
		orderVo.setCusId(cusUserVo.getCusId());
		orderVo.setName(cusUserVo.getName());
		orderVo.setMobileNum(mobileNumber);
		orderVo.setIdentityCard(cusUserVo.getIdentityCard());
		orderVo.setGender(cusUserVo.getGender());
		orderVo.setSalary(cusUserAddVo.getSalary());
		orderVo.setApplyDate(new Date());
		orderVo.setSesameSeed(cusUserVo.getSesameSeed());
		orderVo.setFirstFlag("S");;
		orderVo.setFinalFlag("S");
		orderVo.setGrantFlag("S");
		orderVo.setPayCount(cusUserVo.getPayCount());
		orderVo.setPayMoney(new BigDecimal(0));
		orderVo.setCount(0);
		insertOne(orderVo);
	}
	@Override
	public OrderVo selectById(Long orderId) {
		// TODO Auto-generated method stub
		return orderDao.selectById(orderId);
	}

	@Override
	public List<OrderVo> selectAll() {
		// TODO Auto-generated method stub
		return orderDao.selectAll();
	}

	@Override
	public void insertOne(OrderVo orderVo) {
		// TODO Auto-generated method stub
		orderDao.insertOne(orderVo);
	}

	@Override
	public void updateOne(OrderVo orderVo) {
		// TODO Auto-generated method stub
		orderDao.updateOne(orderVo);
	}

	@Override
	public void deleteById(Long orderId) {
		// TODO Auto-generated method stub
		orderDao.deleteById(orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> selectByWhere(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from OrderVo";
		String queryHql = "";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			Iterator iter = requestMap.entrySet().iterator();
			boolean first = true;
			Entry<Object, Object> entry = null;
			Object key = null;
			Object val = null;
			while(iter.hasNext()){
				entry = (Entry<Object, Object>) iter.next();
				key = entry.getKey();
				val = entry.getValue();
				if(val != null && !val.equals("")){
					if(first){
						queryHql += " where "+key+" like '%"+val+"%'";
						first = false;
					}else{
						queryHql += " and "+key+" like '%"+val+"%'";
					}
				}
			}
		}
		queryHql += " order by orderId desc";
		hql += " "+queryHql;
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderVo> selectByCusId(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId =";
		String queryHql = "";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			hql += requestMap.get("cusId").toString();
		}
		queryHql += " order by orderId desc";
		hql += " "+queryHql;
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo";
		String queryHql = "";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			Iterator iter = requestMap.entrySet().iterator();
			boolean first = true;
			Entry<Object, Object> entry = null;
			Object key = null;
			Object val = null;
			while(iter.hasNext()){
				entry = (Entry<Object, Object>) iter.next();
				key = entry.getKey();
				val = entry.getValue();
				if(val != null && !val.equals("")){
					if(first){
						queryHql += " where "+key+" like '%"+val+"%'";
						first = false;
					}else{
						queryHql += " and "+key+" like '%"+val+"%'";
					}
				}
			}
		}
		hql += " "+queryHql;
		return orderDao.getRowCount(hql);
	}
	
	@Override
	public int getRowCountByCusid(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where cusId =";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			hql += requestMap.get("cusId").toString();
		}
		return orderDao.getRowCount(hql);
	}
	
	public int getOrderCount(Long cusUserId) {
		String hql = "select count(orderId) from OrderVo where cusId = "+cusUserId;
		return orderDao.getRowCount(hql);
	}
	/**
	 * 获取待审批申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getApproveFirst(Long orderId) {
		_log.info("获取待审批申请="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and firstFlag = 'S' order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取审批中申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getApproveFinal(Long orderId) {
		_log.info("获取审批中申请="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and firstFlag = 'Y' and finalFlag = 'S' order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取待放款
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getGrantFirst(Long orderId) {
		_log.info("获取待放款="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'S' order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取已放款
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getGrantFinal(Long orderId) {
		_log.info("获取已放款="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取审批退回
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getReturnApply(Long orderId) {
		_log.info("获取审批退回="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and (firstFlag = 'N' or finalFlag = 'N')  order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取逾期
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getNotOverFlag(Long orderId) {
		_log.info("获取逾期="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'N' order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取结清
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getOverFlag(Long orderId) {
		_log.info("获取结清="+orderId);
		// TODO Auto-generated method stub
		String hql = "from OrderVo where cusId = "+orderId+" and firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'Y' order by orderId desc";
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取待审批申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getApproveFirst() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'S' order by orderId desc";
		_log.info("获取审批中申请="+hql);
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取审批中申请
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getApproveFinal() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'Y' and finalFlag = 'S' order by orderId desc";
		_log.info("获取审批中申请="+hql);
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取待放款
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getGrantFirst() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'S' order by orderId desc";
		_log.info("获取待放款="+hql);
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取已放款
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getGrantFinal() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' order by orderId desc";
		_log.info("获取已放款="+hql);
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取审批退回
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getReturnApply() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'N' or finalFlag = 'N' order by orderId desc";
		_log.info("获取审批退回="+hql);
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取逾期
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getNotOverFlag() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'N' order by orderId desc";
		_log.info("获取逾期="+hql);
		return orderDao.find(hql,-1,-1);
	}
	/**
	 * 获取结清
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getOverFlag() {
		// TODO Auto-generated method stub
		String hql = "from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'Y' order by orderId desc";
		_log.info("获取结清="+hql);
		return orderDao.find(hql,-1,-1);
	}

	@Override
	public void approveFirst(OrderVo orderVo,Long comUserId) {
		// TODO Auto-generated method stub
		_log.info("提交初审");
		String hql = "update OrderVo set salary="+orderVo.getSalary()+",applyMoney="+orderVo.getApplyMoney()+",dateCount="+orderVo.getDateCount()+",firstComment='"+orderVo.getFirstComment()+"',firstFlag='"+orderVo.getFirstFlag()+"',firstComUser.userId="+comUserId+",firstDate='"+Time.getTimeYMDHMS()+"' where orderId="+orderVo.getOrderId();
		orderDao.update(hql);
	}

	@Override
	public void approveFinal(OrderVo orderVo,Long comUserId) {
		// TODO Auto-generated method stub
		_log.info("提交终审");
		String hql = "update OrderVo set salary="+orderVo.getSalary()+",applyMoney="+orderVo.getApplyMoney()+",dateCount="+orderVo.getDateCount()+",approveMoney="+orderVo.getApproveMoney()+",finalCommment='"+orderVo.getFinalCommment()+"',finalFlag='"+orderVo.getFinalFlag()+"',finalComUser.userId="+comUserId+",finalDate='"+Time.getTimeYMDHMS()+"' where orderId="+orderVo.getOrderId();
		orderDao.update(hql);
	}

	@Override
	public void grantFirst(OrderVo orderVo,Long comUserId) {
		// TODO Auto-generated method stub
		_log.info("提交放款");
		String hql = "update OrderVo set grantFlag='Y',overFlag='S',grantMoney="+orderVo.getGrantMoney()+",grantDate='"+Time.updateDateYMD(orderVo.getGrantDate())+"',returnDate='"+Time.updateDateYMD(orderVo.getReturnDate())+"',comment='"+orderVo.getComment()+"',actionComUser.userId="+comUserId+",actionDate='"+Time.getTimeYMDHMS()+"' where orderId="+orderVo.getOrderId();
		orderDao.update(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getApproveFirst(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'S' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取待审核列表="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getApproveFirstRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'S' ";
		hql += getRequestHql(requestPage);
		_log.info("获取待审核列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getApproveFinal(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'Y' and finalFlag = 'S' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取审核中列表="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getApproveFinalRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'Y' and finalFlag = 'S' ";
		hql += getRequestHql(requestPage);
		_log.info("获取审核中列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getGrantFirst(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'S' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取待放款列表="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getGrantFirstRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'S' ";
		hql += getRequestHql(requestPage);
		_log.info("获取待放款列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getGrantFinal(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取已放款列表="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getGrantFinalRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' ";
		hql += getRequestHql(requestPage);
		_log.info("获取已放款列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getReturnApply(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'N' or finalFlag = 'N' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取申请退回列表="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getReturnApplyRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'N' or finalFlag = 'N' ";
		hql += getRequestHql(requestPage);
		_log.info("获取申请退回列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getPlanOverFlag(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'S' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取未还款列表="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getPlanOverFlagRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'S' ";
		hql += getRequestHql(requestPage);
		_log.info("获取未还款列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getNotOverFlag(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'N' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取逾期列表="+hql);
		List list = orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
		if(list != null && !list.isEmpty()) {
			for(int i=0;i<list.size();i++) {
				String Payhql = "from PayVo where orderVo.orderId = ";
				OrderVo orderVo = (OrderVo) list.get(i);
				Payhql += orderVo.getOrderId();
				Payhql += " order by payCount asc";
				orderVo.setPayList(payDao.find(Payhql, -1, -1));
				Date remainReturnDate = orderVo.getRemainReturnDate();
				Date nowDate = new Date();
				if(nowDate.compareTo(remainReturnDate) > 0) {
					orderVo.setActionType("Y");
				}else {
					orderVo.setActionType("N");
				}
			}
		}
		return list;
	}
	@Override
	public int getNotOverFlagRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'N' ";
		hql += getRequestHql(requestPage);
		_log.info("获取逾期列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVo> getOverFlag(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = " from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'Y' ";
		hql += getRequestHql(requestPage)+" order by orderId desc";
		_log.info("获取结清列表个数="+hql);
		return orderDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	@Override
	public int getOverFlagRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(orderId) from OrderVo where firstFlag = 'Y' and finalFlag = 'Y' and grantFlag = 'Y' and overFlag = 'Y' ";
		hql += getRequestHql(requestPage);
		_log.info("获取结清列表个数="+hql);
		return orderDao.getRowCount(hql);
	}
	
	public String getRequestHql(RequestPage requestPage) {
		String queryHql = "";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			String name = (String) requestMap.get("name");
			System.out.println("name="+name);
			if(name != null && !name.equals("")) {
				queryHql += " and name like '%"+name+"%' ";
			}
			String mobileNum = (String) requestMap.get("mobileNum");
			System.out.println("mobileNum="+mobileNum);
			if(mobileNum != null && !mobileNum.equals("")) {
				queryHql += " and mobileNum like '%"+mobileNum+"%' ";
			}
			String applyDate = (String) requestMap.get("applyDate");
			System.out.println("applyDate="+applyDate);
			if(applyDate != null && !applyDate.equals("")) {
				queryHql += " and applyDate = '"+applyDate+"' ";
			}
		}
		return queryHql;
	}
	
	
	
}
