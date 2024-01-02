package com.brickfarm.dto.user.kyj;

import java.sql.Timestamp;
import java.util.List;

import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @packageName : com.brickfarm.etc.kyj 
 * @fileName :  UploadFileApiResponse.java
 * @author : kyj
 * @date : 2023. 10. 11.
 * @description : 문의 작성 중 드래그 앤 드랍을 통한 이미지 파일 업로드 시 api 응답을 위한 객체
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserUploadFileApiResponseDTO {
	private List<UserInquiryImagesVO> fileList;
	private String message;
	private Timestamp createdAt;
	private String errorCode;
	private String errorMessage;
}
