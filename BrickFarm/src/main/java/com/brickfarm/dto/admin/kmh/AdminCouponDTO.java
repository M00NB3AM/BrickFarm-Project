package com.brickfarm.dto.admin.kmh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AdminCouponDTO {
	
	private String member_grade_name;
	private String coupon_name;
	private String coupon_discription;
	private Float discount_rate;
	private int validity_period;
	
	public void setDiscount_rate(int discount_rate) {
		this.discount_rate = discount_rate /100f;
	}

	public void setMember_grade_name(String member_grade_name) {
		this.member_grade_name = member_grade_name;
	}

	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}

	public void setCoupon_discription(String coupon_discription) {
		this.coupon_discription = coupon_discription;
	}	
	
	public void setValidity_period(int validity_period) {
		this.validity_period = validity_period;
	}


}
