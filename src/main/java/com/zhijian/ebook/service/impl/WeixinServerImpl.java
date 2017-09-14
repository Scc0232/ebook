package com.zhijian.ebook.service.impl;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.dao.AccessTokenMapper;
import com.zhijian.ebook.entity.AccessToken;
import com.zhijian.ebook.service.WeixinServer;
import com.zhijian.ebook.util.StringConsts;
import com.zhijian.ebook.util.WechatConfig;
import com.zhijian.ebook.util.WechatUtils;


import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
@Service
public class WeixinServerImpl implements WeixinServer {

	private static Logger log = LoggerFactory.getLogger(WeixinServerImpl.class);

	private final static String openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";

	private final static String userinfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	@Autowired
	private AccessTokenMapper accessTokenMapper;

	@Autowired
	private UserMapper userMapper;

	@SuppressWarnings({ "resource" })
	@Override
	public String getOpenId(String code) {

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";
		url = url.replace("AppId", WechatConfig.APPID).replace("AppSecret", WechatConfig.APPSECRECT).replace("CODE", code);
		HttpGet get = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();

		String openid = "";
		try {
			HttpResponse response = httpclient.execute(get);
			String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
			com.alibaba.fastjson.JSONObject jsonTexts = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(jsonStr);
			if (jsonTexts.get("openid") != null) {
				openid = jsonTexts.get("openid").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return openid;
	}

	@Override
	public String getOpenIds(String code) {

		String url = openid_url.replace("AppId", WechatConfig.APPID).replace("AppSecret", WechatConfig.APPSECRECT).replace("CODE", code);
		JSONObject jsonObject = WechatUtils.httpRequest(url, "GET", null);
		String openid = null;
		// 如果请求成功
		if (null != jsonObject) {
			try {
				openid = jsonObject.getString("openid").toString();
			} catch (Exception e) {
				// 获取openid失败
				openid = null;
				log.error("获取openid失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return openid;
	}

	@Override
	public void refreshToken() {
		// 清空AccessToken表
		accessTokenMapper.deleteByExample(null);

		AccessToken accessToken = null;
		accessToken = WechatUtils.getAccessToken(WechatConfig.APPID, WechatConfig.APPSECRECT);
		accessToken.setUpdateTime(new Date());
		accessTokenMapper.insert(accessToken);
	}

	@Override
	public String getAccessToken() {
		return accessTokenMapper.selectByExample(null).get(0).getToken();
	}

	@Override
	public User insertUser(String openid) {
		String url = userinfo_url.replace("ACCESS_TOKEN", getAccessToken()).replaceAll("OPENID", openid);
		JSONObject jsonObject = WechatUtils.httpRequest(url, "GET", null);
		User user = null;
		// 如果请求成功
		if (null != jsonObject) {
			try {
				user = new User();
				user.setId(StringConsts.randomFileName());
				user.setUsername(jsonObject.getString("openid").toString());
				user.setPetName(jsonObject.getString("nickname").toString());
				user.setSex(jsonObject.getString("sex").toString());
				user.setIcon(jsonObject.getString("headimgurl").toString());
				user.setIsValid(Boolean.TRUE);
				userMapper.insert(user);
			} catch (Exception e) {
				// 获取userinfo失败
				user = null;
				log.error("获取userinfo失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return user;
	}

}
