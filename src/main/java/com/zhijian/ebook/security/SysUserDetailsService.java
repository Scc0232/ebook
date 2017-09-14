package com.zhijian.ebook.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.SysUserDetails;

/**
 * 将SysUserDetails提供给security使用
 * 
 * @author zhanghua on Oct 22, 2015
 *
 */
public class SysUserDetailsService implements UserDetailsService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("----------security框架根据用户名查找用户信息-----------");
        log.debug("----------security框架接收到的用户名为【{}】-----------", username);
        SysUserDetails suser;
        try {
            suser = userService.findSysUserDetails(username);
            log.debug(suser.getUsername() + " : " + suser.getPassword());
            log.debug("----------security框架查到的用户为【{}】-----------", suser);

            // 这里需要新建一个用户，不然后面向前台传输用户信息时，会删除用户密码。导致验证不成功。
            User user = new User(suser.getUsername(), suser.getPassword(), suser.isEnabled(),
                    suser.isAccountNonExpired(), suser.isCredentialsNonExpired(), suser.isAccountNonLocked(),
                    suser.getAuthorities());
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

}
