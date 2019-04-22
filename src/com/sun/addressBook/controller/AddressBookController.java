/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.addressBook.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.addressBook.service.AddressBookService;
import com.sun.addressBook.vo.AddressBookVo;
import com.sun.system.message.RespMessage;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.view.RequestPage;

import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@RestController
@RequestMapping(value="/api/addressBook")
public class AddressBookController {
	@Resource
	private AddressBookService addressBookService;
	@Resource
	private UserAttestService userAttestService;
	
	@RequestMapping(value="/saveBookList",method=RequestMethod.POST)
	public Object saveAddressBookList(HttpServletRequest arg0,@RequestBody List<AddressBookVo> list) {
		String token = arg0.getHeader("token");
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		addressBookService.insertList(Long.valueOf(jedis.get(token)),list);
		userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 8L,"");
		respMessage.setErrorCode(200);
		respMessage.setData(list);
		JedisPoolUtil.returnRes(jedis);
		return respMessage;
	}
	
	@RequestMapping(value="/getBookList",method=RequestMethod.POST)
	public Object getBookList(@RequestBody RequestPage requestPage) {
		RespMessage respMessage = new RespMessage();
		respMessage.setErrorCode(200);
		respMessage.setData(addressBookService.selectBycusId(requestPage));
		requestPage.setTotalCount(addressBookService.selectCountBycusId(requestPage));
		respMessage.setRequestPage(requestPage);
		return respMessage;
	}
}
