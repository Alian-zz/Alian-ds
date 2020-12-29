package com.alian.ums.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author ZZL
 */
@Data
@Document("system-logs")
public class SystemLogs {

    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 类名
     */
    private String className;
    /**
     * 参数名
     */
    private List<Object> argsName;
    /**
     * 登录设备
     */
    private String deviceName;
    /**
     * 类型 1正常 2异常
     */
    private Short type;
    /**
     * 运行时间
     */
    private Long runTime;
    /**
     * 日志描述
     */
    private String describeInfo;
    /**
     * 错误信息
     */
    private String errorInfo;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;



}
