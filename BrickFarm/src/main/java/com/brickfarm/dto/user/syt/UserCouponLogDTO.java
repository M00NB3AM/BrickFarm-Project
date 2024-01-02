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
public class UserCouponLogDTO {
	private String merchant_uid;
	private int coupon_held_no;
	private String usage_reason;
}
