package com.brickfarm.dao.inquiryboard;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.vo.admin.kyj.board.AdminInquiryVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;
import com.brickfarm.vo.user.psj.UserInquiryInfoVO;

/**
 * @packageName : com.brickfarm.dao.inquiryboard 
 * @fileName :  InquiryBoardDAO.java
 * @author : kyj
 * @date : 2023. 11. 24.
 * @description : 
 */
public interface InquiryBoardDAO {
	// ==[송지영]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[김미형]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[이경민]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[염세환]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[박상진]========================================================================================================================================
	List<UserInquiryInfoVO> selectAnsweredInquiry(int memberNo) throws Exception;
	
	List<UserInquiryInfoVO> selectNoAnsweredInquiry(int memberNo) throws Exception;
	
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
	 * @date : 2023. 10. 5. 오후 6:08:40
	 * @description : 선택적으로 페이지 정보, 검색 조건을 받아 조건에 맞는 1:1 문의글 목록을 데이터베이스로부터 얻어와 반환한다. 
	 */
	public List<UserInquiryVO> selectInquiryByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException;

	/**
	 * @methodName : selectAllInquiryCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 5. 오후 6:08:43
	 * @description : 데이터베이스로부터 1:1 문의글 전체 목록 혹은 검색된 항목의 수를 얻어와 반한한다.
	 */
	public int selectInquiryCountByCondition(SearchCriteria sc) throws SQLException;
	
	/**
	 * @methodName : selectNextRefOfInquiryBoard
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 13. 오전 2:19:56
	 * @description : 문의 게시글을 insert 하기 위해 필요한 다음에 추가될 ref값을 얻어와 반환한다. 
	 */
	public int selectNextRefOfInquiryBoard() throws SQLException;
	
	/**
	 * @methodName : insertInquiry
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 10. 오전 1:30:55
	 * @description :  사용자 페이지에서 문의 작성 시 이 메서드를 통해 데이터베이스로 INSERT 요청을 한다. INSERT 된 ROW의 PK(inquiry_board_no)를 반환한다. 문제 발생 시 -1을 반환한다.
	 */
	public int insertInquiry(UserInquiryVO inquiry) throws SQLException;
	
	/**
	 * @methodName : selectInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 13. 오전 3:26:21
	 * @description : 넘겨받은 문의 게시글 번호로 해당 문의글을 데이터베이스에서 얻어와 반환한다.   
	 */
	public UserInquiryVO selectInquiryByInquiryNo(int inquiryBoardNo) throws SQLException;
	
	/**
	 * @methodName : updateInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 7:57:14
	 * @description : 넘겨받은 문의 게시글 정보로 해당 문의글을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean updateInquiryByInquiryNo(UserInquiryVO inquiry) throws SQLException;
	
	/**
	 * @methodName : deleteInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 7:57:17
	 * @description : 넘겨받은 문의 게시글 번호로 해당 문의글을 데이터베이스에서 찾아 삭제처리한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean deleteInquiryByInquiryNo(int inquiryBoardNo) throws SQLException;
	
	// 문의 관리
	/**
	 * @methodName : selectInquiriesByConditionCountInAdmin
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:54:10
	 * @description : 문의 관리 - 문의 관리 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectInquiriesByConditionCountInAdmin(SearchCriteriaBoard sc) throws SQLException;
	
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
	 * @date : 2023. 11. 23. 오후 8:07:21
	 * @description : 문의 관리 - 넘겨받은 페이지 정보와 검색 조건으로 문의글 목록을 데이터베이스에서 받아와 반환한다. 
	 */
	public List<AdminInquiryVO> selectInquiriesByConditionInAdmin(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException;

	/**
	 * @methodName : selectInquiryByInquiryNoInAdmin
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 11:46:13
	 * @description : 문의 관리 - 넘겨받은 문의글 번호로 문의글 상세정보를 데이터베이스에서 받아와 반한한다.
	 */
	public AdminInquiryVO selectInquiryByInquiryNoInAdmin(int inquiryBoardNo) throws SQLException;

	/**
	 * @methodName : updateRefOrder
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 24. 오전 3:58:59
	 * @description : 문의 관리 - 문의 답글 처리 시 같은 ref를 가진 글들의 refOrder를 +1씩 update한다. 
	 */
	public boolean updateRefOrder(UserInquiryVO inquiry) throws SQLException;

	/**
	 * @methodName : insertReplyInquiry
	 * @author : kyj
	 * @param inquiry
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 24. 오전 4:04:11
	 * @description : 문의 관리 - 문의 답글 처리
	 */
	public int insertReplyInquiry(UserInquiryVO inquiry) throws SQLException;;
	// ==================================================================================================================================================


}
