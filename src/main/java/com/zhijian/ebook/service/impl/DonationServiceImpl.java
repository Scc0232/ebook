package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.AddressMapper;
import com.zhijian.ebook.dao.DonationMapper;
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.DonationExample;
import com.zhijian.ebook.service.DonationService;

@Service
public class DonationServiceImpl implements DonationService {

	@Autowired
	private DonationMapper donationMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AddressMapper addressMapper;

	@Override
	public EasyuiPagination<Donation> findDonationPagination(Donation donation, Integer page, Integer rows) {
		DonationExample donationExample = new DonationExample();
		DonationExample.Criteria criteria = donationExample.createCriteria();
		if (StringUtils.isNotBlank(donation.getBookName())) {
			criteria.andBookNameLike("%" + donation.getBookName() + "%");
		}
		if (donation.getStatus() != null) {
			criteria.andStatusEqualTo(donation.getStatus());
		}
		donationExample.setOrderByClause("status desc, create_time asc");
		List<Donation> list = donationMapper.findPaginationList(new Page(page, rows), donationExample);
		for (Donation sCart : list) {
			User user = userMapper.selectByPrimaryKey(sCart.getUserid());
			if (user != null) {
				sCart.setUsername(user.getPetName());
			}
			Address address = addressMapper.selectByPrimaryKey(sCart.getAddressId());
			if (address != null) {
				sCart.setAddress(address.getCollege() + address.getFlat() + address.getRoomNum());
				sCart.setCnname(address.getUsername());
				sCart.setPhoneNo(address.getPhone());
			}
		}
		int counts = donationMapper.countByExample(donationExample);
		return new EasyuiPagination<Donation>(counts, list);
	}

	@Override
	public Donation findDonationById(String donationid) {
		return donationMapper.selectByPrimaryKey(donationid);
	}

	@Override
	public int addDonation(Donation donation) {
		return donationMapper.insert(donation);
	}

	@Override
	public int modifyDonation(Donation donation) {
		Donation donation2 = donationMapper.selectByPrimaryKey(donation.getId());
		User user = userMapper.selectByPrimaryKey(donation2.getUserid());
		user.setBlance(user.getBlance() + donation2.geteValue());
		userMapper.updateByPrimaryKeySelective(user);
		return donationMapper.updateByPrimaryKeySelective(donation);
	}

	@Override
	public int removeDonationById(String id) {
		return donationMapper.deleteByPrimaryKey(id);
	}

}
