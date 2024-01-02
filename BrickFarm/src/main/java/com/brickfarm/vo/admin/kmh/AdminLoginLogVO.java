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
public class AdminLoginLogVO {
	
	private int login_log_no;
	private Timestamp login_date;
	private String member_name;
	private String member_id;
	private String email;
	private String phone_number;
	
	public void setLogin_log_no(int login_log_no) {
		this.login_log_no = login_log_no;
	}
	public void setLogin_date(Timestamp login_date) {
		this.login_date = login_date;
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
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number.substring(0, phone_number.indexOf('-')+1) + "****" + phone_number.substring(phone_number.lastIndexOf("-"));
	}

}
