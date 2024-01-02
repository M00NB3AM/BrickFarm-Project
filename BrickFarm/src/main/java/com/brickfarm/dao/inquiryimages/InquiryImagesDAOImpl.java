package com.brickfarm.dao.inquiryimages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;

@Repository
public class InquiryImagesDAOImpl implements InquiryImagesDAO {
	
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.InquiryImagesMapper.";

	/**
	 * @methodName : insertInquiryImages
	 * @author : kyj
	 * @param fileList
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param fileList
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 12. 오후 4:43:00
	 * @description : 문의 작성 시 첨부한 파일들의 목록을 받아 데이터베이스에 insert 쿼리문을 수행한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean insertInquiryImages(List<UserInquiryImagesVO> fileList, int inquiryBoardNo) throws SQLException {
		boolean result = false;
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> paramFileList = new ArrayList<Map<String,Object>>();
		for(UserInquiryImagesVO iImg : fileList) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("inquiry_board_no", inquiryBoardNo);
			item.put("original_file_name", iImg.getOriginal_file_name());
			item.put("new_file_name", iImg.getNew_file_name());
			item.put("file_size", iImg.getFile_size());
			item.put("thumbnail_file_name", iImg.getThumbnail_file_name());
			paramFileList.add(item);
		}
		params.put("fileList", paramFileList);
		// System.out.println(params.toString());
		int insertResult = ses.insert(ns + "insertInquiryImages", params);
		// System.out.println("INSERT에 성공한 첨부 이미지 정보 수 : " + insertResult);
		if(insertResult == fileList.size()) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean insertInquiryImage(UserInquiryImagesVO newFile) throws SQLException {
		boolean result = false;
		if(ses.insert(ns + "insertInquiryImage", newFile) == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : selectInquiryImagesByInguiryNo
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:17:48
	 * @description : inquiry_images 테이블에서 전달받은 문의글 번호에 해당하는 데이터들을 얻어와 반환한다.
	 */
	@Override
	public List<UserInquiryImagesVO> selectInquiryImagesByInguiryNo(int inquiryBoardNo) throws SQLException {
		return ses.selectList(ns + "selectInquiryImagesByInquiryNo", inquiryBoardNo);
	}

	/**
	 * @methodName : selectInquiryImagesByImageNo
	 * @author : kyj
	 * @param inquiryImageNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryImageNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 16. 오후 7:24:59
	 * @description : inquiry_images 테이블에서 전달받은 사진 파일 번호에 해당하는 데이터를 얻어와 반환한다.
	 */
	@Override
	public UserInquiryImagesVO selectInquiryImageByImageNo(int inquiryImageNo) throws SQLException {
		return ses.selectOne(ns + "selectInquiryImageByImageNo", inquiryImageNo);
	}
	
	@Override
	public UserInquiryImagesVO selectInquiryImageByFileName(String newFileName) throws SQLException {
		return ses.selectOne(ns + "selectInquiryImageByFileName", newFileName);
	}

	/**
	 * @methodName : deleteInquiryImagesByImageNo
	 * @author : kyj
	 * @param inquiryImagesNo
	 * @param numberOfImages
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryImagesNo
	 * @returnValue : @param numberOfImages
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 15. 오후 8:17:50
	 * @description : 전달 받은 문의글 번호로 관련된 사진 정보들을 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean deleteInquiryImagesByInquiryNo(int inquiryBoardNo, int numberOfImages) throws SQLException {
		boolean result = false;
		if(ses.delete(ns + "deleteInquiryImagesByInquiryNo", inquiryBoardNo) == numberOfImages) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : deleteInquiryImagesByImageNo
	 * @author : kyj
	 * @param inquiryImageNo
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryImageNo
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 16. 오후 7:19:00
	 * @description : 전달 받은 사진 번호와 일치하는 사진 정보를 데이터베이스에서 찾아 삭제한다. 성공 시 true, 실패 시 false를 반환한다.
	 */
	@Override
	public boolean deleteInquiryImagesByImageNo(int inquiryImageNo) throws SQLException {
		boolean result = false;
		if(ses.delete(ns + "deleteInquiryImagesByImageNo", inquiryImageNo) == 1) {
			result = true;
		}
		return result;
	}
	

}
