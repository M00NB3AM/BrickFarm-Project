package com.brickfarm.vo.user.syt;

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
public class UserAddressVO {
	private int member_address_book_no;
	private int member_no;
	private String address_name;
	private String address;
	private String detaile_address;
	private String zip_code;
	private String recipient;
	private String phone_number;
	private String is_default;

}
