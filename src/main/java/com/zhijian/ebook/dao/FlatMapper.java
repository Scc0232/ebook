package com.zhijian.ebook.dao;

import com.zhijian.ebook.entity.Flat;
import com.zhijian.ebook.entity.FlatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlatMapper {
    int countByExample(FlatExample example);

    int deleteByExample(FlatExample example);

    int insert(Flat record);

    int insertSelective(Flat record);

    List<Flat> selectByExample(FlatExample example);

    int updateByExampleSelective(@Param("record") Flat record, @Param("example") FlatExample example);

    int updateByExample(@Param("record") Flat record, @Param("example") FlatExample example);

	List<String> selectCollege();

	List<String> selectFlat(@Param("collegename")String collegename);
}