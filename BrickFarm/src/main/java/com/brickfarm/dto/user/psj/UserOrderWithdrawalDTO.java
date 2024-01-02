package com.brickfarm.dto.user.psj;

import java.sql.Date;

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
public class UserOrderWithdrawalDTO {
	private String merchant_uid; 
	private Date order_day; 
	private String recipient;
	private String recipient_address;
	private String recipient_zip_code;
	private String recipient_phone;
	private int detailed_order_no;
	private String bank_brand;
	private String refund_account;
	private int product_price;
	private int discounted_price;
	private int quantity;
	private String payment_state;
	private Date order_detailed_complete_date;
	private String product_name;
	private String product_main_image;
	private int exchange_no; 
	private String exchange_state;
	private String  exchange_post_number;
	private Date exchange_application_date;
	private Date exchange_check_date;
	private Date exchange_process_date;
	private Date exchange_complete_date;
	private int cancellation_return_no;
	private int cancel_cancel_money;
	private String cancel_what;
	private String cancel_state;
	private int add_post_money;
	private Date cancel_application_date;
	private Date cancel_check_date;
	private Date cancel_complete_date;
}
