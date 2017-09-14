package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.OrganizationMapper;
import com.zhijian.ebook.base.dao.StyleSettingMapper;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.Organization;
import com.zhijian.ebook.base.entity.OrganizationExample;
import com.zhijian.ebook.base.entity.StyleSetting;
import com.zhijian.ebook.base.entity.StyleSettingExample;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.StyleSettingService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.security.UserContextHelper;

/**
 * 
 * @author Beyond
 *
 */
@Service
public class StyleSettingServiceImpl implements StyleSettingService {

	@Autowired
	private StyleSettingMapper styleSettingMapper;

	@Autowired
	private OrganizationMapper organizationMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public EasyuiPagination<StyleSetting> findStyleSettingPagination(StyleSetting styleSetting, Integer page,
			Integer rows) {
		StyleSettingExample example = new StyleSettingExample();
		StyleSettingExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(styleSetting.getCssResourceUrl())) {
			String cssResourceUrl = "%" + styleSetting.getCssResourceUrl() + "%";
			criteria.andCssResourceUrlLike(cssResourceUrl);
		}
		Integer total = styleSettingMapper.countByExample(example);
		List<StyleSetting> list = styleSettingMapper.findPaginationList(new Page(page, rows), example);

		return new EasyuiPagination<StyleSetting>(total, list);
	}

	@Override
	public StyleSetting findStyleSettingById(String id) {
		return styleSettingMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addStyleSetting(StyleSetting styleSetting) {
		String userName = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(userName);
		styleSetting.setId(user.getId());
		return styleSettingMapper.insert(styleSetting);
	}

	@Override
	public int modifyStyleSetting(StyleSetting styleSetting) {
//		String userName = UserContextHelper.getUsername();
//		User user = userMapper.findUserByUsername(userName);
//		styleSetting.setId(user.getId());
		return styleSettingMapper.updateByPrimaryKeySelective(styleSetting);
	}

	@Override
	public int removeStyleSettingById(String id) {
		return styleSettingMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Organization> findOrgIdList() {
		OrganizationExample example = new OrganizationExample();
		return organizationMapper.selectByExample(example);
	}

}
