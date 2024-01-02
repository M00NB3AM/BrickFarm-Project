package com.brickfarm.dto.user.syt;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserCouponHeldDTO {
	private int coupon_held_no;
	private int coupon_policy_no;
	private int member_no;
	private Timestamp published_date;
	private Timestamp expiration_date;
	private String available_coupon;
	private float discount_rate;
	private String coupon_policy_name;
}
