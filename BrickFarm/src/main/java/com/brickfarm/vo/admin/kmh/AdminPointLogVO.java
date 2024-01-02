package com.brickfarm.vo.admin.kmh;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AdminPointLogVO {
	
	private Timestamp accrual_date;
	private Timestamp usage_date;
	private String member_name;
	private String member_id;
	private String merchant_uid;
	private int usage_amount;
	private int accrual_log_amount;
	private String point_accrual_reason;
	private String reason;

	public void setAccrual_date(Timestamp accrual_date) {
		this.accrual_date = accrual_date;
	}
	public void setUsage_date(Timestamp usage_date) {
		this.usage_date = usage_date;
	}
	public void setMember_name(String member_name) {
		if(member_name.length() == 2) {
			this.member_name = member_name.substring(0, 1) + "*";
		} else {
			String name = member_name.substring(0, 1);	
			for (int i=0; i<member_name.length() -2; i++){
				name += "*";
			}
			name += member_name.substring(member_name.length() -1);
			this.member_name = name;
		}
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public void setUsage_amount(int usage_amount) {
		this.usage_amount = usage_amount;
	}
	public void setAccrual_log_amount(int accrual_log_amount) {
		this.accrual_log_amount = accrual_log_amount;
	}
	public void setPoint_accrual_reason(String point_accrual_reason) {
		this.point_accrual_reason = point_accrual_reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
