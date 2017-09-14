package com.zhijian.ebook.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.UserRoleRelation;

/**
 * 
 * 用户与角色关系服务层
 * 
 * @author Administrator
 *
 */
public interface UserRoleRelationService {

	/**
	 * 根据用户ID 或者  根据角色ID  查询用户角色关系
	 * @param urr 用户角色关系实体
	 * @return 用户角色实体列表
	 */
	public List<UserRoleRelation> findUserRoleRelation(UserRoleRelation urr);
	
	
	/**
     * 增加角色用户关系
     * 
     * @param roleId
     *            角色ID
     * @param userIds
     *            JSON数组 用户ID
     * @throws Exception
     */
	int addRelation(String roleId, JSONArray userIds) throws Exception;
	
	/**删除角色用户关系
	 * @param roleId  角色ID
	 * @param userIds  JSON数组 用户ID
	 * @return JSON数组 用户ID
	 * @throws Exception
	 */
	int removeRelation(String roleId, JSONArray userIds) throws Exception;
}
