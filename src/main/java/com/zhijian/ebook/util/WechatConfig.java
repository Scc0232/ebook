package com.zhijian.ebook.util;

public class WechatConfig {

	private static WechatConfig wConfig = new WechatConfig();

	private ReadPropertiesFileUtils readPropertiesFileUtils = ReadPropertiesFileUtils.getInstance();

	private String APPID = readPropertiesFileUtils.getPropValueByKey("APPID");
	private String APPSECRECT = readPropertiesFileUtils.getPropValueByKey("APPSECRECT");
	private String KEYSECRECT = readPropertiesFileUtils.getPropValueByKey("KEYSECRECT");
	private String MCHID = readPropertiesFileUtils.getPropValueByKey("MCHID");
	private String NOTIFYURL = readPropertiesFileUtils.getPropValueByKey("NOTIFYURL");
	private String ORDERURL = readPropertiesFileUtils.getPropValueByKey("ORDERURL");
	private String TOKEN = readPropertiesFileUtils.getPropValueByKey("TOKEN");
	private String EncodingAESKey = readPropertiesFileUtils.getPropValueByKey("EncodingAESKey");

	public static WechatConfig access() {
		return wConfig;
	}

	private WechatConfig() {
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
