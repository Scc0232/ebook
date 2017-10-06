package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.ShoppingCartMapper;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.entity.ShoppingCartExample;
import com.zhijian.ebook.service.ShoppingCartService;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public EasyuiPagination<ShoppingCart> findShoppingCartPagination(ShoppingCart shoppingCart, Integer page, Integer rows) {
		ShoppingCartExample shoppingCartExample = new ShoppingCartExample();
		ShoppingCartExample.Criteria criteria = shoppingCartExample.createCriteria();
		if (StringUtils.isNotBlank(shoppingCart.getProductName())) {
			criteria.andProductNameLike("%" + shoppingCart.getProductName() + "%");
		}
		if (shoppingCart.getProductType()!=null) {
			criteria.andProductTypeEqualTo(shoppingCart.getProductType());
		}
		criteria.andIsValidEqualTo(true);
		shoppingCartExample.setOrderByClause(" create_time desc");
		List<ShoppingCart> list = shoppingCartMapper.findPaginationList(new Page(page, rows), shoppingCartExample);
		for(ShoppingCart sCart : list) {
			String username = userMapper.selectByPrimaryKey(sCart.getUserid()).getPetName();
			if (StringUtils.isNotBlank(username)) {
				sCart.setUsername(username);
			}
			
		}
		int counts = shoppingCartMapper.countByExample(shoppingCartExample);
		return new EasyuiPagination<ShoppingCart>(counts, list);
	}

	@Override
	public ShoppingCart findShoppingCartById(String shoppingCartid) {
		return shoppingCartMapper.selectByPrimaryKey(shoppingCartid);
	}

	@Override
	public int addShoppingCart(ShoppingCart shoppingCart) {
		return shoppingCartMapper.insert(shoppingCart);
	}

	@Override
	public int modifyShoppingCart(ShoppingCart shoppingCart) {
		return shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
	}

	@Override
	public int removeShoppingCartById(String id) {
		return shoppingCartMapper.deleteByPrimaryKey(id);
	}

}
