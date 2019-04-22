/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.addressBook.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.eclipse.jdt.internal.compiler.flow.LoopingFlowContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.addressBook.dao.AddressBookDao;
import com.sun.addressBook.vo.AddressBookVo;
import com.sun.util.view.RequestPage;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@Service
@Transactional
public class AddressBookServiceImpl implements AddressBookService{
	@Resource
	private AddressBookDao addressBookDao;
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param tableId
	 * @return  
	 * @see com.sun.addressBook.service.AddressBookService#selectById(java.lang.Long)  
	 */
	@Override
	public AddressBookVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return addressBookDao.selectById(tableId);
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param addressBookVo  
	 * @see com.sun.addressBook.service.AddressBookService#insertOne(com.sun.addressBook.vo.AddressBookVo)  
	 */
	@Override
	public void insertOne(AddressBookVo addressBookVo) {
		// TODO Auto-generated method stub
		addressBookDao.insertOne(addressBookVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param addressBookVo  
	 * @see com.sun.addressBook.service.AddressBookService#updateOne(com.sun.addressBook.vo.AddressBookVo)  
	 */
	@Override
	public void updateOne(AddressBookVo addressBookVo) {
		// TODO Auto-generated method stub
		addressBookDao.updateOne(addressBookVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param tableId  
	 * @see com.sun.addressBook.service.AddressBookService#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long tableId) {
		// TODO Auto-generated method stub
		addressBookDao.deleteById(tableId);
	}
	
	public void insertList(Long cusId,List list) {
		// TODO Auto-generated method stub
		if(list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++) {
				AddressBookVo addressBookVo = (AddressBookVo) list.get(i);
				addressBookVo.setCusId(cusId);
				addressBookDao.insertOne(addressBookVo);
			}
		}
	}

	/* (non-Javadoc)  
	 * <p>Title: selectBycusId</p>  
	 * <p>Description: </p>  
	 * @param cusId
	 * @return  
	 * @see com.sun.addressBook.service.AddressBookService#selectBycusId(java.lang.Long)  
	 */
	@Override
	public List selectBycusId(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from AddressBookVo where cusId = ";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0) {
			hql += requestMap.get("cusId").toString();
		}else {
			hql += "-1";
		}
		hql += " order by tableId asc";
		return addressBookDao.find(hql,requestPage.getPageStart(), requestPage.getRowCount());
	}

	/* (non-Javadoc)  
	 * <p>Title: selectCountBycusId</p>  
	 * <p>Description: </p>  
	 * @param cusId
	 * @param requestPage
	 * @return  
	 * @see com.sun.addressBook.service.AddressBookService#selectCountBycusId(java.lang.Long, com.sun.util.view.RequestPage)  
	 */
	@Override
	public int selectCountBycusId(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(tableId) from AddressBookVo where cusId = ";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0) {
			hql += requestMap.get("cusId").toString();
		}else {
			hql += "-1";
		}
		return addressBookDao.getRowCount(hql);
	}

}
