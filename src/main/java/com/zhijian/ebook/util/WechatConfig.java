package com.zhijian.ebook.util;

public class WechatConfig {

	private static WechatConfig wConfig = new WechatConfig();

	private  ReadPropertiesFileUtils readPropertiesFileUtils = ReadPropertiesFileUtils.getInstance();

	private final String APPID = readPropertiesFileUtils.getPropValueByKey("APPID");
	private final String APPSECRECT = readPropertiesFileUtils.getPropValueByKey("APPSECRECT");
	private final String KEYSECRECT = readPropertiesFileUtils.getPropValueByKey("KEYSECRECT");
	private final String MCHID = readPropertiesFileUtils.getPropValueByKey("MCHID");
	private final String NOTIFYURL = readPropertiesFileUtils.getPropValueByKey("NOTIFYURL");
	private final String ORDERURL = readPropertiesFileUtils.getPropValueByKey("ORDERURL");
	private final String TOKEN = readPropertiesFileUtils.getPropValueByKey("TOKEN");
	private final String EncodingAESKey = readPropertiesFileUtils.getPropValueByKey("EncodingAESKey");

	public static WechatConfig access() {
		return wConfig;
	}

	public String getAPPID() {
		return APPID;
	}

	public String getAPPSECRECT() {
		return APPSECRECT;
	}

	public String getKEYSECRECT() {
		return KEYSECRECT;
	}

	public String getMCHID() {
		return MCHID;
	}

	public String getNOTIFYURL() {
		return NOTIFYURL;
	}

	public String getORDERURL() {
		return ORDERURL;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public String getEncodingAESKey() {
		return EncodingAESKey;
	}

}
