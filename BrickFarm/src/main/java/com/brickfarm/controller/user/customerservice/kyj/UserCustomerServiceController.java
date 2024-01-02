package com.brickfarm.controller.user.customerservice.kyj;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.brickfarm.dto.user.kyj.UserUploadFileApiResponseDTO;
import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.etc.kyj.UploadFileProcess;
import com.brickfarm.service.user.customerservice.UserCSService;
import com.brickfarm.vo.user.kyj.UserFaqVO;
import com.brickfarm.vo.user.kyj.UserFaqCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;

@Controller
@RequestMapping("/customer-service/*")
public class UserCustomerServiceController {
//	private static final Logger logger = LoggerFactory.getLogger(UserCustomerServiceController.class);

	@Inject
	private UserCSService csService;

	// private List<UserInquiryImagesVO> fileList = new ArrayList<UserInquiryImagesVO>();

	/**
	 * @methodName : faqList
	 * @author : kyj
	 * @param model
	 * @return
	 * @throws SQLException
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 4. 오전 1:16:02
	 * @description : 고객지원 메뉴의 자주 묻는 질문(FAQ) 페이지와 매핑된 메서드, DB로부터 받아온 데이터로 FAQ 페이지를
	 *              응답해준다.
	 */
	@GetMapping("faq")
	public String faqList(Model model) throws SQLException {
		// logger.info("자주 묻는 질문 페이지 요청");
		List<UserFaqVO> faqList = csService.getAllFaqList();
		List<UserFaqCategoryVO> faqCategoryList = csService.getAllFaqCategoryList();

		model.addAttribute("faqList", faqList);
		model.addAttribute("faqCategoryList", faqCategoryList);

		return "/user/customer-service/faq";
	}

	/**
	 * @methodName : inquiryList
	 * @author : kyj
	 * @param model
	 * @return
	 * @throws SQLException
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 5. 오후 4:15:49
	 * @description : 문의하기 목록 페이지와 매핑된 메서드, DB로부터 받아온 데이터로 문의글 게시판 페이지를 응답해준다.
	 */
	@GetMapping("inquiry/list")
	public String inquiryList(
			Model model,
			HttpSession ses,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "searchType", defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord,
			@RequestParam(value = "isMine", defaultValue = "false") boolean isMine) throws SQLException {
		// logger.info("문의하기 메인 페이지 요청");

		SearchCriteria sc = SearchCriteria.builder()
				.searchType(searchType)
				.searchWord(searchWord)
				.build();

		if(isMine) {
			// System.out.println(isMine);
			UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
			if(member == null) {
				model.addAttribute("status", "noPermission");
				return "redirect:/customer-service/inquiry/list";
			}
			sc.setMember_no(member.getMember_no());
		}
		
		PaginationInfo pi = csService.createPaginationInfo(curPageNo, rowCountPerPage, sc);
		// System.out.println(pi.toString());
		// System.out.println(sc.toString());

		List<UserInquiryVO> inquiryList = csService.getInquiryListByCondition(pi, sc);
//		List<InquiryCategory> inquiryCategoryList = csService.getAllInquiryCategoryList();

		model.addAttribute("inquiryList", inquiryList);
//		model.addAttribute("inquiryCategoryList", inquiryCategoryList);
		model.addAttribute("pagingInfo", pi);
		model.addAttribute("searchInfo", sc);

		return "/user/customer-service/inquiryList";
	}
	
