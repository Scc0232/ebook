package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.dao.AuthorityMapper;
import com.zhijian.ebook.base.dao.AuthorityResourceRelationMapper;
import com.zhijian.ebook.base.dao.ResourceMapper;
import com.zhijian.ebook.base.entity.AuthorityResourceRelation;
import com.zhijian.ebook.base.entity.AuthorityResourceRelationExample;
import com.zhijian.ebook.base.service.AuthorityResourceRelationService;
//import com.gener.common.util.ObjectUtil;
@Service
public class AuthorityResourceRelationServiceImpl implements AuthorityResourceRelationService {
	
	@Autowired
	private AuthorityResourceRelationMapper authorityResourceRelationMapper;
	
	@Autowired 
	private AuthorityMapper authorityMapper;
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Override
	public List<AuthorityResourceRelation> findAuthorityResourceRelation(AuthorityResourceRelation arr) {
		AuthorityResourceRelationExample example = new AuthorityResourceRelationExample();
		AuthorityResourceRelationExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(arr.getAuthorityId())){
			criteria.andAuthorityIdEqualTo(arr.getAuthorityId());
		}
		if(StringUtils.isNotEmpty(arr.getResourceId())){
			criteria.andResourceIdEqualTo(arr.getResourceId());
		}
		return authorityResourceRelationMapper.selectByExample(example);
	}

	@Override
	public int addRelation(String authorityId, JSONArray resourceIds)
			throws Exception {
		String resourceId = null;
		AuthorityResourceRelation arr = null;
		for (int i = 0; i < resourceIds.size(); i++) {
			resourceId = resourceIds.getString(i);
			arr = new AuthorityResourceRelation();
			arr.setAuthorityId(authorityId);
			arr.setResourceId(resourceId);
			authorityResourceRelationMapper.insert(arr);
		}
		return 1;
		
	}
	@Override
	public int removerRelation(String authorityId, JSONArray resourceIds)
			throws Exception {
		AuthorityResourceRelationExample example = null;
		AuthorityResourceRelationExample.Criteria criteria = null;
		String resourceId = null;
		for (int i = 0; i < resourceIds.size(); i++) {
			resourceId = resourceIds.getString(i);
			example = new AuthorityResourceRelationExample();
			criteria = example.createCriteria();
			criteria.andAuthorityIdEqualTo(authorityId);
			criteria.andResourceIdEqualTo(resourceId);
			authorityResourceRelationMapper.deleteByExample(example);
		}
		return 1;
	}

}
