package com.zhijian.ebook.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.DiaryMapper;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryExample;
import com.zhijian.ebook.service.DiaryService;


@Service
public class DiaryServiceImpl implements DiaryService {
	
	@Autowired
	private DiaryMapper diaryMapper;

	@Override
	public EasyuiPagination<Diary> findDiaryPagination(Diary diary, Integer page, Integer rows) {
		DiaryExample diaryExample = new DiaryExample();
		DiaryExample.Criteria criteria = diaryExample.createCriteria();
		if (StringUtils.isNotBlank(diary.getTitle())) {
			criteria.andTitleLike("%" + diary.getTitle() + "%");
		}
		diaryExample.setOrderByClause("username desc, create_time desc");
		List<Diary> list = diaryMapper.findPaginationList(new Page(page, rows), diaryExample);

		return new EasyuiPagination<Diary>(list.size(), list);
	}

	@Override
	public Diary findDiaryById(String diaryid) {
		return diaryMapper.selectByPrimaryKey(diaryid);
	}

	@Override
	public int addDiary(Diary diary) {
		return diaryMapper.insert(diary);
	}

	@Override
	public int modifyDiary(Diary diary) {
		return diaryMapper.updateByPrimaryKeySelective(diary);
	}

	@Override
	public int removeDiaryById(String id) {
		return diaryMapper.deleteByPrimaryKey(id);
	}

}
