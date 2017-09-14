package com.zhijian.ebook.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
//import com.gener.common.util.ObjectUtil;
import com.zhijian.ebook.base.dao.AuthorityMapper;
import com.zhijian.ebook.base.dao.RoleAuthorityRelationMapper;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.dao.UserRoleRelationMapper;
import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.base.entity.AuthorityExample;
import com.zhijian.ebook.base.entity.RoleAuthorityRelation;
import com.zhijian.ebook.base.entity.RoleAuthorityRelationExample;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.entity.UserRoleRelation;
import com.zhijian.ebook.base.entity.UserRoleRelationExample;
import com.zhijian.ebook.base.service.AuthorityService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.util.StringConsts;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleRelationMapper userRoleRelationMapper;
	
	@Autowired
	private RoleAuthorityRelationMapper roleAuthorityRelationMapper;
	
	@Override
	public List<Authority> findAuthorityByUserId(String userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		String username = user.getUsername();
		StringConsts.USER_NAME = username;
		if (userService.isSuperUser(username)) {
			return authorityMapper.selectByExample(null);
		} else {
//			/*return authorityMapper.selectByExample(null);*/
			
			UserRoleRelationExample urrExample = new UserRoleRelationExample();
			UserRoleRelationExample.Criteria urrCriteria = urrExample.createCriteria();
			urrCriteria.andUserIdEqualTo(userId);
			List<UserRoleRelation> urrList = userRoleRelationMapper.selectByExample(urrExample);
			List<RoleAuthorityRelation> rarList = null;
			Authority authority = null;
			List<Authority> authorityList = new ArrayList<Authority>();
			for (int i = 0; i < urrList.size(); i++) {
				RoleAuthorityRelationExample rarExample = new RoleAuthorityRelationExample();
				RoleAuthorityRelationExample.Criteria rarCriteria = rarExample.createCriteria();
				rarCriteria.andRoleIdEqualTo(urrList.get(i).getRoleId());
				rarList = roleAuthorityRelationMapper.selectByExample(rarExample);
				for (int j = 0; j < rarList.size(); j++) {
					authority = authorityMapper.selectByPrimaryKey(rarList.get(j).getAuthorityId());
					if(authority!=null)
					authorityList.add(authority);
				}
			}
			return authorityList;
		}
	}

	@Override
	public EasyuiPagination<Authority> findAuthorityPagination(
			Authority authority, int page, int rows) {
		AuthorityExample example = new AuthorityExample();
		AuthorityExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(authority.getName())){
			String name = "%"+authority.getName()+"%";
			criteria.andNameLike(name);
		}
		if(StringUtils.isNotEmpty(authority.getDisplayName())){
			String displayName = "%"+authority.getDisplayName()+"%";
			criteria.andDisplayNameLike(displayName);
		}
		Integer total = authorityMapper.countByExample(example);
		List<Authority> list = authorityMapper.findPaginationList(new Page(page, rows), example);
		return new EasyuiPagination<Authority>(total,list);
	}

	@Override
	public int addAuthority(Authority authority) throws Exception {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		authority.setModifyPersonId(ModifyPersonId);
		String createPersonId = user.getId();
		authority.setCreatePersonId(createPersonId);
		Date date = new Date();
		authority.setCreateTime(date);
		return authorityMapper.insert(authority);
	}

	@Override
	public int modifyAuthority(Authority authority) throws Exception {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		authority.setModifyPersonId(ModifyPersonId);
		Date date =new Date();
		authority.setModifyTime(date);
		return authorityMapper.updateByPrimaryKey(authority);
	}

	@Override
	public int removeAuthorityById(JSONArray json) throws Exception {
		for (int i = 0; i < json.size(); i++) {
            String id = json.getString(i);
            authorityMapper.deleteByPrimaryKey(id);
        }
		return 0;
	}

	@Override
	public Authority findAuthorityByid(String id) throws Exception {
		return authorityMapper.selectByPrimaryKey(id);
	}

	@Override
	public EasyuiPagination<Authority> getThisRoleUnselected(
			Authority authority, String roleId, Integer page, Integer rows)
			throws Exception {
		Integer totle = authorityMapper.countByUnRoleId(roleId,authority);
		List<Authority> list = authorityMapper.unSelectAuthorityByRoleId(new Page(page, rows), roleId,authority);
		return new EasyuiPagination<Authority>(totle,list);
	}

	@Override
	public EasyuiPagination<Authority> getThisRoleSelected(Authority authority,
			String roleId, Integer page, Integer rows) throws Exception {
		Integer totle = authorityMapper.countByRoleId(roleId,authority);
		List<Authority> list = authorityMapper.findAuthorityByRoleId(new Page(page, rows), roleId,authority);
		return new EasyuiPagination<Authority>(totle,list);
	}

}
