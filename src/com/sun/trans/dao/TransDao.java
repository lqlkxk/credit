/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.trans.dao;

import java.util.List;

import com.sun.trans.vo.TransVo;

/**@author SUNCHANGQING
 * @date 2018年6月5日 
 *
 */
public interface TransDao {
	public TransVo selectById(Long tableId);
	public List<TransVo> selectAll();
	public void insertOne(TransVo transVo);
	public void updateOne(TransVo transVo);
	public void deleteById(Long tableId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
