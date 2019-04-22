/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.userAttest.dao.UserAttestDao;
import com.sun.userAttest.vo.UserAttestVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@Service
@Transactional
public class UserAttestServiceImpl implements UserAttestService {
	@Resource
	private UserAttestDao userAttestDao;
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param attestId
	 * @return  
	 * @see com.sun.userAttest.service.UserAttestService#selectById(java.lang.Long)  
	 */
	@Override
	public UserAttestVo selectById(Long attestId) {
		// TODO Auto-generated method stub
		return userAttestDao.selectById(attestId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.userAttest.service.UserAttestService#selectAll()  
	 */
	@Override
	public List<UserAttestVo> selectAll() {
		// TODO Auto-generated method stub
		return userAttestDao.selectAll();
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param userAttestVo  
	 * @see com.sun.userAttest.service.UserAttestService#insertOne(com.sun.userAttest.vo.UserAttestVo)  
	 */
	@Override
	public void insertOne(UserAttestVo userAttestVo) {
		// TODO Auto-generated method stub
		userAttestDao.insertOne(userAttestVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param userAttestVo  
	 * @see com.sun.userAttest.service.UserAttestService#updateOne(com.sun.userAttest.vo.UserAttestVo)  
	 */
	@Override
	public void updateOne(UserAttestVo userAttestVo) {
		// TODO Auto-generated method stub
		userAttestDao.updateOne(userAttestVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param attestId  
	 * @see com.sun.userAttest.service.UserAttestService#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long attestId) {
		// TODO Auto-generated method stub
		userAttestDao.deleteById(attestId);
	}

	/* (non-Javadoc)  
	 * <p>Title: getUserAttest</p>  
	 * <p>Description: </p>  
	 * @param valueOf
	 * @return  
	 * @see com.sun.userAttest.service.UserAttestService#getUserAttest(java.lang.Long)  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserAttestVo> getUserAttest(Long userId) {
		// TODO Auto-generated method stub
		String hql = "from UserAttestVo where cusId = "+userId+" order by attestTypeVo.displayOrder asc";
		return userAttestDao.find(hql,-1,-1);
	}
	
	public int getUserAttestCount(Long userId) {
		// TODO Auto-generated method stub
		String hql = "select count(attestId) from UserAttestVo where cusId = "+userId+" and statusFlag='Y'";
		return userAttestDao.getRowCount(hql);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateWhere</p>  
	 * <p>Description: </p>  
	 * @param cusId
	 * @param typeId
	 * @return  
	 * @see com.sun.userAttest.service.UserAttestService#updateWhere(java.lang.Long, java.lang.Long)  
	 */
	@Override
	public int updateWhere(Long cusId, Long typeId,String messageId) {
		// TODO Auto-generated method stub
		String hql = "update UserAttestVo set messageId ='"+messageId+"',statusFlag='Y' where cusId="+cusId+" and attestTypeVo.typeId="+typeId;
		return userAttestDao.update(hql);
	}
	
	public int updateWhere(Long cusId, Long typeId,String messageId,String message1) {
		// TODO Auto-generated method stub
		String hql = "update UserAttestVo set messageId ='"+messageId+"',statusFlag='Y',message1='"+message1+"' where cusId="+cusId+" and attestTypeVo.typeId="+typeId;
		return userAttestDao.update(hql);
	}

	/* (non-Javadoc)  
	 * <p>Title: getOneUserAttestVo</p>  
	 * <p>Description: </p>  
	 * @param cusId
	 * @param typeId
	 * @return  
	 * @see com.sun.userAttest.service.UserAttestService#getOneUserAttestVo(java.lang.Long, java.lang.Long)  
	 */
	@Override
	public UserAttestVo getOneUserAttestVo(Long cusId, Long typeId) {
		// TODO Auto-generated method stub
		String hql = "from UserAttestVo where statusFlag='Y' and cusId = "+cusId+" and attestTypeVo.typeId="+typeId;
		List list =userAttestDao.find(hql,-1,-1);
		if(list != null && !list.isEmpty()) {
			return (UserAttestVo) list.get(0);
		}else {
			return null;
		}
		
	}

}
