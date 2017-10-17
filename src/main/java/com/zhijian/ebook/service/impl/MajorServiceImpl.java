package com.zhijian.ebook.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.MajorMapper;
import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.entity.MajorExample;
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

	@Override
	public EasyuiPagination<Major> findMajorPagination(Major major, Integer page, Integer rows) {
		MajorExample example = new MajorExample();
		MajorExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(major.getCollegeName())) {
			criteria.andCollegeNameLike("%"+major.getCollegeName()+"%");
		}
		if (StringUtils.isNotBlank(major.getAcademyName())) {
			criteria.andCollegeNameLike("%"+major.getAcademyName()+"%");
		}
		if (StringUtils.isNotBlank(major.getProfessionName())) {
			criteria.andCollegeNameLike("%"+major.getProfessionName()+"%");
		}
		example.setOrderByClause("college_name desc ,create_time desc");
		List<Major> list = majorMapper.findPaginationList(new Page(page, rows), example);
		int counts = majorMapper.countByExample(example);
		return new EasyuiPagination<>(counts, list);
	}

	@Override
	public int addMajor(Major major) {
		major.setCreateTime(new Date());
		return majorMapper.insert(major);
	}

	@Override
	public int modifyMajor(Major major) {
		
		return majorMapper.updateByPrimaryKeySelective(major);
	}

	@Override
	public int removeMajorById(String id) {
		return majorMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Major findMajorById(String majorid) {
		return majorMapper.selectByPrimaryKey(majorid);
	}

}
