package com.sun.login.service;

import com.sun.login.vo.LoginVo;

public interface ComLoginService {
	public LoginVo doLoginCode(String phone,String code);
	public LoginVo doLoginPassCode(LoginVo loginVo);
	public LoginVo doLoginPass(LoginVo loginVo);
	public String code();
}
