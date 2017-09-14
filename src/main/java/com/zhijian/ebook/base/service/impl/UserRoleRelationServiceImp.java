package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.dao.UserRoleRelationMapper;
import com.zhijian.ebook.base.entity.UserRoleRelation;
import com.zhijian.ebook.base.entity.UserRoleRelationExample;
import com.zhijian.ebook.base.service.UserRoleRelationService;
//import com.gener.common.util.ObjectUtil;
@Service
public class UserRoleRelationServiceImp implements UserRoleRelationService {

	@Autowired
	private UserRoleRelationMapper userRoleRelationMapper;
	
	@Override
	public List<UserRoleRelation> findUserRoleRelation(UserRoleRelation urr) {
		UserRoleRelationExample example = new UserRoleRelationExample();
		UserRoleRelationExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(urr.getUserId())){
			criteria.andUserIdEqualTo(urr.getUserId());
		}
		if(StringUtils.isNotEmpty(urr.getRoleId())){
			criteria.andRoleIdEqualTo(urr.getRoleId());
		}
		return userRoleRelationMapper.selectByExample(example);
	}

	@Override
	public int addRelation(String roleId, JSONArray userIds) throws Exception {
		
		String userId = null;
		UserRoleRelation arr = null;
		for (int i = 0; i < userIds.size(); i++) {
			 userId =  userIds.getString(i);
			 arr = new UserRoleRelation();
			 arr.setRoleId(roleId);
			 arr.setUserId(userId);
			 userRoleRelationMapper.insert(arr);
		}
		return 1;
	}

	@Override
	public int removeRelation(String roleId, JSONArray userIds)
			throws Exception {
		UserRoleRelationExample example = null;
		UserRoleRelationExample.Criteria criteria = null;
		String userId = null;
		for (int i = 0; i < userIds.size(); i++) {
			userId = userIds.getString(i);
			example = new UserRoleRelationExample();
			criteria = example.createCriteria();
			criteria.andRoleIdEqualTo(roleId);
			criteria.andUserIdEqualTo(userId);
			userRoleRelationMapper.deleteByExample(example);
		}
		return 1;
	}
	
}
