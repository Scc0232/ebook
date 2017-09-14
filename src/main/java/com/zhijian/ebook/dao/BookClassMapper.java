package com.zhijian.ebook.dao;

import com.zhijian.ebook.entity.BookClass;
import com.zhijian.ebook.entity.BookClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookClassMapper {
    int countByExample(BookClassExample example);

    int deleteByExample(BookClassExample example);

    int deleteByPrimaryKey(String id);

    int insert(BookClass record);

    int insertSelective(BookClass record);

    List<BookClass> selectByExample(BookClassExample example);

    BookClass selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BookClass record, @Param("example") BookClassExample example);

    int updateByExample(@Param("record") BookClass record, @Param("example") BookClassExample example);

    int updateByPrimaryKeySelective(BookClass record);

    int updateByPrimaryKey(BookClass record);
}