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
public class AdminNoActiveMemberDTO {
	private int member_no;
	private String member_name = "";
	private String member_id = "";
	private String member_grade_name = "";
	private String date_start = "";
	private String date_end = "";
	private boolean inactive_status;
	private String withdraw_reason;
}
