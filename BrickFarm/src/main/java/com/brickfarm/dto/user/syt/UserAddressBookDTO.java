package com.brickfarm.dto.user.syt;

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
public class UserAddressBookDTO {
	private int member_no;
	private String address_name;
	private String address;
	private String zip_code;
	private String recipient;
	private String phone_number;
}
