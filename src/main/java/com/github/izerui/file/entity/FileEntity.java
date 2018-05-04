package com.github.izerui.file.entity;

import com.github.izerui.file.utils.RelativeDateFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by serv on 2017/4/8.
 */
@Data
@Entity
@Table(name = "file")
public class FileEntity implements Serializable{

    @Id
    private String id;
    //修改时间
    private Date uploadTime;
    //发布时间
    private Date deployTime;
    //文件名
    private String fileName;
    //服务器
    private String server;
    //服务器IP地址
    private String serverAddress;
    //发布方式
    private String deployType;//发布方式
    //文件大小
    private Long size = 0L;//文件大小
    @Transient
    private String uploadTimeStr;
    @Transient
    private String deployTimeStr;
    //文件路径
    private String filePath;
    @Transient
    private String status;
    @Transient
    private String url;
    @Transient
    private Integer port;

    public String getUploadTimeStr(){
        if(uploadTime == null){
            return null;
        }
        return RelativeDateFormat.format(uploadTime);
    }

    public String getDeployTimeStr(){
        if(deployTime == null){
            return null;
        }
        return RelativeDateFormat.format(deployTime);
    }
}
