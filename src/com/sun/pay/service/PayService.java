package com.sun.pay.service;

import java.util.List;
import com.sun.pay.vo.PayVo;
import com.sun.util.view.RequestPage;

public interface PayService {
	public PayVo selectById(Long payId);
	public List<PayVo> selectAll();
	public void insertOne(PayVo payVo,Long userId);
	public void updateOne(PayVo payVo);
	public void deleteById(Long payId);
	public List<PayVo> selectByWhere(RequestPage requestPage);
	public int getRowCount(RequestPage requestPage);
	public void insertUrgePay(PayVo payVo, Long userId);
	public List<PayVo> getByOrder(RequestPage requestPage);
	public int getByOrderRowCount(RequestPage requestPage);
}
