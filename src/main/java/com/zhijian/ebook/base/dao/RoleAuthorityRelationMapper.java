package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.RoleAuthorityRelation;
import com.zhijian.ebook.base.entity.RoleAuthorityRelationExample;

public interface RoleAuthorityRelationMapper {
    int countByExample(RoleAuthorityRelationExample example);

    int deleteByExample(RoleAuthorityRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(RoleAuthorityRelation record);

    int insertSelective(RoleAuthorityRelation record);

    List<RoleAuthorityRelation> selectByExample(RoleAuthorityRelationExample example);

    RoleAuthorityRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoleAuthorityRelation record, @Param("example") RoleAuthorityRelationExample example);

    int updateByExample(@Param("record") RoleAuthorityRelation record, @Param("example") RoleAuthorityRelationExample example);

    int updateByPrimaryKeySelective(RoleAuthorityRelation record);

    int updateByPrimaryKey(RoleAuthorityRelation record);
}