package com.sun.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;

import com.sun.util.jedis.JedisPoolUtil;
import com.sun.util.properties.SystemProperties;
/**
 * @author SUNCHANGQING
 * @date 2018年5月17日 
 * 第二级拦截器
 */
public class MyInterceptor2 implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}
	/**
	 * token判断拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String reqUrl = arg0.getRequestURI();
		boolean allRun = SystemProperties.getBoolean("request.url.spring.all.run", false);
		if(allRun){
			return true;
		}else{
			String[] rules = SystemProperties.getArray("request.url.spring.interceptor",null);
			if(rules != null && rules.length > 0){
				boolean run = true;
				for(int i=0;i<rules.length;i++){
					if(reqUrl.indexOf(rules[i]) > -1){
						run = false;
						break;
					}
				}
				if(run){
					String token = arg0.getHeader("token");
					if(token == null || token.equals("null")){
						arg1.sendRedirect(SystemProperties.get("request.url.interceptor3.redirect", "/sun/api/error/interceptorurl3"));
						return false;
					}else{
						Jedis jedis = JedisPoolUtil.getJedis();
						String tokenVlaue = jedis.get(token);
						JedisPoolUtil.returnRes(jedis);
						if(tokenVlaue == null || tokenVlaue.equals("null")){
							arg1.sendRedirect(SystemProperties.get("request.url.interceptor2.redirect", "/sun/api/error/interceptorurl2"));
							return false;
						}else{
							return true;
						}
					}	
				}else{
					return true;
				}
			}else{
				String token = arg0.getHeader("token");
				Jedis jedis = JedisPoolUtil.getJedis();
				String tokenVlaue = jedis.get(token);
				JedisPoolUtil.returnRes(jedis);
				if(tokenVlaue == null || tokenVlaue.equals("null")){
					arg1.sendRedirect(SystemProperties.get("request.url.interceptor2.redirect", "/sun/api/error/interceptorurl2"));
					return false;
				}else{
					return true;
				}
			}
		}
	}

}
