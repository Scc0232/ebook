package com.zhijian.ebook.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.service.BookService;

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
	private BookService BookService;

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
		Book book = BookService.findBookById(bookid);
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
	public ResponseMsg addBook(Book book) {
		try {
			// if (Book.getBindMobileCount() == null || Book.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，图书需联系管理员从管理平台修改)
			// Book.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = bookService.addBook(book);
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
	public ResponseMsg modifyBook(Book book) {
		// BookMap.setQq(qq);
		// BookMap.setCompanyName(companyName);
		// BookMap.setCompanyPhone(companyPhone);
		// BookMap.setCompanyAddr(companyAddr);
		// BookMap.setMyReferralCode(myReferralCode);
		// BookMap.setBindMobileCount(bindMobileCount);
		int row = BookService.modifyBook(book);
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

}
