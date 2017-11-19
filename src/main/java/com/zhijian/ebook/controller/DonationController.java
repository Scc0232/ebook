package com.zhijian.ebook.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.service.DonationService;


/**
 * 
 * 订单控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/donation")
public class DonationController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private DonationService donationService;

	/**
	 * 订单列表界面
	 * 
	 * @return 订单列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/donation/donationList";
	}

	/**
	 * 获取订单分页数据
	 * 
	 * @param Donation
	 *            订单实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findDonationPagination")
	public EasyuiPagination<Donation> findDonationPagination(Donation donation, Integer page, Integer rows) {

		return donationService.findDonationPagination(donation, page, rows);
	}

	/**
	 * 添加订单页面
	 * 
	 * @return 添加订单路径
	 */
	@RequestMapping("addDonationView")
	public String addDonationView() {
		return "manager/donation/addDonation";
	}

	/**
	 * 修改订单界面
	 * 
	 * @param map
	 *            订单信息
	 * @param DonationId
	 *            订单ID
	 * @return 修改订单路径
	 */
	@RequestMapping("modifyDonationView")
	public String modifyDonationView(ModelMap map, String donationid) {
		Donation donation = donationService.findDonationById(donationid);
		// if (DonationMap.getBindMobileCount() == null) {
		// DonationMap.setBindMobileCount(0);
		// }
		map.put("donation", donation);
		return "manager/donation/modifyDonation";
	}

	/**
	 * 添加订单
	 * 
	 * @param Donation
	 *            订单信息
	 * @return 添加订单情况
	 */
	@ResponseBody
	@RequestMapping("addDonation")
	public ResponseMsg addDonation(Donation donation) {
		try {
			// if (Donation.getBindMobileCount() == null || Donation.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，订单需联系管理员从管理平台修改)
			// Donation.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = donationService.addDonation(donation);
			if (row > 0) {
				return ResponseMsg.success("添加订单成功！");
			} else {
				return ResponseMsg.fail("添加订单失败！");
			}
		} catch (Exception e) {
			logger.error("添加订单异常", e);
			return ResponseMsg.fail("添加订单异常！");
		}

	}

	/**
	 * 修改订单
	 * 
	 * @param Donation
	 *            订单信息
	 * @return 修改订单情况
	 */
	@ResponseBody
	@RequestMapping("modifyDonation")
	public ResponseMsg modifyDonation(Donation donation) {
		if (donation.getStatus()==0) {
			return ResponseMsg.success("未修改！");
		}
		int row = donationService.modifyDonation(donation);
		if (row > 0) {
			return ResponseMsg.success("修改订单成功！");
		} else {
			return ResponseMsg.fail("修改订单失败！");
		}
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 *            订单ID
	 * @return 删除订单情况
	 */
	@ResponseBody
	@RequestMapping("removeDonation")
	public ResponseMsg removeDonationById(String id) {
		int row = donationService.removeDonationById(id);
		if (row > 0) {
			return ResponseMsg.success("删除订单成功！");
		} else {
			return ResponseMsg.fail("删除订单失败！");
		}
	}

}
