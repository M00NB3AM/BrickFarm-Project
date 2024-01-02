package com.brickfarm.dto.user.psj;

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
public class UserWithdrawalApplyDTO {
	private String merchant_uid;
	private int detailed_order_no;
	private String product_code;
	private int product_price;
	private int discounted_price;
	private int quantity;
	private String product_name;
	private String product_main_image;
	private String bank_brand;
	private String refund_account;
	private String payment_state;
	private String recipient;
	private String recipient_zip_code;
	private String recipient_address;
	private String recipient_phone;
	private int total_pay_money;
	private int total_discounted_price;
	private int post_money;
	private int point_pay_money;
}
