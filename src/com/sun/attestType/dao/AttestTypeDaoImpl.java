/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.attestType.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.attestType.vo.AttestTypeVo;
import com.sun.system.dao.AbstractDaoSupport;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@Repository
public class AttestTypeDaoImpl extends AbstractDaoSupport implements AttestTypeDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param typeId
	 * @return  
	 * @see com.sun.attestType.dao.AttestTypeDao#selectById(java.lang.Long)  
	 */
	@Override
	public AttestTypeVo selectById(Long typeId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(AttestTypeVo.class, typeId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.attestType.dao.AttestTypeDao#selectAll()  
	 */
	@Override
	public List<AttestTypeVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(AttestTypeVo.class);
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param attestTypeVo  
	 * @see com.sun.attestType.dao.AttestTypeDao#insertOne(com.sun.attestType.vo.AttestTypeVo)  
	 */
	@Override
	public void insertOne(AttestTypeVo attestTypeVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(attestTypeVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param attestTypeVo  
	 * @see com.sun.attestType.dao.AttestTypeDao#updateOne(com.sun.attestType.vo.AttestTypeVo)  
	 */
	@Override
	public void updateOne(AttestTypeVo attestTypeVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(attestTypeVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param typeId  
	 * @see com.sun.attestType.dao.AttestTypeDao#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long typeId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(typeId));
	}

}
