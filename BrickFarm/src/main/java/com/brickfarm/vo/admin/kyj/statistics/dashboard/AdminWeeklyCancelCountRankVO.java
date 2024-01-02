package com.brickfarm.vo.admin.kyj.statistics.dashboard;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminWeeklyCancelCountRankVO {
	private String product_code;
	private String product_name;
	private int total_cancel_count;
	private int total_cancel_quantity;
	private String payment_state;
	private Timestamp complete_date;
}
