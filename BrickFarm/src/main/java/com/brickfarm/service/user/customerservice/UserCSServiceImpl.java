package com.brickfarm.service.user.customerservice;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.brickfarm.dao.faqboard.FaqBoardDAO;
import com.brickfarm.dao.faqcategory.FaqCategoryDAO;
import com.brickfarm.dao.inquiryboard.InquiryBoardDAO;
import com.brickfarm.dao.inquirycategory.InquiryCategoryDAO;
import com.brickfarm.dao.inquiryimages.InquiryImagesDAO;
import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.vo.user.kyj.UserFaqCategoryVO;
import com.brickfarm.vo.user.kyj.UserFaqVO;
import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;

@Service
public class UserCSServiceImpl implements UserCSService {
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
	private FaqBoardDAO fDao;
	
	@Inject
	private FaqCategoryDAO fcDao;
	
	@Inject
	private InquiryBoardDAO iDao;
	
	@Inject
	private InquiryCategoryDAO icDao;
	
	@Inject
	private InquiryImagesDAO iImgDao;
	
	/**
	 * @methodName : getAllFaqList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 4. 오전 1:03:07
	 * @description : FAQ 전체목록을 얻어와 반환한다.
	 */
	@Override
	public List<UserFaqVO> getAllFaqList() throws SQLException {
		return fDao.selectAllFaq();
	}

	/**
	 * @methodName : getAllFaqCategoryList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 5. 오전 11:18:25
	 * @description : FAQ 분류 전체목록을 얻어와 반환한다.
	 */
	@Override
	public List<UserFaqCategoryVO> getAllFaqCategoryList() throws SQLException {
		return fcDao.selectAllFaqCategory();
	}

	/**
	 * @methodName : createPaginationInfo
	 * @author : kyj
	 * @param curPageNo
	 * @param rowCountPerPage
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param curPageNo
	 * @returnValue : @param rowCountPerPage
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 10. 오전 12:30:45
	 * @description : 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	@Override
	public PaginationInfo createPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteria sc) throws SQLException {
		PaginationInfo pi = new PaginationInfo();
		
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		pi.setTotalRowCount(iDao.selectInquiryCountByCondition(sc));
		
		pi.paginationProcess();
		
		return pi;
	}

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
	 * @date : 2023. 10. 10. 오전 12:30:28
	 * @description : 선택적으로 받은 페이지 정보, 검색 조건을 베이스로 문의글 목록을 받아와 반환한다.
	 */
	@Override
	public List<UserInquiryVO> getInquiryListByCondition(PaginationInfo pi, SearchCriteria sc) throws SQLException {
		return iDao.selectInquiryByCondition(pi, sc);
	}

