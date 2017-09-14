package com.zhijian.ebook.dao;

import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.entity.SouvenirExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SouvenirMapper {
    int countByExample(SouvenirExample example);

    int deleteByExample(SouvenirExample example);

    int deleteByPrimaryKey(String id);

    int insert(Souvenir record);

    int insertSelective(Souvenir record);

    List<Souvenir> selectByExample(SouvenirExample example);

    Souvenir selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Souvenir record, @Param("example") SouvenirExample example);

    int updateByExample(@Param("record") Souvenir record, @Param("example") SouvenirExample example);

    int updateByPrimaryKeySelective(Souvenir record);

    int updateByPrimaryKey(Souvenir record);
}