package com.brickfarm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.brickfarm.vo.admin.ysh.AdminVO;

public class AdminInterceptor implements HandlerInterceptor {
//	private static final Logger logger = LoggerFactory.getLogger(MemberInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;
		HttpSession session = request.getSession();
//		String currentPath = request.getRequestURI(); // 요청한 경로
		AdminVO adminVO = (AdminVO) session.getAttribute("adminInfo");
//		System.out.println("adminVO" + adminVO);
//		System.out.println(currentPath);
		if (adminVO == null) {
			response.sendRedirect("/admin/adminlogin");
			result = false;
		} else if (adminVO != null) {
			// response.sendRedirect(currentPath);
			result = true;
		}
		return result;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
