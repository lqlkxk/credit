package com.sun.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public abstract class AbstractDaoSupport extends HibernateDaoSupport{
	/**
	 * 分页查询
	 * @author SUNCHANGQING
	 * 2018年5月6日上午7:41:57
	 *
	 */
	public List find(String queryString,int start, int maxResults){
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(queryString);
        if ((start != -1) && (maxResults != -1)) {
        	query.setFirstResult(start);
        	query.setMaxResults(maxResults);
        }
        List list = query.list();
        session.close();
        return list;
	}
	/**
	 * 查询数据总条数
	 * @author SUNCHANGQING
	 * 2018年5月6日上午7:42:14
	 *
	 */
	public int getRowCount(String fromAndWhere){
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(fromAndWhere);
		Long count = (Long) query.list().get(0);
		session.close();
	    return count.intValue();
	}
	/**
	 * 条件修改
	 * @author SUNCHANGQING
	 * 2018年5月6日上午7:45:22
	 *
	 */
	public int update(String fromAndWhere){
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(fromAndWhere);
		int cpunt = query.executeUpdate();
		session.close();
		return cpunt;
	}
	
}
