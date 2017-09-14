package com.zhijian.ebook.bean;

import com.alibaba.fastjson.JSONObject;
//import com.gener.common.util.ObjectUtil;

/***
 * 
 * 响应实体
 * 
 * @author Administrator
 *
 */
public class ResponseEntity {

    private ResponseEntity(int status, Object data, String message) {
        super();
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     * 200 OK
     */
    public static final int STATUS_OK = 200;

    /**
     * 403 没有权限
     */
    public static final int STATUS_FORBIDDEN = 403;

    /**
     * 408 Session超时
     */
    public static final int STATUS_SESSION_TIMEOUT = 408;

    /**
     * 417 非法参数异常
     */
    public static final int STATUS_ILLEGAL_PARAM = 417;

    /**
     * 500 服务器异常
     */
    public static final int STATUS_SERVER_ERROR = 500;

    /**
     * 600 未登录
     */
    public static final int STATUS_NOT_LOGGED_IN = 600;

    /**
     * 状态码
     */
    private int status;

    /**
     * 数据
     */
    private Object data;

    /**
     * 消息
     */
    private String message;

    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 如果这个data对象为空，实例化一个JSONObject并返回，否则返回data
     * 
     * @param data
     *            查询条件
     * @return 对象数据
     */
    private static Object ifEmpty(Object data) {
        if (data==null) {
            return new JSONObject();
        } else {
            return data;
        }
    }

    /**
     * 成功
     * 
     * @return 成功响应实体
     */
    public static ResponseEntity ok() {
        return new ResponseEntity(STATUS_OK, "", "成功！");
    }

    /**
     * 成功
     * 
     * @param message
     *            成功消息
     * @return 成功响应实体
     */
    public static ResponseEntity ok(String message) {
        return new ResponseEntity(STATUS_OK, "", message);
    }

    /**
     * 成功
     * 
     * @param data
     *            要返回的数据
     * @return 成功响应实体
     */
    public static ResponseEntity ok(Object data) {
        return new ResponseEntity(STATUS_OK, ifEmpty(data), "成功！");
    }

    /**
     * 成功
     * 
     * @param data
     *            要返回的数据
     * @param message
     *            响应消息
     * @return 成功状态的响应实体
     */
    public static ResponseEntity ok(Object data, String message) {
        return new ResponseEntity(STATUS_OK, ifEmpty(data), message);
    }

    /**
     * 没有权限，message=没有权限！
     * 
     * @return 没有权限状态的响应实体
     */
    public static ResponseEntity forbidden() {
        return new ResponseEntity(STATUS_FORBIDDEN, "", "没有权限！");
    }

    /**
     * 会话超时，message=会话超时，请重新登录！
     * 
     * @return 会话超时状态的响应实体
     */
    public static ResponseEntity sessionTimeout() {
        return new ResponseEntity(STATUS_SESSION_TIMEOUT, "", "会话超时，请重新登录！");
    }

    /**
     * 非法参数
     * 
     * @param message
     *            响应消息
     * @return 非法参数状态的响应实体
     */
    public static ResponseEntity illegalParam(String message) {
        return new ResponseEntity(STATUS_ILLEGAL_PARAM, "", message);
    }

    /**
     * 服务器异常
     * 
     * @param message
     *            响应消息
     * @return 服务器异常状态的响应实体
     */
    public static ResponseEntity serverError(String message) {
        return new ResponseEntity(STATUS_SERVER_ERROR, "", message);
    }

    /**
     * 服务器异常， message=系统异常，请稍后重试！
     * 
     * @return 服务器异常状态的响应实体
     */
    public static ResponseEntity serverError() {
        return new ResponseEntity(STATUS_SERVER_ERROR, "", "系统异常，请稍后重试！");
    }

    /**
     * 未登录， message=未登录！
     * 
     * @return 未登录状态的响应实体
     */
    public static ResponseEntity notLoggedIn() {
        return new ResponseEntity(STATUS_NOT_LOGGED_IN, "", "未登录！");
    }

    @Override
    public String toString() {
        return "ResponseEntity [status=" + status + ", data=" + data + ", message=" + message + "]";
    }

}
