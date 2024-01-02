package com.brickfarm.dto.user.syt;

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
public class UserOrderProductDTO {
	private String product_code;
	private String product_name;
	private String product_price;
	private String event_product_price;
	private int quantity;
	private String product_main_image;
	private int event_discount_rate;
}
