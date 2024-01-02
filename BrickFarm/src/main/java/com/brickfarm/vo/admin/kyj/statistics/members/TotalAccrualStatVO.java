package com.brickfarm.vo.admin.kyj.statistics.members;

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
public class TotalAccrualStatVO {
	private Timestamp log_date;
	private int total_accrual_count;
	private int total_accrual_amount;
	private int total_usage_count;
	private int total_usage_amount;
	private int balance;
}
