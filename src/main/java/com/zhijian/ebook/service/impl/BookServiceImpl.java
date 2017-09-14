package com.zhijian.ebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.dao.BookClassMapper;
import com.zhijian.ebook.dao.BookMapper;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookExample;
import com.zhijian.ebook.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private BookClassMapper bookClassMapper;

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

}
