package com.zhijian.ebook.base.service;

import java.util.List;

import com.zhijian.ebook.base.entity.Organization;
import com.zhijian.ebook.base.entity.StyleSetting;
import com.zhijian.ebook.bean.EasyuiPagination;

public interface StyleSettingService {

	/**
	 * 风格设置分页方法
	 * @param styleSetting 风格设置实体
	 * @param page 页码
	 * @param rows 行数
	 * @return
	 */
	EasyuiPagination<StyleSetting> findStyleSettingPagination(StyleSetting styleSetting, Integer page, Integer rows);
	/**
	 * 查询风格设置方法
	 * @param id 风格设置ID
	 * @return StyleSetting实体
	 */
	StyleSetting findStyleSettingById(String id);
	/**
	 * 增加风格设置
	 * @param styleSetting 风格设置实体
	 * @return
	 */
	int addStyleSetting(StyleSetting styleSetting);
	
	/**
	 * 修改风格设置
	 * @param styleSetting 实体
	 * @return
	 */
	int modifyStyleSetting(StyleSetting styleSetting);
	/**
	 * 删除风格设置
	 * @param id 风格ID
	 * @return
	 */
	int removeStyleSettingById(String id);
	
	/**
	 * 根据机构ID查询风格设置
	 * @param orgId 
	 * @return
	 */
	List<Organization> findOrgIdList();

	

	

	
}
