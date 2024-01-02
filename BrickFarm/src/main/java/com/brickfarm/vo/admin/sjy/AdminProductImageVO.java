package com.brickfarm.vo.admin.sjy;

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
public class AdminProductImageVO {
	private int product_image_no;
	private String product_code;
	private String product_file_path;
}
