package com.zhijian.ebook.service;

import java.util.List;

import com.zhijian.ebook.entity.Book;

public interface BookService {

	List<Book> selectHotBook(String grade, String classid);

	List<Book> selectByISBN(String content);

	List<Book> selectByBookName(String content);

	List<Book> selectByAuthor(String content);

	int updateHotValue(String bookid);

}
