package com.brickfarm.controller.user.member.ysh;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.service.user.member.MemberService;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Controller
public class MypageInfoModify {

	@Inject
	private MemberService mService;

	@RequestMapping(value = "/user/member/mypageinfomodify", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> MypageModify(@RequestParam("member_no") int member_no,
			@RequestParam("member_name") String member_name, @RequestParam("phone_number") String phone_number,
			@RequestParam("email") String email, @RequestParam("zip_code") String zip_code,
			@RequestParam("address") String address, Model model) {

		ResponseEntity<Map<String, Object>> result = null;

		String tmpNumber;
		try {
			tmpNumber = mService.getMemberPhoneNumber(member_no);
			// System.out.println(tmpNumber);

			UserMemberVO memberVO = new UserMemberVO();
			memberVO.setMember_no(member_no);
			memberVO.setMember_name(member_name);
			memberVO.setEmail(email);
			memberVO.setZip_code(zip_code);
			memberVO.setAddress(address);
			memberVO.setPhone_number(phone_number);

			// System.out.println(memberVO.toString());
			Map<String, Object> data = new HashMap<String, Object>();
			if (mService.ModifiMemberInfo(memberVO)) {
				data.put("fail", true);
				result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);

			} else {
				data.put("fail", false);
				result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 기존의 전화번호 가져오기.
		return result;

	}

}
