package com.brickfarm.vo.admin.sjy;

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
public class AdminEventLogVO {
	private int event_log_no;
	private String product_code;
	private int event_no;
}
