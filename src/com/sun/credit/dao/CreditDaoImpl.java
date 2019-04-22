/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.credit.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.credit.vo.CreditVo;
import com.sun.system.dao.AbstractDaoSupport;
import com.sun.trans.vo.TransVo;

/**@author SUNCHANGQING
 * @date 2018年6月12日 
 *
 */
@Repository
public class CreditDaoImpl extends AbstractDaoSupport implements CreditDao{
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param tableId
	 * @return  
	 * @see com.sun.credit.dao.CreditDao#selectById(java.lang.Long)  
	 */
	@Override
	public CreditVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(CreditVo.class, tableId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.credit.dao.CreditDao#selectAll()  
	 */
	@Override
	public List<CreditVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(CreditVo.class);
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param creditVo  
	 * @see com.sun.credit.dao.CreditDao#insertOne(com.sun.credit.vo.CreditVo)  
	 */
	@Override
	public void insertOne(CreditVo creditVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(creditVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param creditVo  
	 * @see com.sun.credit.dao.CreditDao#updateOne(com.sun.credit.vo.CreditVo)  
	 */
	@Override
	public void updateOne(CreditVo creditVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(creditVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param tableId  
	 * @see com.sun.credit.dao.CreditDao#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long tableId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(tableId));
	}

}
