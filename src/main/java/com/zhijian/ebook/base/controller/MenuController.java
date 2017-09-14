package com.zhijian.ebook.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.Menu;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.MenuService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.EasyuiTree;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.security.UserContextHelper;

/**
 * 
 * 菜单控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("index")
    public String index(){
        return "manager/menu/index";
    }
	
	/**
	 * 获取菜单分页数据
	 * @param menu 菜单实体
	 * @param page 页数
	 * @param rows 行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findMenuPagination")
	public EasyuiPagination<Menu> findMenuPagination(Menu menu,int page, int rows) {
		return menuService.findMenuPagination(menu, page, rows);
	}
	
	/**
	 * 查询父菜单
	 * @param id 菜单ID
	 * @return 父菜单列表
	 */
	@ResponseBody
	@RequestMapping("findParentMenuList")
	public List<Menu> findParentMenuList(String id){
		return menuService.findParentMenuList(id);
	}
	
	@RequestMapping("addMenuView")
	public String addMenuView(){
	    return "manager/menu/addMenu";
	}
	
	@RequestMapping("modifyMenuView")
    public String modifyMenuView(ModelMap map, String menuId){
	    Menu menu = menuService.findMenuById(menuId);
	    map.put("menu", menu);
        return "manager/menu/modifyMenu";
    }
	
	@ResponseBody
    @RequestMapping("addMenu")
	public ResponseMsg addMenu(Menu menu) {
		menu.setIsValid(true);
	    int row = menuService.addMenu(menu);
	    if (row > 0) {
            return ResponseMsg.success("添加菜单成功！");
        } else {
            return ResponseMsg.fail("添加菜单失败！");
        }
	}
	
	@ResponseBody
    @RequestMapping("modifyMenu")
    public ResponseMsg modifyMenu(Menu menu) {
		menu.setIsValid(true);
        int row = menuService.modifyMenu(menu);
        if (row > 0) {
            return ResponseMsg.success("修改菜单成功！");
        } else {
            return ResponseMsg.fail("修改菜单失败！");
        }
    }
	
	
	@ResponseBody
    @RequestMapping("menuRemove")
    public ResponseMsg menuRemove(String id) {
        int row = menuService.removeMenuById(id);
        if (row > 0) {
            return ResponseMsg.success("删除菜单成功！");
        } else {
            return ResponseMsg.fail("删除菜单失败！");
        }
    }
	
	@ResponseBody
    @RequestMapping("removeMenu")
    public ResponseMsg removeMenu(List<String> ids) {
        int row = menuService.removeMenu(ids);
        if (row > 0) {
            return ResponseMsg.success("删除菜单成功！");
        } else {
            return ResponseMsg.fail("删除菜单失败！");
        }
    }
	
	/**
     * 根据用户的权限获得所有的菜单
     * 
     * @return 树列表
     */
    @ResponseBody
    @RequestMapping("getMenu")
    public List<EasyuiTree> getMenu() {
        String username = UserContextHelper.getUsername();

        if (username != null) {
            try {
                User user = userService.findUserByUsername(username);
                //return service.getMenuTreeByUserAuthority(user.getId());
            	return menuService.findMenus(user.getId());
            } catch (Exception e) {
            	e.printStackTrace();
                //log.error("根据用户的权限获得所有的菜单失败！username={}", username, e);
            }
        }
        return null;
    }
	
}
