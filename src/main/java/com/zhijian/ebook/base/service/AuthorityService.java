package com.zhijian.ebook.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.bean.EasyuiPagination;

public interface AuthorityService {

	List<Authority> findAuthorityByUserId(String id);

	/**
	 * 查询权限分页
	 * 
	 * @param authority
	 *            权限
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return
	 */
	EasyuiPagination<Authority> findAuthorityPagination(Authority authority,
			int page, int rows);

	/**
	 * 添加权限
	 * 
	 * @param authority
	 *            权限实体
	 * @return 返回查询数据并且分页
	 * @throws Exception
	 *             异常处理
	 */
	int addAuthority(Authority authority) throws Exception;

	/**
	 * 修改权限
	 * 
	 * @param authority
	 *            权限实体
	 * @return
	 * @throws Exception
	 */
	int modifyAuthority(Authority authority) throws Exception;

	/**
	 * 删除权限
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	int removeAuthorityById(JSONArray json) throws Exception;

	/**
	 * 根据权限id获取权限数据
	 * 
	 * @param id
	 *            权限id
	 * @return
	 * @throws Exception
	 */
	Authority findAuthorityByid(String id) throws Exception;

	/**
	 * 获得这个角色未选择的权限
	 * 
	 * @param authority 权限实体
	 * @param roleId 角色id
	 * @param page 行数
	 * @param rows 页数
	 * @return
	 * @throws Exception
	 */
	EasyuiPagination<Authority> getThisRoleUnselected(Authority authority,
			String roleId, Integer page, Integer rows) throws Exception;

	/**
	 * 获得这个角色已选择的权限
	 * 
	 * @param authority 权限实体
	 * @param roleId 角色id
	 * @param page 行数
	 * @param rows 页数
	 * @return
	 * @throws Exception
	 */
	EasyuiPagination<Authority> getThisRoleSelected(Authority authority,
			String roleId, Integer page, Integer rows) throws Exception;
}
