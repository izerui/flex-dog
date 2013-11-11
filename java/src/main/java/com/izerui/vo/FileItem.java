package com.izerui.vo;

import java.util.Date;

public class FileItem{
	private String filename;//文件全名
	private String extension;//后缀名
	private String basename;//去掉后缀名
	private String folderpath;//所属文件夹路径
	private Long size;//文件大小
	private boolean ishidden;//是否是隐藏文件
	private boolean isfolder;
	private Date lashmodifydate;
	public Date getLashmodifydate() {
		return lashmodifydate;
	}
	public void setLashmodifydate(Date lashmodifydate) {
		this.lashmodifydate = lashmodifydate;
	}
	public boolean isIshidden() {
		return ishidden;
	}
	public void setIshidden(boolean ishidden) {
		this.ishidden = ishidden;
	}
	public boolean isIsfolder() {
		return isfolder;
	}
	public void setIsfolder(boolean isfolder) {
		this.isfolder = isfolder;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getBasename() {
		return basename;
	}
	public void setBasename(String basename) {
		this.basename = basename;
	}
	public String getFolderpath() {
		return folderpath;
	}
	public void setFolderpath(String folderpath) {
		this.folderpath = folderpath;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
}
