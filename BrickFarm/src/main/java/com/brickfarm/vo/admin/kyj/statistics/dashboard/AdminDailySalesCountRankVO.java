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
public class AdminDailySalesCountRankVO {
	private String product_code;
	private String product_name;
	private int total_order_count;
	private int total_confirmed_quantity;
}
