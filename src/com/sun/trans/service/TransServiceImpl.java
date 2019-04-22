/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.trans.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.trans.dao.TransDao;
import com.sun.trans.vo.TransVo;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@Service
@Transactional
public class TransServiceImpl implements TransService{
	public static Log _log = LogFactory.getLog(TransServiceImpl.class);
	@Resource
	private TransDao transDao;

	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param tableId
	 * @return  
	 * @see com.sun.trans.service.TransService#selectById(java.lang.Long)  
	 */
	@Override
	public TransVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return transDao.selectById(tableId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.trans.service.TransService#selectAll()  
	 */
	@Override
	public List<TransVo> selectAll() {
		// TODO Auto-generated method stub
		return transDao.selectAll();
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param transVo  
	 * @see com.sun.trans.service.TransService#insertOne(com.sun.trans.vo.TransVo)  
	 */
	@Override
	public void insertOne(TransVo transVo) {
		// TODO Auto-generated method stub
		transDao.insertOne(transVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param transVo  
	 * @see com.sun.trans.service.TransService#updateOne(com.sun.trans.vo.TransVo)  
	 */
	@Override
	public void updateOne(TransVo transVo) {
		// TODO Auto-generated method stub
		transDao.updateOne(transVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param tableId  
	 * @see com.sun.trans.service.TransService#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long tableId) {
		// TODO Auto-generated method stub
		transDao.deleteById(tableId);
	}

}
