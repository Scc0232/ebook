package com.zhijian.ebook.dao;

import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.entity.Flat;
import com.zhijian.ebook.entity.FlatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlatMapper {
    int countByExample(FlatExample example);

    int deleteByExample(FlatExample example);

    int deleteByPrimaryKey(String id);

    int insert(Flat record);

    int insertSelective(Flat record);

    List<Flat> selectByExample(FlatExample example);

    Flat selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Flat record, @Param("example") FlatExample example);

    int updateByExample(@Param("record") Flat record, @Param("example") FlatExample example);

    int updateByPrimaryKeySelective(Flat record);

    int updateByPrimaryKey(Flat record);

	List<String> selectCollege();

	List<String> selectFlat(@Param("collegename")String collegename);

	List<Flat> findPaginationList(@Param("page")Page page, @Param("example")FlatExample example);
}