package com.sun.util.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.order.vo.OrderVo;
import com.sun.util.properties.SystemProperties;

public class RequestPage implements Serializable{
	private int currentPage = 1;
	private int pageCount;
	private int rowCount = SystemProperties.getInt("system.view.page.rowcount", 10);
	private int totalCount;
	private int pageStart;
	private Map<Object, Object> requestMap = new HashMap<Object, Object>();
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		if(this.rowCount != 0){
			double count = this.totalCount*1.0/this.rowCount;
			this.pageCount = (int) Math.ceil(count);
		}else{
			double count = this.totalCount*1.0/10;
			this.pageCount = (int) Math.ceil(count);
		}
		return this.pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
//		if(this.rowCount != 0){
//			this.pageCount = this.totalCount/this.rowCount;
//		}else{
//			this.pageCount = this.totalCount/10;
//		}
	}
	
	public int getPageStart() {
		this.pageStart = (this.currentPage-1)*this.rowCount;
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public Map<Object, Object> getRequestMap() {
		return requestMap;
	}
	public void setRequestMap(Map<Object, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
}
