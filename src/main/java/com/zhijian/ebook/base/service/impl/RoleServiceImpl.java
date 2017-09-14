/**
 * 
 */
package com.zhijian.ebook.base.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.dao.RoleMapper;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.Role;
import com.zhijian.ebook.base.entity.RoleExample;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.RoleService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.security.UserContextHelper;

/**
 *
 * @author zengshaoxin 2016年1月21日 下午7:37:11
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public EasyuiPagination<Role> findRolePagination(Role role, int page,
			int rows) {
		RoleExample example = new RoleExample();
		RoleExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(role.getName())) {
			String name = "%"+role.getName()+"%";
			criteria.andNameLike(name);
		}
		Integer total = roleMapper.countByExample(example);
		List<Role> list = roleMapper.findPaginationList(new Page(page, rows), example);
		return new EasyuiPagination<Role>(total, list);
		
	}

	@Override
	public int addRole(Role role)throws Exception {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		role.setModifyPersonId(ModifyPersonId);
		String createPersonId = user.getId();
		role.setCreatePersonId(createPersonId);
		Date date = new Date();
		role.setCreateTime(date);
		return roleMapper.insert(role);
	}

	@Override
	public int modifyRole(Role role)throws Exception {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		role.setModifyPersonId(ModifyPersonId);
		Date date =new Date();
		role.setModifyTime(date);
		return roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public int removeroleById(JSONArray json)throws Exception {
		 for (int i = 0; i < json.size(); i++) {
	            String id = json.getString(i);
	            roleMapper.deleteByPrimaryKey(id);
	        }
		return 0;
	}

	@Override
	public Role findRoleByName(String name) throws Exception {
		return roleMapper.findRoleByName(name);
	}

	
	@Override
	public Role findRoleById(String id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Role findUniqueRole(String username) {
		Role role = roleMapper.findRoleByName(username);
		if (role == null) {
			return null;
		}
		return role;
	}

}
