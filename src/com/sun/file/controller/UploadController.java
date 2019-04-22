/**
 * @author SUNCHANGQING
 * @email CHANGQING.SUN@ALIYUN.COM
 */
package com.sun.file.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.cususer.service.CusUserService;
import com.sun.system.message.RespMessage;
import com.sun.util.jedis.JedisPoolUtil;
import com.xinyan.config.XinyanProperties;

import redis.clients.jedis.Jedis;


/**@author SUNCHANGQING
 * @date 2018年6月11日 
 *
 */
@RestController
@RequestMapping(value="/api/file")
public class UploadController {
	public static Log _log = LogFactory.getLog(UploadController.class);
	@Resource
	private CusUserService cusUserService;
	
	@RequestMapping(value="/uploadPhoto",consumes = "multipart/form-data",method=RequestMethod.POST)
	public Object doUploadPhoto(HttpServletRequest arg0,@RequestParam("file") MultipartFile file){
		String token = arg0.getHeader("token");
		_log.info("上传身份证手持照片="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		String newFile = "";
		try {
			String url = arg0.getRequestURL().toString();
			String fileNames = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String uploadPath = XinyanProperties.get("tomcat", "")+"download";
			newFile = uploadPath+"/"+jedis.get(token)+"_p"+fileNames;
			String newUrl = url.substring(0, url.indexOf("credit"))+"download"+"/"+jedis.get(token)+"_p"+fileNames;
			file.transferTo(new File(newFile));
			respMessage.setErrorCode(200);
			respMessage.setData(newUrl);
		} catch (IOException e) {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("上传身份证手持照片失败="+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	@RequestMapping(value="/uploadLeftPhoto",consumes = "multipart/form-data",method=RequestMethod.POST)
	public Object doUploadLeftPhoto(HttpServletRequest arg0,@RequestParam("file") MultipartFile file){
		String token = arg0.getHeader("token");
		_log.info("上传身份证反面照片="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		String newFile = "";
		try {
			String url = arg0.getRequestURL().toString();
			String fileNames = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String uploadPath = XinyanProperties.get("tomcat", "")+"download";
			newFile = uploadPath+"/"+jedis.get(token)+"_lp"+fileNames;
			String newUrl = url.substring(0, url.indexOf("credit"))+"download"+"/"+jedis.get(token)+"_lp"+fileNames;
			file.transferTo(new File(newFile));
			respMessage.setErrorCode(200);
			respMessage.setData(newUrl);
		} catch (IOException e) {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("上传身份证反面照片失败="+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	@RequestMapping(value="/uploadRightPhoto",consumes = "multipart/form-data",method=RequestMethod.POST)
	public Object doUploadRightPhoto(HttpServletRequest arg0,@RequestParam("file") MultipartFile file){
		String token = arg0.getHeader("token");
		_log.info("上传身份证正面照片="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		String newFile = "";
		try {
			String url = arg0.getRequestURL().toString();
			String fileNames = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String uploadPath = XinyanProperties.get("tomcat", "")+"download";
			newFile = uploadPath+"/"+jedis.get(token)+"_rp"+fileNames;
			String newUrl = url.substring(0, url.indexOf("credit"))+"download"+"/"+jedis.get(token)+"_rp"+fileNames;
			file.transferTo(new File(newFile));
			respMessage.setErrorCode(200);
			respMessage.setData(newUrl);
		} catch (IOException e) {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("上传身份证正面照片失败="+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	@RequestMapping(value="/uploadSelfPhoto",consumes = "multipart/form-data",method=RequestMethod.POST)
	public Object doUploadSelfPhoto(HttpServletRequest arg0,@RequestParam("file") MultipartFile file){
		String token = arg0.getHeader("token");
		_log.info("上传头像照片="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		String newFile = "";
		try {
			String url = arg0.getRequestURL().toString();
			String fileNames = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String uploadPath = XinyanProperties.get("tomcat", "")+"download";
			newFile = uploadPath+"/"+jedis.get(token)+"_sp"+fileNames;
			String newUrl = url.substring(0, url.indexOf("credit"))+"download"+"/"+jedis.get(token)+"_sp"+fileNames;
			file.transferTo(new File(newFile));
			int count = cusUserService.updateSelfPhoto(newUrl, Long.valueOf(jedis.get(token)));
			if(count == 1) {
				_log.info("修改客户头像信息"+token+"成功");
				respMessage.setErrorCode(200);
				respMessage.setData(newUrl);
			}else {
				_log.info("修改客户头像信息"+token+"失败");
				respMessage.setSuccess(false);
				respMessage.setErrorCode(201);
				respMessage.setData("客户头像数据修改失败");
			}
		} catch (IOException e) {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("上传头像照片失败="+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
	@RequestMapping(value="/upload",consumes = "multipart/form-data",method=RequestMethod.POST)
	public Object doUpload(HttpServletRequest arg0,@RequestParam("file") MultipartFile file,@RequestParam("code")String code){
		String token = arg0.getHeader("token");
		_log.info("上传身份证手持照片="+token);
		Jedis jedis = JedisPoolUtil.getJedis();
		RespMessage respMessage = new RespMessage();
		String newFile = "";
		try {
			String url = arg0.getRequestURL().toString();
			String fileNames = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String uploadPath = XinyanProperties.get("tomcat", "")+"download";
			newFile = uploadPath+"/"+jedis.get(token)+"_"+code+fileNames;
			String newUrl = url.substring(0, url.indexOf("credit"))+"download"+"/"+jedis.get(token)+"_"+code+fileNames;
			file.transferTo(new File(newFile));
			respMessage.setErrorCode(200);
			respMessage.setData(newUrl);
		} catch (IOException e) {
			respMessage.setSuccess(false);
			respMessage.setErrorCode(201);
			respMessage.setData("上传身份证手持照片失败="+e);
		}finally {
			JedisPoolUtil.returnRes(jedis);
		}
		return respMessage;
	}
}
