package com.brickfarm.controller.user.member.ysh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.auth.SNSLogin;
import com.brickfarm.auth.SnsValue;
import com.brickfarm.service.user.member.MemberService;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Controller
public class SnsCallback {

	@Inject
	private SnsValue naverSns;

	@Inject
	private GoogleConnectionFactory googleConnectionFactory;

	@Inject
	private OAuth2Parameters googleOAuth2Parameters;

	@Inject
	private SnsValue googleSns;

	@Inject
	private MemberService mService;

	// ---------------------- 로그인페이지 이동
	// ----------------------------------------------------------------------
	// 로그인 폼으로 들어옴.
	@GetMapping("/user/member/loginpage")
	public void loginMember(Model model, HttpSession session, HttpServletRequest request) throws Exception {
		String referer = request.getHeader("Referer"); // 이전경로
		// System.out.println(referer);
//        response.setHeader("beforePath", referer);
//        String path = (String)request.getHeader("beforePath");
//        System.out.println("과연?" + path);
		// System.out.println("이전경로?" + referer);
		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("currentPath", currentPath);
		String savePath = (String) request.getSession().getAttribute("currentPath");
		String prevURI = (String) session.getAttribute("requestURI");

		// System.out.println("로그임 폼 -> 이전경로 : " + referer);
		// System.out.println("로그임 폼 -> 요청한경로? : " + currentPath); // 로그인페이지
		// System.out.println("로그임 폼 -> 요청한경로? : " + savePath); // 로그인페이지
		// System.out.println("로그임 폼 -> 무슨경로? : " + prevURI); //-> null
		session.setAttribute("beforePath", referer);

		// naver 로그인 호출
		SNSLogin naverLogin = new SNSLogin(naverSns);
		model.addAttribute("naver_url", naverLogin.getNaverAuthURL());

		// 구글 로그인 호출
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		model.addAttribute("google_url", url);
	}

	// 회원가입 폼으로 들어옴
	@GetMapping("/user/member/registermember")
	public void RegisterMember(Model model, HttpSession session, HttpServletRequest request) throws Exception {

		String referer = request.getHeader("Referer"); // 이전경로
		// System.out.println(referer);

		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("currentPath", currentPath);
		String savePath = (String) request.getSession().getAttribute("currentPath");

		String prevURI = (String) session.getAttribute("requestURI");
		// System.out.println("소셜로그인 부분 : " + prevURI);

		// naver 로그인 호출
		SNSLogin naverLogin = new SNSLogin(naverSns);
		model.addAttribute("naver_url", naverLogin.getNaverAuthURL());

		// 구글 로그인 호출
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameters);
		model.addAttribute("google_url", url);
	}

