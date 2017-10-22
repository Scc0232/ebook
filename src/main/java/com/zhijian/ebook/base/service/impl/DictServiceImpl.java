package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.gener.common.util.ObjectUtil;
import com.zhijian.ebook.base.dao.DictMapper;
import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.entity.DictExample;
import com.zhijian.ebook.base.service.DictService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;

@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public int addDict(Dict dict) {
		return dictMapper.insert(dict);
	}

	@Override
	public int modifyDict(Dict dict) {
		return dictMapper.updateByPrimaryKeySelective(dict);
	}

	@Override
	public int removeDictById(String id) {
		return dictMapper.deleteByPrimaryKey(id);
	}

	@Override
	public EasyuiPagination<Dict> findDictPagination(Dict dict, int page, int rows) {
		try {
			DictExample example = new DictExample();
			DictExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(dict.getDictName())) {
				String dictName = "%" + dict.getDictName() + "%";
				criteria.andDictNameLike(dictName);
			}
			if (StringUtils.isNotBlank(dict.getDictType())) {
				String dictType = "%" + dict.getDictType() + "%";
				criteria.andDictTypeLike(dictType);
			}
			Integer total = dictMapper.countByExample(example);
			List<Dict> list = dictMapper.findPaginationList(new Page(page, rows), example);
			;
			return new EasyuiPagination<Dict>(total, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Dict findDictById(String id) {
		return dictMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Dict> findTypesByKey(String key) {
		DictExample example = new DictExample();
		DictExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(key)) {
			criteria.andDictTypeEqualTo(key);
		}
		return dictMapper.selectByExample(example);
	}

	@Override
	public Dict findDictByCode(String code) {
		DictExample example = new DictExample();
		DictExample.Criteria criteria = example.createCriteria();
		criteria.andDictCodeEqualTo(code);
		List<Dict> list = dictMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Dict> findDictByType(String dictType) {
		return dictMapper.selectByDictType(dictType);
	}

}
