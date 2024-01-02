package com.brickfarm.vo.admin.syt;

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
public class AdminSchedulerPaymentDataVO {
	
	private String imp_uid;
	private String merchant_uid;
	private int point_pay_money;
	private int member_no;
	private int coupon_held_no;
}
