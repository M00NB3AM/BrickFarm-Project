package com.brickfarm.vo.user.psj;


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
public class UserAddressBookVO {
	private int member_address_book_no;
	private int member_no;
	private String address_name;
	private String address;
	private String zip_code;
	private String recipient;
	private String phone_number;
	private String is_default;
}
