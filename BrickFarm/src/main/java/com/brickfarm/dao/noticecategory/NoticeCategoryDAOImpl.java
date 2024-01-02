package com.brickfarm.dao.noticecategory;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.kyj.UserNoticeCategoryVO;

@Repository
public class NoticeCategoryDAOImpl implements NoticeCategoryDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.NoticeCategoryMapper.";
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
	 * @methodName : selectAllNoticeCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 19. 오후 6:48:24
	 * @description : 모든 공지사항 분류 정보 목록을 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<UserNoticeCategoryVO> selectAllNoticeCategory() throws SQLException {
		return ses.selectList(ns + "selectAllNoticeCategory");
	}
	
	// 전달 받은 공지사항 분류명으로 공지사항 분류 항목을 데이터베이스에 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean insertNoticeCategory(String noticeCategoryName) throws SQLException {
		boolean result = false;
		if(ses.insert(ns + "insertNoticeCategory", noticeCategoryName) == 1) {
			result = true;
		}
		return result;
	}
	
	// 전달 받은 공지사항 분류 정보로 해당하는 항목을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean updateNoticeCategoryByCategoryNo(UserNoticeCategoryVO noticeCategory) throws SQLException {
		boolean result = false;
		if(ses.update(ns + "updateNoticeCategoryByCategoryNo", noticeCategory) == 1) {
			result = true;
		}
		return result;
	}
	
	// 전달 받은 공지사항 분류 번호로 해당하는 항목을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean deleteNoticeCategoryByCategoryNo(int noticeCategoryNo) throws SQLException {
		boolean result = false;
		if(ses.delete(ns + "deleteNoticeCategoryByCategoryNo", noticeCategoryNo) == 1) {
			result = true;
		}
		return result;
	}
	// ==================================================================================================================================================
}
