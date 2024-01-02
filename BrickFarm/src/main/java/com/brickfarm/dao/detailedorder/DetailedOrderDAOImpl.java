package com.brickfarm.dao.detailedorder;

import java.util.List;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.user.psj.UserOrderDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.etc.sjy.AdminProductRank;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderInfoVO;
import com.brickfarm.vo.admin.syt.AdminDeliveryDetailVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;

@Repository
public class DetailedOrderDAOImpl implements DetailedOrderDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.DetailedOrderMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminProductRank> selectFiveTopList() {
		return ses.selectList(ns + ".selectFiveTopList");
	}

	@Override
	public int selectSaledCount() {
		return ses.selectOne(ns + ".selectSaledCount");
	}

	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================

	/**
	 * @methodName : selectOrderDetailByMerchantUID
	 * @author : kmh
	 * @param merchant
	 * @return
	 * @throws Exception
	 * @returnValue : @param merchant
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 23. 오후 5:44:25
	 * @description : 회원 주문번호로 주문 상세 내역 가져오기
	 */
	@Override
	public AdminDetailOrderInfoVO selectOrderDetailByMerchantUID(String merchant) throws Exception {

		return ses.selectOne(ns + ".findOrderDetailByMerchantUID", merchant);
	}

	/**
	 * @methodName : selectOrderDetailByMerchantUIDInWithdrawMember
	 * @author : kmh
	 * @param merchant
	 * @return
	 * @throws Exception
	 * @returnValue : @param merchant
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 24. 오후 8:52:49
	 * @description : 탈퇴 회원 주문번호로 주문 상세 내역 가져오기
	 */
	@Override
	public AdminDetailOrderInfoVO selectOrderDetailByMerchantUIDInWithdrawMember(String merchant) throws Exception {
		return ses.selectOne(ns + ".findOrderDetailByMerchantUIDInWithdrawMember", merchant);
	}

	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	@Override
	public List<UserOrderDTO> selectOrder(int memberNo, String merchant_uid) throws Exception {
		return ses.selectList(ns + ".selectOrder", merchant_uid);
	}

	@Override
	public int updateDetailedOrderPaymentStateByOrderConfirm(Map<String, Object> confirmOrderInfo) throws Exception {
		int detailedOrderNo = (int) confirmOrderInfo.get("detailedOrderNo");
		return ses.update(ns + ".updateDetailedOrderPaymentStateByOrderConfirm", detailedOrderNo);
	}
	@Override
	public List<Integer> selectDetailedOrderNoList(Map<String, Object> confirmOrderInfo) throws Exception {
		String merchant_uid = (String) confirmOrderInfo.get("merchantUid");
		return ses.selectList(ns +".selectDetailedOrderNoList", merchant_uid);
	}
	
	@Override
	public int selectCompleteDetailedOrderCount(Map<String, Object> confirmOrderInfo) throws Exception {
		String merchant_uid = (String) confirmOrderInfo.get("merchantUid");
		return ses.selectOne(ns+".selectCompleteDetailedOrderCount", merchant_uid);
	}
	@Override
	public int selectCompleteCancelDetailedOrderCount(List<Integer> detailedOrderNoList) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("detailedOrderNoList", detailedOrderNoList);
		return ses.selectOne(ns+".selectCompleteCancelDetailedOrderCount" , params);
	}

	@Override
	public int selectCompleteExchangeDetailedOrderCount(List<Integer> detailedOrderNoList) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("detailedOrderNoList", detailedOrderNoList);
		return ses.selectOne(ns+".selectCompleteExchangeDetailedOrderCount" , params);
	}
	


	@Override
	public int updateDetailedOrderPaymentStateByWithDrawal(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception {
		return ses.update(ns + ".updateDetailedOrderPaymentStateByWithDrawal", userWithDrawalConfirmDTO);
	}

	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	@Override
	public int insertDetailedOrder(List<UserDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		return ses.insert(ns + ".insertDetailedOrder", param);
	}

	/**
	 * @methodName : updateDetailedOrderOnPayment
	 * @author : syt
	 * @param detailedOrderList
	 * @returnValue : @param detailedOrderList
	 * @date : 2023. 11. 13. 오후 1:09:41
	 * @description : [주문관리] 주문서상세 결제상태 변경(취소,교환,반품,구매확정)
	 */
	@Override
	public int updateDetailedOrderOnPayment(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		param.put("state", state);
		return ses.update(ns + ".updateDetailedOrderOnPayment", param);
	}

	/**
	 * @methodName : updateDetailedOrderCompleteOnPayment
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @param date
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @param date
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 8:02:40
	 * @description : [주문관리] 주문서상세 결제상태 변경(구매확정)
	 */
	@Override
	public int updateDetailedOrderCompleteOnPayment(List<AdminDetailedOrderDTO> detailedOrderList, String state, Timestamp now)  throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		param.put("state", state);
		param.put("now", now);
		return ses.update(ns + ".updateDetailedOrderCompleteOnPayment", param);
	}

	/**
	 * @methodName : selectDetailedOrderGetUid
	 * @author : syt
	 * @param detailedOrderList
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 2:43:44
	 * @description : [주문관리] UID 얻어오기 (중복제거)
	 */
	@Override
	public List<String> selectDetailedOrderGetUid(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		return ses.selectList(ns + ".selectDetailedOrderGetUid", param);
	}
	// --------------------------------------------------------------------------------------------------

	/**
	 * @methodName : getValidationPrice
	 * @author : syt
	 * @param validationDataList
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param validationDataList
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 8:46:08
	 * @description : [결제페이지] API 데이터 검증을 위한 값 계산하기
	 */
	@Override
	public int getValidationPrice(List<UserPaymentListDTO> paymentList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("paymentList", paymentList);
		return ses.selectOne(ns + ".getValidationPrice", param);
	}

	/**
	 * @methodName : updateSchedulerDetailedOrderCancel
	 * @author : syt
	 * @param schedulerDataList
	 * @return
	 * @returnValue : @param schedulerDataList
	 * @returnValue : @return
	 * @date : 2023. 11. 20. 오후 8:41:46
	 * @description : [스케줄러] 입금기한 만료로 인해 자동 취소신청
	 */
	@Override
	public int updateSchedulerDetailedOrderCancel(List<AdminSchedulerDataVO> schedulerDataList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schedulerDataList", schedulerDataList);
		return ses.update(ns + ".updateSchedulerDetailedOrderCancel", param);
	}

	/**
	 * @methodName : selectCarryinOutDataList
	 * @author : syt
	 * @param pkNumberList
	 * @param what
	 * @return
	 * @returnValue : @param pkNumberList
	 * @returnValue : @param what
	 * @returnValue : @return
	 * @date : 2023. 11. 22. 오전 10:51:29
	 * @description : 반출을 위한 데이터 얻기
	 */
	@Override
	public List<UserPaymentListDTO> selectCarryinOutDataList(List<Integer> pkNumberList, String what) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pkNumberList", pkNumberList);
		param.put("what", what);
		return ses.selectList(ns + ".selectCarryinOutDataList", param);
	}

	/**
	 * @methodName : selectDeliveryDetailList
	 * @author : syt
	 * @param merchant_uid
	 * @return
	 * @returnValue : @param merchant_uid
	 * @returnValue : @return
	 * @date : 2023. 11. 23. 오전 11:50:13
	 * @description : [배송관리] 디테일정보 얻기
	 */
	@Override
	public List<AdminDeliveryDetailVO> selectDeliveryDetailList(String merchant_uid) throws Exception {
		return ses.selectList(ns + ".selectDeliveryDetailList", merchant_uid);
	}

	/**
	 * @methodName : selectOrderDetailList
	 * @author : syt
	 * @param detailed_order_no
	 * @return
	 * @returnValue : @param detailed_order_no
	 * @returnValue : @return
	 * @date : 2023. 11. 23. 오후 3:23:03
	 * @description : [주문관리] 디테일정보 얻기
	 */
	@Override
	public List<AdminDeliveryDetailVO> selectOrderDetailList(int detailed_order_no) throws Exception {
		return ses.selectList(ns + ".selectOrderDetailList", detailed_order_no);
	}

	/**
	 * @methodName : selectSchedulerStateManageOrderComplete
	 * @author : syt
	 * @param ago11Day
	 * @return
	 * @returnValue : @param ago11Day
	 * @returnValue : @return
	 * @date : 2023. 11. 27. 오후 4:07:09
	 * @description : [스케줄러] 구매확정 리스트 받아오기
	 */
	@Override
	public List<AdminDetailedOrderDTO> selectSchedulerStateManageOrderComplete() throws Exception {
		return ses.selectList(ns + ".selectSchedulerStateManageOrderComplete");
	}

	/**
	 * @methodName : selectDetailedOrderGetCount
	 * @author : syt
	 * @param detailedOrderList
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @return
	 * @date : 2023. 11. 27. 오후 6:25:27
	 * @description : 검증을 위한 갯수 구하기
	 */
	@Override
	public int selectDetailedOrderGetCount(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		return ses.selectOne(ns + ".selectDetailedOrderGetCount", param);
	}

	// [대시보드] 교환/반품/취소 신청상태 갯수
	@Override
	public int selectDashboardStateCount() throws Exception {
		return ses.selectOne(ns + ".selectDashboardStateCount");
	}


	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
