package com.brickfarm.dao.noticecategory;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.vo.user.kyj.UserNoticeCategoryVO;

public interface NoticeCategoryDAO {
	/**
	 * @methodName : selectAllNoticeCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 19. 오후 6:46:28
	 * @description : 모든 공지사항 분류 정보 목록을 데이터베이스로부터 얻어와 반환한다. 
	 */
	public List<UserNoticeCategoryVO> selectAllNoticeCategory() throws SQLException;
	
	// 전달 받은 공지사항 분류명으로 공지사항 분류 항목을 데이터베이스에 추가한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean insertNoticeCategory(String noticeCategoryName) throws SQLException;
	
	// 전달 받은 공지사항 분류 정보로 해당하는 항목을 데이터베이스에서 찾아 수정한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean updateNoticeCategoryByCategoryNo(UserNoticeCategoryVO noticeCategory) throws SQLException;
	
	// 전달 받은 공지사항 분류 번호로 해당하는 항목을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	public boolean deleteNoticeCategoryByCategoryNo(int noticeCategoryNo) throws SQLException;
}
