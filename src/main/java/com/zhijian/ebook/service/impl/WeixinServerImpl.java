package com.zhijian.ebook.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.dao.AccessTokenMapper;
import com.zhijian.ebook.entity.AccessToken;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.WeixinServer;
import com.zhijian.ebook.util.StringConsts;
import com.zhijian.ebook.util.WechatConfig;
import com.zhijian.ebook.util.WechatUtils;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
@Service
public class WeixinServerImpl implements WeixinServer {

	private static Logger log = LogManager.getLogger();

	private final static String openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";

	private final static String userinfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	private final static String QR_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	private final static String GET_QR_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	private final static String LONG2SHORT = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
	

	@Autowired
	private AccessTokenMapper accessTokenMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@SuppressWarnings({ "resource" })
	@Override
	public String getOpenId(String code) {

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=AppId&secret=AppSecret&code=CODE&grant_type=authorization_code";
		url = url.replace("AppId", WechatConfig.access().getAPPID()).replace("AppSecret", WechatConfig.access().getAPPSECRECT()).replace("CODE", code);
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

		String url = openid_url.replace("AppId", WechatConfig.access().getAPPID()).replace("AppSecret", WechatConfig.access().getAPPSECRECT()).replace("CODE", code);
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
	public String createQRcode() {

		String url = QR_URL.replace("ACCESS_TOKEN", getAccessToken());
		JSONObject scene = new JSONObject();
		scene.put("scene_str", UserContextHelper.getUsername());
		JSONObject paramMap = new JSONObject();
		paramMap.put("action_name", "QR_LIMIT_SCENE");
		paramMap.put("scene", scene.toString());


		JSONObject jsonObject = WechatUtils.httpRequest(url, "POST", paramMap.toString());
		if (jsonObject.containsKey("ticket")) {
			url = GET_QR_URL.replace("TICKET", jsonObject.getString("ticket"));
			
			
//			JSONObject jsonObject = WechatUtils.httpRequest(LONG2SHORT.replace(ACCESS_TOKEN, getAccessToken()), "POST", paramMap.toString());
		}
		return url;
	}

	@Override
	public void refreshToken() {
		// 清空AccessToken表
		accessTokenMapper.deleteByExample(null);

		AccessToken accessToken = null;
		accessToken = WechatUtils.getAccessToken(WechatConfig.access().getAPPID(), WechatConfig.access().getAPPSECRECT());
		accessToken.setUpdateTime(new Date());
		accessTokenMapper.insert(accessToken);
	}

	@Override
	public String getAccessToken() {
		List<AccessToken> list = accessTokenMapper.selectByExample(null);
		if (list == null || list.size() == 0) {
			refreshToken();
			list = accessTokenMapper.selectByExample(null);
		}
		AccessToken accessToken = list.get(0);
		String token = accessToken.getToken();
		long distance = (new Date().getTime() - accessToken.getUpdateTime().getTime()) / 1000;
		if (StringUtils.isBlank(token) || distance > accessToken.getExpiresin()) {
			refreshToken();
			token = accessTokenMapper.selectByExample(null).get(0).getToken();
		}
		return token;
	}

	@Override
	public User insertUser(String openid) {
		String url = userinfo_url.replace("ACCESS_TOKEN", getAccessToken()).replaceAll("OPENID", openid);
		JSONObject jsonObject = WechatUtils.httpRequest(url, "GET", null);
		User user = new User();
		// 如果请求成功
		if (null != jsonObject && jsonObject.containsKey("subscribe")) {
			int subscribe = jsonObject.getInt("subscribe");
			if (subscribe == 1) {
				String userid = StringConsts.randomFileName();
				user.setId(userid);
				user.setUsername(jsonObject.getString("openid").toString());
				String nickname = jsonObject.getString("nickname").toString();
				if (StringUtils.isNotBlank(nickname)) {
					user.setPetName(jsonObject.getString("nickname").toString());
				} else {
					user.setPetName("");
				}
				user.setSex(jsonObject.getString("sex").toString());
				user.setIcon(jsonObject.getString("headimgurl").toString());
				user.setCreateTime(new Date());
				user.setModifyTime(new Date());
				user.setIsValid(Boolean.TRUE);
				user.setAccredit(1);
				user.setBlance((double) 0);
				userService.addUser(user, StringConsts.USER_ROLE_ID);
			} else {
				String userid = StringConsts.randomFileName();
				user.setId(userid);
				user.setUsername(openid);
				user.setPetName(openid.substring(openid.length() - 4));
				user.setIcon(null);
				user.setCreateTime(new Date());
				user.setModifyTime(new Date());
				user.setIsValid(Boolean.TRUE);
				user.setAccredit(1);
				user.setBlance((double) 0);
				userService.addUser(user, StringConsts.USER_ROLE_ID);
			}

			// log.error("获取userinfo失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
			// jsonObject.getString("errmsg"));
		} else {
			log.error("获取userinfo失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			user.setRemark(jsonObject.getString("errmsg"));
			return user;
		}

		return user;
	}

	@Override
	public User getUserInfo(User user) {
		String url = userinfo_url.replace("ACCESS_TOKEN", getAccessToken()).replaceAll("OPENID", user.getUsername());
		JSONObject jsonObject = WechatUtils.httpRequest(url, "GET", null);
		if (null != jsonObject && jsonObject.containsKey("subscribe")) {
			int subscribe = jsonObject.getInt("subscribe");
			if (subscribe == 1) {
				String nickname = jsonObject.getString("nickname").toString();
				if (StringUtils.isNotBlank(nickname)) {
					user.setPetName(jsonObject.getString("nickname").toString());
				} else {
					user.setPetName("");
				}
				user.setSex(jsonObject.getString("sex").toString());
				user.setIcon(jsonObject.getString("headimgurl").toString());
				userMapper.updateByPrimaryKeySelective(user);
			}
		}
		return user;
	}

}
