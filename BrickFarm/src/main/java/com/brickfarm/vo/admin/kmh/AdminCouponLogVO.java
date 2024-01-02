package com.brickfarm.vo.admin.kmh;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AdminCouponLogVO {

	private String member_id;
	private String member_name;
	private int coupon_held_no;
	private String coupon_policy_name;
	private Timestamp published_date;
	private Timestamp expiration_date;
	private String available_coupon;
	private String merchant_uid;
	private Timestamp usage_date;
	private String usage_reason;
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
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
	public void setCoupon_held_no(int coupon_held_no) {
		this.coupon_held_no = coupon_held_no;
	}
	public void setCoupon_policy_name(String coupon_policy_name) {
		this.coupon_policy_name = coupon_policy_name;
	}
	public void setPublished_date(Timestamp published_date) {
		this.published_date = published_date;
	}
	public void setExpiration_date(Timestamp expiration_date) {
		this.expiration_date = expiration_date;
	}
	public void setAvailable_coupon(String available_coupon) {
		this.available_coupon = available_coupon;
	}
	public void setMerchant_uid(String merchant_uid) {
		this.merchant_uid = merchant_uid;
	}
	public void setUsage_date(Timestamp usage_date) {
		this.usage_date = usage_date;
	}
	public void setUsage_reason(String usage_reason) {
		this.usage_reason = usage_reason;
	}
	
	
}
