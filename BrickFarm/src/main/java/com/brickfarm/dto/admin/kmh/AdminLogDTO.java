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
public class AdminLogDTO {
	
	private String member_name;
	private String member_id;
	private String useORaccrual;
	private String dateOption;
	private String date_start;
	private String date_end;
	
}
