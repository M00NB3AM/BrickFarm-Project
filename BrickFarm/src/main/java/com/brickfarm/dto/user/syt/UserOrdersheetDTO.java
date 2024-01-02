package com.brickfarm.dto.user.syt;

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
public class UserOrdersheetDTO {
	private String merchant_uid;
	private int member_no;
	private int coupon_held_no;
	private Timestamp order_day;
	private String post_no;
	private String recipient;
	private String recipient_zip_code;
	private String recipient_address;
	private String recipient_phone;
	private String memo;
	private String delivery_state;
	private String bank_brand;
	private String refund_account;
	private int total_product_price;
	private int total_pay_money;
	private Timestamp delivery_waiting_date;
	private String total_state;
	private Timestamp complete_date;
}
