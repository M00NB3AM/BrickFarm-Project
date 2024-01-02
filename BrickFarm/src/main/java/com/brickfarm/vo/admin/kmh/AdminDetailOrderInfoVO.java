package com.brickfarm.vo.admin.kmh;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AdminDetailOrderInfoVO {
		
	// 배송 정보
	
	// 주문자
	private String member_name;
	private String member_id;
	private String phone_number;
	
	// 수령자
	private String recipient;
	private String recipient_zip_code;
	private String recipient_address;
	private String recipient_phone;
	
	// 배송상태(운송장 번호)
	private String delivery_state;
	private String post_no;
	
	
	// 상품정보
	private List<AdminDetailOrderProductsVO> productList;
	
	
	// 결제 정보
	private int total_product_price;
	private int total_discounted_price;
	private int post_money;
	private int point_pay_money;
	private int cancel_money;
	private int total_pay_money;
	
	
	
	// 결제 수단
	private String payType;
	private String buyer;
	private Timestamp checked_time;
	private Timestamp deposit_deadline;
	private String brand;
	private String checked_info;
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
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number.substring(0, phone_number.indexOf('-')+1) + "****" + phone_number.substring(phone_number.lastIndexOf("-"));
	}
	public void setRecipient(String recipient) {
		if(recipient.length() == 2) {
			this.recipient = recipient.substring(0, 1) + "*";
		} else {
			String name = recipient.substring(0, 1);	
			for (int i=0; i<recipient.length() -2; i++){
				name += "*";
			}
			name += recipient.substring(recipient.length() -1);
			this.recipient = name;
		}
	}
	public void setRecipient_zip_code(String recipient_zip_code) {
		this.recipient_zip_code = recipient_zip_code;
	}
	public void setRecipient_address(String recipient_address) {
		String[] addrList = recipient_address.split(" ");
		this.recipient_address = addrList[0] + " " + addrList[1] + " " + addrList[2];
	}
	public void setRecipient_phone(String recipient_phone) {
		this.recipient_phone = recipient_phone.substring(0, recipient_phone.indexOf('-')+1) + "****" + recipient_phone.substring(recipient_phone.lastIndexOf("-"));
	}
	public void setDelivery_state(String delivery_state) {
		this.delivery_state = delivery_state;
	}
	public void setPost_no(String post_no) {
		this.post_no = post_no;
	}
	public void setProductList(List<AdminDetailOrderProductsVO> productList) {
		this.productList = productList;
	}
	public void setTotal_product_price(int total_product_price) {
		this.total_product_price = total_product_price;
	}
	public void setTotal_discounted_price(int total_discounted_price) {
		this.total_discounted_price = total_discounted_price;
	}
	public void setPost_money(int post_money) {
		this.post_money = post_money;
	}
	public void setPoint_pay_money(int point_pay_money) {
		this.point_pay_money = point_pay_money;
	}
	public void setCancel_money(int cancel_money) {
		this.cancel_money = cancel_money;
	}
	public void setTotal_pay_money(int total_pay_money) {
		this.total_pay_money = total_pay_money;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public void setBuyer(String buyer) {
		if(buyer.length() == 2) {
			this.buyer = buyer.substring(0, 1) + "*";
		} else {
			String name = buyer.substring(0, 1);	
			for (int i=0; i<buyer.length() -2; i++){
				name += "*";
			}
			name += buyer.substring(buyer.length() -1);
			this.buyer = name;
		}
	}
	public void setChecked_time(Timestamp checked_time) {
		this.checked_time = checked_time;
	}
	public void setDeposit_deadline(Timestamp deposit_deadline) {
		this.deposit_deadline = deposit_deadline;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setChecked_info(String checked_info) {
		this.checked_info = checked_info;
	}
	
	
	
	
	

}
