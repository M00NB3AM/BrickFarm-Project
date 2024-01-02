package com.brickfarm.vo.admin.kmh;

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
public class AdminDetailOrderProductsVO {
	
	private String product_name;
	private String product_main_image;
	private int quantity;
	private int product_price;
	private int discounted_price;
	private String state;
	private Timestamp complete_date;

}
