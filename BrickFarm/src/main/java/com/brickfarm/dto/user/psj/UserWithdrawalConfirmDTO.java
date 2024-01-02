package com.brickfarm.dto.user.psj;

import java.sql.Date;

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
public class UserWithdrawalConfirmDTO {
	private int detailed_order_no;
	private String reason;
	private int cancel_money;
	private String what;
	private String negligence;
}
