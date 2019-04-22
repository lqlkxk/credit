package com.sun.comuser.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.comuser.vo.ComUserVo;
import com.sun.system.dao.AbstractDaoSupport;
@Repository
public class ComUserDaoImpl extends AbstractDaoSupport implements ComUserDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public ComUserVo selectById(Long userId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(ComUserVo.class,userId);
	}

	@Override
	public List<ComUserVo> selectSome(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(ComUserVo.class);
	}

	@Override
	public void insertOne(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(comUserVo);
	}

	@Override
	public void updateOne(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(comUserVo);
	}

	@Override
	public void deleteById(Long userId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(userId));
	}
	@Override
	public List<ComUserVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(ComUserVo.class);
	}

}
