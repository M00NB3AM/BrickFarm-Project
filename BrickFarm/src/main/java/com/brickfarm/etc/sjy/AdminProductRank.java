package com.brickfarm.etc.sjy;

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
public class AdminProductRank {
	private String product_code;
	private String product_name;
	private int product_price;
	private String product_main_image;
	private int ranking;
}
