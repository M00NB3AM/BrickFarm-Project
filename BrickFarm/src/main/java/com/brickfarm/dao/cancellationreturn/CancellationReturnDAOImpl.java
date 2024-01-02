package com.brickfarm.dao.cancellationreturn;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.dto.admin.syt.AdminCancelReturnDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.dto.user.psj.UserCancelAndReturnOrderDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.dto.user.syt.CancelDataDTO;
import com.brickfarm.etc.syt.Pagination;
import com.brickfarm.vo.admin.syt.AdminCancellationReturnVO;
import com.brickfarm.vo.admin.syt.AdminExchangeVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;

@Repository
public class CancellationReturnDAOImpl implements CancellationReturnDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.CancellationReturnMapper";

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
	public List<UserCancelAndReturnOrderDTO> selectMemberCancelOrderInfo(int member_no, String merchant_uid) throws Exception {
		return ses.selectList(ns + ".selectCancelAndReturnOrderInfo", merchant_uid);
	}
	@Override
	public int insertCancelAndReturnConfirm(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception {
		
		return ses.insert(ns + ".insertCancelAndReturnApply", userWithDrawalConfirmDTO);
	}
	
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : getCancelDataList
	 * @author : syt
	 * @param returnList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 4. 오후 6:01:35
	 * @description : [취소,반품] 결제취소를 위한 데이터 수집
	 */
	@Override
	public AdminCancelDataDTO getCancelData(int pkNumber) throws Exception {
		return ses.selectOne(ns + ".getCancelData", pkNumber);
	}
	
	/**
	 * @methodName : selectTotalDataCount
	 * @author : syt
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 11. 2. 오후 9:29:45
	 * @description : [취소,반품] 페이지네이션을 위한 총 데이터 갯수 얻는 메서드
	 */
	@Override
	public int selectTotalDataCount(AdminSearchDTO searchDto, String what) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("searchDto", searchDto);
		param.put("what", what);
		return ses.selectOne(ns + ".selectTotalDataCount", param);
	}

	/**
	 * @methodName : selectReturnList
	 * @author : syt
	 * @param searchDto
	 * @param pagination
	 * @return
	 * @returnValue : @param searchDto
	 * @returnValue : @param pagination
	 * @returnValue : @return
	 * @date : 2023. 11. 2. 오후 9:30:36
	 * @description : [취소,반품] 데이터 얻는 메서드
	 */
	@Override
	public List<AdminCancellationReturnVO> selectCancelReturnList(AdminSearchDTO searchDto, Pagination pagination, String what) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("searchDto", searchDto);
		param.put("pagination", pagination);
		param.put("what", what);
		return ses.selectList(ns + ".selectCancelReturnList", param);
	}
	
	/**
	 * @methodName : updateReturnByState
	 * @author : syt
	 * @param returnList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 4. 오후 5:31:55
	 * @description : [취소,반품] 상태(확인) 변경
	 */
	@Override
	public int updateCancelReturnByStateCheck(List<AdminCancelReturnDTO> cancelReturnList, String what) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("cancelReturnList", cancelReturnList);
		param.put("what", what);
		return ses.update(ns + ".updateCancelReturnByStateCheck", param);
	}
	
	/**
	 * @methodName : updateReturnByState
	 * @author : syt
	 * @param returnList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 4. 오후 5:31:55
	 * @description : [취소,반품] 상태(완료) 변경
	 */
	@Override
	public int updateCancelReturnByStateComplete(List<AdminCancelReturnDTO> cancelReturnList, String what) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("cancelReturnList", cancelReturnList);
		param.put("what", what);
		return ses.update(ns + ".updateCancelReturnByStateComplete", param);
	}

	/**
	 * @methodName : deleteCancelReturnByDetailedOrderNo
	 * @author : syt
	 * @param detailedOrderList
	 * @returnValue : @param detailedOrderList
	 * @date : 2023. 11. 13. 오후 3:50:44
	 * @description : [주문관리] 신청 취소시 로직(delete)
	 */
	@Override
	public void deleteCancelReturnByDetailedOrderNo(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("detailedOrderList", detailedOrderList);
		ses.delete(ns + ".deleteCancelReturnByDetailedOrderNo", param);
	}

	/**
	 * @methodName : insertCancelReturnList
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 4:28:45
	 * @description : [주문관리] 취소,반품 신청시 로직
	 */
	@Override
	public int insertCancelReturnList(List<AdminDetailedOrderDTO> detailedOrderList, String what) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("detailedOrderList", detailedOrderList);
		param.put("what", what);
		return ses.insert(ns + ".insertCancelReturnList", param);
	}

	/**
	 * @methodName : insertSchedulerCancel
	 * @author : syt
	 * @param schedulerDataList
	 * @param today
	 * @return
	 * @returnValue : @param schedulerDataList
	 * @returnValue : @param today
	 * @returnValue : @return
	 * @date : 2023. 11. 20. 오후 8:30:03
	 * @description : [스케줄러] 입금기한 지난사람 취소처리
	 */
	@Override
	public int insertSchedulerCancel(List<AdminSchedulerDataVO> schedulerDataList, Timestamp now) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("schedulerDataList", schedulerDataList);
		param.put("now", now);
		return ses.insert(ns + ".insertSchedulerCancel", param);
	}

	/**
	 * @methodName : deleteSchedulerCancel
	 * @author : syt
	 * @param schedulerDataList
	 * @return
	 * @returnValue : @param schedulerDataList
	 * @returnValue : @return
	 * @date : 2023. 11. 27. 오후 3:50:22
	 * @description : [스케줄러] 다시 인설트를 위해 지우기
	 */
	@Override
	public int deleteSchedulerCancel(List<AdminSchedulerDataVO> schedulerDataList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("schedulerDataList", schedulerDataList);
		return ses.delete(ns + ".deleteSchedulerCancel", param);
	}

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
