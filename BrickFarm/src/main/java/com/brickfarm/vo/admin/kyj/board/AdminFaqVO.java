package com.brickfarm.vo.admin.kyj.board;

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
public class AdminFaqVO {
	private int faq_board_no;
	private int faq_category_no;
	private String faq_category_name;
	private int admin_no;
	private String admin_id;
	private String title;
	private String content;
	private Timestamp created_date;
	private Timestamp updated_date;
}
