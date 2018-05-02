package com.github.izerui.file.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileItem implements Serializable {
    private String id;
    //修改时间
    private Date uploadTime;
    //发布时间
    private Date deployTime;
    //文件名
    private String fileName;
    //服务器
    private String server;
    //发布方式
    private String deployType;//发布方式
    //文件大小
    private Long size = 0L;//文件大小
    private String uploadTimeStr;
    private String deployTimeStr;

}
