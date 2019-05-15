package com.github.izerui.file.web;


public class Response {

    /**
     * 请求处理是否成功
     */
    private boolean success;


    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 响应内容实体
     */
    private Object data;

    protected Response() {
    }

    protected Response(boolean success, String errMsg, Object data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    /**
     * 成功
     */
    public static <T> Response success(T data) {
        return new Response(true, null, data);
    }

    /**
     * 成功
     */
    public static Response success() {
        return new Response(true, null, null);
    }

    /**
     * 失败
     */
    public static Response error(String errMsg) {
        return new Response(false, errMsg, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
