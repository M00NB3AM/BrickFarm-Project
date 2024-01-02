package com.brickfarm.service.admin.member;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.kmh.AdminAvailableCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminGiveCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageDTO;
import com.brickfarm.dto.admin.kmh.AdminNoActiveMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminOrderMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminWithdrawMemberDTO;
import com.brickfarm.vo.admin.kmh.AdminChartVO;
import com.brickfarm.vo.admin.kmh.AdminCouponLogVO;
import com.brickfarm.vo.admin.kmh.AdminCouponVo;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderInfoVO;
import com.brickfarm.vo.admin.kmh.AdminInactiveMemberVO;
import com.brickfarm.vo.admin.kmh.AdminLoginLogVO;
import com.brickfarm.vo.admin.kmh.AdminMemberDashboardVO;
import com.brickfarm.vo.admin.kmh.AdminMemberVO;
import com.brickfarm.vo.admin.kmh.AdminOrderMemberVO;
import com.brickfarm.vo.admin.kmh.AdminPointLogVO;
import com.brickfarm.vo.admin.kmh.AdminWithdrawMemberVO;
import com.brickfarm.vo.admin.ysh.AdminVO;

public interface MemberManagingService {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
		
		// 대시보드에 들어갈 통계
		AdminMemberDashboardVO countAboutMember() throws Exception;
		
		// 최근 가입한 회원 조회
		List<AdminMemberVO> findRecentRegistMember() throws Exception;
		
		// 회원 조회
		List<AdminMemberVO> findMember(AdminMemberDTO tmpMember) throws Exception;

		// 휴먼 회원 조회
		List<AdminInactiveMemberVO> findInactiveMember(AdminNoActiveMemberDTO tmpMember) throws Exception;

		// 주문 이력이 있는 회원 조회
		List<AdminOrderMemberVO> findOrderMember(AdminOrderMemberDTO tmpMember) throws Exception;

		// 탈퇴 회원 조회
		List<AdminWithdrawMemberVO> findWithdrawMember(AdminNoActiveMemberDTO tmpMember) throws Exception;
		
		// 쿠폰 목록 조회
		List<AdminCouponVo> findAllCoupon() throws Exception;
		
		// 쿠폰 옵셔널 조회(쿠폰 조회 하나로 합치기)
		List<AdminCouponVo> findAllCoupon(AdminCouponDTO couponOption) throws Exception;

		// 회원 강제 탈퇴
		boolean removeMemberTransaction(AdminWithdrawMemberDTO tmpdelMember) throws Exception;

		// 쿠폰 생성
		boolean makeCoupon(AdminCouponDTO tmpCoupon) throws Exception;

		// 쿠폰 삭제
		boolean removeCoupon(String coupon_policy_no) throws Exception;

		// 아이디별 쿠폰 일괄 지급
		boolean giveCouponToMember(AdminGiveCouponDTO couponInfo) throws Exception;

		// 로그인 이력 조회
		List<AdminLoginLogVO> findLoginMember(AdminMemberDTO loginMember) throws Exception;
		
		// 쿠폰 이력 조회
		List<AdminCouponLogVO> findCouponLog(AdminLogDTO couponLog) throws Exception;

		// 포인트 사용 적립 이력 조회
		List<AdminPointLogVO> findPointLog(AdminLogDTO pointLog) throws Exception;

		// 등급별 쿠폰 지급
		boolean giveCouponToGrade(AdminGiveCouponDTO couponInfo) throws Exception;

		// 6개월 내 로그인 이력 없는 회원 휴먼회원 전환
		boolean updateInactiveMember() throws Exception;

		// 생일 쿠폰 발급
		boolean giveBirthDayCoupon(String mmDD) throws Exception;

		// 쿠폰 만료
		boolean expireCoupon() throws Exception;

		// 매월 등급 update 및 쿠폰 발급
		boolean updateMemberGrade() throws Exception;

		// 삭제할 쿠폰을 보유하는 회원 수
		int countAvailableCoupon(AdminGiveCouponDTO delCouponNo) throws Exception;

		// 수동 발급 여부 변경
		boolean changeAvailableCoupon(AdminAvailableCouponDTO couponInfo) throws Exception;

		// 활성화 시키기
		boolean activeMember(String member_id) throws Exception;

		// 1개월 내 주문 건수 가장 높은 회원부터 찾기
		List<AdminOrderMemberVO> findBestCustomer() throws Exception;

		// 회원들에게 문자 보내기
		boolean sendMessageToMember(AdminMessageDTO tmpMemssage) throws Exception;

		// 회원 주문 내역
		Map<String, Object> findOrderList(int member_no, int pageNo, boolean withdraw_member) throws Exception;

		// 회원 주문 상세 내역 조회
		AdminDetailOrderInfoVO findDetailOrder(String merchant, boolean withdraw_member) throws Exception;

		// 탈퇴 회원 정보 조회
		Map<String, Object> findWithdrawMemberInfo(AdminNoActiveMemberDTO tmpMember, int pageNo) throws Exception;

		// 가입부터 주문까지 걸리는 시간 구하기
		AdminChartVO findTimeToOrder() throws Exception;
	
	
	// ==================================================================================================================================================

	
	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
		
		//관리자 로그인시 아이디 체크
		String loginAdminCheck(String admin_id, String admin_password) throws Exception;

		//관리자 로그인
		AdminVO adminLogin(String admin_id, String admin_password) throws Exception;

	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
