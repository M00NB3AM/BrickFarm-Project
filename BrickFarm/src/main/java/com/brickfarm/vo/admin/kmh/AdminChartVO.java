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
public class AdminChartVO {
	
	private int no;
	private int day;
	private int week;
	private int month;
	private int hundred;
	private int elses;

}
