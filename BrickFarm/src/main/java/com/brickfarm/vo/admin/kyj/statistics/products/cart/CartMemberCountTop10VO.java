package com.brickfarm.vo.admin.kyj.statistics.products.cart;

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
public class CartMemberCountTop10VO {
	private String product_code;
	private String product_name;
	private int member_count;
}
