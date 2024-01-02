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
public class AdminDeliveryDetailVO {
	private int detailed_order_no;
	private String discounted_price;
	private String product_main_image;
	private int quantity;
	private String state;
	
	private String product_name;
	private Timestamp complete_date;
	private String recipient_address;
	
}
