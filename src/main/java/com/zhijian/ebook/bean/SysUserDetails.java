package com.zhijian.ebook.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 系统用户详细信息
 * 
 * @author Administrator
 *
 */
public class SysUserDetails extends User {

    private static final long serialVersionUID = -8650609656509839775L;

    public SysUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }

    public SysUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 用户拥有的权限
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    /**
     * 密码
     */
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * 用户名
     */
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * 用户账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    /**
     * 账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    /**
     * 用户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

}
