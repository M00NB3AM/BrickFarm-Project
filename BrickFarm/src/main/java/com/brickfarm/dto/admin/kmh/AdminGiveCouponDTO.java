package com.brickfarm.dto.admin.kmh;

import java.util.List;

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
public class AdminGiveCouponDTO {

	private int coupon_policy_no;
	private List<Integer> member_no;
	private String member_grade_name;
}
