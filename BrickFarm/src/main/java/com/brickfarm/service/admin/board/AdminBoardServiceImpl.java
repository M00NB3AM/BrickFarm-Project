package com.brickfarm.service.admin.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.brickfarm.dao.faqboard.FaqBoardDAO;
import com.brickfarm.dao.faqcategory.FaqCategoryDAO;
import com.brickfarm.dao.inquiryboard.InquiryBoardDAO;
import com.brickfarm.dao.inquirycategory.InquiryCategoryDAO;
import com.brickfarm.dao.inquiryimages.InquiryImagesDAO;
import com.brickfarm.dao.noticeboard.NoticeBoardDAO;
import com.brickfarm.dao.noticecategory.NoticeCategoryDAO;
import com.brickfarm.dao.statistics.StatisticsDAO;
import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.etc.kyj.board.constant.BoardClassification;
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

@Service
public class AdminBoardServiceImpl implements AdminBoardService {

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
	private InquiryBoardDAO iDao;

	@Inject
	private InquiryCategoryDAO icDao;
	
	@Inject
	private InquiryImagesDAO iImgDao;
	
	@Inject
	private NoticeBoardDAO nDao;
	
	@Inject
	private NoticeCategoryDAO ncDao;
	
	@Inject
	private FaqBoardDAO fDao;
	
	@Inject
	private FaqCategoryDAO fcDao;
	
	@Inject
	private StatisticsDAO statDao;

	/* === 게시판 대시보드 ============================================================================================================================================ */
	// 게시판 대시보드에 표현할 정보를 가져와 반환한다.
	@Override
	public AdminBoardsStatVO getBoardsStat() throws SQLException {
		return statDao.selectBoardsStat();
	}
	
	// 게시판 대시보드의 문의 현황 그래프에 표현할 정보를 가져와 반환한다.
	@Override
	public List<AdminInquiriesGraphDataVO> getInquiriesGraphData() throws SQLException {
		return statDao.selectInquiriesGraphData();
	}
	
