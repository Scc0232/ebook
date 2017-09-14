package com.zhijian.ebook.util;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 验证码 工具类
 * 
 * @author zhanghua on Oct 13, 2015
 *
 */
public class CaptchaUtils {

    /**
     * Session 中的验证码KEY
     */
    public static final String SESSION_CAPTCHA_KEY = "EASYPLAY_SMS_CAPTCHA";

    /**
     * 图片验证码 宽度
     */
    private static final int IMAGE_CAPTCHA_WIDTH = 200;

    /**
     * 图片验证码 高度
     */
    private static final int IMAGE_CAPTCHA_HEIGHT = 50;

    /**
     * 图片验证码 字符数
     */
    private static final int IMAGE_CAPTCHA_LENGTH = 4;

    /**
     * 验证码长度
     */
    private static final int CAPTCHA_LENGTH = 6;

    /**
     * 生成CAPTCHA_LENGTH位，纯数字验证码
     * 
     * @return
     */
    public static String generate() {
        return RandomStringUtils.randomNumeric(CAPTCHA_LENGTH);
    }

    /**
     * 生成IMAGE_CAPTCHA_LENGTH位，含有大小写字母和数字的验证码
     * 
     * @return
     */
    public static String generateCode() {
        return RandomStringUtils.randomAlphabetic(IMAGE_CAPTCHA_LENGTH);
    }

/*    *//**
     * 图片验证码
     * 
     * @param captcha
     * @return
     *//*
    public static BufferedImage imageCaptcha(String captcha) {
        return new Captcha().generate(IMAGE_CAPTCHA_WIDTH, IMAGE_CAPTCHA_HEIGHT, captcha).getImage();
    }*/
}
