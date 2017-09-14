package com.zhijian.ebook.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.City;
import com.zhijian.ebook.base.service.CityService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;


/**
 * 
 * 城市控制器
 * 
 * @author lidongwei on 15/1/23
 *
 */
@Controller
@RequestMapping("manager/city")
public class CityController {
    
    @Autowired
    private CityService cityService;

    /**
     * 获取市区主页视图
     */
    @RequestMapping("index")
    public String index(){
        return "manager/city/cityList";
    }
    
    /**
     * 获取城市分页数据
     * @param city 城市实体
     * @param page 页数
     * @param rows 行数
     * @return 分页数据
     */
    @ResponseBody
    @RequestMapping("findCityPagination")
    public EasyuiPagination<City> findCityPagination(City city,int page, int rows) {
        return cityService.findCityPagination(city, page, rows);
    }
    
    /**
     * 获取城市添加视图
     */
    @RequestMapping("addCityView")
    public String addCityView(){
        return "manager/city/addCity";
    }
    
    /**
     * 获取城市修改视图
     */
    @RequestMapping("modifyCityView")
    public String modifyCityView(ModelMap map, String cityId){
        City city = cityService.findCityById(cityId);
        map.put("city", city);
        return "manager/city/modifyCity";
    }
    
    /**
     * 添加城市
     * @param dict 城市实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("addCity")
    public ResponseMsg addCity(City city) {
        int row = cityService.addCity(city);
        if (row > 0) {
            return ResponseMsg.success("添加城市成功！");
        } else {
            return ResponseMsg.fail("添加城市失败！");
        }
    }
    
    /**
     * 修改城市
     * @param dict 城市实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("modifyCity")
    public ResponseMsg modifyCity(City city) {
        int row = cityService.modifyCity(city);
        if (row > 0) {
            return ResponseMsg.success("修改城市成功！");
        } else {
            return ResponseMsg.fail("修改城市失败！");
        }
    }
    
    /**
     * 删除城市
     * @param dict 城市实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("cityRemove")
    public ResponseMsg cityRemove(String id) {
        int row = cityService.removeCityById(id);
        if (row > 0) {
            return ResponseMsg.success("删除城市成功！");
        } else {
            return ResponseMsg.fail("删除城市失败！");
        }
    }
    
}
