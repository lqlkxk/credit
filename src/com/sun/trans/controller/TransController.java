/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.trans.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.trans.service.TransService;

/**@author SUNCHANGQING
 * @date 2018年6月6日 
 *
 */
@RestController
@RequestMapping(value="/api/trans")
public class TransController {
	@Resource
	private TransService transService;
}
