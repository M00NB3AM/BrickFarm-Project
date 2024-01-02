package com.brickfarm.vo.admin.sjy;

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
public class AdminStockVO {
	private int stock_no;
	private String product_code;
	private String is_auto_order;
	private int safety_stock_quantity;
	private int stock_quantity;
}
