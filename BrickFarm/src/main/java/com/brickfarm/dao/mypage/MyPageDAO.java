package com.brickfarm.dao.mypage;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.user.psj.UserWithdrawalApplyDTO;
import com.brickfarm.dto.user.psj.UserOrderListDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalListDTO;
import com.brickfarm.etc.psj.PaginationInfo;
import com.brickfarm.etc.psj.SearchCriteria;
import com.brickfarm.vo.user.ysh.UserMemberVO;

public interface MyPageDAO {

	// ==[이경민]==========================================================================================================================================

	// ====================================================================================================================================================

	// 로그인한 멤버의 모든 정보를 select하는 메서드
	UserMemberVO selectMemberInfo(int member_no) throws Exception;

	// 로그인한 멤버의 요약 주문 정보 조회(리스트 업용)
	List<UserOrderListDTO> selectOrderList(PaginationInfo pi, int memberNo, SearchCriteria sc) throws Exception;

	// 로그인한 멤버의 교환, 취소, 반품 물품리스트 조회
	List<UserOrderWithdrawalListDTO> selectOrderWithDrawalList(int memberNo, PaginationInfo pi, SearchCriteria sc) throws Exception;

	// 로그인한 멤버의 교환, 취소, 반품 상품 상세 조회
	UserOrderWithdrawalDTO selectUserOrderWithdrawal(int memberNo, int detailed_order_no) throws Exception;

	// 구매 확정 시 회원의 포인트 총 보유량 수정
	int updateMemberPoint(Map<String, Object> confirmOrderInfo) throws Exception;

	// 교환, 취소, 반품 신청에 필요한 데이터 조회
	UserWithdrawalApplyDTO selectUserWithDrawalApplyDTO(int detailedOrderNo) throws Exception;

	// 검색 조건에 따라 조회된 총 데이터 개수 조회 - 주문 목록
	int selectAllOrderListCount(int memberNo, SearchCriteria sc) throws Exception;

	// 검색 조건에 따라 조회된 총 데이터 개수 조회 - 취소, 교환, 환불 목록
	int selectAllCancelAndExchangeListCount(int memberNo, SearchCriteria sc) throws Exception;

	// 검색 조건에 따라 조회된 총 데이터 개수 조회 - 쿠폰 목록
	int selectAllCouponListCount(int memberNo, SearchCriteria sc) throws Exception;

	

}
