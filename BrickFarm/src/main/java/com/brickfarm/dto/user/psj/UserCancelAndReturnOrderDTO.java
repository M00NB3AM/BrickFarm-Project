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
public class UserCancelAndReturnOrderDTO {
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
	private String cancel_reason;
    private String cancel_state;
	private Date cancel_application_date;
	private Date cancel_check_date;
	private Date cancel_complete_date;
	private int cancel_money;
	private String what;
	private String negligence;
	private int post_money;
	private int total_pay_money;
	private int total_discounted_price;
	private int point_pay_money;
	
}
