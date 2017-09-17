package com.zhijian.ebook.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhijian.ebook.base.service.UserService;
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
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.SouvenirService;
import com.zhijian.ebook.util.StringConsts;

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
	
	@Autowired
	private UserService userService;

	@Override
	public List<Souvenir> selectSouvenirAll() {

		List<Souvenir> list = null;
		SouvenirExample example = new SouvenirExample();
		SouvenirExample.Criteria criteria = example.createCriteria();
		criteria.andIsValidEqualTo(true);
		example.setOrderByClause("name asc");
		
		list = souvenirMapper.selectByExample(example);
		return list;
	}

	@Override
	public int addNewDiary(Diary diary) {
		if (diary.getVisibility()==null) {
			diary.setVisibility(true);
		}
		diary.setBrowsedTimes(0);
		diary.setCommentTimes(0);
		diary.setLikedTimes(0);
		diary.setIsValid(true);
		return diaryMapper.insert(diary);
	}

	@Override
	public EasyuiPagination<Diary> selectDiaryAll(Integer page, Integer rows) throws Exception{
		List<Diary> list = null;
		DiaryExample example = new DiaryExample();
		DiaryExample.Criteria criteria = example.createCriteria();
		criteria.andVisibilityEqualTo(Boolean.TRUE);
		Page pag = null;
		if (page == null || rows == null || page < 1 || rows < 0) {
			pag = new Page(1, 10);
		}
		list = diaryMapper.selectDiaryByExample(example, pag);
		addBrowsed(list,userService.findUserByUsername(UserContextHelper.getUsername()).getId());
		
		
		
		return new EasyuiPagination<>(list.size(), list);
	}

	@Override
	public int addDiaryComment(DiaryComment diaryComment) throws Exception{
		Diary diary = diaryMapper.selectByPrimaryKey(diaryComment.getDiaryId());
		diary.setCommentTimes(diary.getCommentTimes()+1);
		diaryMapper.updateByPrimaryKeySelective(diary);
		
		diaryComment.setUserid(userService.findUserByUsername(UserContextHelper.getUsername()).getId());
		diaryComment.setCreateTime(new Date());
		diaryComment.setIsValid(true);
		return diaryCommentMapper.insert(diaryComment);
	}

	@Override
	public int addDiaryLike(DiaryLike diaryLike) throws Exception{
		String userId = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		diaryLike.setUserid(userId);
		return diaryLikeMapper.insert(diaryLike);
	}

	@Override
	public int removeDiaryLike(String diaryId) throws Exception{
		// TODO
		String userId = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		DiaryLikeExample example = new DiaryLikeExample();
		DiaryLikeExample.Criteria criteria = example.createCriteria();
		criteria.andDiaryIdEqualTo(diaryId);
		criteria.andUseridEqualTo(userId);
		return diaryLikeMapper.deleteByExample(example);
	}

	@Override
	public int addBrowsed(List<Diary> list, String userid) {
		for(Diary diary: list) {
			if (!diary.getUserid().equals(userid)) {
				diary.setBrowsedTimes(diary.getBrowsedTimes()+1);
			}
			diaryMapper.updateByPrimaryKeySelective(diary);
		}
		
		return 0;
	}

	@Override
	public EasyuiPagination<Diary> selectMyDiary(Integer page, Integer rows, String userid) {
		List<Diary> list = null;
		DiaryExample example = new DiaryExample();
		DiaryExample.Criteria criteria = example.createCriteria();
		criteria.andVisibilityEqualTo(Boolean.TRUE);
		criteria.andUseridEqualTo(userid);
		Page pag = null;
		if (page == null || rows == null || page < 1 || rows < 0) {
			pag = new Page(1, 10);
		}
		list = diaryMapper.selectDiaryByExample(example, pag);
		return new EasyuiPagination<>(list.size(), list);
	}

	@Override
	public int findIsLike(String diaryId) throws Exception{
		String userid = userService.findUserByUsername(UserContextHelper.getUsername()).getId();
		DiaryLikeExample likeExample = new DiaryLikeExample();
		DiaryLikeExample.Criteria criteria = likeExample.createCriteria();
		criteria.andDiaryIdEqualTo(diaryId);
		criteria.andUseridEqualTo(userid);
		return diaryLikeMapper.countByExample(likeExample);
	}

}
