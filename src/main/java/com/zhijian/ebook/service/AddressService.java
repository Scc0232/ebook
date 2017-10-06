package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Address;

public interface AddressService {

	EasyuiPagination<Address> findAddressPagination(Address address, Integer page, Integer rows);

	Address findAddressById(String addressid);

	int addAddress(Address address);

	int modifyAddress(Address address);

	int removeAddressById(String id);

}
