package com.brickfarm.vo.admin.ysh;

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
public class AdminVO {

	private int admin_no;
	private String admin_id;
	private String admin_password;
	private String authority;

	
}
