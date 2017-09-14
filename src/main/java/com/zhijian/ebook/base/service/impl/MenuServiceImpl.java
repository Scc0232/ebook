package com.zhijian.ebook.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.gener.common.util.ObjectUtil;
import com.zhijian.ebook.base.dao.MenuMapper;
import com.zhijian.ebook.base.dao.ResourceMapper;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.Menu;
import com.zhijian.ebook.base.entity.MenuExample;
import com.zhijian.ebook.base.entity.Resource;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.MenuService;
import com.zhijian.ebook.base.service.ResourceService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.EasyuiTree;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.security.UserContextHelper;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public int addMenu(Menu menu) {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		menu.setModifyPersonId(ModifyPersonId);
		String createPersonId = user.getId();
		menu.setCreatePersonId(createPersonId);
		Date date = new Date();
		menu.setCreateTime(date);
		return menuMapper.insert(menu);
	}

	@Override
	public int modifyMenu(Menu menu) {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		menu.setModifyPersonId(ModifyPersonId);
		Date date =new Date();
		menu.setModifyTime(date);
		return menuMapper.updateByPrimaryKey(menu);
	}

	@Override
	public int removeMenuById(String id) {
		return menuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public EasyuiPagination<Menu> findMenuPagination(Menu menu, int page, int rows) {
		try {
			MenuExample example = new MenuExample();
			MenuExample.Criteria criteria = example.createCriteria();
			if(StringUtils.isNotBlank(menu.getName())){
				String name = "%"+menu.getName()+"%";
				criteria.andNameLike(name);
			}
			if (StringUtils.isNotBlank(menu.getIcon())) {
				String icon = "%"+menu.getIcon()+"%";
			    criteria.andIconLike(icon);
            }
			Integer total = menuMapper.countByExample(example);
			List<Menu> list = menuMapper.findPaginationList(new Page(page, rows),example);
			Menu parentMenu = null;
			for(int i=0;i<list.size();i++){
			    Menu m = list.get(i);
			    if(StringUtils.isNotEmpty(m.getParentId())){
			        parentMenu =  menuMapper.selectByPrimaryKey(m.getParentId()); 
			        if(parentMenu!=null){
			        m.setPname(parentMenu.getName());  
			        }
			    }
			}
			return new EasyuiPagination<Menu>(total, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public int removeMenu(List<String> ids) {
        return menuMapper.removeMenu(ids);
    }

    @Override
    public Menu findMenuById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

	@Override
	public List<EasyuiTree> findMenus(String userId) {
		/*List<Authority> authoritys = authorityService.getAuthorityByUserId(userId);
        List<Resource> resources = resourceService.getResourceByAuthority(authoritys);
        List<Menu> allMenu = dao.findObjectList();
        return createTree(allMenu, resources);*/
		
        List<Resource> resources = resourceMapper.findResourceListByUserId(userId); //resourceMapper.selectByExample(null); 
        List<Menu> allMenu = menuMapper.selectByExample(null);
        return createTree(allMenu, resources);
	}
	
	
	private List<EasyuiTree> createTree(List<Menu> menus, List<Resource> resources) {
		List<EasyuiTree> result = new ArrayList<>();
		for (Menu m : menus) {
			Resource r = resourceService.getResource(resources, m.getResourceId());
			// 濡傛灉浠庤繖涓敤鎴锋嫢鏈夌殑璧勬簮涓彇鍑虹殑璧勬簮涓簄ull锛屽苟涓旇繖涓彍鍗曚笉鏄埗鑿滃崟锛屽垯涓嶆斁鍏ユ爲涓�
			if (r == null && !StringUtils.isEmpty(m.getParentId())) {
				continue;
			}
			put(result, m, r);
		}
		EasyuiTree.arrange(result);
		return result;
	}
	
	/**
     * 灏嗕竴涓彍鍗曟斁鍏ユ爲涓�
     * 
     * @param list
     * @param menu
     * @param resource
     */
    private void put(List<EasyuiTree> list, Menu menu, Resource resource) {
        String id = menu.getId();
        String text = menu.getName();
        String parentId = menu.getParentId();
        String iconCls = menu.getIcon();
        String url = null;
        if (resource != null) {
            url = resource.getUrl();
        }

        EasyuiTree e = new EasyuiTree(id, text, parentId, iconCls, url, false);
        list.add(e);
    }

	@Override
	public List<Menu> findParentMenuList(String id) {
		MenuExample example = new MenuExample();
		MenuExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(id)){
			criteria.andIdEqualTo(id);
		}
		criteria.andParentIdEqualTo("");
		return menuMapper.selectByExample(example);
	}

}
