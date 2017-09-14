package com.zhijian.ebook.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.CaptchaService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.util.PasswordEncoderUtil;
import com.zhijian.ebook.util.StringConsts;


/**
 * 主控制器
 * 
 * @author Administrator
 *
 */

@Controller
public class MainController {

	private static final Logger log = LogManager.getLogger();
	
	/**
     * 用户名密码不匹配
     */
    private static final Integer LOGIN_USER_PWD_NOMATCH = 1;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CaptchaService captchaService;
    
	/**
     * 登录视图
     * 
     * @param map
     *            modelMap
     * @param login_error
     *            登陆错误
     * @return String 字符结果
     */
    @RequestMapping("loginView")
    public String loginView(ModelMap map, Integer login_error) {
        if (LOGIN_USER_PWD_NOMATCH.equals(login_error)) {
            map.put("nomatch", true);
        }
        return "login";
    }
    
    /**
     * 没有权限（403）视图
     * 
     * @return String 字符结果
     */

    @RequestMapping("notauthView")
    public String notauthView() {
        return "notauth";
    }

    /**
     * 后台管理， 首页视图
     * 
     * @param map
     *            modelMap 模型Map
     * @return String 字符结果
     */
    @RequestMapping("manager/index")
    public String indexView(ModelMap map) {
        String username = UserContextHelper.getUsername();
        map.put("username", username);
        return "manager/index";
    }

    /**
     * 登录
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param imageCaptcha
     *            图片验证码， 验证码通过了才能登录.获取图片验证码地址：/captcha/imageCaptcha.do
     * @param session
     *            会话
     * @param response 响应
     * @return 当flag=fail, msg是失败消息。当flag=success，msg的roleName属性是用户的角色名称
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseMsg login(String username, String password, String imageCaptcha, HttpSession session, HttpServletResponse response) {
        try {
        	System.out.println("用户名："+username+"  密码："+password);
            if (!captchaService.validateImageCaptcha(imageCaptcha, session)) {
                // return ResponseMsg.fail("验证码错误！请重新输入");
            }
            User user = userService.findUserByUsername(username);
            if (user != null) {// 验证用户名是否存在
                PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(username, StringConsts.PASSWORD_ENCRYPTION_WAY);
                String md5 = md5Mncoder.encode(password);
                if (md5.equals(user.getPassword())) {// 验证密码是否正确
                    // 登录
                    UserDetails userDetails = userService.findSysUserDetails(username);
                    UserContextHelper.login(userDetails, session);
                    
                    // 将角色信息放入Session
                    /*Role role = roleService.findUniqueRole(username);
                    sessionService.setRole(role, session);
                    Map<String, Object> msg = new HashMap<>();
                    msg.put("roleName", role.getName());
                    return ResponseMsg.success(msg);*/
                    
                    return ResponseMsg.success();
                }
            }
            return ResponseMsg.fail("用户名或密码错误！");
        } catch (Exception e) {
            log.error("登录失败！username={}", username, e);
            return ResponseMsg.fail("系统异常，请稍后重试！");
        }
    }

    /**
     * 登出
     * 
     * @param request
     *            请求
     * @param response
     *            响应
     * @return 返回成功消息
     */
    @ResponseBody
    @RequestMapping("logout")
    public ResponseMsg logout(HttpServletRequest request, HttpServletResponse response) {
        UserContextHelper.logout(request, response);
        return ResponseMsg.success();
    }
}
