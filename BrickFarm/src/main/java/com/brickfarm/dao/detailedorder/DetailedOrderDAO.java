package com.brickfarm.dao.detailedorder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.user.psj.UserOrderDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.etc.sjy.AdminProductRank;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderInfoVO;
import com.brickfarm.vo.admin.syt.AdminDeliveryDetailVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;

public interface DetailedOrderDAO {

	// ==[송지영]==========================================================================================================================================
	List<AdminProductRank> selectFiveTopList() throws Exception;
	
	int selectSaledCount() throws Exception;
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	
		// 회원 상세 주문 내역 가져오기
		AdminDetailOrderInfoVO selectOrderDetailByMerchantUID(String merchant) throws Exception;
		
		// 탈퇴 회원 상세 주문 내역 가져오기
		AdminDetailOrderInfoVO selectOrderDetailByMerchantUIDInWithdrawMember(String merchant) throws Exception;
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// 상세 주문 정보 조회
	List<UserOrderDTO> selectOrder(int memberNo, String merchantUid) throws Exception;
	// 상세 주문의 결제 상태 변경(주문 확정
	int updateDetailedOrderPaymentStateByOrderConfirm(Map<String, Object> confirmOrderInfo) throws Exception;
	
	List<Integer> selectDetailedOrderNoList(Map<String, Object> confirmOrderInfo) throws Exception;
	
	int selectCompleteDetailedOrderCount(Map<String, Object> confirmOrderInfo) throws Exception;
	
	int selectCompleteCancelDetailedOrderCount(List<Integer> detailedOrderNoList) throws Exception;
	
	int selectCompleteExchangeDetailedOrderCount(List<Integer> detailedOrderNoList) throws Exception;
	
	// 교환, 신청, 반품 신청 시 해당 상세주문의 상태 변경
	int updateDetailedOrderPaymentStateByWithDrawal(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception;
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	int insertDetailedOrder(List<UserDetailedOrderDTO> detailedOrderList) throws Exception;
	// 결제상태 변경(통합)
	int updateDetailedOrderOnPayment(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception;
	// 결제상태 변경(구매확정)
	int updateDetailedOrderCompleteOnPayment(List<AdminDetailedOrderDTO> detailedOrderList, String state, Timestamp now) throws Exception;
	// UID 얻어오기(중복은 제거된)
	List<String> selectDetailedOrderGetUid(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception;
	// 검증가격 구해오기
	int getValidationPrice(List<UserPaymentListDTO> paymentList) throws Exception;
	// 입금기한 만료로 인해 자동 취소신청
	int updateSchedulerDetailedOrderCancel(List<AdminSchedulerDataVO> schedulerDataList) throws Exception;
	// 반출을 위한 데이터 얻기
	List<UserPaymentListDTO> selectCarryinOutDataList(List<Integer> pkNumberList, String what) throws Exception;
	// [배송관리] 디테일정보 얻기
	List<AdminDeliveryDetailVO> selectDeliveryDetailList(String merchant_uid) throws Exception;
	// [주문관리] 디테일 정보 얻기
	List<AdminDeliveryDetailVO> selectOrderDetailList(int detailed_order_no) throws Exception;
	// [스케줄러] 구매확정으로 변해야하는애들 모음
	List<AdminDetailedOrderDTO> selectSchedulerStateManageOrderComplete() throws Exception;
	// 검증을 위한 갯수 구해오기
	int selectDetailedOrderGetCount(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception;
	// [대시보드] 취소/교환/반품 신청 상태 갯수
	int selectDashboardStateCount() throws Exception;
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
