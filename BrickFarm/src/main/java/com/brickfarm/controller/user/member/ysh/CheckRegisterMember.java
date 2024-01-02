package com.brickfarm.controller.user.member.ysh;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.dto.user.ysh.UserMemberDTO;
import com.brickfarm.service.user.member.MemberService;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Controller
public class CheckRegisterMember {
	@Inject
	private MemberService mService;

	// 일반 회원가입시 아이디 중복체크
	@RequestMapping(value = "/user/member/checkUserid", method = RequestMethod.POST)
	public ResponseEntity<String> checkRegisterUserId(@RequestParam("member_id") String member_id, Model model,
			HttpStatus status) {
		ResponseEntity<String> result = null;
		String checkedUserId;
		try {
			checkedUserId = mService.checkUserId(member_id);
			// System.out.println(checkedUserId); // 유저가 중복확인 하려는 id
			if (checkedUserId != null) {
				result = new ResponseEntity<String>("duplicate", status.OK);
			} else {
				result = new ResponseEntity<String>("notduplicate", status.OK);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 일반회원가입시 이메일, 전화번호 중복검사 소셜체크
	@RequestMapping(value = "/user/member/checkedmemberforregister", method = RequestMethod.POST)
	public ResponseEntity<UserMemberDTO> checkedmember(@RequestParam("member_name") String member_name,
			@RequestParam("phone_number") String phone_number, @RequestParam("email") String email, Model model) {

		ResponseEntity<UserMemberDTO> result = null;
		// System.out.println(phone_number);
		// System.out.println(email);
		// System.out.println(member_name);

		UserMemberDTO memberDTO = null;
		try {
			memberDTO = mService.checkedmemberforregister(member_name, phone_number, email);
			result = new ResponseEntity<UserMemberDTO>(memberDTO, HttpStatus.OK);
			// System.out.println("중복검사 : " + memberDTO);
		} catch (Exception e) {
			result = new ResponseEntity<UserMemberDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;

	}

	// sns로그인 추가입력 사항시 중복검사
	@RequestMapping(value = "/user/member/checkuserinfo", method = RequestMethod.POST)
	public ResponseEntity<String> CheckUserInfo(@RequestParam("phone_number") String phone_number) {
		String phone;
		ResponseEntity<String> result = null;
		try {
			phone = mService.findMemberPhoneInfo(phone_number);
			result = new ResponseEntity<String>(phone, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

}
