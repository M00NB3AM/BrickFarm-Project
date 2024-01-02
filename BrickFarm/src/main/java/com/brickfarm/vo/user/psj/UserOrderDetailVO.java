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
public class UserOrderDetailVO {
	// 주문번호
	private String merchant_uid;
	// 물품 별 상세 주문 번호
	private int detailed_order_no;
	// 주문 일
	private Date order_day;
	// 주문자 송장번호
	private String ordersheet_post_no;
	// 수신자
	private String recipient;
	// 수신지
	private String recipient_address;
	// 수신자 전화번호
	private String recipient_phone;
	// 배송 상태
	private String delivery_state;
	// 총 물품 가격
	private int total_product_price;
	// 할인 적용된 가격
	private int total_discounted_price;
	// 각 상품 가격
	private int detail_price;
	// 각 상품 이름
	private String product_name;
	// 각 상품 이미지
	private String product_main_image;
	// 각 상품 갯수
	private int quantity;
	// 결제 상태
	private String payment_state;
	// 완료 날짜
	private Date order_detailed_complete_date;
	// 각 상품 가격
	private int product_price;
	// 배송비
	private int post_money;
	// 카드 결제 금액
	private int card_pay_money;
	// 현금 결제 금액
	private int cash_pay_money;
	// 포인트 결제 금액
	private int point_pay_money;
	// 환불 금액 
	private int pay_cancel_money;
	// 사용된 쿠폰 이름
	private String coupon_policy_name;
	// 사용된 쿠폰 할인율
	private float discount_rate;
	// 구매자 등급 
	private String member_grade_name;
	// 교환 번호
	private int exchange_no;
	// 교환 운송장 번호
	private String exchange_post_number; 
	// 교환 신청 일
	private Date exchange_application_date;
	// 교환 신청 확인 일
	private Date exchange_check_date;
	// 교환 신청 진행일
	private Date exchange_process_date;
	// 교환 완료일
	private Date exchange_complete_date;
	// 교환 상태
	private String exchange_state;
	// 취소, 반품 사유
	private String cancel_reason;
	// 취소, 반품 금액
	private int cancel_cancel_money;
	// 취소 인지 반품인지
	private String cancel_what;
	// 취소 반품 상태
	private String cancel_state;
	// 취소 반품 신청일
	private Date cancel_application_date;
	// 취소 반품 신청 확인 일
	private Date cancel_check_date;
	// 취소 반품 완료 일
	private Date cancel_complete_date;
}