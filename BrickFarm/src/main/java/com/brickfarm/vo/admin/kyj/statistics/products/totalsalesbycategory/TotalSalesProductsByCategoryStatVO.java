package com.brickfarm.vo.admin.kyj.statistics.products.totalsalesbycategory;

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
public class TotalSalesProductsByCategoryStatVO {
	private int large_category_no;
	private String large_category_name;
	private int medium_category_no;
	private String medium_category_name;
	private int small_category_no;
	private String small_category_name;
	private int order_count;
	private int return_quantity;
	private int sales_quantity;
	private int sum_total_price;
}
