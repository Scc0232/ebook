package com.zhijian.ebook.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.Resource;
import com.zhijian.ebook.base.service.ResourceService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 资源控制器
 * 
 * @author pengzhangli 2016年1月22日 下午12:43:36
 */

@Controller
@RequestMapping("manager/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	/**
	 * 资源列表界面
	 * 
	 * @return 资源列表路径
	 */
	@SuppressWarnings("static-method")
	@RequestMapping("index")
	public String index() {
		return "manager/resource/resourceList";
	}

	/**
	 * 获取资源分页数据
	 * 
	 * @param resource
	 *            资源实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findResourcePagination")
	public EasyuiPagination<Resource> findUserPagination(Resource resource,
			Integer page, Integer rows) {
		return resourceService.findResourcePagination(resource, page, rows);
	}

	/**
	 * 添加资源页面
	 * 
	 * @return 添加资源路径
	 */
	@SuppressWarnings("static-method")
	@RequestMapping("addResourceView")
	public String addResourceView() {
		return "manager/resource/addResource";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 *            用户信息
	 * @return 添加用户情况
	 */
	@ResponseBody
	@RequestMapping("addResource")
	public ResponseMsg addResource(Resource resource) {
		Boolean isValid = true;
		resource.setIsValid(isValid);
		int row = resourceService.addResource(resource);
		if (row > 0) {
			return ResponseMsg.success("添加资源成功！");
		} else {
			return ResponseMsg.fail("添加资源失败！");
		}
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 *            资源ID
	 * @return 删除资源情况
	 */
	@ResponseBody
	@RequestMapping("removeResource")
	public ResponseMsg removeResourceById(String id) {
		int row = resourceService.removeResourceById(id);
		if (row > 0) {
			return ResponseMsg.success("删除资源成功！");
		} else {
			return ResponseMsg.fail("删除资源失败！");
		}
	}

	/**
	 * 修改资源界面
	 * 
	 * @param map
	 *            资源信息
	 * @param Id
	 *            资源ID
	 * @return 修改资源路径
	 */
	@RequestMapping("modifyResourceView")
	public String modifyResourceView(ModelMap map, String id) {
		Resource resourceMap = resourceService.findResourceById(id);
		map.put("resourceMap", resourceMap);
		return "manager/resource/modifyResource";
	}

	/**
	 * 修改资源
	 * 
	 * @param resource
	 *            资源信息
	 * @return 修改资源情况
	 */
	@ResponseBody
	@RequestMapping("modifyResource")
	public ResponseMsg modifyResource(Resource resource) {
		Boolean isValid = true;
		resource.setIsValid(isValid);
		int row = resourceService.modifyResource(resource);
		if (row > 0) {
			return ResponseMsg.success("修改资源成功！");
		} else {
			return ResponseMsg.fail("修改资源失败！");
		}
	}

	/**
	 * 获得这个权限已选择的资源
	 * 
	 * @param resource
	 *            资源实体
	 * @param authorityId
	 *            权限id
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getThisAuthoritySelected")
	public EasyuiPagination<Resource> getThisRoleSelected(Resource resource,
			String authorityId, Integer page, Integer rows) {
		try {
			return resourceService.getThisAuthoritySelected(resource,
					authorityId, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**获得这个权限未选择的资源
	 * @param resource 资源实体
	 * @param authorityId 权限id
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getThisAuthorityUnselected")
	public EasyuiPagination<Resource> getThisRoleUnselected(Resource resource,
			String authorityId, Integer page, Integer rows) {
		try {
			return resourceService.getThisAuthorityUnselected(resource,
					authorityId, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
