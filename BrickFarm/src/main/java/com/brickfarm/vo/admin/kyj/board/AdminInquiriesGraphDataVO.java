package com.brickfarm.vo.admin.kyj.board;

import java.sql.Timestamp;

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
public class AdminInquiriesGraphDataVO {
	private Timestamp created_date;
	private int inquiry_count;
	private int unanswered_inquiry_count;
}
