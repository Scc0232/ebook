package com.zhijian.ebook.service;

import com.zhijian.ebook.base.entity.User;

public interface WeixinServer {

	String getOpenId(String code);
	
	String getOpenIds(String code);

	void refreshToken();
	
	String getAccessToken();
	
	User insertUser(String openid);

	User getUserInfo(User user);

	String createQRcode();

}
