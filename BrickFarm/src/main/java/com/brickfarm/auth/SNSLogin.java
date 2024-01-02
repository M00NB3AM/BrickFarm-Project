package com.brickfarm.auth;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.brickfarm.vo.user.ysh.UserMemberVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class SNSLogin {
	private OAuth20Service oauthService;
	private SnsValue sns;

	public SNSLogin(SnsValue sns) {
		this.oauthService = new ServiceBuilder(sns.getClientId()).apiSecret(sns.getClientSecret())
				.callback(sns.getRedirectUrl()).scope("profile").build(sns.getApi20Instance());
		this.sns = sns;
		// System.out.println("SNS : " + sns); // service가 구글인지 아닌지 판별

	}

	// 카카로 로그인 호출
	public String getKakaoAuthURL() {

		return this.oauthService.getAuthorizationUrl();
	}

	public UserMemberVO getKakaoProfile(String code) throws IOException, InterruptedException, ExecutionException {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		OAuthRequest request = new OAuthRequest(Verb.GET, this.sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
//		Response response = oauthService.execute(request);
		return null;
//		return kakaoparseJson(response.getBody(), accessToken); 
	}

// ------------------------------------ 네이버 --------------------------------------------------------------

	public String getNaverAuthURL() {// 네이버 로그인 호출

		return this.oauthService.getAuthorizationUrl();
	}

	// 네이버 로그인
	public UserMemberVO getNaverProfile(String code) throws IOException, InterruptedException, ExecutionException {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		OAuthRequest request = new OAuthRequest(Verb.GET, this.sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
		Response response = oauthService.execute(request);
		return naverparseJson(response.getBody(), accessToken);
	}

	private UserMemberVO naverparseJson(String body, OAuth2AccessToken accessToken)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(body);
		UserMemberVO naverVO = new UserMemberVO();
		// System.out.println(accessToken);

		if (this.sns.isNaver()) {
			// System.out.println("네이버 로그인 지역 jsonparse");
			// System.out.println("rootNode : " + rootNode);

			// social check
			String social_check = "";
			if (rootNode.findValue("response").has("id")) {
				social_check = "naver_" + rootNode.findValue("response").findValue("id").asText();
				// System.out.println(social_check);
			} else if (!rootNode.findValue("response").has("id")) {
				social_check = null;
			}
			// gender
			String gender = "";
			if (rootNode.findValue("response").has("gender")) {
				gender = rootNode.findValue("response").findValue("gender").asText();
				// System.out.println(gender);
			} else if (!rootNode.findValue("response").has("gender")) {
				gender = null;
			}

			// email
			String email = "";
			if (rootNode.findValue("response").has("email")) {
				email = rootNode.findValue("response").findValue("email").asText();
			} else if (!rootNode.findValue("response").has("email")) {
				email = null;
			}

			// mobile
			String phone_number = "";
			if (rootNode.findValue("response").has("mobile")) {
				phone_number = rootNode.findValue("response").findValue("mobile").asText();
			} else if (!rootNode.findValue("response").has("mobile")) {
				phone_number = null;
			}
			// member_name
			String member_name = "";
			if (rootNode.findValue("response").has("name")) {
				member_name = rootNode.findValue("response").findValue("name").asText();
			} else if (!rootNode.findValue("response").has("name")) {
				member_name = null;

			}

			naverVO.setSocial_check(social_check);
			naverVO.setGender(gender);
			naverVO.setEmail(email);
			naverVO.setPhone_number(phone_number);
			naverVO.setMember_name(member_name);
			naverVO.setMember_grade_name("일반");
			naverVO.setInactive_status('N');
			naverVO.setEnabled('1');
			naverVO.setAuthority("ROLE_MEMBER");

			naverVO.setAccess_token(accessToken.toString());

		}

		return naverVO;
	}
//	------------------------------------- 네이버 끝--------------------------------------------------------------

	// 구글 로그인시.
	public UserMemberVO getUserProfile(String code) throws Exception {
		OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
		OAuthRequest request = new OAuthRequest(Verb.GET, this.sns.getProfileUrl());
		oauthService.signRequest(accessToken, request);
		Response response = oauthService.execute(request);
		// System.out.println("SNSLogin : getUserProfile");
		// 응답 받은건 json으로 온다.
		return googleparseJson(response.getBody(), accessToken);
	}

	private UserMemberVO googleparseJson(String body, OAuth2AccessToken accessToken)
			throws JsonMappingException, JsonProcessingException {
		// System.out.println("SNSLogin : parseJson");

		UserMemberVO googleVO = new UserMemberVO();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(body);

		// 구글 로그인 했을 경우
		if (this.sns.isGoogle()) {
			// System.out.println("구글 로그인 지역 jsonparse");
			// System.out.println("rootNode : " + rootNode);

			// 유저의 아이디
			String id = "";
			if (rootNode.has("resourceName")) {
				id = rootNode.findValue("resourceName").asText().substring(7);
			} else if (!rootNode.has("resourceName")) {
				id = null;
			}
			// System.out.println("id : " + id);

			// 유저의 이름
//			String displayName = "";
//			if (rootNode.has("names")) {
//				displayName = rootNode.findValue("names").get(0).get("displayName").asText();
//			} else if (!rootNode.has("names")) {
//				displayName = null;
//			}
			// System.out.println("displayName = " + displayName);

			// 성별
			String gender = "";
			if (rootNode.has("genders")) {
				gender = rootNode.findValue("genders").get(0).get("value").asText();
			} else if (!rootNode.has("genders")) {
				gender = null;
			}
			// System.out.println("gender : " + gender);

			// 이메일
			String email = "";
			if (rootNode.has("emailAddresses")) { // 있다면
				email = rootNode.findValue("emailAddresses").get(0).get("value").asText();
			} else if (!rootNode.has("emailAddresses")) { // 없다면
				// System.out.println("null이여도 괜찮아요.");
			}
			// System.out.println("이메일 : " + email);

			// 전화번호
			String phoneNumber = "";
			if (rootNode.has("phoneNumbers")) {
				phoneNumber = rootNode.findValue("phoneNumbers").get(0).get("value").asText();
			} else if (!rootNode.has("phoneNumbers")) {
				phoneNumber = null;
			}
			// System.out.println("핸드폰 번호 : " + phoneNumber);

			// 파싱한 데이터 member객체에 넣어주기.
			String birth = null;
			// System.out.println(googleVO);
			googleVO.setPhone_number(phoneNumber);
			googleVO.setEmail(email);
			googleVO.setBirth_date(birth);
			googleVO.setSocial_check("google_" + id);

			if (gender.equals("male")) {
				googleVO.setGender("M");
			} else if (gender.equals("female")) {
				googleVO.setGender("F");
			}
			Calendar cal = Calendar.getInstance();
			int calYear = cal.get(Calendar.YEAR);
			int calMonth = cal.get(Calendar.MONTH);
			int calDay = cal.get(Calendar.DATE);
			cal.set(calYear, calMonth, calDay);
			SimpleDateFormat tmp = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
			String dateStr = tmp.format(cal.getTime());

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA);
			LocalDate localDate = LocalDate.parse(dateStr, formatter);
			// System.out.println("날짜 : " + localDate);

			Timestamp registDate = Timestamp.valueOf(localDate.atStartOfDay());
//				Timestamp registDate = (Timestamp) Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			// System.out.println(registDate);
			googleVO.setMember_regist_date(registDate);
			googleVO.setInactive_status('N');
			googleVO.setEnabled('1');
			googleVO.setAuthority("ROLE_MEMBER");
			googleVO.setAccess_token(accessToken.toString());
			googleVO.setMember_grade_name("일반");
		}
		return googleVO;
	}

}
