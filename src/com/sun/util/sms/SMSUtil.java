package com.sun.util.sms;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.sun.util.properties.SystemProperties;
/**
 * 发送短信验证码
 * @author SUNCHANGQING
 * 2018年5月8日下午2:41:55
 */
public class SMSUtil {
	private CCPRestSmsSDK restAPI;
	private String templateId;
	public SMSUtil(){
		restAPI = new CCPRestSmsSDK();
		//******************************注释*********************************************
		//*初始化服务器地址和端口                                                       *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		restAPI.init(SystemProperties.get("system.sms.code.url", "app.cloopen.com"), SystemProperties.get("system.sms.code.prot", "8883"));
		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
		restAPI.setAccount(SystemProperties.get("system.sms.code.acount.sid", "0"), SystemProperties.get("system.sms.code.auth.token", "0"));
		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		restAPI.setAppId(SystemProperties.get("system.sms.code.app.id", "0"));
		templateId = SystemProperties.get("system.sms.code.template.id", "1");
	}
	public HashMap<String, Object> sendTemplateSMS(String to,String[] datas){
		//**************************************举例说明***********************************************************************
		//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
		//*********************************************************************************************************************
		return restAPI.sendTemplateSMS(to, templateId, datas);
	}
}
