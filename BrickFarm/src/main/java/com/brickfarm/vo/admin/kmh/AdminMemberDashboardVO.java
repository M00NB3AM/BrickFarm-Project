package com.brickfarm.vo.admin.kmh;

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
public class AdminMemberDashboardVO {

	private int total;
	private int regist;
	private int withdraw;
	private int login;
}
