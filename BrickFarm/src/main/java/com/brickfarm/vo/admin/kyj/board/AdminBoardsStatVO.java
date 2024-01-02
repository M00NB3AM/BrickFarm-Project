package com.brickfarm.vo.admin.kyj.board;

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
public class AdminBoardsStatVO {
	private int notice;
	private int notice_category;
	private int today_unanswered_inquiry;
	private int unanswered_inquiry;
	private int inquiry;
	private int inquiry_category;
	private int inquiry_images;
	private int inquiry_images_file_size;
	private int faq;
	private int faq_category;
}
