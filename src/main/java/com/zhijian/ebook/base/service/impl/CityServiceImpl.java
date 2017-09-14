package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.CityMapper;
import com.zhijian.ebook.base.entity.City;
import com.zhijian.ebook.base.entity.CityExample;
import com.zhijian.ebook.base.service.CityService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;

@Service
public class CityServiceImpl implements CityService{

    @Autowired
    private CityMapper cityMapper;
    
    @Override
    public int addCity(City city) {
        return cityMapper.insert(city);
    }

    @Override
    public int modifyCity(City city) {
        return cityMapper.updateByPrimaryKey(city);
    }

    @Override
    public int removeCityById(String id) {
        return cityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EasyuiPagination<City> findCityPagination(City city, int page, int rows) {
        try {
            CityExample example = new CityExample();
            CityExample.Criteria criteria = example.createCriteria();
            if(StringUtils.isNotBlank(city.getName())){
            	String name = "%"+city.getName()+"%";
                criteria.andNameLike(name);
            }
            if (StringUtils.isNotBlank(city.getCode())) {
            	String code = "%"+city.getCode()+"%";
                criteria.andCodeLike(code);
            }
            Integer total = cityMapper.countByExample(example);
            List<City> list = cityMapper.findPaginationList(new Page(page, rows),example);;
            return new EasyuiPagination<City>(total, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City findCityById(String id) {
        return cityMapper.selectByPrimaryKey(id);
    }

}
