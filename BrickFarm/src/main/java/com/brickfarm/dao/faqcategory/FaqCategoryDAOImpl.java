package com.brickfarm.dao.faqcategory;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.kyj.UserFaqCategoryVO;

@Repository
public class FaqCategoryDAOImpl implements FaqCategoryDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.FaqCategoryMapper.";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================

	/**
	 * @methodName : selectAllFaqCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 5. 오전 10:57:05
	 * @description : 데이터베이스로부터 FAQ 분류 전체 목록을 얻어와 반환한다.
	 */
	@Override
	public List<UserFaqCategoryVO> selectAllFaqCategory() throws SQLException {
		return ses.selectList(ns + "selectAllFaqCategory");
	}
	
	// 전달 받은 FAQ 분류명으로 FAQ 분류 항목을 데이터베이스에 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean insertFaqCategory(String faqCategoryName) throws SQLException {
		boolean result = false;
		if(ses.insert(ns + "insertFaqCategory", faqCategoryName) == 1) {
			result = true;
		}
		return result;
	}
	
	// 전달 받은 FAQ 분류 정보로 해당하는 항목을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean updateFaqCategoryByCategoryNo(UserFaqCategoryVO faqCategory) throws SQLException {
		boolean result = false;
		if(ses.update(ns + "updateFaqCategoryByCategoryNo", faqCategory) == 1) {
			result = true;
		}
		return result;
	}
	
	// 전달 받은 FAQ 분류 번호로 해당하는 항목을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean deleteFaqCategoryByCategoryNo(int faqCategoryNo) throws SQLException {
		boolean result = false;
		if(ses.delete(ns + "deleteFaqCategoryByCategoryNo", faqCategoryNo) == 1) {
			result = true;
		}
		return result;
	}
	// ==================================================================================================================================================
}
