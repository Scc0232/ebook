package com.zhijian.ebook.base.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.service.UserRoleRelationService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.bean.EasyuiPagination;
import com.zhijian.ebook.bean.ResponseMsg;
import com.zhijian.ebook.util.PasswordEncoderUtil;
import com.zhijian.ebook.util.ReadPropertiesFileUtils;
import com.zhijian.ebook.util.StringConsts;


/**
 * 
 * 用户控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("manager/user")
public class UserController {
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleRelationService userRoleService;
	
	private static String DEFAULT_ROLE_ID = "599a895311b811e6be1feca86ba4ba05";
	
	/**
	 * 用户列表界面
	 * 
	 * @return 用户列表路径
	 */
	@RequestMapping("index")
	public String index(){
		return "manager/user/userList";
	}
	
	/**
	 * 获取用户分页数据
	 * 
	 * @param user 用户实体
	 * @param page 页数
	 * @param rows 行数
	 * @return 分页数据
	 */
	@ResponseBody
	@RequestMapping("findUserPagination")
	public EasyuiPagination<User> findUserPagination(User user,Integer page, Integer rows) {
		
		return userService.findUserPagination(user, page, rows);
	}
	
	/**
	 * 添加用户页面
	 * 
	 * @return 添加用户路径
	 */
	@RequestMapping("addUserView")
	public String addUserView(){
	    return "manager/user/addUser";
	}
	
	/**
	 * 修改用户界面
	 * 
	 * @param map 用户信息
	 * @param userId 用户ID
	 * @return 修改用户路径
	 */
	@RequestMapping("modifyUserView")
    public String modifyUserView(ModelMap map, String userId){
	    User userMap = userService.findUserById(userId);
//	    if (userMap.getBindMobileCount() == null) {
//	    	userMap.setBindMobileCount(0);
//		}
	    map.put("userMap", userMap);
        return "manager/user/modifyUser";
    }
	
	/**
     * 重置密码界面
     * 
     * @param map 用户信息
     * @param userId 用户ID
     * @return 重置密码路径
     */
    @RequestMapping("modifyPasswordView")
    public String modifyPasswordView(ModelMap map,String userId){
        User userMap = userService.findUserById(userId);
        map.put("userMap", userMap);
        return "manager/user/modifyPassword";
    }
    
    /**
     * 重置密码
     * 
     * @param map 用户信息
     * @param userId 用户ID
     * @param password 用户密码
     * @return 重置密码路径
     */
    @ResponseBody
    @RequestMapping("modifyPassword")
    public ResponseMsg modifyPassword(ModelMap map,User user){
        String id = user.getId();
        User userMap = userService.findUserById(id);
        PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(userMap.getUsername(), StringConsts.PASSWORD_ENCRYPTION_WAY);
        String password = user.getPassword();
        String md5 = md5Mncoder.encode(password);       
        map.put("userMap", userMap);
        userMap.setPassword(md5);
        int row = userService.modifyUser(userMap);
        if (row > 0) {
            return ResponseMsg.success("重置成功！");
        } else {
            return ResponseMsg.fail("重置失败！");
        }
    }   
	/**
	 * 添加用户
	 * 
	 * @param user 用户信息
	 * @return 添加用户情况
	 */
	@ResponseBody
    @RequestMapping("addUser")
	public ResponseMsg addUser(User user) {
		try {
//			if (user.getBindMobileCount() == null || user.getBindMobileCount() == 0) {
//				String countStr = ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
//				//设置手机可更换绑定次数(配置文件配置，如特殊需要修改，用户需联系管理员从管理平台修改)
//				user.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
//			}
		    int row = userService.addUser(user);
		    User u = userService.findUserByUsername(user.getUsername());
		    JSONArray userIds = new JSONArray();
		    userIds.add(u.getId());
		    userRoleService.addRelation(DEFAULT_ROLE_ID, userIds);
		    if (row > 0) {
	            return ResponseMsg.success("添加用户成功！");
	        } else {
	            return ResponseMsg.fail("添加用户失败！");
	        }
		} catch (Exception e) {
			logger.error("添加用户异常",e);
			return ResponseMsg.fail("添加用户异常！");
		}
		
	}
	
	/**
	 * 修改用户
	 * 
	 * @param user 用户信息
	 * @return 修改用户情况
	 */
	@ResponseBody
    @RequestMapping("modifyUser")
    public ResponseMsg modifyUser(ModelMap map,User user) {
        String id = user.getId();
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
//        Long qq = user.getQq();
//        String companyName = user.getCompanyName();
//        String companyPhone = user.getCompanyPhone();
//        String companyAddr = user.getCompanyAddr();
//        String myReferralCode = user.getMyReferralCode();
//        Integer bindMobileCount = user.getBindMobileCount();
        User userMap = userService.findUserById(id);
        userMap.setUsername(username);
        userMap.setName(name);
        userMap.setPhone(phone);
        userMap.setEmail(email);
//        userMap.setQq(qq);
//        userMap.setCompanyName(companyName);
//        userMap.setCompanyPhone(companyPhone);
//        userMap.setCompanyAddr(companyAddr);
//        userMap.setMyReferralCode(myReferralCode);
//        userMap.setBindMobileCount(bindMobileCount);
        map.put("userMap", userMap);
        int row = userService.modifyUser(userMap);
        if (row > 0) {
            return ResponseMsg.success("修改用户成功！");
        } else {
            return ResponseMsg.fail("修改用户失败！");
        }
    }
	
	/**
	 * 删除用户
	 * 
	 * @param id 用户ID
	 * @return 删除用户情况
	 */
	@ResponseBody
    @RequestMapping("removeUser")
    public ResponseMsg removeUserById(String id) {
        int row = userService.removeUserById(id);
        if (row > 0) {
            return ResponseMsg.success("删除用户成功！");
        } else {
            return ResponseMsg.fail("删除用户失败！");
        }
    }
	
	/**
	 * 已授权
	 */
	private static final int ACCREDIT_OK = 1;
	
	/**
	 * 未授权
	 */
	private static final int ACCREDIT_NO = 0;
	
	/**
	 * 授权登录权限/取消登录权限授权
	 * @param id 账号id
	 * @param accreditStatus  授权状态  1：授权  0：取消授权
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="accredit",method = RequestMethod.POST)
	public ResponseMsg accredit(String id,int accreditStatus) {
		try {
			logger.info((accreditStatus == 1 ? "授权登录权限" : "取消登录权限授权")+"开始... id={}",id);
			User user = userService.findUserById(id);
			
			if (user == null) {
				return ResponseMsg.fail("用户不存在，操作失败！");
			}
			//APP登录用户校验
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");  
			  
			Matcher m = p.matcher(user.getUsername());  
		  
			boolean isflag = m.matches();
			
			if (!isflag && accreditStatus == 1) {
				return ResponseMsg.fail("用户名不合法，授权失败！");
			}
			
			Integer accredit = user.getAccredit() == null ? 0 : user.getAccredit();
			if (accredit == ACCREDIT_OK && accreditStatus == ACCREDIT_OK) {
				return ResponseMsg.fail("该用户已获登录授权，无需再重复授权！");
			}
			if (accredit == ACCREDIT_NO && accreditStatus == ACCREDIT_NO) {
				return ResponseMsg.fail("该用户还没有登录授权，无法取消授权！");
			}
			user.setAccredit(accreditStatus);
			userService.modifyUser(user);
			logger.info((accreditStatus == 1 ? "授权登录权限" : "取消登录权限授权") + "操作成功！");
			return ResponseMsg.success((accreditStatus == 1 ? "授权登录权限" : "取消登录权限授权") + "操作成功！");
		} catch (Exception e) {
			logger.error((accreditStatus == 1 ? "授权登录权限" : "取消登录权限授权") + "操作异常！",e);
			return ResponseMsg.fail((accreditStatus == 1 ? "授权登录权限" : "取消登录权限授权") + "操作异常！");
		}
	}

	/**获得这个角色已选择的用户
	 * @param user 用户实体
	 * @param roleId 角色id
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getThisRoleSelected")
	public EasyuiPagination<User> getThisRoleSelected(User user, String roleId,
			Integer page, Integer rows){
		
				try {
					return userService.getThisRoleSelected(user, roleId, page, rows);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
	}
	
	/**获得这个角色未选择的用户
	 * @param user 用户实体
	 * @param roleId 角色id
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getThisRoleUnselected")
	public EasyuiPagination<User> getThisRoleUnselected(User user,
			String roleId, Integer page, Integer rows){
				try {
					return userService.getThisRoleUnselected(user, roleId, page, rows);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
	}
}
