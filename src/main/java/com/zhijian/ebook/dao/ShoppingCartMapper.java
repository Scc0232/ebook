package com.zhijian.ebook.dao;

import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.entity.ShoppingCart;
import com.zhijian.ebook.entity.ShoppingCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartMapper {
	int countByExample(ShoppingCartExample example);

	int deleteByExample(ShoppingCartExample example);

	int deleteByPrimaryKey(String id);

	int insert(ShoppingCart record);

	int insertSelective(ShoppingCart record);

	List<ShoppingCart> selectByExample(ShoppingCartExample example);

	ShoppingCart selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

	int updateByExample(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

	int updateByPrimaryKeySelective(ShoppingCart record);

	int updateByPrimaryKey(ShoppingCart record);

	List<ShoppingCart> findPaginationList(@Param("page") Page page, @Param("example") ShoppingCartExample example);
}