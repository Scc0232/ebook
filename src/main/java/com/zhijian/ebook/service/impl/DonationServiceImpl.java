package com.zhijian.ebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.DonationMapper;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.DonationExample;
import com.zhijian.ebook.service.DonationService;

@Service
public class DonationServiceImpl implements DonationService {

	@Autowired
	private DonationMapper donationMapper;


	@Override
	public EasyuiPagination<Donation> findDonationPagination(Donation donation, Integer page, Integer rows) {
		DonationExample donationExample = new DonationExample();
//		DonationExample.Criteria criteria = donationExample.createCriteria();
		List<Donation> list = donationMapper.findPaginationList(new Page(page, rows), donationExample);
//		for (Donation sCart : list) {
//			String username = userMapper.selectByPrimaryKey(sCart.getUserid()).getPetName();
//			if (StringUtils.isNotBlank(username)) {
//				sCart.setUsername(username);
//			}
//		}
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
		return donationMapper.updateByPrimaryKeySelective(donation);
	}

	@Override
	public int removeDonationById(String id) {
		return donationMapper.deleteByPrimaryKey(id);
	}

}