	/**
	 * @methodName : inquiryDetail
	 * @author : kyj
	 * @param model
	 * @param inquiryBoardNo
	 * @return
	 * @returnValue : @param model
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 6:44:44
	 * @description : 문의하기 상세 게시글 페이지와 매핑된 메서드, DB로부터 받아온 데이터로 문의글 상세 페이지를 응답해준다.
	 * 페이지 응답에 문제가 생길 경우 다음과 같이 처리한다. (리다이렉트 경로, status 파라메터)
	 * - 로그인 되어 있지 않은 경우 : /customer-service/inquiry/list, noPermission
	 * - 게시글이 존재하지 않는 경우 : /customer-service/inquiry/list, notFound
	 * - 해당 게시글의 작성자가 아닌 경우 : /customer-service/inquiry/list, notWriter
	 * - 에러 발생 시 : /customer-service/inquiry/list, serverError
	 */
	@GetMapping("inquiry/detail")
	public String inquiryDetail(
			@RequestParam(value = "no", defaultValue = "0") int inquiryBoardNo,
			Model model,
			HttpSession ses) {
		// logger.info("문의글 상세정보 요청");
		
		UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
		if(member == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/customer-service/inquiry/list";
		}
		
		if(inquiryBoardNo == 0) {
			model.addAttribute("status", "notFound");
			return "redirect:/customer-service/inquiry/list";
		}
		
		try {
			UserInquiryVO inquiry = csService.getInquiryByInquiryNo(inquiryBoardNo);
			if(inquiry.getInquiry_board_no() != inquiry.getRef()) {
				UserInquiryVO parentInquiry = csService.getInquiryByInquiryNo(inquiry.getRef());
				if(parentInquiry.getMember_no() != member.getMember_no()) {
					model.addAttribute("status", "notWriter");
					return "redirect:/customer-service/inquiry/list";
				}
			} else if(inquiry.getMember_no() != member.getMember_no()) {
				model.addAttribute("status", "notWriter");
				return "redirect:/customer-service/inquiry/list"; 
			}
			
			if(inquiry != null) {
				if(inquiry.getIs_delete().equals("N")) {
					List<UserInquiryImagesVO> inquiryImages = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);
					model.addAttribute("inquiry", inquiry);
					model.addAttribute("inquiryImages", inquiryImages);
				} else {
					model.addAttribute("status", "notFound");
					return "redirect:/customer-service/inquiry/list";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status", "serverError");
			return "redirect:/customer-service/inquiry/list"; 
		}
		
		return "/user/customer-service/inquiryDetail";
	}

	/**
	 * @methodName : writeInquiry
	 * @author : kyj
	 * @param model
	 * @return
	 * @throws SQLException
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 10. 오전 1:02:52
	 * @description : 문의 작성 페이지와 매핑된 메서드, GET 방식으로 해당
	 * url(/customer-service/inquiry/write)을 요청 시 문의 작성 페이지를 응답해준다.
	 * - 로그인 되어 있지 않은 경우 : /customer-service/inquiry/list, noPermission
	 */
	@GetMapping("inquiry/write")
	public String writeInquiry(Model model, HttpSession ses) throws SQLException {
		// logger.info("문의 작성 페이지 요청");

		UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
		if(member == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/customer-service/inquiry/list";
		}

		List<UserInquiryCategoryVO> inquiryCategoryList = csService.getAllInquiryCategoryList();

		model.addAttribute("inquiryCategoryList", inquiryCategoryList);

		return "/user/customer-service/writeInquiry";
	}

	/**
	 * @methodName : writeInquiryProcess
	 * @author : kyj
	 * @param title
	 * @param inquiryCategoryNo
	 * @param content
	 * @return
	 * @throws SQLException
	 * @returnValue : @param title
	 * @returnValue : @param inquiryCategoryNo
	 * @returnValue : @param content
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 10. 오전 1:13:43
	 * @description : 문의 작성 페이지에서 입력폼을 통해 POST 방식으로 해당
	 * url(/customer-service/inquiry/write)을 요청 시 문의 작성 처리 서비스를 실행한다.
	 * 페이지 응답에 문제가 생길 경우 다음과 같이 처리한다. (리다이렉트 경로, status 파라메터)
	 * - 로그인 되어 있지 않은 경우 : /customer-service/inquiry/list, noPermission
	 * - 파라메터 값이 유효하지 않은 경우 : /customer-service/inquiry/write, invalid
	 * - insert 과정에러 에러 발생 시 : /customer-service/inquiry/write, insertFailed
	 * - 에러 발생 시 : /customer-service/inquiry/write, serverError
	 */
	@PostMapping("inquiry/write")
	public String writeInquiryProcess(@RequestParam(value = "title") String title,
			@RequestParam(value = "inquiryCategoryNo") int inquiryCategoryNo,
			@RequestParam(value = "content") String content,
			HttpSession ses,
			Model model,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("문의 작성 처리 요청");
		int insertedInquiryBoardNo = -1;

		UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
		if(member == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/customer-service/inquiry/list";
		}
		
		//유효성 검사
		boolean isValid = true;
		if(title == null || title.length() == 0) {
			isValid = false;
		}
		if(content == null || content.length() == 0) {
			isValid = false;
		}
		if(!isValid) {
			// logger.error("값이 유효하지 않습니다.");
			model.addAttribute("status", "invalid");
			return "redirect:/customer-service/inquiry/write";
		}
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				model.addAttribute("status", "serverError");
				return "redirect:/customer-service/inquiry/write";
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}
		
		UserInquiryVO inquiry = UserInquiryVO.builder()
				.member_no(member.getMember_no())
				.title(title)
				.inquiry_category_no(inquiryCategoryNo)
				.content(content)
				.build();
		// System.out.println(inquiry.toString());
		
		try {
			insertedInquiryBoardNo = csService.createInquiry(inquiry, fileList);
			if(insertedInquiryBoardNo != -1) {
				// 성공
				// logger.info("INSERT SUCCESS");
				model.addAttribute("no", insertedInquiryBoardNo);
			} else {
				// 실패
				// logger.info("INSERT ERROR");
				model.addAttribute("status", "insertFailed");
				return "redirect:/customer-service/inquiry/write";
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info("INSERT ERROR");
			// logger.info(e.getMessage());
			model.addAttribute("status", "serverError");
			return "redirect:/customer-service/inquiry/write";
		}
		
		// fileList.clear();

		return "redirect:/customer-service/inquiry/detail";
	}
	
	/**
	 * @methodName : modifyInquiry
	 * @author : kyj
	 * @param model
	 * @param inquiryBoardNo
	 * @return
	 * @returnValue : @param model
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @date : 2023. 10. 23. 오후 7:35:37
	 * @description : 문의 수정 페이지와 매핑된 메서드, GET 방식으로 해당
	 * url(/customer-service/inquiry/modify)을 요청 시 문의 수정 페이지를 응답해준다.
	 * 페이지 응답에 문제가 생길 경우 다음과 같이 처리한다. (리다이렉트 경로, status 파라메터)
	 * - 게시글이 존재하지 않는 경우 : /customer-service/inquiry/list, notFound
	 * - 로그인 되어 있지 않은 경우 : /customer-service/inquiry/list, noPermission
	 * - 해당 게시글의 작성자가 아닌 경우 : /customer-service/inquiry/list, notWriter
	 * - 에러 발생 시 : /customer-service/inquiry/write, serverError
	 */
	@GetMapping("inquiry/modify")
	public String modifyInquiry(Model model, @RequestParam(value = "no", defaultValue = "0") int inquiryBoardNo,
			HttpSession ses) {
		// logger.info("문의글 수정창 요청");

		if(inquiryBoardNo == 0) {
			model.addAttribute("status", "notFound");
			return "redirect:/customer-service/inquiry/list";
		}
		
		UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
		if(member == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/customer-service/inquiry/list"; 
		}
		
		try {
			UserInquiryVO inquiry = csService.getInquiryByInquiryNo(inquiryBoardNo);
			
			if(inquiry.getMember_no() != member.getMember_no()) {
				model.addAttribute("status", "notWriter");
				return "redirect:/customer-service/inquiry/list"; 
			}
			
			List<UserInquiryCategoryVO> inquiryCategoryList = csService.getAllInquiryCategoryList();

			if(inquiry != null) {
				if(inquiry.getIs_delete().equals("N")) {				
					List<UserInquiryImagesVO> inquiryImages = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);
					// fileList = (List<UserInquiryImagesVO>) ((ArrayList<UserInquiryImagesVO>)inquiryImages).clone();
					String inquiryImagesJsonString = new ObjectMapper().writeValueAsString(inquiryImages);
					model.addAttribute("inquiry", inquiry);
					model.addAttribute("inquiryCategoryList", inquiryCategoryList);
					model.addAttribute("inquiryImages", inquiryImages);
					model.addAttribute("inquiryImagesJsonString", inquiryImagesJsonString);
				} else {
					model.addAttribute("status", "notFound");
					return "redirect:/customer-service/inquiry/list";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status", "serverError");
			model.addAttribute("no", inquiryBoardNo);
			return "redirect:/customer-service/inquiry/detail"; 
		}
		
		return "/user/customer-service/modifyInquiry";
	}
	
	/**
	 * @methodName : modifyInquiryProcess
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @param inquiryCategoryNo
	 * @param title
	 * @param content
	 * @param request
	 * @return
	 * @throws SQLException
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @param inquiryCategoryNo
	 * @returnValue : @param title
	 * @returnValue : @param content
	 * @returnValue : @param request
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 10. 23. 오후 7:36:07
	 * @description : 문의 수정 페이지에서 입력폼을 통해 POST 방식으로 해당
	 * url(/customer-service/inquiry/modify)을 요청 시 문의 수정 처리 서비스를 실행한다.
	 * 페이지 응답에 문제가 생길 경우 다음과 같이 처리한다. (리다이렉트 경로, status 파라메터)
	 * - 로그인 되어 있지 않은 경우 : /customer-service/inquiry/list, noPermission
	 * - 수정 성공 : /customer-service/inquiry/detail, modifySuccess
	 * - 수정 실패 : 이전 경로, modifyFailed
	 * - 에러 발생 시 : 이전 경로, serverError
	 */
	@PostMapping("inquiry/modify")
	public String modifyInquiryProcess(
			@RequestParam(value = "inquiryBoardNo") int inquiryBoardNo,
			@RequestParam(value = "inquiryCategoryNo") int inquiryCategoryNo,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content,
			HttpServletRequest request,
			Model model,
			HttpSession ses,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) throws SQLException {
		// logger.info("문의 수정 처리 요청");
		
		String requestUrl = request.getHeader("Referer");

		UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
		if(member == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/customer-service/inquiry/list";
		}
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				model.addAttribute("status", "serverError");
				return "redirect:/customer-service/inquiry/list";
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}
		
		int memberNo = member.getMember_no();
		UserInquiryVO inquiry = UserInquiryVO.builder()
				.inquiry_board_no(inquiryBoardNo)
				.inquiry_category_no(inquiryCategoryNo)
				.member_no(memberNo)
				.title(title)
				.content(content)
				.build();
		
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");
			List<UserInquiryImagesVO> oldFileList = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);
			
			if(csService.modifyInquiry(inquiry, fileList, realPath)) {
				// 성공
				// logger.info("MODIFY SUCCESS");
				model.addAttribute("status", "modifySuccess");
				model.addAttribute("no", inquiryBoardNo);
				requestUrl = "redirect:/customer-service/inquiry/detail";
			} else {
				// 실패
				// logger.info("MODIFY ERROR");
				model.addAttribute("status", "modifyFailed");
				
				// 임시 업로드 파일 삭제 처리
				/*
				 * fileList 반복 돌려서 oldFileList 안에 없으면 삭제처리
				 */				
				List<UserInquiryImagesVO> removeFileList = new ArrayList<UserInquiryImagesVO>();
				
				for(UserInquiryImagesVO newFile : fileList) {
					boolean isExist = false;
					for(UserInquiryImagesVO oldFile : oldFileList) {
						if(newFile.getNew_file_name().equals(oldFile.getNew_file_name())) {
							isExist = true;
						}
					}
					if(!isExist) {
						removeFileList.add(newFile);
					}
				}
				
				UploadFileProcess.deleteAllFile(removeFileList, realPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info("MODIFY ERROR");
			model.addAttribute("status", "serverError");
			
			// 임시 업로드 파일 삭제 처리
			String realPath = request.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");
			List<UserInquiryImagesVO> oldFileList = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);
			
			List<UserInquiryImagesVO> removeFileList = new ArrayList<UserInquiryImagesVO>();
			
			for(UserInquiryImagesVO newFile : fileList) {
				boolean isExist = false;
				for(UserInquiryImagesVO oldFile : oldFileList) {
					if(newFile.getNew_file_name().equals(oldFile.getNew_file_name())) {
						isExist = true;
					}
				}
				if(!isExist) {
					removeFileList.add(newFile);
				}
			}
			
			UploadFileProcess.deleteAllFile(removeFileList, realPath);
		}
		
		fileList.clear();
		
		return requestUrl;
	}
	
