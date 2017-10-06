package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.OrderMapper;
import com.zhijian.ebook.entity.Order;
import com.zhijian.ebook.entity.OrderExample;
import com.zhijian.ebook.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public EasyuiPagination<Order> findOrderPagination(Order order, Integer page, Integer rows) {
		OrderExample orderExample = new OrderExample();
		OrderExample.Criteria criteria = orderExample.createCriteria();
		if (StringUtils.isNotBlank(order.getProductName())) {
			criteria.andProductNameLike("%" + order.getProductName() + "%");
		}
		if (order.getProductType() != null) {
			criteria.andProductTypeEqualTo(order.getProductType());
		}
		if (order.getOrderStatus() != null) {
			criteria.andOrderStatusEqualTo(order.getOrderStatus());
		}
		criteria.andIsValidEqualTo(true);
		orderExample.setOrderByClause(" create_time desc");
		List<Order> list = orderMapper.findPaginationList(new Page(page, rows), orderExample);
		for (Order sCart : list) {
			String username = userMapper.selectByPrimaryKey(sCart.getUserid()).getPetName();
			if (StringUtils.isNotBlank(username)) {
				sCart.setUsername(username);
			}
		}
		int counts = orderMapper.countByExample(orderExample);
		return new EasyuiPagination<Order>(counts, list);
	}

	@Override
	public Order findOrderById(String orderid) {
		return orderMapper.selectByPrimaryKey(orderid);
	}

	@Override
	public int addOrder(Order order) {
		return orderMapper.insert(order);
	}

	@Override
	public int modifyOrder(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public int removeOrderById(String id) {
		return orderMapper.deleteByPrimaryKey(id);
	}

}
