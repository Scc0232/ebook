package com.zhijian.ebook.enums;

/**
 * 超级用户, 这些用户可以获得最高权限
 * 
 * @author zhanghua on 15/11/06
 *
 */
public enum SuperUser {

    ROOT("root"), ADMIN("admin");

    private String username;

    private SuperUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