	/**
	 * @methodName : removeInquiry
	 * @author : kyj
	 * @param inquiryBoardNo
	 * @param model
	 * @param ses
	 * @return
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @param model
	 * @returnValue : @param ses
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 6:53:28
	 * @description : 문의하기 상세 게시글 페이지에서 파일 삭제버튼을 눌렀을 때 해당 메서드를 통해 삭제처리(is_delete = 'Y')를 진행한다.
	 * 페이지 응답에 문제가 생길 경우 다음과 같이 처리한다. (리다이렉트 경로, status 파라메터)
	 * - 로그인 되어 있지 않은 경우 : /customer-service/inquiry/list, noPermission
	 * - 삭제 성공 : /customer-service/inquiry/list, removeSuccess
	 * - 삭제 실패 : /customer-service/inquiry/detail, removeFailed
	 * - 에러 발생 시 : /customer-service/inquiry/detail, serverError
	 */
	@PostMapping("inquiry/remove")
	public String removeInquiry(
			@RequestParam(value = "inquiryBoardNo") int inquiryBoardNo,
			Model model,
			HttpSession ses) {
		
		UserMemberVO member = (UserMemberVO) ses.getAttribute("loginMemberInfo");
		if(member == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/customer-service/inquiry/list";
		}
		
		try {
			String realPath = ses.getServletContext().getRealPath("/resources/user/uploads/inquiry");
			if(csService.removeInquiry(inquiryBoardNo, realPath)) {
				// 삭제처리 성공
				// logger.info("REMOVE SUCCESS");
				model.addAttribute("status", "removeSuccess");
			} else {
				// 삭제처리 실패
				// logger.info("REMOVE ERROR");
				model.addAttribute("status", "removeFailed");
				return "redirect:/customer-service/inquiry/detail?no=" + inquiryBoardNo;
			}
		} catch (SQLException e) {
			// 삭제처리 실패
			// logger.info("REMOVE ERROR");
			e.printStackTrace();
			model.addAttribute("status", "serverError");
			return "redirect:/customer-service/inquiry/detail?no=" + inquiryBoardNo;
		}

		return "redirect:/customer-service/inquiry/list";
	}

