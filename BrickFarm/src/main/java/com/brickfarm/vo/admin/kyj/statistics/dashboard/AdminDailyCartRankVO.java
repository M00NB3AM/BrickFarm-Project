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
public class AdminDailyCartRankVO {
	private String product_code;
	private String product_name;
	private int total_add_count;
	private int total_cart_quantity;
}
