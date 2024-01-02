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
public class UserOrderDTO {
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
    private int review_detailed_order_no;
}
