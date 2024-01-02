package com.brickfarm.vo.admin.kyj.index;

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
public class AdminMemberAndPointsAccrualInfoBy7DaysVO {
	private Timestamp dates;
	private int regist_count;
	private int withdraw_count;
	private int total_accrual_log_amount;
}
