package com.brickfarm.dto.admin.syt;

import java.util.Date;

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
public class AdminSearchDTO {
	private String stateSelect;
	private String dateSelect;
	private String dateRange;
	private Date dateStart;
	private Date dateEnd;
	private String wordSelect;
	private String searchText;
	private String totalState;
	private String negligence;
}
