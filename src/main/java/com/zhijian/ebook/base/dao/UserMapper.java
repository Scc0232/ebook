package com.zhijian.ebook.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.entity.UserExample;
import com.zhijian.ebook.bean.Page;



public interface UserMapper {
	int countByExample(UserExample example);

	int deleteByExample(UserExample example);

	int deleteByPrimaryKey(String id);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectByExample(UserExample example);

	User selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

	int updateByExample(@Param("record") User record, @Param("example") UserExample example);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User findUserByUsername(String username);
	
	User findUserByPhone(String username);

	List<User> findPaginationList(@Param("page") Page page, @Param("example") UserExample example);

	List<User> unSelectUserByRoleId(@Param("page") Page page, @Param("roleId") String roleId, @Param("user") User user);

	int countByUnRoleId(@Param("roleId") String roleId, @Param("user") User user);

	List<User> findUserByRoleId(@Param("page") Page page, @Param("roleId") String roleId, @Param("user") User user);

	int countByRoleId(@Param("roleId") String roleId, @Param("user") User user);

	int findCountByReferralCode(String referralCode);

	List<User> selectByIsValid(Boolean isValid);

	User selectUserByExample(@Param("example") UserExample example);
	
	List<User> findAllUser();

	List<User> findCountLabelCircle(@Param("circleId")String circleId, @Param("userId")String userId, @Param("page")Page page);

	User selectBaseInfoByPrimaryKey(@Param("id")String id);

	int countByUnCircleId(@Param("circleId") String circleId, @Param("user") User user);//
	
	List<User> unSelectUserByCircleId(@Param("page") Page page, @Param("circleId") String circleId, @Param("user") User user);//
	
	int countByCircleId(@Param("circleId") String circleId, @Param("user") User user);//
	
	List<User> findUserByCircleId(@Param("page") Page page, @Param("circleId") String circleId, @Param("user") User user);//

	int countByGroupId(@Param("groupId") String groupId, @Param("user") User user);//

	List<User> findUserByGroupId(@Param("page") Page page, @Param("groupId") String groupId, @Param("user") User user);//
	
	int countByUnGroupId(@Param("groupId") String groupId, @Param("user") User user);//
	
	List<User> unSelectUserByGroupId(@Param("page") Page page, @Param("groupId") String groupId, @Param("user") User user);//
	
	int countByFriendId(@Param("userId") String userId, @Param("user") User user);//

	List<User> findUserByFriendId(@Param("page") Page page, @Param("userId") String userId, @Param("user") User user);//
	
	int countByUnFriendId(@Param("userId") String userId, @Param("user") User user);//
	
	List<User> unSelectUserByFriendId(@Param("page") Page page, @Param("userId") String userId, @Param("user") User user);//

	List<User> findInvitationList(@Param("page")Page page);

	User selectBaseUserByPrimaryKey(String recommendedUserId);
}