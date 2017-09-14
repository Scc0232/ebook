package com.zhijian.ebook.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhijian.ebook.service.WeixinServer;


public class RefreshTokenJob {

	private static final Logger logger = LoggerFactory.getLogger(RefreshTokenJob.class);
	
	@Autowired
	private WeixinServer weixinServer;
	
	public void refreshToken() {
		try {
			weixinServer.refreshToken();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
}
