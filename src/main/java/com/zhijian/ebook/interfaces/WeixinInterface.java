package com.zhijian.ebook.interfaces;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.zhijian.ebook.util.WechatUtils;

@Controller
@RequestMapping(value="weixin")
public class WeixinInterface {
	
	@Autowired
	private WeixinServer weixinServer;
	
	private static final Logger log = LogManager.getLogger();


	@ResponseBody
	@RequestMapping(value="unlogin/link",method=RequestMethod.GET)
	public void link(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
				String signature = request.getParameter("signature"); 
				// 时间戳
				String timestamp = request.getParameter("timestamp");
				// 随机数
				String nonce = request.getParameter("nonce");
				// 随机字符串	
				String echostr = request.getParameter("echostr");

				PrintWriter out = response.getWriter();
				
				log.error(signature+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				log.error(timestamp+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				log.error(nonce+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				log.error(echostr+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				
				// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
				if (WechatUtils.checkSignature(signature, timestamp, nonce)) {
					out.print(echostr);
				}
				out.close();
				out = null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="unlogin/getopenId",method=RequestMethod.GET)
	public String getopenId(String code ) {
		
		String openid = null;
		
		try {
			
			openid = weixinServer.getOpenId(code);
			
		} catch (Exception e) {
		}
		
		return openid;
	}
	
	
	
}
