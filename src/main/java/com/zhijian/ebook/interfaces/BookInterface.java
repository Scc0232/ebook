package com.zhijian.ebook.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseEntity;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryComment;
import com.zhijian.ebook.entity.DiaryLike;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.enums.GradeLevel;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.BookClassService;
import com.zhijian.ebook.service.BookService;
import com.zhijian.ebook.service.SouvenirService;

/**
 * 图书接口
 * 
 * @author XM
 * @version v.0.1
 * @date 2017年9月11日
 */
@Controller
@RequestMapping(value = "book")
public class BookInterface {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	private BookClassService bookclassService;

	@Autowired
	private BookService bookService;

	@Autowired
	private SouvenirService souvenirService;

	@Autowired
	private UserService userService;

	/**
	 * 获取轮播图
	 * 
	 * @return ResponseEntity 返回dict实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/findBanner", method = RequestMethod.GET)
	public ResponseEntity findBanner() {
		// String username = UserContextHelper.getUsername();
		List<Dict> list = null;
		try {
			list = bookclassService.findBanner();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}

		return ResponseEntity.ok(list);
	}

	/**
	 * 获取图书分类接口和考研列表
	 * 
	 * @return ResponseEntity 返回图书分类实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/selectBookClass", method = RequestMethod.GET)
	public ResponseEntity selectBookClass() {
		// String username = UserContextHelper.getUsername();
		Map<String, List<?>> map = null;

		try {
			map = bookclassService.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}

		return ResponseEntity.ok(map);
	}

	/**
	 * 获取热门图书(首页热门书籍， 年级书籍，分类书籍)
	 * 
	 * @return ResponseEntity 返回图书实体
	 */
	@SuppressWarnings("unlikely-arg-type")
	@ResponseBody
	@RequestMapping(value = "unlogin/selectHotBook", method = RequestMethod.GET)
	public ResponseEntity selectHotBook(String grade, String classid) {
		// String username = UserContextHelper.getUsername();
		List<Book> list = null;
		if (grade != null) {
			grade = grade.trim();
		}
		if (classid != null) {
			classid = classid.trim();
		}
		try {
			if ((grade != null) && (grade.equals(GradeLevel.ONE_LEVEL.getLevel())
					|| grade.equals(GradeLevel.TWO_LEVEL.getLevel()) || grade.equals(GradeLevel.THREE_LEVEL.getLevel())
					|| grade.equals(GradeLevel.FOUR_LEVEL.getLevel()))) {
				list = bookService.selectHotBook(grade, null);
			} else if (classid != null) {
				list = bookService.selectHotBook(null, classid);
			} else {
				list = bookService.selectHotBook(null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}

		return ResponseEntity.ok(list);
	}

	/**
	 * 图书搜索
	 * 
	 * @return ResponseEntity 返回图书实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/searchBook", method = RequestMethod.GET)
	public ResponseEntity searchBook(String content) {
		// String username = UserContextHelper.getUsername();
		if (content != null) {
			content = content.trim();
		} else {
			return ResponseEntity.ok(new ArrayList<>(), "请输入内容");
		}
		List<Book> list = null;
		Pattern pattern = Pattern.compile("[0-9]*");
		if (pattern.matcher(content).matches()) {
			list = bookService.selectByISBN(content);
			if (list == null || list.isEmpty()) {
				return ResponseEntity.ok(new ArrayList<>(), "请输入正确的ISBN号");
			}
		} else {
			list = bookService.selectByBookName(content);
			if (list == null || list.isEmpty()) {
				list = bookService.selectByAuthor(content);
				if (list == null || list.isEmpty()) {
					return ResponseEntity.ok(new ArrayList<>(), "无信息");
				}
			}
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 查看图书详情
	 * 
	 * @return ResponseEntity 返回图书分类实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/updateHotValue", method = RequestMethod.GET)
	public ResponseEntity updateHotValue(String bookid) {
		// String username = UserContextHelper.getUsername();
		int flag = 0;
		try {
			flag = bookService.updateHotValue(bookid);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(flag);
	}

	/**
	 * 搜索纪念品
	 * 
	 * @return ResponseEntity 返回纪念品实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/selectSouvenir", method = RequestMethod.GET)
	public ResponseEntity selectSouvenir() {
		// String username = UserContextHelper.getUsername();
		List<Souvenir> list = null;
		try {
			list = souvenirService.selectSouvenirAll();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 添加日记
	 * 
	 * @return ResponseEntity 返回图书分类实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/addDiary", method = RequestMethod.GET)
	public ResponseEntity addDiary(Diary diary) {
		// String username = UserContextHelper.getUsername();
		int flag = 0;
		try {
			if ((StringUtils.isNotBlank(diary.getTitle()))
					&& (StringUtils.isNotBlank(diary.getContent()) || StringUtils.isNotBlank(diary.getIcon()))) {
				flag = souvenirService.addNewDiary(diary);
				return ResponseEntity.ok(flag);
			} else {
				return ResponseEntity.serverError("信息缺失");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 查看全部可见日记
	 * 
	 * @return ResponseEntity 返回日记实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/selectDiary", method = RequestMethod.GET)
	public ResponseEntity selectDiary(Integer page, Integer rows) {
		String username = UserContextHelper.getUsername();
		EasyuiPagination<Diary> list = null;
		try {
			String userid = userService.findUserByUsername(username).getId();
			list = souvenirService.selectDiaryAll(page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 查看我的日记
	 * 
	 * @return ResponseEntity 返回日记实体
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/selectMyDiary", method = RequestMethod.GET)
	public ResponseEntity selectMyDiary(Integer page, Integer rows) {
		String username = UserContextHelper.getUsername();
		EasyuiPagination<Diary> list = null;
		try {
			String userid = userService.findUserByUsername(username).getId();
			list = souvenirService.selectMyDiary(page, rows, userid);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 添加日记评论
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/addComment", method = RequestMethod.POST)
	public ResponseEntity addComment(DiaryComment diaryComment) {
		int flag = 0;
		try {
			if (StringUtils.isBlank(diaryComment.getContent())) {
				return ResponseEntity.serverError("没有评论内容 content");
			}
			flag = souvenirService.addDiaryComment(diaryComment);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(flag);
	}

	/**
	 * 日记点赞
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/addLike", method = RequestMethod.POST)
	public ResponseEntity addLike(DiaryLike diaryLike) {
		try {
			int rows = souvenirService.findIsLike(diaryLike.getDiaryId());
			if (rows > 0) {
				return ResponseEntity.ok("已点赞");
			}
			souvenirService.addDiaryLike(diaryLike);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("点赞成功");
	}

	/**
	 * 日记取消赞
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/removeLike", method = RequestMethod.POST)
	public ResponseEntity removeLike(String diaryId) {
		int flag = 0;
		try {
			int rows = souvenirService.findIsLike(diaryId);
			if (rows < 1) {
				return ResponseEntity.ok("还未点赞");
			}
			flag = souvenirService.removeDiaryLike(diaryId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(flag);
	}

	/**
	 * 图书收藏
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/addCollection", method = RequestMethod.POST)
	public ResponseEntity addCollection(String bookid) {
		try {
			int rows = bookService.findIsCollection(bookid);
			if (rows > 0) {
				return ResponseEntity.ok("已收藏");
			}
			bookService.addCollection(bookid);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("收藏成功");
	}

	/**
	 * 图书取消收藏
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/removeCollection", method = RequestMethod.POST)
	public ResponseEntity removeCollection(String bookid) {
		int flag = 0;
		try {
			int rows = bookService.findIsCollection(bookid);
			if (rows < 1) {
				return ResponseEntity.ok("还未收藏");
			}
			flag = bookService.removeCollection(bookid);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(flag);
	}

	/**
	 * 我的图书收藏
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/findCollection", method = RequestMethod.GET)
	public ResponseEntity findCollection() {
		List<Collect> list = null;
		try {
			list = bookService.findCollection();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}
	
	
	
	
	
	/**
	 * 添加到购物车
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/addShoppingCart", method = RequestMethod.POST)
	public ResponseEntity addShoppingCart(String productid) {
		try {
			int rows = bookService.isInShoppingCart(productid);
			if (rows > 0) {
				return ResponseEntity.ok("已添加");
			}else {
				bookService.addShoppingCart(productid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("添加成功");
	}

	/**
	 * 从购物车删除
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/removeShoppingCart", method = RequestMethod.POST)
	public ResponseEntity removeShoppingCart(String productid) {
		int flag = 0;
		try {
			int rows = bookService.isInShoppingCart(productid);
			if (rows < 1) {
				return ResponseEntity.ok("已删除");
			}else {
				flag = bookService.removeShoppingCart(productid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("删除成功");
	}

	/**
	 * 我的购物车
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/findShoppingCart", method = RequestMethod.GET)
	public ResponseEntity findShoppingCart() {
		List<ShoppingCart> list = null;
		try {
			list = bookService.findShoppingCart();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}
	
	
	/**
	 * 提交订单
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/submitOrder", method = RequestMethod.POST)
	public ResponseEntity submitOrder(String productids) {
		try {
			int rows = bookService.submitOrder(productids);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("添加成功");
	}
	
	/**
	 * 提交订单
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/dirSubmitOrder", method = RequestMethod.POST)
	public ResponseEntity dirSubmitOrder(String productid, int nums) {
		try {
			int rows = bookService.dirSubmitOrder(productid,nums);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("添加成功");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
