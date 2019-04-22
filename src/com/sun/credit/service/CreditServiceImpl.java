/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.credit.service;

import java.util.List;

import javax.annotation.Resource;

import org.ietf.jgss.Oid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.credit.dao.CreditDao;
import com.sun.credit.vo.CreditVo;

/**@author SUNCHANGQING
 * @date 2018年6月12日 
 *
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService{
	@Resource
	private CreditDao creditDao;
	/* (non-Javadoc)  
	 * <p>Title: selectById</p>  
	 * <p>Description: </p>  
	 * @param tableId
	 * @return  
	 * @see com.sun.credit.service.CreditService#selectById(java.lang.Long)  
	 */
	@Override
	public CreditVo selectById(Long tableId) {
		// TODO Auto-generated method stub
		return creditDao.selectById(tableId);
	}

	/* (non-Javadoc)  
	 * <p>Title: selectAll</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.sun.credit.service.CreditService#selectAll()  
	 */
	@Override
	public List<CreditVo> selectAll() {
		// TODO Auto-generated method stub
		return creditDao.selectAll();
	}

	/* (non-Javadoc)  
	 * <p>Title: insertOne</p>  
	 * <p>Description: </p>  
	 * @param creditVo  
	 * @see com.sun.credit.service.CreditService#insertOne(com.sun.credit.vo.CreditVo)  
	 */
	@Override
	public void insertOne(CreditVo creditVo) {
		// TODO Auto-generated method stub
		creditDao.insertOne(creditVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: updateOne</p>  
	 * <p>Description: </p>  
	 * @param creditVo  
	 * @see com.sun.credit.service.CreditService#updateOne(com.sun.credit.vo.CreditVo)  
	 */
	@Override
	public void updateOne(CreditVo creditVo) {
		// TODO Auto-generated method stub
		creditDao.updateOne(creditVo);
	}

	/* (non-Javadoc)  
	 * <p>Title: deleteById</p>  
	 * <p>Description: </p>  
	 * @param tableId  
	 * @see com.sun.credit.service.CreditService#deleteById(java.lang.Long)  
	 */
	@Override
	public void deleteById(Long tableId) {
		// TODO Auto-generated method stub
		creditDao.deleteById(tableId);
	}
	
	public CreditVo getCreditVo(CreditVo creditVo) {
		// TODO Auto-generated method stub
		String hql = "from CreditVo where creditType = '"+creditVo.getCreditType()+"' ";
		if(creditVo.getCusId() != null && !"".equals(creditVo.getCusId().toString())) {
			hql += " and cusId = "+creditVo.getCusId();
		}else {
			hql += " and cusId is null ";
			if(creditVo.getPhone_no() != null && !"".equals(creditVo.getPhone_no())) {
				hql += " and phone_no = '"+creditVo.getPhone_no()+"' ";
			}else {
				hql += " and phone_no is null ";
			}
			if(creditVo.getId_name() != null && !"".equals(creditVo.getId_name())) {
				hql += " and id_name = '"+creditVo.getId_name()+"' ";
			}else {
				hql += " and id_name is null ";
			}
			if(creditVo.getId_no() != null && !"".equals(creditVo.getId_no())) {
				hql += " and id_no = '"+creditVo.getId_no()+"' ";
			}else {
				hql += " and id_no is null ";
			}
		}
		hql += "  order by tableId desc ";
		List list = creditDao.find(hql, -1, -1);
		if(list != null && !list.isEmpty()){
			return (CreditVo) list.get(0);
		}else{
			return null;
		}
	}
	
	public void insertCreditVo(CreditVo creditVo) {
		String hql = "from CreditVo where creditType = '"+creditVo.getCreditType()+"' ";
		if(creditVo.getCusId() != null && !"".equals(creditVo.getCusId().toString())) {
			hql += " and cusId = "+creditVo.getCusId();
		}
		if(creditVo.getPhone_no() != null && !"".equals(creditVo.getPhone_no())) {
			hql += " and phone_no = '"+creditVo.getPhone_no()+"' ";
		}
		if(creditVo.getId_name() != null && !"".equals(creditVo.getId_name())) {
			hql += " and id_name = '"+creditVo.getId_name()+"' ";
		}
		if(creditVo.getId_no() != null && !"".equals(creditVo.getId_no())) {
			hql += " and id_no = '"+creditVo.getId_no()+"' ";
		}
		List list = creditDao.find(hql, -1, -1);
		if(list != null && !list.isEmpty()){
			for(int i=0;i<list.size();i++) {
				CreditVo vo = (CreditVo) list.get(i);
				creditDao.deleteById(vo.getTableId());
			}
		}
		creditDao.insertOne(creditVo);
	}

}
