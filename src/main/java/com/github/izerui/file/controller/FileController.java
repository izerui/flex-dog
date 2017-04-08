package com.github.izerui.file.controller;

import com.github.izerui.file.utils.VideoMimeTypes;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Controller
public class FileController {
	
	private Logger log = Logger.getLogger(FileController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(
			@RequestParam("Filedata") MultipartFile file,
			@RequestParam("serverpath") String serverpath, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		try {
			File newFile = new File(FilenameUtils.getFullPath(serverpath)+ file.getOriginalFilename());

			IOUtils.copy(file.getInputStream(),new FileOutputStream(newFile));

			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("success");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public void download(@RequestParam("path") String filepath,@RequestParam("name") String filename,HttpServletResponse response){
		try {
			filepath += filename;
			File file = new File(filepath);
			if(file.isFile()){//存在文件
				response.reset();  
				response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");  
				response.addHeader("Content-Length", "" + file.length());  
				response.setContentType("application/octet-stream;charset=UTF-8");  
				IOUtils.copy(new FileInputStream(file), response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
	@RequestMapping(value = "/getVideoFile", method = RequestMethod.GET)
	public void getVideoFile(@RequestParam("flvfile") String flvfile, HttpServletResponse response) {
		try {
			String filename = null;
			Long filelength = 0l;
			File file = null;
			file = new File(flvfile);
			filename = file.getName();
			filelength = file.length();
			response.reset();
			response.setContentType(VideoMimeTypes.getVideoMimeTypes(
					FilenameUtils.getExtension(filename)).getMine());
			response.setContentLength(filelength.intValue());
			response.setHeader("Content-Disposition", "inline; filename="
					+ filename);
			IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
