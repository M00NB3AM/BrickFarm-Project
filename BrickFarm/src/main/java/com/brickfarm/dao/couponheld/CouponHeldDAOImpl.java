package com.brickfarm.dao.couponheld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminGiveCouponDTO;
import com.brickfarm.dto.user.syt.UserCouponHeldDTO;
import com.brickfarm.etc.psj.PaginationInfo;
import com.brickfarm.etc.psj.SearchCriteria;
import com.brickfarm.vo.user.psj.UserMemberHavingCouponVO;

@Repository
public class CouponHeldDAOImpl implements CouponHeldDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.CouponHeldMapper";

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
		
		@Override
		public boolean insertCouponToMember(AdminGiveCouponDTO couponInfo) throws Exception {
			boolean result = false;
			
			if(couponInfo.getMember_no().size() == ses.insert(ns + ".insertCouponToMember", couponInfo)) {
				result = true;
			}
						
			return result;
		}
		
		@Override
		public int insertCouponToGrade(AdminGiveCouponDTO couponInfo) {
			
			return ses.insert(ns + ".insertCouponToGrade", couponInfo);
		}
		
		@Override
		public int insertBirthDayCoupon(String mmDD) throws Exception {
		
			return ses.insert(ns + ".insertBirthDayCoupon", mmDD);
		}
		
		@Override
		public List<Integer> selectExpirationCoupon() throws Exception {

			return ses.selectList(ns + ".selectExpirationCoupon");
			
		}
		

		@Override
		public int updateAvailableCoupon(List<Integer> expireCouponList) {
			return ses.update(ns + ".updateAvailableCoupon", expireCouponList);
		}


		
		@Override
		public int selectAvailableCoupon(AdminGiveCouponDTO delCouponNo) {
			if(delCouponNo.getMember_no() == null || delCouponNo.getMember_no().size() == 0) {
				
				return ses.selectOne(ns + ".selectAvailableCoupon", delCouponNo);
			} else {
				return ses.selectOne(ns + ".selectAvailableCoupons", delCouponNo);
			}
		}
		
	
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
		@Override
		public List<UserMemberHavingCouponVO> selectMemberCouponInfo(int member_no, PaginationInfo pi, SearchCriteria sc) throws Exception {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("member_no", member_no);
			params.put("startRowIndex", pi.getStartRowIndex());
			params.put("rowCountPerPage", pi.getRowCountPerPage());
			params.put("searchState", sc.getSearchState());
			params.put("searchOrder", sc.getSearchOrder());
			return ses.selectList(ns + ".selectCouponInfo", params);
		}
		
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : selectCouponHeld
	 * @author : syt
	 * @param member_no
	 * @return
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:40:29
	 * @description : 유저가 보유하고 있는 쿠폰목록
	 */
	@Override
	public List<UserCouponHeldDTO> selectCouponHeld(int member_no) throws Exception {
		return ses.selectList(ns + ".selectCouponHeld", member_no);
	}
	
	/**
	 * @methodName : updateCouponHeldOnAvailableCoupon
	 * @author : syt
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:39:46
	 * @description : [결제] 사용한 쿠폰 사용으로 변경
	 */
	@Override
	public int updateCouponHeldOnAvailableCoupon(int coupon_held_no) throws Exception {
		return ses.update(ns + ".updateCouponHeldOnAvailableCoupon", coupon_held_no);
	}
	
	/**
	 * @methodName : getValidationRate
	 * @author : syt
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 16. 오후 5:44:20
	 * @description : 결제검증을 위해 쿠폰할인율 얻어오기
	 */
	@Override
	public float getValidationRate(int coupon_held_no) throws Exception {
		return ses.selectOne(ns + ".getValidationRate", coupon_held_no);
	}
	
	/**
	 * @methodName : updateSchedulerCouponYN
	 * @author : syt
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:35:38
	 * @description :  [스케줄러] 기한만료로 취소된 결제건 쿠폰 되돌리기
	 */
	@Override
	public int updateSchedulerCouponYN(int coupon_held_no) throws Exception {
		return ses.update(ns + ".updateSchedulerCouponYN", coupon_held_no);
	}
	
	// ==================================================================================================================================================






	

	

	

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
