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
public class StatByDaynameVO {
	private String day;
	private int login_count;
	private int regist_count;
	private int confirmed_product_quantity;
	private int total_confirmed_price;
	private int canceled_product_quantity;
	private int total_canceled_price;
}
