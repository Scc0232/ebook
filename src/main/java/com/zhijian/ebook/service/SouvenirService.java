package com.zhijian.ebook.service;

import java.util.List;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryComment;
import com.zhijian.ebook.entity.DiaryLike;
import com.zhijian.ebook.entity.Souvenir;

public interface SouvenirService {

	List<Souvenir> selectSouvenirAll();

	int addNewDiary(Diary diary);

	EasyuiPagination<Diary> selectDiaryAll(Integer page, Integer rows);

	int addDiaryComment(DiaryComment diaryComment);

	int addDiaryLike(DiaryLike diaryLike);

	int removeDiaryLike(String diaryId);

}
