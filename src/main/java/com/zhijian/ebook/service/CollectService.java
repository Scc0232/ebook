package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Collect;

public interface CollectService {

	EasyuiPagination<Collect> findCollectPagination(Collect collect, Integer page, Integer rows);

	Collect findCollectById(String collectid);

	int addCollect(Collect collect);

	int modifyCollect(Collect collect);

	int removeCollectById(String id);

}
