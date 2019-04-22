/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.credit.service;

import java.util.List;

import com.sun.credit.vo.CreditVo;

/**@author SUNCHANGQING
 * @date 2018年6月12日 
 *
 */
public interface CreditService {
	public CreditVo selectById(Long tableId);
	public List<CreditVo> selectAll();
	public void insertOne(CreditVo creditVo);
	public void updateOne(CreditVo creditVo);
	public void deleteById(Long tableId);
	public CreditVo getCreditVo(CreditVo creditVo);
	public void insertCreditVo(CreditVo creditVo);
}
