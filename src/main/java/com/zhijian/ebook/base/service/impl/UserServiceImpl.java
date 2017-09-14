package com.zhijian.ebook.base.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

//import com.gener.common.util.ObjectUtil;
import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.dao.UserRoleRelationMapper;
import com.zhijian.ebook.base.entity.Authority;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.entity.UserExample;
import com.zhijian.ebook.base.entity.UserRoleRelation;
import com.zhijian.ebook.base.service.AuthorityService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.bean.SysUserDetails;
import com.zhijian.ebook.enums.SuperUser;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.util.PasswordEncoderUtil;
import com.zhijian.ebook.util.StringConsts;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private UserRoleRelationMapper userRoleRelationMapper;
	
	 /**
     * 邀请码长度
     */
    private static final int REFERRAL_CODE_LENGTH = 18;

	@Override
	public SysUserDetails findSysUserDetails(String username) {
		User user = userMapper.findUserByUsername(username);
		if (user == null) {// 没有这个用户
			return null;
		}
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		List<Authority> l = authorityService.findAuthorityByUserId(user.getId());
		for (Authority a : l) {// 创建用户权限列表
			authorities.add(new SimpleGrantedAuthority(a.getName()));
		}
		return new SysUserDetails(user.getUsername(), user.getPassword(),authorities);
	}

	@Override
	public boolean isSuperUser(String username) {
		SuperUser[] enums = SuperUser.values();
        for (SuperUser superUser : enums) {
            if (username.equals(superUser.getUsername())) {
                return true;
            }
        }
        return false;
	}

	@Override
	public EasyuiPagination<User> findUserPagination(User user, Integer page,
			Integer rows) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(user.getUsername())){
			String userName = "%"+user.getUsername()+"%";
			criteria.andUsernameLike(userName);
		}
		if(StringUtils.isNotEmpty(user.getName())){
			String name = "%"+user.getName()+"%";
			criteria.andNameLike(name);
		}
		example.setOrderByClause(" ACCREDIT DESC,CREATE_TIME DESC");
		Integer total = userMapper.countByExample(example);
		List<User> list = userMapper.findPaginationList(new Page(page, rows),example);
		String loginUserName = UserContextHelper.getName();
		if (!"admin".equalsIgnoreCase(loginUserName)) {
			for (int i = 0; i < list.size(); i++) {
				if ("admin".equalsIgnoreCase(list.get(i).getName())) {
					list.remove(i);
					total--;
				}
			}
		}
		return new EasyuiPagination<User>(total, list);
	}

	@Override
	public User findUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int addUser(User user) {
		PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(user.getUsername(), StringConsts.PASSWORD_ENCRYPTION_WAY);
		String password = user.getPassword();
		if(StringUtils.isEmpty(password)){
			password = user.getUsername();
		}
        String md5 = md5Mncoder.encode(password);
        user.setPassword(md5);
        user.setIsValid(true);
        String ModifyPersonId = UserContextHelper.getUsername();
    	User u = userMapper.findUserByUsername(ModifyPersonId);
    	ModifyPersonId = u.getId(); 
    	user.setModifyPersonId(ModifyPersonId);
    	String createPersonId = u.getId();
    	user.setCreatePersonId(createPersonId);
    	Date date = new Date();
    	user.setCreateTime(date);
    	user.setAccredit(0);
        int flag = 0;
        try {
        	flag = userMapper.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
	
	@Override
	public int modifyUser(User user) {
		 int flag = 0;
	        try {  
	        	String ModifyPersonId = UserContextHelper.getUsername();
	    		User u = userMapper.findUserByUsername(ModifyPersonId);
	    		ModifyPersonId = u.getId(); 
	    		user.setModifyPersonId(ModifyPersonId);
	    		Date date = new Date();
	    		user.setModifyTime(date);
	        	flag = userMapper.updateByPrimaryKey(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
//		return userMapper.updateByPrimaryKey(user);
	}

	@Override
	public int removeUserById(String id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User findUserByUsername(String username) throws Exception {
		return userMapper.findUserByUsername(username);
	}

	@Override
	public boolean modifyUserPassword(String username, String password, String newPassword) {
		boolean flag = false;
        User user = userMapper.findUserByUsername(username);
        PasswordEncoderUtil passwordEncoderUtil = new PasswordEncoderUtil(
                username, StringConsts.PASSWORD_ENCRYPTION_WAY);
        if (null != user) {
            String encPass = user.getPassword();
            flag = passwordEncoderUtil.isPasswordValid(encPass, password);
            if (flag) {
                newPassword = passwordEncoderUtil.encode(newPassword);
                user.setPassword(newPassword);
                userMapper.updateByPrimaryKeySelective(user);
                flag = true;
            }
        }
        return flag;
	}
	
	@Override
	public int modifyUserByUsername(User user) throws Exception {
		User baseUser = new User();
		baseUser.setName(user.getName());
		baseUser.setPhone(user.getPhone());
		baseUser.setEmail(user.getEmail());
//		baseUser.setQq(user.getQq());
//		baseUser.setWechat(user.getWechat());
//		baseUser.setToken(user.getToken());
//		baseUser.setReferralCode(user.getReferralCode());
//		baseUser.setMyReferralCode(user.getMyReferralCode());
//		baseUser.setCompanyName(user.getCompanyName());
//		baseUser.setCompanyAddr(user.getCompanyAddr());
//		baseUser.setCompanyPhone(user.getCompanyPhone());
//		baseUser.setJob(user.getJob());
		baseUser.setModifyTime(new Date());
		baseUser.setRemark(user.getRemark());		
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		return userMapper.updateByExampleSelective(baseUser, example);
	}

	@Override
	public int modifyUserIcon(String username, String filePath) throws Exception {
		User user = new User();
		user.setIcon(filePath);
		user.setModifyTime(new Date());
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		return userMapper.updateByExampleSelective(user, example);
	}
	public EasyuiPagination<User> getThisRoleSelected(User user, String roleId,
			Integer page, Integer rows) throws Exception {
		Integer total = userMapper.countByRoleId(roleId,user);
		List<User> list = userMapper.findUserByRoleId(new Page(page, rows), roleId,user);
		return new EasyuiPagination<User>(total,list);
	}

	@Override
	public EasyuiPagination<User> getThisRoleUnselected(User user,
			String roleId, Integer page, Integer rows) throws Exception {
		
		Integer total = userMapper.countByUnRoleId(roleId,user);
		List<User> list = userMapper.unSelectUserByRoleId(new Page(page, rows), roleId,user);
		return new EasyuiPagination<User>(total,list);
	}

	@Override
	public int addUser(User user, String userRoleId) {
		//TODO 生成推荐码
//		user.setMyReferralCode(generateReferralCode());
		PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(user.getUsername(), StringConsts.PASSWORD_ENCRYPTION_WAY);
		String password = user.getPassword();
		if(StringUtils.isEmpty(password)){
			password = user.getUsername();
		}else{
			password = user.getPassword();
		}
        String md5 = md5Mncoder.encode(password);
        user.setPassword(md5);
        user.setIsValid(true);
        user.setAccredit(0);
        int flag = userMapper.insert(user);
        if(flag == 1){
        	User baseUser = userMapper.findUserByUsername(user.getUsername());
        	UserRoleRelation urr = new UserRoleRelation();
        	urr.setUserId(baseUser.getId());
        	urr.setRoleId(userRoleId);
        	userRoleRelationMapper.insert(urr);
        }
		return flag;
	}

	@Override
	public void doResetPassword(String username, String newPassword) {
		User user = userMapper.findUserByUsername(username);
        PasswordEncoderUtil passwordEncoderUtil = new PasswordEncoderUtil(
                username, StringConsts.PASSWORD_ENCRYPTION_WAY);
        if (null != user) {
            newPassword = passwordEncoderUtil.encode(newPassword);
            user.setPassword(newPassword);
            userMapper.updateByPrimaryKey(user);
        }
	}
	
	
	
	 /**
     * 生成我的邀请码
     *
     * @return
     */
    private String generateReferralCode() {
        return RandomStringUtils.randomAlphanumeric(REFERRAL_CODE_LENGTH)
                .toUpperCase();
    }

	@Override
	public int findCountByReferralCode(String referralCode) throws Exception {
		return userMapper.findCountByReferralCode(referralCode);
	}

	@Override
	public int modifyUserByUserId(User user) {
		 int flag = 0;
	        try {  
	    		Date date = new Date();
	    		user.setModifyTime(date);
	        	flag = userMapper.updateByPrimaryKey(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
	
	}
}
