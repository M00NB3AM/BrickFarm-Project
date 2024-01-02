package com.brickfarm.controller.user.member.ysh;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.service.user.member.MemberService;

@Controller
public class DeleteMemberInfo {

	@Inject
	private MemberService mService;

	@RequestMapping("/user/member/deletemember")
	public void deleteMemberInfoControl(HttpSession session, HttpServletRequest request) {
		// System.out.println("일반 회원탈퇴 페이지");
	}

	@RequestMapping("/user/member/snsdeletemember")
	public void deletesocialMemberInfoControl(HttpSession session, HttpServletRequest request) {
		// System.out.println("소셜로그인 회원탈퇴 페이지");
	}

	// 일반회원 탈퇴시 비밀번호 check
	@RequestMapping(value = "/user/member/checkdeletememberpwd", method = RequestMethod.POST)
	public ResponseEntity<String> checkDeleteMemberPwd(@RequestParam("password") String password,
			@RequestParam("member_no") int member_no) {
		ResponseEntity<String> result = null;
		try {
			if (mService.checkDeleteMemberPwd(password, member_no)) {
				result = new ResponseEntity<String>("true", HttpStatus.OK);
			} else {
				result = new ResponseEntity<String>("false", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// 소셜로그인 유저 회원 탈퇴시 이메일 비교
	@RequestMapping(value = "/user/member/checkdeletesocialmemberemail", method = RequestMethod.POST)
	public ResponseEntity<String> checkdeletesocialmemberemail(@RequestParam("email") String email,
			@RequestParam("member_no") int member_no) {
		ResponseEntity<String> result = null;
		try {
			if (mService.checkDeleteSocialMemberEmail(email, member_no)) {
				result = new ResponseEntity<String>("true", HttpStatus.OK);
			} else {
				result = new ResponseEntity<String>("false", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 일반 회원 탈퇴 처리
	@RequestMapping(value = "/user/member/deletemember", method = RequestMethod.POST)
	public String deleteMemberInfo(HttpSession session, HttpServletRequest request,
			@RequestParam("password") String password, @RequestParam("member_no") int member_no,
			@RequestParam("deletereason") String deletereason, Model model) {
		// System.out.println("여기에서 처리합니다.");
		// System.out.println("사유는 : " + deletereason);
		String result = "";
		// 회원 탈퇴
		try {
			if (mService.deleteMember(password, member_no, deletereason)) {// 처리가 완료 되었다면.
				session.invalidate();
				result = "redirect:/user/member/deletesuccess?success=success";
			} else {
				result = "redirect:/user/member/deletesuccess?fail=fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/user/member/deletesuccess")
	public void deletesuccess() {
		// System.out.println("회원삭제 완료");
	}

}
