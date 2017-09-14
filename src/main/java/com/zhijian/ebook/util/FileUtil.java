package com.zhijian.ebook.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 文件操作工具类 用于复制 移动 删除文件
 * @author Administrator
 */

@Component
public class FileUtil {
	
	
	/**
	 * 根据路径 获取此路径下的文件列表
	 * @param path
	 * @return
	 */
	public static List<File> getFilesPath(String path){
		
		File fileFolder = new File(path);
		if(!fileFolder.exists()){
			fileFolder.mkdirs();
		}
		File[] fileArray = fileFolder.listFiles();
        
		List<File> fileList = new ArrayList<File>();
        for(File file : fileArray){
        	if(!file.isDirectory()) {
        		fileList.add(file);
        	}
        }
		return fileList;
	}
	
	
	/**
	 * 移动文件  从fromPath路径移动到targetPath路径
	 * @param fromPath
	 * @param targetFolderPath
	 * @return
	 */
	public static boolean moveFile(String fromPath,String targetFolderPath){
		File fromFile = new File(fromPath);
		File targetFile = new File(targetFolderPath);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		boolean flag = fromFile.renameTo(new File(targetFile, fromFile.getName()));
		return flag;
	}
	
	/**
	 * 删除文件夹
	 * @param file
	 * @return
	 */
	public static boolean removeFolder(File file){
		boolean flag = false;
		if(file!=null && file.isDirectory()){
			//删除文件夹之前 先删除文件夹里面的文件  file.delete()方法只删除空文件夹
			File[] listFiles = file.listFiles();
			int len = listFiles.length;
			for(int i=0;i<len;i++){
				//递归
				removeFolder(listFiles[i]);
			}
			//删除文件夹
			flag = file.delete();
		}
		return flag;
	}
	
	/**
	 * 删除文件
	 * @param file
	 * @return
	 */
	public static boolean removeFile(File file){
		boolean flag = false;
		if(file!=null && file.isFile()){
			flag = file.delete();
		}
		return flag;
	}
	
	
}