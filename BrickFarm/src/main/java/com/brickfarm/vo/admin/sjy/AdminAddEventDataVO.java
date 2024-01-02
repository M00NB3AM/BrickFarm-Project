package com.brickfarm.vo.admin.sjy;

import java.util.List;

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
public class AdminAddEventDataVO {
	private AdminEventVO event;
	private List<String> eventProductList;
}