	/**
	 * @methodName : uploadImages
	 * @author : kyj
	 * @param uploadFiles
	 * @param req
	 * @return
	 * @returnValue : @param uploadFiles
	 * @returnValue : @param req
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 6:55:49
	 * @description : 문의 작성 페이지에서 파일을 첨부했을 때 임시 업로드 처리작업을 수행한다. 원본파일과 리사이징한 섬네일파일로
	 *              나누어 파일을 저장하며 업로드 과정에 문제가 생길 시 파일은 저장되지 않는다.
	 */
	@PostMapping("inquiry/uploadFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> uploadImages(MultipartFile uploadFiles, HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("문의글 작성 중 파일 첨부 요청");

		boolean isSuccess = true;
		UserUploadFileApiResponseDTO apiResponse;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));

		// System.out.println(uploadFiles);
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				apiResponse = UserUploadFileApiResponseDTO.builder()
						.createdAt(new Timestamp(System.currentTimeMillis()))
						.errorCode("00")
						.errorMessage("서버 내에 에러가 발생하였습니다.").build();
				
				return new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.BAD_REQUEST);
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}

		if (uploadFiles != null) {
			String realPath = req.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");
//			System.out.println(realPath);

			UserInquiryImagesVO uploadFile = null;
			try {
				uploadFile = UploadFileProcess.fileUpload(uploadFiles.getOriginalFilename(), uploadFiles.getSize(),
						uploadFiles.getContentType(), uploadFiles.getBytes(), realPath);

				if (uploadFile != null) {
					fileList.add(uploadFile);
				} else {
					isSuccess = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				uploadFile = null;
				isSuccess = false;
			}

//			System.out.println(
//					"┌─현재 업로드 파일 리스트─────────────────────────────────────────────────────────────────────────────────────────────────────────────");
//			for (UserInquiryImagesVO file : fileList) {
//				System.out.println(file.toString());
//			}
//			System.out.println(
//					"└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		} else {
			isSuccess = false;
		}

		ResponseEntity<UserUploadFileApiResponseDTO> result;
		if (isSuccess) {
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.fileList(fileList)
					.message("success")
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.build();
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);
		} else {
			/*
			 * 추후 에러 코드 상의 필요
			 */
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.errorCode("00")
					.errorMessage("failed").build();
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		System.out.println(result);

		return result;
	}

	/**
	 * @methodName : removeImage
	 * @author : kyj
	 * @param removeFile
	 * @param req
	 * @return
	 * @returnValue : @param removeFile
	 * @returnValue : @param req
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 6:57:43
	 * @description : 문의 작성 페이지에서 '하나의 첨부파일'에 대한 삭제요청에 대해 파일 삭제 처리작업을 수행한다.
	 */
	@GetMapping("inquiry/removeFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> removeImage(@RequestParam("removeFile") String removeFile, HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("문의글 작성 중 첨부 파일(단일) 삭제 요청");
		// System.out.println("삭제할 파일 : " + removeFile);

		String realPath = req.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");

		ResponseEntity<UserUploadFileApiResponseDTO> result = null;
		UserUploadFileApiResponseDTO apiResponse = null;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));

		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				apiResponse = UserUploadFileApiResponseDTO.builder()
						.createdAt(new Timestamp(System.currentTimeMillis()))
						.errorCode("00")
						.errorMessage("서버 내에 에러가 발생하였습니다.").build();
				
				return new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.BAD_REQUEST);
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}
		
		UploadFileProcess.deleteFile(fileList, removeFile, realPath);

		int removeIndex = 0;
		for (UserInquiryImagesVO uploadFile : fileList) {
			if (!removeFile.equals(uploadFile.getNew_file_name())) {
				removeIndex++;
			} else {
				break;
			}
		}
		fileList.remove(removeIndex);
		
		apiResponse = UserUploadFileApiResponseDTO.builder()
				.fileList(fileList)
				.message("success")
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.build();
		result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);

