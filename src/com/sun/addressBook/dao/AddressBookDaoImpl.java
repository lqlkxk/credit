/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.addressBook.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sun.addressBook.vo.AddressBookVo;
import com.sun.system.dao.AbstractDaoSupport;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@Repository
public class AddressBookDaoImpl extends AbstractDaoSupport implements AddressBookDao{
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param tableId
	 * @return  
	 * @see com.sun.addressBook.dao.AddressBookDao#selectById(java.lang.Long)  
	 */
	@Override
	public AddressBookVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(AddressBookVo.class, tableId);
	}
	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param addressBookVo  
	 * @see com.sun.addressBook.dao.AddressBookDao#insertOne(com.sun.addressBook.vo.AddressBookVo)  
	 */
	@Override
	public void insertOne(AddressBookVo addressBookVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(addressBookVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param addressBookVo  
	 * @see com.sun.addressBook.dao.AddressBookDao#updateOne(com.sun.addressBook.vo.AddressBookVo)  
	 */
	@Override
	public void updateOne(AddressBookVo addressBookVo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(addressBookVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param tableId  
	 * @see com.sun.addressBook.dao.AddressBookDao#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long tableId) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().delete(selectById(tableId));
	}

}
