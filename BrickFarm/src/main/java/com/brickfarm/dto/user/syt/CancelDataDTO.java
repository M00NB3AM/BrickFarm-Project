package com.brickfarm.dto.user.syt;

import java.math.BigDecimal;

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
public class CancelDataDTO {
	private String imp_uid;
	private BigDecimal cancel_request_amount;
	private String reason;
	private BigDecimal checksum;
}
