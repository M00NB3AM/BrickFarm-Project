package com.brickfarm.vo.admin.kmh;

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
public class AdminCouponVo {
	private int coupon_policy_no;
	private String coupon_policy_name;
	private String member_grade_name  = "all";
	private String coupon_discription;
	private Float discount_rate;
	private int validity_period;
	private String status;
}
