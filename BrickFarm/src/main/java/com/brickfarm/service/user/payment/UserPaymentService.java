package com.brickfarm.service.user.payment;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.user.syt.UserCompleteDataDTO;
import com.brickfarm.dto.user.syt.UserCouponHeldDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserOrderProductDTO;
import com.brickfarm.dto.user.syt.UserOrdersheetDTO;
import com.brickfarm.dto.user.syt.UserPaymentDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.vo.user.syt.UserAddressVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

public interface UserPaymentService {
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
	// 뷰 유저보유 쿠폰 출력
	List<UserCouponHeldDTO> findCouponHeld(int member_no) throws Exception;
	// 결제완료
	boolean inputCompleteOrder(UserOrdersheetDTO ordersheet, UserPaymentDTO payment, List<UserDetailedOrderDTO> detailedOrderList, UserCompleteDataDTO completeData) throws Exception;
	// 결제완료에 사용할 객체 생성하는 메서드
	Map<String, Object> makeCompleteOrderByDataToUse(UserOrdersheetDTO ordersheet, UserPaymentDTO payment, UserCompleteDataDTO completeData, List<UserPaymentListDTO> paymentList, UserMemberVO member) throws Exception;
	// 새로고침을 위한 자료 검색
	boolean hasPaymentByImpUid(String imp_uid) throws Exception;
	// 장바구니에서 넘어온 상품의 정보 리스트 받아오기
	List<UserOrderProductDTO> getDetailedOrder(List<Integer> shoppingCartNoList) throws Exception;
	// 세션의 유저PK로 맴버 정보 받아오기
	UserMemberVO getMember(int member_no) throws Exception;
	// 재고,포인트,쿠폰 사용 유효성검사
	String verifyAll(int member_no, int point_pay_money, int coupon_held_no, List<UserPaymentListDTO> paymentList) throws Exception;
	// 배송지테이블 가져옴
	List<UserAddressVO> getAddressBook(int member_no) throws Exception;
	// API 가격 검증을 위해 데이터 얻어오기
	int getValidationPrice(List<UserPaymentListDTO> paymentList, int coupon_held_no, int post_money, int point_pay_money) throws Exception;
	// ==================================================================================================================================================


	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
