package com.sun.pay.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sun.comuser.vo.ComUserPo;
import com.sun.cususer.dao.CusUserDao;
import com.sun.order.dao.OrderDao;
import com.sun.order.vo.OrderVo;
import com.sun.pay.dao.PayDao;
import com.sun.pay.vo.PayVo;
import com.sun.system.time.Time;
import com.sun.util.view.RequestPage;
/**
 * @author SUNCHANGQING
 * @date 2018年5月17日 
 *
 */
@Service
@Transactional
public class PayServiceImpl implements PayService {
	@Resource
	private PayDao payDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private CusUserDao cusUserDao;
	@Override
	/**
	 * 根据主键payId查询信息
	 */
	public PayVo selectById(Long payId) {
		// TODO Auto-generated method stub
		return payDao.selectById(payId);
	}
	/**
	 * 查询所用支付信息（不推荐使用）
	 */
	@Override
	public List<PayVo> selectAll() {
		// TODO Auto-generated method stub
		return payDao.selectAll();
	}
	/**
	 * 新增支付信息——基于首次支付
	 */
	@Override
	public void insertOne(PayVo payVo,Long usertId) {
		// TODO Auto-generated method stub
		//获取支付对应的订单信息
		OrderVo orderVo = orderDao.selectById(payVo.getOrderVo().getOrderId());
		String hql = "";
		//判断本次支付是否为结清操作
		if(orderVo.getGrantMoney().compareTo(payVo.getPayMoney()) == 1) {
			payVo.setOverFlag("N");
			hql = "update OrderVo set payMoney="+orderVo.getPayMoney().add(payVo.getPayMoney()).doubleValue()+",overFlag='N',degree="+(orderVo.getCount()+1)+",remainReturnDate='"+Time.updateDateYMD(payVo.getReturnDate())+"' where orderId="+payVo.getOrderVo().getOrderId();
		}else {
			payVo.setOverFlag("Y");
			hql = "update OrderVo set payMoney="+orderVo.getPayMoney().add(payVo.getPayMoney()).doubleValue()+",overFlag='Y',degree="+(orderVo.getCount()+1)+" where orderId="+payVo.getOrderVo().getOrderId();
			//维护客户信息中结清次数
			String hqlString = "update CusUserVo set payCount = payCount+"+1+" where cusId="+orderVo.getCusId();
			cusUserDao.update(hqlString);
		}
		//维护订单中部分支付信息
		orderDao.update(hql);
		ComUserPo comUserPo = new ComUserPo();
		comUserPo.setUserId(usertId);
		payVo.setComUserPo(comUserPo);
		payVo.setActionDate(new Date());
		payVo.setPayCount(orderVo.getCount()+1);
		payVo.setPunishMoney(new BigDecimal(0));
		payVo.setPayNum(Time.getTimeString()+"_"+orderVo.getOrderNum());
		payVo.setRemainMoney(orderVo.getGrantMoney().subtract(payVo.getPayMoney()));
		//新增支付信息
		payDao.insertOne(payVo);
	}
	/**
	 * 新增支付信息——基于首次支付后未结清订单
	 */
	@Override
	public void insertUrgePay(PayVo payVo, Long userId) {
		// TODO Auto-generated method stub
		//获取支付对应的订单信息
		OrderVo orderVo = orderDao.selectById(payVo.getOrderVo().getOrderId());
		//订单当前要还的总金额  = 放款金额 + 累计惩罚金额 
		BigDecimal orderAllMoney = orderVo.getGrantMoney().add(orderVo.getPunishMoney());
		//订单当前要还的剩余金额  = 订单当前要还的总金额 - 累计还款金额 
		BigDecimal orderRemainMoney = orderAllMoney.subtract(orderVo.getPayMoney());
		//订单要还的金额 = 订单当前要还的剩余金额 + 本次惩罚金额
		BigDecimal remainMoney = null;
		//判断本次支付是否有惩罚金额；如果有，按照“订单要还的金额”进行计算；如果买有，按0处理
		if(payVo.getPunishMoney() != null) {
			remainMoney = orderRemainMoney.add(payVo.getPunishMoney());
		}else {
			remainMoney = orderRemainMoney;
			payVo.setPunishMoney(new BigDecimal(0));
		}
		String hql = "";
		//判断本次支付是否为结清操作
		if(remainMoney.compareTo(payVo.getPayMoney()) == 1) {
			payVo.setOverFlag("N");
			hql = "update OrderVo set payMoney="+orderVo.getPayMoney().add(payVo.getPayMoney()).doubleValue()+",punishMoney="+orderVo.getPunishMoney().add(payVo.getPunishMoney()).doubleValue()+",overFlag='N',degree="+(orderVo.getCount()+1)+",remainReturnDate='"+Time.updateDateYMD(payVo.getReturnDate())+"' where orderId="+payVo.getOrderVo().getOrderId();
		}else {
			payVo.setOverFlag("Y");
			hql = "update OrderVo set payMoney="+orderVo.getPayMoney().add(payVo.getPayMoney()).doubleValue()+",punishMoney="+orderVo.getPunishMoney().add(payVo.getPunishMoney()).doubleValue()+",overFlag='Y',degree="+(orderVo.getCount()+1)+" where orderId="+payVo.getOrderVo().getOrderId();
			String hqlString = "update CusUserVo set payCount = payCount+"+1+" where cusId="+orderVo.getCusId();
			//维护客户信息中结清次数
			cusUserDao.update(hqlString);
		}
		//维护订单中部分支付信息
		orderDao.update(hql);
		ComUserPo comUserPo = new ComUserPo();
		comUserPo.setUserId(userId);
		payVo.setComUserPo(comUserPo);
		payVo.setActionDate(new Date());
		payVo.setPayCount(orderVo.getCount()+1);
		payVo.setPayNum(Time.getTimeString()+"_"+orderVo.getOrderNum());
		payVo.setRemainMoney(remainMoney.subtract(payVo.getPayMoney()));
		//新增支付信息
		payDao.insertOne(payVo);
	}
	//提交对象进行修改
	@Override
	public void updateOne(PayVo payVo) {
		// TODO Auto-generated method stub
		payDao.updateOne(payVo);
	}
	//按主键payId删除信息
	@Override
	public void deleteById(Long payId) {
		// TODO Auto-generated method stub
		payDao.deleteById(payId);
	}
	//组合条件查询支付列表
	@SuppressWarnings("unchecked")
	@Override
	public List<PayVo> selectByWhere(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from PayVo";
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
		queryHql += " order by payId desc";
		hql += " "+queryHql;
		return payDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}
	//组合条件查询数据条数
	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(payId) from PayVo";
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
		return payDao.getRowCount(hql);
	}
	//查询特定订单对应的支付列表
	@SuppressWarnings("unchecked")
	@Override
	public List<PayVo> getByOrder(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from PayVo where orderVo.orderId =";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			Long orderId = (Long) requestMap.get("orderId");
			if(orderId != null) {
				hql += orderId;
			}else {
				hql += "0";
			}
			hql += " order by payCount asc";
			return payDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
		}else {
			return null;
		}
	}
	//查询特定订单对应的支付条数
	@Override
	public int getByOrderRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(payId) from PayVo where orderVo.orderId =";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			Long orderId = (Long) requestMap.get("orderId");
			if(orderId != null) {
				hql += orderId;
			}else {
				hql += "0";
			}
			return payDao.getRowCount(hql);
		}else {
			return 0;
		}
		
	}
}
