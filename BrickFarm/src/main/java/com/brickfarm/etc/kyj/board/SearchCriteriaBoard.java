package com.brickfarm.etc.kyj.board;

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
public class SearchCriteriaBoard {
	private Timestamp startDate;
	private Timestamp endDate;
	
	private int categoryNo;
	private String searchKey;
	private String searchValue;
	private boolean isNeedResponse;
	private String isFixed;
}
