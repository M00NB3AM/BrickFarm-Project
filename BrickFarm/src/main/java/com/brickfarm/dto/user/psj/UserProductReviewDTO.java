package com.brickfarm.dto.user.psj;

import java.sql.Date;

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
public class UserProductReviewDTO {
	private String merchant_uid;
	private int product_review_no;
	private int member_no;
	private int detailed_order_no;
	private String product_code;
	private String content;
	private Date created_date;
	private int star_count;
}
