package com.zhijian.ebook.base.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.zhijian.ebook.base.entity.Resource;
import com.zhijian.ebook.bean.EasyuiPagination;


public interface ResourceService {

	Map<RequestMatcher, Collection<ConfigAttribute>> getRequestMap() throws Exception;
	
	Resource getResource(List<Resource> list, String resourceId);
	
	/**
	 * 获取资源分页数据
	 * 
	 * @param resource 用户实体
	 * @param page 页数
	 * @param rows 行数
	 * @return 分页数据
	 */
	EasyuiPagination<Resource> findResourcePagination(Resource resource, Integer page, Integer rows);
	
	/**
	 * 增加资源
	 * @param resource 资源实体
	 * @return 
	 */
	int addResource(Resource resource);
	
	/**
	 * 根据资源ID删除信息
	 * @param id 资源ID
	 * @return 
	 */
	int removeResourceById(String id);
	
	/**
	 *修改资源 
	 * @param resource 资源ID
	 * @return
	 */
	int modifyResource(Resource resource);
	
	/**
	 * 根据资源ID得到一条信息
	 * @param id 资源ID
	 * @return 一条资源信息
	 */
	Resource findResourceById(String id);
	
	
	/**获得这个权限未选择的资源
	 * @param resource 资源实体
	 * @param authorityId 权限id
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 * @throws Exception
	 */
	EasyuiPagination<Resource> getThisAuthorityUnselected(Resource resource, String authorityId, Integer page,
            Integer rows) throws Exception;
	
	/**获得这个权限已经选择的资源
	 * @param resource 资源实体
	 * @param authorityId 权限id
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 * @throws Exception
	 */
	EasyuiPagination<Resource> getThisAuthoritySelected(Resource resource, String authorityId, Integer page,
            Integer rows) throws Exception;

}
