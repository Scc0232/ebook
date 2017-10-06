package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.CollectMapper;
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.entity.CollectExample;
import com.zhijian.ebook.service.CollectService;

@Service
public class CollectServiceImpl implements CollectService {

	@Autowired
	private CollectMapper collectMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public EasyuiPagination<Collect> findCollectPagination(Collect collect, Integer page, Integer rows) {
		CollectExample collectExample = new CollectExample();
		CollectExample.Criteria criteria = collectExample.createCriteria();
		if (StringUtils.isNotBlank(collect.getBookName())) {
			criteria.andBookNameLike("%" + collect.getBookName() + "%");
		}

		collectExample.setOrderByClause("book_name desc, create_time desc");
		List<Collect> list = collectMapper.findPaginationList(new Page(page, rows), collectExample);
		for (Collect col : list) {
			String userid = col.getUserid();
			String username = userMapper.selectByPrimaryKey(userid).getPetName();
			if (StringUtils.isNotBlank(username)) {
				col.setUsername(username);
			}
		}
		int counts = collectMapper.countByExample(collectExample);
		return new EasyuiPagination<Collect>(counts, list);
	}

	@Override
	public Collect findCollectById(String collectid) {
		return collectMapper.selectByPrimaryKey(collectid);
	}

	@Override
	public int addCollect(Collect collect) {
		return collectMapper.insert(collect);
	}

	@Override
	public int modifyCollect(Collect collect) {
		return collectMapper.updateByPrimaryKeySelective(collect);
	}

	@Override
	public int removeCollectById(String id) {
		return collectMapper.deleteByPrimaryKey(id);
	}

}
