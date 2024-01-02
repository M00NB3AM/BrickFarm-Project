package com.brickfarm.dao.pointsaccurallog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.vo.admin.kmh.AdminPointLogVO;
import com.brickfarm.vo.user.psj.UserPointsAccrualLogVO;

@Repository
public class PointsAccuralLogDAOImpl implements PointsAccuralLogDAO{
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.PointsAccuralLogMapper";
	

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================

	@Override
	public List<AdminPointLogVO> selectPointAccrualLog(AdminLogDTO pointLog) {
		return ses.selectList(ns + ".findPointAccrualLog", pointLog);
	}
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================

	@Override
	public List<UserPointsAccrualLogVO> selectPointsAccrualLog(int member_no) throws Exception {
		return ses.selectList(ns + ".selectPointsAccrualLog", member_no);
	}
	
	@Override
	public int insertPointsAccrualLogByOrderConfirm(Map<String, Object> confirmOrderInfo) {
		return ses.insert(ns + ".insertPointsAccrualLogByOrderConfirm", confirmOrderInfo);
	}
	@Override
	public int insertPointsAccrualLogByWriteReview(int memberNo,String merchant_uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberNo", memberNo);
		params.put("merchantUid", merchant_uid);
		return ses.insert(ns + ".insertPointsAccrualLogByWriteReview", params);
	}

	@Override
	public int insertPointsAccrualLogByWritePhotoReview(int memberNo ,String merchant_uid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberNo", memberNo);
		params.put("merchantUid", merchant_uid);
		return ses.insert(ns + ".insertPointsAccrualLogByWritePhotoReview", params);
	}
	
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	@Override
	public int insertPointAccrualLogByCompletion(List<String> haveToMerchantUidList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("haveToMerchantUidList", haveToMerchantUidList);
		return ses.insert(ns + ".insertPointAccrualLogByCompletion", param);
	}
	// ==================================================================================================================================================
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
