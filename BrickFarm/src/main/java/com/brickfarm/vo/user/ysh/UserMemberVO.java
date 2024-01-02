package com.brickfarm.vo.user.ysh;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class UserMemberVO {
	private int member_no;
	private String member_grade_name;// 일반, 실버, 골드
	private String member_id;
	private String password;
	private String member_name;
	private String phone_number;
	private String email;
	private String birth_date;
	private String zip_code;
	private String address;
	private Timestamp member_regist_date;
	private String gender;
	private char inactive_status; // 회원 휴먼 상태
	private String access_token; // 소셜 회원 토큰
	private int accrual_amount; // 적립금
	private String social_check;// 구글인지 아닌지. google이면 google : google / naver : naver / kakao = kakao / 아무것도 아니면
								// null
	private char enabled; // default 1
	private String authority; // 회원은 기본 default값이 ROLE_MEMBER
	// 멤버 보유 쿠폰수
	private int coupon_count;
	// 주문 갯수
	private int order_count;
	// 취소 상품 갯수
	private int cancel_count;
	// 찜 상품 갯수
	private int wish_list_count;

	// 회원 아이디를 찾기 위한 생성자
	public UserMemberVO(String member_name, String phone_number) {
		this.member_name = member_name;
		this.phone_number = phone_number;
	}

	// 비밀번호를 찾기 위한 생성자.
	public UserMemberVO(String member_name, String email, String member_id, String phone_number) {
		this.member_name = member_name;
		this.email = email;
		this.member_id = member_id;
		this.phone_number = phone_number;

	}

	public UserMemberVO(int member_no, String member_id, String social_check) {
		this.member_no = member_no;
		this.member_id = member_id;
		this.social_check = social_check;
	}

	// 소셜로그인을 하더라도 유저에게 만약 정보가 있다면 회원가입폼에 넣어주고 없다면
	// 아이디, 비밀번호, 이름, 전화번호, 이메일, 생일, 주소, 상세주소, 성별 받아주기

}
