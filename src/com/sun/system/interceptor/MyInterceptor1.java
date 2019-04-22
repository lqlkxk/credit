package com.sun.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.sun.util.properties.SystemProperties;
/**
 * @author SUNCHANGQING
 * @date 2018年5月17日 
 * 第一级拦截器
 */
public class MyInterceptor1 implements HandlerInterceptor {

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
	 * 拦截请求路径中包含特殊符号的请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String reqUrl = arg0.getRequestURI();
		//获取配置文件中设置的需要拦截的特殊字符
		String[] rules = SystemProperties.getArray("request.url.char.interceptor",null);
		if(rules != null && rules.length > 0){
			boolean run = true;
			for(int i=0;i<rules.length;i++){
				if(reqUrl.indexOf(rules[i]) > -1){
					run = false;
					break;
				}
			}
			if(run){
				return run;
			}else{
				arg1.sendRedirect(SystemProperties.get("request.url.interceptor1.redirect", "/sun/api/error/interceptorurl1"));
				return run;
			}
		}else{
			return true;
		}
	}

}
