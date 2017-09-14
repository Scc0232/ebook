package com.zhijian.ebook.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 获取src或src/main/resources下的properties的值
 * @author conlin
 *
 */
public class ReadPropertiesFileUtils {
	private static final Logger logger = LogManager.getLogger();
	
	private static InputStream inputStream = null;
	
	private static Properties prop = null;
	
	public ReadPropertiesFileUtils () {
		
	}
	
	public static ReadPropertiesFileUtils getInstance(){
		ReadPropertiesFileUtils read = new ReadPropertiesFileUtils();
		inputStream = read.getClass().getResourceAsStream("/config.properties");
		try {
			logger.info("开始加载properties文件");
			prop = new Properties();
			prop.load(inputStream);
			logger.info("properties文件加载结束！");
		} catch (FileNotFoundException e) {
			logger.error("Properties加载异常！",e);
		} catch (IOException e) {
			logger.error("Properties加载异常！",e);
		}
		return read;
	}
	
	public String getPropValueByKey(String key,String defaultValue) {
		logger.info("根据key获取properties value , key={},defaultValue={}",key,defaultValue);
		return prop.getProperty(key, defaultValue);
	}
	public String getPropValueByKey(String key) {
		logger.info("根据key获取properties value , key={}",key);
		return prop.getProperty(key);
	}
	
	/**
	 * 测试工具的用法
	 * @param args
	 */
	public static void main(String[] args) {
		ReadPropertiesFileUtils readTest = ReadPropertiesFileUtils.getInstance();
		//无默认值的情况
		String value = readTest.getPropValueByKey("sign_in_task_on_time");
		//有默认值的情况
		String value2 = readTest.getPropValueByKey("test_key", "这是默认值");
		System.out.println("读取的值 value=" + value + " value2 = " + value2);
		
		String appid = readTest.getPropValueByKey("appid");
		System.out.println(appid);
		
		
	}
}
