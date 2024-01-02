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
public class UserPointsUsageLogVO {
	private int points_usage_log_no;
	private String merchant_uid;
	private int member_no;
	private Date usage_date;
	private int usage_amount;
	private int total_usage_amount;
}
