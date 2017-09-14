package com.zhijian.ebook.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.zhijian.ebook.util.PasswordEncoderUtil;

/**
 * security 密码加密器
 * 
 * @author zhanghua on Oct 22, 2015
 *
 */
@SuppressWarnings("deprecation")
public class Md5PasswordEncoder implements PasswordEncoder {

    private static final Logger log = LogManager.getLogger();

    @Override
    public String encodePassword(String arg0, Object arg1) {
        log.debug(arg0);
        log.debug(arg1);
        PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(arg0, "MD5");
        String md5 = md5Mncoder.encode(arg0);
        log.debug(md5);
        return md5;
    }

    @Override
    public boolean isPasswordValid(String arg0, String arg1, Object arg2) {
        PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(arg2, "MD5");
        String md5 = md5Mncoder.encode(arg1);
        if (md5.equals(arg0)) {
            return true;
        }
        return false;
    }

}
