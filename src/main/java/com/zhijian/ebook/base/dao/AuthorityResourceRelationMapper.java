package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.AuthorityResourceRelation;
import com.zhijian.ebook.base.entity.AuthorityResourceRelationExample;

public interface AuthorityResourceRelationMapper {
    int countByExample(AuthorityResourceRelationExample example);

    int deleteByExample(AuthorityResourceRelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(AuthorityResourceRelation record);

    int insertSelective(AuthorityResourceRelation record);

    List<AuthorityResourceRelation> selectByExample(AuthorityResourceRelationExample example);

    AuthorityResourceRelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AuthorityResourceRelation record, @Param("example") AuthorityResourceRelationExample example);

    int updateByExample(@Param("record") AuthorityResourceRelation record, @Param("example") AuthorityResourceRelationExample example);

    int updateByPrimaryKeySelective(AuthorityResourceRelation record);

    int updateByPrimaryKey(AuthorityResourceRelation record);
}