/**
 * 
 */
package com.zhijian.ebook.base.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.base.service.AuthorityResourceRelationService;
import com.zhijian.ebook.base.service.AuthorityService;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 权限与资源控制器
 * 
 * @author zengshaoxin 2016年1月23日 上午10:37:59
 */
@Controller
@RequestMapping("manager/authorityResourceRelation")
public class AuthorityResourceRelationController {

	@Autowired
	AuthorityResourceRelationService service;
	
	@Autowired 
	private AuthorityService authorityService;

	@RequestMapping("index")
	public String index(ModelMap map, String authorityId) {
		try {
			Authority authority = authorityService.findAuthorityByid(authorityId);
			map.put("authority", authority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manager/authorityResourceRelation/authorityResourceRelationList";
	
	}

	/**增加权限与资源的关系
	 * @param authorityId  资源id
	 * @param jaResourceIds 权限id json 数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addRelation")
	public ResponseMsg addRelation(String authorityId, String jaResourceIds) {
		try {
			if (StringUtils.isNotEmpty(jaResourceIds)) {
				JSONArray resourceIds = JSONArray.parseArray(jaResourceIds);
				service.addRelation(authorityId, resourceIds);
			}
			return ResponseMsg.success();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**删除权限与资源的关系
	 * @param authorityId 资源id
	 * @param jaResourceIds 权限id json 数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removeRelation")
	public ResponseMsg removeRelation(String authorityId, String jaResourceIds){
		try {
			if (StringUtils.isNotEmpty(jaResourceIds)) {
				JSONArray resourceIds = JSONArray.parseArray(jaResourceIds);
				service.removerRelation(authorityId, resourceIds);
			}
			return ResponseMsg.success();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
