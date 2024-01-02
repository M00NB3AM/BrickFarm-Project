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
public class UserPointsAccrualLogVO {
	private int points_accrual_log_no;
	private int points_accrual_policy_no;
	private int member_no;
	private String merchant_uid;
	private Date accrual_date;
	private int accrual_log_amount;
	private String points_accrual_reason;
	
}
