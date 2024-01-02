package com.brickfarm.dto.user.psj;

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
public class UserProductReviewInfoDTO {
private int detailed_order_no;
private String merchant_uid;
private String product_name;
private int parts_quantity;
private String recommend_age;
private String product_main_image;
private int product_category_no;
private String product_category_name;
private String product_code;
}
