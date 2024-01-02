package com.brickfarm.vo.user.kyj;

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
public class UserInquiryVO {
	private int inquiry_board_no;
	private int inquiry_category_no;
	private int member_no;
	private int admin_no;
	private String title;
	private String content;
	private Timestamp created_date;
	private Timestamp updated_date;
	private int ref;
	private int step;
	private int ref_order;
	private String is_delete;
	private String inquiry_category_name;
	private String member_id;
	private String admin_id;
}
