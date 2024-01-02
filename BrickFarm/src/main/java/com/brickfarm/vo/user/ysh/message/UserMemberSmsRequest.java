package com.brickfarm.vo.user.ysh.message;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserMemberSmsRequest {
	private String type;
	private String contentType;
	private String countryCode;
	private String from;
	private String content;
	private List<MessageDTO> message;
}
