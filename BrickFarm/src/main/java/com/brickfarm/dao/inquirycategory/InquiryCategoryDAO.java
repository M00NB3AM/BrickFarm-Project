package com.brickfarm.dao.inquirycategory;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;

public interface InquiryCategoryDAO {

	// ==[송지영]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[김미형]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[이경민]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[염세환]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[박상진]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[송영태]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[김용진]========================================================================================================================================

	/**
	 * @methodName : selectAllInquiryCategory
	 * @author : kyj
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 10. 6. 오후 6:19:42
	 * @description : 데이터베이스로부터 1:1 문의 게시판 분류 전체 목록을 얻어와 반한한다.
	 */
	public List<UserInquiryCategoryVO> selectAllInquiryCategory() throws SQLException;
	
	/**
	 * @methodName : insertInquiryCategory
	 * @author : kyj
	 * @param inquiryCategoryName
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryCategoryName
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:10:17
	 * @description : 전달 받은 문의 분류명으로 문의 분류 항목을 데이터베이스에 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean insertInquiryCategory(String inquiryCategoryName) throws SQLException;
	
	/**
	 * @methodName : updateInquiryCategoryByCategoryNo
	 * @author : kyj
	 * @param inquiryCategory
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryCategory
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:10:56
	 * @description : 전달 받은 문의 분류 정보로 해당하는 항목을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean updateInquiryCategoryByCategoryNo(UserInquiryCategoryVO inquiryCategory) throws SQLException;
	
	/**
	 * @methodName : deleteInquiryCategoryByCategoryNo
	 * @author : kyj
	 * @param inquiryCategoryNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryCategoryNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:11:39
	 * @description : 전달 받은 문의 분류 번호로 해당하는 항목을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean deleteInquiryCategoryByCategoryNo(int inquiryCategoryNo) throws SQLException;
	// ==================================================================================================================================================
}
