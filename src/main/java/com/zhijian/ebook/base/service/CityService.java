package com.zhijian.ebook.base.service;

import com.zhijian.ebook.base.entity.City;
import com.zhijian.ebook.bean.EasyuiPagination;

/**
 * 城市 服务层
 * 
 * @author lidongwei on 15/1/23
 *
 */
public interface CityService {

    /**
     * 添加城市
     * @param city 城市实体
     * @return 
     */
    
    int addCity(City city);
    
    /**
     * 修改城市
     * @param city 城市实体
     * @return 
     */
    int modifyCity(City city);
    
    /**
     * 删除城市
     * @param id 字典主键id
     * @return 
     */
    int removeCityById(String id);
    
    /**
     * 分页查询城市
     * @param page 页数
     * @param rows 行数
     * @return 分页数据
     * @return 
     */
    EasyuiPagination<City> findCityPagination(City city, int page, int rows);
    
    /**
     * 根据id查询城市详情
     * @param id 城市主键id
     * @return 
     */
    City findCityById(String id);
}
