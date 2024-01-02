package com.brickfarm.dao.payment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserPaymentDTO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;

@Repository
public class PaymentDAOImpl implements PaymentDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.PaymentMapper";

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	@Override
	public int selectActualPaymentAmount(Map<String, Object> confirmOrderInfo) throws Exception {
		return ses.selectOne(ns + ".getActualPaymentAmount", confirmOrderInfo);
	}
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	@Override
	public int insertPayment(UserPaymentDTO payment) throws Exception {
		return ses.insert(ns + ".insertPayment", payment);	
	}
	
	@Override
	public int selectPaymentByImpUid(String imp_uid) throws Exception {
		return ses.selectOne(ns + ".selectPaymentByImpUid", imp_uid);	
	}
	
	/**
	 * @methodName : updatePaymentOnCencel
	 * @author : syt
	 * @param cancel_request_amount
	 * @return
	 * @returnValue : @param cancel_request_amount
	 * @returnValue : @return
	 * @date : 2023. 11. 5. 오후 4:31:53
	 * @description : [결제] 취소/반품 금액 업데이트
	 */
	@Override
	public int updatePaymentOnCancel(AdminCancelDataDTO AdminCancelData) throws Exception {
		return ses.update(ns + ".updatePaymentOnCancel", AdminCancelData);
	}
	
	/**
	 * @methodName : updatePaymentOnDepositorName
	 * @author : syt
	 * @param detailedOrderList
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 1:56:37
	 * @description : [주문관리] 입금자명, 입금시간 업데이트
	 */
	@Override
	public int updatePaymentOnDepositorName(List<AdminDetailedOrderDTO> detailedOrderList, Timestamp now) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		param.put("now", now);
		return ses.update(ns +".updatePaymentOnDepositorName", param);
	}

	/**
	 * @methodName : updateSchedulerPayment
	 * @author : syt
	 * @param schedulerDataList
	 * @return
	 * @returnValue : @param schedulerDataList
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 2:22:33
	 * @description : 입금기한만료에 따른 취소데이터 업데이트
	 */
	@Override
	public int updateSchedulerPayment(List<AdminSchedulerPaymentDataVO> schedulerPaymentDataList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schedulerPaymentDataList", schedulerPaymentDataList);
		return ses.update(ns + ".updateSchedulerPayment", param);
	}

	/**
	 * @methodName : getSchedulerPaymentData
	 * @author : syt
	 * @param schedulerDataList
	 * @return
	 * @returnValue : @param schedulerDataList
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 7:04:29
	 * @description : [스케줄러] 중복제거 리스트 얻어오기
	 */
	@Override
	public List<AdminSchedulerPaymentDataVO> getSchedulerPaymentData(List<AdminSchedulerDataVO> schedulerDataList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schedulerDataList", schedulerDataList);
		return ses.selectList(ns + ".getSchedulerPaymentData", param);
	}
	
	// 검증을위한 카운트
	@Override
	public List<String> selectPaymentMoney(List<String> compareMerchantUidList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("compareMerchantUidList", compareMerchantUidList);
		return ses.selectList(ns + ".selectPaymentMoney", param);
	}
	
	
	// ==================================================================================================================================================


	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
