package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.AreaMapper;
import com.zhijian.ebook.base.entity.Area;
import com.zhijian.ebook.base.entity.AreaExample;
import com.zhijian.ebook.base.service.AreaService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;


@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;
    
    @Override
    public int addArea(Area area) {
        return areaMapper.insert(area);
    }

    @Override
    public int modifyArea(Area area) {
        return areaMapper.updateByPrimaryKey(area);
    }

    @Override
    public int removeAreaById(String id) {
        return areaMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EasyuiPagination<Area> findAreaPagination(Area area, int page, int rows) {
        try {
            AreaExample example = new AreaExample();
            AreaExample.Criteria criteria = example.createCriteria();
            if(StringUtils.isNotBlank(area.getName())){
            	String name = "%"+area.getName()+"%";
                criteria.andNameLike(name);
            }
            if (StringUtils.isNotBlank(area.getCode())) {
            	String code = "%"+area.getCode()+"%";
                criteria.andCodeLike(code);
            }
            Integer total = areaMapper.countByExample(example);
            List<Area> list = areaMapper.findPaginationList(new Page(page, rows),example);;
            return new EasyuiPagination<Area>(total, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Area findAreaById(String id) {
        return areaMapper.selectByPrimaryKey(id);
    }


}

