package com.brickfarm.vo.admin.kyj.statistics.products.totalsales;

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
public class TotalSalesProductsStatVO {
	private String product_code;
	private String product_name;
	private int order_count;
	private int product_price;
	private int current_stock_quantity;
	private int return_quantity;
	private int sales_quantity;
	private int sum_total_price;
}
