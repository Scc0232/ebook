package com.zhijian.ebook.base.service;

import com.zhijian.ebook.base.entity.Version;

/**
 * 
 * 系统各版本服务层
 * 
 * @author Administrator
 *
 */
public interface VersionService {
	
	/**
	 * 查询版本
	 * @param version 实体条件
	 * @return 版本列表
	 */
//	public List<Version> findVersions(Version version);
	
	
	/**
     * 检查更新
     *
     * @param currentVerion 当前版本
     * @param platform      平台(IOS, ANDROID)
     * @return 如果有新版本, 返回最新版本的Version对象, 如果没有返回null
     * @throws Exception
     */
    Version doCheckUpgrade(Integer currentVerion, String platform) throws Exception;
	
}
