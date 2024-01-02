package com.brickfarm.dao.payment;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserPaymentDTO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;

public interface PaymentDAO {

	int insertPayment(UserPaymentDTO payment) throws Exception;
	int selectPaymentByImpUid(String imp_uid) throws Exception; 
	// 결제테이블 취소반품금액 업데이트
	int updatePaymentOnCancel(AdminCancelDataDTO AdminCancelData) throws Exception;
	// 결제테이블 입금자명 업데이트
	int updatePaymentOnDepositorName(List<AdminDetailedOrderDTO> detailedOrderList, Timestamp now) throws Exception;
	// 입금기한만료에 따른 취소데이터 업데이트
	int updateSchedulerPayment(List<AdminSchedulerPaymentDataVO> schedulerPaymentDataList) throws Exception;
	// 중복제거 리스트 얻어오기
	// ---------------- 박상진 ---------------------------
	// 해당 주문의 총 결제금액(현금 + 카드) 조회
	int selectActualPaymentAmount(Map<String, Object> confirmOrderInfo) throws Exception;
	// ---------------- 박상진 ---------------------------
	List<AdminSchedulerPaymentDataVO> getSchedulerPaymentData(List<AdminSchedulerDataVO> schedulerDataList) throws Exception;
	// 0원을 뺀 UID 얻기
	List<String> selectPaymentMoney(List<String> compareMerchantUidList) throws Exception;

}
