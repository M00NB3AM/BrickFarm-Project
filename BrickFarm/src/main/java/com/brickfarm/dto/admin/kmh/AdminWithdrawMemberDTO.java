package com.brickfarm.dto.admin.kmh;

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
public class AdminWithdrawMemberDTO {
	private int withdraw_member_no;
	private int member_no;
	private String member_id;
	private String withdraw_reason;
	private String reason_memo;

}

