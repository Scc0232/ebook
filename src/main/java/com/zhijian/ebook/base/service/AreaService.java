package com.zhijian.ebook.base.service;


import com.zhijian.ebook.base.entity.Area;
import com.zhijian.ebook.bean.EasyuiPagination;
/**
 * 区域 服务层
 * 
 * @author lidongwei on 15/1/23
 *
 */
public interface AreaService {

    /**
     * 添加区域
     * @param area 区域实体
     * @return 
     */
    int addArea(Area area);
    
    /**
     * 修改区域
     * @param area 区域实体
     * @return 
     */
    int modifyArea(Area area);
    
    /**
     * 删除区域
     * @param id 区域主键id
     * @return 
     */
    int removeAreaById(String id);
    
    /**
     * 分页查询区域
     * @param page 页数
     * @param rows 行数
     * @return 分页数据
     * @return 
     */
    EasyuiPagination<Area> findAreaPagination(Area area, int page, int rows);
    
    /**
     * 根据id查询区域详情
     * @param id 区域主键id
     * @return 
     */
    Area findAreaById(String id);

}
