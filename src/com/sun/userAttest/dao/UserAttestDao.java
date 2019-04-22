/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.dao;

import java.util.List;

import com.sun.userAttest.vo.UserAttestVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
public interface UserAttestDao {
	public UserAttestVo selectById(Long attestId);
	public List<UserAttestVo> selectAll();
	public void insertOne(UserAttestVo userAttestVo);
	public void updateOne(UserAttestVo userAttestVo);
	public void deleteById(Long attestId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
