package com.brickfarm.vo.admin.sjy;

import java.sql.Timestamp;

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
public class AdminEventVO {
	private int event_no;
	private String event_name;
	private Timestamp event_start;
	private Timestamp event_end;
	private Float discount_rate;
}
