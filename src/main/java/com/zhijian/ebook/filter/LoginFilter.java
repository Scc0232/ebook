package com.zhijian.ebook.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zhijian.ebook.security.UserContextHelper;


public class LoginFilter implements Filter {

    /**
     * 只过滤包含这个URL的请求
     */
    private String filterURL = "/login/";

    /**
     * Spring Security Session key
     */
    private String sessionKey = UserContextHelper.SPRING_SECURITY_CONTEXT;

    /**
     * 未登录 重定向地址
     */
    private String redirectURL = "/response/notLoggedIn.do";

    @Override
    public void destroy() {

    }

    /**
     * 是否开始过滤
     * 
     * @param requestURL
     * @return
     */
    private boolean isDoFilter(StringBuffer requestURL) {
        return requestURL.lastIndexOf(filterURL) != -1;
    }

    /**
     * 当前是否登录
     * 
     * @param session
     * @return
     */
    private boolean isLogined(HttpSession session) {
        return session.getAttribute(sessionKey) != null;
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;

        if (isDoFilter(request.getRequestURL())) {
            // 获得Session
            HttpSession session = request.getSession();
            if (!isLogined(session)) {// 未登录，跳转至指定页面
                HttpServletResponse response = (HttpServletResponse) arg1;
                response.sendRedirect(request.getContextPath() + redirectURL);
                return;
            }
        }

        arg2.doFilter(arg0, arg1);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
