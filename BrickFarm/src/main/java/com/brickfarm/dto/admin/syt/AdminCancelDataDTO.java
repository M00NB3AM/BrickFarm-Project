package com.brickfarm.dto.admin.syt;

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
public class AdminCancelDataDTO {
	private String imp_uid;
	private int cancel_request_amount;     //요청된 취소 금액
	private String reason;
	private String what;
	private String negligence;
	private int checksum;          // 취소가능금액 ( 카드 + 현금 )
	private int pk_number;
	private int total_pay_money;    // 최종 실 결제금액 (카드+현금 + 포인트 +배송비)
	private int post_money;
	private int point_pay_money;
	private String pay_method;
	private int add_post_money;
	private int change_point_pay_money;
	private int change_cancel_amount; 
}
