package com.brickfarm.service.admin.order;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.syt.AdminExchangeDTO;
import com.brickfarm.dto.admin.syt.AdminCancelReturnDTO;
import com.brickfarm.dto.admin.syt.AdminDeliveryDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;

public interface AdminOrderService {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	// 교환
	Map<String, Object> searchOrderExchange(int pageNo, AdminSearchDTO searchDto) throws Exception;
	boolean changeStateExchange(List<AdminExchangeDTO> exchangeList) throws Exception;
	boolean changeStateExchangeByComplete(List<AdminExchangeDTO> exchangeList, List<Integer> exchange_no) throws Exception;
	//반품
	Map<String, Object> searchOrderReturn(int pageNo, AdminSearchDTO searchDto) throws Exception;
	boolean changeStateReturn(List<AdminCancelReturnDTO> returnList) throws Exception;
	Map<String, Object> changeStateReturnByComplete(List<Integer> returnNoList) throws Exception;
	// 취소
	Map<String, Object> searchOrderCancel(int pageNo, AdminSearchDTO searchDto) throws Exception;
	boolean changeStateCancel(List<AdminCancelReturnDTO> returnList) throws Exception;
	Map<String, Object> changeStateCancelByComplete(List<Integer> returnNoList) throws Exception;
	
	// 주문관리 데이터검색
	Map<String, Object> searchOrder(int pageNo, AdminSearchDTO searchDto) throws Exception;
	// 주문관리 상세정보 얻기
	Map<String, Object> getOrderDetail(int detailed_order_no) throws Exception;
	// 주문관리 상태 변경 메서드 4종
	boolean changeStateManageOrderPayment(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception;
	boolean changeStateManageOrderExchange(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception;
	boolean changeStateManageOrderCancelReturn(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception;
	boolean changeStateManageOrderComplete(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception;
	
	// 배송관리 데이터 검색
	Map<String, Object> searchOrderDelivery(int pageNo, AdminSearchDTO searchDto) throws Exception;
	// 배송관리 상태 변경
	boolean changeDeliveryState(List<AdminDeliveryDTO> deliveryList, String state) throws Exception;
	// 배송관리 상세정보 얻기
	Map<String, Object> getOrderDeliveryDetail(String merchant_uid) throws Exception;
	
	// 입금기한만료 스케줄러
	void schedulerDepositDeadlineExpired() throws Exception;
	// 대시보드 데이터 얻기
	Map<String, Object> getDashboardData() throws Exception;
	
	// 스케줄러 구매확정 11일 후
	void schedulerStateManageOrderComplete() throws Exception;
	// 스케줄러 배송상태 변경 (배송중)
	void schedulerDelivery() throws Exception;
	// 스케줄러 배송상태 변경 (배송완료)
	void schedulerDeliveryCompleted() throws Exception;
	// ==================================================================================================================================================


	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
