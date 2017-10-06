package com.zhijian.ebook.service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Diary;

public interface DiaryService {

	EasyuiPagination<Diary> findDiaryPagination(Diary diary, Integer page, Integer rows);

	Diary findDiaryById(String diaryid);

	int addDiary(Diary diary);

	int modifyDiary(Diary diary);

	int removeDiaryById(String id);

}
