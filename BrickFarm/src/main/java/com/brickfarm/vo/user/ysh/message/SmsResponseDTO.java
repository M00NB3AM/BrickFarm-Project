package com.brickfarm.vo.user.ysh.message;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class SmsResponseDTO {
	 private String requestId;
	    private LocalDateTime requestTime;
	    private String statusCode;
	    private String statusName;
	    private String smsConfirmNum;
	    
	    
		public SmsResponseDTO(String smsConfirmNum) {
			super();
			this.smsConfirmNum = smsConfirmNum;
		}
	    
	    
}

