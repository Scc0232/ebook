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
import com.zhijian.ebook.entity.Address;
import com.zhijian.ebook.service.AddressService;


/**
 * 
 * 地址控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/address")
public class AddressController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private AddressService addressService;

	/**
	 * 地址列表界面
	 * 
	 * @return 地址列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/address/addressList";
	}

	/**
	 * 获取地址分页数据
	 * 
	 * @param Address
	 *            地址实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findAddressPagination")
	public EasyuiPagination<Address> findAddressPagination(Address address, Integer page, Integer rows) {

		return addressService.findAddressPagination(address, page, rows);
	}

	/**
	 * 添加地址页面
	 * 
	 * @return 添加地址路径
	 */
	@RequestMapping("addAddressView")
	public String addAddressView() {
		return "manager/address/addAddress";
	}

	/**
	 * 修改地址界面
	 * 
	 * @param map
	 *            地址信息
	 * @param AddressId
	 *            地址ID
	 * @return 修改地址路径
	 */
	@RequestMapping("modifyAddressView")
	public String modifyAddressView(ModelMap map, String addressid) {
		Address address = addressService.findAddressById(addressid);
		// if (AddressMap.getBindMobileCount() == null) {
		// AddressMap.setBindMobileCount(0);
		// }
		map.put("address", address);
		return "manager/address/modifyAddress";
	}

	/**
	 * 添加地址
	 * 
	 * @param Address
	 *            地址信息
	 * @return 添加地址情况
	 */
	@ResponseBody
	@RequestMapping("addAddress")
	public ResponseMsg addAddress(Address address) {
		try {
			// if (Address.getBindMobileCount() == null || Address.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，地址需联系管理员从管理平台修改)
			// Address.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = addressService.addAddress(address);
			if (row > 0) {
				return ResponseMsg.success("添加地址成功！");
			} else {
				return ResponseMsg.fail("添加地址失败！");
			}
		} catch (Exception e) {
			logger.error("添加地址异常", e);
			return ResponseMsg.fail("添加地址异常！");
		}

	}

	/**
	 * 修改地址
	 * 
	 * @param Address
	 *            地址信息
	 * @return 修改地址情况
	 */
	@ResponseBody
	@RequestMapping("modifyAddress")
	public ResponseMsg modifyAddress(Address address) {
		int row = addressService.modifyAddress(address);
		if (row > 0) {
			return ResponseMsg.success("修改地址成功！");
		} else {
			return ResponseMsg.fail("修改地址失败！");
		}
	}

	/**
	 * 删除地址
	 * 
	 * @param id
	 *            地址ID
	 * @return 删除地址情况
	 */
	@ResponseBody
	@RequestMapping("removeAddress")
	public ResponseMsg removeAddressById(String id) {
		int row = addressService.removeAddressById(id);
		if (row > 0) {
			return ResponseMsg.success("删除地址成功！");
		} else {
			return ResponseMsg.fail("删除地址失败！");
		}
	}

}
