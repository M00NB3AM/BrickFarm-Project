package com.brickfarm.dto.admin.syt;

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
public class AdminCancelReturnDTO {
	private int cancellation_return_no;
	private String state;
	private Timestamp check_date;
	private Timestamp complete_date;
	private int add_post_money;
}
