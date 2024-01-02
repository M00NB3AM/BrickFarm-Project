package com.brickfarm.vo.user.psj;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCancelAndExchangeApplyVO {
	private int detailed_order_no;
	private String merchant_uid;
	private int quantity;
	private String payment_state;
	private String product_name;
	private int product_price;
	private int discount_price;
	private int discounted_price;
	private String product_main_image;
	private String imp_uid;
	private Date deposit_time;
	private String card_brand;
	private String depositor_name;
	private int total_pay_money;
	private int card_pay_money;
	private int cash_pay_money;
	private int point_pay_money;
	private int cancel_money;
}
