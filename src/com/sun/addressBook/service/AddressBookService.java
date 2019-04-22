/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.addressBook.service;

import java.util.List;

import com.sun.addressBook.vo.AddressBookVo;
import com.sun.util.view.RequestPage;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
public interface AddressBookService {
	public AddressBookVo selectById(Long tableId);
	public void insertOne(AddressBookVo addressBookVo);
	public void updateOne(AddressBookVo addressBookVo);
	public void deleteById(Long tableId);
	public void insertList(Long cusId,List list);
	public List selectBycusId(RequestPage requestPage);
	public int selectCountBycusId(RequestPage requestPage);
}
