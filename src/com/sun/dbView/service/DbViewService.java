/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.dbView.service;

import java.text.ParseException;

import com.sun.dbView.vo.DbViewVo;

/**@author SUNCHANGQING
 * @date 2018年6月9日 
 *
 */
public interface DbViewService {
	public DbViewVo getDbViewVo(DbViewVo dbViewVo) throws ParseException;
}
