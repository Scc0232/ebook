package com.zhijian.ebook.bean;

/**
 * 短信验证码, 目前短信验证码有效时间为5分钟
 * 
 * @author zhanghua on 15/11/02
 *
 */
public class SMSCaptcha {

    /**
     * 超时时间 5分钟
     */
    private final long timeOut = 1000 * 60 * 5;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 发送时间
     */
    private long sendTime;

    public SMSCaptcha() {
        super();
    }

    public SMSCaptcha(String phoneNumber, String captcha) {
        super();
        this.phoneNumber = phoneNumber;
        this.captcha = captcha;
        this.sendTime = System.currentTimeMillis();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "SMSCaptcha [phoneNumber=" + phoneNumber + ", captcha=" + captcha + ", sendTime=" + sendTime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((captcha == null) ? 0 : captcha.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj 是从session中取出的对象
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SMSCaptcha other = (SMSCaptcha) obj;
        if (captcha == null) {
            if (other.captcha != null)
                return false;
        } else if (!captcha.equals(other.captcha))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;

        long sendTime = other.getSendTime();
        long currTime = System.currentTimeMillis();
        if (currTime - sendTime > timeOut) {
            // 超时
            return false;
        }
        return true;
    }

}
