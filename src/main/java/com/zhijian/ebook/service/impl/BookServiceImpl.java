package com.zhijian.ebook.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.AddressMapper;
import com.zhijian.ebook.dao.BookClassMapper;
import com.zhijian.ebook.dao.BookMapper;
import com.zhijian.ebook.dao.BookShelfMapper;
import com.zhijian.ebook.dao.CollectMapper;
import com.zhijian.ebook.dao.DonationMapper;
import com.zhijian.ebook.dao.FlatMapper;
import com.zhijian.ebook.dao.MajorMapper;
import com.zhijian.ebook.dao.OrderMapper;
import com.zhijian.ebook.dao.ShoppingCartMapper;
import com.zhijian.ebook.dao.SouvenirMapper;
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.entity.AddressExample;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookExample;
import com.zhijian.ebook.entity.BookShelf;
import com.zhijian.ebook.entity.BookShelfExample;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.CollectExample;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.DonationExample;
import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.entity.Order;
import com.zhijian.ebook.entity.OrderExample;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.entity.ShoppingCartExample;
import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.BookService;
import com.zhijian.ebook.util.StringConsts;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private BookClassMapper bookClassMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private CollectMapper collectMapper;

	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	@Autowired
	private SouvenirMapper souvenirMapper;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private FlatMapper flatMapper;

	@Autowired
	private DonationMapper donationMapper;

	@Autowired
	private BookShelfMapper bookShelfMapper;

	@SuppressWarnings("unused")
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MajorMapper majorMapper;

	@Override
	public List<Book> selectHotBook(String collegeName, String academyName, String professionName, String grade, String classid) {
		Calendar calendar = Calendar.getInstance();
		// 获得当前时间的月份，月份从0开始所以结果要加1
		int month = calendar.get(Calendar.MONTH) + 1;
		List<Book> list = null;
		BookExample example = new BookExample();
		BookExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(collegeName)) {
			criteria.andCollegeEqualTo(collegeName);
		}
		if (StringUtils.isNotBlank(academyName)) {
			criteria.andAcademyEqualTo(academyName);
		}
		if (StringUtils.isNotBlank(professionName)) {
			criteria.andProfessionEqualTo(professionName);
		}
		if (StringUtils.isNotBlank(grade)) {
			if (grade.indexOf("上") == -1 && grade.indexOf("下") == -1) {
				if (month > 7) {
					grade = grade + "上";
				} else {
					grade = grade + "下";
				}
			}
			criteria.andGradeEqualTo(grade);
		}
		if (StringUtils.isNotBlank(classid)) {
			BookClass bookClass = bookClassMapper.selectByPrimaryKey(classid);
			if (bookClass != null) {
				criteria.andClassIdEqualTo(classid);
			}
		}
		example.setOrderByClause("hot_value desc, title asc");
		list = bookMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Book> selectByISBN(String content) {
		List<Book> list = null;
		BookExample example = new BookExample();
		BookExample.Criteria criteria = example.createCriteria();
		criteria.andIsbnEqualTo(content);
		example.setOrderByClause("hot_value desc");
		list = bookMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Book> selectByBookName(String content) {
		List<Book> list = null;
		BookExample example = new BookExample();
		BookExample.Criteria criteria = example.createCriteria();
		criteria.andTitleLike("%" + content + "%");
		example.setOrderByClause("hot_value desc, title asc");
		list = bookMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<Book> selectByAuthor(String content) {
		List<Book> list = null;
		BookExample example = new BookExample();
		BookExample.Criteria criteria = example.createCriteria();
		criteria.andAuthorLike("%" + content + "%");
		example.setOrderByClause("hot_value desc, title asc");
		list = bookMapper.selectByExample(example);
		return list;
	}

	@Override
	public Book updateHotValue(String bookid) {
		Book book = bookMapper.selectByPrimaryKey(bookid);
		if (book == null) {
			return null;
		}
		Book newbook = new Book();
		newbook.setId(bookid);
		newbook.setHotValue(book.getHotValue() + 1);
		bookMapper.updateByPrimaryKeySelective(newbook);
		return book;
	}

	@Override
	public int findIsCollection(String bookid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		CollectExample collectExample = new CollectExample();
		CollectExample.Criteria criteria = collectExample.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andBookIdEqualTo(bookid);
		return collectMapper.countByExample(collectExample);
	}

	@Override
	public int addCollection(String bookid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		Book book = bookMapper.selectByPrimaryKey(bookid);
		Collect collect = new Collect();
		collect.setBookIcon(book.getIcon());
		collect.setBookId(bookid);
		collect.setBookName(book.getTitle());
		collect.seteValue(book.getePrice());
		collect.setUserid(userid);
		return collectMapper.insert(collect);
	}

	@Override
	public int removeCollection(String bookid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		CollectExample collectExample = new CollectExample();
		CollectExample.Criteria criteria = collectExample.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andBookIdEqualTo(bookid);
		return collectMapper.deleteByExample(collectExample);
	}

	@Override
	public List<Collect> findCollection() throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		CollectExample collectExample = new CollectExample();
		CollectExample.Criteria criteria = collectExample.createCriteria();
		criteria.andUseridEqualTo(userid);
		return collectMapper.selectByExample(collectExample);
	}

	@Override
	public List<ShoppingCart> isInShoppingCart(String productid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCartExample example = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo(productid);
		criteria.andUseridEqualTo(userid);
		List<ShoppingCart> list = shoppingCartMapper.selectByExample(example);
		return list;

		// if (list != null && list.size() > 0) {
		// if (flag) {
		// ShoppingCart shoppingCart = list.get(0);
		// shoppingCart.setCount(shoppingCart.getCount() + numbers);
		// shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
		// }else {
		// shoppingCartMapper.deleteByExample(example);
		// }
		// return 1;
		//
		// } else {
		// return 0;
		// }
	}

	@Override
	public int addShoppingCart(String productid, int numbers, List<ShoppingCart> list) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		if (list != null && list.size() > 0) {
			ShoppingCart shoppingCart = list.get(0);
			shoppingCart.setCount(shoppingCart.getCount() + numbers);
			shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
			return 1;
		} else {
			ShoppingCart shoppingCart = new ShoppingCart();
			Book book = bookMapper.selectByPrimaryKey(productid);
			if (book == null) {
				Souvenir souvenir = souvenirMapper.selectByPrimaryKey(productid);
				// 纪念品type =0
				shoppingCart.setProductType((byte) 0);
				shoppingCart.setProductIcon(souvenir.getIcon());
				shoppingCart.setProductName(souvenir.getName());
				shoppingCart.setProductPrice(souvenir.getPrice());
				shoppingCart.setDepPrice(0.0);
				shoppingCart.setDesposit(0.0);
			} else {
				// 图书 type = 1
				shoppingCart.setProductType((byte) 1);
				shoppingCart.setProductIcon(book.getIcon());
				shoppingCart.setProductName(book.getTitle());
				shoppingCart.setProductPrice(book.getePrice());
				shoppingCart.setDepPrice(book.getDepPrice());
				shoppingCart.setDesposit(book.getDeposit());
			}
			shoppingCart.setProductId(productid);
			shoppingCart.setUserid(userid);
			shoppingCart.setCount(numbers);
			shoppingCart.setCreateTime(new Date());
			shoppingCart.setIsValid(true);
			return shoppingCartMapper.insert(shoppingCart);
		}
	}

	@Override
	public int removeShoppingCart(String productid, List<ShoppingCart> list) throws Exception {
		return shoppingCartMapper.deleteByPrimaryKey(list.get(0).getId());
	}

	@Override
	public List<ShoppingCart> findShoppingCart() throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCartExample example = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andIsValidEqualTo(true);
		example.setOrderByClause("create_time desc");
		List<ShoppingCart> list = null;
		list = shoppingCartMapper.selectByExample(example);

		return list;
	}

	@Override
	public String submitOrder(String productids, String addressid) throws Exception {
		String[] product = productids.split(";");
		String orderNo = getRandomOrderNO();
		for (String productidnum : product) {
			String productid = StringUtils.substringBefore(productidnum, StringConsts.COMMA);
			int nums = Integer.parseInt(StringUtils.substringAfter(productidnum, StringConsts.COMMA));
			dirSubmitOrder(productid, nums, addressid, orderNo);
		}
		return orderNo;
	}

	@Override
	public int dirSubmitOrder(String productid, int nums, String addressid, String orderNo) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		if (orderNo == null) {
			orderNo = getRandomOrderNO();
		}

		Order order = new Order();
		Book book = bookMapper.selectByPrimaryKey(productid);
		Souvenir souvenir = null;
		if (book == null) {
			souvenir = souvenirMapper.selectByPrimaryKey(productid);
			order.setProductIcon(souvenir.getIcon());
			order.setProductName(souvenir.getName());
			order.setProductPrice(souvenir.getPrice());
			order.setProductType((byte) 0);
			order.setDepPrice(0.0);
			order.setDesposit(0.0);
		} else {
			order.setProductIcon(book.getIcon());
			order.setProductName(book.getTitle());
			order.setProductPrice(book.getePrice());
			order.setProductType((byte) 1);
			order.setDepPrice(book.getDepPrice());
			order.setDesposit(book.getDeposit());

			Book value = bookMapper.selectByPrimaryKey(productid);
			Book newbook = new Book();
			newbook.setId(productid);
			newbook.setHotValue(5 * nums + value.getHotValue());
			bookMapper.updateByPrimaryKeySelective(newbook);
		}
		order.setProductId(productid);
		order.setAddressId(addressid);
		order.setCount(nums);
		order.setIsValid(true);
		order.setOrderNo(orderNo);
		order.setUserid(userid);
		order.setOrderStatus(0);

		ShoppingCartExample shopExample = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = shopExample.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andProductIdEqualTo(productid);
		shoppingCartMapper.deleteByExample(shopExample);
		return orderMapper.insert(order);
	}

	public static String getRandomOrderNO() {
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		return rannum + str;// 当前时间 }
	}

	@Override
	public int addAddress(Address address) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		address.setUserid(userid);
		if (StringUtils.isNotBlank(address.getId())) {
			address.setIsDefault(addressMapper.selectByPrimaryKey(address.getId()).getIsDefault());

			return addressMapper.updateByPrimaryKey(address);
		}
		AddressExample example = new AddressExample();
		AddressExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		int counts = addressMapper.countByExample(example);
		if (counts > 0) {
			address.setIsDefault(false);
		} else {
			address.setIsDefault(true);
		}

		return addressMapper.insert(address);
	}

	@Override
	public List<Address> findAddress(Boolean def) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		AddressExample example = new AddressExample();
		AddressExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		if (def) {
			example.setOrderByClause(" is_default desc, create_time asc limit 1");
		} else {
			example.setOrderByClause(" is_default desc, create_time asc");
		}
		List<Address> list = addressMapper.selectByExample(example);
		return list;
	}

	@Override
	public int selectDefaultAddress(String addressid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		AddressExample example = new AddressExample();
		AddressExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andIsDefaultEqualTo(true);
		List<Address> list = addressMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			Address oldaddress = list.get(0);
			oldaddress.setIsDefault(false);
			addressMapper.updateByPrimaryKeySelective(oldaddress);
		}
		Address newAddress = addressMapper.selectByPrimaryKey(addressid);
		newAddress.setIsDefault(true);

		return addressMapper.updateByPrimaryKeySelective(newAddress);
	}

	@Override
	public int deleteAddress(String addressid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		AddressExample example = new AddressExample();
		AddressExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andIdEqualTo(addressid);
		int counts = addressMapper.countByExample(example);
		return counts > 0 ? addressMapper.deleteByPrimaryKey(addressid) : 0;
	}

	@Override
	public List<String> findCollege() throws Exception {
		List<String> list = null;
		list = flatMapper.selectCollege();
		return list;
	}

	@Override
	public List<String> findFlat(String collegename) {
		List<String> list = null;
		list = flatMapper.selectFlat(collegename);
		return list;
	}

	@Override
	public List<Order> findMyOrders() throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		List<Order> list = null;
		OrderExample example = new OrderExample();
		OrderExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andIsValidEqualTo(true);
		example.setOrderByClause("create_time desc");
		list = orderMapper.selectByExample(example);
		return list;
	}

	@Override
	public int addDonation(Donation donation) throws Exception {
		User user = userService.findUserByUsername(UserContextHelper.getUsername());
		String userid = user.getId();
		Book book = bookMapper.selectByPrimaryKey(donation.getBookId());
		BookShelf bookShelf = bookShelfMapper.selectByPrimaryKey(donation.getBookId());
		Address address = addressMapper.selectByPrimaryKey(donation.getAddressId());
		if (book != null && address != null) {
			donation.setAuthor(book.getAuthor());
			donation.setBookIcon(book.getIcon());
			donation.setBookName(book.getTitle());
			donation.setCreateTime(new Date());
			donation.seteValue(book.getGetEprice());
			donation.setIsbn(book.getIsbn());
			donation.setIsValid(true);
			donation.setPublisher(book.getPublisher());
			donation.setStatus(0);
			donation.setUserid(userid);
			donationMapper.insert(donation);
			// user.setBlance(user.getBlance()+book.getePrice());
			// userMapper.updateByPrimaryKeySelective(user);
			return 1;
		} else if (bookShelf != null && address != null) {
			donation.setAuthor(bookShelf.getAuthor());
			donation.setBookIcon(bookShelf.getIcon());
			donation.setBookName(bookShelf.getTitle());
			donation.setCreateTime(new Date());
			donation.seteValue(bookShelf.getePrice());
			donation.setIsbn(bookShelf.getIsbn());
			donation.setIsValid(true);
			donation.setPublisher(bookShelf.getPublisher());
			donation.setStatus(0);
			donation.setUserid(userid);
			donationMapper.insert(donation);
		}

		return 0;
	}

	@Override
	public List<Donation> findMyDonation() throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		List<Donation> list = null;
		DonationExample example = new DonationExample();
		DonationExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andIsValidEqualTo(true);
		example.setOrderByClause("create_time desc");
		list = donationMapper.selectByExample(example);
		return list;
	}

	@Override
	public int deleteShoppingCart() throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCartExample example = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		criteria.andIsValidEqualTo(true);
		return shoppingCartMapper.deleteByExample(example);
	}

	@Override
	public EasyuiPagination<Book> findBookPagination(Book book, Integer page, Integer rows) {
		BookExample bookExample = new BookExample();
		BookExample.Criteria criteria = bookExample.createCriteria();
		if (StringUtils.isNotBlank(book.getTitle())) {
			criteria.andTitleLike("%" + book.getTitle() + "%");
		}
		if (StringUtils.isNotBlank(book.getIsbn())) {
			criteria.andIsbn10EqualTo(book.getIsbn());
		}
		if (StringUtils.isNotBlank(book.getClassId())) {
			criteria.andClassIdEqualTo(book.getClassId());
		}
		if (StringUtils.isNotBlank(book.getGrade())) {
			criteria.andGradeEqualTo(book.getGrade());
		}
		if (StringUtils.isNotBlank(book.getAuthor())) {
			criteria.andAuthorEqualTo("%" + book.getAuthor() + "%");
		}
		bookExample.setOrderByClause("hot_value desc");
		List<Book> list = bookMapper.findPaginationList(new Page(page, rows), bookExample);
		int counts = bookMapper.countByExample(bookExample);
		return new EasyuiPagination<Book>(counts, list);
	}

	@Override
	public int addBook(Book book) {
		BookClass bookClass = bookClassMapper.selectByPrimaryKey(book.getClassId());
		book.setClassName(bookClass.getName());
		return bookMapper.insert(book);
	}

	@Override
	public int removeBookById(String id) {
		return bookMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Book findBookById(String id) {
		return bookMapper.selectByPrimaryKey(id);
	}

	@Override
	public int modifyBook(Book book) {
		if (StringUtils.isNotBlank(book.getClassId())) {
			BookClass bookClass = bookClassMapper.selectByPrimaryKey(book.getClassId());
			book.setClassName(bookClass.getName());
		}
		return bookMapper.updateByPrimaryKeySelective(book);
	}

	@Override
	public List<BookClass> findClassNameList() {

		return bookClassMapper.selectByExample(null);
	}

	@Override
	public Map<String, String> computePrice(String orderNo, String enumbers) {
		User user = null;
		if (StringUtils.isBlank(enumbers)) {
			enumbers = "0";
		}
		try {
			user = userService.findUserByUsername(UserContextHelper.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<>();
		List<Order> list = null;
		OrderExample example = new OrderExample();
		OrderExample.Criteria criteria = example.createCriteria();
		criteria.andOrderNoEqualTo(orderNo);
		criteria.andUseridEqualTo(user.getId());
		criteria.andIsValidEqualTo(true);
		list = orderMapper.selectByExample(example);
		int value = 0;
		int prevalue = 0;
		int eamount = 0;
		int enumber = (int) Double.parseDouble(enumbers) * 100;
		for (Order order : list) {
			value += ((int) (order.getDepPrice() * 100) + (int) (order.getDesposit() * 100) + (int) (order.getProductPrice() * 100)) * order.getCount();
			eamount += (int) (order.getProductPrice() * 100);
			if (order.getProductType() == 1) {
				prevalue += order.getDepPrice() * order.getCount();
			} else {
				prevalue += 3 * order.getCount();
			}
		}
		if (enumber > 0) {
			if (enumber > eamount || enumber > (int) (user.getBlance() * 100)) {
				enumber = eamount > (int) (user.getBlance() * 100) ? (int) (user.getBlance() * 100) : eamount;
			}
//			user.setBlance(((int) (user.getBlance() * 100) - enumber) / 100.0);
//			userMapper.updateByPrimaryKeySelective(user);
		}
		map.put("value", (int) (value - enumber) / 100.0 + "");
		map.put("prevalue", prevalue + "");
		map.put("orderNo", orderNo);
		Order order = new Order();
		order.setValue(value / 100.0);
		order.setPreValue(prevalue * 1.0);
		order.setPayEvalue(enumber / 100.0);
		orderMapper.updateByExampleSelective(order, example);
		return map;
	}

	@Override
	public List<BookShelf> selectFromShelfByISBN(String isbn) {
		List<BookShelf> list = null;
		BookShelfExample example = new BookShelfExample();
		BookShelfExample.Criteria criteria = example.createCriteria();
		criteria.andIsbnEqualTo(isbn);
		list = bookShelfMapper.selectByExample(example);
		return list;
	}

	@Override
	public Map<String, String> preComputePrice(String productids) {
		Map<String, String> map = new HashMap<>();
		int zujin = 0;
		int zhejiu = 0;
		int yajin = 0;
		String[] product = productids.split(";");
		for (String productidnum : product) {
			String productid = StringUtils.substringBefore(productidnum, StringConsts.COMMA);
			int nums = Integer.parseInt(StringUtils.substringAfter(productidnum, StringConsts.COMMA));
			Book book = bookMapper.selectByPrimaryKey(productid);
			if (book != null) {
				zujin += book.getePrice() * 100.0 * nums;
				zhejiu += book.getDepPrice() * 100.0 * nums;
				yajin += book.getDeposit() * 100.0 * nums;
			} else {
				Souvenir souvenir = souvenirMapper.selectByPrimaryKey(productid);
				zujin += souvenir.getPrice() * 100.0 * nums;
			}

			// dirSubmitOrder(productid, nums, addressid, orderNo);
		}

		map.put("zujin", zujin / 100.0 + "");
		map.put("zhejiu", zhejiu / 100.0 + "");
		map.put("yajin", yajin / 100.0 + "");
		map.put("amount", (zujin + zhejiu + yajin) / 100.0 + "");
		return map;
	}

	@Override
	public List<Major> findAcademy(String collegeName) {
		return majorMapper.selectAcademy(collegeName);
	}

	@Override
	public List<Major> findCollegeList() {
		return majorMapper.selectCollegeList();
	}

	@Override
	public List<Major> findProfession(String collegeName, String academyName) {
		return majorMapper.selectProfession(academyName, collegeName);
	}

}
