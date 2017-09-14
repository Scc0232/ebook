package com.zhijian.ebook.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.AuthorityResourceRelation;

/**
 * 
 * 权限与资源关系服务层
 * 
 * @author Administrator
 *
 */
public interface AuthorityResourceRelationService {
	
	/**
	 * 根据权限ID 或者  根据资源ID  查询权限资源关系
	 * @param urr 权限资源关系实体
	 * @return 权限资源实体列表
	 */
	public List<AuthorityResourceRelation> findAuthorityResourceRelation(AuthorityResourceRelation arr);
	
	/**添加权限与资源关系
	 * @param authorityId 权限id
	 * @param resourceIds 资源id
	 * @return
	 * @throws Exception
	 */
	int addRelation(String authorityId, JSONArray resourceIds)throws Exception;
	
	/**删除权限与资源关系
	 * @param authorityId 权限id 
	 * @param resourceIds 资源id
	 * @return
	 * @throws Exception
	 */
	int removerRelation(String authorityId, JSONArray resourceIds)throws Exception;
}
