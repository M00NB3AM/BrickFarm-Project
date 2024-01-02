package com.brickfarm.etc.sjy;

import java.util.HashMap;

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
public class ProductSearchCondition {
	private String all;
	private String display;
	private int event_no;
	private String is_new;
	private String not_display;
	private String search_word;
	private int product_category_no;
	private String min_date;
	private String max_date;
	private int minimum_price;
	private int maximum_price;
	private int minimum_stock;
	private int maximum_stock;
	private String stock_search_type;
	private String search_type;
	private int safety_stock_quantity;
	private HashMap<String, Object> sales_status;
	private HashMap<String, Object> product_price;
	private HashMap<String, Object> stock_quantity;
	private HashMap<String, Object> product_regist_date;
}
