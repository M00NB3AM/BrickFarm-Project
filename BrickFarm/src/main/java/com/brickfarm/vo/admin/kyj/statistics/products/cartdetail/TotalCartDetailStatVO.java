package com.brickfarm.vo.admin.kyj.statistics.products.cartdetail;

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
public class TotalCartDetailStatVO {
	private String member_id;
	private String member_name;
	private String product_code;
	private String product_name;
	private int product_price;
	private int quantity;
	private int current_stock_quantity;
}
