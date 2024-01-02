package com.brickfarm.dto.admin.kmh;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString

public class AdminAvailableCouponDTO {

	private char isAvailable;
	private List<Integer> change_coupon_no;
	
	public void setIsAvailable(boolean isAvailable) {
		if(isAvailable) {
			this.isAvailable = 'Y';
		} else {
			this.isAvailable = 'N';
		}
	}
	public void setChange_coupon_no(List<Integer> change_coupon_no) {
		this.change_coupon_no = change_coupon_no;
	}
	
	
}
