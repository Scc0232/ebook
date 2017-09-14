package com.zhijian.ebook.util;

public class WechatConfig {

	private static ReadPropertiesFileUtils readPropertiesFileUtils = ReadPropertiesFileUtils.getInstance();

	public static String APPID = readPropertiesFileUtils.getPropValueByKey("APPID");
	public static String APPSECRECT = readPropertiesFileUtils.getPropValueByKey("APPSECRECT");
	public static String KEYSECRECT = readPropertiesFileUtils.getPropValueByKey("KEYSECRECT");
	public static String MCHID = readPropertiesFileUtils.getPropValueByKey("MCHID");
	public static String NOTIFYURL = readPropertiesFileUtils.getPropValueByKey("NOTIFYURL");
	public static String ORDERURL = readPropertiesFileUtils.getPropValueByKey("ORDERURL");
	public static String TOKEN = readPropertiesFileUtils.getPropValueByKey("TOKEN");
	public static String EncodingAESKey = readPropertiesFileUtils.getPropValueByKey("EncodingAESKey");
}
