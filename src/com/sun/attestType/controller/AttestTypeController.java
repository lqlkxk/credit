/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.attestType.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.attestType.service.AttestTypeService;
import com.sun.system.message.RespMessage;
import com.sun.util.view.RequestPage;

/**@author SUNCHANGQING
 * @date 2018年5月20日 
 *
 */
@RestController
@RequestMapping(value="/api/attestType")
public class AttestTypeController {
	@Resource
	private AttestTypeService attestTypeService;
	@RequestMapping(value="/getAll",method=RequestMethod.POST)
	public Object getAll(@RequestBody RequestPage requestPage) {
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(attestTypeService.selectAll());
		requestPage.setRowCount(100);
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
}
