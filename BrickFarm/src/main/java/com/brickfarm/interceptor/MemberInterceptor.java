package com.brickfarm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.brickfarm.vo.user.ysh.UserMemberVO;

public class MemberInterceptor implements HandlerInterceptor {

//	private static final Logger logger = LoggerFactory.getLogger(MemberInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = false;
		HttpSession session = request.getSession();
		String currentPath = request.getRequestURI(); // 요청한 경로
		UserMemberVO userMemberVO = (UserMemberVO) session.getAttribute("loginMemberInfo");
		// session.setAttribute("requestURI : 요청한경로 -> ", currentPath);

//		System.out.println("========================   preHandle START   ========================");
//		System.out.println(currentPath);		
		if (userMemberVO == null) {
			response.sendRedirect("/user/member/loginpage");
			result = false;
		} else if (userMemberVO != null) {
			result = true;
		}
//		System.out.println("========================   preHandle END   ========================");
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		System.out.println("========================   postHandle START   ========================");

//		System.out.println("========================   postHandle END   ========================");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
