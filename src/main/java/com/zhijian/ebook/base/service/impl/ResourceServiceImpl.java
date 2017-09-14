package com.zhijian.ebook.base.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.AuthorityMapper;
import com.zhijian.ebook.base.dao.ResourceMapper;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.base.entity.Resource;
import com.zhijian.ebook.base.entity.ResourceExample;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.ResourceService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.security.UserContextHelper;
//import com.gener.common.util.ObjectUtil;
@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Override
	public Map<RequestMatcher, Collection<ConfigAttribute>> getRequestMap() throws Exception {
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();
        List<Resource> list = resourceMapper.selectByExample(null);
        Iterator<Resource> it = list.iterator();
        while (it.hasNext()) {
            put(it.next(), requestMap);
        }
        return requestMap;
	}

	private void put(Resource resource, Map<RequestMatcher, Collection<ConfigAttribute>> requestMap) throws Exception {
        String url = resource.getUrl();
        String resourceId = resource.getId();
        RequestMatcher matcher = new AntPathRequestMatcher(url);
        List<ConfigAttribute> conf = new ArrayList<ConfigAttribute>();
        List<Authority> authorityList = authorityMapper.findAuthorityListByResourceId(resourceId);
        for (Authority authority : authorityList) {// 将所有和这个资源有关系的权限都添加至conf中
            ConfigAttribute attr = new SecurityConfig(authority.getName());
            conf.add(attr);
        }
        requestMap.put(matcher, conf);
    }

	@Override
	public Resource getResource(List<Resource> list, String resourceId) {
		if (StringUtils.isEmpty(resourceId)) {
            return null;
        }
        for (Resource r : list) {
            if (resourceId.equals(r.getId())) {
                return r;
            }
        }
        return null;
	}

	@Override
	public EasyuiPagination<Resource> findResourcePagination(Resource resource,
			Integer page, Integer rows) {
		ResourceExample example = new ResourceExample();
		ResourceExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(resource.getDisplayName())){
			String displayName = "%"+resource.getDisplayName()+"%";
			criteria.andDisplayNameLike(displayName);
		}
		example.setOrderByClause("CREATE_TIME");
		Integer total = resourceMapper.countByExample(example);
		
		List<Resource> list =null;
		try {
			list = resourceMapper.findPaginationList(new Page(page, rows), example);
			return new EasyuiPagination<Resource>(total, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public int addResource(Resource resource) {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		resource.setModifyPersonId(ModifyPersonId);
		String createPersonId = user.getId();
		resource.setCreatePersonId(createPersonId);
		Date date = new Date();
		resource.setCreateTime(date);
		return resourceMapper.insert(resource);
	}

	@Override
	public int removeResourceById(String id) {
		return resourceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int modifyResource(Resource resource) {
		String ModifyPersonId = UserContextHelper.getUsername();
		User user = userMapper.findUserByUsername(ModifyPersonId);
		ModifyPersonId = user.getId(); 
		resource.setModifyPersonId(ModifyPersonId);
		Date date = new Date();
		resource.setModifyTime(date);
		return resourceMapper.updateByPrimaryKey(resource);
	}

	@Override
	public Resource findResourceById(String id) {
		return resourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public EasyuiPagination<Resource> getThisAuthorityUnselected(Resource resource,
			String authorityId, Integer page, Integer rows) throws Exception {
		Integer total = resourceMapper.countResourceByUnAuthorityId(authorityId,resource);
		List<Resource> list = resourceMapper.unSelectResourceByAuthorityId(new Page(page, rows), authorityId,resource);
		return new EasyuiPagination<Resource>(total,list);
	}

	@Override
	public EasyuiPagination<Resource> getThisAuthoritySelected(Resource resource,
			String authorityId, Integer page, Integer rows) throws Exception {
		Integer total = resourceMapper.countByAuthorityId(authorityId,resource);
		List<Resource> list = resourceMapper.findResourceByAuthorityId(new Page(page, rows), authorityId,resource);
		return new EasyuiPagination<Resource>(total,list);
	}
}