//	----------------------------- 네이버 로그인 콜백 -------------------------------------------------------------------------
	@RequestMapping(value = "/auth/naver/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String naverLoginCallback(Model model, @RequestParam String code, HttpSession session,
			HttpServletRequest request) {
		// System.out.println("네이버 로그인 callback");
		// System.out.println(code);
		String result = null;
		String prevURI = (String) session.getAttribute("requestURI");

		// System.out.println("네이버 로그인 callback : " + prevURI);
		String referer = request.getHeader("Referer");
		// System.out.println("네이버 로그인 callback : " + referer);

		String currentPath = request.getRequestURI(); // 요청한 경로 확인
		request.getSession().setAttribute("naverLogin callback : ", currentPath);
		String savePath = (String) request.getSession().getAttribute("currnetPath");
		// System.out.println("naverLogin savePath = " + savePath);
		SNSLogin naverLogin = new SNSLogin(naverSns);
		UserMemberVO naverLoginMember;
		try {
			naverLoginMember = naverLogin.getNaverProfile(code);
			// System.out.println("naver 가입 유저는 : " + naverLoginMember);
			String social_check = naverLoginMember.getSocial_check();
			Map<String, String> map = new HashMap<String, String>();
			map.put("member_grade_name", naverLoginMember.getMember_grade_name());
			map.put("member_name", naverLoginMember.getMember_name());
			map.put("phone_number", naverLoginMember.getPhone_number());
			map.put("email", naverLoginMember.getEmail());
			map.put("gender", naverLoginMember.getGender());
			map.put("inactive_status", String.valueOf(naverLoginMember.getInactive_status()));
			map.put("accessToken", naverLoginMember.getAccess_token());
			map.put("social_check", naverLoginMember.getSocial_check());
			map.put("authority", naverLoginMember.getAuthority());

			// System.out.println("120번줄save : " + savePath);
			// System.out.println("121번줄curr : " + currentPath);
			// System.out.println("122번줄prev :" + prevURI);
			UserMemberVO naverLoginVO = new UserMemberVO();
			naverLoginVO = mService.snsCheck(social_check);

			if (naverLoginVO == null) {// 여기에서도 member_no를 넣어서 같이 보내줘야함..
				int member_no = snsRegister(map, social_check, naverLoginMember.getEmail());
				if (member_no == 0) {
					model.addAttribute("member_no", member_no);
					result = "/user/member/findresult";
				} else {
					model.addAttribute("member_no", member_no);
					result = "/user/member/snsloginregister";
				}
			} else if (naverLoginVO != null && savePath == null && currentPath != null && prevURI == null) { // 인덱스에서
																												// 소셜로그인
																												// 했을경우.
				// System.out.println("첫번째 else if");
				mService.snsLoginLog(naverLoginVO.getMember_no());
				session.setAttribute("loginMemberInfo", naverLoginVO);
				result = "redirect:/";
			} else if (naverLoginVO != null && savePath == null && currentPath != null) { // 상품에서 바로 장바구니 담으려고 할때 소셜로그인
				mService.snsLoginLog(naverLoginVO.getMember_no());
				session.setAttribute("loginMemberInfo", naverLoginVO);
				result = "redirect:" + prevURI;
			} else if ((naverLoginVO != null && savePath == null && currentPath == null)
					|| (naverLoginVO != null && savePath == null && currentPath.matches("(.*)callback(.*)")
							|| (naverLoginVO != null && savePath == null && !currentPath.matches("(.*)callback(.*)"))
							|| (naverLoginVO != null && savePath != null && currentPath == null))
					|| (naverLoginVO != null && savePath != null && currentPath != null)
					|| (naverLoginVO != null && savePath != null && currentPath.matches("(.*)callback(.*)"))
					|| (naverLoginVO != null && savePath != null && !currentPath.matches("(.*)callback(.*)"))
					|| (naverLoginVO != null && !savePath.matches("(.*)callback(.*)") && currentPath == null)
					|| (naverLoginVO != null && !savePath.matches("(.*)callback(.*)") && currentPath != null)
					|| (naverLoginVO != null && !savePath.matches("(.*)callback(.*)")
							&& currentPath.matches("(.*)callback(.*)"))
					|| (naverLoginVO != null && !savePath.matches("(.*)callback(.*)")
							&& !currentPath.matches("(.*)callback(.*)"))
					|| (naverLoginVO != null && savePath.matches("(.*)callback(.*)") && currentPath == null)
					|| (naverLoginVO != null && savePath.matches("(.*)callback(.*)") && currentPath != null)
					|| (naverLoginVO != null && savePath.matches("(.*)callback(.*)")
							&& currentPath.matches("(.*)callback(.*)"))
					|| (naverLoginVO != null && savePath.matches("(.*)callback(.*)")
							&& !currentPath.matches("(.*)callback(.*)"))) {
				// System.out.println("else if 의 경우");
				mService.snsLoginLog(naverLoginVO.getMember_no());
				session.setAttribute("loginMemberInfo", naverLoginVO);
				result = "redirect:/";
			} else if (naverLoginVO != null && !prevURI.matches("(.*)callback(.*)") && prevURI != null
					&& prevURI == "") {
				mService.snsLoginLog(naverLoginVO.getMember_no());
				// System.out.println("prev의 경우");
				session.setAttribute("loginMemberInfo", naverLoginVO);
				result = prevURI;
			} else {
				mService.snsLoginLog(naverLoginVO.getMember_no());
				// System.out.println("else의 경우");
				session.setAttribute("loginMemberInfo", naverLoginVO);
				result = prevURI;
			}

			// System.out.println("Controller Callback 위치 - prevURI : " + prevURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	// ----------------------------- 구글 로그인 콜백
	// ------------------------------------------------------------------------
	@RequestMapping(value = "/auth/google/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String googleLoginCallback(Model model, @RequestParam String code, HttpSession session,
			HttpServletRequest request) {

		String result = null;
		String prevURI = (String) session.getAttribute("requestURI");

		// System.out.println("70번줄 : "+prevURI);
		// System.out.println("Controller Callback 위치");
		String referer = request.getHeader("Referer"); // 이전경로
		// System.out.println("LoginRegisterController 224줄 : " + referer);

		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("snsLoginCallback - currentPath", currentPath);
		String savePath = (String) request.getSession().getAttribute("currentPath");
		// System.out.println("google/callback savePath : " + savePath);
		// System.out.println("google/callback currentPath : " + currentPath);

		// 1. code를 이용해서 access_token 받기
		// 2. access_token을 이용해서 사용자 profile 정보 가져오기.
		SNSLogin snsLogin = new SNSLogin(googleSns);
		// System.out.println("controller google Code : " + code);
		UserMemberVO googleSnsMember;
		try {
			googleSnsMember = snsLogin.getUserProfile(code); // 여기에서 데이터를 받는다..
			// System.out.println("Profile >> " + googleSnsMember); // sns로그인 버튼 클릭후 가져온
			// 데이터.
//				UserMemberVO userMemberVO = mService.insertSnsLogin(snsMember);
			// DB에 보내서 데이터가 없다면 회원가입 진행 있다면 이전페이지로..

			String access_token = googleSnsMember.getAccess_token(); // 엑세스토큰
			String email = googleSnsMember.getEmail();
			String tmpbirth_date = googleSnsMember.getBirth_date();
			String birth_date = tmpbirth_date;

			String gender = googleSnsMember.getGender();
			char inactive_status = googleSnsMember.getInactive_status();
			String social_check = googleSnsMember.getSocial_check();
			char enabled = googleSnsMember.getEnabled();
			String authority = googleSnsMember.getAuthority();
			String member_grade_name = googleSnsMember.getMember_grade_name();
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", access_token);
			map.put("email", email);
			map.put("birth_date", birth_date);
			map.put("gender", gender);
			map.put("inactive_status", String.valueOf(inactive_status));
			map.put("social_check", social_check);
			map.put("enabled", String.valueOf(enabled));
			map.put("authority", authority);
			map.put("member_grade_name", member_grade_name);

			// System.out.println("member_no : " + model);

			UserMemberVO userMemberVO = new UserMemberVO();
			userMemberVO = mService.snsCheck(social_check);

			// System.out.println("120번줄save : " + savePath);
			// System.out.println("121번줄curr : " + currentPath);
			// System.out.println("122번줄prev :" + prevURI);
			if (userMemberVO == null) {// 여기에서도 member_no를 넣어서 같이 보내줘야함..
				int member_no = snsRegister(map, social_check, email); // 회원번호 또는 0이 넘어옴
				if (member_no == 0) {
					model.addAttribute("member_no", member_no);
					result = "/user/member/findresult";
				} else {
					model.addAttribute("member_no", member_no);
					result = "/user/member/snsloginregister";
				}
			} else if (userMemberVO != null && savePath == null && currentPath != null && prevURI == null) { // 인덱스에서
																												// 소셜로그인
																												// 했을경우.
				// System.out.println("첫번째 else if");
				// sns login로그 넣어주기
				mService.snsLoginLog(userMemberVO.getMember_no());
				session.setAttribute("loginMemberInfo", userMemberVO);
				result = "redirect:/";
			} else if (userMemberVO != null && savePath == null && currentPath != null) { // 상품에서 바로 장바구니 담으려고 할때 소셜로그인
				mService.snsLoginLog(userMemberVO.getMember_no());
				session.setAttribute("loginMemberInfo", userMemberVO);
				result = "redirect:" + prevURI;
			} else if ((userMemberVO != null && savePath == null && currentPath == null)
					|| (userMemberVO != null && savePath == null && currentPath.matches("(.*)callback(.*)")
							|| (userMemberVO != null && savePath == null && !currentPath.matches("(.*)callback(.*)"))
							|| (userMemberVO != null && savePath != null && currentPath == null))
					|| (userMemberVO != null && savePath != null && currentPath != null)
					|| (userMemberVO != null && savePath != null && currentPath.matches("(.*)callback(.*)"))
					|| (userMemberVO != null && savePath != null && !currentPath.matches("(.*)callback(.*)"))
					|| (userMemberVO != null && !savePath.matches("(.*)callback(.*)") && currentPath == null)
					|| (userMemberVO != null && !savePath.matches("(.*)callback(.*)") && currentPath != null)
					|| (userMemberVO != null && !savePath.matches("(.*)callback(.*)")
							&& currentPath.matches("(.*)callback(.*)"))
					|| (userMemberVO != null && !savePath.matches("(.*)callback(.*)")
							&& !currentPath.matches("(.*)callback(.*)"))
					|| (userMemberVO != null && savePath.matches("(.*)callback(.*)") && currentPath == null)
					|| (userMemberVO != null && savePath.matches("(.*)callback(.*)") && currentPath != null)
					|| (userMemberVO != null && savePath.matches("(.*)callback(.*)")
							&& currentPath.matches("(.*)callback(.*)"))
					|| (userMemberVO != null && savePath.matches("(.*)callback(.*)")
							&& !currentPath.matches("(.*)callback(.*)"))) {
				mService.snsLoginLog(userMemberVO.getMember_no());
				session.setAttribute("loginMemberInfo", userMemberVO);
				result = "redirect:/";
			} else if (userMemberVO != null && !prevURI.matches("(.*)callback(.*)") && prevURI != null
					&& prevURI == "") {
				// System.out.println("prev의 경우");
				mService.snsLoginLog(userMemberVO.getMember_no());
				session.setAttribute("loginMemberInfo", userMemberVO);
				result = prevURI;
			} else {
				// System.out.println("else의 경우");
				mService.snsLoginLog(userMemberVO.getMember_no());
				session.setAttribute("loginMemberInfo", userMemberVO);
				result = prevURI;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	// --------------------------sns유저
	// 회원가입-----------------------------------------------
	public int snsRegister(Map<String, String> map, String social_check, String email) { // 맵에는 snslogin시 처음 받아오는 데이터가
																							// 들어있음..

		int tmpmember_no = 0;
		try {

			if (mService.findMemberEmailBySocial(email)) { // sns 로그인 회원가입 전에 데이터가 있다면
				tmpmember_no = 0;
			} else {
				tmpmember_no = mService.snsloginRegister(map, social_check); // 회원가입 진행후 member_no 가져오기
			}
			// System.out.println("snsRegister - member_no : " + tmpmember_no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmpmember_no;

	}

	// ---------------sns 유저 추가정보 받는곳.----------------------------------
	@RequestMapping(value = "/user/member/addinfosnsloginregister", method = RequestMethod.POST)
	public String addsnsInfo(@RequestParam("member_id") String member_id, @RequestParam("member_no") int member_no,
			@RequestParam("member_name") String member_name, @RequestParam("phone_number") String phone_number,
			@RequestParam("zip_code") String zip_code, @RequestParam("address") String tmpaddress,
			@RequestParam("detailaddress") String detailAddress, @RequestParam("gender") String gender,
			@RequestParam("birth_date") String birth_date, HttpServletRequest request, HttpSession session,
			Model model) {

		String address = tmpaddress + detailAddress;

		// 현재 요청한 주소
		String currentPath = request.getRequestURI(); // 현재요청한 경로
		request.getSession().setAttribute("addsnsInfo - currentPath sns : ", currentPath); // 요청한 주소 저장
		String savePath = (String) request.getSession().getAttribute("currentPath"); // 요청한 주소 가져오기

		// 이전경로
		String referer = request.getHeader("Referer"); // 이전경로
		// System.out.println("addsnsInfo - referer"+referer);

		// System.out.println("sns로그인 추가 사항.;");
		UserMemberVO snsloginVO = new UserMemberVO();

		try {
			snsloginVO = mService.addSnsLoginInfo(member_no, member_id, member_name, phone_number, zip_code, address,
					gender, birth_date);// 추가사항 insert
			// System.out.println("SnsCallback 353번줄 = " + snsloginVO);

			if (mService.insertLoginLog(member_no)) { // 로그인 로그.(안들어가졌음..) 따라서 주소도 입력이 안되는것 같음..

				// System.out.println("여기로 넘어나요...");

				UserMemberVO tmpMemberVO = new UserMemberVO();
				tmpMemberVO.setMember_no(member_no);
				tmpMemberVO.setAddress(address);
				tmpMemberVO.setZip_code(zip_code);
				tmpMemberVO.setMember_name(member_name);
				tmpMemberVO.setPhone_number(phone_number);

				// System.out.println("------------------------------------callback : " +
				// tmpMemberVO);
//								mService.insertSnsMemberAddress(tmpMemberVO); 	//addressbook 기본 배송지 주소 넣어주는곳

			}
			// System.out.println("/user/member/addinfosnsloginregister의 snsloginVO : " +
			// snsloginVO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (referer == null || referer.matches("(.*)callback(.*)")) { // callback이 있다면
			try {

				session.setAttribute("loginMemberInfo", snsloginVO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/";
		}

		return "/user/index";
	}
}
