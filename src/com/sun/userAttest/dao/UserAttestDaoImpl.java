/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.attestType.vo.AttestTypeVo;
import com.sun.system.dao.AbstractDaoSupport;
import com.sun.userAttest.vo.UserAttestVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@Repository
public class UserAttestDaoImpl extends AbstractDaoSupport implements UserAttestDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param attestId
	 * @return  
	 * @see com.sun.userAttest.dao.UserAttestDao#selectById(java.lang.Long)  
	 */
	@Override
	public UserAttestVo selectById(Long attestId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(UserAttestVo.class, attestId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.userAttest.dao.UserAttestDao#selectAll()  
	 */
	@Override
	public List<UserAttestVo> selectAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().loadAll(UserAttestVo.class);
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param userAttestVo  
	 * @see com.sun.userAttest.dao.UserAttestDao#insertOne(com.sun.userAttest.vo.UserAttestVo)  
	 */
	@Override
	public void insertOne(UserAttestVo userAttestVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(userAttestVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param userAttestVo  
	 * @see com.sun.userAttest.dao.UserAttestDao#updateOne(com.sun.userAttest.vo.UserAttestVo)  
	 */
	@Override
	public void updateOne(UserAttestVo userAttestVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(userAttestVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param attestId  
	 * @see com.sun.userAttest.dao.UserAttestDao#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long attestId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(attestId));
	}

}
