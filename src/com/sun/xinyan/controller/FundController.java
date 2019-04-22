/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.xinyan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.system.message.RespMessage;
import com.sun.trans.service.TransService;
import com.sun.trans.vo.TransVo;
import com.sun.userAttest.service.UserAttestService;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.xinyan.vo.FundVo;
import com.xinyan.config.XinyanProperties;
import com.xinyan.rsa.RsaCodingUtil;
import com.xinyan.util.HttpUtils;
import com.xinyan.util.JXMConvertUtil;
import com.xinyan.util.SecurityUtil;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**@author SUNCHANGQING
 * @date 2018年6月4日 
 *
 */
@RestController
@RequestMapping(value="/api/xinyan/fund")
public class FundController {
	public static Log _log = LogFactory.getLog(FundController.class);
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private TransService transService;
	/**
	 * <p>Title: doFundArealistUrl</p>  
	 * <p>Description: 获取支持公积金的城市列表</p>  
	 * @return
	 */
	@RequestMapping(value="/fundArealistUrl",method=RequestMethod.GET)
	public Object doFundArealistUrl() {
		_log.info("获取支持公积金的城市列表");
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.fund.fundArealistUrl", "");
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data").toString());
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取支持公积金的城市列表失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doFundLoginUrl</p>  
	 * <p>Description: 获取公积金登录信息</p>  
	 * @param fundVo
	 * @return
	 */
	@RequestMapping(value="/fundLoginUrl/{areaCode}",method=RequestMethod.GET)
	public Object doFundLoginUrl(@PathVariable("areaCode")String areaCode) {
		_log.info("获取公积金登录信息="+areaCode);
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.fund.fundLoginUrl", "")+areaCode;
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data").toString());
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("获取公积金登录信息失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doFundTaskCreateUrl</p>  
	 * <p>Description: 创建采集订单</p>  
	 * @param arg0
	 * @param fundVo
	 * @return
	 */
	@RequestMapping(value="/fundTaskCreateUrl",method=RequestMethod.POST)
	public Object doFundTaskCreateUrl(HttpServletRequest arg0,@RequestBody FundVo fundVo) {
		String token = arg0.getHeader("token");
		_log.info("法人公积金核查创建采集订单====获取客户基本信息====token="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.fund.fundTaskCreateUrl", "");
		String trans_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String trans_id = XinyanProperties.get("xinyan.trans.id", ""+System.currentTimeMillis())+System.currentTimeMillis();// 商户订单号
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String XmlOrJson = "";
		/** 组装参数  **/
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("member_trans_date", trans_date);
		ArrayData.put("member_trans_id", trans_id);
		ArrayData.put("area_code", fundVo.getArea_code());
		ArrayData.put("account",fundVo.getAccount());
		ArrayData.put("password", fundVo.getPassword());
		ArrayData.put("login_type", fundVo.getLogin_type());
		ArrayData.put("id_card", fundVo.getId_card());
		ArrayData.put("mobile", fundVo.getMobile());
		ArrayData.put("real_name", fundVo.getReal_name());
		
		ArrayData.put("user_id",Long.valueOf(jedis.get(token)));
		ArrayData.put("origin","2");
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		_log.info("法人公积金核查创建采集订单====请求明文:" + XmlOrJson);
		String base64str = null;
		try {
			base64str = SecurityUtil.Base64Encode(XmlOrJson);
			_log.info("base64:"+base64str);
			/** rsa加密 **/
			String pfxpath = XinyanProperties.get("webRoot", "")+ "/key/" + XinyanProperties.get("xinyan.pfx.name", "");// 商户私钥
			_log.info("pfxpath:"+pfxpath);
			File pfxfile = new File(pfxpath);
			if (!pfxfile.exists()) {
				_log.info("法人公积金核查创建采集订单====私钥文件不存在！");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setErrorMsg("私钥文件不存在");
			}else {
				String pfxpwd =XinyanProperties.get("xinyan.pfx.pwd", "");// 私钥密码
				String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
				_log.info("法人公积金核查创建采集订单====加密串:" + data_content);
				/** ============== http 请求==================== **/
				JSONObject object = new JSONObject();
				object.put("member_id", member_id);
				object.put("terminal_id", terminal_id);
				object.put("data_content", data_content);

				String PostString = HttpUtils.doPostObject(url, headers,object);
				Map<String,Object> ArrayDataString = JXMConvertUtil.JsonConvertHashMap(PostString);
				_log.info("ArrayDataString：" + ArrayDataString);
				TransVo transVo = new TransVo();
				transVo.setTransId(trans_id);
				transVo.setTransDate(trans_date);
				transVo.setCusId(Long.valueOf(jedis.get(token)));
				transVo.setTypeId(7L);
				transVo.setTypeName("法人公积金");
				/** ================处理返回结果============= **/
				if (PostString.isEmpty()) {// 判断参数是否为空
					_log.info("法人公积金核查创建采集订单=====返回数据为空");
					respMessage.setSuccess(false);
					respMessage.setErrorCode(201);
					respMessage.setErrorMsg("创建采集订单====返回数据为空");
					transVo.setSuccess("N");
				}else {
					Object tradeNo = ArrayDataString.get("tradeNo");
					_log.info("法人公积金核查创建采集订单======新颜订单号====="+tradeNo);
					userAttestService.updateWhere(Long.valueOf(jedis.get(token)), 7L,tradeNo.toString());
					respMessage.setSuccess(true);
					respMessage.setErrorCode(200);
					respMessage.setData(ArrayDataString);
					transVo.setSuccess("Y");
				}
				transService.insertOne(transVo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_log.error(e);
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("法人公积金核查请求失败:"+e);
		} finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskStatusUrl</p>  
	 * <p>Description: 订单执行状态查询</p>  
	 * @param fundVo
	 * @return
	 */
	@RequestMapping(value="/taskStatusUrl",method=RequestMethod.POST)
	public Object doTaskStatusUrl(@RequestBody FundVo fundVo) {
		_log.info("法人公积金核查订单执行状态查询="+fundVo.getTradeNo());
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.fund.taskStatusUrl", "")+"/"+fundVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		String PostString = HttpUtils.doGet(url, headers,new HashMap<>());
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("法人公积金核查订单执行状态查询失败");
		}
		return respMessage;
	}
	/**
	 * <p>Title: doTaskinputUrl</p>  
	 * <p>Description: 输入图片验证码/短信</p>  
	 * @param fundVo
	 * @return
	 */
	@RequestMapping(value="/taskinputUrl",method=RequestMethod.POST)
	public Object doTaskinputUrl(@RequestBody FundVo fundVo) {
		_log.info("法人公积金核查输入图片验证码/短信="+fundVo.getTradeNo());
		RespMessage respMessage = new RespMessage();
		/** 1、 商户号 **/
		String member_id = XinyanProperties.get("xinyan.member.id", "");
		/**2、终端号 **/
		String terminal_id = XinyanProperties.get("xinyan.terminal.id", "");
		/**3、请求地址 **/
		String url = XinyanProperties.get("xinyan.fund.taskinputUrl", "")+"/"+fundVo.getTradeNo();
		Map<String, String> headers =new HashMap<>();
		headers.put("memberId", member_id);
		headers.put("terminalId",terminal_id);
		JSONObject object = new JSONObject();
		object.put("input", fundVo.getInput());
		String PostString = HttpUtils.doPostObject(url, headers, object);
		if(PostString != null && !PostString.equals("")) {
			JSONObject jsonObject = JSONObject.fromObject(PostString);
			respMessage.setSuccess(true);
			respMessage.setErrorCode(200);
			respMessage.setData(jsonObject.get("data"));
		}else {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setErrorMsg("法人公积金核查输入图片验证码/短信失败");
		}
		return respMessage;
	}
}
