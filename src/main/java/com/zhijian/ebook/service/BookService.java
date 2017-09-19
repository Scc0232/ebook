package com.zhijian.ebook.service;

import java.util.List;

import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.ShoppingCart;

public interface BookService {

	List<Book> selectHotBook(String grade, String classid);

	List<Book> selectByISBN(String content);

	List<Book> selectByBookName(String content);

	List<Book> selectByAuthor(String content);

	int updateHotValue(String bookid);

	int findIsCollection(String bookid) throws Exception;

	int addCollection(String bookid) throws Exception;

	int removeCollection(String bookid) throws Exception;

	List<Collect> findCollection() throws Exception;

	int isInShoppingCart(String productid,int numbers, boolean flag) throws Exception;

	int addShoppingCart(String productid,int numbers ) throws Exception;

	int removeShoppingCart(String productid) throws Exception;

	List<ShoppingCart> findShoppingCart() throws Exception;

	int submitOrder(String productids) throws Exception;

	int dirSubmitOrder(String productid, int nums);

}
