package com.github.izerui.file.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Table(name = "audit_record")
public class AuditRecordEntity {

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
     * 来源ip
     */
    private String ip;
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
     * 成功状态
     */
    private boolean success;
    /**
     * 开始时间
     */
    private Date begin;
    /**
     * 结束时间
     */
    private Date end;
    /**
     * 耗时
     */
    private long time;
    /**
     * token
     */
    private String token;

    /**
     * 操作年份
     */
    private Integer bYear;
    /**
     * 操作月份
     */
    private Integer bMonth;
    /**
     * 操作日(按月计)
     */
    private Integer bDay;
    /**
     * 操作时(按24小时计)
     */
    private Integer bHour;
    /**
     * 操作分钟
     */
    private Integer bMinutes;
    /**
     * 操作秒
     */
    private Integer bSeconds;
    /**
     * 操作周(按年计)
     */
    private Integer bWeek;

    public void setBegin(Date begin) {
        this.begin = begin;
        Calendar calendar = new Calendar.Builder()
                .setInstant(begin)
                .build();
        this.bYear = calendar.get(calendar.YEAR);
        this.bMonth = calendar.get(calendar.MONTH) + 1;
        this.bDay = calendar.get(calendar.DAY_OF_MONTH);
        this.bHour = calendar.get(calendar.HOUR_OF_DAY);
        this.bMinutes = calendar.get(calendar.MINUTE);
        this.bSeconds = calendar.get(calendar.SECOND);
        this.bWeek = calendar.get(calendar.WEEK_OF_YEAR);
    }
}
