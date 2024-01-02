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
public class AdminOrderMemberDTO {
	private String member_name = "";
	private String member_id = "";
	private String member_grade_name = "all";
	private String date_start;
	private String date_end;
	private int money_range_start;
	private int money_range_end;
	
}
