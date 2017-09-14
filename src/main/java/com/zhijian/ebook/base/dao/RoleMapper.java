package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.Role;
import com.zhijian.ebook.base.entity.RoleExample;
import com.zhijian.ebook.bean.Page;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    Role findRoleByName(String name);
    
    List<Role> findPaginationList(@Param("page") Page page, @Param("example") RoleExample example);

    //未实现
   	List<Role> queryRoleByUsername(String username);
   
}