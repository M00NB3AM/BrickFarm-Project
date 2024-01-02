package com.brickfarm.etc.kyj.statistics;

import java.sql.Timestamp;
import java.util.List;

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
public class SearchCriteriaProducts {
	private Timestamp startDate;
	private Timestamp endDate;
	
	private int productLargeCategoryNo;
	private int productMediumCategoryNo;
	private int productSmallCategoryNo;
	
	// select + input
	private String productCode;
	private String productName;
	
	private int startPartsQuantity;
	private int endPartsQuantity;
	
	private List<String> recommendAges; // checkbox
	
	private int startPrice;
	private int endPrice;
	
	private String memberId;
	private String memberName;
	
	private String orderByColumnName;
}
