package com.brickfarm.vo.user.psj;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOrderListVO {
	// 주문번호
	private String merchant_uid;
	// 주문 총 가격
	private int total_pay_money;
	// 주문 일 
	private Date order_day;
	// 주문의 총 상품 갯수
	private int total_quantity;
	// 주문 상품 대표 이미지
	private String product_main_image;
	// 주문 상품 대표 이름
	private String product_name;
	// 주문 배송 상태
	private String delivery_state;
}
