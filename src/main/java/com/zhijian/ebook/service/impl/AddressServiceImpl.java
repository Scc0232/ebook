package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.AddressMapper;
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.entity.AddressExample;
import com.zhijian.ebook.service.AddressService;


@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressMapper addressMapper;

	@Override
	public EasyuiPagination<Address> findAddressPagination(Address address, Integer page, Integer rows) {
		AddressExample addressExample = new AddressExample();
		AddressExample.Criteria criteria = addressExample.createCriteria();
		if (StringUtils.isNotBlank(address.getPhone())) {
			criteria.andPhoneLike("%" + address.getPhone() + "%");
		}
		if (StringUtils.isNotBlank(address.getUsername())) {
			criteria.andUsernameLike("%" + address.getUsername() + "%");
		}
		if (StringUtils.isNotBlank(address.getCollege())) {
			criteria.andCollegeLike("%" + address.getCollege() + "%");
		}
		addressExample.setOrderByClause("username desc, create_time desc");
		List<Address> list = addressMapper.findPaginationList(new Page(page, rows), addressExample);

		return new EasyuiPagination<Address>(list.size(), list);
	}

	@Override
	public Address findAddressById(String addressid) {
		return addressMapper.selectByPrimaryKey(addressid);
	}

	@Override
	public int addAddress(Address address) {
		return addressMapper.insert(address);
	}

	@Override
	public int modifyAddress(Address address) {
		return addressMapper.updateByPrimaryKeySelective(address);
	}

	@Override
	public int removeAddressById(String id) {
		return addressMapper.deleteByPrimaryKey(id);
	}

}
