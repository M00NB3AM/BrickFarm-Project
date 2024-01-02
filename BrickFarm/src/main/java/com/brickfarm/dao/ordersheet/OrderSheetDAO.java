package com.brickfarm.dao.ordersheet;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminOrderMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminWithdrawMemberDTO;
import com.brickfarm.dto.admin.syt.AdminDeliveryDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.dto.user.psj.UserOrderInfoDTO;
import com.brickfarm.dto.user.syt.UserOrdersheetDTO;
import com.brickfarm.etc.kmh.PaginationInfo;
import com.brickfarm.etc.syt.Pagination;
import com.brickfarm.vo.admin.kmh.AdminMemberOrderVO;
import com.brickfarm.vo.admin.kmh.AdminMemberVO;
import com.brickfarm.vo.admin.kmh.AdminOrderMemberVO;
import com.brickfarm.vo.admin.syt.AdminCancellationReturnVO;
import com.brickfarm.vo.admin.syt.AdminDeliveryVO;
import com.brickfarm.vo.admin.syt.AdminOrderVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;

public interface OrderSheetDAO {

	// ==[송지영]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[김미형]========================================================================================================================================

		// 주문 이력이 있는 회원 조회
		List<AdminOrderMemberVO> selectOrderMember(AdminOrderMemberDTO tmpMember) throws Exception;

		// 1개월 내 확정 주문 건수 TOP 5 찾기 
		List<AdminOrderMemberVO> selectBestCustomer() throws Exception;
		
		// 주문서에 탈퇴 회원 번호 업데이트
		int updateWithdrawMember(AdminWithdrawMemberDTO tmpdelMember) throws Exception;
		
		// 회원 주문서 조회
		List<AdminMemberOrderVO> selectMemberOrderList(int member_no, PaginationInfo pi) throws Exception;
		
		// 회원 주문 수 조회 ( 페이지네이션 )
		int selectOrderListCount(int member_no) throws Exception;
		
	// ==================================================================================================================================================

	// ==[이경민]========================================================================================================================================

	// ==================================================================================================================================================

	// ==[염세환]========================================================================================================================================
		
		//삭제한 회원의 삭제 회원 넘버 넣어주기
		void insertWithdrawMember(AdminWithdrawMemberDTO withdrawMember);
		

	// ==================================================================================================================================================

	// ==[박상진]========================================================================================================================================
		
		// 로그인한 멤버의 주문 정보 조회
		UserOrderInfoDTO selectOrderInfo(int memberNo, String merchantUid) throws Exception;
		
		// 주문서 총 완료날짜 변경
		int updateOrdersheetTotalCompleteDate(Map<String, Object> confirmOrderInfo) throws Exception;
	// ==================================================================================================================================================

	// ==[송영태]========================================================================================================================================
		int insertOrderSheet(UserOrdersheetDTO ordersheet) throws Exception;
		// 주문서 최종상태 변경
		int updateOrderSheetOnState(List<String> compareMerchantUidList, Timestamp now) throws Exception;
		//[교환] 검증(최종상태 변경을 위한)
		List<String> compareCompleteStateExchange(List<Integer> pkNumber) throws Exception;
		//[반품,취소] 검증(최종상태 변경을 위한)
		List<String> compareCompleteStateCancelReturn(List<Integer> pkNumber) throws Exception;

		//[주문관리] 총 데이터수
		int selectTotalDataCount(AdminSearchDTO searchDto) throws Exception;
		//[주문 관리] 데이터
		List<AdminOrderVO> selectOrderList(AdminSearchDTO searchDto, Pagination pagination) throws Exception;
		// 확정상태 검증비교 후 UID반환
		List<String> compareCompleteStateByUid(List<String> merchantUidList) throws Exception;
		// 주문서테이블 배송상태 업데이트
		int updateOrdersheetOnDeliveryPrepare(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception;
		
		//[배송관리] 총 데이터수
		int selectTotalDeliveryDataCount(AdminSearchDTO searchDto) throws Exception;
		//[배송관리] 데이터
		List<AdminDeliveryVO> selectDeliveryList(AdminSearchDTO searchDto, Pagination pagination) throws Exception;
		//[배송관리] 상태변경
		int updateOrderSheetOnDeliveryState(List<AdminDeliveryDTO> deliveryList, String state) throws Exception;
		//[배송관리] 상태변경(배송대기중)
		int updateOrderSheetOnDeliveryWait(List<AdminDeliveryDTO> deliveryList, String state, Timestamp now) throws Exception;
		// [스케줄러]
		int schedulerDelivery() throws Exception;
		int schedulerDeliveryCompleted() throws Exception;
		// [스케줄러] 입금기한 만료시 수정해야하는 데이터 얻기
		List<AdminSchedulerDataVO> selectSchedulerDepositData() throws Exception;
		// [스케줄러] 주문서 업데이트
		int updateSchedulerOrderSheet(List<AdminSchedulerPaymentDataVO> schedulerPaymentDataList, Timestamp now) throws Exception;
		// [대시보드] 오늘 총 주문건
		int selectDashboardCount(LocalDate now) throws Exception;
		// [대시보드] 오늘 총 매출
		String selectDashboardSales(LocalDate now) throws Exception;
		// [대시보드] 오늘 배송준비중 갯수
		int selectDashboardDeliveryCount() throws Exception;
	// ==================================================================================================================================================


	// ==[김용진]========================================================================================================================================

	// ==================================================================================================================================================

}
