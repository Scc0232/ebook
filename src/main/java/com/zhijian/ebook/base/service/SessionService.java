package com.zhijian.ebook.base.service;

import javax.servlet.http.HttpSession;

import com.zhijian.ebook.base.entity.Role;

/**
 * 
 * Session 服务层
 * 
 * @author Administrator
 *
 */
public interface SessionService {

    /**
     * 将角色放入会话中
     * 
     * @param role
     *            要放入的角色
     * @param session
     *            会话
     */
    void setRole(Role role, HttpSession session);

    /**
     * 从会话中获得角色
     * 
     * @param session
     *            会话
     * @return
     */
    Role getRole(HttpSession session);
}
