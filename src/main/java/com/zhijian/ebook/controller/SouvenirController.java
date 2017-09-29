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
import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.service.SouvenirService;



/**
 * 
 * 纪念品控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/souvenir")
public class SouvenirController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SouvenirService SouvenirService;

	@Autowired
	private SouvenirService souvenirService;

	/**
	 * 纪念品列表界面
	 * 
	 * @return 纪念品列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/souvenir/souvenirList";
	}

	/**
	 * 获取纪念品分页数据
	 * 
	 * @param Souvenir
	 *            纪念品实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findSouvenirPagination")
	public EasyuiPagination<Souvenir> findSouvenirPagination(Souvenir souvenir, Integer page, Integer rows) {

		return souvenirService.findSouvenirPagination(souvenir, page, rows);
	}

	/**
	 * 添加纪念品页面
	 * 
	 * @return 添加纪念品路径
	 */
	@RequestMapping("addSouvenirView")
	public String addSouvenirView() {
		return "manager/souvenir/addSouvenir";
	}

	/**
	 * 修改纪念品界面
	 * 
	 * @param map
	 *            纪念品信息
	 * @param SouvenirId
	 *            纪念品ID
	 * @return 修改纪念品路径
	 */
	@RequestMapping("modifySouvenirView")
	public String modifySouvenirView(ModelMap map, String souvenirid) {
		Souvenir souvenir = SouvenirService.findSouvenirById(souvenirid);
		// if (SouvenirMap.getBindMobileCount() == null) {
		// SouvenirMap.setBindMobileCount(0);
		// }
		map.put("souvenir", souvenir);
		return "manager/souvenir/modifySouvenir";
	}

	/**
	 * 添加纪念品
	 * 
	 * @param Souvenir
	 *            纪念品信息
	 * @return 添加纪念品情况
	 */
	@ResponseBody
	@RequestMapping("addSouvenir")
	public ResponseMsg addSouvenir(Souvenir souvenir) {
		try {
			// if (Souvenir.getBindMobileCount() == null || Souvenir.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，纪念品需联系管理员从管理平台修改)
			// Souvenir.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = souvenirService.addSouvenir(souvenir);
			if (row > 0) {
				return ResponseMsg.success("添加纪念品成功！");
			} else {
				return ResponseMsg.fail("添加纪念品失败！");
			}
		} catch (Exception e) {
			logger.error("添加纪念品异常", e);
			return ResponseMsg.fail("添加纪念品异常！");
		}

	}

	/**
	 * 修改纪念品
	 * 
	 * @param Souvenir
	 *            纪念品信息
	 * @return 修改纪念品情况
	 */
	@ResponseBody
	@RequestMapping("modifySouvenir")
	public ResponseMsg modifySouvenir(Souvenir souvenir) {
		// SouvenirMap.setQq(qq);
		// SouvenirMap.setCompanyName(companyName);
		// SouvenirMap.setCompanyPhone(companyPhone);
		// SouvenirMap.setCompanyAddr(companyAddr);
		// SouvenirMap.setMyReferralCode(myReferralCode);
		// SouvenirMap.setBindMobileCount(bindMobileCount);
		int row = SouvenirService.modifySouvenir(souvenir);
		if (row > 0) {
			return ResponseMsg.success("修改纪念品成功！");
		} else {
			return ResponseMsg.fail("修改纪念品失败！");
		}
	}

	/**
	 * 删除纪念品
	 * 
	 * @param id
	 *            纪念品ID
	 * @return 删除纪念品情况
	 */
	@ResponseBody
	@RequestMapping("removeSouvenir")
	public ResponseMsg removeSouvenirById(String id) {
		int row = souvenirService.removeSouvenirById(id);
		if (row > 0) {
			return ResponseMsg.success("删除纪念品成功！");
		} else {
			return ResponseMsg.fail("删除纪念品失败！");
		}
	}

}
