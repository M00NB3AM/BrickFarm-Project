package com.brickfarm.dao.inquirycategory;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;

@Repository
public class InquiryCategoryDAOImpl implements InquiryCategoryDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.InquiryCategoryMapper.";
	
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
	 * @date : 2023. 10. 6. 오후 6:22:06
	 * @description : 데이터베이스로부터 1:1 문의 게시판 분류 전체 목록을 얻어와 반한한다.
	 */
	@Override
	public List<UserInquiryCategoryVO> selectAllInquiryCategory() {
		return ses.selectList(ns + "selectAllInquiryCategory");
	}

	/**
	 * @methodName : insertInquiryCategory
	 * @author : kyj
	 * @param inquiryCategoryName
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryCategoryName
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:12:14
	 * @description : 전달 받은 문의 분류명으로 문의 분류 항목을 데이터베이스에 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean insertInquiryCategory(String inquiryCategoryName) throws SQLException {
		boolean result = false;
		if(ses.insert(ns + "insertInquiryCategory", inquiryCategoryName) == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : updateInquiryCategoryByCategoryNo
	 * @author : kyj
	 * @param inquiryCategory
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryCategory
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:12:26
	 * @description : 전달 받은 문의 분류 정보로 해당하는 항목을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean updateInquiryCategoryByCategoryNo(UserInquiryCategoryVO inquiryCategory) throws SQLException {
		boolean result = false;
		if(ses.update(ns + "updateInquiryCategoryByCategoryNo", inquiryCategory) == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : deleteInquiryCategoryByCategoryNo
	 * @author : kyj
	 * @param inquiryCategoryNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryCategoryNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:12:31
	 * @description : 전달 받은 문의 분류 번호로 해당하는 항목을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean deleteInquiryCategoryByCategoryNo(int inquiryCategoryNo) throws SQLException {
		boolean result = false;
		if(ses.delete(ns + "deleteInquiryCategoryByCategoryNo", inquiryCategoryNo) == 1) {
			result = true;
		}
		return result;
	}
	
	// ==================================================================================================================================================
}
