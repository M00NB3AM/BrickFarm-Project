package com.brickfarm.service.user.mypage;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.brickfarm.dto.user.psj.UserWithdrawalApplyDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
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
import com.brickfarm.vo.user.psj.UserCancelAndExchangeApplyVO;
import com.brickfarm.vo.user.psj.UserInquiryInfoVO;
import com.brickfarm.vo.user.psj.UserPointsAccrualLogVO;
import com.brickfarm.vo.user.psj.UserPointsUsageLogVO;
import com.brickfarm.vo.user.psj.UserProductReviewImageVO;
import com.brickfarm.vo.user.psj.ShoppingCartVO;
import com.brickfarm.vo.user.psj.UserAddressBookVO;
import com.brickfarm.vo.user.psj.UserMemberHavingCouponVO;
import com.brickfarm.vo.user.psj.UserOrderDetailVO;
import com.brickfarm.vo.user.psj.UserOrderListVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

/**
 * 테스트용 : 마이페이지 관련 서비스 인터페이스입니다?
 * 으아아아악
 * @author : psj
 */
public interface MyPageService {
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	
	// 찜 목록에 상품 추가 / 삭제
	boolean verificationProductToWishlist(int memberNo, String productCode, boolean isLiked) throws Exception;
	
	// 찜 목록 리스트 가져오기
	List<UserProductVO> getLikeProductList(int memberNo) throws Exception;
	
	// 찜 목록 전체 삭제
	void deleteProductAllToWishList(int memberNo) throws Exception;
	
	// 장바구니에 상품 추가
	void verificationProductToCart(int memberNo, String productCode, int productQuantity) throws Exception;
	
	
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// 주문 목록(List) 조회시 검색 조건에 따른 페이지네이션 생성
	PaginationInfo createOrderListPaginationInfo(int curPageNo, int rowCountPerPage,int memberNo, SearchCriteria sc) throws Exception;
	
	// 취소,교환,환불 목록(List) 조회시 검색 조건에 따른 페이지네이션 생성
	PaginationInfo createCancelAndExchangeListPaginationInfo(int curPageNo, int rowCountPerPage,int memberNo, SearchCriteria sc) throws Exception;
	
	// 쿠폰 목록(List) 조회시 검색 조건에 따른 페이지네이션 생성
	PaginationInfo createCouponListPaginationInfo(int curPageNo, int rowCountPerPage,int memberNo, SearchCriteria sc) throws Exception;
	
	// myPage에 접속한 loginMember의 VO를 가져오는 메서드
	UserMemberVO getMemberInfo(int member_no) throws Exception;

	// myPage에 접속한 loginMember의 총 보유 쿠폰 정보를 가져오는 메서드
	List<UserMemberHavingCouponVO> getMemberCouponHeld(int memberNo, PaginationInfo pi, SearchCriteria sc) throws Exception;
	
	// myPage에 접속한 loginMember의 총 요약 주문 정보를 가져오는 메서드
	List<UserOrderListDTO> getOrderList(PaginationInfo pi, int memberNo, SearchCriteria sc) throws Exception;
	
	// myPage에 접속한 loginMember의 총 주문 정보를 가져오는 메서드
	Map<String, Object> getMemberTotalOrderInfo(int memberNo, String merchantUid) throws Exception;
	
	// myPage에 접속한 loginMember의 교환, 취소, 환불 품목 리스트 가져오는 메서드
	List<UserOrderWithdrawalListDTO> getOrderWithDrawalList(int memberNo, PaginationInfo pi,SearchCriteria sc) throws Exception;
	
	// myPage에 접속한 loginMember의 교환, 취소, 환불 품목 상세 정보 가져오는 메서드
	UserOrderWithdrawalDTO getUserOrderWithdrawal(int memberNo, int detailed_order_no ) throws Exception;
	
	// 취소, 반품,교환 신청폼에 필요한 데이터 조회
	UserWithdrawalApplyDTO getWithdrawalApplyDTO(int detailedOrderNo) throws Exception;
	
	// 취소, 반품,교환 신청
	int addCancelAndExchangeOrderConfirm(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception;
	
	// 구매확정
	int modifyOrderConfirm(Map<String, Object> confirmOrderInfo) throws Exception;
	
	// 포인트 적립 내역 가져오기
	List<UserPointsAccrualLogVO> getPointsAccrualLog(int memberNo) throws Exception;
	
	// 포인트 변동 내역 가져오기
	List<UserPointsUsageLogVO> getPointsUsageLog(int memberNo) throws Exception;
	
	// 배송지 전체 목록 조회
	List<UserAddressBookVO> getAllAddressBook(int memberNo) throws Exception; 
	
	// 배송지 추가
	int addAddressBook(UserAddressBookVO userAdressBookVO, int memberNo) throws Exception;
	
	// 배송지 수정
	int modifyAddressBook(UserAddressBookVO addressBookVO, int memberNo)throws Exception;
	
	// 배송지 수정 폼 호출 시 기존의 배송지 정보 조회
	UserAddressBookVO selectAddressInfo(int memberAddressBookNo) throws Exception;
		
	// 배송지 이름 목록 조회
	List<String> getAddressBookNameList(int member_no) throws Exception;
	// 기본 배송지 조회
	UserAddressBookVO getDefaultAddressBook(int member_no) throws Exception;
	
	// 배송지 삭제
	int removeAddressBook(int addressBookNo) throws Exception;
	
	// 장바구니 목록(LIst)조회
	List<UserShoppingCartDTO> getShoppingCartList(int memberNo) throws Exception;
	
	// 장바구니 품목 수량 변경
	int modifyShoppingCartProductCnt(List<Integer> shoppingCartNoList, List<Integer> shoppingQuantityList) throws Exception;
	
	// 장바구니 단일 품목 삭제
	int removeShoppingCart(int shoppingCartNo) throws Exception;
	
	// 장바구니 모든 품목 삭제
	int removeAllShoppingCart(int memberNo) throws Exception;
	
	// 리뷰 작성시 필요한 제품 정보 조회
	UserProductReviewInfoDTO getProductReviewInfo(int detailedOrderNo) throws Exception;
	
	// 리뷰 작성 완료 
	int addProductReview(UserProductReviewDTO productReviewDTO, List<UserProductReviewImageVO> fileList , int memberNo) throws Exception;
	
	// 리뷰 수정시 작성된 리뷰 조회 
	UserProductReviewDTO getProductReview(int product_review_no) throws Exception;
	
	// 작성된 리뷰 이미지 파일 조회 
	List<UserProductReviewImageVO> getProductReviewImg(int product_review_no) throws Exception;
	
	// 리뷰 수정
	int modifyProductReview(UserProductReviewDTO productReviewDTO, List<UserProductReviewImageVO> fileList, String realPath) throws Exception;
	
	// 답변 된 문의 내역 조회
	List<UserInquiryInfoVO> getAnsweredInquiry(int memberNo) throws Exception;
	
	// 미답변 문의 내역 조회
	List<UserInquiryInfoVO> getNoAnsweredInquiry(int memberNo) throws Exception;
	
	// 작성한 리뷰 수 조회 
	int getReviewCount(int memberNo) throws Exception;
	// ==================================================================================================================================================


	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
