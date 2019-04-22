/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.service;

import java.util.List;

import com.sun.userAttest.vo.UserAttestVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
public interface UserAttestService {
	public UserAttestVo selectById(Long attestId);
	public List<UserAttestVo> selectAll();
	public void insertOne(UserAttestVo userAttestVo);
	public void updateOne(UserAttestVo userAttestVo);
	public void deleteById(Long attestId);
	public UserAttestVo getOneUserAttestVo(Long cusId,Long typeId);
	public List<UserAttestVo> getUserAttest(Long userId);
	public int getUserAttestCount(Long userId);
	public int updateWhere(Long cusId,Long typeId,String messageId);
	public int updateWhere(Long cusId, Long typeId,String messageId,String message1);
}
