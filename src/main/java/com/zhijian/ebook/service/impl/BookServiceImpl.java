package com.zhijian.ebook.service.impl;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.dao.BookClassMapper;
import com.zhijian.ebook.dao.BookMapper;
import com.zhijian.ebook.dao.CollectMapper;
import com.zhijian.ebook.dao.ShoppingCartMapper;
import com.zhijian.ebook.dao.SouvenirMapper;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookExample;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.CollectExample;
import com.zhijian.ebook.entity.Order;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.entity.ShoppingCartExample;
import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.interfaces.BookInterface;
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
	public int isInShoppingCart(String productid,int numbers, boolean flag) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCartExample example = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = example.createCriteria();
		criteria.andProductIconEqualTo(productid);
		criteria.andUseridEqualTo(userid);
		List<ShoppingCart> list = shoppingCartMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			if (flag) {
				ShoppingCart shoppingCart = list.get(0);
				shoppingCart.setCount(shoppingCart.getCount() + numbers);
				shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
			}else {
				shoppingCartMapper.deleteByExample(example);
			}
			return 1;
			
		} else {
			return 0;
		}
	}

	@Override
	public int addShoppingCart(String productid,int numbers) throws Exception{		
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCart shoppingCart = new ShoppingCart();
		Book book = bookMapper.selectByPrimaryKey(productid);
		if (book == null) {
			Souvenir souvenir = souvenirMapper.selectByPrimaryKey(productid);
			shoppingCart.setProductType((byte) 0);
			shoppingCart.setProductIcon(souvenir.getIcon());
			shoppingCart.setProductName(souvenir.getName());
			shoppingCart.setProductPrice(souvenir.getPrice());
		} else {
			shoppingCart.setProductType((byte) 1);
			shoppingCart.setProductIcon(book.getIcon());
			shoppingCart.setProductName(book.getTitle());
			shoppingCart.setProductPrice(book.getePrice());
		}

		shoppingCart.setUserid(userid);
		shoppingCart.setCount(numbers);
		shoppingCart.setCreateTime(new Date());
		shoppingCart.setIsValid(true);
		return shoppingCartMapper.insert(shoppingCart);
	}

	@Override
	public int removeShoppingCart(String productid) throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCartExample example = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = example.createCriteria();
		criteria.andProductIconEqualTo(productid);
		criteria.andUseridEqualTo(userid);
		return shoppingCartMapper.deleteByExample(example);
	}

	@Override
	public List<ShoppingCart> findShoppingCart() throws Exception {
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		ShoppingCartExample example = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		example.setOrderByClause("create_time desc");
		List<ShoppingCart> list = null;
		list = shoppingCartMapper.selectByExample(example);
		
		return list;
	}

	@Override
	public int submitOrder(String productids) throws Exception {
		List<String> productlist = Arrays.asList(productids.split(StringConsts.COMMA));
		if (productlist.size()<2) {
			Book book = bookMapper.selectByPrimaryKey(productlist.get(0));
			if (book!=null) {
				Order order = new Order();
			}
		}
		
		
		return 0;
	}

	@Override
	public int dirSubmitOrder(String productid, int nums) {
		Book book = bookMapper.selectByPrimaryKey(productid);
		if (book==null) {
			Souvenir souvenir = souvenirMapper.selectByPrimaryKey(productid);
		}
		
		return 0;
	}
	
	
	public static String getRandomOrderNO() {
		  SimpleDateFormat simpleDateFormat;
		  simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		  Date date = new Date();
		  String str = simpleDateFormat.format(date);
		  Random random = new Random();
		  int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		  return rannum + str;// 当前时间  }
		  }

}
