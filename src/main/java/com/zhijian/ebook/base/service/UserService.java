package com.zhijian.ebook.base.service;

import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.SysUserDetails;
public interface UserService {

	EasyuiPagination<User> findUserPagination(User user, Integer page, Integer rows);

	User findUserById(String userId);
	
	/**
	 * 增加用户
	 * @param user 用户实体
	 * @return 
	 */
	int addUser(User user);
	
	int addUser(User user,String userRoleId);
	
	/**
	 * 修改用户
	 * @param user 用户实体
	 * @return
	 */
	int modifyUser(User user);
	
	/**
	 * 修改用户
	 * @param user 用户实体
	 * @return
	 */
	int modifyUserByUserId(User user);
	/**
	 * 根据用户ID删除一条用户信息
	 * @param id 用户ID
	 * @return
	 */
	int removeUserById(String id);
	
	/**
	 * 根据用户姓名查询用户信息
	 * @param username 用户姓名
	 * @return
	 * @throws Exception
	 */
	User findUserByUsername(String username) throws Exception;
	
	SysUserDetails findSysUserDetails(String username);
	
	boolean isSuperUser(String username);

	boolean modifyUserPassword(String username, String password, String newPassword);
	
	int modifyUserByUsername(User user) throws Exception;
	
	int modifyUserIcon(String username, String filePath) throws Exception;

	/**获得这个角色已选择的用户
	 * @param user 
	 * @param roleId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	EasyuiPagination<User> getThisRoleSelected(User user, String roleId,
			Integer page, Integer rows) throws Exception;

	/**获得这个角色未选择的用户
	 * @param user
	 * @param roleId
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	EasyuiPagination<User> getThisRoleUnselected(User user, String roleId,
			Integer page, Integer rows) throws Exception;

	/**
	 * 忘记密码
	 * @param username 用户名
	 * @param newPassword 新密码
	 */
	void doResetPassword(String username, String newPassword);
	
	/**
	 * 根据推荐码统计是否存在相应用户
	 * @param referralCode
	 * @return
	 * @throws Exception
	 */
	public int findCountByReferralCode(String referralCode)throws Exception;
}
