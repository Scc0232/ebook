package com.zhijian.ebook.service;

import java.util.List;
import java.util.Map;

import com.zhijian.ebook.base.entity.Dict;


public interface BookClassService {
	
	/**
	 * 获取全部图书分类
	 * @return
	 */
	Map<String, List<?>> selectAll() throws Exception;

	List<Dict> findBanner();

}
