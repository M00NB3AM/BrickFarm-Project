package com.brickfarm.vo.admin.syt;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrderDetailVO {
	private int detailed_order_no;
	private String product_name;
	private String product_main_image;
	private String product_price;
	private String event_product_price;
	private String discounted_price;
	private int quantity;
	private String total_state;
	private Timestamp total_complete_date;
	private String memo;
}
