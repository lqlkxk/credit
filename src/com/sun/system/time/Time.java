package com.sun.system.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Time {
	public static String getTimeYMDHMS() {
		Calendar calendar = Calendar.getInstance();
		Date date = (Date) calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
	public static String getTimeYMD() {
		Calendar calendar = Calendar.getInstance();
		Date date = (Date) calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	}
	public static String updateDateYMD(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	}
	public static String updateDateYMDHMS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
	public static String getTimeString() {
		 return System.currentTimeMillis()+"";
	}
	
	public static String getWeekBegin(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
         }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一 
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day); 
        return sdf.format(cal.getTime());
	}
	
	public static String getWeekEnd(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
         }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一 
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);
        cal.add(Calendar.DATE, 6);
        return sdf.format(cal.getTime());
	}
	
	public static String[] getWeek(String time) throws ParseException {
		String[] list = new String[7];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
         }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);
        list[0] = sdf.format(cal.getTime());
        for(int i =0;i<6;i++) {
        	cal.add(Calendar.DATE, 1);
        	list[i+1] = sdf.format(cal.getTime());
        }
        return list;
	}
}
