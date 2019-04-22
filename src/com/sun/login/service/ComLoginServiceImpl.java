package com.sun.login.service;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.sun.comuser.dao.ComUserDao;
import com.sun.comuser.service.ComUserService;
import com.sun.comuser.vo.ComUserVo;
import com.sun.login.vo.LoginVo;
import com.sun.system.time.Time;
import com.sun.util.encode.EncodeUtil;
import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.properties.SystemProperties;
@Service
public class ComLoginServiceImpl implements ComLoginService {
	@Resource
	private ComUserService comUserService;
	@Override
	public LoginVo doLoginCode(String phone, String code) {
		LoginVo loginVo = new LoginVo();
		return loginVo;
	}

	@Override
	public LoginVo doLoginPassCode(LoginVo loginVo) {
		// TODO Auto-generated method stub
		//从连接池获取jedis
		Jedis jedis = JedisPoolUtil.getJedis();
		//获取已发送的图片证码
		String sysCodeString = jedis.get("code"+loginVo.getCode());
		if(sysCodeString != null && sysCodeString.equals(loginVo.getCode())){
			//通过哪种方式登录到系统有待优化
			ComUserVo comUserVo = comUserService.selectByEmpId(loginVo.getName());
			if(comUserVo != null){
				String encodePass = EncodeUtil.encode(loginVo.getPassword());
				if(encodePass.equals(comUserVo.getPassword())){
					//生成token，加密，并将token和对应的手机号码存储于redis中，并设置过期时间
					String token = EncodeUtil.encode("comName"+loginVo.getName()+Time.getTimeString());
					if(!token.equals("null")){
						jedis.setex(token,SystemProperties.getInt("redis.time.token.pc", 86400), comUserVo.getUserId()+"");
						loginVo.setSuccess(true);
						loginVo.setToken(token);
						loginVo.setMessage("登录成功");
					}else{
						loginVo.setSuccess(false);
						loginVo.setToken(token);
						loginVo.setMessage("TOKEN生成失败");
					}
				}else{
					loginVo.setSuccess(false);
					loginVo.setMessage("密码错误");
				}
			}else{
				loginVo.setSuccess(false);
				loginVo.setMessage("该用户未注册，请注册后再登录");
			}
		}else{
			loginVo.setSuccess(false);
			loginVo.setMessage("图片证码错误");
		}
		//将jedis返还到连接池中
		JedisPoolUtil.returnRes(jedis);
		return loginVo;
	}
	
	public LoginVo doLoginPass(LoginVo loginVo) {
		// TODO Auto-generated method stub
		//从连接池获取jedis
		Jedis jedis = JedisPoolUtil.getJedis();
		//通过哪种方式登录到系统有待优化
		ComUserVo comUserVo = comUserService.selectByEmpId(loginVo.getName());
		if(comUserVo != null){
			String encodePass = EncodeUtil.encode(loginVo.getPassword());
			if(encodePass.equals(comUserVo.getPassword())){
				//生成token，加密，并将token和对应的手机号码存储于redis中，并设置过期时间
				String token = EncodeUtil.encode("comName"+loginVo.getName()+Time.getTimeString());
				if(!token.equals("null")){
					jedis.setex(token,SystemProperties.getInt("redis.time.token.pc", 36000), comUserVo.getUserId()+"");
					loginVo.setSuccess(true);
					loginVo.setToken(token);
					loginVo.setMessage("登录成功");
				}else{
					loginVo.setSuccess(false);
					loginVo.setToken(token);
					loginVo.setMessage("TOKEN生成失败");
				}
			}else{
				loginVo.setSuccess(false);
				loginVo.setMessage("密码错误");
			}
		}else{
			loginVo.setSuccess(false);
			loginVo.setMessage("该用户未注册，请注册后再登录");
		}
		//将jedis返还到连接池中
		JedisPoolUtil.returnRes(jedis);
		return loginVo;
	}

	@Override
	public String code() {
		// TODO Auto-generated method stub
		String code = getRandom();
		Jedis jedis = JedisPoolUtil.getJedis();
		int time = SystemProperties.getInt("redis.time.code.pc",600);
		jedis.setex("code"+code,time,code);
		JedisPoolUtil.returnRes(jedis);
		return code;
	}
	
	public String getRandom(){
		String[] codes = new String[] {"0","1","2","3","4","5","6","7","8","9",
				"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		Random random = new Random();
		String code = "";
		int codeLength = SystemProperties.getInt("system.pc.code.length", 4);
		for(int i=0;i<codeLength;i++){
			code += codes[random.nextInt(36)];
		}
		return code;
	}
}
