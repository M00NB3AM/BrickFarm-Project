package com.brickfarm.dto.user.psj;

import java.sql.Date;
import java.time.LocalDate;

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
public class UserOrderListDTO {
	// 주문번호
	private String merchant_uid;
	// 주문 총 가격
	private int total_pay_money;
	// 주문 일 
	private Date order_day;
	// 배송 대기 중 변경 날짜
	private Date delivery_waiting_date;
	// 배송 완료 일 
	private LocalDate delivery_done_date;
	// 주문의 총 상품 갯수
	private int total_quantity;
	// 주문 상품 대표 이미지
	private String product_main_image;
	// 주문 상품 대표 이름
	private String product_name;
	// 주문 배송 상태
	private String delivery_state;
	
	private int point_pay_money;
}
