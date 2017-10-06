package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Order;

public interface OrderService {

	EasyuiPagination<Order> findOrderPagination(Order order, Integer page, Integer rows);

	Order findOrderById(String orderid);

	int addOrder(Order order);

	int modifyOrder(Order order);

	int removeOrderById(String id);

}
