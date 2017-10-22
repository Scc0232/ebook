package com.zhijian.ebook.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.service.DictService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.util.FileUpLoadUtils;
import com.zhijian.ebook.util.StringConsts;

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
	public ResponseMsg addDict(Dict dict, HttpServletRequest request) {
		int row = 0;
		String icon = uploadImg(request);
		if (icon != null) {
			dict.setIcon(icon);
		} else {
			dict.setIcon("img/test.jpg");
		}
		row = dictService.addDict(dict);
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
	public ResponseMsg modifyDict(Dict dict, HttpServletRequest request) {
		int row = 0;
		String icon = uploadImg(request);
		if (icon != null) {
			dict.setIcon(icon);
		}
		row = dictService.modifyDict(dict);
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

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @return
	 */
	public String uploadImg(HttpServletRequest request) {
		String uploadFilePath = null;
		try {
			List<String> imgSufferList = new ArrayList<String>();

			imgSufferList.add("jpg");
			imgSufferList.add("png");
			imgSufferList.add("gif");
			imgSufferList.add("bmp");
			imgSufferList.add("jpeg");

			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				Long newFileName = null;

				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartFile headImg = multiRequest.getFile("icons");
				String suffer = null;
				if ((headImg != null) && (headImg.getSize() > 0L)) {
					suffer = FileUpLoadUtils.getFileSuffix(headImg);
					if (!imgSufferList.contains(suffer.toLowerCase())) {
						throw new Exception();
					}
					newFileName = Long.valueOf(StringConsts.getUUID16Id());
					uploadFilePath = FileUpLoadUtils.writeFile(headImg, "/var/ebook/image/" + StringConsts.TO_PATH_IMG,
							newFileName.toString(), false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (org.springframework.util.StringUtils.isEmpty(uploadFilePath)) {
			return null;
		}
		return uploadFilePath;
	}

}
