package com.zhijian.ebook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.dao.BookClassMapper;
import com.zhijian.ebook.dao.BookMapper;
import com.zhijian.ebook.entity.Book;
import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookClassExample;
import com.zhijian.ebook.entity.BookExample;
import com.zhijian.ebook.service.BookClassService;

@Service
public class BookClassServiceImpl implements BookClassService {

	@Autowired
	private BookClassMapper bookclassMapper;

	@Autowired
	private BookMapper bookMapper;

	@SuppressWarnings("null")
	@Override
	public Map<String, List<?>> selectAll() {
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		List<BookClass> classlist = null;
		BookClassExample example = new BookClassExample();
		example.setOrderByClause(" create_time asc");
		classlist = bookclassMapper.selectByExample(example);
		map.put("bookclass", classlist);
		BookExample bookExample = new BookExample();
		BookExample.Criteria criteria = bookExample.createCriteria();
		criteria.andClassIdEqualTo(classlist.get(0).getId());
		List<Book> booklist = bookMapper.selectByExample(bookExample);
		map.put("kaoyan", booklist);

		return map;
	}

}
