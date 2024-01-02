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
public class UserOrderWithdrawalListDTO {
	private int detailed_order_no;
	private int product_price;
	private int quantity;
	private String payment_state;
	private Date order_detailed_complete_date;
	private int discounted_price;
	private String product_name;
	private String product_main_image;
	private int exchange_no;
	private Date exchange_application_date;
	private Date exchange_check_date;
	private Date exchange_process_date;
	private Date exchange_complete_date;
	private String exchange_state;
	private int cancellation_return_no;
	private String cancel_state;
	private Date cancel_application_date;
	private Date cancel_check_date;
	private Date cancel_complete_date;
}
