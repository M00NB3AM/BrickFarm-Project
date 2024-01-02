package com.brickfarm.controller.user.member.ysh;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.dto.user.ysh.UserMemberDTO;
import com.brickfarm.service.user.member.MemberService;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Controller
public class FindMemberInfo {

	@Inject
	private MemberService mService;

	@RequestMapping("/user/member/finduserid")
	public void findUserInfo() {
		// System.out.println("유저의 아이디 / 비밀번호 정보를 찾는 곳");
	}

	// 유저의 아이디 찾는곳
	@RequestMapping(value = "/user/member/findmemberid", method = RequestMethod.POST)
	public String findUserId(@RequestParam("member_name") String member_name,
			@RequestParam("phone_number") String phone_number, Model model) {
		UserMemberVO memberVO;
		try {
			memberVO = mService.findUserId(member_name, phone_number);
			// System.out.println("memberId : " + memberVO);
			model.addAttribute("memberVO", memberVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("유저의 아이디를 찾는 Controller");
		return "/user/member/findresult";

	}

	// 유저의 비밀번호 찾기. -
	@RequestMapping(value = "/user/member/finduserpw", method = RequestMethod.POST)
	public String findUserPwd(@RequestParam("member_name") String member_name,
			@RequestParam("member_id") String member_id, @RequestParam("phone_number") String phone_number,
			HttpSession session, Model model) {
		String result = "";
		// System.out.println("유저의 비밀번호를 찾는 controller");
		UserMemberDTO memberInfo;
		try {
			memberInfo = mService.finduserPwd(member_name, member_id, phone_number);
			// System.out.println("유저의 비밀번호 찾기 FindMemberInfo" + memberInfo);
			if (memberInfo.getMember_id() == null && memberInfo.getPhone_number() == null) {
				result = "/user/member/findresult";// 비밀번호 찾기 했을 경우 유저의 정보가 없을때.(정보가 없다고 알려주는 설정 해야함..)
			} else {
				model.addAttribute("memberInfo", memberInfo); // 객체로 다시 가져옵시다. 그래야 id 비교 할 수 있습니다.
				result = "/user/member/modifypassword"; // 비밀번호 변경 페이지로 이동.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 비밀번호 변경
	@RequestMapping(value = "/user/member/modifymemberpwd", method = RequestMethod.POST)
	public String modifyMemberPwd(@RequestParam("newPassoword") String password,
			@RequestParam("member_id") String member_id, Model model) {
		boolean result = false;
		// System.out.println("유저의 비밀번호를 변경하는 controller");
		// System.out.println(password +" // "+ member_id);
//		ModifyMemberDTO memberDTO = (ModifyMemberDTO) session.getAttribute("memberInfo");
		try {
			if (mService.updateUserPwd(password, member_id)) { // 변경 성공시
				result = true;
				model.addAttribute("result", result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/user/member/modifyresult";
	}

	// 비밀번호 변경시 이전 비밀번호와 같은지 조회
	@RequestMapping("/user/member/checkpassword")
	public String checkedPwd(@RequestParam("member_id") String member_id) {
		String beforePwd = "";
		try {
			beforePwd = mService.getbeforePwd(member_id);
			// System.out.println("이전의 비밀번호 : " + beforePwd);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beforePwd;
	}

}
