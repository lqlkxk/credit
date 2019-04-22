/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.attestType.dao;

import java.util.List;

import com.sun.attestType.vo.AttestTypeVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
public interface AttestTypeDao {
	public AttestTypeVo selectById(Long typeId);
	public List<AttestTypeVo> selectAll();
	public void insertOne(AttestTypeVo attestTypeVo);
	public void updateOne(AttestTypeVo attestTypeVo);
	public void deleteById(Long typeId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
