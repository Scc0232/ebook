package com.zhijian.ebook.base.service.impl;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.dao.VersionMapper;
import com.zhijian.ebook.base.entity.Version;
import com.zhijian.ebook.base.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionMapper versionMapper;
	

	@Override
    public Version doCheckUpgrade(Integer currentVerion, String platform) throws Exception {
        Version newestVersion =  versionMapper.selectByPlatform(platform);
        if (newestVersion == null) {
            return null;
        }
        BigDecimal oldVersion=newestVersion.getVersion();
        int newVersion=oldVersion.intValue(); 
        // 最近版本号大于当前版本号
        if (newVersion > currentVerion) {
            return newestVersion;
        }

        return null;
    }

}
