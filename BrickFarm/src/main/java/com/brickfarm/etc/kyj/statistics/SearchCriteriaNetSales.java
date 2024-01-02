package com.brickfarm.etc.kyj.statistics;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SearchCriteriaNetSales {
	private Timestamp startDate;
	private Timestamp endDate;
	private int recentWeek;
	private boolean isCheckedUsePoint;
}
