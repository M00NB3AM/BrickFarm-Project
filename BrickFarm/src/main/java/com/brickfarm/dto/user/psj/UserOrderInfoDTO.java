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
public class UserOrderInfoDTO {
		
		// ordersheet 테이블
		// 주문번호
		private String merchant_uid;
		// 주문 일
		private Date order_day;
		// 배송 대기 중 변경 날짜
		private Date delivery_waiting_date;
		// 배송 완료 일 
		private LocalDate delivery_done_date;
		// 주문자 송장번호
		private String post_no;
		// 수신자
		private String recipient;
		// 수신자 전화번호
		private String recipient_phone;
		// 수신지
		private String recipient_address;
		// 수신지 우편 번호
		private String recipient_zip_code;
		// 환불 받을 계좌
		private String refund_account; 
		// 주문 배송 상태
		private String delivery_state;
		// 주문 총 상품 금액
		private int total_product_price; 
		// 주문 총 할인 금액
		private int total_discount_price; 
		// 최종 실 결제 금액(가격 - 쿠폰할인금액) + 배송비
		private int total_pay_money;  
		// 주문의 최종 상태
		private String total_state;
		// 최종 상태의 완료 변경일
		private Date total_complete_date; 
		
		/*
		// detailed_order 테이블
		// 상세 주문 번호
		private int detailed_order_no;
		// 상세 주문 쿠폰 할인 금액
		private int discount_price;
		// 상세 주문 할인 적용된 금액
		private int discounted_price;
		// 상세 주문 수량 
		private int quantity;
		// 상세 주문 결제 상태
		private int payment_state;
		// 상세 주문 구매확정 일자
		private Date order_detailed_complete_date;
		
		// product 테이블
		// 상품 이름
		private String product_name;
		// 상품 이미지
		private String product_main_image;
		*/
		
		// payment 테이블
		// 카드 결제 금액
		private int card_pay_money;
		// 카드 회사 명
		private String card_brand;
		// 카드 번호
		private String card_number; 
		private int cash_pay_money;
		private String depositor_name;
		private Date deposit_time;
		private String virtual_account_bank_brand;
		private String virtual_account_number;
		private Date deposit_deadline;
		private int post_money;
		private int point_pay_money;
		private int cancel_money;
		
}
