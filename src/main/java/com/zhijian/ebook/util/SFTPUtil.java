package com.zhijian.ebook.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP 工具类 用于上传 下载 删除文件
 * 
 * @author Administrator
 *
 */
@Component
public class SFTPUtil {

	Logger logger = Logger.getLogger(SFTPUtil.class);
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 端口 默认22
	 */
	private int port = 22;

	
	/**
	 * 得到 sftpsession
	 * @return
	 */
	public Session getSftpsshSession() {
		JSch jsch = new JSch();
		try {
			jsch.getSession(userName, ip, port);
			return jsch.getSession(userName, ip, port);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 默认上传目录
	 */
	private String pathPrefix;

	public ChannelSftp getChannelSftpConnection(Session sshSession) {
		ChannelSftp sftp = null;
		try {
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			logger.error("SFTP服务器网络连接失败。" + "SFTP[IP：" + ip + " PORT：" + port
					+ " UserName：" + userName + " Password：" + password + "]");
		}
		return sftp;
	}

	/**
	 * 关闭连接
	 * 
	 * @param sftp
	 */
	public void close(ChannelSftp sftp, Session session) {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
			} else if (sftp.isClosed()) {
				System.out.println("sftp is closed already");
			}
		}
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
			} 
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public String upload(String directory, String uploadFile) {
		try {
			Session session=getSftpsshSession();
			ChannelSftp sftp = getChannelSftpConnection(session);
			// sftp.
			String path = pathPrefix;
			if (directory != null && !directory.isEmpty()) {
				path += directory;
			}
			if (path.indexOf("\\") != -1) {
				path = path.replaceAll("\\", "/");
			}
			mkdirs(path, sftp);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
			// 关闭
			close(sftp ,session);
			return path + file.getName();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件到SFTP异常！");
		}
		return "";
	}

	/**
	 * 创建多级目录
	 * 
	 * @param pathName
	 *            要创建的目录路径，可以是相对路径，也可以是绝路径("/"开始)
	 * @return 如果成功创建目录返回true，否则返回false(如果目录已存在也返回false)
	 * @throws IOException
	 */
	public boolean mkdirs(String pathName, ChannelSftp sftp) throws Exception {
		String path = pathName;
		if (path.indexOf("\\") != -1) {
			path = pathName.replaceAll("\\\\", "/");
		}
		String[] paths = path.split("/");
		for (int i = 0; i < paths.length; i++) {
			String dir = paths[i];
			if (dir.isEmpty()) {
				if (i == 0) {
					sftp.cd("/");
				}
				continue;
			}
			Vector<Object> ls = _list(dir, sftp);
			if (ls == null || ls.size() <= 0) {
				sftp.mkdir(dir);
			}
			sftp.cd(dir);
		}
		return true;
	}

	/**
	 * 获取sftp文件夹下文件
	 * 
	 * @param dir
	 * @param sftp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Vector<Object> _list(String dir, ChannelSftp sftp) {
		try {
			return sftp.ls(dir);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public boolean download(String directory, String downloadFile,
			String saveFile) {
		try {
			Session 	Session=getSftpsshSession();
			ChannelSftp sftp = getChannelSftpConnection(Session);
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载SFTP文件异常！");
			return false;
		}
		return true;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public boolean download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			System.out.println("开始下载");
			sftp.cd(directory);
			File file = new File(saveFile);
			System.out.println("开始 sfst的get方法下载开始"+file.getName());
			sftp.get(downloadFile, new FileOutputStream(file));
			System.out.println("sfst的get方法下载结束"+file.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下载SFTP文件异常！");
			return false;
		}
		return true;
	}

	/**
	 * 下载目录下全部文件 并且移动(下载完成移动)
	 * 
	 * @param directory
	 *            下载目录
	 * @throws Exception
	 */
	public boolean downloadByDirectory(String directory, String saveDirectory)
			throws Exception {
		Session session=getSftpsshSession();
		ChannelSftp sftp = getChannelSftpConnection(session);
		String downloadFile = "";
		// 判断目录是否需要创建
		mkdirs(directory, sftp);
		List<String> downloadFileList = listFiles(directory, sftp);
		Iterator<String> it = downloadFileList.iterator();

		while (it.hasNext()) {
			downloadFile = it.next().toString();
			if (downloadFile.toString().indexOf(".") < 0) {
				continue;
			}
			String saveFile = saveDirectory + File.separator + downloadFile;
			boolean flag = download(directory, downloadFile, saveFile, sftp);
			if (flag) {
				String oldPath = directory + File.separator + downloadFile;
				String newPath = directory + File.separator + "suss";
				// 判断目录是否需要创建
				mkdirs(newPath, sftp);
				newPath += File.separator + downloadFile;
				rename(oldPath, newPath, sftp);
			}
		}
		// 关闭sftp连接
		close(sftp ,session);
		return true;
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @return list 文件名列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> listFiles(String directory, ChannelSftp sftp)
			throws Exception {
		List<String> fileNameList = new ArrayList<String>();
		Vector<Object> fileList = sftp.ls(directory);
		Iterator<Object> it = fileList.iterator();

		while (it.hasNext()) {
			String fileName = ((LsEntry) it.next()).getFilename();
			if (".".equals(fileName) || "..".equals(fileName)) {
				continue;
			}
			fileNameList.add(fileName);
		}
		return fileNameList;
	}

	/**
	 * 移动文件（重命名目录）
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public boolean rename(String oldPath, String newPath, ChannelSftp sftp) {
		try {
			if (oldPath.indexOf("\\") != -1) {
				oldPath = oldPath.replaceAll("\\\\", "/");
			}
			if (newPath.indexOf("\\") != -1) {
				newPath = newPath.replaceAll("\\\\", "/");
			}
			sftp.rename(oldPath, newPath);
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("移动（重命名）SFTP文件异常！");
			return false;
		}
		return true;
	}

	/**
	 * 移动文件（重命名目录）
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public boolean rename(String oldPath, String newPath) {
		try {
			Session 	Session=getSftpsshSession();
			ChannelSftp sftp = getChannelSftpConnection(Session);
			if (oldPath.indexOf("\\") != -1) {
				oldPath = oldPath.replaceAll("\\\\", "/");
			}
			if (newPath.indexOf("\\") != -1) {
				newPath = newPath.replaceAll("\\\\", "/");
			}
			sftp.rename(oldPath, newPath);
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("移动（重命名）SFTP文件异常！");
			return false;
		}
		return true;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

}