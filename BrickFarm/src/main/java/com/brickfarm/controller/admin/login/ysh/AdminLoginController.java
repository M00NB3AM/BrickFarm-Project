package com.brickfarm.controller.admin.login.ysh;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.brickfarm.service.admin.member.MemberManagingService;
import com.brickfarm.vo.admin.ysh.AdminVO;

@Controller
@RequestMapping("/admin/*")
public class AdminLoginController {

	@Inject
	private MemberManagingService mMService;
	
	@RequestMapping("/adminlogin")
	public String LoginForm() {
		
		return "/admin/adminlogin";
	}

	// 로그인시 아이디 체크
	@RequestMapping(value="/adminglogincheck", method = RequestMethod.POST)
	public @ResponseBody boolean AdminLoginCheck(@RequestParam("admin_id") String admin_id, @RequestParam("admin_password") String admin_password, HttpServletRequest request) { 
		boolean result = false;
		
//		String referer = request.getHeader("Referer"); // 이전경로
        // System.out.println(referer);
        
        String currentPath = request.getRequestURI(); // 현재요청한 경로
        request.getSession().setAttribute("currentPath", currentPath);
//        String savePath = (String) request.getSession().getAttribute("currentPath");
		
		try {
			if(mMService.loginAdminCheck(admin_id, admin_password)!= null) { //role이 있다면
				result = true;
			}else {// role이 없다면
				result = false; 
			}
		} catch (Exception e) {
			
		}	
		return result;
	}

	// 관리자 로그인시 session 객체에 담아주면서 index로 경로 변경해주기.
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String AdmingLogin(@RequestParam("admin_id") String admin_id, @RequestParam("admin_password") String admin_password, HttpSession session) {
		String url = "";
		AdminVO admin = new AdminVO();
		try {
			admin = mMService.adminLogin(admin_id, admin_password);
			// System.out.println(admin);
			session.setAttribute("adminInfo", admin);
			url = "redirect:/admin";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	
	//관리자 로그아웃
	@RequestMapping("/logout")
	public ModelAndView Adminlogout(HttpSession session) {
		session.invalidate();
		ModelAndView mv = new ModelAndView("redirect:/admin");

		return mv;
	}
	
	

}
