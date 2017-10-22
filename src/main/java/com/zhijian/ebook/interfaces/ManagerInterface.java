package com.zhijian.ebook.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zhijian.ebook.base.dao.UserMapper;
import com.zhijian.ebook.base.entity.Dict;
import com.zhijian.ebook.base.entity.User;
import com.zhijian.ebook.base.entity.Version;
import com.zhijian.ebook.base.service.CaptchaService;
import com.zhijian.ebook.base.service.DictService;
import com.zhijian.ebook.base.service.UserService;
import com.zhijian.ebook.base.service.VersionService;
import com.zhijian.ebook.bean.ResponseEntity;
import com.zhijian.ebook.bean.SMSCaptcha;
import com.zhijian.ebook.bean.SysUserDetails;
import com.zhijian.ebook.security.UserContextHelper;
import com.zhijian.ebook.service.WeixinServer;
import com.zhijian.ebook.util.ConstantString;
import com.zhijian.ebook.util.FileUpLoadUtils;
import com.zhijian.ebook.util.PasswordEncoderUtil;
import com.zhijian.ebook.util.StringConsts;

/**
 * 
 * 系统管理接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "manager")
public class ManagerInterface {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private UserService userService;

	@Autowired
	private VersionService versionService;

	@Autowired
	private DictService dictService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private WeixinServer weixinServer;

	/**
	 * 修改密码接口
	 * 
	 * @param password
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping(value = "login/modifyUserPassword", method = RequestMethod.POST)
	public ResponseEntity modifyUserPassword(String password, String newPassword) {
		String username = UserContextHelper.getUsername();
		log.info("=========>username:" + username);
		log.info("=========>旧密码:" + password);
		log.info("=========>新密码:" + newPassword);
		try {
			if (userService.modifyUserPassword(username, password, newPassword)) {
				return ResponseEntity.ok("密码更新成功");
			} else {
				return ResponseEntity.serverError("密码更新失败");
			}
		} catch (Exception e) {
			log.error("密码更新失败 {} ", e);
			return ResponseEntity.serverError("密码更新失败");
		}
	}

	/**
	 * 注册用户（注册完毕后直接登录）
	 *
	 * @param user
	 *            用户名必填 (password:可以传，可以不传)
	 * @param captcha
	 *            短信验证码
	 * @param session
	 *            Spring MVC 自动注入
	 * @return ResponseEntity 响应消息
	 */
	@ResponseBody
	@RequestMapping(value = "unLogin/register")
	public ResponseEntity register(User user, SMSCaptcha captcha, HttpSession session) {
		try {
			// 验证短信验证码输入是否正确
			/*
			 * boolean isFlag = captchaService .validateSMSCaptcha(captcha, session); if
			 * (!isFlag) { return ResponseEntity.serverError("验证码错误！"); }
			 */
			// String countStr =
			// ReadPropertiesFileUtils.getInstance().getPropValueByKey("default_bind_mobile_phone_count");
			// 设置手机可更换绑定次数(配置文件配置，如特殊需要修改，用户需联系管理员从管理平台修改)
			// user.setBindMobileCount(StringUtils.isBlank(countStr)?0:Integer.valueOf(countStr));
			// 验证手机号
			User u = userService.findUserByUsername(user.getUsername());
			if (u != null) {
				return ResponseEntity.serverError("该手机号已注册，请直接登录");
			}
			int flag = userService.addUser(user, StringConsts.USER_ROLE_ID);
			if (flag == StringConsts.SUCCESS_STATUS) {
				SysUserDetails userDetails = userService.findSysUserDetails(user.getUsername());
				UserContextHelper.login(userDetails, session);
				User baseUser = userService.findUserByUsername(user.getUsername());
				session.setAttribute("user", baseUser);
				Map<String, Object> msg = new HashMap<String, Object>();
				msg.put("user", baseUser);
				PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(user.getUsername(), StringConsts.PASSWORD_ENCRYPTION_WAY);
				String md5 = md5Mncoder.encode(user.getUsername());
				if (md5.equals(user.getPassword())) {
					return ResponseEntity.ok(msg, "注册成功！默认密码为您的用户名，请您登录后修改！");
				} else {
					return ResponseEntity.ok("注册成功!");
				}
			} else {
				return ResponseEntity.serverError("注册失败");
			}
		} catch (Exception e) {
			log.error("注册新用户失败！user={}", user, e);
			return ResponseEntity.serverError();
		}
	}

	// private static final String VERFY_MOBILE_PHONEKEY_RESULT_OK = "1"; // 校验通过

	// private static final String VERFY_MOBILE_PHONEKEY_RESULT_INIT = "0"; //
	// 登录手机key未绑定，需要绑定

	// private static final String VERFY_MOBILE_PHONEKEY_RESULT_EDIT = "2"; //
	// 更换手机key绑定状态

	// private static final String VERFY_MOBILE_PHONEKEY_RESULT_FAIL = "-1"; //
	// 更换手机key绑定状态

	/**
	 * 校验登录用户手机唯一key的绑定情况接口（登录前校验）
	 * 
	 * @param username
	 *            用户名
	 * @return {"status":200,"data":0,"message":"尊敬的用户...."} verifyStatus：校验结果状态 "0"
	 *         //登录手机key未绑定，需要绑定 "2" //更换手机key绑定状态 "-1" //更换手机key绑定状态 "1" //验证通过
	 */
	// @ResponseBody
	// @RequestMapping(value = "unLogin/verifyMobilePhoneKeyBind", method =
	// RequestMethod.POST)
	// public ResponseEntity verifyMobilePhoneKeyBindStatus(String username,String
	// mobilePhoneKey) {
	// try {
	// if (StringUtils.isBlank(username)) {
	// return ResponseEntity.serverError("请输入账号");
	// }
	// if (StringUtils.isBlank(mobilePhoneKey)) {
	// return ResponseEntity.serverError("手机key不能为空！");
	// }
	// User user = userService.findUserByUsername(username);
	// if (user == null) {
	// return ResponseEntity.serverError("用户不存在！");
	// }
	// String app_name =
	// ReadPropertiesFileUtils.getInstance().getPropValueByKey("_app_name",
	// "折腾吧");
	// if (StringUtils.isBlank(user.getMobilePhoneKey())) {
	// return ResponseEntity.ok(VERFY_MOBILE_PHONEKEY_RESULT_INIT , "尊敬的用户，欢迎登录[" +
	// app_name + "]为了您账号的安全，我们需要验证码进行验证您的账号，请获取并输入验证码");
	// }
	// if (mobilePhoneKey.equals(user.getMobilePhoneKey())) {
	// return ResponseEntity.ok( VERFY_MOBILE_PHONEKEY_RESULT_OK, "验证通过");
	// } else {
	// Integer bindCount = user.getBindMobileCount();//可更换绑定资料
	// if (bindCount == null || bindCount == 0) {
	// return ResponseEntity.ok(VERFY_MOBILE_PHONEKEY_RESULT_FAIL,
	// "尊敬的用户，系统检测到您更换了新的手机，为了您的账号安全，暂且拦截您的登录，请您联系系统管理员。");
	// }
	// bindCount--;
	// user.setBindMobileCount(bindCount);
	// userService.modifyUser(user);
	// return ResponseEntity.ok(VERFY_MOBILE_PHONEKEY_RESULT_EDIT , "尊敬的用户，欢迎登录[" +
	// app_name + "],系统检测到您更换了新的手机，为了您账号的安全，我们需要验证码进行验证您的账号，请获取并输入验证码");
	// }
	// } catch (Exception e) {
	// log.error("校验异常！",e);
	// return null;
	// }
	// }

	/**
	 * 登入
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param session
	 *            Spring MVC 自动注入
	 * @param response
	 *            Spring MVC 自动注入
	 * @return ResponseEntity 响应消息
	 */
	@ResponseBody
	@RequestMapping(value = "unLogin/login")
	public ResponseEntity login(String username, String password, HttpSession session, HttpServletResponse response) {
		try {
			User user = userService.findUserByUsername(username);
			// 验证用户名是否存在
			if (user == null) {
				return ResponseEntity.serverError("用户不存在！");
			}

			// 验证是否有效
			if (!user.getIsValid()) {
				return ResponseEntity.serverError("用户已被冻结, 无法登录!");
			}
			PasswordEncoderUtil md5Mncoder = new PasswordEncoderUtil(username, StringConsts.PASSWORD_ENCRYPTION_WAY);
			String md5 = md5Mncoder.encode(password);
			if (md5.equals(user.getPassword())) {
				UserDetails userDetails = userService.findSysUserDetails(username);
				UserContextHelper.login(userDetails, session);
				session.setAttribute("user", user);
				Map<String, Object> msg = new HashMap<String, Object>();
				msg.put("user", user);
				return ResponseEntity.ok(msg);
			}
			return ResponseEntity.serverError("用户名或密码错误！");
		} catch (Exception e) {
			log.error("登录失败！username={}", username, e);
			return ResponseEntity.serverError();
		}
	}

	/**
	 * 微信登入
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param session
	 *            Spring MVC 自动注入
	 * @param response
	 *            Spring MVC 自动注入
	 * @return ResponseEntity 响应消息
	 */
	@ResponseBody
	@RequestMapping(value = "unLogin/weblogin")
	public ResponseEntity weblogin(String code, String openid, HttpSession session, HttpServletResponse response) {
		try {

			if (openid == null) {
				openid = weixinServer.getOpenIds(code);
				if (openid == null) {
					return ResponseEntity.serverError("重复的code");
				}
			}

			// String openid = weixinServer.getOpenIds(code);
			// openid = code;
			System.out.println(openid);
			User user = userService.findUserByUsername(openid);
			// 验证用户名是否存在
			if (user == null) {
				log.info("新用户，添加用户");
				user = weixinServer.insertUser(openid);
			}
			if (StringUtils.isBlank(user.getId())) {
				return ResponseEntity.serverError(user.getRemark());
			}
			if (StringUtils.isBlank(user.getIcon())) {
				user = weixinServer.getUserInfo(user);
			}

			// // 验证是否有效
			// if (!user.getIsValid()) {
			// return ResponseEntity.serverError("用户已被冻结, 无法登录!");
			// }
			UserDetails userDetails = userService.findSysUserDetails(openid);
			UserContextHelper.login(userDetails, session);
			session.setAttribute("user", user);
			Map<String, Object> msg = new HashMap<String, Object>();
			user.setPassword(null);
			msg.put("user", user);
			return ResponseEntity.ok(msg);
		} catch (Exception e) {
			log.error("登录失败！code={}", code, e);
			return ResponseEntity.serverError();
		}
	}

	/**
	 * 登出
	 *
	 * @param request
	 *            请求
	 * @param response
	 *            Spring MVC 自动注入
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping(value = "unLogin/logout", method = RequestMethod.POST)
	public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
		UserContextHelper.logout(request, response);
		return ResponseEntity.ok();
	}

	/**
	 * 修改用户信息，但不能修改： 用户名， 密码， 用户图片
	 * 
	 * @param user
	 *            用户信息
	 * @return ResponseEntity 状态信息
	 */
	@ResponseBody
	@RequestMapping(value = "login/modifyUserInfo", method = RequestMethod.POST)
	public ResponseEntity modifyUserInfo(User user) {
		String userName = UserContextHelper.getUsername();
		if (org.apache.commons.lang.StringUtils.isBlank(userName)) {
			return ResponseEntity.serverError("请先登录！");
		}
		try {
			user.setUsername(userName);
			int count = userService.modifyUserByUsername(user);
			if (count <= 0) {
				return ResponseEntity.serverError("用户信息更新失败");
			}
		} catch (Exception e) {
			log.error(e);
			return ResponseEntity.serverError("用户信息更新失败");
		}
		return ResponseEntity.ok("用户信息更新成功");
	}

	/**
	 * 修改用户图像
	 * 
	 * @param request
	 *            请求
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping(value = "login/modifyUserIcon", method = RequestMethod.POST)
	public ResponseEntity modifyUserIcon(HttpServletRequest request) {
		String userName = UserContextHelper.getUsername();
		if (org.apache.commons.lang.StringUtils.isBlank(userName)) {
			return ResponseEntity.serverError("请先登录！");
		}
		try {
			List<String> sufferList = new ArrayList<String>();
			sufferList.add("jpg");
			sufferList.add("png");
			sufferList.add("gif");
			sufferList.add("bmp");
			sufferList.add("jpeg");
			// 生成一个文件名，防止文件覆盖
			String newFileName = randomFileName();
			// 文件上传
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			Map<String, String> fileImgMap = new HashMap<String, String>();
			List<MultipartFile> valueList = new ArrayList<MultipartFile>();
			int vainIndex = 0;
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String suffer = FileUpLoadUtils.getFileSuffix(file);
						if (sufferList.contains(suffer.toLowerCase())) {
							valueList.add(file);
						} else {
							vainIndex++;
						}
					}
				}
				if (vainIndex > 0) {
					return ResponseEntity.serverError("只支持(jpg,png,gif,bmp,jpeg)格式的图片，请检查！");
				}
				if (valueList.size() == 0) {
					return ResponseEntity.serverError("没有要上传的图片，请检查！");
				}

				// 用户图片上传路径
				String userIconPath = StringConsts.FILE_SAVE_ROOT_DIRECTORY + StringConsts.USER_ICON;
				String iconFilePath = "";
				for (MultipartFile file : valueList) {
					String filename = FileUpLoadUtils.writeFile(file, userIconPath, newFileName, true);
					iconFilePath = StringConsts.USER_ICON + filename;
					userService.modifyUserIcon(userName, iconFilePath);
				}
				fileImgMap.put("iconFilePath", iconFilePath);
				log.info("文件上传成功，上传路径={}", fileImgMap.toString());
				return ResponseEntity.ok(fileImgMap, "上传用户图片操作成功");
			} else {
				log.info("文件上传成功，上传路径={}", fileImgMap.toString());
				return ResponseEntity.serverError("上传用户图片操作失败");
			}

		} catch (Exception e) {
			log.error("上传用户图片操作异常！", e);
			return ResponseEntity.serverError("上传用户图片操作异常！");
		}
	}

	
	/**
	 * 忘记密码接口（需要短信验证码）
	 *
	 * @param sms
	 *            参数
	 * @param newPassword
	 *            新密码
	 * @param session
	 *            Spring MVC 自动注入
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping(value = "unLogin/forgetPassword")
	public ResponseEntity forgetPassword(SMSCaptcha sms, String newPassword, HttpSession session) {
		boolean result = captchaService.validateSMSCaptcha(sms, session);
		// result = true;
		try {
			if (result) {
				// 验证成功， 重置密码
				String username = sms.getPhoneNumber();
				userService.doResetPassword(username, newPassword);
				log.info("{} 重置了密码！", username);
				return ResponseEntity.ok();
			} else {
				// 验证失败
				return ResponseEntity.serverError("验证码错误！");
			}
		} catch (Exception e) {
			log.error("重置密码失败！{}", sms, e);
			return ResponseEntity.serverError();
		}
	}

	/**
	 * 检查更新
	 *
	 * @param currentVersion
	 *            当前版本号
	 * @param platform
	 *            平台
	 * @return isNeedUpdrage 是否需要更新, version 版本信息{@link Version}, message 消息
	 */
	@ResponseBody
	@RequestMapping(value = "unLogin/checkUpdrage", method = RequestMethod.POST)
	public ResponseEntity checkUpdrage(Integer currentVersion, String platform) {
		if (currentVersion == null) {
			return ResponseEntity.illegalParam("currentVersion can not be null");
		}

		if (StringUtils.isEmpty(platform)) {
			return ResponseEntity.illegalParam("platform can not be null");
		}

		if (!ConstantString.IOS.equalsIgnoreCase(platform) && !ConstantString.ANDROID.equalsIgnoreCase(platform)) {
			return ResponseEntity.illegalParam(String.format("platform can not be %s, expect value : %s, %s", platform, ConstantString.IOS, ConstantString.ANDROID));
		}

		try {
			Map<String, Object> msg = new HashMap<>();
			Version version = versionService.doCheckUpgrade(currentVersion, platform);
			if (version == null) {
				msg.put("isNeedUpdrage", false);
				msg.put("message", "您已是最新版本!");
			} else {
				msg.put("isNeedUpdrage", true);
				msg.put("version", version);
				msg.put("message", "发现新版本!");
			}
			return ResponseEntity.ok(msg);
		} catch (Exception e) {
			log.error("检查更新失败! currentVersion={}, platform={}", currentVersion, platform, e);
			return ResponseEntity.serverError();
		}
	}

	/**
	 * 根据Key获取类型
	 * 
	 * @param key
	 *            类型Key
	 * @return 类型列表
	 */
	@ResponseBody
	@RequestMapping("unLogin/findTypesByKey")
	public ResponseEntity findTypesByKey(String key) {
		if (StringUtils.isEmpty(key)) {
			return ResponseEntity.illegalParam("参数异常");
		}
		List<Dict> dictList = dictService.findTypesByKey(key);
		return ResponseEntity.ok(dictList);
	}

	/**
	 * 校验推荐码
	 * 
	 * @param referralCode
	 *            推荐码
	 * @return ResponseEntity 响应信息
	 */
	@ResponseBody
	@RequestMapping("unLogin/verifyReferralCode")
	public ResponseEntity verifyReferralCode(String referralCode) {
		try {
			if (StringUtils.isBlank(referralCode)) {
				return ResponseEntity.serverError("推荐码不能为空！");
			}
			int row = userService.findCountByReferralCode(referralCode);
			if (row > 0) {
				return ResponseEntity.ok("验证成功！");
			} else {
				return ResponseEntity.serverError("验证不通过！");
			}
		} catch (Exception e) {
			log.error("校验推荐码异常！", e);
			return ResponseEntity.serverError("验证异常！");
		}
	}

	/**
	 * 生成随机文件名
	 * 
	 * @return
	 */
	public static String randomFileName() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return ResponseEntity (User)
	 */
	@ResponseBody
	@RequestMapping("findUserInfo")
	public ResponseEntity findUserByUserName() {
		String userName = UserContextHelper.getUsername();
		if (StringUtils.isEmpty(userName)) {
			return ResponseEntity.illegalParam("请先登录");
		}
		User user = userMapper.findUserByUsername(userName);
		/*
		 * String str =
		 * JSONObject.toJSONString(user,SerializerFeature.WriteMapNullValue);
		 * 
		 * @SuppressWarnings("unchecked") Map<String,String> map =
		 * JSONObject.parseObject(str, Map.class);
		 */
		return ResponseEntity.ok(user);
	}

}
