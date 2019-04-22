/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.trans.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.system.dao.AbstractDaoSupport;
import com.sun.trans.vo.TransVo;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@Repository
public class TransDaoImpl extends AbstractDaoSupport implements TransDao{
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param orderId
	 * @return  
	 * @see com.sun.trans.dao.TransDao#selectById(java.lang.Long)  
	 */
	@Override
	public TransVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(TransVo.class, tableId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.trans.dao.TransDao#selectAll()  
	 */
	@Override
	public List<TransVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(TransVo.class);
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param transVo  
	 * @see com.sun.trans.dao.TransDao#insertOne(com.sun.trans.vo.TransVo)  
	 */
	@Override
	public void insertOne(TransVo transVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(transVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param transVo  
	 * @see com.sun.trans.dao.TransDao#updateOne(com.sun.trans.vo.TransVo)  
	 */
	@Override
	public void updateOne(TransVo transVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(transVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param tableId  
	 * @see com.sun.trans.dao.TransDao#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long tableId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(tableId));
	}

}
