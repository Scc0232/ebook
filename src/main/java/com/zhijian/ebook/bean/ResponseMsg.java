package com.zhijian.ebook.bean;

/**
 * 响应消息
 * 
 * @author zhanghua on 2015-7-28 14:17:27
 *
 */
public class ResponseMsg {

    /**
     * 成功标记 success
     */
    public static final String FLAG_SUCCESS = "success";

    /**
     * 失败标记 fail
     */
    public static final String FLAG_FAIl = "fail";

    private ResponseMsg(String flag, Object msg) {
        if (msg == null)
            msg = "";
        this.flag = flag;
        this.msg = msg;
    }

    /**
     * 标记， 成功：{@link FLAG_SUCCESS}, 失败：{@link FLAG_FAIl}
     */
    private String flag;

    /**
     * 消息
     */
    private Object msg;

    public Object getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回成功消息，msg为给定消息
     * 
     * @param msg
     *            给定消息
     * @return 响应成功信息
     */
    public static ResponseMsg success(Object msg) {
        return new ResponseMsg(FLAG_SUCCESS, msg);
    }

    /**
     * 返回成功消息, msg为：空
     * 
     * @return 响应成功状态
     */
    public static ResponseMsg success() {
        return new ResponseMsg(FLAG_SUCCESS, null);
    }

    /**
     * 返回失败消息，msg为给定消息
     * 
     * @param msg
     *            给定消息
     * @return 响应失败信息
     */
    public static ResponseMsg fail(Object msg) {
        return new ResponseMsg(FLAG_FAIl, msg);
    }

    /**
     * 返回失败消息，msg为：系统异常，请稍后重试！
     * 
     * @return 响应失败信息
     */
    public static ResponseMsg fail() {
        return new ResponseMsg(FLAG_FAIl, "系统异常，请稍后重试！");
    }

    @Override
    public String toString() {
        return "ResponseMsg [flag=" + flag + ", msg=" + msg + "]";
    }

}
