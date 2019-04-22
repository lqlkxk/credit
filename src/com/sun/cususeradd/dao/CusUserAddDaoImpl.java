package com.sun.cususeradd.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.cususeradd.vo.CusUserAddVo;
import com.sun.system.dao.AbstractDaoSupport;
@Repository
public class CusUserAddDaoImpl extends AbstractDaoSupport implements CusUserAddDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public CusUserAddVo selectById(Long cusAddId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(CusUserAddVo.class, cusAddId);
	}

	@Override
	public List<CusUserAddVo> selectSome(CusUserAddVo cusUserAddVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CusUserAddVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(CusUserAddVo.class);
	}

	@Override
	public void insertOne(CusUserAddVo cusUserAddVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(cusUserAddVo);
	}

	@Override
	public void updateOne(CusUserAddVo cusUserAddVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(cusUserAddVo);
	}

	@Override
	public void deleteById(Long cusAddId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(cusAddId));
	}

}
