package com.zhijian.ebook.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.FlatMapper;
import com.zhijian.ebook.entity.Flat;
import com.zhijian.ebook.entity.FlatExample;
import com.zhijian.ebook.service.FlatService;

@Service
public class FlatServiceImpl implements FlatService {
	
	@Autowired
	private FlatMapper flatMapper;

	@Override
	public EasyuiPagination<Flat> findFlatPagination(Flat flat, Integer page, Integer rows) {
		FlatExample flatExample = new FlatExample();
		FlatExample.Criteria criteria = flatExample.createCriteria();
		if (StringUtils.isNotBlank(flat.getCollegeName())) {
			criteria.andCollegeNameLike("%"+flat.getCollegeName()+"%");
		}
		criteria.andIsValidEqualTo(true);
		flatExample.setOrderByClause("create_time desc");
		List<Flat> list = flatMapper.findPaginationList(new Page(page, rows), flatExample);
		return new EasyuiPagination<>(list.size(), list);
	}

	@Override
	public Flat findFlatById(String flatid) {
		return flatMapper.selectByPrimaryKey(flatid);
	}

	@Override
	public int addFlat(Flat flat) {
		flat.setCollegeName(flat.getCollegeName().trim());
		flat.setFlatName(flat.getFlatName().trim());
		flat.setIsValid(true);
		flat.setCreateTime(new Date());
		return flatMapper.insert(flat);
	}

	@Override
	public int removeFlatById(String id) {
		return flatMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modifyFlat(Flat flat) {
		return flatMapper.updateByPrimaryKeySelective(flat);
	}

}
