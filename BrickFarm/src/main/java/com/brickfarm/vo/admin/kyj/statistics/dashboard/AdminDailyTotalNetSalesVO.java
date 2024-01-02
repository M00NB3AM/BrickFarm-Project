package com.brickfarm.vo.admin.kyj.statistics.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminDailyTotalNetSalesVO {
	private int daily_net_sales;
	private int daily_refund_amount;
	private int daliy_payment_count;
	private int daily_cancel_exchange_count;
	private int daily_delivery_waiting_count;
	private int daily_delivery_complete_count;
}