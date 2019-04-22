package com.sun.cususer.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.cususer.dao.CusUserDao;
import com.sun.cususer.vo.CusUserPo;
import com.sun.cususer.vo.CusUserVo;
import com.sun.system.time.Time;
import com.sun.util.encode.EncodeUtil;
import com.sun.util.view.RequestPage;
@Service
@Transactional
public class CusUserServiceImpl implements CusUserService {
	public static Log _log = LogFactory.getLog(CusUserServiceImpl.class);
	@Resource
	private CusUserDao cusUserDao;
	@Override
	public CusUserPo selectCusUserPoById(Long cusId) {
		// TODO Auto-generated method stub
		return cusUserDao.selectCusUserPoById(cusId);
	}
	@Override
	public CusUserVo selectById(Long cusId) {
		// TODO Auto-generated method stub
		return cusUserDao.selectById(cusId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CusUserVo> selectByWhere(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from CusUserVo";
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
		queryHql += " order by cusId desc";
		hql += " "+queryHql;
		return cusUserDao.find(hql, requestPage.getPageStart(), requestPage.getRowCount());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(cusId) from CusUserVo";
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
		return cusUserDao.getRowCount(hql);
	}
	@Override
	public List<CusUserVo> selectAll() {
		// TODO Auto-generated method stub
		return cusUserDao.selectAll();
	}

	@Override
	public void insertOne(CusUserVo cusUserVo) {
		// TODO Auto-generated method stub
		cusUserVo.setPassword(EncodeUtil.encode(cusUserVo.getPassword()));
		cusUserDao.insertOne(cusUserVo);
	}

	@Override
	public void updateOne(CusUserVo cusUserVo) {
		// TODO Auto-generated method stub
		cusUserDao.updateOne(cusUserVo);
	}

	@Override
	public void deleteById(Long cusId) {
		// TODO Auto-generated method stub
		cusUserDao.deleteById(cusId);
	}

	@Override
	public int updateWhere(String name,Long cusId) {
		String hql = "update CusUserVo set name = '"+name+"' where cusId="+cusId;
		return cusUserDao.update(hql);
	}

	@Override
	public CusUserVo selectByPhone(String phone) {
		// TODO Auto-generated method stub
		String hql = "from CusUserVo where mobileNumber ='"+phone+"' order by cusId desc";
		List list = cusUserDao.find(hql, -1, -1);
		if(list != null && !list.isEmpty()){
			return (CusUserVo) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int update(CusUserVo cusUserVo) {
		// TODO Auto-generated method stub
		boolean first = true;
		String hql = "update CusUserVo set ";
		if(cusUserVo.getSysId() != null) {
			if(first) {
				hql += " sysId='"+cusUserVo.getSysId()+"' ";
				first = false;
			}else {
				hql += " ,sysId='"+cusUserVo.getSysId()+"' ";
			}
		}
		if(cusUserVo.getName() != null) {
			if(first) {
				hql += " name='"+cusUserVo.getName()+"' ";
				first = false;
			}else {
				hql += " ,name='"+cusUserVo.getName()+"' ";
			}
		}
		if(cusUserVo.getPassword() != null) {
			String password = EncodeUtil.encode(cusUserVo.getPassword());
			if(first) {
				hql += " password='"+password+"' ";
				first = false;
			}else {
				hql += " ,password='"+password+"' ";
			}
		}
		if(cusUserVo.getGender() != null) {
			if(first) {
				hql += " gender='"+cusUserVo.getGender()+"' ";
				first = false;
			}else {
				hql += " ,gender='"+cusUserVo.getGender()+"' ";
			}
		}
		if(cusUserVo.getBirthday() != null) {
			String birthday = Time.updateDateYMD(cusUserVo.getBirthday());
			if(first) {
				hql += " birthday='"+birthday+"' ";
				first = false;
			}else {
				hql += " ,birthday='"+birthday+"' ";
			}
		}
		if(cusUserVo.getIdentityCard() != null) {
			if(first) {
				hql += " identityCard='"+cusUserVo.getIdentityCard()+"' ";
				first = false;
			}else {
				hql += " ,identityCard='"+cusUserVo.getIdentityCard()+"' ";
			}
		}
		if(cusUserVo.getTeleNumber() != null) {
			if(first) {
				hql += " teleNumber='"+cusUserVo.getTeleNumber()+"' ";
				first = false;
			}else {
				hql += " ,teleNumber='"+cusUserVo.getTeleNumber()+"' ";
			}
		}
		if(cusUserVo.getEmail() != null) {
			if(first) {
				hql += " email='"+cusUserVo.getEmail()+"' ";
				first = false;
			}else {
				hql += " ,email='"+cusUserVo.getEmail()+"' ";
			}
		}
		if(cusUserVo.getQqNumber() != null) {
			if(first) {
				hql += " qqNumber='"+cusUserVo.getQqNumber()+"' ";
				first = false;
			}else {
				hql += " ,qqNumber='"+cusUserVo.getQqNumber()+"' ";
			}
		}
		if(cusUserVo.getWechatNumber() != null) {
			if(first) {
				hql += " wechatNumber='"+cusUserVo.getWechatNumber()+"' ";
				first = false;
			}else {
				hql += " ,wechatNumber='"+cusUserVo.getWechatNumber()+"' ";
			}
		}
		if(cusUserVo.getComUserPo() != null) {
			if(first) {
				hql += " comUserVo.userId="+cusUserVo.getComUserPo().getUserId();
				first = false;
			}else {
				hql += " ,comUserVo.userId="+cusUserVo.getComUserPo().getUserId();
			}
		}
		if(cusUserVo.getComment() != null) {
			if(first) {
				hql += " comment='"+cusUserVo.getComment()+"' ";
				first = false;
			}else {
				hql += " ,comment='"+cusUserVo.getComment()+"' ";
			}
		}
		if(cusUserVo.getSesameSeed() != null) {
			if(first) {
				hql += " sesameSeed="+cusUserVo.getSesameSeed();
				first = false;
			}else {
				hql += " ,sesameSeed="+cusUserVo.getSesameSeed();
			}
		}
		if(cusUserVo.getPlace() != null) {
			if(first) {
				hql += " place='"+cusUserVo.getPlace()+"' ";
				first = false;
			}else {
				hql += " ,place='"+cusUserVo.getPlace()+"' ";
			}
		}
		if(cusUserVo.getAddress() != null) {
			if(first) {
				hql += " address='"+cusUserVo.getAddress()+"' ";
				first = false;
			}else {
				hql += " ,address='"+cusUserVo.getAddress()+"' ";
			}
		}
		if(cusUserVo.getPhotoRight() != null) {
			if(first) {
				hql += " photoRight='"+cusUserVo.getPhotoRight()+"' ";
				first = false;
			}else {
				hql += " ,photoRight='"+cusUserVo.getPhotoRight()+"' ";
			}
		}
		if(cusUserVo.getPhotoLeft() != null) {
			if(first) {
				hql += " photoLeft='"+cusUserVo.getPhotoLeft()+"' ";
				first = false;
			}else {
				hql += " ,photoLeft='"+cusUserVo.getPhotoLeft()+"' ";
			}
		}
		if(cusUserVo.getPhoto() != null) {
			if(first) {
				hql += " photo='"+cusUserVo.getPhoto()+"' ";
				first = false;
			}else {
				hql += " ,photo='"+cusUserVo.getPhoto()+"' ";
			}
		}
		hql += " where cusId = "+cusUserVo.getCusId();
		_log.info("执行修改sql="+hql);
		return cusUserDao.update(hql);
	}
	
	public int updateSelfPhoto(String selfPhoto,Long cusId) {
		String hql = "update CusUserVo set selfPhoto = '"+selfPhoto+"' where cusId="+cusId;
		return cusUserDao.update(hql);
	}

	
	

}
