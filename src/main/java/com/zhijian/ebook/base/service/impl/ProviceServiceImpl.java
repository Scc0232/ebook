package com.zhijian.ebook.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.ProvinceMapper;
import com.zhijian.ebook.base.entity.Province;
import com.zhijian.ebook.base.entity.ProvinceExample;
import com.zhijian.ebook.base.service.ProvinceService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;

@Service
public class ProviceServiceImpl implements ProvinceService{

    @Autowired
    private ProvinceMapper provinceMapper;
    
    @Override
    public int addProvince(Province province) {
        return provinceMapper.insert(province);
    }

    @Override
    public int modifyProvince(Province province) {
        return provinceMapper.updateByPrimaryKey(province);
    }

    @Override
    public int removeProvinceById(String id) {
        return provinceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EasyuiPagination<Province> findProvincePagination(Province province, int page, int rows) {
        try {
            ProvinceExample example = new ProvinceExample();
            ProvinceExample.Criteria criteria = example.createCriteria();
            if(StringUtils.isNotBlank(province.getName())){
            	String name = "%"+province.getName()+"%";
                criteria.andNameLike(name);
            }
            if (StringUtils.isNotBlank(province.getCode())) {
            	String code = "%"+province.getCode()+"%";
                criteria.andCodeLike(code);
            }
            Integer total = provinceMapper.countByExample(example);
            List<Province> list = provinceMapper.findPaginationList(new Page(page, rows),example);;
            return new EasyuiPagination<Province>(total, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Province findProvinceById(String id) {
        return provinceMapper.selectByPrimaryKey(id);
    }

}
