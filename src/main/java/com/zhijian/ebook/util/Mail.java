package com.zhijian.ebook.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 发邮件
 * @author conlin
 *
 */
public class Mail {
	public static final String sendPass = "yumi0311";// 发件人密码
	private static final Logger logger = LogManager.getLogger();
	private MimeMessage mimeMsg;
	private Session session;
	private Properties props;
	private String username;
	private String password;
	private Multipart mp;

	public Mail(String smtp) {
		try {
			setSmtpHost(smtp);
			createMimeMessage();
		} catch (Exception e) {
			logger.info("设置系统属性异常！",e);
		}
	}
	
	/**
	 * 设置系统属性
	 * @param hostName
	 */
	public void setSmtpHost(String hostName) throws Exception{
		logger.info("设置系统属性：mail.smtp.host={}", hostName);
		if (props == null) {
			props = System.getProperties();
		}
		props.put("mail.smtp.host", hostName);
	}

	public boolean createMimeMessage() throws Exception{
		try {
			logger.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			logger.error("获取邮件会话错误！", e);
			return false;
		}
		logger.info("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session);
			mp = new MimeMultipart();
			return true;
		} catch (Exception e) {
			logger.error("创建MIME邮件对象失败！", e);
			return false;
		}
	}

	/**
	 * 定义SMTP是否需要验证 
	 * @param need
	 */
	public void setNeedAuth(boolean need) throws Exception{
		logger.info("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	public void setNamePass(String name, String pass) throws Exception{
		username = name;
		password = pass;
	}

	/**
	 * 定义邮件主题
	 * @param mailSubject  邮件主题
	 * @return
	 */
	public boolean setSubject(String mailSubject) throws Exception{
		logger.info("定义邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			logger.error("定义邮件主题发生错误！", e);
			return false;
		}
	}

	/**
	 * 定义邮件正文
	 * @param mailBody  邮件正文内容
	 * @return
	 */
	public boolean setBody(String mailBody) throws Exception{
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=GBK");
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			logger.error("定义邮件正文时发生错误！", e);
			return false;
		}
	}

	/**
	 * 设置发信人
	 * @param from  发件人账号
	 * @return
	 */
	public boolean setFrom(String from) throws Exception{
		logger.info("设置发信人！from={}", from);
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 定义收信人
	 * @param to  收件人邮箱地址
	 * @return
	 */
	public boolean setTo(String to) throws Exception{
		if (to == null)
			return false;
		logger.info("定义收信人！to={}", to);
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 定义抄送人
	 * @param copyto  抄送到的用户邮箱地址
	 * @return
	 */
	public boolean setCopyTo(String copyto) throws Exception{
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 发送邮件
	 * @return
	 */
	public boolean sendOut() throws Exception{
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			logger.info("邮件发送中....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg,
					mimeMsg.getAllRecipients());
			logger.info("发送成功！");
			transport.close();
			return true;
		} catch (Exception e) {
			logger.error("邮件失败！", e);
			return false;
		}
	}

	/**
	 * 调用sendOut方法完成发送 
	 * @param smtp  smtp服务器
	 * @param from   邮件显示名称
	 * @param to   收件人的邮件地址，必须是真实地址
	 * @param copyto  抄送人邮件地址
	 * @param subject  邮件标题
	 * @param content  邮件内容
	 * @param username 发件人真实的账户名
	 * @param password  发件人账号密码
	 * @return
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password) throws Exception {
		logger.info("发送邮件-==to = {},copyto={},subject={},content={}",to,copyto,subject,content);
		Mail theMail = new Mail(smtp);
		theMail.setNeedAuth(true); // 验证
		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);
		if (!theMail.sendOut())
			return false;
		return true;
	}
}
