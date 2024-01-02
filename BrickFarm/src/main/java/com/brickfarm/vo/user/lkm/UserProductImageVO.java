package com.brickfarm.vo.user.lkm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserProductImageVO {
	private int product_image_no;
	private String product_code;
	private String product_file_path;
}
