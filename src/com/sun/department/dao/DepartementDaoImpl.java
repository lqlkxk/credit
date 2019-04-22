package com.sun.department.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.department.vo.DepartementVo;
import com.sun.system.dao.AbstractDaoSupport;
@Repository
public class DepartementDaoImpl extends AbstractDaoSupport implements DepartementDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public DepartementVo selectById(Long depaId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(DepartementVo.class, depaId);
	}

	@Override
	public List<DepartementVo> selectSome(DepartementVo departementVo) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(DepartementVo.class);
	}

	@Override
	public void insertOne(DepartementVo departementVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(departementVo);
	}

	@Override
	public void updateOne(DepartementVo departementVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(departementVo);
	}

	@Override
	public void deleteById(Long depaId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(depaId));
	}

}
