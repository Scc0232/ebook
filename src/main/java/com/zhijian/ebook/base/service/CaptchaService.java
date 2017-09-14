package com.zhijian.ebook.base.service;

import javax.servlet.http.HttpSession;

import com.zhijian.ebook.bean.ResponseEntity;
import com.zhijian.ebook.bean.SMSCaptcha;

/**
 * 验证码 服务层
 * 
 * @author zhanghua on Oct 15, 2015
 *
 */
public interface CaptchaService {

    /**
     * 验证图片验证码 是否正确
     * 
     * @param imageCaptcha
     *            用户输入的图片验证码
     * @param session
     * @return
     * @throws Exception
     */
    boolean validateImageCaptcha(String imageCaptcha, HttpSession session);

    /**
     * 验证短信验证码 是否正确
     * 
     * @param sms
     * @param session
     * @return
     * @throws Exception
     */
    boolean validateSMSCaptcha(SMSCaptcha sms, HttpSession session);

    /**
     * 添加图形验证码到Session
     * 
     * @param imageCaptcha
     * @param session
     * @throws Exception
     */
    void setImageCaptchaToSession(String imageCaptcha, HttpSession session);

    /**
     * 添加短信验证码到Session
     * 
     * @param sms
     * @param session
     */
    void setSMSCaptchaToSession(SMSCaptcha sms, HttpSession session);

    /**
     * 是否可以发送短信验证码，如果手机号码已被注册，不能发送，为了防止短信轰炸，我们将限制同一IP重复获取验证码的频率
     * 
     * @param remoteAddr
     *            远端地址
     * @param to
     *            发送至（手机号）
     * @param isRegister
     *            是否是注册, if true 是注册，需要验证手机号是否已被注册 if false 是找回密码，需要验证手机号是否注册过
     * @return 如果返回值不等于null, 则不能发送。返回的是失败原因。如果返回null,则可以发送
     * @throws Exception
     *             异常
     */
    ResponseEntity isCanSend(String remoteAddr, String to, boolean isRegister) throws Exception;
}
