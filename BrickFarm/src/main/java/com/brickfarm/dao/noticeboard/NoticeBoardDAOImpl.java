package com.brickfarm.dao.noticeboard;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.vo.admin.kyj.board.AdminNoticeVO;
import com.brickfarm.vo.user.kyj.UserNoticeVO;

@Repository
public class NoticeBoardDAOImpl implements NoticeBoardDAO {
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
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.NoticeBoardMapper.";

	/**
	 * @methodName : selectNoticeByCondition
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 2. 오후 10:41:56
	 * @description : 선택적으로 페이지 정보, 검색 조건을 받아 조건에 맞는 공지사항 목록을 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<UserNoticeVO> selectNoticeByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException {
		List<UserNoticeVO> result = null;

		if (sc.getSearchType().equals("")) {
			result = ses.selectList(ns + "selectNoticeByPaginationInfo", pi);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startRowIndex", pi.getStartRowIndex());
			params.put("rowCountPerPage", pi.getRowCountPerPage());
			params.put("searchType", sc.getSearchType());
			params.put("searchWord", sc.getSearchWord());
			params.put("searchCategory", sc.getSearchCategory());
			// System.out.println(params.toString());
			result = ses.selectList(ns + "selectNoticeByCondition", params);
		}

		return result;
	}

	/**
	 * @methodName : selectAllNoticeCount
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 3. 오후 7:43:21
	 * @description : 데이터베이스로부터 조건에 맞는 공지사항 목록의 수를 얻어와 반환한다. 
	 */
	@Override
	public int selectNoticeCountByCondition(SearchCriteria sc) throws SQLException {
		int result = -1;
		if(sc.getSearchType().equals("")) {
			result = ses.selectOne(ns + "selectAllNoticeCount");
		} else {
			result = ses.selectOne(ns + "selectNoticeCountByCondition", sc);
		}
		return result;
	}

	/**
	 * @methodName : selectFixedNotice
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 3. 오후 7:43:23
	 * @description : 데이터베이스로부터 상단 고정할 공지사항의 목록을 얻어와 반환한다. 
	 */
	@Override
	public List<UserNoticeVO> selectFixedNotice() throws SQLException {
		return ses.selectList(ns + "selectFixedNotice");
	}

	@Override
	public UserNoticeVO selectNoticeByNoticeBoardNo(int noticeBoardNo) throws SQLException {
		return ses.selectOne(ns + "selectNoticeByNoticeBoardNo", noticeBoardNo);
	}

	/* === 공지사항 관리 ================================================================================================================================================ */
	@Override
	// 공지사항 관리 - 문의 관리 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	public int selectNoticesByConditionCountInAdmin(SearchCriteriaBoard sc) throws SQLException {
		return ses.selectOne(ns + "selectNoticeByConditionCountInAdmin", sc);
	}

	@Override
	// 공지사항 관리 - 넘겨받은 페이지 정보와 검색 조건으로 문의글 목록을 데이터베이스에서 받아와 반환한다.
	public List<AdminNoticeVO> selectNoticesByConditionInAdmin(PaginationInfo pi, SearchCriteriaBoard sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("categoryNo", sc.getCategoryNo());
		params.put("searchKey", sc.getSearchKey());
		params.put("searchValue", sc.getSearchValue());
		params.put("isFixed", sc.getIsFixed());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "selectNoticeByConditionInAdmin", params);
	}

	@Override
	// 공지사항 관리 - 넘겨받은 공지사항 글번호로 게시글 상세정보를 데이터베이스에서 받아와 반한한다.
	public AdminNoticeVO selectNoticeByNoticeBoardNoInAdmin(int noticeBoardNo) throws SQLException {
		return ses.selectOne(ns + "selectNoticeByNoticeBoardNoInAdmin", noticeBoardNo);
	}

	@Override
	// 공지사항 관리 - 넘겨받은 값으로 공지사항 게시글을 데이터베이스에 추가한다.
	public int insertNotice(AdminNoticeVO notice) throws SQLException {
		return ses.insert(ns + "insertNotice", notice);
	}

	@Override
	// 공지사항 관리 - 넘겨받은 게시글 번호에 해당하는 게시글을 데이터베이스에서 삭제한다.
	public int deleteNoticeByNoticeBoardNo(int noticeBoardNo) throws SQLException {
		return ses.delete(ns + "deleteNoticeByNoticeBoardNo", noticeBoardNo);
	}

	@Override
	// 공지사항 관리 - 넘겨받은 값에 해당하는 공지사항 게시글을 데이터베이스에서 수정한다. 
	public int updateNotice(AdminNoticeVO notice) throws SQLException {
		return ses.update(ns + "updateNotice", notice);
	}

	@Override
	// 공지사항 관리 - 넘겨받은 값에 해당하는 게시글들의 상단 고정 여부를 데이터베이스에서 수정한다.
	public int updateNoticeIsFixed(Map<String, Object> params) throws SQLException {
		return ses.update(ns + "updateNoticeIsFixed", params);
	}
	
	
	// ==================================================================================================================================================`
}
