/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.credit.dao;

import java.util.List;

import com.sun.credit.vo.CreditVo;

/**@author SUNCHANGQING
 * @date 2018年6月12日 
 *
 */
public interface CreditDao {
	public CreditVo selectById(Long tableId);
	public List<CreditVo> selectAll();
	public void insertOne(CreditVo creditVo);
	public void updateOne(CreditVo creditVo);
	public void deleteById(Long tableId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
