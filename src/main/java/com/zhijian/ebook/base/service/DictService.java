package com.zhijian.ebook.base.service;

import java.util.List;

import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.bean.EasyuiPagination;

/**
 * 通用配置 服务层
 * 
 * @author lidongwei on 15/1/23
 *
 */
public interface DictService {

	/**
	 * 添加字典
	 * 
	 * @param dict
	 *            字典实体
	 * @return
	 */

	int addDict(Dict dict);

	/**
	 * 修改字典
	 * 
	 * @param dict
	 *            字典实体
	 * @return
	 */
	int modifyDict(Dict dict);

	/**
	 * 删除字典
	 * 
	 * @param id
	 *            字典主键id
	 * @return
	 */
	int removeDictById(String id);

	/**
	 * 分页查询字典
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            行数
	 * @return 分页数据
	 * @return
	 */
	EasyuiPagination<Dict> findDictPagination(Dict dict, int page, int rows);

	/**
	 * 根据id查询字典详情
	 * 
	 * @param id
	 *            字典主键id
	 * @return
	 */
	Dict findDictById(String id);
	
	Dict findDictByCode(String code);
	
	List<Dict> findDictByType(String dictType);

	/**
	 * 根据key获取类型列表
	 * 
	 * @param key
	 *            主键
	 * @return 类型列表
	 */
	List<Dict> findTypesByKey(String key);

}
