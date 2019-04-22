/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.dbView.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.dbView.service.DbViewService;
import com.sun.dbView.vo.DbViewVo;
import com.sun.system.message.RespMessage;

/**@author SUNCHANGQING
 * @date 2018年6月9日 
 *
 */
@RestController
@RequestMapping(value="/api/dbView")
public class DbViewController {
	@Resource
	private DbViewService dbViewService;
	@RequestMapping(method=RequestMethod.POST)
	public Object getDbView(@RequestBody DbViewVo dbViewVo) {
		RespMessage respMessage = new RespMessage();
		try {
			respMessage.setData(dbViewService.getDbViewVo(dbViewVo));
			respMessage.setErrorCode(200);
		} catch (ParseException e) {
			respMessage.setErrorCode(201);
			respMessage.setSuccess(false);
			respMessage.setErrorMsg("时间格式转换异常");
		}
		return respMessage;
	}
}
