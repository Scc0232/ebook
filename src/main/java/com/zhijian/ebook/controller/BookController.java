package com.zhijian.ebook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.service.BookService;
import com.zhijian.ebook.util.FileUpLoadUtils;
import com.zhijian.ebook.util.StringConsts;

/**
 * 
 * 图书控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/book")
public class BookController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private BookService bookService;

	/**
	 * 图书列表界面
	 * 
	 * @return 图书列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/book/bookList";
	}

	/**
	 * 获取图书分页数据
	 * 
	 * @param Book
	 *            图书实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findBookPagination")
	public EasyuiPagination<Book> findBookPagination(Book book, Integer page, Integer rows) {

		return bookService.findBookPagination(book, page, rows);
	}

	/**
	 * 添加图书页面
	 * 
	 * @return 添加图书路径
	 */
	@RequestMapping("addBookView")
	public String addBookView() {
		return "manager/book/addBook";
	}

	/**
	 * 修改图书界面
	 * 
	 * @param map
	 *            图书信息
	 * @param BookId
	 *            图书ID
	 * @return 修改图书路径
	 */
	@RequestMapping("modifyBookView")
	public String modifyBookView(ModelMap map, String bookid) {
		Book book = bookService.findBookById(bookid);
		// if (BookMap.getBindMobileCount() == null) {
		// BookMap.setBindMobileCount(0);
		// }
		map.put("book", book);
		return "manager/book/modifyBook";
	}

	/**
	 * 添加图书
	 * 
	 * @param Book
	 *            图书信息
	 * @return 添加图书情况
	 */
	@ResponseBody
	@RequestMapping("addBook")
	public ResponseMsg addBook(Book book, HttpServletRequest request) {
		try {
			// if (Book.getBindMobileCount() == null || Book.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，图书需联系管理员从管理平台修改)
			// Book.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = 0;
			String icon = uploadImg(request);
			if (icon != null) {
				book.setIcon(icon);
			} else {
				book.setIcon("img/test.jpg");
			}
			row = bookService.addBook(book);
			if (row > 0) {
				return ResponseMsg.success("添加图书成功！");
			} else {
				return ResponseMsg.fail("添加图书失败！");
			}
		} catch (Exception e) {
			logger.error("添加图书异常", e);
			return ResponseMsg.fail("添加图书异常！");
		}

	}

	/**
	 * 修改图书
	 * 
	 * @param Book
	 *            图书信息
	 * @return 修改图书情况
	 */
	@ResponseBody
	@RequestMapping("modifyBook")
	public ResponseMsg modifyBook(Book book, HttpServletRequest request) {
		// BookMap.setQq(qq);
		// BookMap.setCompanyName(companyName);
		// BookMap.setCompanyPhone(companyPhone);
		// BookMap.setCompanyAddr(companyAddr);
		// BookMap.setMyReferralCode(myReferralCode);
		// BookMap.setBindMobileCount(bindMobileCount);
		int row = 0;
		String icon = uploadImg(request);
		if (icon != null) {
			book.setIcon(icon);
		} else {
			book.setIcon("img/test.jpg");
		}
		row = bookService.modifyBook(book);
		if (row > 0) {
			return ResponseMsg.success("修改图书成功！");
		} else {
			return ResponseMsg.fail("修改图书失败！");
		}
	}

	/**
	 * 删除图书
	 * 
	 * @param id
	 *            图书ID
	 * @return 删除图书情况
	 */
	@ResponseBody
	@RequestMapping("removeBook")
	public ResponseMsg removeBookById(String id) {
		int row = bookService.removeBookById(id);
		if (row > 0) {
			return ResponseMsg.success("删除图书成功！");
		} else {
			return ResponseMsg.fail("删除图书失败！");
		}
	}

	/**
	 * 查询学校名称
	 * 
	 * @param id
	 *            风格id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findCollegeList")
	public List<Major> findCollegeList() {
		return bookService.findCollegeList();
	}

	/**
	 * 查询学院名称
	 * 
	 * @param id
	 *            风格id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findAcademy")
	public List<Major> findAcademy(String collegeName) {
		List<Major> list = bookService.findAcademy(collegeName);
		return list;
	}

	/**
	 * 查询专业名称
	 * 
	 * @param id
	 *            风格id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findProfession")
	public List<Major> findProfession(String collegeName, String academyName) {
		List<Major> list = bookService.findProfession(collegeName, academyName);
		return list;
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @return
	 */
	public String uploadImg(HttpServletRequest request) {
		String uploadFilePath = null;
		try {
			List<String> imgSufferList = new ArrayList<String>();

			imgSufferList.add("jpg");
			imgSufferList.add("png");
			imgSufferList.add("gif");
			imgSufferList.add("bmp");
			imgSufferList.add("jpeg");

			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				Long newFileName = null;

				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartFile headImg = multiRequest.getFile("icons");
				String suffer = null;
				if ((headImg != null) && (headImg.getSize() > 0L)) {
					suffer = FileUpLoadUtils.getFileSuffix(headImg);
					if (!imgSufferList.contains(suffer.toLowerCase())) {
						throw new Exception();
					}
					newFileName = Long.valueOf(StringConsts.getUUID16Id());
					uploadFilePath = FileUpLoadUtils.writeFile(headImg, "/var/ebook/image/" + StringConsts.TO_PATH_IMG, newFileName.toString(), false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (org.springframework.util.StringUtils.isEmpty(uploadFilePath)) {
			return null;
		}
		return uploadFilePath;
	}

}
