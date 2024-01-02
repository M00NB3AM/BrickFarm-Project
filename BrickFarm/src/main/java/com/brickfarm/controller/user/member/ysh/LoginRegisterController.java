package com.brickfarm.controller.user.member.ysh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.dto.user.ysh.UserMemberLoginDTO;
import com.brickfarm.service.user.member.MemberService;
import com.brickfarm.vo.user.psj.ShoppingCartVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;
import com.github.scribejava.core.model.Response;

@Controller
public class LoginRegisterController {

	@Inject
	private MemberService mService;

	// 로그인시에 데이터를 가져오는 메서드
	@RequestMapping(value = "/user/member/headershoplist", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> cartList(@RequestParam("member_no") int member_no) {
		Map<String, Object> shoppinglist = new HashMap<String, Object>();

		try {
			List<UserShoppingCartDTO> tmpList = mService.getHeaderShoppingList(member_no);
			shoppinglist.put("list", tmpList);// 장바구니 가져오기
			// System.out.println("controller로 들어온 shoppingList" + shoppinglist);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(shoppinglist);
	}

	@RequestMapping(value = "/user/member/headerwishlist", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getWishList(@RequestParam("member_no") int member_no) {
		Map<String, Object> wishList = new HashMap<String, Object>();
		try {
			int count = mService.getHeaderWishList(member_no);
			wishList.put("headerWishList", count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok(wishList);
	}

	// ---------------------- 일반로그인시 아이디 비밀번호 확인
	// -----------------------------------------------
	@RequestMapping(value = "/user/member/loginpage", method = RequestMethod.POST)
	public @ResponseBody boolean login(@RequestParam("member_id") String member_id,
			@RequestParam("password") String password, HttpServletRequest request) {

		String referer = request.getHeader("Referer"); // 이전경로
		// System.out.println(referer);

		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("currentPath", currentPath);
		String savePath = (String) request.getSession().getAttribute("currentPath");

		boolean result = false;
		// System.out.println("login id 비밀번호 확인.");
		// System.out.println(member_id + " //" + password);
		try {
			if (mService.login(member_id, password)) {
				// System.out.println("LOGINCONTROLLER");
				result = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// sns로그인시 아이디 중복확인버튼.
	@RequestMapping(value = "/user/member/snsmemberidcheck", method = RequestMethod.POST)
	public @ResponseBody boolean login(@RequestParam("member_id") String member_id) {
		boolean result = false;

		try {
			if (mService.snsLoginMemberCheck(member_id)) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	// ------------------------- 일반 회원 로그인부분---------------------------------------
	@RequestMapping(value = "/user/member/loginMember", method = RequestMethod.POST)
	public String loginMember(@RequestParam("member_id") String member_id, @RequestParam("password") String password,
			HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) {
		String URI = (String) session.getAttribute("requestURI");
		UserMemberLoginDTO memberDTO = new UserMemberLoginDTO();
		memberDTO.setMember_id(member_id);
		memberDTO.setPassword(password);
		String referer = request.getHeader("Referer"); // 이전경로

		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("currentPath", currentPath);
		String savePath = (String) request.getSession().getAttribute("currentPath");
		String previousPath = (String) session.getAttribute("savePath");
		String beforePath = (String) session.getAttribute("beforePath");

		// System.out.println("이전경로 : " + referer);
		// System.out.println("요청경로 : " + currentPath);
		// System.out.println("무슨경로지?" + previousPath);
		// System.out.println("이건 무슨 경로야?" + URI);
		// System.out.println("로그인에서 넘어온 이전경로 맞아?" + beforePath);

		UserMemberVO member;
		String result = "";

		try {
			member = mService.loginMember(memberDTO);
			if (member != null && URI == null) {
				// 로그인 성공시.
				UserMemberVO sessionMember = new UserMemberVO(member.getMember_no(), member.getMember_id(),
						member.getSocial_check());
				session.setAttribute("loginMemberInfo", sessionMember);

				// System.out.println("로그인 성공");
				// 1. 로그인 성공시 회원 번호를 이용하여 로그인 로그기록에 insert
				mService.insertLoginLog(member.getMember_no());

				// 2. 로그인 성공하면 회원번호를 이용하여 장바구니 리스트랑 찜목록 가져오기.
				List<ShoppingCartVO> shoppingList = mService.getShoppingList(member.getMember_no());
				// System.out.println("회원의 쇼핑 리스트는 : " + shoppingList);

				session.setAttribute("shoppingList", shoppingList);// 상품리스트...

				// System.out.println("loginController129 : "+model);
				if (beforePath != null) {

					if (beforePath.equals("http://localhost:8081/user/member/registermember")) { // 만약 이전경로가 회원가입
																									// 페이지라면..
						beforePath = "/";
					}
				} else {
					beforePath = "/";
				}
				result = "redirect:" + beforePath; // 이전페이지로

			} else if (member != null && URI != null) {
				UserMemberVO sessionMember = new UserMemberVO(member.getMember_no(), member.getMember_id(),
						member.getSocial_check());
				session.setAttribute("loginMemberInfo", sessionMember);
				// System.out.println("가려고 하는 경로?" + URI);
				if (URI.equals("http://localhost:8081/user/member/registermember")) {
					URI = "/";
				}
				result = "redirect:" + URI; // 가려고 하는 경로로
			} else {
//				// 로그인 실패.
				// System.out.println("로그인 실패");
				// System.out.println("savePath" + savePath);
				// System.out.println("referer" + referer);
				result = referer;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 일반 유저 회원가입
	@RequestMapping(value = "/user/member/registermember", method = RequestMethod.POST)
	// security : csrf는 나중에 처리하자..;;
	public String registerMember(@ModelAttribute UserMemberVO memberVO,
			@RequestParam("detailaddress") String detailAddress, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) { // security : csrf는 나중에 처리하자..;;
		String tmpAddress = memberVO.getAddress();
		String address = tmpAddress + detailAddress;
		memberVO.setAddress(address);
		// System.out.println("회원가입 주소는 : " + address);
		String referer = request.getHeader("Referer"); // 이전경로
		// System.out.println(referer);

		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("currentPath", currentPath);
		String savePath = (String) request.getSession().getAttribute("currentPath");

		String result = "";

		// System.out.println("일반 유저의 회원가입 부분입니다.");
		// System.out.println(memberVO);

		try {
			if (mService.registerMember(memberVO)) { // 회원가입에 성공했을 경우 메시지 띄어주고 로그인폼으로 다시 이동.
				result = "redirect:/user/member/loginpage";
			} else { // 회원가입에 실패했을 경우 메시지 띄어주고 회원가입폼으로 다시 이동..
				result = "redirect:/user/member/registermember";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
