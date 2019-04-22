/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.dbView.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sun.system.dao.AbstractDaoSupport;

/**@author SUNCHANGQING
 * @date 2018年6月9日 
 *
 */
@Repository
public class DbViewDaoImpl extends AbstractDaoSupport implements DbViewDao {
	@Resource
	private void setMySessioFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public BigDecimal getMoney(String sql){
		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		BigDecimal dBigDecimal = new BigDecimal(0.00);
		if(list != null && !list.isEmpty()) {
			Map map = (Map) list.get(0);
			dBigDecimal = new BigDecimal(map.get("money").toString());
		}
		session.close();
        return dBigDecimal ;
	}
	
	public int getCount(String sql){
		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		int counts = 0;
		if(list != null && !list.isEmpty()) {
			Map map = (Map) list.get(0);
			counts = Integer.valueOf(map.get("counts").toString());
		}
		session.close();
        return counts;
	}
	
	public BigDecimal[] getMoneys(String sql){
		Session session = this.getSessionFactory().openSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		BigDecimal[] dBigDecimals = new BigDecimal[2];
		if(list != null && !list.isEmpty()) {
			Map map = (Map) list.get(0);
			dBigDecimals[0] = new BigDecimal(map.get("grantmoney").toString());
			dBigDecimals[1] = new BigDecimal(map.get("paymoney").toString());
		}
		session.close();
        return dBigDecimals ;
	}
}
