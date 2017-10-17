package com.zhijian.ebook.service;

import java.util.List;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Major;

public interface MajorService {

	List<Major> findCollegeList();

	EasyuiPagination<Major> findMajorPagination(Major major, Integer page, Integer rows);

	int addMajor(Major major);

	int modifyMajor(Major major);

	int removeMajorById(String id);

	Major findMajorById(String majorid);

}
