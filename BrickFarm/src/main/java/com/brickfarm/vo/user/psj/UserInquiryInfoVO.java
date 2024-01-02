package com.brickfarm.vo.user.psj;

import java.security.Timestamp;
import java.sql.Date;

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
public class UserInquiryInfoVO {
	private int inquiry_board_no;
	private int inquiry_category_no;
	private int member_no;
	private int admin_no;
	private String content;
	private Timestamp created_date;
	private Timestamp updated_date;
	private int ref;
	private int step;
	private int ref_order;
	private String is_delete;
	private int row_count;
}
