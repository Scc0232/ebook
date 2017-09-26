package com.zhijian.ebook.util.CLSMS;

/**
 * 创蓝短信类
 * 
 * @author liuheng on 2016年6月22日 下午6:44:00
 *
 */
public class SMSType {

	/**
	 * 发送短信
	 */
	public static final String SEND_URL = "http://smssh1.253.com/msg/send/json";
	
	/**
	 * 查询余额
	 */
	public static final String QUERY_URL = "http://smssh1.253.com/msg/balance/json";
	/**
	 * 发送国际验证码
	 */
	public static final String INTERNATIONAL_URL = "http://222.73.117.140:8044/mt";
	/**
	 * 应用账号
	 */
	public final static String ACCOUNT = "N8502302";

	/**
	 * 应用密码
	 */
	public final static String PASSWORD = "Hv90R738A";

	public final static String EB_TITLE = "【致笺e本书】";
	

	/**
	 * 发送短信验证码
	 * 
	 * @param title
	 *            项目名称
	 * @param captcha
	 *            验证码
	 * @return
	 */
	public static String templateSMS(String title, String captcha) {

		return  title + YOUR_VERIFICATION_CODE_IS + captcha + INPUT_TIME;
	}
	
	public static String defaultPwdSMS(String title, String captcha) {

		return  title + YOUR_DEFAULT_PWD_IS + captcha + WELCOME;
	}

	/**
	 * 票据挂失
	 * 
	 * @param no
	 *            票据编号
	 * @param lostTime
	 *            挂失时间
	 * @param court
	 *            机构
	 * @return
	 */
	public static String reportLoss(String no, String lostTime, String court) {

		return BILL_COLLECTION_OF_NOTES + no + _IN + lostTime + _COVER + court + NOTICE_OF_LOSS;
	}

	
	
	private final static String YOUR_VERIFICATION_CODE_IS = "您的验证码是：";

	private final static String YOUR_DEFAULT_PWD_IS = "您的登录密码是：";

	private final static String INPUT_TIME = "，感谢您的使用，请于5分钟内正确输入。";

	private final static String WELCOME = "，感谢您的使用。";

	private final static String BILL_COLLECTION_OF_NOTES = "【依融信息科技】尊敬的依融帮用户，您收藏的票据：";

	private final static String _IN = "，已于";

	private final static String _COVER = "被";

	private final static String NOTICE_OF_LOSS = "公告挂失，具体详细信息请登录依融帮查询，感谢您对依融帮的支持，祝您生活愉快！";

}
