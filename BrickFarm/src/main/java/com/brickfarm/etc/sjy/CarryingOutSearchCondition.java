package com.brickfarm.etc.sjy;

import java.util.HashMap;

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
public class CarryingOutSearchCondition {
	private String search_word;
	private String search_type;
	private String min_date;
	private String max_date;
	private String all;
	private String wait;
	private String complete;
	private HashMap<String, Object> is_carried_out;
	private HashMap<String, Object> carrying_out_date;
}
