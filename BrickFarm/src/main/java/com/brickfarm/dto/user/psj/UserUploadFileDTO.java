package com.brickfarm.dto.user.psj;

import java.util.List;

import com.brickfarm.vo.user.psj.UserProductReviewImageVO;

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
public class UserUploadFileDTO {
	List<UserProductReviewImageVO> fileList;
}
