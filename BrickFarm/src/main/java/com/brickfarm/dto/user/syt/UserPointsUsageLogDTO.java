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
public class UserPointsUsageLogDTO {
	private String merchant_uid;
	private int member_no;
	private int usage_amount;
	private String reason;
}
