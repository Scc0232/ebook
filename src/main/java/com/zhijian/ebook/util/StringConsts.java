package com.zhijian.ebook.util;

import java.util.UUID;

public class StringConsts {

	public static String USER_NAME = "";
	
	public static String PASSWORD_ENCRYPTION_WAY = "MD5";
	
	 /** 上传图片 存放图片的文件夹路径名 */
    public static final String TO_PATH_IMG = "img/";
    
    /**
     * 上传票据---小图片保存地址
     */
    public static final String ZHETENGBA_SMALL_IMG_PATH = "smallImg/";
    /**
     * 上传票据---大图片保存地址
     */
    public static final String ZHETENGBA_BIG_IMG_PATH = "bigImg/";
    
    /**
     * ue上传票据---小图片保存地址
     */
    public static final String UE_IMG_PATH = "ueImg/";
    
    /**
     * 服务器地址
     */
    public static final String SERVER_PATH = "http://localhost:8080/zhetengba/";
    
    /**
     * 票据文件上传保存根目录
     */
    public static final String FILE_SAVE_ROOT_DIRECTORY = "/var/zhetengba/";
    /**
     * 用户图片上传保存根目录
     */
    public static final String USER_ICON = "userIcon/"; 
    
    /**
     * 新闻图片保存根目录
     */
    public static final String NEWS_ICON = "news/"; 
        
    /**
     * 视频上传保存根目录
     */
    public static final String VIDEO = "video/";
    
    /**
     * 课程图片保存目录
     */
    public static String classImg="/var/zhetengba/";

	public static String COMMA = ",";
 
    
    /**
     * 普通角色ID
     */
    public static final String USER_ROLE_ID = "88228f81c0d011e5a130eca86ba4ba05";
    
    /**
     * 成功状态
     */
    public static final int SUCCESS_STATUS = 1;
    
    /**
     * 失败状态
     */
    public static final int FAILED_STATUS = 0;
    
    public static final String ZERO = "0";
    
    public static final String ONE = "1";

    
    public static String randomFileName() {
		return UUID.randomUUID().toString().replace("-", "");
	}
    
}
