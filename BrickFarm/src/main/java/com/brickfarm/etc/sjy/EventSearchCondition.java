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
public class EventSearchCondition {
	private String search_word;
	private String search_type;
	private String event_start;
	private String event_end;
	private float min_rate;
	private float max_rate;
	private HashMap<String, Object> discount_rate;
}
