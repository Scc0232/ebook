/**
 * 
 */
package com.zhijian.ebook.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.Role;
import com.zhijian.ebook.base.service.RoleService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 *角色控制器
 * @author zengshaoxin 2016年1月21日 下午6:49:50
 */
@Controller
@RequestMapping("manager/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping("index")
	public String index() {
		return "manager/role/roleList";
	}

	/**
	 * 页面分页查询方法
	 * 
	 * @param role
	 *            角色实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findRolePagination")
	public EasyuiPagination<Role> findRolePagination(Role role, Integer page,
			Integer rows) {
		return roleService.findRolePagination(role, page, rows);
	}

	/**
	 * 添加方法跳转页面
	 * 
	 * @return
	 */
	@RequestMapping("addRoleView")
	public String addRoleView() {
		return "manager/role/addRole";
	}

	/**
	 * 添加角色方法
	 * 
	 * @param role
	 *            角色实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addRole")
	public ResponseMsg addRole(Role role) {
		role.setIsValid(true);
		int row;
		try {
			row = roleService.addRole(role);
			if (row > 0) {
				return ResponseMsg.success("添加角色成功");
			} else {
				return ResponseMsg.success("添加角色失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改页面跳转方法
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping("modifyRoleView")
	public String modifyRoleView(ModelMap map, String id) {
		Role role = roleService.findRoleById(id);
		map.put("role", role);
		return "manager/role/modifyRole";

	}

	/**修改角色方法
	 * @param role 角色实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modifyRole")
	public ResponseMsg modifyRole(Role role) {
		role.setIsValid(true);
		int row;
		try {
			row = roleService.modifyRole(role);
			if(row > 0){
				return ResponseMsg.success("修改角色成功");
			}else{
				return ResponseMsg.success("修改角色失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	 /**删除方法
     * @param id
     * 参数  角色id
     * @return
     * 删除失败
     */
    @ResponseBody
	@RequestMapping("removeRole")
	private ResponseMsg removerRole(String id) {
		JSONArray json = JSONArray.parseArray(id);
		try {
			roleService.removeroleById(json);
			return ResponseMsg.success(null);
		} catch (Exception e) {
			return ResponseMsg.fail(e.getMessage());
		}

	}
}
