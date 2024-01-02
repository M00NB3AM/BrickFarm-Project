package com.brickfarm.vo.user.psj;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartVO {
	private int member_no;
	private int shopping_cart_no;
	private String product_code;
	private int quantity;
	private String product_name;
	private int product_price;
	private	int parts_quantity;
	private String recommend_age;
	private String product_main_image;
	private int upper_category_no;
	private String product_category_name;
	private int stock_quantity;
	private int event_no;
	private String event_name;
	private Date event_start; 
	private Date event_end; 
	private float discount_rate;
}
