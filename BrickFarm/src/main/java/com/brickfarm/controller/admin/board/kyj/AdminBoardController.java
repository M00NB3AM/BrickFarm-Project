package com.brickfarm.controller.admin.board.kyj;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.brickfarm.etc.kyj.UploadFileProcess;
import com.brickfarm.etc.kyj.board.SearchCriteriaBoard;
import com.brickfarm.etc.kyj.board.constant.BoardClassification;
import com.brickfarm.service.admin.board.AdminBoardService;
import com.brickfarm.service.user.customerservice.UserCSService;
import com.brickfarm.service.user.notice.UserNoticeService;
import com.brickfarm.vo.admin.kyj.board.AdminBoardsStatVO;
import com.brickfarm.vo.admin.kyj.board.AdminFaqVO;
import com.brickfarm.vo.admin.kyj.board.AdminInquiriesGraphDataVO;
import com.brickfarm.vo.admin.kyj.board.AdminInquiryVO;
import com.brickfarm.vo.admin.kyj.board.AdminNoticeVO;
import com.brickfarm.vo.admin.ysh.AdminVO;
import com.brickfarm.vo.user.kyj.UserFaqCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryCategoryVO;
import com.brickfarm.vo.user.kyj.UserInquiryImagesVO;
import com.brickfarm.vo.user.kyj.UserInquiryVO;
import com.brickfarm.vo.user.kyj.UserNoticeCategoryVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin/board/*")
public class AdminBoardController {
	// private static final Logger logger = LoggerFactory.getLogger(AdminBoardController.class);
	
	@Inject
	private AdminBoardService bService;
	
	@Inject
	private UserCSService csService;
	
	@Inject
	private UserNoticeService nService;
	
	private List<UserInquiryImagesVO> fileList = new ArrayList<UserInquiryImagesVO>();
	
	public void printFileList(List<UserInquiryImagesVO> fileList) {
		System.out.println(
				"┌─현재 업로드 파일 리스트─────────────────────────────────────────────────────────────────────────────────────────────────────────────");
		for (UserInquiryImagesVO file : fileList) {
			System.out.println(file.toString());
		}
		System.out.println(
				"└───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
	}
	
	// 로그인 권한이 없을 때 반환할 객체를 만들어주는 메서드
	public ResponseEntity<Map<String, Object>> unauthorizedResponse() {
		Map<String, Object> errResponse = new HashMap<String, Object>();
		errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
		errResponse.put("errorCode", "99");
		errResponse.put("errorMessage", "권한이 없습니다. [로그인 필요]");
		
		return new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.UNAUTHORIZED);
	}
	
	/* === 대시보드 ================================================================================================================================================= */
	@GetMapping("dashboard")
	public String dashboard(Model model) {
		// logger.info("[게시판 관리] 대시보드 페이지 요청");
		
		try {
			AdminBoardsStatVO boardsStat = bService.getBoardsStat();
			List<AdminInquiriesGraphDataVO> inquiriesGraphData = bService.getInquiriesGraphData();
			String inquiriesGraphDataJsonString = new ObjectMapper().writeValueAsString(inquiriesGraphData);
			
			// System.out.println(boardsStat);
			// System.out.println(inquiriesGraphDataJsonString);
			
			model.addAttribute("boardsStat", boardsStat);
			model.addAttribute("inquiriesGraphData", inquiriesGraphDataJsonString);
			model.addAttribute("status", "success");
		} catch (SQLException | JsonProcessingException e) {
			e.printStackTrace();
			model.addAttribute("status", "error");
		}
		
		return "/admin/board/dashboard";
	}
	
	/* === 문의 관리 ================================================================================================================================================ */
	@GetMapping("inquiry")
	public String inquiry() {
		// logger.info("[게시판 관리] 문의 관리 페이지 요청");
		return "/admin/board/inquiry";
	}
	
	@GetMapping("inquiry/category")
	public ResponseEntity<Map<String, Object>> inquiriesCategory() {
		// logger.info("[게시판 관리] 문의 분류 목록 데이터 api 요청");
		
		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			List<UserInquiryCategoryVO> category = csService.getAllInquiryCategoryList();
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("category", category);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");

			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	} 
	
	@GetMapping("inquiry/data")
	public ResponseEntity<Map<String, Object>> inquiriesInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "categoryNo", defaultValue = "-1") int categoryNo,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(value = "searchValue", defaultValue = "") String searchValue,
			@RequestParam(value = "isNeedResponse", defaultValue = "false") Boolean isNeedResponse) {
		// logger.info("[게시판 관리] 문의 관리 페이지 검색 목록 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaBoard sc = SearchCriteriaBoard.builder()
					.categoryNo(categoryNo)
					.searchKey(searchKey)
					.searchValue(searchValue)
					.isNeedResponse(isNeedResponse)
					.build();
			// System.out.println(sc.toString());
			
			PaginationInfo pi = bService.createPaginationInfo(curPageNo, rowCountPerPage, sc, BoardClassification.INQUIRY);
			// System.out.println(pi.toString());
			
			List<AdminInquiryVO> inquiryBoardList = bService.getInquiriesByCondition(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("inquiryBoardList", inquiryBoardList);
			responseData.put("paginationInfo", pi);
			
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	@GetMapping("inquiry/detail")
	public ResponseEntity<Map<String, Object>> inquiryDetailByInquiryNo(
			@RequestParam(value = "inquiryBoardNo", defaultValue = "-1") int inquiryBoardNo) {
		// logger.info("[게시판 관리] 문의 관리 페이지 '" + inquiryBoardNo + "'번 글 상세 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			AdminInquiryVO inquiry = bService.getInquiryByInquiryNo(inquiryBoardNo);
			if(inquiry != null) {
				if(inquiry.getIs_delete().equals("N")) {
					List<UserInquiryImagesVO> inquiryImages = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);
					// fileList = (List<UserInquiryImagesVO>) ((ArrayList<UserInquiryImagesVO>)inquiryImages).clone();
					Map<String, Object> responseData = new HashMap<String, Object>();
					responseData.put("inquiry", inquiry);
					responseData.put("inquiryImages", inquiryImages);
					
					result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
				} else {
					Map<String, Object> errResponse = new HashMap<String, Object>();
					errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
					errResponse.put("errorCode", "11");
					errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [삭제된 데이터]");

					result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
				}
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");
				
				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;		
	}
	
	@PostMapping("inquiry/write")
	public String writeInquiryProcess(
			@RequestParam(value = "ref") int ref,
			@RequestParam(value = "step") int step,
			@RequestParam(value = "refOrder") int refOrder,
			@RequestParam(value = "inquiryCategoryNo") int inquiryCategoryNo,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content,
			HttpSession ses,
			Model model,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("[게시판 관리] 문의 답글 작성 처리 요청");
		int insertedInquiryBoardNo = -1;
		String realPath = ses.getServletContext().getRealPath("/resources/user/uploads/inquiry");
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				fileList = new ArrayList<UserInquiryImagesVO>();
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}

		AdminVO writer = (AdminVO) ses.getAttribute("adminInfo");
		if(writer == null) {
			model.addAttribute("status", "noPermission");
			
			// 임시 파일 삭제 처리
			UploadFileProcess.deleteAllFile(fileList, realPath);
			
			return "redirect:/admin/board/inquiry";
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
			return "redirect:/admin";
		}
		
		UserInquiryVO inquiry = UserInquiryVO.builder()
				.admin_no(writer.getAdmin_no())
				.inquiry_category_no(inquiryCategoryNo)
				.title(title)
				.ref(ref)
				.step(step)
				.ref_order(refOrder)
				.content(content)
				.build();
		// System.out.println(inquiry.toString());
		
		try {
			insertedInquiryBoardNo = bService.createResponseInquiry(inquiry, fileList);
			if(insertedInquiryBoardNo != -1) {
				// 성공
				// logger.info("INSERT SUCCESS");
				model.addAttribute("no", insertedInquiryBoardNo);
			} else {
				// 실패
				// logger.info("INSERT ERROR");
				// 임시 파일 삭제 처리
				UploadFileProcess.deleteAllFile(fileList, realPath);
				model.addAttribute("status", "insertFailed");
				return "redirect:/admin/board/inquiry";
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 임시 파일 삭제 처리
			UploadFileProcess.deleteAllFile(fileList, realPath);
			// logger.info("INSERT ERROR");
			// logger.info(e.getMessage());
			model.addAttribute("status", "serverError");
			return "redirect:/admin/board/inquiry";
		}
		
		fileList.clear();

		return "redirect:/admin/board/inquiry";
	}
	
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
		// logger.info("[게시판 관리] 문의 수정 처리 요청");
		
		String requestUrl = "/admin/board/inquiry";
		
		AdminVO writer = (AdminVO) ses.getAttribute("adminInfo");
		if(writer == null) {
			model.addAttribute("status", "noPermission");
			return "redirect:/admin";
		}
		
		UserInquiryVO inquiry = UserInquiryVO.builder()
				.inquiry_board_no(inquiryBoardNo)
				.inquiry_category_no(inquiryCategoryNo)
				.title(title)
				.content(content)
				.build();
		
		List<UserInquiryImagesVO> fileList = null;
		if(!fileListJSONString.equals("")) {
			
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				fileList = new ArrayList<UserInquiryImagesVO>(Arrays.asList(mapper.readValue(fileListJSONString, UserInquiryImagesVO[].class)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				model.addAttribute("status", "modifyFailed");
				return requestUrl;
			}
		} else {
			fileList = new ArrayList<UserInquiryImagesVO>();
		}
		
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/user/uploads/inquiry");
			List<UserInquiryImagesVO> oldFileList = csService.getInquiryImagesByInquiryNo(inquiryBoardNo);
			
			if(csService.modifyInquiry(inquiry, fileList, realPath)) {
				// 성공
				// logger.info("MODIFY SUCCESS");
				model.addAttribute("status", "modifySuccess");
				model.addAttribute("no", inquiryBoardNo);
				return "redirect:/admin/board/inquiry";
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
		
		// fileList.clear();
		
		return requestUrl;
	}
	
	@PostMapping("inquiry/remove")
	public ResponseEntity<Map<String, Object>> removeInquiry(
			@RequestParam(value = "inquiryBoardNo") int inquiryBoardNo,
			HttpSession ses) {
		// logger.info("[게시판 관리] 문의글 삭제 처리 api 요청");
		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "99");
			errResponse.put("errorMessage", "권한이 없습니다. [로그인 필요]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			
			return result;
		}
		
		try {
			String realPath = ses.getServletContext().getRealPath("/resources/user/uploads/inquiry");
			if(csService.removeInquiry(inquiryBoardNo, realPath)) {
				// 삭제처리 성공
				// logger.info("REMOVE SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 삭제처리 실패
				// logger.info("REMOVE ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다.");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			// 삭제처리 실패
			// logger.info("REMOVE ERROR");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	/* === 파일 처리 =================================================================================================================================================== */
	@PostMapping("inquiry/uploadFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> uploadImages(MultipartFile uploadFiles, HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("[게시판 관리] 문의글 작성 중 파일 첨부 요청");

		boolean isSuccess = true;
		UserUploadFileApiResponseDTO apiResponse = null;
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
		
		// System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		// System.out.println(fileList.getClass().getName());
		// System.out.println(fileList.toString());
		// System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

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
					apiResponse = UserUploadFileApiResponseDTO.builder()
							.createdAt(new Timestamp(System.currentTimeMillis()))
							.errorCode("00")
							.errorMessage("서버 내에 에러가 발생하였습니다.").build();
				}
			} catch (IOException e) {
				e.printStackTrace();
				isSuccess = false;
				apiResponse = UserUploadFileApiResponseDTO.builder()
						.createdAt(new Timestamp(System.currentTimeMillis()))
						.errorCode("00")
						.errorMessage("서버 내에 에러가 발생하였습니다.").build();
			}

			// printFileList(fileList);
		} else {
			isSuccess = false;
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.errorCode("00")
					.errorMessage("서버 내에 에러가 발생하였습니다.").build();
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
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// System.out.println(result);

		return result;
	}
	
	@GetMapping("inquiry/removeFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> removeImage(@RequestParam("removeFile") String removeFile,
			HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("[게시판 관리] 문의글 작성 중 첨부 파일(단일) 삭제 요청");
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
		
		// printFileList(fileList);
		
		apiResponse = UserUploadFileApiResponseDTO.builder()
				.fileList(fileList)
				.message("success")
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.build();
		result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);

		return result;
	}
	
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
			
			// printFileList(fileList);
			
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.fileList(fileList)
					.message("success")
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.build();
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);
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
	
	@GetMapping("inquiry/removeAllFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> removeAllFile(HttpServletRequest req,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("[게시판 관리] 문의 작성 (답글 달기) 취소 버튼 동작 시 임시 파일 전체 제거 api 요청");

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
	 * @methodName : removeAllFile
	 * @author : kyj
	 * @param ses
	 * @param inquiryBoardNo
	 * @return
	 * @returnValue : @param ses
	 * @returnValue : @param inquiryBoardNo
	 * @returnValue : @return
	 * @date : 2023. 11. 24. 오후 1:02:11
	 * @description : 
	 */
	@GetMapping("inquiry/toBeRemoveAllFile")
	public ResponseEntity<UserUploadFileApiResponseDTO> removeAllFile(
			HttpSession ses,
			@RequestParam("inquiryBoardNo") int inquiryBoardNo,
			@RequestParam(value = "fileListJSONString", defaultValue = "") String fileListJSONString) {
		// logger.info("[게시판 관리] 문의 수정 취소 버튼 동작 시 임시 파일만 전체 제거 api 요청");

		String realPath = ses.getServletContext().getRealPath("/resources/user/uploads/inquiry");

		UserUploadFileApiResponseDTO apiResponse;
		ResponseEntity<UserUploadFileApiResponseDTO> result;
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

			for (UserInquiryImagesVO uploadFile : fileList) {
				boolean isExist = false;
				for (UserInquiryImagesVO oldFile : oldFileList) {
					if (uploadFile.getNew_file_name().equals(oldFile.getNew_file_name())) {
						isExist = true;
					}
				}
				if (!isExist) {
					UploadFileProcess.deleteFile(this.fileList, uploadFile.getNew_file_name(), realPath);
				}
			}

			fileList = (List<UserInquiryImagesVO>) ((ArrayList<UserInquiryImagesVO>) oldFileList).clone();

			// printFileList(fileList);
			
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.fileList(fileList)
					.message("success")
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.build();
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			apiResponse = UserUploadFileApiResponseDTO.builder()
					.createdAt(new Timestamp(System.currentTimeMillis()))
					.errorCode("00")
					.errorMessage("서버 내에 에러가 발생하였습니다.").build();
			result = new ResponseEntity<UserUploadFileApiResponseDTO>(apiResponse, header, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	/* === 공지사항 관리 ================================================================================================================================================ */
	@GetMapping("notice")
	public String notice() {
		// logger.info("[게시판 관리] 공지사항 관리 페이지 요청");
		return "/admin/board/notice";
	}
	
	@GetMapping("notice/category")
	public ResponseEntity<Map<String, Object>> noticeCategory() {
		// logger.info("[게시판 관리] 공지사항 분류 목록 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			List<UserNoticeCategoryVO> category = nService.getAllNoticeCategoryList();
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("category", category);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");

			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@GetMapping("notice/data")
	public ResponseEntity<Map<String, Object>> noticesInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "categoryNo", defaultValue = "-1") int categoryNo,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(value = "searchValue", defaultValue = "") String searchValue) {
		// logger.info("[게시판 관리] 공지사항 관리 페이지 검색 목록 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaBoard sc = SearchCriteriaBoard.builder()
					.categoryNo(categoryNo)
					.searchKey(searchKey)
					.searchValue(searchValue)
					.isFixed("Y")
					.build();
			// System.out.println(sc.toString());
			
			// 상단 고정 공지 목록
			PaginationInfo pi = bService.createPaginationInfo(curPageNo, rowCountPerPage, sc, BoardClassification.NOTICE);
			// System.out.println(pi.toString());
						
			List<AdminNoticeVO> fixedNoticeBoardList = bService.getNoticesByCondition(pi, sc);
			
			// 상단 비고정 공지 목록
			sc.setIsFixed("N");
			pi = bService.createPaginationInfo(curPageNo, rowCountPerPage, sc, BoardClassification.NOTICE);
			
			List<AdminNoticeVO> noticeBoardList = bService.getNoticesByCondition(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("fixedNoticeBoardList", fixedNoticeBoardList);
			responseData.put("noticeBoardList", noticeBoardList);
			responseData.put("paginationInfo", pi);
			
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	@GetMapping("notice/detail")
	public ResponseEntity<Map<String, Object>> noticeDetailByNoticeNo(
			@RequestParam(value = "noticeBoardNo", defaultValue = "-1") int noticeBoardNo) {
		// logger.info("[게시판 관리] 공지사항 관리 페이지 '" + noticeBoardNo + "'번 글 상세 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			AdminNoticeVO notice = bService.getNoticeByNoticeNo(noticeBoardNo);
			if(notice != null) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("notice", notice);
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");
				
				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;		
	}
	
	@PostMapping("notice/write")
	public ResponseEntity<Map<String, Object>> writeNotice(
			@RequestParam(value = "noticeCategoryNo") int noticeCategoryNo,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content, HttpSession ses, Model model) {
		// logger.info("[게시판 관리] 공지사항 게시글 작성 api 요청");		

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO writer = (AdminVO) ses.getAttribute("adminInfo");
		if(writer == null) {
			return unauthorizedResponse();
		}
		
		//유효성 검사
		boolean isValid = true;
		if(noticeCategoryNo == -1) {
			isValid = false;
		}
		if(title == null || title.length() == 0) {
			isValid = false;
		}
		if(content == null || content.length() == 0) {
			isValid = false;
		}
		if(!isValid) {
			// logger.error("값이 유효하지 않습니다.");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");
			return new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
				
		AdminNoticeVO notice = AdminNoticeVO.builder()
				.admin_no(writer.getAdmin_no())
				.notice_category_no(noticeCategoryNo)
				.title(title)
				.content(content)
				.build();
		// System.out.println(notice.toString());
		
		try {
			if(bService.createNotice(notice)) {
				// 성공
				// logger.info("INSERT SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 실패
				// logger.info("INSERT ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	@PostMapping("notice/remove")
	public ResponseEntity<Map<String, Object>> removeNotice(
			@RequestParam(value = "noticeBoardNo") int noticeBoardNo,
			HttpSession ses) {
		// logger.info("[게시판 관리] 공지사항 게시글 삭제 api 요청");
		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {			
			return unauthorizedResponse();
		}
		
		try {
			if(bService.removeNotice(noticeBoardNo)) {
				// 삭제처리 성공
				// logger.info("REMOVE SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 삭제처리 실패
				// logger.info("REMOVE ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			// 삭제처리 실패
			// logger.info("REMOVE ERROR");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	@PostMapping("notice/modify")
	public ResponseEntity<Map<String, Object>> modifyNotice(
			@RequestParam(value = "noticeBoardNo") int noticeBoardNo,
			@RequestParam(value = "noticeCategoryNo") int noticeCategoryNo,
			@RequestParam(value = "adminId") String adminId,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content,
			HttpSession ses) {
		// logger.info("[게시판 관리] 공지사항 게시글 수정 api 요청");
		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		if(!admin.getAdmin_id().equals(adminId)) {
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "98");
			errResponse.put("errorMessage", "권한이 없습니다. [작성자 불일치]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.FORBIDDEN);
			
			return result;
		}
		
		AdminNoticeVO notice = AdminNoticeVO.builder()
				.notice_board_no(noticeBoardNo)
				.notice_category_no(noticeCategoryNo)
				.admin_no(admin.getAdmin_no())
				.title(title)
				.content(content)
				.build();
		// System.out.println(notice.toString());
		
		try {
			if(bService.modifyNotice(notice)) {
				// 수정 성공
				// logger.info("MODIFY SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 수정 실패
				// logger.info("MODIFY ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			// 수정 실패
			// logger.info("MODIFY ERROR");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	@PostMapping("notice/fix")
	public ResponseEntity<Map<String, Object>> fixNotice(
			@RequestParam(value = "noticeBoardNoList[]") ArrayList<Integer> noticeBoardNoList,
			@RequestParam(value = "isFixed") String isFixed,
			HttpSession ses) {
		// logger.info("[게시판 관리] 공지사항 게시글 상단 고정 처리 api 요청");		

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("noticeBoardNoList", noticeBoardNoList);
		params.put("isFixed", isFixed);
		
		try {
			if(bService.fixNotice(params)) {
				// 성공
				// logger.info("FIX PROCESS SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 실패
				// logger.info("FIX PROCESS ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	/* === 자주묻는 질문 관리 ================================================================================================================================================ */
	@GetMapping("faq")
	public String faq() {
		// logger.info("[게시판 관리] 자주묻는 질문 관리 페이지 요청");
		return "/admin/board/faq";
	}
	
	@GetMapping("faq/category")
	public ResponseEntity<Map<String, Object>> faqCategory() {
		// logger.info("[게시판 관리] 자주묻는 질문 분류 목록 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			List<UserFaqCategoryVO> category = csService.getAllFaqCategoryList();
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("category", category);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");

			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@GetMapping("faq/data")
	public ResponseEntity<Map<String, Object>> faqInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "categoryNo", defaultValue = "-1") int categoryNo,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(value = "searchValue", defaultValue = "") String searchValue) {
		// logger.info("[게시판 관리] 자주묻는 질문 관리 페이지 검색 목록 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaBoard sc = SearchCriteriaBoard.builder()
					.categoryNo(categoryNo)
					.searchKey(searchKey)
					.searchValue(searchValue)
					.build();
			// System.out.println(sc.toString());
			
			// 상단 고정 공지 목록
			PaginationInfo pi = bService.createPaginationInfo(curPageNo, rowCountPerPage, sc, BoardClassification.FAQ);
			// System.out.println(pi.toString());
						
			List<AdminFaqVO> faqBoardList = bService.getFaqByCondition(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("faqBoardList", faqBoardList);
			responseData.put("paginationInfo", pi);
			
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	@GetMapping("faq/detail")
	public ResponseEntity<Map<String, Object>> faqDetailByFaqNo(
			@RequestParam(value = "faqBoardNo", defaultValue = "-1") int faqBoardNo) {
		// logger.info("[게시판 관리] 자주묻는 질문 관리 페이지 '" + faqBoardNo + "'번 글 상세 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			AdminFaqVO faq = bService.getFaqByFaqNo(faqBoardNo);
			if(faq != null) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("faq", faq);
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");
				
				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;		
	}
	
	@PostMapping("faq/write")
	public ResponseEntity<Map<String, Object>> writeFaq(
			@RequestParam(value = "faqCategoryNo") int faqCategoryNo,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content, HttpSession ses, Model model) {
		// logger.info("[게시판 관리] 자주묻는 질문 게시글 작성 api 요청");		

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO writer = (AdminVO) ses.getAttribute("adminInfo");
		if(writer == null) {
			return unauthorizedResponse();
		}
		
		//유효성 검사
		boolean isValid = true;
		if(faqCategoryNo == -1) {
			isValid = false;
		}
		if(title == null || title.length() == 0) {
			isValid = false;
		}
		if(content == null || content.length() == 0) {
			isValid = false;
		}
		if(!isValid) {
			// logger.error("값이 유효하지 않습니다.");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");
			return new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
				
		AdminFaqVO faq = AdminFaqVO.builder()
				.admin_no(writer.getAdmin_no())
				.faq_category_no(faqCategoryNo)
				.title(title)
				.content(content)
				.build();
		// System.out.println(faq.toString());
		
		try {
			if(bService.createFaq(faq)) {
				// 성공
				// logger.info("INSERT SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 실패
				// logger.info("INSERT ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	@PostMapping("faq/remove")
	public ResponseEntity<Map<String, Object>> removeFaq(
			@RequestParam(value = "faqBoardNo") int faqBoardNo,
			HttpSession ses) {
		// logger.info("[게시판 관리] 자주묻는 질문 게시글 삭제 api 요청");
		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		try {
			if(bService.removeFaq(faqBoardNo)) {
				// 삭제처리 성공
				// logger.info("REMOVE SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 삭제처리 실패
				// logger.info("REMOVE ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			// 삭제처리 실패
			// logger.info("REMOVE ERROR");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	@PostMapping("faq/modify")
	public ResponseEntity<Map<String, Object>> modifyFaq(
			@RequestParam(value = "faqBoardNo") int faqBoardNo,
			@RequestParam(value = "faqCategoryNo") int faqCategoryNo,
			@RequestParam(value = "adminId") String adminId,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content,
			HttpSession ses) {
		// logger.info("[게시판 관리] 자주묻는 질문 게시글 수정 api 요청");
		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		AdminFaqVO faq = AdminFaqVO.builder()
				.faq_board_no(faqBoardNo)
				.faq_category_no(faqCategoryNo)
				.admin_no(admin.getAdmin_no())
				.title(title)
				.content(content)
				.build();
		// System.out.println(faq.toString());
		
		try {
			if(bService.modifyFaq(faq)) {
				// 수정 성공
				// logger.info("MODIFY SUCCESS");
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				// 수정 실패
				// logger.info("MODIFY ERROR");
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			// 수정 실패
			// logger.info("MODIFY ERROR");
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	
	/* === 분류 관리 ================================================================================================================================================ */
	@GetMapping("category")
	public String category() {
		// logger.info("[게시판 관리] 분류 관리 페이지 요청");
		return "/admin/board/category";
	}
	
	@PostMapping("notice/category/create")
	public ResponseEntity<Map<String, Object>> createNoticeCategory(
			@RequestParam(value = "categoryName") String noticeCategoryName,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 공지사항 분류 추가 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			result = unauthorizedResponse();
			return result;
		}
		
		try {
			if (bService.createNoticeCategory(noticeCategoryName)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}

	@PostMapping("notice/category/modify")
	public ResponseEntity<Map<String, Object>> modifyNoticeCategory(
			@RequestParam(value = "categoryNo") int noticeCategoryNo,
			@RequestParam(value = "categoryName") String noticeCategoryName,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 공지사항 분류 수정 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		UserNoticeCategoryVO noticeCategory = new UserNoticeCategoryVO(noticeCategoryNo, noticeCategoryName);
		try {
			if (bService.modifyNoticeCategoryByCategoryNo(noticeCategory)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}

	@PostMapping("notice/category/remove")
	public ResponseEntity<Map<String, Object>> removeNoticeCategory(
			@RequestParam(value = "categoryNo") int noticeCategoryNo,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 공지사항 분류 삭제 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		try {
			if (bService.removeNoticeCategoryByCategoryNo(noticeCategoryNo)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("inquiry/category/create")
	public ResponseEntity<Map<String, Object>> createInquiryCategory(
			@RequestParam(value = "categoryName") String inquiryCategoryName,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 문의 분류 추가 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		try {
			if (bService.createInquiryCategory(inquiryCategoryName)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("inquiry/category/modify")
	public ResponseEntity<Map<String, Object>> modifyInquiryCategory(
			@RequestParam(value = "categoryNo") int inquiryCategoryNo,
			@RequestParam(value = "categoryName") String inquiryCategoryName,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 문의 분류 수정 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		UserInquiryCategoryVO inquiryCategory = new UserInquiryCategoryVO(inquiryCategoryNo, inquiryCategoryName);
		try {
			if (bService.modifyInquiryCategoryByCategoryNo(inquiryCategory)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("inquiry/category/remove")
	public ResponseEntity<Map<String, Object>> removeInquiryCategory(
			@RequestParam(value = "categoryNo") int inquiryCategoryNo,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 문의 분류 삭제 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		try {
			if (bService.removeInquiryCategoryByCategoryNo(inquiryCategoryNo)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("faq/category/create")
	public ResponseEntity<Map<String, Object>> createFaqCategory(
			@RequestParam(value = "categoryName") String faqCategoryName,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 자주묻는 질문 분류 추가 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		try {
			if (bService.createFaqCategory(faqCategoryName)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "10");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [파라메터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("faq/category/modify")
	public ResponseEntity<Map<String, Object>> modifyFaqCategory(
			@RequestParam(value = "categoryNo") int faqCategoryNo,
			@RequestParam(value = "categoryName") String faqCategoryName,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 자주묻는 질문 분류 수정 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		UserFaqCategoryVO faqCategory = new UserFaqCategoryVO(faqCategoryNo, faqCategoryName);
		try {
			if (bService.modifyFaqCategoryByCategoryNo(faqCategory)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@PostMapping("faq/category/remove")
	public ResponseEntity<Map<String, Object>> removeFaqCategory(
			@RequestParam(value = "categoryNo") int faqCategoryNo,
			HttpSession ses) {
		// logger.info("[게시판 관리] 분류 관리 - 자주묻는 질문 분류 삭제 요청 api ");

		ResponseEntity<Map<String,Object>> result = null;
		
		AdminVO admin = (AdminVO) ses.getAttribute("adminInfo");
		if(admin == null) {
			return unauthorizedResponse();
		}
		
		try {
			if (bService.removeFaqCategoryByCategoryNo(faqCategoryNo)) {
				Map<String, Object> responseData = new HashMap<String, Object>();
				responseData.put("createdAt", new Timestamp(System.currentTimeMillis()));
				responseData.put("message", "success");
				
				result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
			} else {
				Map<String, Object> errResponse = new HashMap<String, Object>();
				errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errResponse.put("errorCode", "12");
				errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [존재하지 않는 데이터]");

				result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	
}
