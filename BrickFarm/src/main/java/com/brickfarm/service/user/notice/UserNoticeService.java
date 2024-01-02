package com.brickfarm.service.user.notice;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.vo.user.kyj.UserNoticeCategoryVO;
import com.brickfarm.vo.user.kyj.UserNoticeVO;

public interface UserNoticeService {
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
	 * @methodName : initNotice
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 2. 오후 7:14:21
	 * @description : 선택적으로 받은 페이지 정보, 검색 조건을 베이스로 공지사항 목록을 받아와 반환한다.
	 */
	public List<UserNoticeVO> getNoticeListByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException;
	
	/**
	 * @methodName : createPaginationInfo
	 * @author : kyj
	 * @param curPageNo
	 * @param rowCountPerPage
	 * @return
	 * @throws SQLException
	 * @returnValue : @param curPageNo
	 * @returnValue : @param rowCountPerPage
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 3. 오후 5:41:13
	 * @description : 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	public PaginationInfo createPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteria sc) throws SQLException;
	
	/**
	 * @methodName : getFixedNoticeList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 3. 오후 7:46:27
	 * @description : 상단 고정할 공지사항 목록을 받아와 반환한다.
	 */
	public List<UserNoticeVO> getFixedNoticeList() throws SQLException;

	/**
	 * @methodName : getNoticeByNoticeBoardNo
	 * @author : kyj
	 * @param noticeBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param noticeBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 19. 오후 5:41:20
	 * @description : 전달받은 공지사항 글 번호에 해당하는 공지사항 게시글 정보를 가져와 반환한다. 
	 */
	public UserNoticeVO getNoticeByNoticeBoardNo(int noticeBoardNo) throws SQLException;
	
	/**
	 * @methodName : getAllNoticeCategoryList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 19. 오후 6:49:55
	 * @description : 모든 공지사항 분류 정보 목록을 얻어와 반환한다.
	 */
	public List<UserNoticeCategoryVO> getAllNoticeCategoryList() throws SQLException;
	// ==================================================================================================================================================
}
