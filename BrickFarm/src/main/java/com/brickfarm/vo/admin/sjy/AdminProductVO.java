package com.brickfarm.vo.admin.sjy;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminProductVO {
	private String product_code;
	private int product_category_no;
	private String product_name;
	private int product_price;
	private String display;
	private String is_new;
	private int event_no;
	private Timestamp product_regist_date;
	private String product_description;
	private int parts_quantity;
	private String recommend_age;
	private String is_auto_order;
	private int safety_stock_quantity;
	private int stock_quantity;
	private String product_main_image;
	private String product_short_description;
	private int stock_price;
}
