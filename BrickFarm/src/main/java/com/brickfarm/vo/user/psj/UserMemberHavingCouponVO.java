package com.brickfarm.vo.user.psj;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserMemberHavingCouponVO {
	// 쿠폰 발행일
	private Date published_date;
	// 쿠폰 만료일
	private Date expiration_date;	
	// 쿠폰 사용 가능 여부
	private char available_coupon;
	// 쿠폰 이름
	private String coupon_policy_name;
	// 쿠폰 설명
	private String coupon_discription;
	// 쿠폰 할인율
	private float discount_rate;
	// 쿠폰 유효 기간 
	private int validity_period;
	// 쿠폰 사용 날짜
	private Date usage_date;
	// 쿠폰 사용 이유 (사용 or 만료)
	private String usage_reason;
}
