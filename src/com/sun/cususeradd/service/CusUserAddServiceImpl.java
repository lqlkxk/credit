package com.sun.cususeradd.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.cususeradd.dao.CusUserAddDao;
import com.sun.cususeradd.vo.CusUserAddVo;
import com.sun.util.view.RequestPage;
@Service
@Transactional
public class CusUserAddServiceImpl implements CusUserAddService {
	@Resource
	private CusUserAddDao cusUserAddDao;
	@Override
	public CusUserAddVo selectById(Long cusAddId) {
		// TODO Auto-generated method stub
		return cusUserAddDao.selectById(cusAddId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CusUserAddVo> selectByWhere(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from CusUserAddVo";
		String queryHql = "";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			Iterator iter = requestMap.entrySet().iterator();
			boolean first = true;
			Entry<Object, Object> entry = null;
			Object key = null;
			Object val = null;
			while(iter.hasNext()){
				entry = (Entry<Object, Object>) iter.next();
				key = entry.getKey();
				val = entry.getValue();
				if(val != null && !val.equals("")){
					if(first){
						queryHql += " where "+key+" like '%"+val+"%'";
						first = false;
					}else{
						queryHql += " and "+key+" like '%"+val+"%'";
					}
				}
			}
		}
		queryHql += " order by cusAddId desc";
		hql += " "+queryHql;
		return cusUserAddDao.find(hql, requestPage.getPageStart(), requestPage.getRowCount());
	}

	@Override
	public List<CusUserAddVo> selectAll() {
		// TODO Auto-generated method stub
		return cusUserAddDao.selectAll();
	}

	@Override
	public void insertOne(CusUserAddVo cusUserAddVo) {
		// TODO Auto-generated method stub
		cusUserAddDao.insertOne(cusUserAddVo);
	}

	@Override
	public void updateOne(CusUserAddVo cusUserAddVo) {
		// TODO Auto-generated method stub
		cusUserAddDao.updateOne(cusUserAddVo);
	}

	@Override
	public void deleteById(Long cusAddId) {
		// TODO Auto-generated method stub
		cusUserAddDao.deleteById(cusAddId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(cusAddId) from CusUserAddVo";
		String queryHql = "";
		Map<Object, Object> requestMap = requestPage.getRequestMap();
		if(requestMap != null && requestMap.size() > 0){
			Iterator iter = requestMap.entrySet().iterator();
			boolean first = true;
			Entry<Object, Object> entry = null;
			Object key = null;
			Object val = null;
			while(iter.hasNext()){
				entry = (Entry<Object, Object>) iter.next();
				key = entry.getKey();
				val = entry.getValue();
				if(val != null && !val.equals("")){
					if(first){
						queryHql += " where "+key+" like '%"+val+"%'";
						first = false;
					}else{
						queryHql += " and "+key+" like '%"+val+"%'";
					}
				}
			}
		}
		hql += " "+queryHql;
		return cusUserAddDao.getRowCount(hql);
	}

	@Override
	public CusUserAddVo selectByCusId(Long cusId) {
		// TODO Auto-generated method stub
		String hql = "from CusUserAddVo where cusUserVo.cusId = "+cusId;
		List list = cusUserAddDao.find(hql,-1,-1);
		if(list != null && !list.isEmpty()){
			return (CusUserAddVo) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int update(CusUserAddVo cusUserAddVo) {
		// TODO Auto-generated method stub
		boolean first = true;
		String hql = "update CusUserAddVo set ";
		if(cusUserAddVo.getCusUserVo() != null) {
			if(first) {
				hql += " cusUserVo.cusId="+cusUserAddVo.getCusUserVo().getCusId()+" ";
				first = false;
			}else {
				hql += " ,cusUserVo.cusId="+cusUserAddVo.getCusUserVo().getCusId()+" ";
			}
		}
		if(cusUserAddVo.getSalary() != null) {
			if(first) {
				hql += " salary="+cusUserAddVo.getSalary()+" ";
				first = false;
			}else {
				hql += " ,salary="+cusUserAddVo.getSalary()+" ";
			}
		}
		if(cusUserAddVo.getDebt() != null) {
			if(first) {
				hql += " debt="+cusUserAddVo.getDebt()+" ";
				first = false;
			}else {
				hql += " ,debt="+cusUserAddVo.getDebt()+" ";
			}
		}
		if(cusUserAddVo.getCompanyName() != null) {
			if(first) {
				hql += " companyName='"+cusUserAddVo.getCompanyName()+"' ";
				first = false;
			}else {
				hql += " ,companyName='"+cusUserAddVo.getCompanyName()+"' ";
			}
		}
		if(cusUserAddVo.getCompanyPost() != null) {
			if(first) {
				hql += " companyPost='"+cusUserAddVo.getCompanyPost()+"' ";
				first = false;
			}else {
				hql += " ,companyPost='"+cusUserAddVo.getCompanyPost()+"' ";
			}
		}
		if(cusUserAddVo.getCompanyTele() != null) {
			if(first) {
				hql += " companyTele='"+cusUserAddVo.getCompanyTele()+"' ";
				first = false;
			}else {
				hql += " ,companyTele='"+cusUserAddVo.getCompanyTele()+"' ";
			}
		}
		if(cusUserAddVo.getCompanyPlace() != null) {
			if(first) {
				hql += " companyPlace='"+cusUserAddVo.getCompanyPlace()+"' ";
				first = false;
			}else {
				hql += " ,companyPlace='"+cusUserAddVo.getCompanyPlace()+"' ";
			}
		}
		if(cusUserAddVo.getCompanyAddress() != null) {
			if(first) {
				hql += " companyAddress='"+cusUserAddVo.getCompanyAddress()+"' ";
				first = false;
			}else {
				hql += " ,companyAddress='"+cusUserAddVo.getCompanyAddress()+"' ";
			}
		}
		if(cusUserAddVo.getContact1Name() != null) {
			if(first) {
				hql += " contact1Name='"+cusUserAddVo.getContact1Name()+"' ";
				first = false;
			}else {
				hql += " ,contact1Name='"+cusUserAddVo.getContact1Name()+"' ";
			}
		}
		if(cusUserAddVo.getContact1Mobile() != null) {
			if(first) {
				hql += " contact1Mobile='"+cusUserAddVo.getContact1Mobile()+"' ";
				first = false;
			}else {
				hql += " ,contact1Mobile='"+cusUserAddVo.getContact1Mobile()+"' ";
			}
		}
		if(cusUserAddVo.getContact2Name() != null) {
			if(first) {
				hql += " contact2Name='"+cusUserAddVo.getContact2Name()+"' ";
				first = false;
			}else {
				hql += " ,contact2Name='"+cusUserAddVo.getContact2Name()+"' ";
			}
		}
		if(cusUserAddVo.getContact2Mobile() != null) {
			if(first) {
				hql += " contact2Mobile='"+cusUserAddVo.getContact2Mobile()+"' ";
				first = false;
			}else {
				hql += " ,contact2Mobile='"+cusUserAddVo.getContact2Mobile()+"' ";
			}
		}
		if(cusUserAddVo.getReserveMobile() != null) {
			if(first) {
				hql += " reserveMobile='"+cusUserAddVo.getReserveMobile()+"' ";
				first = false;
			}else {
				hql += " ,reserveMobile='"+cusUserAddVo.getReserveMobile()+"' ";
			}
		}
		if(cusUserAddVo.getWifeName() != null) {
			if(first) {
				hql += " wifeName='"+cusUserAddVo.getWifeName()+"' ";
				first = false;
			}else {
				hql += " ,wifeName='"+cusUserAddVo.getWifeName()+"' ";
			}
		}
		if(cusUserAddVo.getWifeTel() != null) {
			if(first) {
				hql += " wifeTel='"+cusUserAddVo.getWifeTel()+"' ";
				first = false;
			}else {
				hql += " ,wifeTel='"+cusUserAddVo.getWifeTel()+"' ";
			}
		}
		if(cusUserAddVo.getFriendName() != null) {
			if(first) {
				hql += " friendName='"+cusUserAddVo.getFriendName()+"' ";
				first = false;
			}else {
				hql += " ,friendName='"+cusUserAddVo.getFriendName()+"' ";
			}
		}
		if(cusUserAddVo.getFriendTel() != null) {
			if(first) {
				hql += " friendTel='"+cusUserAddVo.getFriendTel()+"' ";
				first = false;
			}else {
				hql += " ,friendTel='"+cusUserAddVo.getFriendTel()+"' ";
			}
		}
		if(cusUserAddVo.getCollegueName() != null) {
			if(first) {
				hql += " collegueName='"+cusUserAddVo.getCollegueName()+"' ";
				first = false;
			}else {
				hql += " ,collegueName='"+cusUserAddVo.getCollegueName()+"' ";
			}
		}
		if(cusUserAddVo.getCollegueTel() != null) {
			if(first) {
				hql += " collegueTel='"+cusUserAddVo.getCollegueTel()+"' ";
				first = false;
			}else {
				hql += " ,collegueTel='"+cusUserAddVo.getCollegueTel()+"' ";
			}
		}
		if(cusUserAddVo.getAsset() != null) {
			if(first) {
				hql += " asset='"+cusUserAddVo.getAsset()+"' ";
				first = false;
			}else {
				hql += " ,asset='"+cusUserAddVo.getAsset()+"' ";
			}
		}
		if(cusUserAddVo.getInsurance() != null) {
			if(first) {
				hql += " insurance='"+cusUserAddVo.getInsurance()+"' ";
				first = false;
			}else {
				hql += " ,insurance='"+cusUserAddVo.getInsurance()+"' ";
			}
		}
		if(cusUserAddVo.getMobileType()!= null) {
			if(first) {
				hql += " mobileType='"+cusUserAddVo.getMobileType()+"' ";
				first = false;
			}else {
				hql += " ,mobileType='"+cusUserAddVo.getMobileType()+"' ";
			}
		}
		hql += " where cusAddId = "+cusUserAddVo.getCusAddId();
		return cusUserAddDao.update(hql);
	}
	

}
