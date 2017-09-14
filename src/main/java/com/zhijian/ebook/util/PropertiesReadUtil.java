package com.zhijian.ebook.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取工具类
 * 
 * @author Administrator
 *
 */
public class PropertiesReadUtil {

	/**
	 * 文件所在路径
	 */
	private static String fileName = "/config.properties";
	private static InputStream is = PropertiesReadUtil.class.getClassLoader().getResourceAsStream(fileName);
	private static Properties prop;
	static {// 装载文件
		try {
			prop = new Properties();
			prop.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getSaveFileDirectory() {
		Properties p = new Properties();
		try {
			InputStream in = PropertiesReadUtil.class.getResourceAsStream(fileName);
			//new FileInputStream(fileName)
			p.load(in);
			in.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return p.getProperty("file.directory");
	}

	/**
	 * 根据propertyKey 获得 value
	 * 
	 * @param key
	 * @return value
	 */
	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
}