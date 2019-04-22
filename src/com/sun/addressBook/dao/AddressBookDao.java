/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.addressBook.dao;

import java.util.List;

import com.sun.addressBook.vo.AddressBookVo;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
public interface AddressBookDao {
	public AddressBookVo selectById(Long tableId);
	public void insertOne(AddressBookVo addressBookVo);
	public void updateOne(AddressBookVo addressBookVo);
	public void deleteById(Long tableId);
	public List find(String queryString, int start, int maxResults);
	public int getRowCount(String fromAndWhere);
	public int update(String fromAndWhere);
}
