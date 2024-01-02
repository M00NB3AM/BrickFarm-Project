package com.brickfarm.service.user.customerservice;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.vo.user.kyj.UserFaqVO;
import com.brickfarm.vo.user.kyj.UserFaqCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;
import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;

public interface UserCSService {
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
	 * @methodName : getAllFaqList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 4. 오전 1:01:13
	 * @description : FAQ 전체목록을 얻어와 반환한다. 
	 */
	public List<UserFaqVO> getAllFaqList() throws SQLException;
	
	/**
	 * @methodName : getAllFaqCategoryList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 5. 오전 11:17:39
	 * @description : FAQ 분류 전체목록을 얻어와 반환한다. 
	 */
	public List<UserFaqCategoryVO> getAllFaqCategoryList() throws SQLException;

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
	 * @date : 2023. 10. 6. 오후 5:16:09
	 * @description : 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	public PaginationInfo createPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteria sc) throws SQLException;

	/**
	 * @methodName : getInquiryListByCondition
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 6. 오후 5:16:57
	 * @description : 선택적으로 받은 페이지 정보, 검색 조건을 베이스로 문의글 목록을 받아와 반환한다.
	 */
	public List<UserInquiryVO> getInquiryListByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException;

	/**
	 * @methodName : getAllInquiryCategoryList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 6. 오후 5:17:24
	 * @description : 문의 게시판 분류 전체목록을 얻어와 반환한다. 
	 */
	public List<UserInquiryCategoryVO> getAllInquiryCategoryList() throws SQLException;
	
	/**
	 * @methodName : createInquiry
	 * @author : kyj
	 * @param inquiry
	 * @param fileList
	 * @return
	 * @throws Exception
	 * @returnValue : @param inquiry
	 * @returnValue : @param fileList
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 12. 오후 4:13:26
	 * @description : 전달받은 정보를 토대로 문의정보를 데이터베이스에 입력한다. 파일이 만약 첨부됐다면 첨부된 파일정보 또한 문의게시판 이미지 테이블에 입력한다. (트랜잭션 처리)
	 * 성공 시 insert된 row의 pk값, 실패 시 -1을 반환한다.
	 */
	public int createInquiry(UserInquiryVO inquiry, List<UserInquiryImagesVO> fileList) throws SQLException;

	/**
	 * @methodName : getInquiryByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 13. 오전 3:30:42
	 * @description : 전달받은 문의글 번호에 해당하는 문의게시글 정보를 가져와 반환한다. 
	 */
	public UserInquiryVO getInquiryByInquiryNo(int inquiryBoardNo) throws SQLException;

	/**
	 * @methodName : getInquiryImagesByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 2:12:54
	 * @description : 전달받은 문의글 번호에 해당하는 문의게시글에 첨부되어있는 사진파일들의 정보를 가져와 반환한다.
	 */
	public List<UserInquiryImagesVO> getInquiryImagesByInquiryNo(int inquiryBoardNo) throws SQLException;

	/**
	 * @methodName : modifyInquiry
	 * @author : kyj
	 * @param inquiry
	 * @param fileList
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiry
	 * @returnValue : @param fileList
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 17. 오후 4:42:32
	 * @description : 전달받은 문의글 수정사항에 대한 정보들을 db에 적용시킨다. 첨부파일에 대한 변경사항이 있을 경우 다음과 같이 처리한다.
	 * 1. 기존에 있던 파일 정보가 수정된 파일 정보 내에 존재할 경우 : 데이터 유지
	 * 2. 기존에 있던 파일 정보가 수정된 파일 정보 내에 존재하지 않을 경우 : 데이터 삭제
	 * 3. 수정된 파일 정보가 현재 db 내에 존재하지 않을 경우 : 데이터 추가
	 * 예외가 발생 시 트랜잭션 rollback 처리를 위해 의도적으로 SQLException을 발생시킨다. 
	 * 모든 작업이 성공 시 true, 실패 시 false를 반환한다.
	 */
	public boolean modifyInquiry(UserInquiryVO inquiry, List<UserInquiryImagesVO> fileList, String realPath) throws SQLException;
	
	/**
	 * @methodName : removeInquiry
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 21. 오후 6:43:34
	 * @description : 전달받은 문의글 번호에 해당하는 문의게시글을 삭제처리한다.
	 */
	public boolean removeInquiry(int inquiryBoardNo, String realPath) throws SQLException;
	
	
	
	// ==================================================================================================================================================
}
