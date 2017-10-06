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
import com.zhijian.ebook.entity.Order;
import com.zhijian.ebook.service.OrderService;


/**
 * 
 * 订单控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/order")
public class OrderController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private OrderService orderService;

	/**
	 * 订单列表界面
	 * 
	 * @return 订单列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/order/orderList";
	}

	/**
	 * 获取订单分页数据
	 * 
	 * @param Order
	 *            订单实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findOrderPagination")
	public EasyuiPagination<Order> findOrderPagination(Order order, Integer page, Integer rows) {

		return orderService.findOrderPagination(order, page, rows);
	}

	/**
	 * 添加订单页面
	 * 
	 * @return 添加订单路径
	 */
	@RequestMapping("addOrderView")
	public String addOrderView() {
		return "manager/order/addOrder";
	}

	/**
	 * 修改订单界面
	 * 
	 * @param map
	 *            订单信息
	 * @param OrderId
	 *            订单ID
	 * @return 修改订单路径
	 */
	@RequestMapping("modifyOrderView")
	public String modifyOrderView(ModelMap map, String orderid) {
		Order order = orderService.findOrderById(orderid);
		// if (OrderMap.getBindMobileCount() == null) {
		// OrderMap.setBindMobileCount(0);
		// }
		map.put("order", order);
		return "manager/order/modifyOrder";
	}

	/**
	 * 添加订单
	 * 
	 * @param Order
	 *            订单信息
	 * @return 添加订单情况
	 */
	@ResponseBody
	@RequestMapping("addOrder")
	public ResponseMsg addOrder(Order order) {
		try {
			// if (Order.getBindMobileCount() == null || Order.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，订单需联系管理员从管理平台修改)
			// Order.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = orderService.addOrder(order);
			if (row > 0) {
				return ResponseMsg.success("添加订单成功！");
			} else {
				return ResponseMsg.fail("添加订单失败！");
			}
		} catch (Exception e) {
			logger.error("添加订单异常", e);
			return ResponseMsg.fail("添加订单异常！");
		}

	}

	/**
	 * 修改订单
	 * 
	 * @param Order
	 *            订单信息
	 * @return 修改订单情况
	 */
	@ResponseBody
	@RequestMapping("modifyOrder")
	public ResponseMsg modifyOrder(Order order) {
		int row = orderService.modifyOrder(order);
		if (row > 0) {
			return ResponseMsg.success("修改订单成功！");
		} else {
			return ResponseMsg.fail("修改订单失败！");
		}
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 *            订单ID
	 * @return 删除订单情况
	 */
	@ResponseBody
	@RequestMapping("removeOrder")
	public ResponseMsg removeOrderById(String id) {
		int row = orderService.removeOrderById(id);
		if (row > 0) {
			return ResponseMsg.success("删除订单成功！");
		} else {
			return ResponseMsg.fail("删除订单失败！");
		}
	}

}
