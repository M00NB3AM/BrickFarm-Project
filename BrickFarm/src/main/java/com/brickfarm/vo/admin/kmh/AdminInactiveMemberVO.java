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
public class AdminInactiveMemberVO {

	private int inactive_member_no;
	private int member_no;
	private Timestamp conversion_date;
	private Timestamp release_date;
	private String member_name;
	private String member_id;
	private String phone_number;
	private String email;
	
	public void setInactive_member_no(int inactive_member_no) {
		this.inactive_member_no = inactive_member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public void setConversion_date(Timestamp conversion_date) {
		this.conversion_date = conversion_date;
	}
	public void setRelease_date(Timestamp release_date) {
		this.release_date = release_date;
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
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number.substring(0, phone_number.indexOf('-')+1) + "****" + phone_number.substring(phone_number.lastIndexOf("-"));
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
