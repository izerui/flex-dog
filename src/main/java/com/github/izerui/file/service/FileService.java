package com.github.izerui.file.service;

import com.github.izerui.file.vo.FileItem;
import com.github.izerui.file.vo.FileTree;

import java.util.List;

public interface FileService {

	/**
	 * 根据目录列出当前目录下文件
	 * @return
	 */
	public List<FileItem> listFilesByFolder() throws Exception;

	/**
	 * 删除多个文件
	 * @param fileItems
	 * @return 
	 */
	public void deleteFile(List<FileItem> fileItems);

	/**
	 * 发布
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String exec(String fileName) throws Exception;
}
