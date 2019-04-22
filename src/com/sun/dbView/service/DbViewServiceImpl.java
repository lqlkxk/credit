/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.dbView.service;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.dbView.dao.DbViewDao;
import com.sun.dbView.vo.DbViewVo;
import com.sun.system.time.Time;

/**@author SUNCHANGQING
 * @date 2018年6月9日 
 *
 */
@Service
@Transactional
public class DbViewServiceImpl implements DbViewService{
	public static Log _log = LogFactory.getLog(DbViewServiceImpl.class);
	@Resource
	private DbViewDao dbViewDao;
	/* (non-Javadoc)  
	 * <p>Title: getDbViewVo</p>  
	 * <p>Description: </p>  
	 * @param dbViewVo
	 * @return  
	 * @see com.sun.dbView.service.DbViewService#getDbViewVo(com.sun.dbView.vo.DbViewVo)  
	 */
	@Override
	public DbViewVo getDbViewVo(DbViewVo dbViewVo) throws ParseException {
		// TODO Auto-generated method stub
		String time = "";
		if(dbViewVo.getTime() != null && !time.equals(dbViewVo.getTime())) {
			time = dbViewVo.getTime();
		}else {
			time = Time.getTimeYMD();
		}
		String[] times = Time.getWeek(time);
		dbViewVo.setTimes(times);
		String getGrantMoney = "select IFNULL(sum(so.grant_money),0) money from sun_order so where so.grant_date between '"+times[0]+"' and '"+times[6]+"'";
		BigDecimal grantMoney = dbViewDao.getMoney(getGrantMoney);
		dbViewVo.setGrantMoney(grantMoney);
		String getPayMoney = "select IFNULL(sum(so.pay_money),0) money from sun_order so where so.return_date between '"+times[0]+"' and '"+times[6]+"'";
		BigDecimal payMoney = dbViewDao.getMoney(getPayMoney);
		dbViewVo.setPayMoney(payMoney);
		String getPlanPayMoney = "select IFNULL(sum(so.grant_money),0) money from sun_order so where so.return_date between '"+times[0]+"' and '"+times[6]+"'";
		BigDecimal planPayMoney = dbViewDao.getMoney(getPlanPayMoney);
		dbViewVo.setOverdueMoney(planPayMoney.subtract(payMoney));
		String getPayCount = "select count(*) counts from sun_order so where so.return_date between '"+times[0]+"' and '"+times[6]+"'";
		int payCount = dbViewDao.getCount(getPayCount);
		String getPayNotOverCount = "select count(*) counts from sun_order so where so.over_flag = 'N' and so.return_date between '"+times[0]+"' and '"+times[6]+"'";
		int payNotOverCount = dbViewDao.getCount(getPayNotOverCount);
		if(payCount != 0) {
			BigDecimal bigDecimal = new BigDecimal(payNotOverCount*100.0/payCount);
			dbViewVo.setOverdue(bigDecimal.setScale(0));
		}else {
			dbViewVo.setOverdue(null);
		}
		for(int i=0;i<times.length;i++) {
			String getMoney0 = "select IFNULL(sum(so.grant_money),0) grantmoney,IFNULL(sum(so.grant_money),0) paymoney from sun_order so where so.grant_date = '"+times[i]+"'";
			BigDecimal[] bigDecimals0 = dbViewDao.getMoneys(getMoney0);
			dbViewVo.getGrantMoneys()[i] = bigDecimals0[0];		
			String getMoney = "select IFNULL(sum(so.grant_money),0) grantmoney,IFNULL(sum(so.pay_money),0) paymoney from sun_order so where so.return_date = '"+times[i]+"'";
			BigDecimal[] bigDecimals = dbViewDao.getMoneys(getMoney);
			dbViewVo.getOverdueMoneys()[i] = bigDecimals[0].subtract(bigDecimals[1]);
		}
		return dbViewVo;
	}

}
