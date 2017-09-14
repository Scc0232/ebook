package com.zhijian.ebook.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.Area;
import com.zhijian.ebook.base.service.AreaService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 
 * 区域控制器
 * 
 * @author lidongwei on 15/1/22
 *
 */
@Controller
@RequestMapping("manager/area")
public class AreaController {
    
    @Autowired
    private AreaService areaService;
    
    /**
     * 获取区域主页视图
     */
    @RequestMapping("index")
    public String index(){
        return "manager/area/areaList";
    }
    
    /**
     * 获取区域分页数据
     * @param area 区域实体
     * @param page 页数
     * @param rows 行数
     * @return 分页数据
     */
    @ResponseBody
    @RequestMapping("findAreaPagination")
    public EasyuiPagination<Area> findAreaPagination(Area area,int page, int rows) {
        return areaService.findAreaPagination(area, page, rows);
    }
    
    /**
     * 获取区域添加视图
     */
    @RequestMapping("addAreaView")
    public String addAreaView(){
        return "manager/area/addArea";
    }
    
    /**
     * 获取区域修改视图
     */
    @RequestMapping("modifyAreaView")
    public String modifyAreaView(ModelMap map, String areaId){
        Area area = areaService.findAreaById(areaId);
        map.put("area", area);
        return "manager/area/modifyArea";
    }
    
    /**
     * 添加区域
     * @param area 区域实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("addArea")
    public ResponseMsg addArea(Area area) {
        int row = areaService.addArea(area);
        if (row > 0) {
            return ResponseMsg.success("添加区域成功！");
        } else {
            return ResponseMsg.fail("添加区域失败！");
        }
    }
    
    /**
     * 修改区域
     * @param area 区域实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("modifyArea")
    public ResponseMsg modifyArea(Area area) {
        int row = areaService.modifyArea(area);
        if (row > 0) {
            return ResponseMsg.success("修改区域成功！");
        } else {
            return ResponseMsg.fail("修改区域失败！");
        }
    }
    
    /**
     * 删除区域
     * @param area 区域实体
     * @return ResponseMsg 结果信息 
     */
    @ResponseBody
    @RequestMapping("areaRemove")
    public ResponseMsg areaRemove(String id) {
        int row = areaService.removeAreaById(id);
        if (row > 0) {
            return ResponseMsg.success("删除区域成功！");
        } else {
            return ResponseMsg.fail("删除区域失败！");
        }
    }
    
}
