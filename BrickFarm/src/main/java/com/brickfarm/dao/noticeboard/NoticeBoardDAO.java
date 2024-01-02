package com.brickfarm.dao.noticeboard;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.vo.admin.kyj.board.AdminNoticeVO;
import com.brickfarm.vo.user.kyj.UserNoticeVO;

public interface NoticeBoardDAO {
	
	/**
	 * @methodName : selectNoticeByCondition
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 2. 오후 10:40:08
	 * @description : 선택적으로 페이지 정보, 검색 조건을 받아 조건에 맞는 공지사항 목록을 데이터베이스로부터 얻어와 반환한다. 
	 */
	public List<UserNoticeVO> selectNoticeByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException;

	/**
	 * @methodName : selectAllNoticeCount
	 * @author : kyj
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 10. 3. 오후 5:44:35
	 * @description : 데이터베이스로부터 조건에 맞는 공지사항 목록의 수를 얻어와 반환한다. 
	 */
	public int selectNoticeCountByCondition(SearchCriteria sc) throws SQLException;
	
	/**
	 * @methodName : selectFixedNotice
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 3. 오후 7:41:50
	 * @description : 데이터베이스로부터 상단 고정할 공지사항의 목록을 얻어와 반환한다. 
	 */
	public List<UserNoticeVO> selectFixedNotice() throws SQLException;

	/**
	 * @methodName : selectNoticeByNoticeBoardNo
	 * @author : kyj
	 * @param noticeBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param noticeBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 19. 오후 5:44:20
	 * @description : 넘겨받은 공지사항 게시글 번호로 해당 게시글을 데이터베이스에서 얻어와 반환한다. 
	 */
	public UserNoticeVO selectNoticeByNoticeBoardNo(int noticeBoardNo) throws SQLException;

	/* === 공지사항 관리 ================================================================================================================================================ */
	// 공지사항 관리 - 공지사항 관리 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	public int selectNoticesByConditionCountInAdmin(SearchCriteriaBoard sc) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 페이지 정보와 검색 조건으로 게시글 목록을 데이터베이스에서 받아와 반환한다.
	public List<AdminNoticeVO> selectNoticesByConditionInAdmin(PaginationInfo pi, SearchCriteriaBoard sc) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 공지사항 글번호로 게시글 상세정보를 데이터베이스에서 받아와 반한한다.
	public AdminNoticeVO selectNoticeByNoticeBoardNoInAdmin(int noticeBoardNo) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 값으로 공지사항 게시글을 데이터베이스에 추가한다.
	public int insertNotice(AdminNoticeVO notice) throws SQLException;
	
	// 공지사항 관리 - 넘겨받은 게시글 번호에 해당하는 게시글을 데이터베이스에서 삭제한다.
	public int deleteNoticeByNoticeBoardNo(int noticeBoardNo) throws SQLException;

	// 공지사항 관리 - 넘겨받은 값에 해당하는 공지사항 게시글을 데이터베이스에서 수정한다. 
	public int updateNotice(AdminNoticeVO notice) throws SQLException;

	// 공지사항 관리 - 넘겨받은 값에 해당하는 게시글들의 상단 고정 여부를 데이터베이스에서 수정한다.
	public int updateNoticeIsFixed(Map<String, Object> params) throws SQLException;
}
