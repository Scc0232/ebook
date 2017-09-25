package com.zhijian.ebook.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传工具类
 * 
 * @author 廖富尧
 *
 */
public class FileUpLoadUtils {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 压缩图片宽度至
	 */
	private static final int COMPRESS_IMAGE_WIDTH = 222;

	/**
	 * 压缩图片高度至
	 */
	private static final int COMPRESS_IMAGE_HEIGHT = 200;

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param toPath
	 *            保存文件的路径 例如：image/
	 * @param uploadLableName
	 *            上传标签id如：<input id='xxx'....>中的id
	 * @return
	 */
	public static String upLoadFile(HttpServletRequest request, String toPath, String uploadLableName) {
		return upLoadFile(request, toPath, uploadLableName, false);
	}

	/**
	 * 图片文件上传
	 * 
	 * @param request
	 * @param toPath
	 *            保存文件的路径 例如：image/
	 * @param uploadLableName
	 *            上传标签id如：<input id='xxx'....>中的id
	 * @param isCompress
	 *            是否需要压缩，if true 压缩图片至COMPRESS_IMAGE_WIDTH*COMPRESS_IMAGE_HEIGHT
	 * @return
	 */
	public static String upLoadImageFile(HttpServletRequest request, String toPath, String uploadLableName, boolean isCompress) {
		return upLoadFile(request, toPath, uploadLableName, isCompress);
	}

