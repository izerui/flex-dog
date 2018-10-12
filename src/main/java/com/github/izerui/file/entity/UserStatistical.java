package com.github.izerui.file.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_statistical", indexes = {
        @Index(name = "application", columnList = "application"),
        @Index(name = "type", columnList = "type"),
        @Index(name = "name", columnList = "name"),
        @Index(name = "userCode", columnList = "userCode")
})
public class UserStatistical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 应用名称
     */
    private String application;
    /**
     * 类型
     */
    private String type;
    /**
     * aop的类的信息
     */
    private String signature;
    /**
     * 名称
     */
    private String name;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 平台账号
     */
    private String accountCode;
    /**
     * 手机号
     */
    private String accountName;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 账套编号
     */
    private String entCode;
    /**
     * 账套名称
     */
    private String entName;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后操作时间
     */
    private Date lastOptTime;

    /**
     * 请求次数
     */
    private long count;
}
