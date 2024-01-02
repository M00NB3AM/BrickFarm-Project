package com.brickfarm.dto.admin.sjy;

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
public class AdminCarryingOutDTO {
	private int carrying_out_no;
	private String product_code;
	private String product_name;
	private int quantity;
	private int total_price;
	private String is_carried_out;
	private Timestamp carrying_out_date;
}
