/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.dbView.dao;

import java.math.BigDecimal;

/**@author SUNCHANGQING
 * @date 2018年6月9日 
 *
 */
public interface DbViewDao {
	public BigDecimal getMoney(String sql);
	public int getCount(String sql);
	public BigDecimal[] getMoneys(String sql);
}
