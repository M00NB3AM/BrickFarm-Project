package com.brickfarm.etc.psj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
	private String searchPeriod;
	private String searchState;
	private String searchOrder;
}
