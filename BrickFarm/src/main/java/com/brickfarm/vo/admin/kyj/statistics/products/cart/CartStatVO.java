package com.brickfarm.vo.admin.kyj.statistics.products.cart;

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
public class CartStatVO {
	private String product_code;
	private String product_name;
	private int product_price;
	private int sum_cart_quantity;
	private int today_payment_count;
	private int current_stock_quantity;
	private int regi_member_count;
}
