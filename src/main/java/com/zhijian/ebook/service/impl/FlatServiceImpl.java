package com.zhijian.ebook.service.impl;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Flat;
import com.zhijian.ebook.service.FlatService;

public class FlatServiceImpl implements FlatService {

	@Override
	public EasyuiPagination<Flat> findFlatPagination(Flat flat, Integer page, Integer rows) {
		return null;
	}

	@Override
	public Flat findFlatById(String flatid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addFlat(Flat flat) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeFlatById(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyFlat(Flat flat) {
		// TODO Auto-generated method stub
		return 0;
	}

}
