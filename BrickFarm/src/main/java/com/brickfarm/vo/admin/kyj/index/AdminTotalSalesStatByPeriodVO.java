package com.brickfarm.vo.admin.kyj.index;

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
public class AdminTotalSalesStatByPeriodVO {
	private int total_sales_amount;
	private int total_return_amount;
	private int total_post_money;
	private int total_usage_amount;

	private float total_sales_amount_avarage;
	private float total_return_amount_avarage;
	private int total_net_sales;

	public void setTotal_sales_amount(int total_sales_amount, int period) {
		this.total_sales_amount = total_sales_amount;
		this.total_sales_amount_avarage = total_sales_amount / period;
	}

	public void setTotal_return_amount(int total_return_amount, int period) {
		this.total_return_amount = total_return_amount;
		this.total_return_amount_avarage = total_return_amount / period;
	}

	public void netSalesCalc() {
		this.total_net_sales = this.total_sales_amount - this.total_return_amount - this.total_post_money
				- this.total_usage_amount;
	}
}
