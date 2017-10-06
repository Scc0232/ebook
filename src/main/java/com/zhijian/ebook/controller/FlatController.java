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
import com.zhijian.ebook.entity.Flat;
import com.zhijian.ebook.service.FlatService;


/**
 * 
 * 宿舍控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/flat")
public class FlatController {
	private static final Logger logger = LogManager.getLogger();


	@Autowired
	private FlatService flatService;

	/**
	 * 宿舍列表界面
	 * 
	 * @return 宿舍列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/flat/flatList";
	}

	/**
	 * 获取宿舍分页数据
	 * 
	 * @param Flat
	 *            宿舍实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findFlatPagination")
	public EasyuiPagination<Flat> findFlatPagination(Flat flat, Integer page, Integer rows) {

		return flatService.findFlatPagination(flat, page, rows);
	}

	/**
	 * 添加宿舍页面
	 * 
	 * @return 添加宿舍路径
	 */
	@RequestMapping("addFlatView")
	public String addFlatView() {
		return "manager/flat/addFlat";
	}

	/**
	 * 修改宿舍界面
	 * 
	 * @param map
	 *            宿舍信息
	 * @param FlatId
	 *            宿舍ID
	 * @return 修改宿舍路径
	 */
	@RequestMapping("modifyFlatView")
	public String modifyFlatView(ModelMap map, String flatid) {
		Flat flat = flatService.findFlatById(flatid);
		// if (FlatMap.getBindMobileCount() == null) {
		// FlatMap.setBindMobileCount(0);
		// }
		map.put("flat", flat);
		return "manager/flat/modifyFlat";
	}

	/**
	 * 添加宿舍
	 * 
	 * @param Flat
	 *            宿舍信息
	 * @return 添加宿舍情况
	 */
	@ResponseBody
	@RequestMapping("addFlat")
	public ResponseMsg addFlat(Flat flat) {
		try {
			// if (Flat.getBindMobileCount() == null || Flat.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，宿舍需联系管理员从管理平台修改)
			// Flat.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = flatService.addFlat(flat);
			if (row > 0) {
				return ResponseMsg.success("添加宿舍成功！");
			} else {
				return ResponseMsg.fail("添加宿舍失败！");
			}
		} catch (Exception e) {
			logger.error("添加宿舍异常", e);
			return ResponseMsg.fail("添加宿舍异常！");
		}

	}

	/**
	 * 修改宿舍
	 * 
	 * @param Flat
	 *            宿舍信息
	 * @return 修改宿舍情况
	 */
	@ResponseBody
	@RequestMapping("modifyFlat")
	public ResponseMsg modifyFlat(Flat flat) {
		// FlatMap.setQq(qq);
		// FlatMap.setCompanyName(companyName);
		// FlatMap.setCompanyPhone(companyPhone);
		// FlatMap.setCompanyAddr(companyAddr);
		// FlatMap.setMyReferralCode(myReferralCode);
		// FlatMap.setBindMobileCount(bindMobileCount);
		int row = flatService.modifyFlat(flat);
		if (row > 0) {
			return ResponseMsg.success("修改宿舍成功！");
		} else {
			return ResponseMsg.fail("修改宿舍失败！");
		}
	}

	/**
	 * 删除宿舍
	 * 
	 * @param id
	 *            宿舍ID
	 */
	@ResponseBody
	@RequestMapping("removeFlat")
	public ResponseMsg removeFlatById(String id) {
		int row = flatService.removeFlatById(id);
		if (row > 0) {
			return ResponseMsg.success("删除宿舍成功！");
		} else {
			return ResponseMsg.fail("删除宿舍失败！");
		}
	}

}
