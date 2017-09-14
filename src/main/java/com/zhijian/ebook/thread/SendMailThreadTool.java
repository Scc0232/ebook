package com.zhijian.ebook.thread;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class SendMailThreadTool {
	/**
	 * 线程池最高线程数量
	 */
	private static final int THREAD_POOL_MAX_SIZE = 10;
	private static final String smtp = "smtp.163.com";// smtp服务器
	private static final String from = "13817014292@163.com";// 邮件显示名称
	private static final String copyto = "";// 抄送人邮件地址
	private static final String subject = "收藏票据挂失提醒";// 邮件标题
	private static final String username = "13817014292@163.com";// 发件人真实的账户名
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final String sendNick = "票据精灵";//邮件发送者昵称
	private static final Logger log = LogManager.getLogger();
	
	private ExecutorService pool = null;
	
	
	@Autowired
	//private BillStoreMapper billStoreMapper;
	
	public SendMailThreadTool(){
		// 创建一个可重用固定线程数的线程池
		pool = Executors.newFixedThreadPool(THREAD_POOL_MAX_SIZE);
	}
	
	public void sendMailAndMsg(){/*
		try {
			if (pool == null) {
				//如果线程池未创建，则创建一个固定线程数据的线程池
				pool = Executors.newFixedThreadPool(THREAD_POOL_MAX_SIZE);
			}
			log.info("收藏票据挂失情况信息查询线程开始....");
			//已挂失的收藏票据
			List<MailMsgContent> result = billStoreMapper.findMailMsgContent();
			if (result != null && result.size() > 0) {
				for (MailMsgContent mailMsgContent : result) {
					pool.execute(new Runnable() {
						
						@Override
						public void run() {
							StringBuilder mailContent = new StringBuilder();
							mailContent.append("尊敬的票据精灵用户，您好！您在");
							mailContent.append(format.format(mailMsgContent.getStoreTime()));
							mailContent.append("收藏的票据:");
							mailContent.append(mailMsgContent.getBillNo());
							mailContent.append("已于");
							mailContent.append(mailMsgContent.getNoticeTime());
							mailContent.append("被");
							mailContent.append(mailMsgContent.getCourt());
							mailContent.append("公告挂失，具体详情请登录票据精灵查询！");
							String email = mailMsgContent.getEmail();
							//发送邮件
							try {
								if (StringUtils.isBlank(email)) {
									log.info("此用户的Email地址为空，忽略邮件发送......");
								} else {
									String nick=javax.mail.internet.MimeUtility.encodeText(sendNick);  
									Mail.sendAndCc(smtp, nick + "<" + from + ">", email, copyto, subject, mailContent.toString(), username, Mail.sendPass);
								}
								//账号名既是手机号
								String phone = mailMsgContent.getUserName();
								if (StringUtils.isBlank(phone)) {
									log.info("[收藏票据挂失提醒]用户没有填写联系手机号码，无法发送短信！");
								} else {
									log.info("发送短信通知用户所收藏的票据挂失情况,phone={},BillNo={},NoticeTime={},Court={}",phone,mailMsgContent.getBillNo(),mailMsgContent.getNoticeTime(),mailMsgContent.getNoticeTime(),mailMsgContent.getCourt());
									//发送短信通知用户所收藏的票据挂失情况
									SMSUtils.sendSMSByBillLose(phone, mailMsgContent.getBillNo(), mailMsgContent.getNoticeTime(), mailMsgContent.getCourt());
									log.info("短信发送成功！");
								}
							} catch (Exception e) {
								log.error("发邮件异常！",e);
							}
							log.info("收藏票据挂失情况信息查询邮件发送成功 userName = {}, email={}",mailMsgContent.getUserName(),email);
						}
					});
				}
			}
			//关闭线程池
			pool.shutdown();
		} catch (Exception e) {
			log.error("收藏票据挂失情况信息查询邮件发送异常！",e);
		}
	*/}
}
