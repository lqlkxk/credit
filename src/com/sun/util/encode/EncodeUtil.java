package com.sun.util.encode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.util.properties.SystemProperties;
/**
 * 实现数据加密
 * @author SUNCHANGQING
 * 2018年5月3日下午1:13:10
 */
public class EncodeUtil {
	
	public static Log _log = LogFactory.getLog(EncodeUtil.class);
	public static String encode(String source){
		String encodeType = SystemProperties.get("system.util.encode.type", "");
		StringBuffer hexValue = new StringBuffer();
		if(encodeType.equals("MD5")){
			try {
	            // 获得MD5 消息摘要
	            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	            // 获得指定编码的字节数据
	            byte[] sourceBytes = source.getBytes("UTF-8");
	            // 指定的字节数组对摘要执行最终更新
	            byte[] digestBytes = messageDigest.digest(sourceBytes);
	            for (int i = 0; i < digestBytes.length; i++) {
	                int val = (digestBytes[i] & 0xff);
	                if (val < 16){
	                    hexValue.append("0");
	                }
	                hexValue.append(Integer.toHexString(val));
	            }
	        }catch (NoSuchAlgorithmException e) {
	        	_log.error("NoSuchAlgorithmException--获得MD5 消息摘要异常");
	        	hexValue.append("null");
	        } catch (UnsupportedEncodingException e) {
	            _log.error("UnsupportedEncodingException--MD5获得指定编码的字节数据异常");
	        	hexValue.append("null");
	        }   
		}else if(encodeType.equals("SHA")){
			try {
	            // 获取SHA的消息摘要
	            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
	            // 获取指定编码的字符串字节数组
	            byte[] sourceBytes = source.getBytes("UTF-8");
	            // 对指定字节数据进行更新
	            byte[] digestBytes = messageDigest.digest(sourceBytes);
	            for (int i = 0; i < digestBytes.length; i++) {
	                int val = (digestBytes[i] & 0xff);
	                if (val < 16) {
	                    hexValue.append("0");
	                }
	                hexValue.append(Integer.toHexString(val));
	            }
	        } catch (NoSuchAlgorithmException e) {
	        	_log.error("NoSuchAlgorithmException--获取SHA的消息摘要异常");
	        	hexValue.append("null");
	        } catch (UnsupportedEncodingException e) {
	        	 _log.error("UnsupportedEncodingException--SHA获得指定编码的字节数据异常");
	        }   
		}else{
			hexValue.append(source);
		}
		return hexValue.toString();
	}
}
