package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.BookShelf;

public interface BookShelfService {

	EasyuiPagination<BookShelf> findBookShelfPagination(BookShelf bookShelf, Integer page, Integer rows);

	BookShelf findBookShelfById(String bookShelfid);

	int addBookShelf(BookShelf bookShelf);

	int modifyBookShelf(BookShelf bookShelf);

	int removeBookShelfById(String id);

}
