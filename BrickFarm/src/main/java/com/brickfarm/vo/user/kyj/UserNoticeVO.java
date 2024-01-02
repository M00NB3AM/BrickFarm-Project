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
public class UserNoticeVO {
	private int notice_board_no;
	private int notice_category_no;
	private int admin_no;
	private String title;
	private String content;
	private Timestamp created_date;
	private Timestamp updated_date;
	private String is_fixed;
	private String notice_category_name;
}
