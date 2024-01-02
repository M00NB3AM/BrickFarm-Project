package com.brickfarm.vo.admin.syt;

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
public class AdminDeliveryVO {
	private String merchant_uid;
	private String post_no;
	private Timestamp order_day;
	private String delivery_state;
	private Timestamp delivery_waiting_date;
	private String total_state;
	private Timestamp total_complete_date;
	private String total_pay_money;
	
	private String member_id;
	private String member_name;
	private String memo;
	
	private Timestamp deposit_time;
	
}
