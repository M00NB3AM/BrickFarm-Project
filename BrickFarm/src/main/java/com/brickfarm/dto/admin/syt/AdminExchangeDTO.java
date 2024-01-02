package com.brickfarm.dto.admin.syt;

import java.sql.Timestamp;
import java.util.Date;

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
public class AdminExchangeDTO {
	private int exchange_no;
	private String state;
	private String post_number;
	private Timestamp check_date;
	private Timestamp process_date;
	private Timestamp complete_date;
}
