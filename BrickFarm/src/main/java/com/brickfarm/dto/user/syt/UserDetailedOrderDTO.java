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
public class UserDetailedOrderDTO {
	private int detailed_order_no;
	private String merchant_uid;
	private String product_code;
	private int product_price;
	private int discounted_price;
	private int quantity;
	private String payment_state;
}
