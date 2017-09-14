package com.zhijian.ebook.util;

public class ConstantString {
	
	/**测试环境的融云 appKey 和 appSecret*/
//	public static final String appKey = "bmdehs6pdw1vs";
//	public static final String appSecret = "OM8VOROndnR1P";
	
	/**生产环境的融云 appKey 和 appSecret*/
	public static final String appKey = "pkfcgjstfd2m8";
	public static final String appSecret = "E7ZSxudXYyfqD5";
	/**信贷经理由于在客户信息表不存在头像等信息，暂时用统一的头像*/
	public static final String PORTRAIT_URI = "http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=2b2dc8d709d162d985bb6a1824ef85da/5366d0160924ab18be0e672436fae6cd7b890b8c.jpg";
	/**新注册的用户由于没有头像，暂时统一用一张头像*/
	public static final String NEW_USER_PORTRAIT_URI = "http://d6.yihaodianimg.com/N03/M07/8C/19/CgQCtFH4yQOAAoITAAAjzplptUw39700.jpg";
	/**用户默认的昵称*/
	public static final String DEFAULT_NICKNAME = "jz_customer";
	/**信贷经理默认的昵称*/
	public static final String CREDIT_DEFAULT_NICKNAME = "jz_creditManager";

	public static final String IOS = "IOS";

	public static final String ANDROID = "ANDROID";
}
