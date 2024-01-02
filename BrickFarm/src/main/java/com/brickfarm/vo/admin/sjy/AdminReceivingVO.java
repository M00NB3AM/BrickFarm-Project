package com.brickfarm.vo.admin.sjy;

import java.sql.Timestamp;

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
public class AdminReceivingVO {
	private int receiving_no;
	private String product_code;
	private int quantity;
	private int total_price;
	private String is_received;
	private Timestamp receiving_date;
}
