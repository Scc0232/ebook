package com.zhijian.ebook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 视图 拦截器，
 * 主要功能是为视图的request中自动放入basePath属性。basePath属性为路径前缀
 * 
 * @author Administrator
 *
 */
public class ViewInterceptor implements HandlerInterceptor {

	/**
	 * 上次请求的服务器名称，举例：localhost
	 */
	private String lastRequestServerName = null;

	private String basePath = null;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// nothing
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView == null) {// 请求的不是视图，不需要放入属性
			return;
		}
		String currRequestServerName = request.getServerName();
		if (lastRequestServerName != currRequestServerName) {
			String contextPath = request.getContextPath();// 上下文Path（项目名）
			StringBuffer sb = new StringBuffer(request.getScheme());// 网络协议，举例：http
			sb.append("://").append(request.getServerName()).append(":")
					.append(request.getServerPort());// 网络地址+端口号
			sb.append(contextPath).append("/");

			basePath = sb.toString();
			lastRequestServerName = currRequestServerName;
		}
		request.setAttribute("basePath", basePath);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// nothing
	}
}
