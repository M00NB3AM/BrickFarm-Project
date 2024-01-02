package com.brickfarm.vo.user.lkm;

import java.sql.Timestamp;

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
public class UserProductVO {
	private String product_code;
	private int product_category_no;
	private String product_name;
	private int product_price;
	private String display;
	private String is_new;
	private String is_event;
	private Timestamp product_regist_date;
	private String product_description;
	private int parts_quantity;
	private String recommend_age;
	private String product_main_image;
	private String product_short_description;
	private int stock_quantity;
	private int event_no;
	private Timestamp event_start;
	private Timestamp event_end;
	private float discount_rate;
	private float total_star_count;
	private int review_count;
}
