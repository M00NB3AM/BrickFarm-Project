package com.brickfarm.vo.admin.kyj.statistics.members;

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
public class StatByMemberGradeVO {
	private String member_grade_name;
	private int member_count;
	private int total_decided_order_count;
	private int confirmed_product_quantity;
	private int total_confirmed_price;
	private int canceled_product_quantity;
	private int total_canceled_price;
}
