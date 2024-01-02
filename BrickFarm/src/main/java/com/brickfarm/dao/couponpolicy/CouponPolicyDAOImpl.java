package com.brickfarm.dao.couponpolicy;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminAvailableCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminCouponDTO;
import com.brickfarm.vo.admin.kmh.AdminCouponVo;

@Repository
public class CouponPolicyDAOImpl implements CouponPolicyDAO {
	
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.CouponPolicyMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	
	@Override
	public List<AdminCouponVo> selectCouponList() throws Exception {
		return ses.selectList(ns + ".findCouponList");
	}
	
	@Override
	public List<AdminCouponVo> selectCouponList(AdminCouponDTO couponOption) {
		return ses.selectList(ns + ".findCouponOptional", couponOption);
	}
	
	@Override
	public int insertCouponPolicy(AdminCouponDTO tmpCoupon) throws Exception {
		return ses.insert(ns + ".makeCoupon", tmpCoupon);
	}
	
	@Override
	public int deleteCoupon(String coupon_policy_no) throws Exception {
		return ses.update(ns + ".removeCoupon", coupon_policy_no);
	}
	@Override
	public int updateAvailable(AdminAvailableCouponDTO couponInfo) throws Exception {
		return ses.update(ns + ".updateAvailable", couponInfo);
	}
	// ==================================================================================================================================================









	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
