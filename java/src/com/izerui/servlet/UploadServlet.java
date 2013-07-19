package com.izerui.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 * @author serv
 */
public class UploadServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(UploadServlet.class);
	
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			log.debug("没有可上传的文件, 你想干什么 !");
			return;
		}
		String serverpath = null;
		// Create a factory for disk-based file items
		String tempFilePath = getServletContext().getRealPath("/")+getServletContext().getInitParameter("tempPath");
		FileItemFactory factory = newDiskFileItemFactory(getServletContext(), new File(tempFilePath));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		//set fileItem encoding utf-8
		upload.setHeaderEncoding("UTF-8");
		/* FileItem */
		List<FileItem> items = null;
		try {
			// Parse the request
			items = (List<FileItem>) upload.parseRequest(request);
			String tempFilename = null;
			String filename = null;
			for (FileItem fileItem : items) {
				
				if (!fileItem.isFormField()){//file
					tempFilename = FilenameUtils.getName(UUID.randomUUID()+"___"+fileItem.getName());
					fileItem.write(new File(tempFilePath+tempFilename));
				}else if(fileItem.getFieldName().equals("serverpath")){ //获取服务器要保存的路径
					serverpath = new String(fileItem.get(),"UTF-8");
				}else if(fileItem.getFieldName().equals("Filename")){ //获取文件实际名字
					filename = new String(fileItem.get(),"UTF-8");
				}
			}
			
			FileUtils.moveFile(new File(tempFilePath+tempFilename), new File(serverpath+filename));
			response.getWriter().write("success");
			
		} catch (FileUploadException e) {
			log.error(e.getMessage());
			throw new IOException();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new IOException();
		}
	}
	
	/**
	 * The FileCleanerCleanup provides an instance of org.apache.commons.io.FileCleaningTracker. 
	 * This instance must be used when creating a org.apache.commons.fileupload.disk.DiskFileItemFactory.
	 */
	public static DiskFileItemFactory newDiskFileItemFactory(
			ServletContext context, File repository) {
		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup
				.getFileCleaningTracker(context);
		DiskFileItemFactory factory = new DiskFileItemFactory(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
		factory.setFileCleaningTracker(fileCleaningTracker);
		return factory;
	}

}
