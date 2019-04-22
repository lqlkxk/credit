package com.sun.cususer.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.cususer.vo.CusUserPo;
import com.sun.cususer.vo.CusUserVo;
import com.sun.system.dao.AbstractDaoSupport;
@Repository
public class CusUserDaoImpl extends AbstractDaoSupport implements CusUserDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public CusUserPo selectCusUserPoById(Long cusId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(CusUserPo.class, cusId);
	}
	@Override
	public CusUserVo selectById(Long cusId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(CusUserVo.class, cusId);
	}

	@Override
	public List<CusUserVo> selectSome(CusUserVo cusUserVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CusUserVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(CusUserVo.class);
	}

	@Override
	public void insertOne(CusUserVo cusUserVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(cusUserVo);
	}

	@Override
	public void updateOne(CusUserVo cusUserVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(cusUserVo);
	}

	@Override
	public void deleteById(Long cusId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(cusId));
	}
	

}
