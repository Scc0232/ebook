package com.zhijian.ebook.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.RoleAuthorityRelation;

/**
 * 
 * 角色与资源关系服务层
 * 
 * @author Administrator
 *
 */
public interface RoleAuthorityRelationService {
	
	/**
	 * 根据角色ID 或者  根据权限ID  查询角色权限关系
	 * @param urr 角色权限关系实体
	 * @return 角色权限实体列表
	 */
	public List<RoleAuthorityRelation> findRoleAuthorityRelation(RoleAuthorityRelation rar);
	
	
	/**添加角色与资源关系
	 * @param roleId 角色id
	 * @param authorityIds 资源id
	 * @return
	 * @throws Exception
	 */
	int addRelation(String roleId, JSONArray authorityIds)throws Exception;
	
	/**删除角色与资源关系
	 * @param roleId 角色id
	 * @param authorityIds 资源id
	 * @return
	 * @throws Exception
	 */
	int removerRelation(String roleId, JSONArray authorityIds)throws Exception;
}
