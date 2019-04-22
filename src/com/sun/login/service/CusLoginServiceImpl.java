package com.sun.login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.sun.attestType.service.AttestTypeService;
import com.sun.attestType.vo.AttestTypeVo;
import com.sun.cususer.service.CusUserService;
import com.sun.cususer.vo.CusUserVo;
import com.sun.login.controller.CusLoginController;
import com.sun.login.vo.LoginVo;
import com.sun.system.time.Time;
import com.sun.userAttest.service.UserAttestService;
import com.sun.userAttest.vo.UserAttestVo;
import com.sun.util.encode.EncodeUtil;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.properties.SystemProperties;
import com.sun.util.sms.SMSUtil;
@Service
public class CusLoginServiceImpl implements CusLoginService {
	public static Log _log = LogFactory.getLog(CusLoginServiceImpl.class);
	@Resource
	private CusUserService cusUserService;
	@Resource
	private UserAttestService userAttestService;
	@Resource
	private AttestTypeService attestTypeService;
	@Override
	public LoginVo doLoginCode(LoginVo loginVo) {
		// TODO Auto-generated method stub
		//从连接池获取jedis
		Jedis jedis = JedisPoolUtil.getJedis();
		//获取已发送的手机验证码
		String sysCodeString = jedis.get("SMS"+loginVo.getPhone());
		if(sysCodeString != null ){
			if(sysCodeString.equals(loginVo.getSmscode())){
				loginVo.setSuccess(true);
				//生成token，加密，并将token和对应的手机号码存储于redis中，并设置过期时间
				String token = EncodeUtil.encode("cusPhone+"+loginVo.getPhone()+Time.getTimeString());
				if(token.equals("null")){
					loginVo.setSuccess(false);
					loginVo.setMessage("TOKEN生成失败");
				}else{
					CusUserVo cusUserVo = cusUserService.selectByPhone(loginVo.getPhone());
					
					if(cusUserVo != null){
						_log.info("用户登录="+loginVo.getPhone());
						jedis.setex(token,SystemProperties.getInt("redis.time.token.phone", 36000), cusUserVo.getCusId()+"");
						_log.info("token="+token+",CusId="+cusUserVo.getCusId());
					}else{
						_log.info("创建新用户="+loginVo.getPhone());
						CusUserVo newCusUserVo = new CusUserVo();
						newCusUserVo.setMobileNumber(loginVo.getPhone());
						newCusUserVo .setPassword(loginVo.getPhone());
						newCusUserVo.setCreateDate(new Date());
						newCusUserVo.setStatus("Y");
						newCusUserVo.setPayCount(0L);
						cusUserService.insertOne(newCusUserVo);
						jedis.setex(token,SystemProperties.getInt("redis.time.token.phone", 36000), newCusUserVo.getCusId()+"");
						_log.info("token="+token+",CusId="+newCusUserVo.getCusId());
						addUserAttest(newCusUserVo.getCusId());
					}
					loginVo.setToken(token);
					loginVo.setMessage("登录成功");
				}
			}else{
				loginVo.setSuccess(false);
				loginVo.setMessage("短信验证码错误");
			}
		}else{
			loginVo.setSuccess(false);
			loginVo.setMessage("短信验证码已过期");
		}
		//将jedis返还到连接池中
		JedisPoolUtil.returnRes(jedis);
		_log.info(loginVo.getMessage());
		return loginVo;
	}

	@Override
	public LoginVo doLoginPass(String name, String pass, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object smsCode(String phone) {
		// TODO Auto-generated method stub
		Jedis jedis = JedisPoolUtil.getJedis();
		String codeSMS = getRandom();
		int time = SystemProperties.getInt("redis.time.code.phone",600);
		SMSUtil smsUtil = new SMSUtil();
		HashMap<String, Object> result = smsUtil.sendTemplateSMS(phone, new String[]{codeSMS,time/60+""});
		_log.info("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			jedis.setex("SMS"+phone,time,codeSMS);
			JedisPoolUtil.returnRes(jedis);
			return "000000";
		}else{
			JedisPoolUtil.returnRes(jedis);
			return result.get("statusMsg");
		}
	}
	
	public String getRandom(){
		Random random = new Random();
		String code = "";
		int codeLength = SystemProperties.getInt("system.sms.code.length", 6);
		for(int i=0;i<codeLength;i++){
			code += random.nextInt(10);
		}
		return code;
	}
	
	public void addUserAttest(Long usetId) {
		List<AttestTypeVo> list = attestTypeService.selectAll();
		if(list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++) {
				UserAttestVo userAttestVo= new UserAttestVo();
				userAttestVo.setAttestTypeVo(list.get(i));
				userAttestVo.setCusId(usetId);
				userAttestVo.setStatusFlag("N");
				userAttestService.insertOne(userAttestVo);
			}
		}
	}
	
}
