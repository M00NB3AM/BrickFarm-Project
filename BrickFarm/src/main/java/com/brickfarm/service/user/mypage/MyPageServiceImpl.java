package com.brickfarm.service.user.mypage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brickfarm.dao.cancellationreturn.CancellationReturnDAO;
import com.brickfarm.dao.couponheld.CouponHeldDAO;
import com.brickfarm.dao.detailedorder.DetailedOrderDAO;
import com.brickfarm.dao.exchange.ExchangeDAO;
import com.brickfarm.dao.inquiryboard.InquiryBoardDAO;
import com.brickfarm.dao.member.MemberDAO;
import com.brickfarm.dao.memberAddressBook.MemberAddressBookDAO;
import com.brickfarm.dao.mypage.MyPageDAO;
import com.brickfarm.dao.ordersheet.OrderSheetDAO;
import com.brickfarm.dao.payment.PaymentDAO;
import com.brickfarm.dao.pointsaccurallog.PointsAccuralLogDAO;
import com.brickfarm.dao.pointsusagelog.PointsUsageLogDAO;
import com.brickfarm.dao.productreview.ProductReviewDAO;
import com.brickfarm.dao.productreviewimage.ProductReviewImageDAO;
import com.brickfarm.dao.productwishlist.ProductWishListDAO;
import com.brickfarm.dao.shoppingcart.ShoppingCartDAO;
import com.brickfarm.dto.user.psj.UserWithdrawalApplyDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.dto.user.psj.UserOrderInfoDTO;
import com.brickfarm.dto.user.psj.UserOrderListDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalListDTO;
import com.brickfarm.dto.user.psj.UserProductReviewDTO;
import com.brickfarm.dto.user.psj.UserProductReviewInfoDTO;
import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.etc.psj.PaginationInfo;
import com.brickfarm.etc.psj.SearchCriteria;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.user.psj.UserInquiryInfoVO;
import com.brickfarm.vo.user.psj.UserPointsAccrualLogVO;
import com.brickfarm.vo.user.psj.UserPointsUsageLogVO;
import com.brickfarm.vo.user.psj.UserProductReviewImageVO;
import com.brickfarm.vo.user.psj.UserAddressBookVO;
import com.brickfarm.vo.user.psj.UserMemberHavingCouponVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Inject
	private MyPageDAO myPageDAO;
	@Inject
	private MemberAddressBookDAO addressBookDAO;
	@Inject
	private ProductReviewDAO reviewDAO;
	@Inject
	private ShoppingCartDAO shoppingCartDAO;
	@Inject
	private OrderSheetDAO orderSheetDAO;
	@Inject
	private DetailedOrderDAO detailedOrderDAO;
	@Inject
	private CancellationReturnDAO cancellationReturnDAO;
	@Inject
	private ExchangeDAO exchangeDAO;
	@Inject
	private PointsAccuralLogDAO pointsAccuralLogDAO;
	@Inject
	private PointsUsageLogDAO pointsUsageLogDAO;
	@Inject
	private MemberDAO memberDAO;
	@Inject
	private ProductReviewImageDAO productReviewImageDAO;
	@Inject
	private CouponHeldDAO couponHeldDAO;
	@Inject
	private ProductWishListDAO productWishListDAO;
	@Inject
	private InquiryBoardDAO inquiryBoardDAO;
	@Inject
	private PaymentDAO paymentDAO;
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================

	@Override
	public boolean verificationProductToWishlist(int memberNo, String productCode, boolean isLiked) throws Exception {
		boolean result = false;
		result = productWishListDAO.verificationProductToWishlist(memberNo, productCode, isLiked);
		
		
		return result;
	}

	@Override
	public List<UserProductVO> getLikeProductList(int memberNo) throws Exception {
		
		List<UserProductVO> likeProductList = productWishListDAO.selectLikeProductList(memberNo);
		
		return likeProductList;
	}

	@Override
	public void deleteProductAllToWishList(int memberNo) throws Exception {

		productWishListDAO.deleteAllProductToWishlist(memberNo);

	}

	@Override
	public void verificationProductToCart(int memberNo, String productCode, int productQuantity) throws Exception {
		shoppingCartDAO.verificationProductToCart(memberNo, productCode, productQuantity);
		
	}

	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	@Override
	public UserMemberVO getMemberInfo(int member_no) throws Exception {
		UserMemberVO loginMemberInfo = myPageDAO.selectMemberInfo(member_no);
		return loginMemberInfo;
	}

	@Override
	public List<UserMemberHavingCouponVO> getMemberCouponHeld(int memberNo, PaginationInfo pi, SearchCriteria sc)
			throws Exception {
		return couponHeldDAO.selectMemberCouponInfo(memberNo, pi, sc);
	}

	@Override
	public Map<String, Object> getMemberTotalOrderInfo(int memberNo, String merchantUid) throws Exception {
		Map<String, Object> orderTotalInfoMap = new HashMap<String, Object>();

		// 주문 정보
		UserOrderInfoDTO orderInfo = orderSheetDAO.selectOrderInfo(memberNo, merchantUid);
		if (orderInfo.getDelivery_waiting_date() != null) {
			// 배송 대기중 변경 날짜
			LocalDate deliveryWatingDate = orderInfo.getDelivery_waiting_date().toLocalDate();
			// 배송 완료 날짜 ( 대기중 + 3 )
			orderInfo.setDelivery_done_date(deliveryWatingDate.plusDays(3));
		}
		orderTotalInfoMap.put("orderInfo", orderInfo);
		// 주문 진행 중인 목록
		orderTotalInfoMap.put("orderList", detailedOrderDAO.selectOrder(memberNo, merchantUid));
		// 취소, 반품 진행중인 목록
		orderTotalInfoMap.put("orderCancelAndReturnList",
				cancellationReturnDAO.selectMemberCancelOrderInfo(memberNo, merchantUid));
		// 교환 진행중인 목록
		orderTotalInfoMap.put("orderExchangeList", exchangeDAO.selectMemberExchangeOrderInfo(memberNo, merchantUid));
		return orderTotalInfoMap;
	}

	@Override
	public List<UserOrderListDTO> getOrderList(PaginationInfo pi, int memberNo, SearchCriteria sc) throws Exception {
		List<UserOrderListDTO> orderList = myPageDAO.selectOrderList(pi, memberNo, sc);
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getDelivery_waiting_date() != null) {
				// 배송 대기중 변경 날짜
				LocalDate deliveryWatingDate = orderList.get(i).getDelivery_waiting_date().toLocalDate();
				// 배송 완료 날짜 ( 대기중 + 3 )
				LocalDate deliveryDoneDate = deliveryWatingDate.plusDays(3);
				orderList.get(i).setDelivery_done_date(deliveryDoneDate);
			}
		}
		return orderList;
	}

	@Override
	public List<UserOrderWithdrawalListDTO> getOrderWithDrawalList(int memberNo, PaginationInfo pi, SearchCriteria sc)
			throws Exception {
		return myPageDAO.selectOrderWithDrawalList(memberNo, pi, sc);
	}

	@Override
	public UserOrderWithdrawalDTO getUserOrderWithdrawal(int memberNo, int detailed_order_no) throws Exception {

		return myPageDAO.selectUserOrderWithdrawal(memberNo, detailed_order_no);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int modifyOrderConfirm(Map<String, Object> confirmOrderInfo) throws Exception {
		int result = -1;
		// 주문확정 된 상품의 상태 변경
		result = detailedOrderDAO.updateDetailedOrderPaymentStateByOrderConfirm(confirmOrderInfo);
		if (result > 0) {
			// 해당 주문의 상세 주문번호 조회
			List<Integer> detailedOrderNoList = detailedOrderDAO.selectDetailedOrderNoList(confirmOrderInfo);
			System.out.println("해당 주문의 상세 주문 번호 목록 : " + detailedOrderNoList.toString());
			System.out.println("해당 주문의 모든 상세 주문 개수 : " + detailedOrderNoList.size());
			
			// 해당 주문의 구매 확정 상세주문 개수 조회
			int completeDetailedOrderCount = detailedOrderDAO.selectCompleteDetailedOrderCount(confirmOrderInfo);
			System.out.println("해당 주문의 확정 상태 상세 주문 개수 : " + completeDetailedOrderCount);
			
			// 해당 주문의 취소,반품 완료 상세 주문 개수 조회 
			int completeCancelDetailedOrderCount = detailedOrderDAO.selectCompleteCancelDetailedOrderCount(detailedOrderNoList);
			System.out.println("해당 주문의 취소, 반품 완료 주문 개수 : " + completeCancelDetailedOrderCount);
			
			// 해당 주문의 교환 완료 상세 주문 개수 조회
			int completeExchangeDetailedOrderCount = detailedOrderDAO.selectCompleteExchangeDetailedOrderCount(detailedOrderNoList);
			System.out.println("해당 주문의 교환 완료 주문 개수 : " + completeExchangeDetailedOrderCount);
			
			// 해당 주문의 상태가 완료인 모든 상세 주문의 개수
			int totalCompleteDetailedOrderCount = completeDetailedOrderCount + completeCancelDetailedOrderCount + completeExchangeDetailedOrderCount;
			System.out.println("완료 상태인 모든 상세 주문의 개수 : " + totalCompleteDetailedOrderCount);
			// 해당 주문의 실 결제 금액(현금 + 카드)
			int actualPaymentAmount = paymentDAO.selectActualPaymentAmount(confirmOrderInfo);
			System.out.println("총 결제 금액 : " + actualPaymentAmount);
			confirmOrderInfo.put("actualPaymentAmount", actualPaymentAmount);
			if (actualPaymentAmount > 0) {
				if (detailedOrderNoList.size() == totalCompleteDetailedOrderCount) {
					// 주문한 회원의 포인트 변동 로그 작성
					result = pointsAccuralLogDAO.insertPointsAccrualLogByOrderConfirm(confirmOrderInfo);
					if (result > 0) {
						// 주문한 회원의 총 보유 포인트 변경
						result = memberDAO.updateMemberAccrualAmountByConfirmOrder(confirmOrderInfo);
						if (result > 0) {
							// 주문의 총 완료날짜 업데이트
							result = orderSheetDAO.updateOrdersheetTotalCompleteDate(confirmOrderInfo);
							if (result <= 0) {
								System.out.println("주문의 총 완료날짜 업데이트 실패");
							}
						} else {
							System.out.println("주문한 회원의 총 보유 포인트양 변경 실패 ");
						}
					} else {
						System.out.println("주문한 회원의 포인트 변동 로그 작성 실패");
					}
				} else {
					System.out.println("상태가 확정이 아닌 주문이 존재함");
				}
			} else {
				System.out.println("전 액 포인트로 결제한 주문");
			}

		} else {
			System.out.println("주문확정 된 상품의 상태 변경 실패");
		}
		return result;
	}

	@Override
	public List<UserPointsAccrualLogVO> getPointsAccrualLog(int memberNo) throws Exception {
		return pointsAccuralLogDAO.selectPointsAccrualLog(memberNo);
	}

	@Override
	public List<UserPointsUsageLogVO> getPointsUsageLog(int memberNo) throws Exception {
		return pointsUsageLogDAO.selectPointsUsageLog(memberNo);
	}

	@Override
	public UserWithdrawalApplyDTO getWithdrawalApplyDTO(int detailedOrderNo) throws Exception {

		return myPageDAO.selectUserWithDrawalApplyDTO(detailedOrderNo);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addCancelAndExchangeOrderConfirm(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception {
		int result = -1;
		if (userWithDrawalConfirmDTO.getWhat().equals("취소") || userWithDrawalConfirmDTO.getWhat().equals("반품")) {
			result = cancellationReturnDAO.insertCancelAndReturnConfirm(userWithDrawalConfirmDTO);
		} else if (userWithDrawalConfirmDTO.getWhat().equals("교환")) {
			result = exchangeDAO.insertExchangeConfirm(userWithDrawalConfirmDTO);
		}
		result += detailedOrderDAO.updateDetailedOrderPaymentStateByWithDrawal(userWithDrawalConfirmDTO);
		return result;
	}

	@Override
	public List<UserShoppingCartDTO> getShoppingCartList(int memberNo) throws Exception {
		return shoppingCartDAO.selectShoppingCartList(memberNo);
	}

	@Override
	public int modifyShoppingCartProductCnt(List<Integer> shoppingCartNoList, List<Integer> shoppingQuantityList)
			throws Exception {
		return shoppingCartDAO.updateShoppingCartProductCnt(shoppingCartNoList, shoppingQuantityList);
	}

	@Override
	public int removeShoppingCart(int shoppingCartNo) throws Exception {

		return shoppingCartDAO.deleteShoppingCart(shoppingCartNo);
	}

	@Override
	public int removeAllShoppingCart(int memberNo) throws Exception {

		return shoppingCartDAO.deleteAllShoppingCart(memberNo);
	}

	@Override
	public PaginationInfo createOrderListPaginationInfo(int curPageNo, int rowCountPerPage, int memberNo,
			SearchCriteria sc) throws Exception {
		PaginationInfo pi = new PaginationInfo();
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		pi.setTotalRowCount(myPageDAO.selectAllOrderListCount(memberNo, sc));
		pi.paginationProcess();

		return pi;
	}

	@Override
	public PaginationInfo createCancelAndExchangeListPaginationInfo(int curPageNo, int rowCountPerPage, int memberNo,
			SearchCriteria sc) throws Exception {
		PaginationInfo pi = new PaginationInfo();
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		pi.setTotalRowCount(myPageDAO.selectAllCancelAndExchangeListCount(memberNo, sc));
		pi.paginationProcess();
		return pi;
	}

	@Override
	public PaginationInfo createCouponListPaginationInfo(int curPageNo, int rowCountPerPage, int memberNo,
			SearchCriteria sc) throws Exception {
		PaginationInfo pi = new PaginationInfo();
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		pi.setTotalRowCount(myPageDAO.selectAllCouponListCount(memberNo, sc));
		pi.paginationProcess();
		return pi;
	}

	@Override
	public UserProductReviewInfoDTO getProductReviewInfo(int detailedOrderNo) throws Exception {
		return reviewDAO.selectProductReviewInfo(detailedOrderNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addProductReview(UserProductReviewDTO productReviewDTO, List<UserProductReviewImageVO> fileList,
			int memberNo) throws Exception {
		int product_review_no = reviewDAO.insertProductReview(productReviewDTO);
		String merchant_uid = productReviewDTO.getMerchant_uid();
		int result = -1;
		if (fileList.size() > 0) {
			productReviewImageDAO.insertProductReviewImgs(product_review_no, fileList);

			pointsAccuralLogDAO.insertPointsAccrualLogByWritePhotoReview(memberNo, merchant_uid);
			result = memberDAO.updateMemberAccrualAmountByWritePhotoReview(memberNo);
		} else {
			pointsAccuralLogDAO.insertPointsAccrualLogByWriteReview(memberNo, merchant_uid);
			result = memberDAO.updateMemberAccrualAmountByWriteReview(memberNo);
		}
		return result;
	}

	@Override
	public int addAddressBook(UserAddressBookVO userAdressBookVO, int memberNo) throws Exception {
		int result = -1;
		UserMemberVO loginMember = myPageDAO.selectMemberInfo(memberNo);
		String loginMemberName = loginMember.getMember_name();
		String addressName = userAdressBookVO.getAddress_name();

		if (addressName.equals("")) {
			userAdressBookVO.setAddress_name(loginMemberName + "님 배송지");
		}
		if (userAdressBookVO.getIs_default().equals("Y")) {
			if (addressBookDAO.selectDefaultAddress(memberNo) >= 0) {
				addressBookDAO.updateDefaultAddress(addressBookDAO.selectDefaultAddress(memberNo));
				result = addressBookDAO.insertAddressBook(userAdressBookVO);
			}
		} else {
			result = addressBookDAO.insertAddressBook(userAdressBookVO);
		}
		return result;
	}

	@Override
	public List<UserAddressBookVO> getAllAddressBook(int memberNo) throws Exception {
		List<UserAddressBookVO> addressBookList = addressBookDAO.selectAllAdressBook(memberNo);

		for (UserAddressBookVO addressBook : addressBookList) {
			String maskedRecipient = nameMasking(addressBook.getRecipient());
			String maskedPhoneNumber = phoneMasking(addressBook.getPhone_number());
			String maskedAddress = addressMasking(addressBook.getAddress());
			addressBook.setRecipient(maskedRecipient);
			addressBook.setPhone_number(maskedPhoneNumber);
			addressBook.setAddress(maskedAddress);
		}
		return addressBookList;
	}

	// 이름 마스킹
	public static String nameMasking(String recipient) throws Exception {
		// 한글만 (영어, 숫자 포함 이름은 제외)
		String regex = "(^[가-힣]+)$";

		Matcher matcher = Pattern.compile(regex).matcher(recipient);
		if (matcher.find()) {
			int length = recipient.length();

			String middleMask = "";
			if (length > 2) {
				middleMask = recipient.substring(1, length - 1);
			} else { // 이름이 외자
				middleMask = recipient.substring(1, length);
			}

			String dot = "";
			for (int i = 0; i < middleMask.length(); i++) {
				dot += "*";
			}
			if (length > 2) {
				return recipient.substring(0, 1) + middleMask.replace(middleMask, dot)
						+ recipient.substring(length - 1, length);
			} else { // 이름이 외자 마스킹 리턴
				return recipient.substring(0, 1) + middleMask.replace(middleMask, dot);
			}
		}
		return recipient;
	}

	// 휴대폰번호 마스킹(가운데 숫자 4자리 마스킹)
	public static String phoneMasking(String phoneNo) throws Exception {
		String regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$";

		Matcher matcher = Pattern.compile(regex).matcher(phoneNo);
		if (matcher.find()) {
			String target = matcher.group(2);
			int length = target.length();
			char[] c = new char[length];
			Arrays.fill(c, '*');

			return phoneNo.replace(target, String.valueOf(c));
		}
		return phoneNo;
	}

	// 주소 마스킹(신주소, 구주소, 도로명 주소 숫자만 전부 마스킹)
	public static String addressMasking(String address) throws Exception {
		// 신(구)주소, 도로명 주소
		String regex = "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|";
		String newRegx = "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))";

		Matcher matcher = Pattern.compile(regex).matcher(address);
		Matcher newMatcher = Pattern.compile(newRegx).matcher(address);
		if (matcher.find()) {
			return address.replaceAll("[0-9]", "*");
		} else if (newMatcher.find()) {
			return address.replaceAll("[0-9]", "*");
		}
		return address;
	}

	@Override
	public UserAddressBookVO selectAddressInfo(int memberAddressBookNo) throws Exception {

		return addressBookDAO.selectAddressInfo(memberAddressBookNo);
	}

	// 배송지 수정 중 기본 배송지일 경우 처리
	@Override
	public int modifyAddressBook(UserAddressBookVO addressBookVO, int memberNo) throws Exception {
		int result = -1;
		UserMemberVO loginMember = myPageDAO.selectMemberInfo(memberNo);
		String loginMemberName = loginMember.getMember_name();
		String addressName = addressBookVO.getAddress_name();

		if (addressName.equals("")) {
			addressBookVO.setAddress_name(loginMemberName + "님 배송지");
		}
		if (addressBookVO.getIs_default().equals("Y")) {
			if (addressBookDAO.selectDefaultAddress(addressBookVO.getMember_no()) > 0) {
				addressBookDAO.updateDefaultAddress(addressBookDAO.selectDefaultAddress(addressBookVO.getMember_no()));
				result = addressBookDAO.updateAddressBook(addressBookVO);
			} else if (addressBookDAO.selectDefaultAddress(addressBookVO.getMember_no()) == 0) {
				result = addressBookDAO.updateAddressBook(addressBookVO);
			}
		} else if (addressBookVO.getIs_default().equals("N")) {
			addressBookDAO.updateAddressBook(addressBookVO);
			if (addressBookDAO.selectDefaultAddress(addressBookVO.getMember_no()) == 0) {
				addressBookVO.setIs_default("Y");
				addressBookDAO.updateAddressBook(addressBookVO);
			}
		} else {
			result = addressBookDAO.updateAddressBook(addressBookVO);
		}
		return result;
	}

	@Override
	public List<String> getAddressBookNameList(int member_no) {
		return addressBookDAO.selectAddressBookNameList(member_no);
	}

	@Override
	public int removeAddressBook(int addressBookNo) throws Exception {
		return addressBookDAO.deleteAddressBook(addressBookNo);
	}

	@Override
	public UserAddressBookVO getDefaultAddressBook(int member_no) throws Exception {
		return addressBookDAO.selectDefaultAddressBook(member_no);
	}

	@Override
	public UserProductReviewDTO getProductReview(int detailed_order_no) throws Exception {
		return reviewDAO.selectProductReview(detailed_order_no);
	}

	@Override
	public int modifyProductReview(UserProductReviewDTO productReviewDTO, List<UserProductReviewImageVO> fileList,
			String realPath) throws Exception {
		int deleteCnt = productReviewImageDAO.deleteAll(productReviewDTO.getProduct_review_no());
		System.out.println("기존의" + deleteCnt + "개 파일 삭제");
		if (fileList.size() > 0) {
			int insertCnt = productReviewImageDAO.insertProductReviewImgs(productReviewDTO.getProduct_review_no(),
					fileList);
			System.out.println("새로운" + insertCnt + "개 파일 추가");
		}

		return reviewDAO.updateProductReview(productReviewDTO);
	}

	@Override
	public List<UserProductReviewImageVO> getProductReviewImg(int product_review_no) throws Exception {

		return productReviewImageDAO.selectProductReviewImg(product_review_no);
	}

	@Override
	public List<UserInquiryInfoVO> getAnsweredInquiry(int memberNo) throws Exception {
		List<UserInquiryInfoVO> result = inquiryBoardDAO.selectAnsweredInquiry(memberNo);
		return result;
	}

	@Override
	public List<UserInquiryInfoVO> getNoAnsweredInquiry(int memberNo) throws Exception {
		List<UserInquiryInfoVO> result = inquiryBoardDAO.selectNoAnsweredInquiry(memberNo);
		return result;
	}

	@Override
	public int getReviewCount(int memberNo) throws Exception {
		return reviewDAO.selectReviewCount(memberNo);
	}

	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
