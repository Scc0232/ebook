package com.zhijian.ebook.dao;

import com.zhijian.ebook.entity.BookShelf;
import com.zhijian.ebook.entity.BookShelfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookShelfMapper {
    int countByExample(BookShelfExample example);

    int deleteByExample(BookShelfExample example);

    int deleteByPrimaryKey(String id);

    int insert(BookShelf record);

    int insertSelective(BookShelf record);

    List<BookShelf> selectByExample(BookShelfExample example);

    BookShelf selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BookShelf record, @Param("example") BookShelfExample example);

    int updateByExample(@Param("record") BookShelf record, @Param("example") BookShelfExample example);

    int updateByPrimaryKeySelective(BookShelf record);

    int updateByPrimaryKey(BookShelf record);
}