package com.brickfarm.dao.inquiryimages;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;

public interface InquiryImagesDAO {
	/**
	 * @methodName : insertInquiryImages
	 * @author : kyj
	 * @param fileList
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param fileList
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 12. 오후 4:41:43
	 * @description : 문의 작성 시 첨부한 파일들의 목록을 받아 데이터베이스에 insert 쿼리문을 수행한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean insertInquiryImages(List<UserInquiryImagesVO> fileList, int inquiryBoardNo) throws SQLException;

	/**
	 * @methodName : insertInquiryImage
	 * @author : kyj
	 * @param newFile
	 * @return
	 * @throws SQLException
	 * @returnValue : @param newFile
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 16. 오후 7:29:56
	 * @description : 단일 파일 정보를 전달 받아 데이터베이스에 insert 쿼리문을 수행한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean insertInquiryImage(UserInquiryImagesVO newFile) throws SQLException;

	/**
	 * @methodName : selectInquiryImagesByInguiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 2:15:08
	 * @description : inquiry_images 테이블에서 전달받은 문의글 번호에 해당하는 데이터들을 얻어와 반환한다.
	 */
	public List<UserInquiryImagesVO> selectInquiryImagesByInguiryNo(int inquiryBoardNo) throws SQLException;
	
	/**
	 * @methodName : selectInquiryImagesByImageNo
	 * @author : kyj
	 * @param inquiryImageNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryImageNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 16. 오후 7:23:14
	 * @description : inquiry_images 테이블에서 전달받은 사진 파일 번호에 해당하는 데이터를 얻어와 반환한다.
	 */
	public UserInquiryImagesVO selectInquiryImageByImageNo(int inquiryImageNo) throws SQLException;
	
	public UserInquiryImagesVO selectInquiryImageByFileName(String newFileName) throws SQLException; 
	
	/**
	 * @methodName : deleteInquiryImagesByImageNo
	 * @author : kyj
	 * @param inquiryImagesNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryImagesNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:12:58
	 * @description : 전달 받은 문의글 번호로 관련된 사진 정보들을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean deleteInquiryImagesByInquiryNo(int inquiryBoardNo, int numberOfImages) throws SQLException;
	
	/**
	 * @methodName : deleteInquiryImagesByImageNo
	 * @author : kyj
	 * @param inquiryImageNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryImageNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 16. 오후 7:16:59
	 * @description : 전달 받은 사진 번호와 일치하는 사진 정보를 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean deleteInquiryImagesByImageNo(int inquiryImageNo) throws SQLException;
}
