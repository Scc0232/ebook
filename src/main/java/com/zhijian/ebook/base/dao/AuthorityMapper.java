package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.base.entity.AuthorityExample;
import com.zhijian.ebook.bean.Page;

public interface AuthorityMapper {
    int countByExample(AuthorityExample example);

    int deleteByExample(AuthorityExample example);

    int deleteByPrimaryKey(String id);

    int insert(Authority record);

    int insertSelective(Authority record);

    List<Authority> selectByExample(AuthorityExample example);

    Authority selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Authority record, @Param("example") AuthorityExample example);

    int updateByExample(@Param("record") Authority record, @Param("example") AuthorityExample example);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    List<Authority> findPaginationList(@Param("page") Page page, @Param("example") AuthorityExample example);
    
    List<Authority> unSelectAuthorityByRoleId(@Param("page") Page page, @Param("roleId") String roleId,@Param("authority") Authority authority);
    
    int countByUnRoleId(@Param("roleId") String roleId,@Param("authority") Authority authority);
    
    List<Authority> findAuthorityByRoleId(@Param("page") Page page, @Param("roleId") String roleId,@Param("authority") Authority authority);
    
    int countByRoleId(String roleId);
    
    List<Authority> findAuthorityListByResourceId(String resourceId);
    
    int countByRoleId(@Param("roleId") String roleId,@Param("authority") Authority authority);
}
