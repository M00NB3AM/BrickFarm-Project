package com.brickfarm.dao.couponheld;

import java.util.List;

import com.brickfarm.dto.admin.kmh.AdminGiveCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.dto.user.syt.UserCouponHeldDTO;
import com.brickfarm.vo.admin.kmh.AdminCouponLogVO;
import com.brickfarm.vo.user.psj.UserMemberHavingCouponVO;
import com.brickfarm.dto.user.syt.UserCouponLogDTO;
import com.brickfarm.etc.psj.PaginationInfo;
import com.brickfarm.etc.psj.SearchCriteria;

public interface CouponHeldDAO {
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
		
		// 아이디별 쿠폰 일괄 지급
		boolean insertCouponToMember(AdminGiveCouponDTO couponInfo) throws Exception;
		
		// 등급별 쿠폰 지급
		int insertCouponToGrade(AdminGiveCouponDTO couponInfo) throws Exception;
		
		// 생일 쿠폰 지급
		int insertBirthDayCoupon(String mmDD) throws Exception;
		
		// 만료 시켜야 하는 쿠폰 조회
		List<Integer> selectExpirationCoupon() throws Exception;
		
		// available_coupon = 'N'으로 업데이트
		int updateAvailableCoupon(List<Integer> expireCouponList) throws Exception;
		
		// 삭제할 쿠폰을 보유하고 있는 회원 수 조회
		int selectAvailableCoupon(AdminGiveCouponDTO delCouponNo) throws Exception;

	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
		// 로그인한 멤버의 보유 쿠폰 정보 select 하는 메서드
	List<UserMemberHavingCouponVO> selectMemberCouponInfo(int memberNo, PaginationInfo pi, SearchCriteria sc) throws Exception;
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	List<UserCouponHeldDTO> selectCouponHeld(int member_no) throws Exception;
	int updateCouponHeldOnAvailableCoupon(int coupon_held_no) throws Exception;
	// 결제 검증을 위해 쿠폰할인율 가져오기
	float getValidationRate(int coupon_held_no) throws Exception;
	// [스케줄러] 기한만료로 취소된 결제건 쿠폰 되돌리기
	int updateSchedulerCouponYN(int coupon_held_no) throws Exception;
	// ==================================================================================================================================================








	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
