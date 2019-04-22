package com.sun.order.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.order.vo.OrderVo;
import com.sun.system.dao.AbstractDaoSupport;
@Repository
public class OrderDaoImpl extends AbstractDaoSupport implements OrderDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public OrderVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(OrderVo.class, tableId);
	}

	@Override
	public List<OrderVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(OrderVo.class);
	}

	@Override
	public void insertOne(OrderVo orderVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(orderVo);
	}

	@Override
	public void updateOne(OrderVo orderVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(orderVo);
	}

	@Override
	public void deleteById(Long orderId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(orderId));
	}

}
