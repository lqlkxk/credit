/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.userAttest.vo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.attestType.vo.AttestTypeVo;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@Entity
@Table(name="sun_user_attest")
public class UserAttestVo implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="attest_id")
	private Long attestId;
	@Column(name="user_id")
	private Long cusId;
	@ManyToOne(targetEntity=AttestTypeVo.class,cascade={CascadeType.REFRESH})
	@JoinColumn(name="type_id")
	private AttestTypeVo attestTypeVo;
	@Column(name="status_flag")
	private String statusFlag;
	@Column(name="message_id")
	private String  messageId;
	@Column(name="message1")
	private String  message1;
	public Long getAttestId() {
		return attestId;
	}
	public void setAttestId(Long attestId) {
		this.attestId = attestId;
	}
	public Long getCusId() {
		return cusId;
	}
	public void setCusId(Long cusId) {
		this.cusId = cusId;
	}
	public AttestTypeVo getAttestTypeVo() {
		return attestTypeVo;
	}
	public void setAttestTypeVo(AttestTypeVo attestTypeVo) {
		this.attestTypeVo = attestTypeVo;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessage1() {
		return message1;
	}
	public void setMessage1(String message1) {
		this.message1 = message1;
	}
	
}
