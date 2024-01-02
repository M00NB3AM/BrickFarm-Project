package com.brickfarm.vo.admin.kmh;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AdminMemberOrderVO {
	
	private int member_no;
	private String member_name;
	private String member_id;
	private String member_grade_name;
	private String merchant_uid;
	private Timestamp order_day;
	private String total_state;
	private Timestamp total_complete_date;
	
	public void setMember_no(int member_no) {
		this.member_no = member_no;
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
	public void setMember_grade_name(String member_grade_name) {
		this.member_grade_name = member_grade_name;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public void setOrder_day(Timestamp order_day) {
		this.order_day = order_day;
	}
	public void setTotal_state(String total_state) {
		this.total_state = total_state;
	}
	public void setTotal_complete_date(Timestamp total_complete_date) {
		this.total_complete_date = total_complete_date;
	}
	
	
	

}
