package com.brickfarm.vo.admin.kyj.statistics.netsales;

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
public class AdminMonthlyNetSalesVO {
	private String complete_date;
	private String confirmed_date;
	private int confirmed_order_count;
	private int confirmed_product_count;
	private int sum_product_price;
	private int sum_discounted_price;
	private int sum_point_usage_amount;
	private int sum_payment;
	private String canceled_date;
	private int canceled_order_count;
	private int canceled_product_count;
	private int sum_refund_amount;
	private int total_net_sales;
}
