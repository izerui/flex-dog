package com.github.izerui.file.vo;

import java.io.Serializable;
import java.util.Date;

public class FileItem implements Serializable{
	private String filename;//文件全名
	private String extension;//后缀名
	private String basename;//去掉后缀名
	private String folderpath;//所属文件夹路径
	private Long size;//文件大小
	private Boolean ishidden;//是否是隐藏文件
	private Boolean isfolder;
	private Date lashmodifydate;
	public Date getLashmodifydate() {
		return lashmodifydate;
	}
	public void setLashmodifydate(Date lashmodifydate) {
		this.lashmodifydate = lashmodifydate;
	}
	public Boolean isIshidden() {
		return ishidden;
	}
	public void setIshidden(Boolean ishidden) {
		this.ishidden = ishidden;
	}
	public Boolean isIsfolder() {
		return isfolder;
	}
	public void setIsfolder(Boolean isfolder) {
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
