package com.sun.comuser.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.comuser.service.ComUserService;
import com.sun.comuser.vo.ComUserVo;
import com.sun.system.message.RespMessage;
import com.sun.util.encode.EncodeUtil;
import com.sun.util.properties.SystemProperties;
import com.sun.util.view.RequestPage;

@RestController
@RequestMapping(value="/api/comuser")
public class ComUserController {
	@Resource
	private ComUserService comUserService;
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public Object selectById(@PathVariable("userId")String userId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(comUserService.selectById(Long.valueOf(userId)));
		return respMessage;	
	}
	@RequestMapping(method=RequestMethod.POST)
	public Object saveCustomer(@RequestBody ComUserVo comUserVo){
		RespMessage respMessage = new RespMessage();
		comUserService.insertOne(comUserVo);
		respMessage.setErrorCode(200);
		respMessage.setData(comUserVo);
		return respMessage;
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Object updateById(@RequestBody ComUserVo comUserVo){
		int count = comUserService.update(comUserVo);
		RespMessage respMessage = new RespMessage();
		if(count == 1){
			respMessage.setErrorCode(200);
			respMessage.setData(comUserService.selectById(comUserVo.getUserId()));
		}else{
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("数据修改失败");
		}
		return respMessage;
	}
	@RequestMapping(value="/get",method=RequestMethod.POST)
	public Object selectByWhere(@RequestBody RequestPage requestPage){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(comUserService.selectByWhere(requestPage));
		requestPage.setTotalCount(comUserService.getRowCount(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
	
	@RequestMapping(value="/updatePassword/{userId}",method=RequestMethod.GET)
	public Object updatePassword(@PathVariable("userId")String userId){
		RespMessage respMessage = new RespMessage();
		String password = SystemProperties.get("system.init.password", "123456");
		String s = EncodeUtil.encode(password);
		int count = comUserService.updatePassword(Long.valueOf(userId),s);
		if(count == 1) {
			respMessage.setErrorCode(200);
			respMessage.setData(password);
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(200);
		}
		return respMessage;	
	}
	
	@RequestMapping(value="/empIdCount/{empId}",method=RequestMethod.GET)
	public Object empIdCount(@PathVariable("empId")String empId){
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(comUserService.empIdCount(empId));
		return respMessage;	
	}
}
