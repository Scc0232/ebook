package com.zhijian.ebook.dao;

import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.entity.DiaryComment;
import com.zhijian.ebook.entity.DiaryCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiaryCommentMapper {
    int countByExample(DiaryCommentExample example);

    int deleteByExample(DiaryCommentExample example);

    int deleteByPrimaryKey(String id);

    int insert(DiaryComment record);

    int insertSelective(DiaryComment record);

    List<DiaryComment> selectByExample(DiaryCommentExample example);

    DiaryComment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DiaryComment record, @Param("example") DiaryCommentExample example);

    int updateByExample(@Param("record") DiaryComment record, @Param("example") DiaryCommentExample example);

    int updateByPrimaryKeySelective(DiaryComment record);

    int updateByPrimaryKey(DiaryComment record);

	List<DiaryComment> slectCommentsByExample(@Param("example")DiaryCommentExample example, @Param("page")Page page);
}