package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.UserRoleRelation;
import com.zhijian.ebook.base.entity.UserRoleRelationExample;

public interface UserRoleRelationMapper {
    int countByExample(UserRoleRelationExample example);

    int deleteByExample(UserRoleRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserRoleRelation record);

    int insertSelective(UserRoleRelation record);

    List<UserRoleRelation> selectByExample(UserRoleRelationExample example);

    UserRoleRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserRoleRelation record, @Param("example") UserRoleRelationExample example);

    int updateByExample(@Param("record") UserRoleRelation record, @Param("example") UserRoleRelationExample example);

    int updateByPrimaryKeySelective(UserRoleRelation record);

    int updateByPrimaryKey(UserRoleRelation record);
}