package com.brickfarm.vo.user.psj;

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
public class UserProductReviewImageVO {
	private int product_review_image_no;
	private int product_review_no;
	private String original_file_name;
	private String new_file_name;
	private String thumb_file_name;
	private long file_size;
}