//		for (UserInquiryImagesVO file : fileList) {
//			System.out.println("현재 파일 업로드 리스트 : " + file.toString());
//		}

		return result;
	}
	
	/**
	 * @methodName : toBeRemoveImage
	 * @author : kyj
	 * @param removeFile
	 * @param inquiryBoardNo
	 * @param req
	 * @return
	 * @returnValue : @param removeFile
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @param req
	 * @returnValue : @return
	 * @date : 2023. 10. 23. 오후 7:36:31
	 * @description : 문의 수정 페이지에서 '하나의 첨부파일'에 대한 삭제요청에 대해 실제 파일 처리는 보류한 상태로 논리적으로 삭제 처리작업을 수행한다.
	 */
	@GetMapping("inquiry/toBeRemoveFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> toBeRemoveImage(@RequestParam("removeFile") String removeFile,
			@RequestParam("inquiryBoardNo") int inquiryBoardNo,
			HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("문의글 수정 중 첨부 파일(단일) 삭제 요청");
		// System.out.println("삭제할 파일 : " + removeFile);
		// System.out.println("문의글 번호 : " + inquiryBoardNo);

		ResponseEntity<UserUploadFileApiResponseDTO> result = null;
		UserUploadFileApiResponseDTO apiResponse = null;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				apiResponse = UserUploadFileApiResponseDTO.builder()
						.createdAt(new Timestamp(System.currentTimeMillis()))
						.errorCode("00")
						.errorMessage("서버 내에 에러가 발생하였습니다.").build();
				
				return new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.BAD_REQUEST);
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}
		
		try {
			List<UserInquiryImagesVO> oldFileList = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);

			boolean isExist = false;
			for(UserInquiryImagesVO oldFile : oldFileList) {
				if(oldFile.getNew_file_name().equals(removeFile)) {
					isExist = true;
				}
			}
			if(!isExist) {				
				String realPath = req.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");
				UploadFileProcess.deleteFile(fileList, removeFile, realPath);					
			}
			
			int removeIndex = 0;
			for (UserInquiryImagesVO uploadFile : fileList) {
				if (!removeFile.equals(uploadFile.getNew_file_name())) {
					removeIndex++;
				} else {
					break;
				}
			}
			fileList.remove(removeIndex);
			
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.fileList(fileList)
					.message("success")
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.build();
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);
			
