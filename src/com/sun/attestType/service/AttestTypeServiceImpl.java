/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.attestType.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.attestType.dao.AttestTypeDao;
import com.sun.attestType.vo.AttestTypeVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@Service
@Transactional
public class AttestTypeServiceImpl implements AttestTypeService {
	@Resource
	private AttestTypeDao attestTypeDao;
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param typeId
	 * @return  
	 * @see com.sun.attestType.service.AttestTypeService#selectById(java.lang.Long)  
	 */
	@Override
	public AttestTypeVo selectById(Long typeId) {
		// TODO Auto-generated method stub
		return attestTypeDao.selectById(typeId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.attestType.service.AttestTypeService#selectAll()  
	 */
	@Override
	public List<AttestTypeVo> selectAll() {
		// TODO Auto-generated method stub
		return attestTypeDao.selectAll();
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param attestTypeVo  
	 * @see com.sun.attestType.service.AttestTypeService#insertOne(com.sun.attestType.vo.AttestTypeVo)  
	 */
	@Override
	public void insertOne(AttestTypeVo attestTypeVo) {
		// TODO Auto-generated method stub
		attestTypeDao.insertOne(attestTypeVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param attestTypeVo  
	 * @see com.sun.attestType.service.AttestTypeService#updateOne(com.sun.attestType.vo.AttestTypeVo)  
	 */
	@Override
	public void updateOne(AttestTypeVo attestTypeVo) {
		// TODO Auto-generated method stub
		attestTypeDao.updateOne(attestTypeVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param typeId  
	 * @see com.sun.attestType.service.AttestTypeService#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long typeId) {
		// TODO Auto-generated method stub
		attestTypeDao.deleteById(typeId);
	}

}
