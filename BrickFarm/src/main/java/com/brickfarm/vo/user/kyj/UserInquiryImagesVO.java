package com.brickfarm.vo.user.kyj;

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
public class UserInquiryImagesVO {
	private int inquiry_images_no;
	private int inquiry_board_no;
	private String original_file_name;
	private String new_file_name;
	private long file_size;
	private String thumbnail_file_name;
}
