package com.sun;

import com.sun.util.encode.EncodeUtil;
import com.sun.util.view.RequestPage;

public class Test {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String tokenString = EncodeUtil.encode("1234");
//		System.out.println(tokenString);
//		System.out.println(new RequestPage());
//	}
	
	String para1;
	StringBuffer para2;

	public void method1(String param){
	para1 = param.replace("j","l");
	}

	public void method2(StringBuffer param){
	para2 = param.append("c");
	}

	public static void main(String[] args){
	Test obj = new Test();
	obj.method1(new String("java"));
	obj.method2(new StringBuffer("java"));
	System.out.println(obj.para1);
	System.out.println(obj.para2.toString());
	}



}
