package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.ShoppingCart;

public interface ShoppingCartService {

	EasyuiPagination<ShoppingCart> findShoppingCartPagination(ShoppingCart shoppingCart, Integer page, Integer rows);

	ShoppingCart findShoppingCartById(String shoppingCartid);

	int addShoppingCart(ShoppingCart shoppingCart);

	int modifyShoppingCart(ShoppingCart shoppingCart);

	int removeShoppingCartById(String id);

}
