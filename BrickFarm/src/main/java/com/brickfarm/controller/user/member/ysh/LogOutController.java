package com.brickfarm.controller.user.member.ysh;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogOutController {

	// 회원이 가지고 있는 정보의 session을 끊어버리기.
	@RequestMapping("/user/member/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mv = new ModelAndView("redirect:/");

		return mv;

	}
}
