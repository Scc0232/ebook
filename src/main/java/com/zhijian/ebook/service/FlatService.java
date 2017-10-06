package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Flat;

public interface FlatService {

	EasyuiPagination<Flat> findFlatPagination(Flat flat, Integer page, Integer rows);

	Flat findFlatById(String flatid);

	int addFlat(Flat flat);

	int removeFlatById(String id);

	int modifyFlat(Flat flat);

}
