package com.zhijian.ebook.service;

import java.util.List;
import java.util.Map;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookShelf;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.entity.Order;
import com.zhijian.ebook.entity.ShoppingCart;

public interface BookService {

	List<Book> selectHotBook(String collegeName, String academyName, String professionName,String grade, String classid);

	List<Book> selectByISBN(String content);

	List<Book> selectByBookName(String content);

	List<Book> selectByAuthor(String content);

	int updateHotValue(String bookid);

	int findIsCollection(String bookid) throws Exception;

	int addCollection(String bookid) throws Exception;

	int removeCollection(String bookid) throws Exception;

	List<Collect> findCollection() throws Exception;

	List<ShoppingCart> isInShoppingCart(String productid) throws Exception;

	int addShoppingCart(String productid,int numbers,List<ShoppingCart>  list) throws Exception;

	int removeShoppingCart(String productid, List<ShoppingCart> list) throws Exception;

	List<ShoppingCart> findShoppingCart() throws Exception;

	String submitOrder(String productids, String addressid) throws Exception;

	int dirSubmitOrder(String productid, int nums, String addressid, String orderNo) throws Exception;

	int addAddress(Address address) throws Exception;

	List<Address> findAddress(Boolean def) throws Exception;

	int selectDefaultAddress(String addressid) throws Exception;

	int deleteAddress(String addressid) throws Exception;

	List<String> findCollege() throws Exception;

	List<String> findFlat(String collegename);

	List<Order> findMyOrders() throws Exception;

	int addDonation(Donation donation) throws Exception;

	List<Donation> findMyDonation() throws Exception;

	int deleteShoppingCart() throws Exception;

	EasyuiPagination<Book> findBookPagination(Book book, Integer page, Integer rows) ;

	int addBook(Book book);

	int removeBookById(String id);

	Book findBookById(String id);

	int modifyBook(Book book);

	List<BookClass> findClassNameList();

	Map<String, String> computePrice(String orderNo, String enumbers);

	List<BookShelf> selectFromShelfByISBN(String isbn);

	Map<String, String> preComputePrice(String productids);

	List<String> findAcademy(String collegeName);

	List<Major> findCollegeList();

	List<String> findProfession(String collegeName, String academyName);

}
