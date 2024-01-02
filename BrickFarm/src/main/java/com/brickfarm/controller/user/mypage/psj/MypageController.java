package com.brickfarm.controller.user.mypage.psj;




import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.brickfarm.dto.user.psj.UserWithdrawalApplyDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.dto.user.psj.UserOrderListDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalListDTO;
import com.brickfarm.dto.user.psj.UserProductReviewDTO;
import com.brickfarm.dto.user.psj.UserProductReviewInfoDTO;
import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.dto.user.psj.UserUploadFileDTO;
import com.brickfarm.etc.psj.PaginationInfo;
import com.brickfarm.etc.psj.SearchCriteria;
import com.brickfarm.etc.psj.UploadFileProcess;
import com.brickfarm.service.user.mypage.MyPageService;
import com.brickfarm.service.user.product.ProductService;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.user.psj.UserInquiryInfoVO;
import com.brickfarm.vo.user.psj.UserPointsAccrualLogVO;
import com.brickfarm.vo.user.psj.UserPointsUsageLogVO;
import com.brickfarm.vo.user.psj.UserProductReviewImageVO;
import com.brickfarm.vo.user.psj.UserAddressBookVO;
import com.brickfarm.vo.user.psj.UserMemberHavingCouponVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);

	@Inject
	private MyPageService myPageService;
	
	@Inject
	private ProductService productService;
	
	private List<UserProductReviewImageVO> fileList = new ArrayList<UserProductReviewImageVO>();
	
	// 마이 페이지 접속 -> 마이 페이지 종합 정보
	@GetMapping({ "", "/profileinfo" })
	public String myPage(Model model, HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("마이 페이지 - 기본/종합정보 요청");
		// 로그인한 회원 정보 가져오기
		UserMemberVO yshMemberVO = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int member_no = yshMemberVO.getMember_no();
		UserMemberVO loginMemberInfo = myPageService.getMemberInfo(member_no);

		SearchCriteria sc = new SearchCriteria("", "", "");
		PaginationInfo pi = myPageService.createOrderListPaginationInfo(1, 5, member_no, sc);
		// 로그인한 회원의 요약 주문정보
		List<UserOrderListDTO> orderList = myPageService.getOrderList(pi, member_no, sc);
		int reviewCount = myPageService.getReviewCount(member_no);
		List<String> addressBookNameList = myPageService.getAddressBookNameList(member_no);
		List<UserInquiryInfoVO> answeredInquiryList = myPageService.getAnsweredInquiry(member_no);
		List<UserInquiryInfoVO> noAnsweredInquiryList =	myPageService.getNoAnsweredInquiry(member_no);
		UserAddressBookVO defaultAddressBook = myPageService.getDefaultAddressBook(member_no);
		
		model.addAttribute("answeredInquiryList", answeredInquiryList);
		model.addAttribute("noAnsweredInquiryList", noAnsweredInquiryList);
		model.addAttribute("loginMemberInfo", loginMemberInfo);
		model.addAttribute("addressBookNameList", addressBookNameList);
		model.addAttribute("defaultAddressBook", defaultAddressBook);
		model.addAttribute("reviewCount", reviewCount);
		model.addAttribute("orderList", orderList);

		return "user/mypage/profileinfo";
	}
	
	@GetMapping("/showcnt")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> showcnt(HttpSession session) {
		logger.info("마이페이지 - 수량정보 호출");
		UserMemberVO yshMemberVO = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int memberNo = yshMemberVO.getMember_no();
		Map<String, Object> cntInfo = new HashMap<String, Object>();
		try {
			UserMemberVO loginMember = myPageService.getMemberInfo(memberNo);
			int cancelCnt = loginMember.getCancel_count();
			int orderCnt = loginMember.getOrder_count();
			int wishListCnt = loginMember.getWish_list_count();
			
			cntInfo.put("cancelCnt", cancelCnt);
			cntInfo.put("orderCnt", orderCnt);
			cntInfo.put("wishListCnt", wishListCnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(cntInfo, HttpStatus.OK);
	}

	// 마이 페이지 내정보
	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
		logger.info("마이페이지 - 내정보 요청");
		UserMemberVO loginMember = (UserMemberVO)session.getAttribute("loginMemberInfo"); // <-- 세환이가 만듬!
		int member_no = loginMember.getMember_no();
		
		try {
			loginMember = myPageService.getMemberInfo(member_no);
			model.addAttribute("loginMember", loginMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "user/mypage/profile";
	}

	// 마이 페이지 내정보 수정
	@GetMapping("/editprofile")
	public String editprofile(HttpSession session, HttpServletRequest request, Model model) {
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO)session.getAttribute("loginMemberInfo");
		int memberNo = loginMember.getMember_no();
		try {
			UserMemberVO loginMemberInfo = myPageService.getMemberInfo(memberNo);
			model.addAttribute("loginMemberInfo", loginMemberInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("마이페이지 - 내정보 수정 요청");
		return "user/mypage/editprofile";
	}

	// 마이 페이지 주문 목록
	@GetMapping("/orderlist")
	public String orderlist(HttpSession session, Model model,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "5") int rowCountPerPage,
			@RequestParam(value = "searchPeriod", defaultValue = "") String searchPeriod,
			@RequestParam(value = "searchState", defaultValue = "") String searchState) {
		logger.info("마이페이지 - 주문목록 요청");
		UserMemberVO loginMemberInfo = (UserMemberVO) session.getAttribute("loginMemberInfo");
		// 검색 기간, 상태 설정
		SearchCriteria sc = new SearchCriteria(searchPeriod, searchState, "");
		int loginMemberNo = loginMemberInfo.getMember_no();
		PaginationInfo pi;

		try {
			// 검색 기간, 상태 설정에 따른 페이징 정보
			pi = myPageService.createOrderListPaginationInfo(curPageNo, rowCountPerPage, loginMemberNo, sc);
			// 로그인한 유저의 주문 목록(List)
			List<UserOrderListDTO> orderList = myPageService.getOrderList(pi, loginMemberNo, sc);
			model.addAttribute("orderList", orderList);
			model.addAttribute("pagingInfo", pi);
			model.addAttribute("searchInfo", sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/mypage/orderlist";
	}

	// 주문 상세조회
	@GetMapping("/orderdetail")
	public String orderdetail(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		logger.info("마이페이지 - 주문목록 상세보기 요청");
		// 상세 조회 될 주문의 주문번호
		String merchant_uid = request.getParameter("merchant_uid");
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int loginMemberNo = loginMember.getMember_no();

		// 상세 조회 될 주문 (주문, 취소,교환,환불) 모두 맵의 형태로 묶음.
		Map<String, Object> orderInfoMap = myPageService.getMemberTotalOrderInfo(loginMemberNo, merchant_uid);
		
		// 주문 정보
		model.addAttribute("orderInfo", orderInfoMap.get("orderInfo"));
		// 주문 진행중인 목록
		model.addAttribute("orderList", orderInfoMap.get("orderList"));
		// 취소, 반품 진행중인 목록
		model.addAttribute("orderCancelAndReturnList", orderInfoMap.get("orderCancelAndReturnList"));
		// 교환 진행중인 목록
		model.addAttribute("orderExchangeList", orderInfoMap.get("orderExchangeList"));
		
		return "user/mypage/orderdetail";
	}

	// 상품 취소 / 교환 / 반품 신청 폼
	@GetMapping("/cancelandrefundapplication")
	public String cancelandrefundapplication(@RequestParam("detailed_order_no") int detailedOrderNo,
			@RequestParam("what") String what, Model model) {
		logger.info("마이페이지 - 취소/교환/반품 신청 페이지 요청");
		try {
			// 취소 / 교환 / 반품 신청에 필요한 데이터를 보냄
			UserWithdrawalApplyDTO withrawalApplyDTO = myPageService.getWithdrawalApplyDTO(detailedOrderNo);
			model.addAttribute("what", what);
			model.addAttribute("withrawalApplyDTO", withrawalApplyDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/mypage/cancelandrefundapplication";
	}
	
	// 마이 페이지 취소/교환/환불 신청
	@PostMapping("/order/cancelandrefundapplication")
	@ResponseBody
	public ResponseEntity<String> cancelandrefundapplication(
			@RequestBody UserWithdrawalConfirmDTO userWithdrawalConfirmDTO) {
		int result = -1;
		ResponseEntity<String> response = null;
		logger.info("마이페이지 - 취소/교환/환불 신청 요청");
		try {
			// 취소, 교환, 환불 처리
			result = myPageService.addCancelAndExchangeOrderConfirm(userWithdrawalConfirmDTO);
			if(result > 0) {
				response = new ResponseEntity<String>("success", HttpStatus.OK);
			} else {
				response = new ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	

	// 상품 구매 확정
	@PostMapping("/order/confirmorder")
	public ResponseEntity<String> confirmorder(@RequestParam("modalProductDetailedOrderNo") int detailedOrderNo,
			@RequestParam("priceNumber") int priceNumber, @RequestParam("merchantUid") String merchantUid,
			HttpSession session, HttpServletRequest request) {
		logger.info("마이페이지 - 주문확정 요청");
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		Map<String, Object> confirmOrderInfo = new HashMap<String, Object>();
		int loginMemberNo = loginMember.getMember_no();
		// 상품의 상태, 회원의 포인트 처리에 필요한 데이터를 맵에 바인딩
		confirmOrderInfo.put("loginMemberNo", loginMemberNo);
		confirmOrderInfo.put("detailedOrderNo", detailedOrderNo);
		confirmOrderInfo.put("priceNumber", priceNumber);
		confirmOrderInfo.put("merchantUid", merchantUid);
		try {
			// 구매 확정시 수행될 로직 처리
			int result = myPageService.modifyOrderConfirm(confirmOrderInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	// 리뷰 작성
	@GetMapping("/writereview")
	public String writeReview(HttpSession session, Model model,
			@RequestParam("detailed_order_no") int detailedOrderNo) {
		logger.info("마이페이지 - 리뷰작성 요청");
		try {
			UserProductReviewInfoDTO productReviewInfo = myPageService.getProductReviewInfo(detailedOrderNo);
			model.addAttribute("productReviewInfo", productReviewInfo);
		} catch (Exception e) {
		}
		return "user/mypage/writereview";
	}
	
	// 리뷰 수정
	@GetMapping("/modifyreviewform")
	public String modifyreviewform(HttpSession session, Model model,
			@RequestParam("detailed_order_no") int detailed_order_no
			) {
		logger.info("마이페이지 - 리뷰수정 요청");
		try {
			UserProductReviewInfoDTO productReviewInfo = myPageService.getProductReviewInfo(detailed_order_no);
			UserProductReviewDTO productReview = myPageService.getProductReview(detailed_order_no);
			int product_review_no = productReview.getProduct_review_no();
			List<UserProductReviewImageVO> productReviewImgs = myPageService.getProductReviewImg(product_review_no);
			fileList = productReviewImgs;
			System.out.println(productReviewImgs.toString());
			model.addAttribute("productReviewImgs", productReviewImgs);
			model.addAttribute("productReviewInfo", productReviewInfo);
			model.addAttribute("productReview", productReview);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/mypage/modifyreview";
	}
	// 리뷰 수정 제출
	@PostMapping("/modifyreview")
	public String modifyreview(@ModelAttribute UserProductReviewDTO productReviewDTO, HttpSession session,
			HttpServletRequest request) {
			logger.info("리뷰 수정 제출");
			String encodedMerchantUid = "";
			try {
				encodedMerchantUid = URLEncoder.encode(productReviewDTO.getMerchant_uid(), "UTF-8");
				String realPath = request.getSession().getServletContext().getRealPath("/resources/user/uploads/review");
				myPageService.modifyProductReview(productReviewDTO, fileList , realPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "redirect:/mypage/orderdetail?merchant_uid="+encodedMerchantUid;
	}
	
	
	// 첨부 파일 업로드
	@PostMapping("/uploadfile")
	public ResponseEntity<UserUploadFileDTO> uploadFile(MultipartFile uploadFiles, HttpServletRequest request) {
		logger.info("파일 업로드 호출");
		boolean isSuccess = true;
		System.out.println("업로드 시도 파일 : " + uploadFiles);
		
		if(uploadFiles != null) {
			String realPath = request.getSession().getServletContext().getRealPath("/resources/user/uploads/review");
			System.out.println("파일 저장 경로 : " + realPath);
			UserProductReviewImageVO uploadFile = null;
			
			try {
				uploadFile = UploadFileProcess.fileUpload(uploadFiles.getOriginalFilename(), uploadFiles.getSize(), 
						uploadFiles.getContentType(), uploadFiles.getBytes(), realPath);
				
				if(uploadFile != null) {
					this.fileList.add(uploadFile);
				} else {
					isSuccess = false;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				uploadFile = null;
				isSuccess = false;
			}
		}
		UserUploadFileDTO response = new UserUploadFileDTO();
		response.setFileList(fileList);
		ResponseEntity<UserUploadFileDTO> result;
		result = new ResponseEntity<UserUploadFileDTO>(response, HttpStatus.OK);
		
		return result;
	}
	
	@GetMapping("/removefile")
	public ResponseEntity<String> removeFile(@RequestParam("removeFile") String removeFile, HttpServletRequest request) {
		logger.info("리뷰 첨부파일 삭제 요청");
		System.out.println("삭제 요청 파일 : " + removeFile);
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/user/uploads/review");
		
		// 실제 저장 경로에서 파일 삭제
		UploadFileProcess.deleteFile(fileList, removeFile, realPath);
		
		// 파일 리스트에서 파일 삭제
		int removeIndex = 0;
		for(UserProductReviewImageVO uploadFile : fileList) {
			if(!removeFile.equals(uploadFile.getNew_file_name())) {
				removeIndex++;
			} else {
				break;
			}
		}
		fileList.remove(removeIndex);
		for(UserProductReviewImageVO file : fileList) {
			System.out.println("삭제 후 파일 리스트 : " + file.toString());
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	

	@PostMapping("/reviewconfirm")
	public String reviewconfirm(@ModelAttribute UserProductReviewDTO productReviewDTO, HttpSession session,
			HttpServletRequest request) {
		logger.info("마이페이지 - 리뷰작성 완료");
		session = request.getSession();
		System.out.println(productReviewDTO.toString());
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int loginMemberNo = loginMember.getMember_no();
		productReviewDTO.setMember_no(loginMemberNo);
		String encodedMerchantUid = "";
		try {
			encodedMerchantUid = URLEncoder.encode(productReviewDTO.getMerchant_uid(), "UTF-8");
			myPageService.addProductReview(productReviewDTO, fileList, loginMemberNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mypage/orderdetail?merchant_uid="+encodedMerchantUid;
	}

	// 마이 페이지 취소/환불 목록
	@GetMapping("/cancelandrefundlist")
	public String cancelAndRefundList(HttpSession session, Model model,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "5") int rowCountPerPage,
			@RequestParam(value = "searchState", defaultValue = "") String searchState) throws Exception {
		logger.info("마이페이지 - 취소/교환/환불 목록 요청");
		UserMemberVO loginMemberInfo = (UserMemberVO) session.getAttribute("loginMemberInfo");
		// 검색 기간, 상태 설정
		SearchCriteria sc = new SearchCriteria("", searchState, "");
		int loginMemberNo = loginMemberInfo.getMember_no();
		PaginationInfo pi;
		// 검색 상태에 따른 페이징 정보
		pi = myPageService.createCancelAndExchangeListPaginationInfo(curPageNo, rowCountPerPage, loginMemberNo, sc);

		// 페이징 정보에 따른 취소, 교환, 환불 목록(List)
		List<UserOrderWithdrawalListDTO> cancelAndRefundList = myPageService.getOrderWithDrawalList(loginMemberNo,
				pi, sc);
		model.addAttribute("cancelAndRefundList", cancelAndRefundList);
		model.addAttribute("pagingInfo", pi);
		model.addAttribute("searchInfo", sc);
		return "user/mypage/cancelandrefundlist";
	}

	// 마이 페이지 취소/환불 상세 조회
	@GetMapping("/cancelandrefuntdetail")
	public String cancelandrefuntdetail(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		logger.info("마이페이지 - 취소/교환/환불 상세보기 요청");
		int detailed_order_no = Integer.parseInt(request.getParameter("detailed_order_no"));
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int loginMemberNo = loginMember.getMember_no();

		// 취소, 환불, 교환 상세 정보
		UserOrderWithdrawalDTO UserOrderWithdrawal = myPageService.getUserOrderWithdrawal(loginMemberNo, detailed_order_no);
		model.addAttribute("UserOrderWithdrawal", UserOrderWithdrawal);

		return "user/mypage/cancelandrefuntdetail";
	}



	// 마이 페이지 배송지 목록
	@GetMapping("/addresslist")
	public String addresslist(HttpServletRequest request, HttpSession session, Model model) {
		logger.info("마이페이지 - 배송지 목록 호출");
		// 애초에 뷰를 보여주는 컨트롤러에서 데이터를 바인딩 할 때 ****로 가려진 데이터를 바인딩 하는 걸 고려해보자 
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		try {
			List<UserAddressBookVO> addressBookList = myPageService.getAllAddressBook(loginMember.getMember_no());
			model.addAttribute("addressBookList", addressBookList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/mypage/addresslist";
	}

	// 마이 페이지 배송지 목록 수정 폼
	@PostMapping("/editaddresslistform")
	public String editaddresslistform(@RequestParam("member_address_book_no") int memberAddressBookNo, Model model) {
		logger.info("마이페이지 - 배송지 수정폼 호출");
		UserAddressBookVO addressBook;
		try {
			addressBook = myPageService.selectAddressInfo(memberAddressBookNo);
			model.addAttribute("addressBook", addressBook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/mypage/editaddresslist";
	}
	
	// 마이 페이지 배송지 목록 수정
	@PostMapping("/editaddresslist")
	public String editaddresslist(@ModelAttribute UserAddressBookVO addressBookVO, @RequestParam(name="is-default", required = false) boolean isDefault, @RequestParam("detail_address") String detailAddress, HttpSession session) {
		logger.info("마이페이지 - 배송지 수정 호출");
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int memberNo = loginMember.getMember_no();
		if(isDefault) {
			addressBookVO.setIs_default("Y");
			
		}else {
			addressBookVO.setIs_default("N");
		}
		addressBookVO.setAddress(addressBookVO.getAddress()+", "+ detailAddress);
		try {
			myPageService.modifyAddressBook(addressBookVO, memberNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mypage/addresslist";
	}

	// 마이 페이지 배송지 추가
	@GetMapping("/addaddresslist")
	public String addaddress() {
		logger.info("마이페이지 - (GET)배송지 추가 호출");
		return "user/mypage/addaddresslist";
	}
	// 마이 페이지 배송지 추가
	@PostMapping("/addaddresslist")
	public String addaddressPost(@RequestParam("recipient") String recipient, @RequestParam(value = "address_name") String addressName,
			@RequestParam("mobile1") String mobile1, @RequestParam("mobile2") String mobile2, @RequestParam("mobile3") String mobile3,
			@RequestParam("recipient_address") String recipientAddress, @RequestParam("recipient_zip_code") String recipientZipCode,
			@RequestParam("recipient_detail_address") String recipient_detail_address,@RequestParam(name="is-default", required = false) boolean isDefault,
			HttpServletRequest request, HttpSession session
			) {
		logger.info("마이페이지 - (POST)배송지 추가 호출");
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO)session.getAttribute("loginMemberInfo");
		UserAddressBookVO addedAddress = new UserAddressBookVO();
		// 멤버 no
		addedAddress.setMember_no(loginMember.getMember_no());
		// 배송지 이름
		addedAddress.setAddress_name(addressName);
		// 배송지 주소
		addedAddress.setAddress(recipientAddress+", " + recipient_detail_address);
		// 휴대전화 번호
		addedAddress.setPhone_number(mobile1+"-"+mobile2+"-"+mobile3);
		// 수신자 
		addedAddress.setRecipient(recipient);
		// 배송지 우편번호
		addedAddress.setZip_code(recipientZipCode);
		if(isDefault) {
			addedAddress.setIs_default("Y");
		} else {
			addedAddress.setIs_default("N");
		}
		try {
			myPageService.addAddressBook(addedAddress,loginMember.getMember_no());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mypage/addresslist";
	}

	// 마이페이지 배송지 삭제 
	@PostMapping("/deleteaddresslist")
	public String deleteAddressList(@RequestParam("addressBookNo") int addressBookNo) {
		logger.info("마이페이지 - 배송지 삭제 호출");
		try {
			myPageService.removeAddressBook(addressBookNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mypage/addresslist";
	}

	// 마이페이지 쿠폰 내역
	@GetMapping("/couponhistory")
	public String couponhistory(HttpSession session, Model model,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "5") int rowCountPerPage,
			@RequestParam(value = "searchOrder", defaultValue = "orderByRecent") String searchOrder,
			@RequestParam(value = "searchState", defaultValue = "") String searchState) throws Exception {
		logger.info("마이페이지 - 쿠폰내역 요청");
		// 로그인한 회원의 쿠폰들 정보
		UserMemberVO loginMemberInfo = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int loginMemberNo = loginMemberInfo.getMember_no();
		SearchCriteria sc = new SearchCriteria("", searchState, searchOrder);
		PaginationInfo pi = myPageService.createCouponListPaginationInfo(curPageNo, rowCountPerPage, loginMemberNo, sc);
		// 보유 쿠폰 목록(List)
		List<UserMemberHavingCouponVO> couponList = myPageService.getMemberCouponHeld(loginMemberNo, pi, sc);
		model.addAttribute("pagingInfo", pi);
		model.addAttribute("searchInfo", sc);
		model.addAttribute("couponList", couponList);
		return "user/mypage/couponhistory";
	}

	// 마이페이지 적립금 내역
	@GetMapping("/pointhistory")
	public String pointhistory(HttpSession session, Model model, HttpServletRequest request) {
		logger.info("마이페이지 - 적립금 내역 요청");
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int member_no = loginMember.getMember_no();
		try {
			UserMemberVO loginMemberInfo = myPageService.getMemberInfo(member_no);
			// 포인트 적립 내역(List)
			List<UserPointsAccrualLogVO> pointsAccrualLog = myPageService.getPointsAccrualLog(loginMember.getMember_no());
			// 포인트 변동 내역(List)
			List<UserPointsUsageLogVO> pointsUsageLog = myPageService.getPointsUsageLog(loginMember.getMember_no());
			model.addAttribute("pointsAccrualLog", pointsAccrualLog);
			model.addAttribute("pointsUsageLog", pointsUsageLog);
			model.addAttribute("loginMemberInfo", loginMemberInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/mypage/pointhistory";
	}

	// 마이 페이지 좋아요 목록
	@GetMapping("/wishlist")
	public String wishlist(Model model, HttpServletRequest request, HttpSession session) {
		
		session = request.getSession();
		UserMemberVO memberInfo = new UserMemberVO(); 
		memberInfo = (UserMemberVO) session.getAttribute("loginMemberInfo");
		
		String result = "";
		
		if (memberInfo != null) {
			
			int memberNo = memberInfo.getMember_no();		
			
			List<UserProductVO> likeProductList;
			try {
				likeProductList = myPageService.getLikeProductList(memberNo);
				model.addAttribute("likeProductList", likeProductList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = "user/mypage/wishlist";
		}
		
		return result;
	}
	
	// 마이 페이지 좋아요 추가 / 삭제
	@ResponseBody
	@RequestMapping(value = "/addToWishlist", method = RequestMethod.POST)
	public ResponseEntity<String> verificationProductToWishlist(Model model, @RequestParam(value = "memberNo") int memberNo, 
			@RequestParam(value = "productCode") String productCode,
			@RequestParam(value = "isLiked") boolean isLiked) {
		
		// isLiked = false 면 하트표시가 안 돼있는 부분이기 때문에 좋아요 추가
		// isLiked = true 면 하트표시가 돼있는 부분이기 때문에 좋아요 삭제
		
		boolean result = false;
		try {
			result = myPageService.verificationProductToWishlist(memberNo, productCode, isLiked);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result == true) {
			return ResponseEntity.ok("Operation successful.");
		} else {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Operation failed: ");
		}
		
	}
	
	@PostMapping("/deleteProductAllToWishlist")
	public void deleteProductWishlist(@RequestParam(value = "memberNo") int memberNo)  {
		try {
			myPageService.deleteProductAllToWishList(memberNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	};
	
	@ResponseBody
	@RequestMapping(value = "addToCart", method = RequestMethod.POST)
	public Map<String, Object> addToCart(Model model,
			@RequestParam(value = "memberNo") int memberNo,
			@RequestParam(value = "productCode") String productCode,
			@RequestParam(value = "productImage") String productImage,
			@RequestParam(value = "productName") String productName,
			@RequestParam(value = "productPrice") int productPrice,
			@RequestParam(value = "productQuantity", defaultValue = "1") int productQuantity) {
		
		Map<String, Object> productDetailResult = new HashMap<String, Object>();
		// 장바구니에 추가
		try {
			myPageService.verificationProductToCart(memberNo, productCode, productQuantity);
			
			productDetailResult = productService.getProductDetail(productCode);
			productDetailResult.put("productQuantity", productQuantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return productDetailResult;
		
		
	}
	

	// 마이 페이지 장바구니
	@GetMapping("/shoppingcart")
	public String cart(HttpSession session, HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("마이페이지 - 장바구니 요청");
		session = request.getSession();
		UserMemberVO loginMember = (UserMemberVO) session.getAttribute("loginMemberInfo");
		int memberNo = loginMember.getMember_no();
		try {
			// 장바구니 목록(List)
			List<UserShoppingCartDTO> shoppingCartList = myPageService.getShoppingCartList(memberNo);
			model.addAttribute("shoppingCartList", shoppingCartList);
			// 장바구니가 비어있을 경우
			if (shoppingCartList.size() == 0) {
				return "user/mypage/shoppingcartempty";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/mypage/shoppingcart";
	}

	// 장바구니 목록 단일 삭제
	@PostMapping("/deleteshoppingcart")
	public ResponseEntity<String> deleteshoppingcart(@RequestParam("shoppingCartNo") int shoppingCartNo) {
		logger.info("마이페이지 - 장바구니 단일 삭제 요청");
		System.out.println(shoppingCartNo);
		try {
			// 장바구니에 담긴 품목 삭제
			myPageService.removeShoppingCart(shoppingCartNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	// 장바구니 목록 전부 삭제
	@PostMapping("/deleteallshoppingcart")
	public ResponseEntity<String> deleteallshoppingcart(@RequestParam("memberNo") int memberNo) {
		logger.info("마이페이지 - 장바구니 비우기 요청");
		try {
			// 장바구니에 담긴 모든 품목 삭제
			myPageService.removeAllShoppingCart(memberNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	// 장바구니 물품 수량 변경시 업데이트
	@PostMapping("/changeShoppingCartProductCnt")
	public void chageShoppingCartProductCnt(
			@RequestParam(value = "shoppingCartNoList[]") List<Integer> shoppingCartNoList,
			@RequestParam(value = "shoppingCartQuantityList[]") List<Integer> shoppingCartQuantityList
			) {
		logger.info("마이페이지 - 장바구니 물품 수량 변경");
		try {
			myPageService.modifyShoppingCartProductCnt(shoppingCartNoList, shoppingCartQuantityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
