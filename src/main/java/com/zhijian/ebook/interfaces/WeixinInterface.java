package com.zhijian.ebook.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.service.WeixinServer;
import com.zhijian.ebook.util.process.WechatProcess;

@Controller
@RequestMapping(value = "weixin")
public class WeixinInterface {

	@Autowired
	private WeixinServer weixinServer;

	private static final Logger log = LogManager.getLogger();

	@ResponseBody
	@RequestMapping(value = "unlogin/link", method = RequestMethod.GET)
	public void getlink(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		/** 读取接收到的xml消息 */
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		String xml = sb.toString(); // 接收到微信端发送过来的xml数据
		System.out.println(xml);
		log.info(">>>>>>>>>>>" + xml + "<<<<<<<<<<<<<");
		String result = "";
		/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
		String echostr = request.getParameter("echostr");
		if (echostr != null && echostr.length() > 1) {
			result = echostr;
		} else {
			// 正常的微信处理流程
			result = new WechatProcess().processWechatMag(xml);
			log.info("<<<<<<<<<<<<<<<<<<<<<<<<" + xml + ">>>>>>>>>>>>>>>>>>>>>");
		}

		try {
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// // 微信加密签名
		// String signature = request.getParameter("signature");
		// // 时间戳
		// String timestamp = request.getParameter("timestamp");
		// // 随机数
		// String nonce = request.getParameter("nonce");
		// // 随机字符串
		// String echostr = request.getParameter("echostr");
		//
		// PrintWriter out = response.getWriter();
		//
		// log.error(signature+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		// log.error(timestamp+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		// log.error(nonce+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		// log.error(echostr+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		//
		// // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		// if (WechatUtils.checkSignature(signature, timestamp, nonce)) {
		// out.print(echostr);
		// }
		// out.close();
		// out = null;
	}

	@ResponseBody
	@RequestMapping(value = "unlogin/link", method = RequestMethod.POST)
	public void postlink(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		getlink(request, response);
	}

	@ResponseBody
	@RequestMapping(value = "unlogin/getopenId", method = RequestMethod.GET)
	public String getopenId(String code) {
		String openid = null;
		try {
			openid = weixinServer.getOpenId(code);
		} catch (Exception e) {
		}
		return openid;
	}

	@ResponseBody
	@RequestMapping(value = "login/getQRcode", method = RequestMethod.GET)
	public String getQRcode() {
		String url = null;
		try {
			url = weixinServer.createQRcode();
		} catch (Exception e) {
		}
		return url;
	}
	
}