	/**
	 * 文件上传（只支持单文件上传）
	 * 
	 * @param request
	 * @param toPath
	 *            保存文件的路径 例如：image/
	 * @param uploadLableName
	 *            上传标签id如：<input id='xxx'....>中的id
	 * @param isCompress
	 *            是否需要压缩，只能压缩图片文件
	 * @return
	 */
	private static String upLoadFile(HttpServletRequest request, String toPath, String uploadLableName, boolean isCompress) {
		logger.info("上传文件开始....");
		InputStream bis = null;
		OutputStream bos = null;
		String fullLocalPath = "";
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
			MultipartFile file = mreq.getFile(uploadLableName);
			String upLoadName = file.getOriginalFilename();
			String fileSuffix = upLoadName.substring(upLoadName.lastIndexOf("."));
			long newFileName = randomFileName();
			if (StringUtils.isBlank(toPath)) {
				logger.info("文件上传服务路径不能为空！toPath={}", toPath);
				return null;
			}
			fullLocalPath = "/var/jinzhu/" + toPath;
			if (!new File(fullLocalPath).isDirectory()) {
				new File(fullLocalPath).mkdirs();
			}
			fullLocalPath = fullLocalPath + newFileName + fileSuffix;
			toPath = toPath + newFileName + fileSuffix;

			if (isCompress) {
				// 需要压缩
				bis = compress(file.getInputStream(), COMPRESS_IMAGE_WIDTH, COMPRESS_IMAGE_HEIGHT);
			} else {
				bis = new BufferedInputStream(file.getInputStream());
			}

			bos = new BufferedOutputStream(new FileOutputStream(new File(fullLocalPath)));
			Streams.copy(bis, bos, true);
			logger.info("上传成功，上传返回路径==" + toPath);
		} catch (Exception e) {
			toPath = null;
			logger.error("文件上传异常！", e);
		}
		return toPath;
	}

	/**
	 * 多文件上传公共类
	 * 
	 * @param request
	 * @param toPath
	 *            保存的路径
	 * @param fileName
	 *            保存的文件名(没有后缀名) 为空时会使用默认的原始文件名
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> uploadMoreFile(HttpServletRequest request, String toPath, String fileName) throws Exception {
		List<Map<String, String>> paths = new ArrayList<Map<String, String>>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					String _fileName = file.getOriginalFilename();
					if (!"".equals(_fileName.trim())) {
						String fileSuffix = _fileName.substring(_fileName.lastIndexOf("."));
						File localFile = null;
						String saveFileName = "";
						if (StringUtils.isNotBlank(fileName)) {
							saveFileName = fileName + fileSuffix;
						} else {
							saveFileName = _fileName;
						}
						localFile = new File(toPath, saveFileName);
						localFile.mkdirs();
						file.transferTo(localFile);
						Map<String, String> pathMap = new HashMap<String, String>();
						pathMap.put("savePath", toPath + saveFileName);
						pathMap.put("fileName", saveFileName);
						paths.add(pathMap);
					}
				}
			}
		}
		return paths;
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 *            上传的文件
	 * @param toPath
	 *            保存路径
	 * @param fileName
	 *            文件保存名称
	 * @param isCompress
	 *            是否需要压缩（用于图片压缩）true ： 压缩 false:不压缩
	 * @throws Exception
	 *             return saveFileName 保存文件名
	 */
	public static String writeFile(MultipartFile file, String toPath, String fileName, boolean isCompress) throws Exception {
		String filePath = "";
		String _fileName = file.getOriginalFilename();
		if (!"".equals(_fileName.trim())) {
			String fileSuffix = _fileName.substring(_fileName.lastIndexOf("."));
			// File localFile = null;
			String saveFileName = "";
			if (StringUtils.isNotBlank(fileName)) {
				saveFileName = fileName + fileSuffix;
			} else {
				saveFileName = _fileName;
			}
			// localFile = new File(toPath,saveFileName);
			// localFile.mkdirs();
			if (!new File(toPath).isDirectory()) {
				new File(toPath).mkdirs();
			}
			toPath = toPath + saveFileName;
			InputStream bis = null;
			if (isCompress) {
				// 需要压缩
				ImageUtil.init(file.getInputStream());
				int width = ImageUtil.getWidth();
				int height = ImageUtil.getHeight();
				float multiples = width / COMPRESS_IMAGE_WIDTH;
				if (multiples > 1.0f) {
					width = (int) (width / multiples);
					height = (int) (height / multiples);
				}
				bis = compress(file.getInputStream(), width, height);
			} else {
				bis = new BufferedInputStream(file.getInputStream());
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(toPath)));
			Streams.copy(bis, bos, true);
			// file.transferTo(localFile);
			filePath = saveFileName;
		}
		return StringConsts.EBOOK_URL+filePath;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getFileSuffix(MultipartFile file) throws Exception {
		String _fileName = file.getOriginalFilename();
		String fileSuffix = "";
		if (!"".equals(_fileName.trim())) {
			fileSuffix = _fileName.substring(_fileName.lastIndexOf(".") + 1);
		}
		return fileSuffix;
	}

	/**
	 * 随机文件名，使用UUID16maker
	 * 
	 * @return
	 */
	private static long randomFileName() {
		return UUID16maker.getUUID16Id();
	}

	/**
	 * 压缩图片至指定尺寸
	 * 
	 * @param image
	 *            图片的输入流
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 * @throws IOException
	 */
	private static InputStream compress(InputStream image, int width, int height) throws IOException {
		return ImageUtil.resizeFix(width, height, image);
	}

	/**
	 * 根据请求头解析出文件名 请求头的格式：火狐和google浏览器下：form-data; name="file";
	 * 
	 * @param header
	 *            请求头
	 * @return 文件名
	 */
	public static String getFileName(String header) {
		String[] tempArr1 = header.split(";");
		String[] tempArr2 = tempArr1[2].split("=");
		// 获取文件名，兼容各种浏览器的写法
		String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
		return fileName;
	}

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath
	 *            远程文件路径
	 * @param localFilePath
	 *            本地文件路径
	 */
	public static void downloadFile(String remoteFilePath, String localFilePath) {
		logger.info("下载文件开始....文件远程路径={},保存本地路径={}", remoteFilePath, localFilePath);
		// 远程文件路径
		URL urlfile = null;
		// 本地保存文件路径
		File file = new File(localFilePath);
		file.setExecutable(true);// 设置可执行权限
		file.setReadable(true);// 设置可读权限
		file.setWritable(true);// 设置可写权限
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			urlfile = new URL(remoteFilePath);
			FileUtils.copyURLToFile(urlfile, file);
		} catch (Exception e) {
			logger.error("文件下载异常！", e);
		}
	}
}
