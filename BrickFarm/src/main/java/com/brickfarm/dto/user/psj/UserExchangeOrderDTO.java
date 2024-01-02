package com.brickfarm.dto.user.psj;

import java.sql.Date;

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
public class UserExchangeOrderDTO {
	private String merchant_uid;
	private int detailed_order_no; 
	private int discounted_price; 
	private int quantity; 
	private String payment_state; 
	private Date complete_date;
    private int product_price;
    private String product_code;
	private String product_name; 
	private String product_main_image;
	private String exchange_reason;
	private String exchange_post_number;
    private String exchange_state;
	private Date exchange_application_date;
	private Date exchange_check_date;
	private Date exchange_process_date;
	private Date exchange_complete_date;
}
