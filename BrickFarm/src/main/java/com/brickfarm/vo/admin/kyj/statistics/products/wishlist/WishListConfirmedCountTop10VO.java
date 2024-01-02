package com.brickfarm.vo.admin.kyj.statistics.products.wishlist;

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
public class WishListConfirmedCountTop10VO {
	private String product_code;
	private String product_name;
	private int payment_quantity;
}
