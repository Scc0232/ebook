package com.zhijian.ebook.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.dao.DiaryCommentMapper;
import com.zhijian.ebook.dao.DiaryLikeMapper;
import com.zhijian.ebook.dao.DiaryMapper;
import com.zhijian.ebook.dao.SouvenirMapper;
import com.zhijian.ebook.entity.Diary;
import com.zhijian.ebook.entity.DiaryComment;
import com.zhijian.ebook.entity.DiaryExample;
import com.zhijian.ebook.entity.DiaryLike;
import com.zhijian.ebook.entity.DiaryLikeExample;
import com.zhijian.ebook.entity.Souvenir;
import com.zhijian.ebook.entity.SouvenirExample;
import com.zhijian.ebook.service.SouvenirService;

@Service
public class SouvenirServiceImpl implements SouvenirService {

	@Autowired
	private SouvenirMapper souvenirMapper;

	@Autowired
	private DiaryMapper diaryMapper;

	@Autowired
	private DiaryCommentMapper diaryCommentMapper;

	@Autowired
	private DiaryLikeMapper diaryLikeMapper;

	@Override
	public List<Souvenir> selectSouvenirAll() {

		List<Souvenir> list = null;
		SouvenirExample example = new SouvenirExample();
		example.setOrderByClause("name asc");
		list = souvenirMapper.selectByExample(example);
		return list;
	}

	@Override
	public int addNewDiary(Diary diary) {

		return diaryMapper.insert(diary);
	}

	@Override
	public EasyuiPagination<Diary> selectDiaryAll(Integer page, Integer rows) {
		List<Diary> list = null;
		DiaryExample example = new DiaryExample();
		DiaryExample.Criteria criteria = example.createCriteria();
		criteria.andVisibilityEqualTo(Boolean.TRUE);
		Page pag = null;
		if (page != null && rows != null && page > 0 && rows > 0) {
			pag = new Page(page, rows);
		}
		list = diaryMapper.selectDiaryByExample(example, pag);
		return new EasyuiPagination<>(list.size(), list);
	}

	@Override
	public int addDiaryComment(DiaryComment diaryComment) {
		// TODO
		String userId = null;
		diaryComment.setUserid(userId);
		return diaryCommentMapper.insert(diaryComment);
	}

	@Override
	public int addDiaryLike(DiaryLike diaryLike) {
		// TODO
		String userId = null;
		diaryLike.setUserid(userId);
		return diaryLikeMapper.insert(diaryLike);
	}

	@Override
	public int removeDiaryLike(String diaryId) {
		// TODO
		String userId = null;
		DiaryLikeExample example = new DiaryLikeExample();
		DiaryLikeExample.Criteria criteria = example.createCriteria();
		criteria.andDiaryIdEqualTo(diaryId);
		criteria.andUseridEqualTo(userId);
		return diaryLikeMapper.deleteByExample(example);
	}

}
