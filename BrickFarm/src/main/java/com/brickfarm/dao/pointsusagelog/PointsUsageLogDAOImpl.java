package com.brickfarm.dao.pointsusagelog;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.vo.admin.kmh.AdminPointLogVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;
import com.brickfarm.vo.user.psj.UserPointsUsageLogVO;
import com.brickfarm.dto.user.syt.UserPointsUsageLogDTO;

@Repository
public class PointsUsageLogDAOImpl implements PointsUsageLogDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.PointsUsageLogMapper";

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	@Override
	public List<AdminPointLogVO> selectPointUseLog(AdminLogDTO pointLog) throws Exception {
		return ses.selectList(ns + ".findPointUseLog", pointLog);
	}
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	@Override
	public List<UserPointsUsageLogVO> selectPointsUsageLog(int member_no) throws Exception {
		if (ses.selectList(ns + ".selectPointsUsageLog", member_no).contains(null)) {
			return Collections.emptyList();
		}
		return ses.selectList(ns + ".selectPointsUsageLog", member_no);
	}

	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : insertPointsUsageLog
	 * @author : syt
	 * @param pointsUsageLog
	 * @return
	 * @returnValue : @param pointsUsageLog
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 12:30:00
	 * @description : 결제시 포인트사용 로그 남김
	 */
	@Override
	public int insertPointsUsageLog(UserPointsUsageLogDTO pointsUsageLog) throws Exception {
		return ses.insert(ns + ".insertPointsUsageLog", pointsUsageLog);
	}

	/**
	 * @methodName : insertCancelPoint
	 * @author : syt
	 * @param adminCancelData
	 * @return
	 * @returnValue : @param adminCancelData
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 12:30:10
	 * @description : 결제취소시 포인트반환 로그남김
	 */
	@Override
	public int insertCancelPoint(AdminCancelDataDTO adminCancelData) throws Exception {
		return ses.insert(ns + ".insertCancelPoint", adminCancelData);
	}

	/**
	 * @methodName : insertSchedulerPointLog
	 * @author : syt
	 * @param adminSchedulerDataVO
	 * @return
	 * @returnValue : @param adminSchedulerDataVO
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:23:23
	 * @description : [스케줄러] 입금만료 취소 포인트 반환
	 */
	@Override
	public int insertSchedulerPointLog(AdminSchedulerPaymentDataVO schedulerPaymentData) throws Exception {
		return ses.insert(ns + ".insertSchedulerPointLog", schedulerPaymentData);
	}
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
