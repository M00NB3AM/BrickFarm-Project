package com.brickfarm.vo.admin.kyj.statistics.dashboard;

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
public class AdminNetSalesByPeriodVO {
	private int recent_period_average;
	private int last_period;
	private int this_period;
}
