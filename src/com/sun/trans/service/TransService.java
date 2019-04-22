/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.trans.service;

import java.util.List;

import com.sun.trans.vo.TransVo;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
public interface TransService {
	public TransVo selectById(Long tableId);
	public List<TransVo> selectAll();
	public void insertOne(TransVo transVo);
	public void updateOne(TransVo transVo);
	public void deleteById(Long tableId);
}
