package com.brickfarm.dao.faqboard;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.kyj.UserFaqVO;
import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.vo.admin.kyj.board.AdminFaqVO;

@Repository
public class FaqBoardDAOImpl implements FaqBoardDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.FaqBoardMapper.";
	
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
	 * @methodName : selectAllFaq
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 4. 오전 12:59:42
	 * @description : 데이터베이스로부터 FAQ 전체 목록을 얻어와 반환한다.
	 */
	@Override
	public List<UserFaqVO> selectAllFaq() throws SQLException {
		return ses.selectList(ns + "selectAllFaq");
	}

	/* === 자주묻는 질문 관리 ================================================================================================================================================ */
	@Override
	public int selectFaqByConditionCountInAdmin(SearchCriteriaBoard sc) throws SQLException {
		return ses.selectOne(ns + "selectFaqByConditionCountInAdmin", sc);
	}

	@Override
	public List<AdminFaqVO> selectFaqByConditionInAdmin(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryNo", sc.getCategoryNo());
		params.put("searchKey", sc.getSearchKey());
		params.put("searchValue", sc.getSearchValue());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "selectFaqByConditionInAdmin", params);
	}

	@Override
	public AdminFaqVO selectFaqByFaqBoardNoInAdmin(int faqBoardNo) throws SQLException {
		return ses.selectOne(ns + "selectFaqByFaqBoardNoInAdmin", faqBoardNo);
	}

	@Override
	public int insertFaq(AdminFaqVO faq) throws SQLException {
		return ses.insert(ns + "insertFaq", faq);
	}

	@Override
	public int deleteFaqByFaqBoardNo(int faqBoardNo) throws SQLException {
		return ses.delete(ns + "deleteFaqByFaqBoardNo", faqBoardNo);
	}

	@Override
	public int updateFaq(AdminFaqVO faq) throws SQLException {
		return ses.update(ns + "updateFaq", faq);
	}
	// ==================================================================================================================================================
}
