package com.sun.pay.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.pay.vo.PayVo;
import com.sun.system.dao.AbstractDaoSupport;
@Repository
public class PayDaoImpl extends AbstractDaoSupport implements PayDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public PayVo selectById(Long payId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(PayVo.class, payId);
	}

	@Override
	public List<PayVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(PayVo.class);
	}

	@Override
	public void insertOne(PayVo payVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(payVo);
	}

	@Override
	public void updateOne(PayVo payVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(payVo);
	}

	@Override
	public void deleteById(Long payId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(payId));
	}

}
