package com.brickfarm.vo.user.lkm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
public class UserProductReviewVO {
	private int product_review_no;
	private int member_no;
	private String member_name;
	private String product_code;
	private String content;
	private Timestamp created_date;
	private int star_count;
	private String thumb_file_name;
	private String new_file_name;
	
	
	public void setMember_name(String member_name) {
	      if(member_name.length() == 2) {
	         this.member_name = member_name.substring(0, 1) + "*";
	      } else {
	         String name = member_name.substring(0, 1);   
	         for (int i=0; i<member_name.length() -2; i++){
	            name += "*";
	         }
	         name += member_name.substring(member_name.length() -1);
	         this.member_name = name;
	      }
	   }
	
}
