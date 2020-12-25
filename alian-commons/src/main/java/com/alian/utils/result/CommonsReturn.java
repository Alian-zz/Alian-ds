package com.alian.utils.result;

public class CommonsReturn {


    private Integer code;
    private String msg;
    private Object data;


    private CommonsReturn(){

    }
    private CommonsReturn(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
    private CommonsReturn(Integer code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static CommonsReturn success(){
        return new CommonsReturn(ReturnCode.success.getCode(), ReturnCode.success.getMsg());
    }
    public static CommonsReturn success(Object data){
        return new CommonsReturn(ReturnCode.success.getCode(), ReturnCode.success.getMsg(),data);
    }
    public static CommonsReturn success(ReturnCode returnCode){
        return new CommonsReturn(returnCode.getCode(),returnCode.getMsg());
    }
    public static CommonsReturn success(ReturnCode returnCode, Object data){
        return new CommonsReturn(returnCode.getCode(),returnCode.getMsg(),data);
    }

    public static CommonsReturn error(){
        return new CommonsReturn(ReturnCode.error.getCode(), ReturnCode.error.getMsg());
    }
    public static CommonsReturn error(Object data){
        return new CommonsReturn(ReturnCode.error.getCode(), ReturnCode.error.getMsg(),data);
    }
    public static CommonsReturn error(ReturnCode returnCode){
        return new CommonsReturn(returnCode.getCode(),returnCode.getMsg());
    }
    public static CommonsReturn error(ReturnCode returnCode, Object data){
        return new CommonsReturn(returnCode.getCode(),returnCode.getMsg(),data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}