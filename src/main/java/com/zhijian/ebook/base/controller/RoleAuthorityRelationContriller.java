/**
 * 
 */
package com.zhijian.ebook.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.Role;
import com.zhijian.ebook.base.service.RoleAuthorityRelationService;
import com.zhijian.ebook.base.service.RoleService;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 角色与权限
 * 
 * @author zengshaoxin 2016年1月23日 下午3:59:49
 */
@Controller
@RequestMapping("manager/authorityRoleRelation")
public class RoleAuthorityRelationContriller {

	@Autowired
	private RoleAuthorityRelationService service;
	
	@Autowired
	private RoleService roleService;

	/**
	 * 返回角色与权限首页
	 * 
	 * @param roleId
	 *            角色id
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap map, String roleId) {
		Role role = roleService.findRoleById(roleId);
		map.put("role", role);
		return "manager/authorityRoleRelation/authorityRoleRelationList";

	}

	/**
	 * 增加角色与权限关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jaAuthorityIds
	 *            权限ID
	 * @return ResponseMsg 相应信息
	 */
	@ResponseBody
	@RequestMapping("addRelation")
	public ResponseMsg addRelation(String roleId, String jaAuthorityIds) {

		try {
			if (!StringUtils.isEmpty(jaAuthorityIds)) {
				JSONArray authorityIds = JSONArray.parseArray(jaAuthorityIds);
				service.addRelation(roleId, authorityIds);
			}
			return ResponseMsg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseMsg.fail();
		}

	}

	/**
	 * 删除角色与权限关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jaAuthorityIds
	 *            权限ID
	 * @return ResponseMsg 相应信息
	 */
	@ResponseBody
	@RequestMapping("removeRelation")
	public ResponseMsg removeRelation(String roleId, String jaAuthorityIds) {
		try {
			if (!StringUtils.isEmpty(jaAuthorityIds)) {
				JSONArray authorityIds = JSONArray.parseArray(jaAuthorityIds);
				service.removerRelation(roleId, authorityIds);
			}
			return ResponseMsg.success();
		} catch (Exception e) {
			return ResponseMsg.fail();
		}
	}
}
