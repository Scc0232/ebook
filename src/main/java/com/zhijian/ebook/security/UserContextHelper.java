package com.zhijian.ebook.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * 
 * 用户上下文 助手
 * 
 * @author Administrator
 *
 */
public class UserContextHelper {

    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    /**
     * 获得安全框架上下文
     * 
     * @return
     */
    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    /**
     * 获得身份认证
     * 
     * @return
     */
    public static Authentication getAuthentication() {
        return getSecurityContext() == null ? null : getSecurityContext().getAuthentication();
    }

    /**
     * 获得角色列表
     * 
     * @return
     */
    public static Collection<? extends GrantedAuthority> getGrantedAuthorityList() {
        return getAuthentication() == null ? null : getAuthentication().getAuthorities();
    }

    public static Object getCredentials() {
        return getAuthentication() == null ? null : getAuthentication().getCredentials();
    }

    /**
     * 获得详细信息
     * 
     * @return
     */
    public static Object getDetails() {
        return getAuthentication() == null ? null : getAuthentication().getDetails();
    }

    /**
     * 获得web认证的详细信息
     * 
     * @return
     */
    public static WebAuthenticationDetails getWebAuthenticationDetails() {
        Object obj = getDetails();
        if (null != obj && (obj instanceof WebAuthenticationDetails)) {
            WebAuthenticationDetails wad = (WebAuthenticationDetails) obj;
            return wad;
        }
        return null;
    }

    /**
     * 获得远程端IP地址
     * 
     * @return
     */
    public static String getRemoteAddress() {
        return getWebAuthenticationDetails() == null ? null : getWebAuthenticationDetails().getRemoteAddress();
    }

    /**
     * 获得SessionID
     * 
     * @return
     */
    public static String getSessionId() {
        return getWebAuthenticationDetails() == null ? null : getWebAuthenticationDetails().getSessionId();
    }

    /**
     * 获得当前用户超类Object
     * 
     * @return
     */
    public static Object getPrincipal() {
        return getAuthentication() == null ? null : getAuthentication().getPrincipal();
    }

    /**
     * 获得当前用户,SpringSecurity的User
     * 
     * @return
     */
    public static User getUser() {
        Object obj = getPrincipal();
        if (null != obj && (obj instanceof User)) {
            return (User) obj;
        }
        return null;
    }

    /**
     * 获取当前用户的用户名
     * 
     * @return
     */
    public static String getUsername() {
        return getUser() == null ? null : getUser().getUsername();
    }

    /**
     * 获取当前登录用户的用户名
     * 
     * @return
     */
    public static String getName() {
        return getAuthentication() == null ? null : getAuthentication().getName();
    }

    public static String getRole() {
        return null;
    }

    /**
     * 获取当前用户是否已经认证通过 true:已通过 false:未通过
     * 
     * @return
     */
    public static boolean isAuthenticated() {
        return getAuthentication() == null ? null : getAuthentication().isAuthenticated();
    }

    /**
     * 登录到本系统
     * 
     * @param userDetails
     * @param session
     */
    public static void login(UserDetails userDetails, HttpSession session) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute(SPRING_SECURITY_CONTEXT, securityContext);
    }

    /**
     * 登出
     * 
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        List<LogoutHandler> handlers = new ArrayList<>();
        // 实例化Spring Security LogoutHandler
        handlers.add(new CookieClearingLogoutHandler());
        handlers.add(new SecurityContextLogoutHandler());
        Authentication auth = UserContextHelper.getSecurityContext().getAuthentication();

        for (LogoutHandler handler : handlers) {
            handler.logout(request, response, auth);
        }
    }
}
