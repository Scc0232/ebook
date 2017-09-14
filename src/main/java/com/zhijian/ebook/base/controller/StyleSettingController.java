package com.zhijian.ebook.base.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.dao.StyleSettingMapper;
import com.zhijian.ebook.base.entity.Organization;
import com.zhijian.ebook.base.entity.StyleSetting;
import com.zhijian.ebook.base.service.StyleSettingService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 风格设置
 * 
 * @author songchaoyue
 *
 */
@Controller
@RequestMapping("styleSetting")
public class StyleSettingController {

	@Autowired
	private StyleSettingService styleSettingService;

	@Autowired
	private StyleSettingMapper styleSettingMapper;

	/**
	 * 风格设置页面
	 * 
	 * @return 风格列表路径
	 */
	@RequestMapping("styleSettingList")
	public String styleSettingList() {
		return "manager/styleSetting/styleSettingList";
	}

	/**
	 * 获取风格分页数据
	 * 
	 * @param styleSetting
	 *            风格实体
	 * @param page
	 *            页码
	 * @param rows
	 *            行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findStyleSettingPagination")
	public EasyuiPagination<StyleSetting> findStyleSettingPagination(StyleSetting styleSetting, Integer page,
			Integer rows) {
		return styleSettingService.findStyleSettingPagination(styleSetting, page, rows);
	}

	/**
	 * 查询机构名称方法
	 * @param id 风格id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findOrgIdList")
	public List<Organization> findOrgIdList(){
		return styleSettingService.findOrgIdList();
	}
	
	/**
	 * 添加风格页面
	 * 
	 * @return 添加风格路径
	 */
	@RequestMapping("addStyleSettingView")
	public String addStyleSettingView() {
		return "manager/styleSetting/addStyleSetting";
	}

	/**
	 * 添加风格方法
	 * 
	 * @param styleSetting
	 *            风格实体
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addStyleSetting")
	public ResponseMsg addStyleSetting(StyleSetting styleSetting) {
		int row = styleSettingService.addStyleSetting(styleSetting);
		if (row > 0) {
			return ResponseMsg.success("添加风格成功");
		} else {
			return ResponseMsg.fail("添加风格失败");

		}
	}

	/**
	 * 修改风格界面
	 * 
	 * @param id
	 *            风格ID
	 * @return 修改风格路径
	 */
	@RequestMapping("modifyStyleSettingView")
	public String modifyStyleSettingView(ModelMap map, String id) {
		StyleSetting styleSetting = styleSettingMapper.selectByPrimaryKey(id);
		map.put("styleSetting", styleSetting);

		return "manager/styleSetting/modifyStyleSetting";
	}

	/**
	 * 修改风格方法
	 * 
	 * @param StyleSetting
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modifyStyleSetting")
	public ResponseMsg modifyStyleSetting(StyleSetting styleSetting) {
		int rows = styleSettingService.modifyStyleSetting(styleSetting);
		if (rows > 0) {
			return ResponseMsg.success("修改风格成功");
		} else {
			return ResponseMsg.fail("修改风格失败");
		}

	}

	/**
	 * 删除风格方法
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removeStyleSetting")
	public ResponseMsg removeStyleSetting(String id) {
		int rows = styleSettingService.removeStyleSettingById(id);
		if (rows > 0) {
			return ResponseMsg.success("风格删除成功");
		} else {
			return ResponseMsg.fail("风格删除失败");
		}
	}

}
