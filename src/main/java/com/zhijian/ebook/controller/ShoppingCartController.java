package com.zhijian.ebook.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.service.ShoppingCartService;


/**
 * 
 * 购物车控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/shoppingCart")
public class ShoppingCartController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ShoppingCartService shoppingCartService;

	/**
	 * 购物车列表界面
	 * 
	 * @return 购物车列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/shoppingCart/shoppingCartList";
	}

	/**
	 * 获取购物车分页数据
	 * 
	 * @param ShoppingCart
	 *            购物车实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findShoppingCartPagination")
	public EasyuiPagination<ShoppingCart> findShoppingCartPagination(ShoppingCart shoppingCart, Integer page, Integer rows) {

		return shoppingCartService.findShoppingCartPagination(shoppingCart, page, rows);
	}

	/**
	 * 添加购物车页面
	 * 
	 * @return 添加购物车路径
	 */
	@RequestMapping("addShoppingCartView")
	public String addShoppingCartView() {
		return "manager/shoppingCart/addShoppingCart";
	}

	/**
	 * 修改购物车界面
	 * 
	 * @param map
	 *            购物车信息
	 * @param ShoppingCartId
	 *            购物车ID
	 * @return 修改购物车路径
	 */
	@RequestMapping("modifyShoppingCartView")
	public String modifyShoppingCartView(ModelMap map, String shoppingCartid) {
		ShoppingCart shoppingCart = shoppingCartService.findShoppingCartById(shoppingCartid);
		// if (ShoppingCartMap.getBindMobileCount() == null) {
		// ShoppingCartMap.setBindMobileCount(0);
		// }
		map.put("shoppingCart", shoppingCart);
		return "manager/shoppingCart/modifyShoppingCart";
	}

	/**
	 * 添加购物车
	 * 
	 * @param ShoppingCart
	 *            购物车信息
	 * @return 添加购物车情况
	 */
	@ResponseBody
	@RequestMapping("addShoppingCart")
	public ResponseMsg addShoppingCart(ShoppingCart shoppingCart) {
		try {
			// if (ShoppingCart.getBindMobileCount() == null || ShoppingCart.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，购物车需联系管理员从管理平台修改)
			// ShoppingCart.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = shoppingCartService.addShoppingCart(shoppingCart);
			if (row > 0) {
				return ResponseMsg.success("添加购物车成功！");
			} else {
				return ResponseMsg.fail("添加购物车失败！");
			}
		} catch (Exception e) {
			logger.error("添加购物车异常", e);
			return ResponseMsg.fail("添加购物车异常！");
		}

	}

	/**
	 * 修改购物车
	 * 
	 * @param ShoppingCart
	 *            购物车信息
	 * @return 修改购物车情况
	 */
	@ResponseBody
	@RequestMapping("modifyShoppingCart")
	public ResponseMsg modifyShoppingCart(ShoppingCart shoppingCart) {
		int row = shoppingCartService.modifyShoppingCart(shoppingCart);
		if (row > 0) {
			return ResponseMsg.success("修改购物车成功！");
		} else {
			return ResponseMsg.fail("修改购物车失败！");
		}
	}

	/**
	 * 删除购物车
	 * 
	 * @param id
	 *            购物车ID
	 * @return 删除购物车情况
	 */
	@ResponseBody
	@RequestMapping("removeShoppingCart")
	public ResponseMsg removeShoppingCartById(String id) {
		int row = shoppingCartService.removeShoppingCartById(id);
		if (row > 0) {
			return ResponseMsg.success("删除购物车成功！");
		} else {
			return ResponseMsg.fail("删除购物车失败！");
		}
	}

}
