package com.zhijian.ebook.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.AddressMapper;
import com.zhijian.ebook.dao.BookClassMapper;
import com.zhijian.ebook.dao.BookMapper;
import com.zhijian.ebook.dao.CollectMapper;
import com.zhijian.ebook.dao.DonationMapper;
import com.zhijian.ebook.dao.FlatMapper;
import com.zhijian.ebook.dao.OrderMapper;
import com.zhijian.ebook.dao.ShoppingCartMapper;
import com.zhijian.ebook.dao.SouvenirMapper;
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.entity.AddressExample;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookExample;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.CollectExample;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.DonationExample;
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

	@Override
	public List<Book> selectHotBook(String grade, String classid) {
		List<Book> list = null;
		BookExample example = new BookExample();
		BookExample.Criteria criteria = example.createCriteria();
		if (grade != null) {
			criteria.andGradeEqualTo(grade);
		}
		if (classid != null) {
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
	public int updateHotValue(String bookid) {
		Book book = bookMapper.selectByPrimaryKey(bookid);
		Book newbook = new Book();
		newbook.setId(bookid);
		newbook.setHotValue(book.getHotValue() + 1);
		return bookMapper.updateByPrimaryKeySelective(newbook);
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
	public int submitOrder(String[] productids, String addressid) throws Exception {
		int flag = 0;
		for (String productidnum : productids) {
			String productid = StringUtils.substringBefore(productidnum, StringConsts.COMMA);
			int nums = Integer.parseInt(StringUtils.substringAfter(productidnum, StringConsts.COMMA));
			dirSubmitOrder(productid, nums, addressid);
			flag++;
		}
		return flag == productids.length ? 1 : 0;

	}

	@Override
	public int dirSubmitOrder(String productid, int nums, String addressid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
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
			order.setDepPrice(book.getDeposit());
			order.setDesposit(book.getDeposit());
		}
		order.setProductId(productid);
		order.setAddressId(addressid);
		order.setCount(nums);
		order.setIsValid(true);
		order.setOrderNo(getRandomOrderNO());
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
		list = orderMapper.selectByExample(example);
		return list;
	}

	@Override
	public int addDonation(Donation donation) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		Book book = bookMapper.selectByPrimaryKey(donation.getBookId());
		Address address = addressMapper.selectByPrimaryKey(donation.getAddressId());
		if (book != null && address != null) {
				donation.setAuthor(book.getAuthor());
				donation.setBookIcon(book.getIcon());
				donation.setBookName(book.getTitle());
				donation.setCreateTime(new Date());
				donation.seteValue(book.getePrice());
				donation.setIsbn(book.getIsbn());
				donation.setIsValid(true);
				donation.setPublisher(book.getPublisher());
				donation.setStatus(0);
				donation.setUserid(userid);
				donationMapper.insert(donation);
				return 1;
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
			criteria.andTitleLike("%"+book.getTitle()+"%");
		}
		if (StringUtils.isNotBlank(book.getIsbn())) {
			criteria.andIsbn10EqualTo(book.getIsbn());
		}
		if (StringUtils.isNotBlank(book.getClassId())) {
			criteria.andClassIdEqualTo(book.getClassId());
		}
		bookExample.setOrderByClause("hot_value desc");
		List<Book> list = bookMapper.findPaginationList(new Page(page, rows),bookExample);
		
		return new EasyuiPagination<Book>(list.size(),list);
	}

	@Override
	public int addBook(Book book) {
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
		return bookMapper.updateByPrimaryKeySelective(book);
	}

}
