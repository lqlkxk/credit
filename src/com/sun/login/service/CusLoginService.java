package com.sun.login.service;

import com.sun.login.vo.LoginVo;

public interface CusLoginService {
	public LoginVo doLoginCode(LoginVo loginVo);
	public LoginVo doLoginPass(String name,String pass,String code);
	public Object smsCode(String phone);
}
