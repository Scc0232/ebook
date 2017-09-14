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
import com.zhijian.ebook.base.service.RoleService;
import com.zhijian.ebook.base.service.UserRoleRelationService;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 用户角色控制器
 * 
 * @author zengshaoxin 2016年1月23日 下午7:19:25
 */
@Controller
@RequestMapping("manager/userRoleRelation")
public class UserRoleRelationController {

	@Autowired
	private UserRoleRelationService service;
	
	@Autowired
	private RoleService roleService;
	

	@RequestMapping("index")
	public String index(ModelMap map, String roleId) {
		Role role = roleService.findRoleById(roleId);
	    map.put("role", role);
		return "manager/userRoleRelation/userRoleRelationList";
	}

	/**
	 * 增加角色与用户的关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jaUserIds
	 *            用户ID
	 * @return ResponseMsg 响应信息
	 */
	@ResponseBody
	@RequestMapping("addRelation")
	public ResponseMsg addRelation(String roleId, String jaUserIds) {

		try {
			if (!StringUtils.isEmpty(jaUserIds)) {
				JSONArray userIds = JSONArray.parseArray(jaUserIds);
				service.addRelation(roleId, userIds);
			}
			return ResponseMsg.success();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseMsg.fail();
		}
	}

	/**
	 * 删除角色与用户的关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param jaUserIds
	 *            用户ID
	 * @return ResponseMsg 响应信息
	 */
	@ResponseBody
	@RequestMapping("removeRelation")
	public ResponseMsg removeRelation(String roleId, String jaUserIds) {
		try {
			if (!StringUtils.isEmpty(jaUserIds)) {
				JSONArray userIds = JSONArray.parseArray(jaUserIds);
				service.removeRelation(roleId, userIds);
			}
			return ResponseMsg.success();
		} catch (Exception e) {
			return ResponseMsg.fail();
		}
	}
}