//			for (UserInquiryImagesVO file : fileList) {
//				System.out.println("현재 파일 업로드 리스트 : " + file.toString());
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.errorCode("00")
					.errorMessage("서버 내에 에러가 발생하였습니다.").build();
			
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.BAD_REQUEST);
		}

		return result;
	}

	/**
	 * @methodName : removeAllFile
	 * @author : kyj
	 * @param req
	 * @return
	 * @returnValue : @param req
	 * @returnValue : @return
	 * @date : 2023. 10. 10. 오후 7:01:07
	 * @description : 문의 작성 페이지에서 '모든 첨부파일'에 대한 삭제요청에 대해 파일 삭제 처리작업을 수행한다.
	 */
	@GetMapping("inquiry/removeAllFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> removeAllFile(HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("BoardController.removeAllFile 요청");
		
		ResponseEntity<UserUploadFileApiResponseDTO> result = null;
		UserUploadFileApiResponseDTO apiResponse = null;
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				apiResponse = UserUploadFileApiResponseDTO.builder()
						.createdAt(new Timestamp(System.currentTimeMillis()))
						.errorCode("00")
						.errorMessage("서버 내에 에러가 발생하였습니다.").build();
				
				return new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.BAD_REQUEST);
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}
		
		String realPath = req.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");
		
		UploadFileProcess.deleteAllFile(fileList, realPath);

		apiResponse = UserUploadFileApiResponseDTO.builder()
				.message("success")
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.build();
		result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);

		return result;
	}
	
	/**
	 * @methodName : contactPage
	 * @author : kyj
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 10. 23. 오후 7:37:24
	 * @description : 찾아오시는 길 페이지와 매핑된 메서드, GET 방식으로 해당
	 *              url(/customer-service/contact)을 요청 시 찾아오시는 길 페이지를 응답해준다.
	 */
	@GetMapping("contact")
	public String contactPage() {
		// logger.info("찾아오시는 길 페이지 오청");
		
		return "/user/customer-service/contact";
	}
	
	/**
	 * @methodName : buildingInstructionsPage
	 * @author : kyj
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 10. 23. 오후 7:38:34
	 * @description : 조립 설명서 검색 페이지와 매핑된 메서드, GET 방식으로 해당
	 *              url(/customer-service/buildinginstructions)을 요청 시 조립 설명서 검색 페이지를 응답해준다.
	 */
	@GetMapping("buildinginstructions")
	public String buildingInstructionsPage() {
		// logger.info("조립 설명서 검색 페이지 요청");
		
		return "/user/customer-service/buildinginstructions";
	}
}
