package com.zhijian.ebook.base.service;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.Role;
import com.zhijian.ebook.bean.EasyuiPagination;


public interface RoleService {
	
	/**角色查询分页
	 * @param role 角色
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
	EasyuiPagination<Role>findRolePagination(Role role,int page,int rows);
	
	/**添加方法
	 * @param role 角色实体
	 * @return
	 */
	int addRole(Role role)throws Exception;
	
	/**修改方法
	 * @param role 角色实体
	 * @return
	 */
	int modifyRole(Role role)throws Exception;
	
	/**根据角色id删除角色
	 * @param id 角色id
	 * @return
	 */
	int removeroleById(JSONArray json)throws Exception;
	

	/**根据角色名称获得角色数据
	 * @param name 角色名称
	 * @return
	 * @throws Exception
	 */
	Role findRoleByName(String name)throws Exception;
	
	/**根据角色id获得角色数据
	 * @param id 角色id
	 * @return
	 */
	Role findRoleById(String id);

	/**
	 * @param username
	 * @return
	 */
	Role findUniqueRole(String username);
}
