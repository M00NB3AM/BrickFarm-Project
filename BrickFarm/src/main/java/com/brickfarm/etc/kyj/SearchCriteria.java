package com.brickfarm.etc.kyj;

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
public class SearchCriteria {
	private String searchType;
	private String searchWord;
	private int searchCategory;
	private int member_no;
}
