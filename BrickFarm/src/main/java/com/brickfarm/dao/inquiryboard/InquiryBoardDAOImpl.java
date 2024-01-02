package com.brickfarm.dao.inquiryboard;

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
import com.brickfarm.vo.admin.kyj.board.AdminInquiryVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;
import com.brickfarm.vo.user.psj.UserInquiryInfoVO;

@Repository
public class InquiryBoardDAOImpl implements InquiryBoardDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.InquiryBoardMapper.";
	
	// ==[송지영]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[김미형]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[이경민]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[염세환]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[박상진]========================================================================================================================================
	@Override
	public List<UserInquiryInfoVO> selectAnsweredInquiry(int memberNo) throws Exception {
		List<UserInquiryInfoVO> result = ses.selectList(ns + "selectAnsweredInquiry", memberNo);
		return result;
	}

	@Override
	public List<UserInquiryInfoVO> selectNoAnsweredInquiry(int memberNo) throws Exception {
		List<UserInquiryInfoVO> result = ses.selectList(ns + "selectNoAnsweredInquiry", memberNo);
		return result;
	}
	// ==================================================================================================================================================

	// ==[송영태]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[김용진]========================================================================================================================================
	/**
	 * @methodName : selectInquiryByCondition
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 6. 오후 6:18:46
	 * @description : 선택적으로 페이지 정보, 검색 조건을 받아 조건에 맞는 1:1 문의글 목록을 데이터베이스로부터 얻어와 반환한다. 
	 */
	@Override
	public List<UserInquiryVO> selectInquiryByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException {
		List<UserInquiryVO> result = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		params.put("searchType", sc.getSearchType());
		params.put("searchWord", sc.getSearchWord());
		params.put("member_no", sc.getMember_no());
		
		if (sc.getMember_no() != 0) {
			result = ses.selectList(ns + "selectMyInquiries", params);
		} else if (sc.getSearchType().equals("")) {
			result = ses.selectList(ns + "selectInquiryByPaginationInfo", pi);
		} else {
			result = ses.selectList(ns + "selectInquiryByCondition", params);
		}

		return result;
	}

	/**
	 * @methodName : selectAllInquiryCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 6. 오후 6:19:04
	 * @description : 데이터베이스로부터 1:1 문의글 전체 목록 혹은 검색된 항목의 수를 얻어와 반한한다.
	 */
	@Override
	public int selectInquiryCountByCondition(SearchCriteria sc) throws SQLException {
		int result = -1;
		if (sc.getMember_no() != 0) {
			result = ses.selectOne(ns + "selectMyInquiriesCount", sc);
		} else if (sc.getSearchType().equals("")) {
			result = ses.selectOne(ns + "selectAllInquiryCount");
		} else {
			result = ses.selectOne(ns + "selectInquiryCountByCondition", sc);
		}
		return result;
	}

	/**
	 * @methodName : selectNextRefOfInquiryBoard
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 13. 오전 2:22:38
	 * @description : 문의 게시글을 insert 하기 위해 필요한 다음에 추가될 ref값을 얻어와 반환한다. 
	 */
	@Override
	public int selectNextRefOfInquiryBoard() throws SQLException {
		int result = 1;
		Object selectResult = ses.selectOne(ns + "selectNextRefOfInquiryBoard");
		if (selectResult != null) {
			result = (Integer) selectResult;
		}
		return result;
	}

	/**
	 * @methodName : insertInquiry
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 10. 오전 1:35:28
	 * @description : 사용자 페이지에서 문의 작성 시 이 메서드를 통해 데이터베이스로 INSERT 요청을 한다. INSERT 된 ROW의 PK(inquiry_board_no)를 반환한다. 문제 발생 시 0을 반환한다.
	 */
	@Override
	public int insertInquiry(UserInquiryVO inquiry) throws SQLException {
		int result = -1;
		if (ses.insert(ns + "insertInquiry", inquiry) == 1) {
			result = inquiry.getInquiry_board_no();
		}
		// System.out.println("\n방금 INSERT 한 ROW의 NO : " + inquiry.getInquiry_board_no() + "\n");
		return result;
	}

	/**
	 * @methodName : selectInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 7:37:17
	 * @description : 넘겨받은 문의 게시글 번호로 해당 문의글을 데이터베이스에서 얻어와 반환한다.
	 */
	@Override
	public UserInquiryVO selectInquiryByInquiryNo(int inquiryBoardNo) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inquiry_board_no", inquiryBoardNo);
		return ses.selectOne(ns + "selectInquiryByInquiryNo", params);
	}

	/**
	 * @methodName : updateInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 7:58:46
	 * @description : 넘겨받은 문의 게시글 정보로 해당 문의글을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean updateInquiryByInquiryNo(UserInquiryVO inquiry) throws SQLException {
		boolean result = false;
		// System.out.println("==================================================");
		// System.out.println(inquiry.toString());
		// System.out.println("==================================================");
		if (ses.update(ns + "updateInquiryByInquiryNo", inquiry) == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : deleteInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 7:58:56
	 * @description : 넘겨받은 문의 게시글 번호로 해당 문의글을 데이터베이스에서 찾아 삭제처리한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean deleteInquiryByInquiryNo(int inquiryBoardNo) throws SQLException {
		boolean result = false;
		if (ses.update(ns + "deleteInquiryByInquiryNo", inquiryBoardNo) == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : selectInquiriesByConditionCountInAdmin
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:59:43
	 * @description : 문의 관리 - 문의 관리 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectInquiriesByConditionCountInAdmin(SearchCriteriaBoard sc) throws SQLException {
		return ses.selectOne(ns + "selectInquiriesByConditionCountInAdmin", sc);
	}

	/**
	 * @methodName : selectInquiriesByConditionInAdmin
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:10:50
	 * @description : 넘겨받은 페이지 정보와 검색 조건으로 문의글 목록을 데이터베이스에서 받아와 반환한다.
	 */
	@Override
	public List<AdminInquiryVO> selectInquiriesByConditionInAdmin(PaginationInfo pi, SearchCriteriaBoard sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("categoryNo", sc.getCategoryNo());
		params.put("searchKey", sc.getSearchKey());
		params.put("searchValue", sc.getSearchValue());
		params.put("isNeedResponse", sc.isNeedResponse());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "selectInquiriesByConditionInAdmin", params);
	}

	/**
	 * @methodName : selectInquiryByInquiryNoInAdmin
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 11:47:38
	 * @description : 문의 관리 - 넘겨받은 문의글 번호로 문의글 상세정보를 데이터베이스에서 받아와 반한한다.
	 */
	@Override
	public AdminInquiryVO selectInquiryByInquiryNoInAdmin(int inquiryBoardNo) throws SQLException {
		return ses.selectOne(ns + "selectInquiryByInquiryNoInAdmin", inquiryBoardNo);
	}

	/**
	 * @methodName : updateRefOrder
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 24. 오전 4:02:04
	 * @description : 문의 관리 - 문의 답글 처리 시 같은 ref를 가진 글들의 refOrder를 +1씩 update한다.
	 */
	@Override
	public boolean updateRefOrder(UserInquiryVO inquiry) throws SQLException {
		boolean result = false;
		
		if(ses.update(ns + "updateRefOrder", inquiry) > -1) {
			result = true;
		}
		
		return result;
	}

	@Override
	public int insertReplyInquiry(UserInquiryVO inquiry) throws SQLException {
		int result = -1;
		if (ses.insert(ns + "insertReplyInquiry", inquiry) == 1) {
			result = inquiry.getInquiry_board_no();
		}
		// System.out.println("\n방금 INSERT 한 ROW의 NO : " + inquiry.getInquiry_board_no() + "\n");
		return result;
	}
	
	
	
	// ==================================================================================================================================================

}
