package com.sun.comuser.service;

import java.util.List;

import com.sun.comuser.vo.ComUserVo;
import com.sun.util.view.RequestPage;

public interface ComUserService {
	public ComUserVo selectById(Long userId);
	public List<ComUserVo> selectAll();
	public List<ComUserVo> selectSome(ComUserVo comUserVo);
	public void insertOne(ComUserVo comUserVo);
	public void updateOne(ComUserVo comUserVo);
	public void deleteById(Long userId);
	public ComUserVo selectByEmpId(String empId);
	public List<ComUserVo> selectByWhere(RequestPage requestPage);
	public int getRowCount(RequestPage requestPage);
	public int update(ComUserVo comUserVo);
	/** <p>Title: updatePassword</p>  
	 * <p>Description: </p>  
	 * @param valueOf
	 * @param password
	 * @return  
	 */ 
	public int updatePassword(Long userId, String password);
	/** <p>Title: empIdCount</p>  
	 * <p>Description: </p>  
	 * @param empId
	 * @return  
	 */ 
	public Integer empIdCount(String empId);
	/** <p>Title: selectByEmpId</p>  
	 * <p>Description: </p>  
	 * @param empId
	 * @return  
	 */ 
}
