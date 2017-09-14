package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.dao.RoleAuthorityRelationMapper;
import com.zhijian.ebook.base.entity.RoleAuthorityRelation;
import com.zhijian.ebook.base.entity.RoleAuthorityRelationExample;
import com.zhijian.ebook.base.service.RoleAuthorityRelationService;
//import com.gener.common.util.ObjectUtil;
@Service
public class RoleAuthorityRelationServiceImpl implements
		RoleAuthorityRelationService {

	@Autowired
	private RoleAuthorityRelationMapper roleAuthorityRelationMapper;
	
	@Override
	public List<RoleAuthorityRelation> findRoleAuthorityRelation(RoleAuthorityRelation rar) {
		RoleAuthorityRelationExample example = new RoleAuthorityRelationExample();
		RoleAuthorityRelationExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(rar.getRoleId())){
			criteria.andRoleIdEqualTo(rar.getRoleId());
		}
		if(StringUtils.isNotEmpty(rar.getAuthorityId())){
			criteria.andAuthorityIdEqualTo(rar.getAuthorityId());
		}
		return roleAuthorityRelationMapper.selectByExample(example);
	}

	@Override
	public int addRelation(String roleId, JSONArray authorityIds)
			throws Exception {
		String authorityId = null;
		RoleAuthorityRelation arr = null;
		for (int i = 0; i < authorityIds.size(); i++) {
			authorityId =  authorityIds.getString(i);
			arr = new RoleAuthorityRelation();
			arr.setAuthorityId(authorityId);
			arr.setRoleId(roleId);
			roleAuthorityRelationMapper.insert(arr);
		}
		return 1;
	}

	@Override
	public int removerRelation(String roleId, JSONArray authorityIds)
			throws Exception {
		
		RoleAuthorityRelationExample example = null;
		RoleAuthorityRelationExample.Criteria criteria = null;
		String authorityId = null;
		for (int i = 0; i < authorityIds.size(); i++) {
			authorityId =  authorityIds.getString(i);
			example = new RoleAuthorityRelationExample();
			criteria = example.createCriteria();
			criteria.andAuthorityIdEqualTo(authorityId);
			criteria.andRoleIdEqualTo(roleId);
			roleAuthorityRelationMapper.deleteByExample(example);
		}
		return 1;
	}

}
