package com.brickfarm.vo.admin.kyj.statistics.products.totalcanceled;

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
public class TotalCanceledQuantityTop10VO {
	private String product_code;
	private String product_name;
	private int canceled_order_count;
	private int return_quantity;
}
