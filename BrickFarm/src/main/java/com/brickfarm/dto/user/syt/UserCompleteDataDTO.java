package com.brickfarm.dto.user.syt;

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
public class UserCompleteDataDTO {
	private int pay_money;
	private String paymentType;
	private String make_address;
	private float discount_rate;
	private String recipient_detaile_address;
}
