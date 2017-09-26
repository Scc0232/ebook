package com.zhijian.ebook.util.CLSMS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author tianyh 
 * @Description:HTTP 请求
 */
public class ChuangLanSmsUtil {
	
	/**
	 * 
	 * @author tianyh 
	 * @Description 
	 * @param path
	 * @param postContent
	 * @return String 
	 * @throws
	 */
	public static String sendSmsByPost(String path, String postContent) {
		URL url = null;
		try {
			url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
			httpURLConnection.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");
			
//			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//			printWriter.write(postContent);
//			printWriter.flush();

			httpURLConnection.connect();
			OutputStream os=httpURLConnection.getOutputStream();
			os.write(postContent.getBytes("UTF-8"));
			os.flush();
			
			StringBuilder sb = new StringBuilder();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				// 开始获取数据
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static SmsSendResponse sendMessage(String content, String phone) {
//		SmsSendRequest smsSingleRequest = new SmsSendRequest(SMSType.ACCOUNT, SMSType.PASSWORD, content, phone, "true");
//		String requestJson = JSON.toJSONString(smsSingleRequest);
//		String response = ChuangLanSmsUtil.sendSmsByPost(SMSType.SEND_URL, requestJson);
//		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
//		return smsSingleResponse;
		return JSON.parseObject(ChuangLanSmsUtil.sendSmsByPost(SMSType.SEND_URL, JSON.toJSONString(new SmsSendRequest(SMSType.ACCOUNT, SMSType.PASSWORD, content, phone, "true"))),SmsSendResponse.class);
	}
	
	public static SmsBalanceResponse selectBlance() {
//		SmsBalanceRequest smsBalanceRequest=new SmsBalanceRequest(SMSType.ACCOUNT, SMSType.PASSWORD);
//        String requestJson = JSON.toJSONString(smsBalanceRequest);
//		String response = ChuangLanSmsUtil.sendSmsByPost(SMSType.QUERY_URL, requestJson);
//		SmsBalanceResponse smsVarableResponse = JSON.parseObject(response, SmsBalanceResponse.class);
//		return smsVarableResponse;
		return JSON.parseObject(ChuangLanSmsUtil.sendSmsByPost(SMSType.QUERY_URL, JSON.toJSONString(new SmsBalanceRequest(SMSType.ACCOUNT, SMSType.PASSWORD))),SmsBalanceResponse.class);
	}

}
