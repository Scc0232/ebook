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
import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.base.service.AuthorityService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 权限控制器
 * 
 * @author zengshaoxin 2016年1月22日 下午1:59:16
 */
@Controller
@RequestMapping("manager/authority")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping("index")
	public String index() {
		return "manager/authority/authorityList";
	}

	/**
	 * 权限分页查询方法
	 * 
	 * @param authority
	 *            权限实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findAuthorityPagination")
	public EasyuiPagination<Authority> findAuthorityPagination(
			Authority authority, Integer page, Integer rows) {
		return authorityService.findAuthorityPagination(authority, page, rows);

	}

	/**
	 * 添加返回页面
	 * 
	 * @return
	 */
	@RequestMapping("addAuthorityView")
	public String addAuthorityView() {
		return "manager/authority/addAuthority";
	}

	/**
	 * 添加方法
	 * 
	 * @param authority
	 *            权限实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addAuthority")
	public ResponseMsg addAuthority(Authority authority) {
		authority.setIsValid(true);
		try {
			int row = authorityService.addAuthority(authority);
			if (row > 0) {
				return ResponseMsg.success("添加权限成功");
			} else {
				return ResponseMsg.success("添加权限失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改方法跳转页面
	 * 
	 * @return
	 */
	@RequestMapping("modifyAuthorityView")
	public String modifyAuthorityView(ModelMap map, String id) {
		try {
			Authority authority = authorityService.findAuthorityByid(id);
			map.put("authority", authority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manager/authority/modifyAuthority";

	}

	/**
	 * 修改权限方法
	 * 
	 * @param authority
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modifyAuthority")
	public ResponseMsg modifyAuthority(Authority authority) {
		authority.setIsValid(true);
		try {
			int row = authorityService.modifyAuthority(authority);
			if (row > 0) {
				return ResponseMsg.success("修改权限成功");
			} else {
				return ResponseMsg.success("修改权限失败");
			}
		} catch (Exception e) {
		}
		return null;

	}

	/**
	 * 删除方法
	 * 
	 * @param id
	 *            权限id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removeAuthority")
	private ResponseMsg removeAuthority(String id) {
		JSONArray json = JSONArray.parseArray(id);
		try {
			authorityService.removeAuthorityById(json);
			return ResponseMsg.success(null);
		} catch (Exception e) {
			return ResponseMsg.fail(e.getMessage());
		}

	}

	/**
	 * 获得这个角色已选择的权限
	 * 
	 * @param authority
	 *            权限实体
	 * @param roleId
	 *            角色id
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getThisRoleSelected")
	public EasyuiPagination<Authority> getThisRoleSelected(Authority authority,
			String roleId, Integer page, Integer rows) {
		try {
			return authorityService.getThisRoleSelected(authority, roleId,
					page, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**获得这个角色未选择的权限
	 * @param authority 权限实体
	 * @param roleId 角色id
	 * @param page  页数
	 * @param rows 行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getThisRoleUnselected")
	public EasyuiPagination<Authority> getThisRoleUnselected(Authority authority,
			String roleId,Integer page,Integer rows){
				try {
					return authorityService.getThisRoleUnselected(authority, roleId, page, rows);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
	}
			
}
