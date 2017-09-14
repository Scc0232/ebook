package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.StyleSetting;
import com.zhijian.ebook.base.entity.StyleSettingExample;
import com.zhijian.ebook.bean.Page;

public interface StyleSettingMapper {
    int countByExample(StyleSettingExample example);

    int deleteByExample(StyleSettingExample example);

    int deleteByPrimaryKey(String id);

    int insert(StyleSetting record);

    int insertSelective(StyleSetting record);

    List<StyleSetting> selectByExample(StyleSettingExample example);

    StyleSetting selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StyleSetting record, @Param("example") StyleSettingExample example);

    int updateByExample(@Param("record") StyleSetting record, @Param("example") StyleSettingExample example);

    int updateByPrimaryKeySelective(StyleSetting record);

    int updateByPrimaryKey(StyleSetting record);

    List<StyleSetting> findPaginationList(@Param("page") Page page, @Param("example") StyleSettingExample example);
    List<StyleSetting> findOrgIdList(@Param("page") Page page, @Param("example") StyleSettingExample example);
}