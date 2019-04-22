package com.sun.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemProperties {
	
	public static Log _log = LogFactory.getLog(SystemProperties.class);
	private static Properties _p = null;
	private static SystemProperties _pu = new SystemProperties();
	
	public SystemProperties(){
		Properties p = new Properties();
		ClassLoader classLoader = SystemProperties.class.getClassLoader();
		_log.info("开始加载system.properties");
		InputStream is = null;
		try {
			URL url = classLoader.getResource("system.properties");
			if (url != null) {
				is = url.openStream();
				p.load(is);
				_log.debug("Loading " + url);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		_p = p;
	    _log.info("加载Properties配置文件完成");
	}
	
	public static Properties getProperties() {
		return _p;
	}
	
	public static String get(String key){
		return get(key, null);
	}
	
	public static String get(String key, String defaultVal){
		String val = _p.getProperty(key); 
	    if ((val == null) || ("".equals(val))) {
	      return defaultVal;
	    }
	    return val;
	}
	
	public static int getInt(String key, int defaultVal){
	    String val = get(key, defaultVal + "");
	    return Integer.parseInt(val);
	}

	public static long getLong(String key, long defaultVal){
	    String val = get(key, defaultVal + "");
	    return Long.parseLong(val);
	}

	public static String[] getArray(String key, String[] defaultVal){
	    String value = get(key);
	    if ((value == null) || ("".equals(value))) {
	      return defaultVal;
	    }
	    return value.split(",");
	}

	public static boolean getBoolean(String key, boolean defaultVal){
	    String val = get(key, defaultVal + "");
	    val = val.toLowerCase();
	    if (("true".equals(val)) || ("y".equals(val)) || ("1".equals(val))) {
	      return true;
	    }
	    return false;
	}
}
