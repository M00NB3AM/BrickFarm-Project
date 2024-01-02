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
public class AdminInquiryVO {
	private int inquiry_board_no;
	private int inquiry_category_no;
	private String inquiry_category_name;
	private int member_no;
	private String member_id;
	private String email;
	private int admin_no;
	private String admin_id;
	private String title;
	private String content;
	private Timestamp created_date;
	private Timestamp updated_date;
	private int ref;
	private int step;
	private int ref_order;
	private String is_delete;
	private int tree_count;
}
