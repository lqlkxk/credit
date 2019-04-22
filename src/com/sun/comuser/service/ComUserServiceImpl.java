package com.sun.comuser.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.comuser.dao.ComUserDao;
import com.sun.comuser.vo.ComUserVo;
import com.sun.system.time.Time;
import com.sun.util.encode.EncodeUtil;
import com.sun.util.properties.SystemProperties;
import com.sun.util.view.RequestPage;
@Service
@Transactional
public class ComUserServiceImpl implements ComUserService {
	@Resource
	private ComUserDao comUserDao;
	@Override
	public ComUserVo selectById(Long userId) {
		// TODO Auto-generated method stub
		return comUserDao.selectById(userId);
	}

	@Override
	public List<ComUserVo> selectSome(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		return comUserDao.selectSome(comUserVo);
	}

	@Override
	public void insertOne(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		if(comUserVo.getPassword() == null || "".equals(comUserVo.getPassword())) {
			comUserVo.setPassword(EncodeUtil.encode(SystemProperties.get("system.init.password", "123456")));
		}
		String password = comUserVo.getPassword();
		comUserVo.setPassword(EncodeUtil.encode(password));
		comUserVo.setStatus("Y");
		comUserDao.insertOne(comUserVo);
	}

	@Override
	public void updateOne(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		comUserDao.updateOne(comUserVo);
	}

	@Override
	public void deleteById(Long userId) {
		// TODO Auto-generated method stub
		comUserDao.deleteById(userId);
	}

	@Override
	public List<ComUserVo> selectAll() {
		// TODO Auto-generated method stub
		return comUserDao.selectAll();
	}

	@Override
	public ComUserVo selectByEmpId(String empId) {
		// TODO Auto-generated method stub
		String hql = "from ComUserVo where empId = '"+empId+"' and status='Y' order by userId desc";
		List list = comUserDao.find(hql, -1, -1);
		if(list != null && !list.isEmpty()){
			return (ComUserVo) list.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComUserVo> selectByWhere(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "from ComUserVo";
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
		queryHql += " order by userId desc";
		hql += " "+queryHql;
		return comUserDao.find(hql, requestPage.getPageStart(), requestPage.getRowCount());
	}

	@Override
	public int getRowCount(RequestPage requestPage) {
		// TODO Auto-generated method stub
		String hql = "select count(userId) from ComUserVo";
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
		return comUserDao.getRowCount(hql);
	}

	@Override
	public int update(ComUserVo comUserVo) {
		// TODO Auto-generated method stub
		boolean first = true;
		String hql = "update ComUserVo set ";
		if(comUserVo.getEmpId() != null) {
			if(first) {
				hql += " empId='"+comUserVo.getEmpId()+"' ";
				first = false;
			}else {
				hql += " ,empId='"+comUserVo.getEmpId()+"' ";
			}
		}
		if(comUserVo.getName() != null) {
			if(first) {
				hql += " name='"+comUserVo.getName()+"' ";
				first = false;
			}else {
				hql += " ,name='"+comUserVo.getName()+"' ";
			}
		}
		if(comUserVo.getPassword() != null) {
			String password = EncodeUtil.encode(comUserVo.getPassword());
			if(first) {
				hql += " password='"+password+"' ";
				first = false;
			}else {
				hql += " ,password='"+password+"' ";
			}
		}
		if(comUserVo.getGender() != null) {
			if(first) {
				hql += " gender='"+comUserVo.getGender()+"' ";
				first = false;
			}else {
				hql += " ,gender='"+comUserVo.getGender()+"' ";
			}
		}
		if(comUserVo.getBirthday() != null) {
			String birthday = Time.updateDateYMD(comUserVo.getBirthday());
			if(first) {
				hql += " birthday='"+birthday+"' ";
				first = false;
			}else {
				hql += " ,birthday='"+birthday+"' ";
			}
		}
		if(comUserVo.getPhone() != null) {
			if(first) {
				hql += " phone='"+comUserVo.getPhone()+"' ";
				first = false;
			}else {
				hql += " ,phone='"+comUserVo.getPhone()+"' ";
			}
		}
		if(comUserVo.getTelePhone() != null) {
			if(first) {
				hql += " telePhone='"+comUserVo.getTelePhone()+"' ";
				first = false;
			}else {
				hql += " ,telePhone='"+comUserVo.getTelePhone()+"' ";
			}
		}
		if(comUserVo.getEmail() != null) {
			if(first) {
				hql += " email='"+comUserVo.getEmail()+"' ";
				first = false;
			}else {
				hql += " ,email='"+comUserVo.getEmail()+"' ";
			}
		}
		if(comUserVo.getQq() != null) {
			if(first) {
				hql += " qq='"+comUserVo.getQq()+"' ";
				first = false;
			}else {
				hql += " ,qq='"+comUserVo.getQq()+"' ";
			}
		}
		if(comUserVo.getWechat() != null) {
			if(first) {
				hql += " wechat='"+comUserVo.getWechat()+"' ";
				first = false;
			}else {
				hql += " ,wechat='"+comUserVo.getWechat()+"' ";
			}
		}
		if(comUserVo.getDepa1() != null) {
			if(first) {
				hql += " depa1.depaId="+comUserVo.getDepa1().getDepaId()+" ";
				first = false;
			}else {
				hql += " ,depa1.depaId="+comUserVo.getDepa1().getDepaId()+" ";
			}
		}
		if(comUserVo.getDepa2() != null) {
			if(first) {
				hql += " depa2.depaId="+comUserVo.getDepa2().getDepaId()+" ";
				first = false;
			}else {
				hql += " ,depa2.depaId="+comUserVo.getDepa2().getDepaId()+" ";
			}
		}
		if(comUserVo.getDepa3() != null) {
			if(first) {
				hql += " depa3.depaId="+comUserVo.getDepa3().getDepaId()+" ";
				first = false;
			}else {
				hql += " ,depa3.depaId="+comUserVo.getDepa3().getDepaId()+" ";
			}
		}
		if(comUserVo.getStatus() != null) {
			if(first) {
				hql += " status='"+comUserVo.getStatus()+"' ";
				first = false;
			}else {
				hql += " ,status='"+comUserVo.getStatus()+"' ";
			}
		}
		hql += " where userId = "+comUserVo.getUserId();
		return comUserDao.update(hql);
	}

	/* (non-Javadoc)  
	 * <p>Title: updatePassword</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param password
	 * @return  
	 * @see com.sun.comuser.service.ComUserService#updatePassword(java.lang.Long, java.lang.String)  
	 */
	@Override
	public int updatePassword(Long userId, String password) {
		// TODO Auto-generated method stub
		String hql = "update ComUserVo set password='"+password+"' where userId = "+userId;
		return comUserDao.update(hql);
	}

	/* (non-Javadoc)  
	 * <p>Title: empIdCount</p>  
	 * <p>Description: </p>  
	 * @param empId
	 * @return  
	 * @see com.sun.comuser.service.ComUserService#empIdCount(java.lang.String)  
	 */
	@Override
	public Integer empIdCount(String empId) {
		// TODO Auto-generated method stub
		String hql = "from ComUserVo where empId = '"+empId+"' order by userId desc";
		List list = comUserDao.find(hql, -1, -1);
		if(list != null && !list.isEmpty()){
			return list.size();
		}else{
			return 0;
		}
	}

}
