package com.zhijian.ebook.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.gener.common.util.ObjectUtil;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.CaptchaService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.ResponseEntity;
import com.zhijian.ebook.bean.SMSCaptcha;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserService userService;

    /**
     * Session中的短信验证码Key
     */
    private static final String SESSION_SMS_CAPTCHA_KEY = "SESSION_SMS_CAPTCHA_KEY";

    /**
     * Session中的图片验证码Key
     */
    private static final String SESSION_IMAGE_CAPTCHA_KEY = "SESSION_IMAGE_CAPTCHA_KEY";

    /**
     * 短信验证码获取最小间隔时间（毫秒），限制短信验证码获取的频率。
     */
    private static final long SMS_GET_MIN_INTERVAL_TIME = 60 * 1000;

    /**
     * 短信服务历史记录
     */
    private static Map<String, Long> smsHistory = new HashMap<>();

    /**
     * 保存短信服务历史记录
     * 
     * @param remoteAddr
     *            远端IP地
     */
    private void saveSMSHistory(String remoteAddr) {
        long timeMillis = System.currentTimeMillis();
        smsHistory.put(remoteAddr, timeMillis);
    }

    @Override
    public ResponseEntity isCanSend(String remoteAddr, String to, boolean isRegister) throws Exception {
        User user = userService.findUserByUsername(to);

        if (isRegister) {
            // 是注册，需要验证手机号是否已被注册
            if (user != null) {
                return ResponseEntity.serverError("手机号已被注册！请直接登录！");
            }
        } else {
            // 是找回密码，需要验证手机号是否注册过
            if (user == null) {
                return ResponseEntity.serverError("手机号未注册！");
            }
        }

        Long timeMillis = smsHistory.get(remoteAddr);
        if (timeMillis != null) {// 这个IP上次请求短信验证码的时间
            // 当前间隔时间
            Long intervalTime = System.currentTimeMillis() - timeMillis;
            if (intervalTime < SMS_GET_MIN_INTERVAL_TIME) {
                log.warn("IP【" + remoteAddr + "】to【" + to + "】有短信轰炸嫌疑，获取短信验证码间隔时间为【" + intervalTime + "】ms");
                return ResponseEntity.serverError("短时间内不能重复获取短信验证码！");
            }
        }
        saveSMSHistory(remoteAddr);// 保存历史记录
        return null;
    }

    @Override
    public boolean validateImageCaptcha(String imageCaptcha, HttpSession session) {
        if (!StringUtils.isEmpty(imageCaptcha)) {
            Object attr = session.getAttribute(SESSION_IMAGE_CAPTCHA_KEY);
            if (attr != null) {
                String captcha = attr.toString();
                if (imageCaptcha.equalsIgnoreCase(captcha)) {
                    session.removeAttribute(SESSION_IMAGE_CAPTCHA_KEY);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setImageCaptchaToSession(String imageCaptcha, HttpSession session) {
        session.setAttribute(SESSION_IMAGE_CAPTCHA_KEY, imageCaptcha);
    }

    @Override
    public boolean validateSMSCaptcha(SMSCaptcha sms, HttpSession session) {
    	log.info("校验验证码开始....传过来的手机号={},验证码={}",sms.getPhoneNumber(),sms.getCaptcha());
        Object attr = session.getAttribute(SESSION_SMS_CAPTCHA_KEY);
        SMSCaptcha smsCap = attr != null ? (SMSCaptcha) attr : null;
        if (smsCap == null || sms == null) {
        	log.info("session中的验证码手机 号为空！");
			return false;
		}
        log.info("session中的手机号={},验证码={}",smsCap.getPhoneNumber(),smsCap.getCaptcha());
        if (sms.getCaptcha().equals(smsCap.getCaptcha()) && sms.getPhoneNumber().equals(smsCap.getPhoneNumber())) {
			return true;
		}
        return false;
    }

    @Override
    public void setSMSCaptchaToSession(SMSCaptcha sms, HttpSession session) {
        session.setAttribute(SESSION_SMS_CAPTCHA_KEY, sms);
    }
}
