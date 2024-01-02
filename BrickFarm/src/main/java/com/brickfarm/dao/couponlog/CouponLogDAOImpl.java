package com.brickfarm.dao.couponlog;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.vo.admin.kmh.AdminCouponLogVO;
import com.brickfarm.vo.admin.kmh.AdminLoginLogVO;
import com.brickfarm.dto.user.syt.UserCouponLogDTO;	

@Repository
public class CouponLogDAOImpl implements CouponLogDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.CouponLogMapper";

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================	
	@Override
	public List<AdminCouponLogVO> selectCouponUseLog(AdminLogDTO couponLog) throws Exception {
		return ses.selectList(ns + ".findCouponUseLog", couponLog);
	}
	
	@Override
	public List<AdminCouponLogVO> selectCouponAllLog(AdminLogDTO couponLog) throws Exception {
		return ses.selectList(ns + ".findCouponAllLog", couponLog);
	}
	
	@Override
	public int insertExpireCoupon(List<Integer> expireCouponList) {
		return ses.insert(ns + ".insertExpireCoupon", expireCouponList);
	}
	
	// ==================================================================================================================================================


	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : insertCouponLog
	 * @author : syt
	 * @param couponLog
	 * @return
	 * @returnValue : @param couponLog
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:32:20
	 * @description : [결제] 사용한 쿠폰 로그 남기기
	 */
	@Override
	public int insertCouponLog(UserCouponLogDTO couponLog) throws Exception {
		return ses.insert(ns + ".insertCouponLog", couponLog);
	}

	/**
	 * @methodName : deleteSchedulerCoupon
	 * @author : syt
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:32:18
	 * @description : [스케줄러] 입금기한만료로 취소된 결제건 쿠폰 되돌리기
	 */
	@Override
	public int deleteSchedulerCoupon(int coupon_held_no) throws Exception {
		return ses.delete(ns + ".deleteSchedulerCoupon", coupon_held_no);
	}

	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
