package com.zhijian.ebook.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.DictMapper;
import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.entity.DictExample;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.dao.BookClassMapper;
import com.zhijian.ebook.dao.BookMapper;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookClassExample;
import com.zhijian.ebook.entity.BookExample;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.BookClassService;
import com.zhijian.ebook.service.WeixinServer;
import com.zhijian.ebook.util.HttpXmlClient;
import com.zhijian.ebook.util.MD5;
import com.zhijian.ebook.util.UUIDGenerator;
import com.zhijian.ebook.util.WechatConfig;
import com.zhijian.ebook.util.WechatCore;
import com.zhijian.ebook.util.WechatUtils;

import net.sf.json.JSONObject;

@Service
public class BookClassServiceImpl implements BookClassService {

	private static final Logger log = LogManager.getLogger();

	public static String ORDERURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	@Autowired
	private BookClassMapper bookclassMapper;

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private WeixinServer weixinServer;

	@Override
	public Map<String, List<?>> selectAll() {
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		List<BookClass> classlist = null;
		BookClassExample example = new BookClassExample();
		example.setOrderByClause(" create_time asc");
		classlist = bookclassMapper.selectByExample(example);
		map.put("bookclass", classlist);
		BookExample bookExample = new BookExample();
		BookExample.Criteria criteria = bookExample.createCriteria();
		criteria.andClassIdEqualTo(classlist.get(0).getId());
		List<Book> booklist = bookMapper.selectByExample(bookExample);
		map.put("kaoyan", booklist);

		return map;
	}

	@Override
	public List<Dict> findBanner() {
		DictExample dictExample = new DictExample();
		dictExample.setOrderByClause("dict_name asc");

		return dictMapper.selectByExample(dictExample);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object prePay(String orderNo, String fee, String ip) {
		User user = null;
		try {
			user = userService.findUserByUsername(UserContextHelper.getUsername());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		String openId = user.getUsername();
//		OrderExample example = new OrderExample();
//		OrderExample.Criteria criteria = example.createCriteria();
//		criteria.andOrderNoEqualTo(orderNo);
//		criteria.andUseridEqualTo(user.getId());
//		criteria.andIsValidEqualTo(true);
//		List<Order> listOrder = orderMapper.selectByExample(example);

		Map<String, String> resultsMap = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", WechatConfig.APPID);
		paramMap.put("mch_id", WechatConfig.MCHID);
		paramMap.put("nonce_str", UUIDGenerator.generator().toUpperCase());
		paramMap.put("body", "订单支付");
		paramMap.put("out_trade_no", String.valueOf(orderNo));
		paramMap.put("fee_type", "CNY");
		paramMap.put("total_fee", Math.round(Double.parseDouble(fee) * 100) + "");
		paramMap.put("spbill_create_ip", ip);
		paramMap.put("notify_url", WechatConfig.NOTIFYURL);
		paramMap.put("trade_type", "JSAPI");
		paramMap.put("openid", openId);
		paramMap.put("attach", "2");// 支付类型，1充值，2单个停车费，3补缴
		paramMap = WechatCore.paraFilter(paramMap);
		String result = WechatCore.createLinkString(paramMap);
		result += "&key=" + WechatConfig.KEYSECRECT;
		try {
			result = MD5.getMessageDigest(result.getBytes("utf-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		paramMap.put("sign", result);
		result = WechatCore.mapToXml(paramMap);
		log.info("统一订单提交前参数:" + result);
		PostMethod method = null;
		try {
			method = new PostMethod(WechatConfig.ORDERURL);
			method.setRequestContentLength(result.length());
			method.setRequestBody(result);
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			int state = httpClient.executeMethod(method);
			result = method.getResponseBodyAsString();
			log.info("微信传回参数" + result);
			log.info("返回参数" + state);
			if (state == 200) {
				paramMap = WechatCore.xmlToMap(result);
				log.info("微信传回参数" + paramMap.toString());
				String signData = paramMap.get("sign");
				paramMap = WechatCore.paraFilter(paramMap);
				result = WechatCore.createLinkString(paramMap);
				result += "&key=" + WechatConfig.KEYSECRECT;
				try {
					result = MD5.getMessageDigest(result.getBytes("utf-8")).toUpperCase();
				} catch (UnsupportedEncodingException e1) {
				}
				if (!result.equals(signData)) {
					log.info("");
					return null;
				}
				String return_code = paramMap.get("result_code");
				log.info("微信传回参数code" + return_code);
				if (return_code.equals("SUCCESS")) {
					log.info("回复参数" + resultsMap.toString());
					String prepay_id = paramMap.get("prepay_id");
					resultsMap.put("appId", WechatConfig.APPID);
					resultsMap.put("package", "prepay_id=" + prepay_id);
					resultsMap.put("nonceStr", UUIDGenerator.generator().toUpperCase());
					resultsMap.put("signType", "MD5");
					resultsMap.put("timeStamp", Long.toString(System.currentTimeMillis() / 1000));
					resultsMap = WechatCore.paraFilter(resultsMap);
					result = WechatCore.createLinkString(resultsMap) + "&key=" + WechatConfig.KEYSECRECT;
					result = MD5.getMD5ofStr(result).toUpperCase();
					resultsMap.put("paySign", result);
					resultsMap.put("payment_id", orderNo);
					resultsMap.put("money", fee);
					log.info("回复参数" + resultsMap.toString());
					return resultsMap;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			// logger.error("数据请求异常" , e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return resultsMap;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object findSign(String targetUrl) {
		int index = targetUrl.indexOf("#");
        if (index > 0) {
            targetUrl = targetUrl.substring(0, index);
            //targetUrl = targetUrl.toLowerCase();
            log.info("targetUrl：" + targetUrl);
        }
		String accessToken = weixinServer.getAccessToken();
		String appid = WechatConfig.APPID;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", WechatConfig.APPID);
		params.put("secret", WechatConfig.APPSECRECT);
		params.put("access_token", accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		Map<String, String> map = new HashMap<String, String>();
		String xml = HttpXmlClient.get(url.replace("ACCESS_TOKEN", accessToken));
		JSONObject jsonMap = JSONObject.fromObject(xml);
		Iterator<String> it = jsonMap.keys();
		jsonMap = JSONObject.fromObject(xml);
		it = jsonMap.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			String u = jsonMap.get(key).toString();
			map.put(key, u);
		}
		String jsapi_ticket = map.get("ticket");
		System.out.println("jsapi_ticket=" + jsapi_ticket);

		String noncestr = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
//		url = "http://www.ebenshu.cn";
		String signature = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("noncestr", noncestr);
		paramMap.put("timestamp", timestamp);
		paramMap.put("jsapi_ticket", jsapi_ticket);
		paramMap.put("url", targetUrl);
		paramMap = WechatCore.paraFilter(paramMap);
		String result = WechatCore.createLinkString(paramMap);
		System.out.println(result);
		try {
			signature = WechatUtils.SHA1(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("appid", appid);
		param.put("noncestr", noncestr);
		param.put("timestamp", timestamp);
		param.put("signature", signature);
		return param;
	}

}
