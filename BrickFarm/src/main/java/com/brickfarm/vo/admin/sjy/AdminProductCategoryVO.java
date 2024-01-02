package com.brickfarm.vo.admin.sjy;

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
public class AdminProductCategoryVO {
	private int product_category_no;
	private int upper_category_no;
	private String category_level;
	private String product_category_name;
}
