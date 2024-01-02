package com.brickfarm.dto.admin.kmh;


import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AdminMessageResponseDTO {
	String requestId;
	Timestamp requestTime;
	String statusCode;
	String statusName;
}
