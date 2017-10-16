package com.zhijian.ebook.dao;

import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.entity.MajorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MajorMapper {
	int countByExample(MajorExample example);

	int deleteByExample(MajorExample example);

	int deleteByPrimaryKey(String id);

	int insert(Major record);

	int insertSelective(Major record);

	List<Major> selectByExample(MajorExample example);

	Major selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Major record, @Param("example") MajorExample example);

	int updateByExample(@Param("record") Major record, @Param("example") MajorExample example);

	int updateByPrimaryKeySelective(Major record);

	int updateByPrimaryKey(Major record);

	@Select("select distinct college_name as collegeName from e_major order by college_name desc")
	List<Major> selectCollegeList();

	@Select("select distinct academy_name as academy_name from e_major  where college_name = #{collegeName} order by academy_name desc")
	List<String> selectAcademyList(String collegeName);

	@Select("select distinct profession_name as profession_name from e_major  where academy_name = #{academyName} and college_name = #{collegeName} order by profession_name desc")
	List<String> selectProfessionList(@Param("academyName")String academyName,@Param("collegeName")String collegeName);

}