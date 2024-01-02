package com.brickfarm.service.admin.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.vo.admin.kyj.board.AdminBoardsStatVO;
import com.brickfarm.vo.admin.kyj.board.AdminFaqVO;
import com.brickfarm.vo.admin.kyj.board.AdminInquiriesGraphDataVO;
import com.brickfarm.vo.admin.kyj.board.AdminInquiryVO;
import com.brickfarm.vo.admin.kyj.board.AdminNoticeVO;
import com.brickfarm.vo.user.kyj.UserFaqCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;
import com.brickfarm.vo.user.kyj.UserNoticeCategoryVO;

public interface AdminBoardService {
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
	/* === 게시판 대시보드 ============================================================================================================================================ */
	// 게시판 대시보드에 표현할 정보를 가져와 반환한다.
	public AdminBoardsStatVO getBoardsStat() throws SQLException;

	// 게시판 대시보드의 문의 현황 그래프에 표현할 정보를 가져와 반환한다.
	public List<AdminInquiriesGraphDataVO> getInquiriesGraphData() throws SQLException;
	
	/* === 게시판 관리 공용 =========================================================================================================================================== */
	/**
	 * @methodName : createPaginationInfo
	 * @author : kyj
	 * @param curPageNo
	 * @param rowCountPerPage
	 * @param sc
	 * @param inquiry
	 * @return
	 * @returnValue : @param curPageNo
	 * @returnValue : @param rowCountPerPage
	 * @returnValue : @param sc
	 * @returnValue : @param inquiry
	 * @returnValue : @return
	 * @date : 2023. 11. 23. 오후 8:47:37
	 * @description : 게시판 관리 - 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	public PaginationInfo createPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteriaBoard sc, int classification) throws SQLException, Exception;
	
	/* === 문의 관리 ================================================================================================================================================ */
	/**
	 * @methodName : getInquiriesByCondition
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:28:05
	 * @description : 문의 관리 - 넘겨받은 페이지 정보와 검색 조건으로 문의글 목록을 얻어와 반환한다.
	 */
	public List<AdminInquiryVO> getInquiriesByCondition(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException;

	/**
	 * @methodName : getInquiryByNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 11:41:35
	 * @description : 문의 관리 - 넘겨받은 문의글 번호로 문의글 상세정보를 얻어와 반한한다.
	 */
	public AdminInquiryVO getInquiryByInquiryNo(int inquiryBoardNo) throws SQLException;
	
	/**
	 * @methodName : createResponseInquiry
	 * @author : kyj
	 * @param inquiry
	 * @param fileList
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @param fileList
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 24. 오전 3:44:45
	 * @description : 전달받은 정보를 토대로 문의답글정보를 데이터베이스에 입력한다. 파일이 만약 첨부됐다면 첨부된 파일정보 또한 문의게시판 이미지 테이블에 입력한다. (트랜잭션 처리)
	 * 성공 시 insert된 row의 pk값, 실패 시 -1을 반환한다.
	 */
	public int createResponseInquiry(UserInquiryVO inquiry, List<UserInquiryImagesVO> fileList) throws SQLException;
	
	/* === 공지사항 관리 ============================================================================================================================================= */
	// 공지사항 관리 - 넘겨받은 페이지 정보와 검색 조건으로 공지사항 글 목록을 얻어와 반환한다.
	public List<AdminNoticeVO> getNoticesByCondition(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 게시글 번호로 공지사항 글 상세정보를 얻어와 반한한다.
	public AdminNoticeVO getNoticeByNoticeNo(int noticeBoardNo) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 값으로 공지사항 게시글을 작성한다.
	public boolean createNotice(AdminNoticeVO notice) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 게시글 번호에 해당하는 게시글을 삭제한다.
	public boolean removeNotice(int noticeBoardNo) throws SQLException;

	// 공지사항 관리 - 넘겨받은 값으로 공지사항 게시글을 수정한다.
	public boolean modifyNotice(AdminNoticeVO notice) throws SQLException;

	// 공지사항 관리 - 넘겨받은 값에 해당하는 게시글들을 상단 고정(혹은 해제) 처리 한다.
	public boolean fixNotice(Map<String, Object> params) throws SQLException;
	
	/* === 자주묻는 질문 관리 ========================================================================================================================================= */
	// 자주묻는 질문 관리 - 넘겨받은 페이지 정보와 검색 조건으로 자주묻는 질문 글 목록을 얻어와 반환한다.
	public List<AdminFaqVO> getFaqByCondition(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException;
	
	// 자주묻는 질문 관리 - 넘겨받은 게시글 번호로 자주묻는 질문 글 상세정보를 얻어와 반한한다.
	public AdminFaqVO getFaqByFaqNo(int faqBoardNo) throws SQLException;
	
	// 자주묻는 질문 관리 - 넘겨받은 값으로 자주묻는 질문 게시글을 작성한다.
	public boolean createFaq(AdminFaqVO faq) throws SQLException;
	
	// 자주묻는 질문 관리 - 넘겨받은 게시글 번호에 해당하는 게시글을 삭제한다.
	public boolean removeFaq(int faqBoardNo) throws SQLException;

	// 자주묻는 질문 관리 - 넘겨받은 값으로 자주묻는 질문 게시글을 수정한다.
	public boolean modifyFaq(AdminFaqVO faq) throws SQLException;
	
	/* === 분류 관리 ================================================================================================================================================ */
	/* -- 공지사항 -- */
	// 전달 받은 공지사항 분류명으로 공지사항 분류 항목을 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean createNoticeCategory(String noticeCategoryName) throws SQLException;
	
	// 전달 받은 공지사항 분류 정보로 해당하는 항목을 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean modifyNoticeCategoryByCategoryNo(UserNoticeCategoryVO noticeCategory) throws SQLException;
	
	// 전달 받은 공지사항 분류 번호로 해당하는 항목을 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean removeNoticeCategoryByCategoryNo(int noticeCategoryNo) throws SQLException;
	
	/* -- 문의 -- */
	// 전달 받은 문의 분류명으로 문의 분류 항목을 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean createInquiryCategory(String inquiryCategoryName) throws SQLException;
	
	// 전달 받은 문의 분류 정보로 해당하는 항목을 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean modifyInquiryCategoryByCategoryNo(UserInquiryCategoryVO inquiryCategory) throws SQLException;
	
	// 전달 받은 문의 분류 번호로 해당하는 항목을 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean removeInquiryCategoryByCategoryNo(int inquiryCategoryNo) throws SQLException;

	/* -- 자주묻는 질문 -- */
	// 전달 받은 자주묻는 질문 분류명으로 자주묻는 질문 분류 항목을 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean createFaqCategory(String faqCategoryName) throws SQLException;
	
	// 전달 받은 자주묻는 질문 분류 정보로 해당하는 항목을 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean modifyFaqCategoryByCategoryNo(UserFaqCategoryVO faqCategory) throws SQLException;
	
	// 전달 받은 자주묻는 질문 분류 번호로 해당하는 항목을 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean removeFaqCategoryByCategoryNo(int faqCategoryNo) throws SQLException;
		
	// ==================================================================================================================================================
}
