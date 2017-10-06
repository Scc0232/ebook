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
import com.zhijian.ebook.entity.Collect;
import com.zhijian.ebook.service.CollectService;


/**
 * 
 * 地址控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/collect")
public class CollectController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private CollectService collectService;

	/**
	 * 地址列表界面
	 * 
	 * @return 地址列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/collect/collectList";
	}

	/**
	 * 获取地址分页数据
	 * 
	 * @param Collect
	 *            地址实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findCollectPagination")
	public EasyuiPagination<Collect> findCollectPagination(Collect collect, Integer page, Integer rows) {

		return collectService.findCollectPagination(collect, page, rows);
	}

	/**
	 * 添加地址页面
	 * 
	 * @return 添加地址路径
	 */
	@RequestMapping("addCollectView")
	public String addCollectView() {
		return "manager/collect/addCollect";
	}

	/**
	 * 修改地址界面
	 * 
	 * @param map
	 *            地址信息
	 * @param CollectId
	 *            地址ID
	 * @return 修改地址路径
	 */
	@RequestMapping("modifyCollectView")
	public String modifyCollectView(ModelMap map, String collectid) {
		Collect collect = collectService.findCollectById(collectid);
		// if (CollectMap.getBindMobileCount() == null) {
		// CollectMap.setBindMobileCount(0);
		// }
		map.put("collect", collect);
		return "manager/collect/modifyCollect";
	}

	/**
	 * 添加地址
	 * 
	 * @param Collect
	 *            地址信息
	 * @return 添加地址情况
	 */
	@ResponseBody
	@RequestMapping("addCollect")
	public ResponseMsg addCollect(Collect collect) {
		try {
			// if (Collect.getBindMobileCount() == null || Collect.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，地址需联系管理员从管理平台修改)
			// Collect.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = collectService.addCollect(collect);
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
	 * @param Collect
	 *            地址信息
	 * @return 修改地址情况
	 */
	@ResponseBody
	@RequestMapping("modifyCollect")
	public ResponseMsg modifyCollect(Collect collect) {
		int row = collectService.modifyCollect(collect);
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
	@RequestMapping("removeCollect")
	public ResponseMsg removeCollectById(String id) {
		int row = collectService.removeCollectById(id);
		if (row > 0) {
			return ResponseMsg.success("删除地址成功！");
		} else {
			return ResponseMsg.fail("删除地址失败！");
		}
	}

}
