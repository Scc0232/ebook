package com.zhijian.ebook.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.service.DictService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;

/**
 * 
 * 字典控制器
 * 
 * @author lidongwei on 15/1/22
 *
 */
@Controller
@RequestMapping("manager/dict")
public class DictController {

	@Autowired
	private DictService dictService;

	/**
	 * 获取字典主页视图
	 */
	@RequestMapping("index")
	public String index() {
		return "manager/dict/dictList";
	}

	/**
	 * 获取字典分页数据
	 * 
	 * @param dict
	 *            字典实体
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findDictPagination")
	public EasyuiPagination<Dict> findDictPagination(Dict dict, int page, int rows) {
		return dictService.findDictPagination(dict, page, rows);
	}

	/**
	 * 获取添加字典视图
	 */
	@RequestMapping("addDictView")
	public String addDictView() {
		return "manager/dict/addDict";
	}

	/**
	 * 获取修改字典视图
	 */
	@RequestMapping("modifyDictView")
	public String modifyDictView(ModelMap map, String dictId) {
		Dict dict = dictService.findDictById(dictId);
		map.put("dict", dict);
		return "manager/dict/modifyDict";
	}

	/**
	 * 添加字典
	 * 
	 * @param dict
	 *            字典实体
	 * @return ResponseMsg 结果信息
	 */
	@ResponseBody
	@RequestMapping("addDict")
	public ResponseMsg addDict(Dict dict) {
		int row = dictService.addDict(dict);
		if (row > 0) {
			return ResponseMsg.success("添加字典成功！");
		} else {
			return ResponseMsg.fail("添加字典失败！");
		}
	}
	
	@ResponseBody
	@RequestMapping("findDictType")
	public List<Dict> findDictType(String dictType) {
		List<Dict> list = dictService.findDictByType(dictType);
		return list;
	}

	/**
	 * 修改字典
	 * 
	 * @param dict
	 *            字典实体
	 * @return ResponseMsg 结果信息
	 */
	@ResponseBody
	@RequestMapping("modifyDict")
	public ResponseMsg modifyDict(Dict dict) {
		int row = dictService.modifyDict(dict);
		if (row > 0) {
			return ResponseMsg.success("修改字典成功！");
		} else {
			return ResponseMsg.fail("修改字典失败！");
		}
	}

	/**
	 * 删除字典
	 * 
	 * @param id
	 *            字典主键id
	 * @return ResponseMsg 结果信息
	 */
	@ResponseBody
	@RequestMapping("dictRemove")
	public ResponseMsg dictRemove(String id) {
		int row = dictService.removeDictById(id);
		if (row > 0) {
			return ResponseMsg.success("删除字典成功！");
		} else {
			return ResponseMsg.fail("删除字典失败！");
		}
	}

	
}
