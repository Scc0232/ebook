package com.zhijian.ebook.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.Map;

/**
 * 短信服务 工具类
 * 
 * @author songchaoyue 
 *
 */
public class SMSUtils {

    private static CCPRestSmsSDK restAPI = null;

    /**
     * 短信服务器IP地址
     */
    private static final String SERVER_IP = "app.cloopen.com";

    /**
     * 短信服务器端口号
     */
    private static final String SERVER_PORT = "8883";

    private static final String ACCOUNT_SID = "8a216da85f008800015f2317c2630bca";

    private static final String AUTH_TOKEN = "21b4837740814a5799ea49e9be06339a";

    private static final String APP_ID = "8a216da85f008800015f2317c3c80bd1";

    /**
     *TODO 注册验证码短信模板ID
     */
    private static String DEFAULT_TEMPLATE_ID = "213393";
    
   

    // 初始化SDK
    static {
        restAPI = new CCPRestSmsSDK();

        // 初始化服务器地址和端口
        restAPI.init(SERVER_IP, SERVER_PORT);

        // 初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);

        // 初始化应用ID
        restAPI.setAppId(APP_ID);
    }

//    private static final String[] singleAry = new String[1];

    private static final String[] bothAry = new String[2];
    
    /**
     * 验证码超时时间(分)
     */
    private static final String CAPTCHA_TIME_OUT = "5分钟";
    
    
    /**
     * 使用默认模板DEFAULT_TEMPLATE_ID 发送验证码
     * 
     * @param to
     *            发送至（手机号）
     * @param captcha
     *            验证码
     * @return
     */
    public static Map<String, Object> sendCaptchaSMS(String to, String captcha) {
        // setHistory(to);
        // singleAry[0] = captcha;
        bothAry[0] = captcha;
        bothAry[1] = CAPTCHA_TIME_OUT;
        return restAPI.sendTemplateSMS(to, DEFAULT_TEMPLATE_ID, bothAry);
    }

    /**
     * 假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为
     * result = restAPI.sendTemplateSMS("13800000000","1" ,new
     * String[]{"6532","5"});
     * 则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入
     * 
     * @param to
     * @param datas
     * @param templateId
     * @return
     */
    public static Map<String, Object> sendTemplateSMS(String to, String[] datas, String templateId) {
        // setHistory(to);
        return restAPI.sendTemplateSMS(to, templateId, datas);
    }
}