	/* === 게시판 관리 공용 =========================================================================================================================================== */
	// 게시판 관리 - 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	@Override
	public PaginationInfo createPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteriaBoard sc,
			int classification) throws SQLException, Exception {
		PaginationInfo pi = new PaginationInfo();
		
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		switch (classification) {
		case BoardClassification.NOTICE:
			pi.setTotalRowCount(nDao.selectNoticesByConditionCountInAdmin(sc));
			break;
		case BoardClassification.INQUIRY:
			pi.setTotalRowCount(iDao.selectInquiriesByConditionCountInAdmin(sc));
			break;
		case BoardClassification.FAQ:
			pi.setTotalRowCount(fDao.selectFaqByConditionCountInAdmin(sc));
			break;
		default:
			throw new Exception("선택된 구분에 문제가 있습니다. 선택된 게시판 구분값 : " + classification);
		}
		
		pi.paginationProcess();
		
		return pi;
	}
	
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
	 * @date : 2023. 11. 23. 오후 8:29:12
	 * @description : 넘겨받은 페이지 정보와 검색 조건으로 문의글 목록을 얻어와 반환한다.
	 */
	@Override
	public List<AdminInquiryVO> getInquiriesByCondition(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException {
		return iDao.selectInquiriesByConditionInAdmin(pi, sc);
	}
	
	/**
	 * @methodName : getInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 11:46:29
	 * @description : 문의 관리 - 넘겨받은 문의글 번호로 문의글 상세정보를 얻어와 반한한다.
	 */
	@Override
	public AdminInquiryVO getInquiryByInquiryNo(int inquiryBoardNo) throws SQLException {
		return iDao.selectInquiryByInquiryNoInAdmin(inquiryBoardNo);
	}

	// 전달받은 정보를 토대로 문의답글정보를 데이터베이스에 입력한다. 파일이 만약 첨부됐다면 첨부된 파일정보 또한 문의게시판 이미지 테이블에 입력한다. (트랜잭션 처리)
	// 성공 시 insert된 row의 pk값, 실패 시 -1을 반환한다.
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public int createResponseInquiry(UserInquiryVO inquiry, List<UserInquiryImagesVO> fileList) throws SQLException {
		int result = -1;
		
		if (iDao.updateRefOrder(inquiry)) {
			int inquiryBoardNo = iDao.insertReplyInquiry(inquiry);
			if (inquiryBoardNo != -1) {
				if (fileList.size() > 0) {
					// 첨부된 파일이 있을 경우
					if (!iImgDao.insertInquiryImages(fileList, inquiryBoardNo)) {
						// 파일 정보를 데이터베이스에 넣는 작업이 실패 했을 때
						result = -1;
						throw new SQLException("파일 정보를 데이터베이스에 입력하는 과정에서 문제가 발생했습니다.");
					} else {
						result = inquiryBoardNo;
					}
				} else {
					result = inquiryBoardNo;
				}
			}
		} else {
			throw new SQLException("refOrder update 과정에서 문제가 발생했습니다.");
		}
		
		return result;
	}
	
	/* === 공지사항 관리 ============================================================================================================================================= */
	// 공지사항 관리 - 넘겨받은 페이지 정보와 검색 조건으로 공지사항 글 목록을 얻어와 반환한다.
	@Override
	public List<AdminNoticeVO> getNoticesByCondition(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException {
		return nDao.selectNoticesByConditionInAdmin(pi, sc);
	}

	// 공지사항 관리 - 넘겨받은 게시글 번호로 공지사항 글 상세정보를 얻어와 반한한다.
	@Override
	public AdminNoticeVO getNoticeByNoticeNo(int noticeBoardNo) throws SQLException {
		return nDao.selectNoticeByNoticeBoardNoInAdmin(noticeBoardNo);
	}

	// 공지사항 관리 - 넘겨받은 값으로 공지사항 게시글을 작성한다.
	@Override
	public boolean createNotice(AdminNoticeVO notice) throws SQLException {
		boolean result = false;
		if (nDao.insertNotice(notice) == 1) {
			result = true;
		}
		return result;
	}

	// 공지사항 관리 - 넘겨받은 게시글 번호에 해당하는 게시글을 삭제한다.
	@Override
	public boolean removeNotice(int noticeBoardNo) throws SQLException {
		boolean result = false;
		if (nDao.deleteNoticeByNoticeBoardNo(noticeBoardNo) == 1) {
			result = true;
		}
		return result;
	}

	// 공지사항 관리 - 넘겨받은 값으로 공지사항 게시글을 수정한다.
	@Override
	public boolean modifyNotice(AdminNoticeVO notice) throws SQLException {
		boolean result = false;
		if (nDao.updateNotice(notice) == 1) {
			result = true;
		}
		return result;
	}

	// 공지사항 관리 - 넘겨받은 값에 해당하는 게시글들을 상단 고정(혹은 해제) 처리 한다.
	@Override
	public boolean fixNotice(Map<String, Object> params) throws SQLException {
		boolean result = false;
		int size = ((ArrayList<Integer>) params.get("noticeBoardNoList")).size();
		if (nDao.updateNoticeIsFixed(params) == size) {
			result = true;
		}
		return result;
	}

	/* === 자주묻는 질문 관리 ========================================================================================================================================= */
	// 자주묻는 질문 관리 - 넘겨받은 페이지 정보와 검색 조건으로 자주묻는 질문 글 목록을 얻어와 반환한다.
	@Override
	public List<AdminFaqVO> getFaqByCondition(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException {
		return fDao.selectFaqByConditionInAdmin(pi, sc);
	}

	// 자주묻는 질문 관리 - 넘겨받은 게시글 번호로 자주묻는 질문 글 상세정보를 얻어와 반한한다.
	@Override
	public AdminFaqVO getFaqByFaqNo(int faqBoardNo) throws SQLException {
		return fDao.selectFaqByFaqBoardNoInAdmin(faqBoardNo);
	}

	// 자주묻는 질문 관리 - 넘겨받은 값으로 자주묻는 질문 게시글을 작성한다.
	@Override
	public boolean createFaq(AdminFaqVO faq) throws SQLException {
		boolean result = false;
		if (fDao.insertFaq(faq) == 1) {
			result = true;
		}
		return result;
	}

	// 자주묻는 질문 관리 - 넘겨받은 게시글 번호에 해당하는 게시글을 삭제한다.
	@Override
	public boolean removeFaq(int faqBoardNo) throws SQLException {
		boolean result = false;
		if (fDao.deleteFaqByFaqBoardNo(faqBoardNo) == 1) {
			result = true;
		}
		return result;
	}

	// 자주묻는 질문 관리 - 넘겨받은 값으로 자주묻는 질문 게시글을 수정한다.
	@Override
	public boolean modifyFaq(AdminFaqVO faq) throws SQLException {
		boolean result = false;
		if (fDao.updateFaq(faq) == 1) {
			result = true;
		}
		return result;
	}
	
	/* === 분류 관리 ================================================================================================================================================ */
	/* -- 공지사항 -- */
	// 전달 받은 공지사항 분류명으로 공지사항 분류 항목을 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean createNoticeCategory(String noticeCategoryName) throws SQLException {
		return ncDao.insertNoticeCategory(noticeCategoryName);
	}

	// 전달 받은 공지사항 분류 정보로 해당하는 항목을 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean modifyNoticeCategoryByCategoryNo(UserNoticeCategoryVO noticeCategory) throws SQLException {
		return ncDao.updateNoticeCategoryByCategoryNo(noticeCategory);
	}

	// 전달 받은 공지사항 분류 번호로 해당하는 항목을 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean removeNoticeCategoryByCategoryNo(int noticeCategoryNo) throws SQLException {
		return ncDao.deleteNoticeCategoryByCategoryNo(noticeCategoryNo);
	}

	/* -- 문의 -- */
	// 전달 받은 문의 분류명으로 문의 분류 항목을 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean createInquiryCategory(String inquiryCategoryName) throws SQLException {
		return icDao.insertInquiryCategory(inquiryCategoryName);
	}

	// 전달 받은 문의 분류 정보로 해당하는 항목을 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean modifyInquiryCategoryByCategoryNo(UserInquiryCategoryVO inquiryCategory) throws SQLException {
		return icDao.updateInquiryCategoryByCategoryNo(inquiryCategory);
	}

	// 전달 받은 문의 분류 번호로 해당하는 항목을 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean removeInquiryCategoryByCategoryNo(int inquiryCategoryNo) throws SQLException {
		return icDao.deleteInquiryCategoryByCategoryNo(inquiryCategoryNo);
	}

	/* -- 자주묻는 질문 -- */
	// 전달 받은 자주묻는 질문 분류명으로 자주묻는 질문 분류 항목을 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean createFaqCategory(String faqCategoryName) throws SQLException {
		return fcDao.insertFaqCategory(faqCategoryName);
	}

	// 전달 받은 자주묻는 질문 분류 정보로 해당하는 항목을 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean modifyFaqCategoryByCategoryNo(UserFaqCategoryVO faqCategory) throws SQLException {
		return fcDao.updateFaqCategoryByCategoryNo(faqCategory);
	}

	// 전달 받은 자주묻는 질문 분류 번호로 해당하는 항목을 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	@Override
	public boolean removeFaqCategoryByCategoryNo(int faqCategoryNo) throws SQLException {
		return fcDao.deleteFaqCategoryByCategoryNo(faqCategoryNo);
	}

	// ==================================================================================================================================================
}
