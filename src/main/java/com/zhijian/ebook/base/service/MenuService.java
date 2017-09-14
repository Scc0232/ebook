package com.zhijian.ebook.base.service;

import java.util.List;

import com.zhijian.ebook.base.entity.Menu;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.EasyuiTree;

public interface MenuService {

	int addMenu(Menu menu);
	
	int modifyMenu(Menu menu);
	
	int removeMenuById(String id);
	
	/**菜单分页方法
	 * @param menu 菜单实体
	 * @param page 页码
	 * @param rows 行数
	 * @return
	 */
	EasyuiPagination<Menu> findMenuPagination(Menu menu, int page, int rows);
	
	int removeMenu(List<String> ids);
	
	Menu findMenuById(String id);
	
	List<EasyuiTree> findMenus(String userId);

	List<Menu> findParentMenuList(String id);
	
}
