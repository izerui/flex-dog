package com.izerui.vo;

import java.util.List;

public class FileTree {
	private String foldername;//文件夹名字
	private String folderpath;//文件夹实际路径
	private List<FileTree> children;//子文件夹
	public String getFoldername() {
		return foldername;
	}
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}
	public List<FileTree> getChildren() {
		return children;
	}
	public void setChildren(List<FileTree> children) {
		this.children = children;
	}
	public String getFolderpath() {
		return folderpath;
	}
	public void setFolderpath(String folderpath) {
		this.folderpath = folderpath;
	}
}
