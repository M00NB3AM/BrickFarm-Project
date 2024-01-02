package com.brickfarm.dao.ordersheet;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
import com.brickfarm.vo.admin.kmh.AdminOrderMemberVO;
import com.brickfarm.vo.admin.syt.AdminDeliveryVO;
import com.brickfarm.vo.admin.syt.AdminOrderVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;

@Repository
public class OrderSheetDAOImpl implements OrderSheetDAO {

	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.OrderSheetMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	
	
	@Override
	public List<AdminOrderMemberVO> selectOrderMember(AdminOrderMemberDTO tmpMember) throws Exception {

		return ses.selectList(ns + ".findOrderMember", tmpMember);
	}
	

	@Override
	public List<AdminOrderMemberVO> selectBestCustomer() throws Exception {
		return ses.selectList(ns + ".findBestCustomer");
	}
	
	@Override
	public int updateWithdrawMember(AdminWithdrawMemberDTO tmpdelMember) throws Exception {
		return ses.update(ns + ".updateWithdrawMemberNo", tmpdelMember);
	}
	
	@Override
	public List<AdminMemberOrderVO> selectMemberOrderList(int member_no, PaginationInfo pi) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_no", member_no);
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowOfNums", pi.getRowOfNums());
		
		return ses.selectList(ns + ".findMemberOrderList", params);
	}
	
	@Override
	public int selectOrderListCount(int member_no) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_no", member_no);
		return ses.selectOne(ns + ".findOrderListCount", params);
	}

	// ==================================================================================================================================================


	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	
	// 탈퇴한 회원의 탈퇴번호를 insert 해주기
	@Override
	public void insertWithdrawMember(AdminWithdrawMemberDTO withdrawMember) {
		 
		ses.update(ns + ".updatewithdrawmember", withdrawMember);
			
	}
	
	
	
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	@Override
	public UserOrderInfoDTO selectOrderInfo(int member_no, String merchant_uid) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("member_no", member_no);
		param.put("merchant_uid", merchant_uid);
		return ses.selectOne(ns + ".selectOrderInfo", param);
	}
	
	
	@Override
	public int updateOrdersheetTotalCompleteDate(Map<String, Object> confirmOrderInfo) throws Exception {
		String merchant_uid = (String) confirmOrderInfo.get("merchantUid");
		return ses.update(ns + ".updateOrdersheetTotalCompleteDate",merchant_uid );
	}
	
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : compareCompleteState
	 * @author : syt
	 * @param pkNumber
	 * @return
	 * @throws Exception
	 * @returnValue : @param pkNumber
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 9:05:45
	 * @description : [교환] 후 검증
	 */
	@Override
	public List<String> compareCompleteStateExchange(List<Integer> pkNumber) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pkNumber", pkNumber);
		return ses.selectList(ns + ".compareCompleteStateExchange", param);
	}
	
	/**
	 * @methodName : compareCompleteStateCancelReturn
	 * @author : syt
	 * @param pkNumber
	 * @return
	 * @throws Exception
	 * @returnValue : @param pkNumber
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 9:05:45
	 * @description : [반품,취소] 후 검증
	 */
	@Override
	public List<String> compareCompleteStateCancelReturn(List<Integer> pkNumber) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pkNumber", pkNumber);
		return ses.selectList(ns + ".compareCompleteStateCancelReturn", param);
	}
	
	/**
	 * @methodName : insertOrderSheet
	 * @author : syt
	 * @param ordersheet
	 * @return
	 * @returnValue : @param ordersheet
	 * @returnValue : @return
	 * @date : 2023. 11. 5. 오후 3:17:30
	 * @description : 
	 */
	@Override
	public int insertOrderSheet(UserOrdersheetDTO ordersheet) throws Exception {
		return ses.insert(ns+ ".insertOrderSheet", ordersheet);
	}
	
	/**
	 * @methodName : updateOrderSheetOnState
	 * @author : syt
	 * @param compareMerchantUidList
	 * @param date
	 * @return
	 * @returnValue : @param compareMerchantUidList
	 * @returnValue : @param date
	 * @returnValue : @return
	 * @date : 2023. 11. 5. 오후 3:17:36
	 * @description : 총상태 완료로 업데이트
	 */
	@Override
	public int updateOrderSheetOnState(List<String> compareMerchantUidList, Timestamp now) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("merchantUidList", compareMerchantUidList);
		param.put("now", now);
		return ses.update(ns + ".updateOrderSheetOnState", param);
	}
	
	/**
	 * @methodName : selectTotalDataCount
	 * @author : syt
	 * @param searchDto
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 7:33:59
	 * @description : [주문관리] 페이지네이션을 위한 총 데이터 갯수 얻는 메서드
	 */
	@Override
	public int selectTotalDataCount(AdminSearchDTO searchDto) throws Exception {
		return ses.selectOne(ns + ".selectTotalDataCount", searchDto);
	}
	
	/**
	 * @methodName : selectOrderList
	 * @author : syt
	 * @param searchDto
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchDto
	 * @returnValue : @param pagination
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 7:34:02
	 * @description : [주문관리] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public List<AdminOrderVO> selectOrderList(AdminSearchDTO searchDto, Pagination pagination)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("searchDto", searchDto);
		param.put("pagination", pagination);
		return ses.selectList(ns + ".selectOrderList", param);
	}
	
	/**
	 * @methodName : compareCompleteStateByUid
	 * @author : syt
	 * @param detailedOrderList
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 5:06:02
	 * @description : [주문관리] 구매확정 후 검증
	 */
	@Override
	public List<String> compareCompleteStateByUid(List<String> merchantUidList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("merchantUidList", merchantUidList);
		return ses.selectList(ns + ".compareCompleteStateByUid", param);
	}
	
	/**
	 * @methodName : updateOrdersheetOnDeliveryState
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오전 11:38:07
	 * @description : [주문관리] 배송상태(배송준비중) 업데이트(결제완료되면 자동변경)
	 */
	@Override
	public int updateOrdersheetOnDeliveryPrepare(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		param.put("state", state);
		return ses.update(ns + ".updateOrdersheetOnDeliveryPrepare", param);
	}

	/**
	 * @methodName : selectTotalDeliveryDataCount
	 * @author : syt
	 * @param searchDto
	 * @return
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 4:10:47
	 * @description : [배송관리] 총데이터 수
	 */
	@Override
	public int selectTotalDeliveryDataCount(AdminSearchDTO searchDto) throws Exception {
		return ses.selectOne(ns + ".selectTotalDeliveryDataCount", searchDto);
	}

	/**
	 * @methodName : selectOrderDeliveryList
	 * @author : syt
	 * @param searchDto
	 * @param pagination
	 * @return
	 * @returnValue : @param searchDto
	 * @returnValue : @param pagination
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 4:10:49
	 * @description : [배송관리] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public List<AdminDeliveryVO> selectDeliveryList(AdminSearchDTO searchDto, Pagination pagination) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("searchDto", searchDto);
		param.put("pagination", pagination);
		return ses.selectList(ns + ".selectDeliveryList", param);
	}

	/**
	 * @methodName : updateOrderSheetOnDeliveryState
	 * @author : syt
	 * @param deliveryList
	 * @param state
	 * @return
	 * @returnValue : @param deliveryList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 5:10:13
	 * @description : [배송관리] 배송상태변경(결제완료되면 자동변경) , [배송관리] 배송상태변경(버튼눌러서)
	 */
	@Override
	public int updateOrderSheetOnDeliveryState(List<AdminDeliveryDTO> deliveryList, String state) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("deliveryList", deliveryList);
		param.put("state", state);
		return ses.update(ns + ".updateOrderSheetOnDeliveryState", param);
	}

	/**
	 * @methodName : updateOrderSheetOnDeliveryWait
	 * @author : syt
	 * @param deliveryList
	 * @param state
	 * @return
	 * @returnValue : @param deliveryList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 5:10:16
	 * @description : [배송관리] 상태변경(배송대기중)
	 */
	@Override
	public int updateOrderSheetOnDeliveryWait(List<AdminDeliveryDTO> deliveryList, String state, Timestamp now) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("deliveryList", deliveryList);
		param.put("state", state);
		param.put("now", now);
		return ses.update(ns + ".updateOrderSheetOnDeliveryWait", param);
	}
	
	/**
	 * @methodName : SchedulerDelivery
	 * @author : syt
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 11. 16. 오전 11:14:12
	 * @description : [스케줄러] 배송상태(배송중) 변경
	 */
	@Override
	public int schedulerDelivery() throws Exception {
		return ses.update(ns + ".schedulerDelivery");
	}

	/**
	 * @methodName : SchedulerDeliveryCompleted
	 * @author : syt
	 * @param today
	 * @return
	 * @returnValue : @param today
	 * @returnValue : @return
	 * @date : 2023. 11. 16. 오전 11:14:26
	 * @description : [스케줄러] 배송상태(배송완료) 변경
	 */
	@Override
	public int schedulerDeliveryCompleted() throws Exception {
		return ses.update(ns + ".schedulerDeliveryCompleted");
	}

	/**
	 * @methodName : selectSchedulerDepositData
	 * @author : syt
	 * @param ago4Day
	 * @return
	 * @returnValue : @param ago4Day
	 * @returnValue : @return
	 * @date : 2023. 11. 20. 오후 7:46:48
	 * @description : [스케줄러] 입금기한 만료시 수정해야하는 데이터 얻기
	 */
	@Override
	public List<AdminSchedulerDataVO> selectSchedulerDepositData() throws Exception {
		return ses.selectList(ns + ".selectSchedulerDepositData");
	}


	/**
	 * @methodName : updateSchedulerOrderSheet
	 * @author : syt
	 * @param schedulerDataList
	 * @param now
	 * @return
	 * @returnValue : @param schedulerDataList
	 * @returnValue : @param now
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:12:27
	 * @description : [스케줄러] 최종입금상태 업데이트
	 */
	@Override
	public int updateSchedulerOrderSheet(List<AdminSchedulerPaymentDataVO> schedulerPaymentDataList, Timestamp now) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("schedulerPaymentDataList", schedulerPaymentDataList);
		param.put("now", now);
		return ses.update(ns + ".updateSchedulerOrderSheet", param);
	}

	// [대시보드] 오늘 총 주문건
	@Override
	public int selectDashboardCount(LocalDate now) throws Exception {
		return ses.selectOne(ns + ".selectDashboardCount", now);
	}

	// [대시보드] 오늘 총 매출
	@Override
	public String selectDashboardSales(LocalDate now) throws Exception {
		return ses.selectOne(ns + ".selectDashboardSales", now);
	}

	// [대시보드] 오늘 총 배송준비중
	@Override
	public int selectDashboardDeliveryCount() throws Exception {
		return ses.selectOne(ns + ".selectDashboardDeliveryCount");
	}

	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
