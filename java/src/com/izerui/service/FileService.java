package com.izerui.service;

import java.util.List;

import com.izerui.vo.FileItem;
import com.izerui.vo.FileTree;

public interface FileService {
	/**
	 * 获取文件夹list tree
	 * @return
	 */
	public List<FileTree> getFolderList();
	
	/**
	 * 根据目录列出当前目录下文件
	 * @param folderPath
	 * @return
	 */
	public List<FileItem> listFilesByFolder(String folderPath);
	/**
	 * 不是新建就是删除
	 * @param path
	 * @param isCreate
	 */
	public String optFolder(String path,boolean isCreate) ;
	
	/**
	 * 重命名文件夹
	 * @param path 文件路径
	 * @param FolderNewName 最底层目录的新名字
	 * @return 新的文件夹路径
	 */
	public String renameFolder(String path,String FolderNewName);
	
	/**
	 * 返回上一级目录
	 * @param path
	 * @return
	 */
	public String interceptPath(String path);
	
	/**
	 * 返回根目录
	 * @return
	 */
	public String fomatRootFilePath();
	
	/**
	 * 删除多个文件
	 * @param fileItems
	 * @return 
	 */
	public void deleteFile(List<FileItem> fileItems);
}
