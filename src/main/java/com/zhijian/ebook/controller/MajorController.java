package com.zhijian.ebook.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.entity.Major;
import com.zhijian.ebook.service.MajorService;



/**
 * 
 * 图书控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/major")
public class MajorController {
	private static final Logger logger = LogManager.getLogger();


	@Autowired
	private MajorService majorService;

	/**
	 * 图书列表界面
	 * 
	 * @return 图书列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/major/majorList";
	}

	/**
	 * 获取图书分页数据
	 * 
	 * @param Major
	 *            图书实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findMajorPagination")
	public EasyuiPagination<Major> findMajorPagination(Major major, Integer page, Integer rows) {

		return majorService.findMajorPagination(major, page, rows);
	}

	/**
	 * 添加图书页面
	 * 
	 * @return 添加图书路径
	 */
	@RequestMapping("addMajorView")
	public String addMajorView() {
		return "manager/major/addMajor";
	}

	/**
	 * 修改图书界面
	 * 
	 * @param map
	 *            图书信息
	 * @param MajorId
	 *            图书ID
	 * @return 修改图书路径
	 */
	@RequestMapping("modifyMajorView")
	public String modifyMajorView(ModelMap map, String majorid) {
		Major major = majorService.findMajorById(majorid);
		// if (MajorMap.getBindMobileCount() == null) {
		// MajorMap.setBindMobileCount(0);
		// }
		map.put("major", major);
		return "manager/major/modifyMajor";
	}

	/**
	 * 添加图书
	 * 
	 * @param Major
	 *            图书信息
	 * @return 添加图书情况
	 */
	@ResponseBody
	@RequestMapping("addMajor")
	public ResponseMsg addMajor(Major major, HttpServletRequest request) {
		try {
			// if (Major.getBindMobileCount() == null || Major.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，图书需联系管理员从管理平台修改)
			// Major.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = 0;
			row = majorService.addMajor(major);
			if (row > 0) {
				return ResponseMsg.success("添加图书成功！");
			} else {
				return ResponseMsg.fail("添加图书失败！");
			}
		} catch (Exception e) {
			logger.error("添加图书异常", e);
			return ResponseMsg.fail("添加图书异常！");
		}

	}

	/**
	 * 修改图书
	 * 
	 * @param Major
	 *            图书信息
	 * @return 修改图书情况
	 */
	@ResponseBody
	@RequestMapping("modifyMajor")
	public ResponseMsg modifyMajor(Major major, HttpServletRequest request) {
		// MajorMap.setQq(qq);
		// MajorMap.setCompanyName(companyName);
		// MajorMap.setCompanyPhone(companyPhone);
		// MajorMap.setCompanyAddr(companyAddr);
		// MajorMap.setMyReferralCode(myReferralCode);
		// MajorMap.setBindMobileCount(bindMobileCount);
		int row = 0;
		row = majorService.modifyMajor(major);
		if (row > 0) {
			return ResponseMsg.success("修改图书成功！");
		} else {
			return ResponseMsg.fail("修改图书失败！");
		}
	}

	/**
	 * 删除图书
	 * 
	 * @param id
	 *            图书ID
	 * @return 删除图书情况
	 */
	@ResponseBody
	@RequestMapping("removeMajor")
	public ResponseMsg removeMajorById(String id) {
		int row = majorService.removeMajorById(id);
		if (row > 0) {
			return ResponseMsg.success("删除图书成功！");
		} else {
			return ResponseMsg.fail("删除图书失败！");
		}
	}
	
	


}
