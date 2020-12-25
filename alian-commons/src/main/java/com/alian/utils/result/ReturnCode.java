package com.alian.utils.result;

/**
 * @author ZZL
 */

public enum ReturnCode {

    //操作成功
    success(200,"操作成功"),
    //操作失败
    error(500,"操作失败"),
    //用户名或密码不存在
    userName_password_null(501,"用户名或密码不存在"),
    //用户名不存在
    userName_noExist(502,"用户名不存在"),
    //密码错误
    password_error(503,"密码错误"),
    //请假时间选择有误
    system_error(505,"请假时间选择有误"),
    //用户已被锁定
    userName_locked(504,"用户已被锁定"),
    //登录失效请重新登录
    login_disable(333,"登录失效请重新登录"),
    //用户名或密码错误
    login_error(403,"用户名或密码错误"),

;

    /**
     * 返回值
     * @param code
     * @param msg
     */
    ReturnCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String msg;


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
