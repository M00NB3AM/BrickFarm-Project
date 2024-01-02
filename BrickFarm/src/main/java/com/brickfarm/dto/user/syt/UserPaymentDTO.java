package com.brickfarm.dto.user.syt;

import java.sql.Timestamp;

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
public class UserPaymentDTO {
	private String imp_uid;
	private String merchant_uid;
	private int total_pay_money;
	private int total_discounted_price;
	private int post_money;
	private int card_pay_money;
	private int cash_pay_money;
	private int point_pay_money;
	private int cancel_money;
	private Timestamp deposit_time;
	private String card_brand;
	private String card_number;
	private String depositor_name;
	private String virtual_account_bank_brand;
	private String virtual_account_number;
	private Timestamp deposit_deadline;
}
