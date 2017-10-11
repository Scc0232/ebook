package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Donation;

public interface DonationService {

	EasyuiPagination<Donation> findDonationPagination(Donation donation, Integer page, Integer rows);

	Donation findDonationById(String donationid);

	int addDonation(Donation donation);

	int modifyDonation(Donation donation);

	int removeDonationById(String id);

}
