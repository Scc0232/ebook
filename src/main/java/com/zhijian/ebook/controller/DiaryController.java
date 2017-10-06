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
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.service.DiaryService;


/**
 * 
 * 日记控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/diary")
public class DiaryController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private DiaryService diaryService;

	/**
	 * 日记列表界面
	 * 
	 * @return 日记列表路径
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/diary/diaryList";
	}

	/**
	 * 获取日记分页数据
	 * 
	 * @param Diary
	 *            日记实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findDiaryPagination")
	public EasyuiPagination<Diary> findDiaryPagination(Diary diary, Integer page, Integer rows) {

		return diaryService.findDiaryPagination(diary, page, rows);
	}

	/**
	 * 添加日记页面
	 * 
	 * @return 添加日记路径
	 */
	@RequestMapping("addDiaryView")
	public String addDiaryView() {
		return "manager/diary/addDiary";
	}

	/**
	 * 修改日记界面
	 * 
	 * @param map
	 *            日记信息
	 * @param DiaryId
	 *            日记ID
	 * @return 修改日记路径
	 */
	@RequestMapping("modifyDiaryView")
	public String modifyDiaryView(ModelMap map, String diaryid) {
		Diary diary = diaryService.findDiaryById(diaryid);
		// if (DiaryMap.getBindMobileCount() == null) {
		// DiaryMap.setBindMobileCount(0);
		// }
		map.put("diary", diary);
		return "manager/diary/modifyDiary";
	}

	/**
	 * 添加日记
	 * 
	 * @param Diary
	 *            日记信息
	 * @return 添加日记情况
	 */
	@ResponseBody
	@RequestMapping("addDiary")
	public ResponseMsg addDiary(Diary diary) {
		try {
			// if (Diary.getBindMobileCount() == null || Diary.getBindMobileCount() == 0) {
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// //设置手机可更换绑定次数(配置文件配置，如特殊需要修改，日记需联系管理员从管理平台修改)
			// Diary.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// }
			int row = diaryService.addDiary(diary);
			if (row > 0) {
				return ResponseMsg.success("添加日记成功！");
			} else {
				return ResponseMsg.fail("添加日记失败！");
			}
		} catch (Exception e) {
			logger.error("添加日记异常", e);
			return ResponseMsg.fail("添加日记异常！");
		}

	}

	/**
	 * 修改日记
	 * 
	 * @param Diary
	 *            日记信息
	 * @return 修改日记情况
	 */
	@ResponseBody
	@RequestMapping("modifyDiary")
	public ResponseMsg modifyDiary(Diary diary) {
		int row = diaryService.modifyDiary(diary);
		if (row > 0) {
			return ResponseMsg.success("修改日记成功！");
		} else {
			return ResponseMsg.fail("修改日记失败！");
		}
	}

	/**
	 * 删除日记
	 * 
	 * @param id
	 *            日记ID
	 * @return 删除日记情况
	 */
	@ResponseBody
	@RequestMapping("removeDiary")
	public ResponseMsg removeDiaryById(String id) {
		int row = diaryService.removeDiaryById(id);
		if (row > 0) {
			return ResponseMsg.success("删除日记成功！");
		} else {
			return ResponseMsg.fail("删除日记失败！");
		}
	}

}
