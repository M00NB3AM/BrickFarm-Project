package com.brickfarm.vo.admin.syt;

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
public class AdminOrderVO {
	private int detailed_order_no;
	private String merchant_uid;
	private String member_id;
	private String member_name;
	
	private String product_name;
	private String product_main_image;
	private String total_discounted_price;
	
	private Timestamp order_day;
	private Timestamp deposit_time;

	private String delivery_state;
	private Timestamp delivery_waiting_date;
	
	private String state;
	private Timestamp complete_date;
	
	private String post_no;
}
