package com.zhijian.ebook.dao;

import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiaryMapper {
    int countByExample(DiaryExample example);

    int deleteByExample(DiaryExample example);

    int deleteByPrimaryKey(String id);

    int insert(Diary record);

    int insertSelective(Diary record);

    List<Diary> selectByExample(DiaryExample example);

    Diary selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Diary record, @Param("example") DiaryExample example);

    int updateByExample(@Param("record") Diary record, @Param("example") DiaryExample example);

    int updateByPrimaryKeySelective(Diary record);

    int updateByPrimaryKey(Diary record);

	List<Diary> selectDiaryByExample(@Param("example") DiaryExample example,@Param("page") Page page);

	List<Diary> findPaginationList(@Param("page") Page page, @Param("example") DiaryExample example);
}