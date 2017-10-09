package com.zhijian.ebook.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.service.CaptchaService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseEntity;
import com.zhijian.ebook.bean.SMSCaptcha;
import com.zhijian.ebook.dao.OrderMapper;
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookShelf;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryComment;
import com.zhijian.ebook.entity.DiaryLike;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.Order;
import com.zhijian.ebook.entity.OrderExample;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.enums.GradeLevel;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.BookClassService;
import com.zhijian.ebook.service.BookService;
import com.zhijian.ebook.service.SouvenirService;
import com.zhijian.ebook.util.CaptchaUtils;
import com.zhijian.ebook.util.FileUpLoadUtils;
import com.zhijian.ebook.util.StringConsts;
import com.zhijian.ebook.util.WechatCore;
import com.zhijian.ebook.util.WechatUtils;
import com.zhijian.ebook.util.CLSMS.ChuangLanSmsUtil;
import com.zhijian.ebook.util.CLSMS.SMSType;
import com.zhijian.ebook.util.CLSMS.SmsBalanceResponse;
import com.zhijian.ebook.util.CLSMS.SmsSendResponse;

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

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private CaptchaService captchaService;

	/**
	 * 获取轮播图
	 * 
	 * @return ResponseEntity 返回dict实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/findBanner", method = RequestMethod.GET)
	public ResponseEntity findBanner() {
		// String username = UserContextHelper.getUsername();
		List<Dict> list = null;
		try {
			list = bookclassService.findBanner();
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/selectBookClass", method = RequestMethod.GET)
	public ResponseEntity selectBookClass() {
		// String username = UserContextHelper.getUsername();
		Map<String, List<?>> map = null;

		try {
			map = bookclassService.selectAll();
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}

		return ResponseEntity.ok(map);
	}

	/**
	 * 获取热门图书(首页热门书籍， 年级书籍，分类书籍)
	 * 
	 * @return ResponseEntity 返回图书实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/selectHotBook", method = RequestMethod.GET)
	public ResponseEntity selectHotBook(String grade, String classid) {
		List<Book> list = null;
		if (grade != null) {
			grade = grade.trim();
		}
		if (classid != null) {
			classid = classid.trim();
		}
		try {
			if ((grade != null) && (grade.equals(GradeLevel.ONE_LEVEL.getLevel()) || grade.equals(GradeLevel.TWO_LEVEL.getLevel()) || grade.equals(GradeLevel.THREE_LEVEL.getLevel()) || grade.equals(GradeLevel.FOUR_LEVEL.getLevel()))) {
				list = bookService.selectHotBook(grade, null);
			} else if (classid != null) {
				list = bookService.selectHotBook(null, classid);
			} else {
				list = bookService.selectHotBook(null, null);
			}
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/searchBook", method = RequestMethod.GET)
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
	 * 图书搜索
	 * 
	 * @return ResponseEntity 返回图书实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/scanCode", method = RequestMethod.GET)
	public ResponseEntity scanCode(String isbn) {
		// String username = UserContextHelper.getUsername();
		if (isbn != null) {
			isbn = isbn.trim();
		} else {
			return ResponseEntity.ok(new ArrayList<>(), "isbn为空");
		}
		List<BookShelf> list = null;
		Pattern pattern = Pattern.compile("[0-9]*");
		if (pattern.matcher(isbn).matches()) {
			list = bookService.selectFromShelfByISBN(isbn);
			if (list == null || list.isEmpty()) {
				return ResponseEntity.ok(new ArrayList<>(), "请输入正确的ISBN号");
			}
		} else {
			return ResponseEntity.ok(new ArrayList<>(), "无信息");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 查看图书详情
	 * 
	 * @return ResponseEntity 返回图书分类实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/updateHotValue", method = RequestMethod.GET)
	public ResponseEntity updateHotValue(String bookid) {
		// String username = UserContextHelper.getUsername();
		int flag = 0;
		try {
			if (StringUtils.isBlank(bookid)) {
				return ResponseEntity.illegalParam("参数错误");
			}
			flag = bookService.updateHotValue(bookid);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/selectSouvenir", method = RequestMethod.GET)
	public ResponseEntity selectSouvenir(int type) {
		// String username = UserContextHelper.getUsername();
		List<Souvenir> list = null;
		try {
			list = souvenirService.selectSouvenirAll(type);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/addDiary", method = RequestMethod.POST)
	public ResponseEntity addDiary(Diary diary) {
		// String username = UserContextHelper.getUsername();
		int flag = 0;
		try {
			if ((StringUtils.isNotBlank(diary.getTitle())) && (StringUtils.isNotBlank(diary.getContent()) || StringUtils.isNotBlank(diary.getIcon()))) {
				flag = souvenirService.addNewDiary(diary);
				return ResponseEntity.ok(flag);
			} else {
				return ResponseEntity.serverError("信息缺失");
			}
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 查看全部可见日记
	 * 
	 * @return ResponseEntity 返回日记实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/selectDiary", method = RequestMethod.GET)
	public ResponseEntity selectDiary(Integer page, Integer rows) {
		EasyuiPagination<Diary> list = null;
		try {
			list = souvenirService.selectDiaryAll(page, rows);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/selectMyDiary", method = RequestMethod.GET)
	public ResponseEntity selectMyDiary(Integer page, Integer rows) {
		String username = UserContextHelper.getUsername();
		EasyuiPagination<Diary> list = null;
		try {
			String userid = userService.findUserByUsername(username).getId();
			list = souvenirService.selectMyDiary(page, rows, userid);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 查看日记详情
	 * 
	 * @return ResponseEntity 返回日记实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/findDiaryDetail", method = RequestMethod.GET)
	public ResponseEntity findDiaryDetail(String diaryId) {
		Diary diary = null;
		try {
			diary = souvenirService.findDiaryDetail(diaryId);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(diary);
	}

	/**
	 * 添加日记评论
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/addComment", method = RequestMethod.POST)
	public ResponseEntity addComment(DiaryComment diaryComment) {
		int flag = 0;
		try {
			if (StringUtils.isBlank(diaryComment.getContent())) {
				return ResponseEntity.serverError("没有评论内容 content");
			}
			flag = souvenirService.addDiaryComment(diaryComment);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(flag);
	}

	/**
	 * 获取日记评论列表
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/findDiaryComments", method = RequestMethod.GET)
	public ResponseEntity findDiaryComments(String diaryId, Integer page, Integer rows) {
		EasyuiPagination<DiaryComment> list = null;
		try {
			if (StringUtils.isBlank(diaryId)) {
				return ResponseEntity.illegalParam("");
			}
			list = souvenirService.findDiaryComments(diaryId, page, rows);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 日记点赞
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/addLike", method = RequestMethod.POST)
	public ResponseEntity addLike(DiaryLike diaryLike) {
		try {
			int rows = souvenirService.findIsLike(diaryLike.getDiaryId());
			if (rows > 0) {
				return ResponseEntity.ok("已点赞");
			}
			souvenirService.addDiaryLike(diaryLike);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/removeLike", method = RequestMethod.POST)
	public ResponseEntity removeLike(String diaryId) {
		int flag = 0;
		try {
			int rows = souvenirService.findIsLike(diaryId);
			if (rows < 1) {
				return ResponseEntity.ok("还未点赞");
			}
			flag = souvenirService.removeDiaryLike(diaryId);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/addCollection", method = RequestMethod.POST)
	public ResponseEntity addCollection(String bookid) {
		try {
			int rows = bookService.findIsCollection(bookid);
			if (rows > 0) {
				return ResponseEntity.ok("已收藏");
			}
			bookService.addCollection(bookid);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/removeCollection", method = RequestMethod.POST)
	public ResponseEntity removeCollection(String bookid) {
		int flag = 0;
		try {
			int rows = bookService.findIsCollection(bookid);
			if (rows < 1) {
				return ResponseEntity.ok("还未收藏");
			}
			flag = bookService.removeCollection(bookid);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/findCollection", method = RequestMethod.GET)
	public ResponseEntity findCollection() {
		List<Collect> list = null;
		try {
			list = bookService.findCollection();
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/addShoppingCart", method = RequestMethod.POST)
	public ResponseEntity addShoppingCart(String productid, int numbers) {
		try {
			if (!(numbers > 0)) {
				numbers = 1;
			}

			List<ShoppingCart> list = bookService.isInShoppingCart(productid);
			bookService.addShoppingCart(productid, numbers, list);
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/removeShoppingCart", method = RequestMethod.POST)
	public ResponseEntity removeShoppingCart(String productid) {
		try {
			if (StringUtils.isBlank(productid)) {
				bookService.deleteShoppingCart();
				return ResponseEntity.ok("清空购物车成功");
			}

			List<ShoppingCart> list = bookService.isInShoppingCart(productid);
			int rows = list != null && list.size() > 0 ? 1 : 0;
			if (rows < 1) {
				return ResponseEntity.ok("已删除");
			} else {
				bookService.removeShoppingCart(productid, list);
			}
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/findShoppingCart", method = RequestMethod.GET)
	public ResponseEntity findShoppingCart() {
		List<ShoppingCart> list = null;
		try {
			list = bookService.findShoppingCart();
		} catch (Exception e) {
			log.error("", e);
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
	@RequestMapping(value = "login/submitOrder", method = RequestMethod.POST)
	public ResponseEntity submitOrder(String productids, String addressid, String enumbers) {
		Map<String, String> map = null;
		try {

			String orderNo = bookService.submitOrder(productids, addressid);
			if (orderNo != null) {
				map = bookService.computePrice(orderNo, enumbers);
				return ResponseEntity.ok(map);
			} else {
				return ResponseEntity.serverError("提交订单失败");
			}
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 计算价格
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/computePrice", method = RequestMethod.POST)
	public ResponseEntity computePrice(String productids) {
		Map<String, String> map = null;
		try {
			map = bookService.preComputePrice(productids);
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 直接提交订单
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/dirSubmitOrder", method = RequestMethod.POST)
	public ResponseEntity dirSubmitOrder(String productid, int nums, String addressid) {
		try {
			if (nums < 1) {
				nums = 1;
			}
			bookService.dirSubmitOrder(productid, nums, addressid, null);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("添加成功");
	}

	/**
	 * 获取我的订单
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/findMyOrders", method = RequestMethod.GET)
	public ResponseEntity findMyOrders() {
		List<Order> list = null;
		try {
			list = bookService.findMyOrders();
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 添加地址
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/addAddress", method = RequestMethod.POST)
	public ResponseEntity addAddress(Address address) {
		try {
			if (StringUtils.isBlank(address.getUsername()) || StringUtils.isBlank(address.getPhone())) {
				return ResponseEntity.ok("请提供明确的收件人和手机号");
			}
			bookService.addAddress(address);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("添加成功");
	}

	/**
	 * 所有地址
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/findAddress", method = RequestMethod.GET)
	public ResponseEntity findAddress(Boolean def) {
		List<Address> list = null;
		try {
			if (def == null) {
				def = false;
			}

			list = bookService.findAddress(def);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok(list);
	}

	/**
	 * 修改默认地址
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/modifyDefaultAddress", method = RequestMethod.POST)
	public ResponseEntity modifyDefaultAddress(String addressid) {
		try {
			bookService.selectDefaultAddress(addressid);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
		return ResponseEntity.ok("修改默认地址成功");
	}

	/**
	 * 删除地址
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/removeAddress", method = RequestMethod.POST)
	public ResponseEntity removeAddress(String addressid) {
		try {
			int rows = bookService.deleteAddress(addressid);
			if (rows > 0) {
				return ResponseEntity.ok("删除地址成功");
			} else {
				return ResponseEntity.ok("参数有误");
			}
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 获取学校名称
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/findCollege", method = RequestMethod.GET)
	public ResponseEntity findCollege() {
		try {
			List<String> colleges = bookService.findCollege();
			return ResponseEntity.ok(colleges);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 获取宿舍楼名称
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "unlogin/findFlat", method = RequestMethod.GET)
	public ResponseEntity findFlat(String collegename) {
		List<String> flats = null;
		try {
			if (StringUtils.isBlank(collegename)) {
				return ResponseEntity.ok("学校名称为空");
			}
			flats = bookService.findFlat(collegename);
			if (flats == null || flats.size() < 1) {
				return ResponseEntity.ok("该校没有宿舍");
			}
			return ResponseEntity.ok(flats);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 捐赠图书
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/addDonation", method = RequestMethod.POST)
	public ResponseEntity addDonation(Donation donation) {
		try {
			if (StringUtils.isBlank(donation.getBookId())) {
				return ResponseEntity.ok("未提供明确的图书信息");
			}
			int rows = bookService.addDonation(donation);
			if (rows > 0) {
				return ResponseEntity.ok("捐赠成功");
			}
			return ResponseEntity.serverError("参数有误");
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 我的捐赠
	 * 
	 * @return ResponseEntity 返回状态
	 */
	@ResponseBody
	@RequestMapping(value = "login/findMyDonation", method = RequestMethod.GET)
	public ResponseEntity findMyDonation() {
		List<Donation> list = null;
		try {
			list = bookService.findMyDonation();
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}
	}

	/**
	 * 预支付
	 * 
	 * @return ResponseEntity 返回实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/prePay", method = RequestMethod.GET)
	public ResponseEntity prePay(String orderNo, String fee, HttpServletRequest request) {
		// String username = UserContextHelper.getUsername();
		Object obj = null;
		try {
			String ip = request.getLocalAddr();
			obj = bookclassService.prePay(orderNo, fee, ip);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}

		return ResponseEntity.ok(obj);
	}

	/**
	 * 微信支付回调
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("unlogin/notify")
	@ResponseBody
	public String notify(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> resultMap = new HashMap<String, String>();
		InputStream is = null;
		try {
			is = request.getInputStream();
			if (is != null) {
				String result = IOUtils.toString(is, "utf-8");
				if (StringUtils.isEmpty(result)) {
					// 参数错误
					resultMap.put("return_code", "FAIL");
					resultMap.put("return_msg", "参数错误");
					log.info("return_msg" + "参数错误");
					return WechatCore.mapToXml(resultMap);
				} else {
					Map<String, String> paramMap = WechatCore.xmlToMap(result);
					log.info("paramMap回传参数:>>>>>>" + paramMap);
					String return_code = paramMap.get("return_code");
					if (return_code.equals("SUCCESS")) {
						// 校验签名
						// paramMap = WechatCore.paraFilter(paramMap);
						if (!WechatUtils.checkSign(result)) {
							resultMap.put("return_code", "FAIL");
							resultMap.put("return_msg", "签名错误");
							log.info("return_msg" + "签名错误");
							return WechatCore.mapToXml(resultMap);
						}
						String result_code = paramMap.get("result_code");
						if (result_code.equals("SUCCESS")) {
							// 成功支付
							// 交易成功，更新商户订单状态
							String id = paramMap.get("out_trade_no");
							OrderExample orderExample = new OrderExample();
							OrderExample.Criteria criteria = orderExample.createCriteria();
							criteria.andOrderNoEqualTo(id);
							int counts = orderMapper.countByExample(orderExample);
							log.info("counts:"+counts);
							List<Order> orders = orderMapper.selectByExample(orderExample);							
							log.info("orders:"+orders);
							if (counts < 1) {
								resultMap.put("return_code", "FAIL");
								resultMap.put("return_msg", "支付订单不存在！");
								log.info("return_msg" + "支付订单不存在！");
								return WechatCore.mapToXml(resultMap);
							}
							if (orders.get(0).getOrderStatus() == 1) {
								resultMap.put("return_code", "FAIL");
								resultMap.put("return_msg", "已支付的订单");
							}
							String attach = paramMap.get("attach");
							String total_fee = paramMap.get("total_fee");
							Integer total = Integer.parseInt(total_fee);
							log.info("total:<<<<<<<"+total);
							Order order = orders.get(0);
							if (Integer.parseInt(attach) == 2) {
								if (total == (int)Math.round(order.getValue() * 100)) {
									Order order2 = new Order();
									order2.setOrderStatus(1);
									orderMapper.updateByExampleSelective(order2, orderExample);
								} else if (total == (int)Math.round(order.getPreValue() * 100)) {
									Order order2 = new Order();
									order2.setOrderStatus(3);
									orderMapper.updateByExampleSelective(order2, orderExample);
								} else if (total == 1) {
									Order order2 = new Order();
									order2.setOrderStatus(4);
									orderMapper.updateByExampleSelective(order2, orderExample);
								} else {
									Order order2 = new Order();
									order2.setOrderStatus(5);
									orderMapper.updateByExampleSelective(order2, orderExample);
								}
								// for (Order or : orders) {
								// or.setOrderStatus(1);
								// orderMapper.updateByPrimaryKeySelective(or);
								// }
								resultMap.put("return_code", "SUCCESS");
								resultMap.put("return_msg", "OK");
								log.info("resultMap:"+resultMap);
								return WechatCore.mapToXml(resultMap);
							}
						}
					}
				}
			}
			resultMap.put("return_code", "FAIL");
			resultMap.put("return_msg", "系统错误");
			log.info("resultMap:"+resultMap);
			return WechatCore.mapToXml(resultMap);
		} catch (IOException e) {
			e.printStackTrace();
			resultMap.put("return_code", "FAIL");
			resultMap.put("return_msg", "系统错误");
			return WechatCore.mapToXml(resultMap);
		}
	}

	/**
	 * 发送手机验证码
	 *
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param phoneNumber
	 *            手机号
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping(value = "login/sendSmsCaptcha", method = RequestMethod.GET)
	public ResponseEntity sendSmsCaptcha(HttpServletRequest request, HttpServletResponse response, String phoneNumber) {
		HttpSession session = request.getSession();
		if (StringUtils.isBlank(phoneNumber)) {
			return ResponseEntity.serverError("手机号为空！");
		}
		if (!userService.isBindThisMobile(phoneNumber)) {
			return ResponseEntity.ok("该手机号已被绑定,请更换手机!");
		}
		try {
			String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
			ResponseEntity result = captchaService.isCanSend(userid, phoneNumber, false);
			if (result == null) {
				String captcha = CaptchaUtils.generate();
				SmsSendResponse res = ChuangLanSmsUtil.sendMessage(SMSType.templateSMS(SMSType.EB_TITLE, captcha), phoneNumber);
				log.info("响应状态:" + res);
				if (res.getCode().equals("0")) {
					SMSCaptcha sms = new SMSCaptcha(phoneNumber, captcha);
					captchaService.setSMSCaptchaToSession(sms, session);
					log.info("发送短信验证码完成！{}", sms);
					return ResponseEntity.ok();
				} else {
					SmsBalanceResponse smsbalance = ChuangLanSmsUtil.selectBlance();
					log.info(smsbalance);
					log.info("余额状态:" + smsbalance.getBalance());
					if (Integer.parseInt(smsbalance.getBalance()) == 0) {
						return ResponseEntity.serverError("余额不足");
					}
					return ResponseEntity.serverError(res.getErrorMsg());
				}
			} else {
				return result;
			}
		} catch (Exception e) {
			return ResponseEntity.serverError();
		}

	}

	/**
	 * 绑定手机号
	 *
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param phoneNumber
	 *            手机号
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping(value = "login/bindMobile", method = RequestMethod.POST)
	public ResponseEntity bindMobile(HttpServletRequest request, String captcha, String phoneNumber) {
		HttpSession session = request.getSession();
		if (StringUtils.isBlank(captcha)) {
			return ResponseEntity.serverError("短信验证码为空！");
		}
		if (StringUtils.isBlank(phoneNumber)) {
			return ResponseEntity.serverError("手机号为空！");
		}
		if (!userService.isBindThisMobile(phoneNumber)) {
			return ResponseEntity.ok("该手机号已被绑定,请更换手机!");
		}
		try {
			SMSCaptcha sms = new SMSCaptcha(phoneNumber, captcha);
			Boolean flag = captchaService.validateSMSCaptcha(sms, session);
			if (flag) {
				int rows = userService.bindMoidle(phoneNumber);
				if (rows > 0) {
					log.info("{} 手机号绑定成功！", phoneNumber);
					return ResponseEntity.ok();
				}
				return ResponseEntity.serverError();
			} else {
				return ResponseEntity.serverError("验证码错误！");
			}
		} catch (Exception e) {
			return ResponseEntity.serverError();
		}

	}

	/**
	 * 获取签名
	 * 
	 * @return ResponseEntity 返回实体
	 */
	@ResponseBody
	@RequestMapping(value = "login/findSign", method = RequestMethod.GET)
	public ResponseEntity findSign(String targetUrl) {
		// String username = UserContextHelper.getUsername();
		Object obj = null;
		try {
			obj = bookclassService.findSign(targetUrl);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.serverError("操作失败");
		}

		return ResponseEntity.ok(obj);
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login/uploadImg", method = RequestMethod.POST)
	public ResponseEntity uploadImg(HttpServletRequest request) {
		String contentType = request.getContentType();
		System.out.println(contentType);
		String userName = UserContextHelper.getUsername();
		if (StringUtils.isBlank(userName)) {
			return ResponseEntity.serverError("请先登录");
		}
		try {
			List<String> sufferList = new ArrayList<>();
			sufferList.add("jpg");
			sufferList.add("png");
			sufferList.add("gif");
			sufferList.add("bmp");
			sufferList.add("jpeg");
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			Map<String, List<String>> fileImgMap = new HashMap<>();
			List<MultipartFile> valueList = new ArrayList<>();
			int vainIndex = 0;
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile((String) iter.next());
					if (file != null) {
						String suffer = FileUpLoadUtils.getFileSuffix(file);
						if (sufferList.contains(suffer.toLowerCase())) {
							valueList.add(file);
						} else {
							vainIndex++;
						}
					}
				}
				if (vainIndex > 0) {
					return ResponseEntity.serverError("只支持jpg,png,gif,bmp,jpeg格式的图片, 请检查!");
				}
				if (valueList.size() == 0) {
					return ResponseEntity.serverError("没有要上传的图片");
				}
				String PATH = "/var/ebook/image/";
				String imgPath = PATH + "gallery/";
				String bigPath = PATH + "biggallery/";
				List<String> imglist = new ArrayList<>();
				for (MultipartFile file : valueList) {
					String newFileName = StringConsts.randomFileName().substring(15);

					String smallPaths = FileUpLoadUtils.writeFile(file, imgPath, newFileName, true);
					imglist.add(smallPaths);
					FileUpLoadUtils.writeFile(file, bigPath, newFileName, false);
				}
				fileImgMap.put("imgpaths", imglist);
			}
			log.info("图片上传成功,上传路径:", new Object[] { fileImgMap.toString() });
			return ResponseEntity.ok(fileImgMap, "上传图片成功");
		} catch (Exception e) {
			log.error("上传图片异常!", e);
		}
		return ResponseEntity.serverError("上传图片异常");
	}

}