	/**
	 * @methodName : getAllInquiryCategoryList
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 10. 오전 12:30:11
	 * @description : 문의 게시판 분류 전체목록을 얻어와 반환한다.
	 */
	@Override
	public List<UserInquiryCategoryVO> getAllInquiryCategoryList() throws SQLException {
		return icDao.selectAllInquiryCategory();
	}

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
	 * @date : 2023. 10. 12. 오후 4:37:22
	 * @description : 전달받은 정보를 토대로 문의정보를 데이터베이스에 입력한다. 파일이 만약 첨부됐다면 첨부된 파일정보 또한 문의게시판 이미지 테이블에 입력한다. (트랜잭션 처리)
	 * 성공 시 insert된 row의 pk값, 실패 시 -1을 반환한다.
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public int createInquiry(UserInquiryVO inquiry, List<UserInquiryImagesVO> fileList) throws SQLException {
		int result = -1;
		
		inquiry.setRef(iDao.selectNextRefOfInquiryBoard());
		int inquiryBoardNo = iDao.insertInquiry(inquiry);
		if(inquiryBoardNo != -1) {
			if(fileList.size() > 0) {
				// 첨부된 파일이 있을 경우
				if(!iImgDao.insertInquiryImages(fileList, inquiryBoardNo)) {
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
		
		return result;
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
	 * @date : 2023. 10. 13. 오전 3:31:56
	 * @description : 전달받은 문의글 번호에 해당하는 문의게시글 정보를 가져와 반환한다.
	 */
	@Override
	public UserInquiryVO getInquiryByInquiryNo(int inquiryBoardNo) throws SQLException {
		return iDao.selectInquiryByInquiryNo(inquiryBoardNo);
	}

	/**
	 * @methodName : getInquiryImagesByInquiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 2:14:53
	 * @description : 전달받은 문의글 번호에 해당하는 문의게시글에 첨부되어있는 사진파일들의 정보를 가져와 반환한다.
	 */
	@Override
	public List<UserInquiryImagesVO> getInquiryImagesByInquiryNo(int inquiryBoardNo) throws SQLException {
		return iImgDao.selectInquiryImagesByInguiryNo(inquiryBoardNo);
	}

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
	 * @date : 2023. 10. 17. 오후 4:47:08
	 * @description : 전달받은 문의글 수정사항에 대한 정보들을 db에 적용시킨다. 첨부파일에 대한 변경사항이 있을 경우 다음과 같이 처리한다.
	 * 1. 기존에 있던 파일 정보가 수정된 파일 정보 내에 존재할 경우 : 데이터 유지
	 * 2. 기존에 있던 파일 정보가 수정된 파일 정보 내에 존재하지 않을 경우 : 데이터 삭제
	 * 3. 수정된 파일 정보가 현재 db 내에 존재하지 않을 경우 : 데이터 추가
	 * 예외가 발생 시 트랜잭션 rollback 처리를 위해 의도적으로 SQLException을 발생시킨다. 
	 * 모든 작업이 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public boolean modifyInquiry(UserInquiryVO inquiry, List<UserInquiryImagesVO> fileList, String realPath) throws SQLException {
		boolean result = false;
		
		if(iDao.updateInquiryByInquiryNo(inquiry)) {
			System.out.println("기본 데이터 수정 완료");
			if(fileList.size() > 0) {
				// 첨부된 파일이 있을 경우
				boolean fileProcessResult = true;
				
				List<UserInquiryImagesVO> oldFileList = iImgDao.selectInquiryImagesByInguiryNo(inquiry.getInquiry_board_no());
				
				for(UserInquiryImagesVO oldFile : oldFileList) {
					boolean isExist = false;
					// db에 이미 있는 파일 정보와 현재 넘어온 파일 정보 중 일치하는 항목이 있는지 탐색
					for(UserInquiryImagesVO newFile : fileList) {
						if(oldFile.getNew_file_name().equals(newFile.getNew_file_name())) {
							// 일치하는 항목이 존재 (그대로 현상 유지)
							System.out.println(oldFile.getNew_file_name() + " 파일은 변경사항이 없습니다. [유지]");
							isExist = true;
						}
					}
					if(!isExist) {
						// 현재 넘어온 파일 정보 내에서 db내의 파일 정보가 없을 경우 (삭제해야 한다.)
						System.out.println(oldFile.getNew_file_name() + " 파일이 삭제되었습니다. [삭제]");
						
						File deleteFile = new File(realPath + oldFile.getNew_file_name());
						if(deleteFile.exists()) {
							deleteFile.delete();
						}
						if(oldFile.getThumbnail_file_name() != null) {
							File thumbFile = new File(realPath + oldFile.getThumbnail_file_name());
							if (thumbFile.exists()) {
								thumbFile.delete();
							}
						}
						
						if(!iImgDao.deleteInquiryImagesByImageNo(oldFile.getInquiry_images_no())) {
							// delete 과정에서 문제 발생
							fileProcessResult = false;
							throw new SQLException("삭제한 파일 정보를 갱신하는 과정에서 문제가 발생하였습니다. 파일명 : " + oldFile.getOriginal_file_name());
						}
					}
				}
				
				for(UserInquiryImagesVO newFile : fileList) {
					if(iImgDao.selectInquiryImageByImageNo(newFile.getInquiry_images_no()) == null) {
						// 현재 넘어온 파일 정보 내에서 이 항목이 db에 없을 경우 (추가해야 한다.)
						System.out.println(newFile.getNew_file_name() + " 파일이 새로 추가되었습니다. [추가]");
						newFile.setInquiry_board_no(inquiry.getInquiry_board_no());
						if(!iImgDao.insertInquiryImage(newFile)) {
							// insert 과정에서 문제 발생
							fileProcessResult = false;
							throw new SQLException("새로 추가된 파일을 업로드하는 과정에서 문제가 발생하였습니다. 파일명 : " + newFile.getOriginal_file_name());
						}
					}
				}
				
				if(fileProcessResult) {
					result = true;
				}
			} else {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * @methodName : removeInquiry
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 21. 오후 6:44:30
	 * @description : 전달받은 문의글 번호에 해당하는 문의게시글을 삭제처리한다. 첨부파일이 있을 경우 파일을 모두 삭제하고 DB를 갱신한다.
	 * 과정에서 오류가 발생 시 변경사항을 적용해선 안되기 때문에 트랜잭션 처리를 하여 문의게시글의 논리적 삭제처리과정을 진행한다.
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public boolean removeInquiry(int inquiryBoardNo, String realPath) throws SQLException {
		boolean result = false;
		
		List<UserInquiryImagesVO> fileList = iImgDao.selectInquiryImagesByInguiryNo(inquiryBoardNo);
		if(iDao.deleteInquiryByInquiryNo(inquiryBoardNo)) {
			System.out.println("기본 데이터 수정 완료");
			// DB 삭제 처리 완료
			if(fileList.size() > 0) {
				// 첨부된 파일이 있을 경우
				boolean fileProcessResult = true;
				for(UserInquiryImagesVO file : fileList) {
					File deleteFile = new File(realPath + file.getNew_file_name());
					if(deleteFile.exists()) {
						deleteFile.delete();
					}
					System.out.println(file.getNew_file_name() + " 파일이 삭제되었습니다.");
					if(file.getThumbnail_file_name() != null) {
						File thumbFile = new File(realPath + file.getThumbnail_file_name());
						if (thumbFile.exists()) {
							thumbFile.delete();
						}
					}
					System.out.println(file.getThumbnail_file_name() + " 파일이 삭제되었습니다.");
					
					if(!iImgDao.deleteInquiryImagesByImageNo(file.getInquiry_images_no())) {
						// delete 과정에서 문제 발생
						fileProcessResult = false;
						throw new SQLException("삭제한 파일 정보를 갱신하는 과정에서 문제가 발생하였습니다. 파일명 : " + file.getOriginal_file_name());
					}
					
				}
				if(fileProcessResult) {
					// 파일 삭제 과정 정상적으로 완료, 실패시 예외를 날리므로 별도 처리 필요 없음
					result = true;
				}
			} else {
				// 첨부된 파일이 없을 경우
				result = true;
			}
		}
		
		return result;
	}
	
	
	// ==================================================================================================================================================
}
