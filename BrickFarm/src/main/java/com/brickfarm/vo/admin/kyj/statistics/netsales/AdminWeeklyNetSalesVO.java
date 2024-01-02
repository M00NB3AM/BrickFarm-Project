package com.brickfarm.vo.admin.kyj.statistics.netsales;

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
public class AdminWeeklyNetSalesVO {
	private int complete_week;
	private int confirmed_week;
	private Timestamp confirmed_start_date;
	private Timestamp confirmed_end_date;
	private int confirmed_order_count;
	private int confirmed_product_count;
	private int sum_product_price;
	private int sum_discounted_price;
	private int sum_point_usage_amount;
	private int sum_payment;
	private int canceled_week;
	private Timestamp canceled_start_date;
	private Timestamp canceled_end_date;
	private int canceled_order_count;
	private int canceled_product_count;
	private int sum_refund_amount;
	private int total_net_sales;
}
