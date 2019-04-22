/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.dbView.vo;

import java.math.BigDecimal;

/**@author SUNCHANGQING
 * @date 2018年6月9日 
 *
 */
public class DbViewVo {
	private String time;
	
	private BigDecimal grantMoney;
	
	private BigDecimal payMoney;
	
	private BigDecimal overdueMoney;
	
	private BigDecimal overdue;
	
	private String[] times;
	private BigDecimal[] grantMoneys = new BigDecimal[7];
	private BigDecimal[] overdueMoneys = new BigDecimal[7];

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getGrantMoney() {
		return grantMoney;
	}

	public void setGrantMoney(BigDecimal grantMoney) {
		this.grantMoney = grantMoney;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public BigDecimal getOverdueMoney() {
		return overdueMoney;
	}

	public void setOverdueMoney(BigDecimal overdueMoney) {
		this.overdueMoney = overdueMoney;
	}

	public BigDecimal getOverdue() {
		return overdue;
	}

	public void setOverdue(BigDecimal overdue) {
		this.overdue = overdue;
	}

	public String[] getTimes() {
		return times;
	}

	public void setTimes(String[] times) {
		this.times = times;
	}

	public BigDecimal[] getGrantMoneys() {
		return grantMoneys;
	}

	public void setGrantMoneys(BigDecimal[] grantMoneys) {
		this.grantMoneys = grantMoneys;
	}

	public BigDecimal[] getOverdueMoneys() {
		return overdueMoneys;
	}

	public void setOverdueMoneys(BigDecimal[] overdueMoneys) {
		this.overdueMoneys = overdueMoneys;
	}
	
}
