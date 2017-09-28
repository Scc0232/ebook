package com.zhijian.ebook.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhijian.ebook.entity.AccessToken;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 公众平台通用接口工具类
 * 
 * @author liuyq
 * @date 2013-08-09
 */
public class WechatUtils {
	private static Logger log = LoggerFactory.getLogger(WechatUtils.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresin(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	// 微信接入第三方服务器token
	public static final String TOKEN = "zhijianebook";

	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		if (signature == null) {
			return false;
		}
		// 将token、timestamp、nonce组成数组
		String[] paramArr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(paramArr);

		// 将三个参数字符串拼接成一个字符串
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串后进行sha1加密

			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 返回加密后的小写字符串
		return signature.equalsIgnoreCase(ciphertext);
	}

	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (byte element : byteArray) {
			strDigest += byteToHexStr(element);
		}
		return strDigest;
	}

	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 微信支付签名算法sign
	 * 
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding, SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String key = WechatConfig.KEYSECRECT;
		sb.append("key=" + key);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	public static boolean checkSign(String xmlString) {
		Map<String, String> map = null;
		try {
			map = WechatCore.xmlToMap(xmlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String signFromAPIResponse = map.get("sign").toString();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		String signForAPIResponse = getSign(map);
		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);
			return false;
		}
		System.out.println("恭喜，API返回的数据签名验证通过!!!");
		return true;
	}

	public static String getSign(Map<String, String> map) {
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
			signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
		}
		signParams.remove("sign");
		String sign = createSign("UTF-8", signParams);
		return sign;
	}

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException {
//		Map<String, String> params = new HashMap<String, String>();
//        params.put("appid",WechatConfig.APPID);
//        params.put("secret",WechatConfig.APPSECRECT);
//        params.put("access_token", "8Ygpu-xPHgsGVFpvWkjTl1r3aiQtL_hYV8Ras4fOiUhGHd-zO_CEJGYWCK1jOd6Uu0iCmb_l8lPrYWLJ58KfxqEGVxWlT08pJ06M82WG9JrxBtTeauPqDx52S_M8DZxfFKGcAEAPUQ");
//		String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
//		Map<String, String> map = new HashMap<String, String>();
//		String xml = HttpXmlClient.post(url,params); 
//		JSONObject jsonMap  = JSONObject.fromObject(xml);
//		Iterator<String> it = jsonMap.keys();  
//		jsonMap  = JSONObject.fromObject(xml);
//        it = jsonMap.keys();  
//        while(it.hasNext()) {  
//            String key = (String) it.next();  
//            String u = jsonMap.get(key).toString();
//            map.put(key, u);  
//        }
//        String jsapi_ticket = map.get("ticket");
//        System.out.println("jsapi_ticket=" + jsapi_ticket);
 
        //获取签名signature
//        String noncestr = UUID.randomUUID().toString();
//        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
//        noncestr = "201ac188-3484-4600-906d-7285b8c201bb";
//        timestamp = "1506437543";
//        
//        String url="http://mp.weixin.qq.com";
//         String jsapi_ticket = null;
//		//"jsapi_ticket=" + jsapi_ticket +
//        String str = "jsapi_ticket=" + jsapi_ticket  +
//                "&noncestr=" + noncestr +
//                "&timestamp=" + timestamp +
//                "&url=" + url;
//        System.out.println(str);
//        //sha1加密
//        String signature = SHA1(str);
//        System.out.println("noncestr=" + noncestr);
//        System.out.println("timestamp=" + timestamp);
//        System.out.println("signature=" + signature);
//        //最终获得调用微信js接口验证需要的三个参数noncestr、timestamp、signature
//        
//        Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("noncestr", noncestr);
//		paramMap.put("timestamp", timestamp);
//		paramMap.put("jsapi_ticket", "null");
//		paramMap.put("url", url);
//		paramMap = WechatCore.paraFilter(paramMap);
//		String result = WechatCore.createLinkString(paramMap);
//		System.out.println(result);
//		System.out.println("result:"+SHA1(result));
//		
//		System.out.println(new BookClassServiceImpl().findSign());
		
		
//		String accessToken = weixinServer.getAccessToken();
		String accessToken = "tFTw_H58-GBhq7x9aDEOAzuZM06ZhBH5TDZ1OKiUdp5rTUQhcABgqJvyHaBugeBtUA_aZL5Wml0kqy9nePDozm67tKlhAZlp6fu-FjIAKNjjJmYhT551rADwJc5SrtdlLYKcAIAVIU";
		String appid = WechatConfig.APPID;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", WechatConfig.APPID);
		params.put("secret", WechatConfig.APPSECRECT);
		params.put("access_token", accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		Map<String, String> map = new HashMap<String, String>();
		String xml = HttpXmlClient.get(url.replace("ACCESS_TOKEN", accessToken));
		log.info(xml);
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
		url = "http://mp.weixin.qq.com";
		String signature = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("noncestr", noncestr);
		paramMap.put("timestamp", timestamp);
		paramMap.put("jsapi_ticket", jsapi_ticket);
		paramMap.put("url", url);
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

		System.out.println(param);
		
	}
	
	public static String SHA1(String str) throws UnsupportedEncodingException {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.reset();
            digest.update(str.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
	
}