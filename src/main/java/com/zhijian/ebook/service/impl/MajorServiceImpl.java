package com.zhijian.ebook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.dao.MajorMapper;
import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService {

	@Autowired
	private MajorMapper majorMapper;

	@Override
	public List<Major> findCollegeList() {
		List<Major> list = majorMapper.selectCollegeList();
		for (Major major : list) {
			Map<String, List<String>> map = new HashMap<>();
			if (major == null) {
				break;
			}
			String collegeName = major.getCollegeName();
			List<String> academyList = majorMapper.selectAcademyList(collegeName);
			for (String acade : academyList) {
				if (acade == null) {
					break;
				}
				List<String> professionlist = majorMapper.selectProfessionList(acade, major.getCollegeName());
				map.put(acade, professionlist);
			}
			major.setAcademyList(map);
		}
		return list;
	}

}
