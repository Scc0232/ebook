package com.zhijian.ebook.dao;

import com.zhijian.ebook.entity.DiaryLike;
import com.zhijian.ebook.entity.DiaryLikeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiaryLikeMapper {
    int countByExample(DiaryLikeExample example);

    int deleteByExample(DiaryLikeExample example);

    int deleteByPrimaryKey(String id);

    int insert(DiaryLike record);

    int insertSelective(DiaryLike record);

    List<DiaryLike> selectByExample(DiaryLikeExample example);

    DiaryLike selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DiaryLike record, @Param("example") DiaryLikeExample example);

    int updateByExample(@Param("record") DiaryLike record, @Param("example") DiaryLikeExample example);

    int updateByPrimaryKeySelective(DiaryLike record);

    int updateByPrimaryKey(DiaryLike record);
}