package com.zhijian.ebook.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.Province;
import com.zhijian.ebook.base.service.ProvinceService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 
 * 省份控制器
 * 
 * @author lidongwei on 15/1/23
 *
 */
@Controller
@RequestMapping("manager/province")
public class ProvinceController {
    
    @Autowired
    private ProvinceService provinceService;
    
    /**
     * 获取省份主页视图
     */
    @RequestMapping("index")
    public String index(){
        return "manager/province/provinceList";
    }
    
    /**
     * 获取省份分页数据
     * @param province 省份实体
     * @param page 页数
     * @param rows 行数
     * @return 分页数据
     */
    @ResponseBody
    @RequestMapping("findProvincePagination")
    public EasyuiPagination<Province> findAreaPagination(Province province,int page, int rows) {
        return provinceService.findProvincePagination(province, page, rows);
    }
    
    /**
     * 获取省份添加视图
     */
    @RequestMapping("addProvinceView")
    public String addProvinceView(){
        return "manager/province/addProvince";
    }
    
    /**
     * 获取省份修改视图
     */
    @RequestMapping("modifyProvinceView")
    public String modifyProvinceView(ModelMap map, String provinceId){
        Province province = provinceService.findProvinceById(provinceId);
        map.put("province", province);
        return "manager/province/modifyProvince";
    }
    
    /**
     * 添加省份
     * @param province 省份实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("addProvince")
    public ResponseMsg addProvince(Province province) {
        int row = provinceService.addProvince(province);
        if (row > 0) {
            return ResponseMsg.success("添加省份成功！");
        } else {
            return ResponseMsg.fail("添加省份失败！");
        }
    }
    
    /**
     * 修改省份
     * @param province 省份实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("modifyProvince")
    public ResponseMsg modifyProvince(Province province) {
        int row = provinceService.modifyProvince(province);
        if (row > 0) {
            return ResponseMsg.success("修改省份成功！");
        } else {
            return ResponseMsg.fail("修改省份失败！");
        }
    }
    
    /**
     * 删除省份
     * @param province 省份实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("provinceRemove")
    public ResponseMsg provinceRemove(String id) {
        int row = provinceService.removeProvinceById(id);
        if (row > 0) {
            return ResponseMsg.success("删除省份成功！");
        } else {
            return ResponseMsg.fail("删除省份失败！");
        }
    }
    
}
