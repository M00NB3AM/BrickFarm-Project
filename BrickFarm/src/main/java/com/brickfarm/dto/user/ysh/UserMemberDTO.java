package com.brickfarm.dto.user.ysh;

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
public class UserMemberDTO {
	private String member_name;
	private String member_id;
	private String phone_number;
	private String social_check;
	private String email;
	private int member_no;
	private String password;
	
	public UserMemberDTO(String member_name, String member_id, String phone_number) {
		super();
		this.member_name = member_name;
		this.member_id = member_id;
		this.phone_number = phone_number;
	}
	
	public UserMemberDTO(String phone_number, String email) {
		this.phone_number = phone_number;
		this.email = email;
	}
	
	public UserMemberDTO(String phone_number, String email, String socialCheck, String member_name) {
		this.phone_number = phone_number;
		this.email = email;
		this.social_check = socialCheck;
		this.member_name = member_name;
	}
	public UserMemberDTO(int member_no, String password) {
		this.member_no = member_no;
		this.password = password;
	}
}


