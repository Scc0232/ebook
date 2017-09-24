package com.zhijian.ebook.service;

import java.util.List;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryComment;
import com.zhijian.ebook.entity.DiaryLike;
import com.zhijian.ebook.entity.Souvenir;

public interface SouvenirService {

	List<Souvenir> selectSouvenirAll(int type);

	int addNewDiary(Diary diary);

	EasyuiPagination<Diary> selectDiaryAll(Integer page, Integer rows) throws Exception;

	int addDiaryComment(DiaryComment diaryComment) throws Exception;

	int addDiaryLike(DiaryLike diaryLike) throws Exception;

	int removeDiaryLike(String diaryId) throws Exception;

	int addBrowsed(List<Diary> list, String userid) throws Exception;

	EasyuiPagination<Diary> selectMyDiary(Integer page, Integer rows, String userid) throws Exception;

	int findIsLike(String diaryId) throws Exception;

	EasyuiPagination<DiaryComment> findDiaryComments(String diaryid, Integer page, Integer rows) throws Exception;

	Diary findDiaryDetail(String diaryId) throws Exception;


}
