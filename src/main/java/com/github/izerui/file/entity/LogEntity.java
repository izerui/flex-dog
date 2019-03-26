package com.github.izerui.file.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "log")
public class LogEntity {

    @Id
    private String id = UUID.randomUUID().toString();
    //操作时间
    private Date logTime = new Date();
    //操作类型
    private String type;
    //操作备注
    private String remark;

    public LogEntity(String type,String remark){
        this.type = type;
        this.remark = remark;
    }

    public LogEntity(){

    }

}
