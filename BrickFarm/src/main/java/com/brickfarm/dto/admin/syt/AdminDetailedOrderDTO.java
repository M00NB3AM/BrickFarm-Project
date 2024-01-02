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
public class AdminDetailedOrderDTO {
	private int detailed_order_no;
	private String depositor_name;
	private String reason;
	private String negligence;
	private int cancel_money;
}
