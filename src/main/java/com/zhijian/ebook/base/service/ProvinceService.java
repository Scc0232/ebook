package com.zhijian.ebook.base.service;


import com.zhijian.ebook.base.entity.Province;
import com.zhijian.ebook.bean.EasyuiPagination;
/**
 * 省份 服务层
 * 
 * @author lidongwei on 15/1/23
 *
 */
public interface ProvinceService {

    /**
     * 添加省份
     * @param province 省份实体
     * @return 
     */
    int addProvince(Province province);
    
    /**
     * 修改省份
     * @param province 省份实体
     * @return 
     */
    int modifyProvince(Province province);
    
    /**
     * 删除省份
     * @param id 省份主键id
     * @return 
     */
    int removeProvinceById(String id);
    
    /**
     * 分页查询省份
     * @param page 页数
     * @param rows 行数
     * @return 分页数据
     * @return 
     */
    EasyuiPagination<Province> findProvincePagination(Province province, int page, int rows);
    
    /**
     * 根据id查询省份详情
     * @param id 省份主键id
     * @return 
     */
    Province findProvinceById(String id);

}
