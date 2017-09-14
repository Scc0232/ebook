package com.zhijian.ebook.base.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.entity.Role;
import com.zhijian.ebook.base.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

    private static final String SESSION_ROLE_KEY = "SESSION_ROLE_KEY";

    @Override
    public void setRole(Role role, HttpSession session) {
        session.setAttribute(SESSION_ROLE_KEY, role);
    }

    @Override
    public Role getRole(HttpSession session) {
        return (Role) session.getAttribute(SESSION_ROLE_KEY);
    }

}
