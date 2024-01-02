package com.brickfarm.vo.admin.kyj.statistics.products.totalcanceled;

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
public class TotalCanceledProductsStatVO {
	private String product_code;
	private String product_name;
	private int product_price;
	private int discounted_price;
	private int current_stock_quantity;
	private int paid_order_count;
	private int paid_quantity;
	private int sales_order_count;
	private int sales_quantity;
	private int canceled_order_count;
	private int return_quantity;
	private float return_quantity_ratio;
	private int sum_sales_amount;
}
