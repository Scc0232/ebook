package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.BookShelfMapper;
import com.zhijian.ebook.entity.BookShelf;
import com.zhijian.ebook.entity.BookShelfExample;
import com.zhijian.ebook.service.BookShelfService;

@Service
public class BookShelfServiceImpl implements BookShelfService {
	
	@Autowired
	private BookShelfMapper bookshelfMapper;
	
	@Override
	public EasyuiPagination<BookShelf> findBookShelfPagination(BookShelf book, Integer page, Integer rows) {
		BookShelfExample bookExample = new BookShelfExample();
		BookShelfExample.Criteria criteria = bookExample.createCriteria();
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
		List<BookShelf> list = bookshelfMapper.findPaginationList(new Page(page, rows), bookExample);
		int counts = bookshelfMapper.countByExample(bookExample);
		return new EasyuiPagination<BookShelf>(counts, list);
	}

	@Override
	public BookShelf findBookShelfById(String bookShelfid) {
		return bookshelfMapper.selectByPrimaryKey(bookShelfid);
	}

	@Override
	public int addBookShelf(BookShelf bookShelf) {
		return bookshelfMapper.insert(bookShelf);
	}

	@Override
	public int modifyBookShelf(BookShelf bookShelf) {
		return bookshelfMapper.updateByPrimaryKeySelective(bookShelf);
	}

	@Override
	public int removeBookShelfById(String id) {
		return bookshelfMapper.deleteByPrimaryKey(id);
	}

}
