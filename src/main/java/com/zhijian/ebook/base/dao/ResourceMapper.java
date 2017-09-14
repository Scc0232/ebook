package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.Resource;
import com.zhijian.ebook.base.entity.ResourceExample;
import com.zhijian.ebook.bean.Page;

public interface ResourceMapper {
    int countByExample(ResourceExample example);

    int deleteByExample(ResourceExample example);

    int deleteByPrimaryKey(String id);

    int insert(Resource record);

    int insertSelective(Resource record);

    List<Resource> selectByExample(ResourceExample example);

    Resource selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByExample(@Param("record") Resource record, @Param("example") ResourceExample example);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
    
    List<Resource> findPaginationList(@Param("page") Page page, @Param("example") ResourceExample example);
    
    List<Resource> findResourceByAuthorityId(@Param("page") Page page, @Param("authorityId") String authorityId,@Param("resource") Resource resource);

    int countByAuthorityId(@Param("authorityId") String authorityId,@Param("resource") Resource resource);
    
    List<Resource> unSelectResourceByAuthorityId(@Param("page") Page page, @Param("authorityId") String authorityId,@Param("resource") Resource resource);

    int countResourceByUnAuthorityId(@Param("authorityId") String authorityId,@Param("resource") Resource resource);
    
    List<Resource> findResourceListByUserId(String userId);
}