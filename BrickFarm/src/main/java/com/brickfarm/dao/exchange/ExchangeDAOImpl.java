package com.brickfarm.dao.exchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminExchangeDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.dto.user.psj.UserExchangeOrderDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.etc.syt.Pagination;
import com.brickfarm.vo.admin.syt.AdminExchangeVO;

@Repository
public class ExchangeDAOImpl implements ExchangeDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.ExchangeMapper";

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
	public List<UserExchangeOrderDTO> selectMemberExchangeOrderInfo(int member_no, String merchant_uid) throws Exception {
		return ses.selectList(ns + ".selectExchangeOrderInfo", merchant_uid);
	}
	@Override
	public int insertExchangeConfirm(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception {
		return ses.insert(ns + ".insertExchangeApply", userWithDrawalConfirmDTO);
	}
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : selectTotalDataCount
	 * @author : syt
	 * @param searchDto
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 27. 오후 7:34:43
	 * @description : [교환] 페이지네이션을 위한 총 데이터 갯수 얻는 메서드
	 */
	@Override
	public int selectTotalDataCount(AdminSearchDTO searchDto) throws Exception {
		return ses.selectOne(ns + ".selectTotalDataCount", searchDto);
	}
	
	/**
	 * @methodName : selectExchangeList
	 * @author : syt
	 * @param searchDto
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchDto
	 * @returnValue : @param pagination
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 27. 오후 7:35:48
	 * @description : [교환] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public List<AdminExchangeVO> selectExchangeList(AdminSearchDTO searchDto, Pagination pagination) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("searchDto", searchDto);
		param.put("pagination", pagination);
		return ses.selectList(ns + ".selectExchangeList", param);
	}

	/**
	 * @methodName : updateExchangeByStateCheck
	 * @author : syt
	 * @param exchangeList
	 * @return
	 * @throws Exception
	 * @returnValue : @param exchangeList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 7:36:03
	 * @description : [교환] 확인 상태 변경
	 */
	@Override
	public int updateExchangeByStateCheck(List<AdminExchangeDTO> exchangeList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("exchangeList", exchangeList);
		return ses.update(ns + ".updateExchangeByStateCheck", param);
	}
	/**
	 * @methodName : updateExchangeByStateProcess
	 * @author : syt
	 * @param exchangeList
	 * @return
	 * @throws Exception
	 * @returnValue : @param exchangeList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 7:36:03
	 * @description : [교환] 진행중 상태 변경
	 */
	@Override
	public int updateExchangeByStateProcess(List<AdminExchangeDTO> exchangeList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("exchangeList", exchangeList);
		return ses.update(ns + ".updateExchangeByStateProcess", param);
	}
	/**
	 * @methodName : updateExchangeByStateComplete
	 * @author : syt
	 * @param exchangeList
	 * @return
	 * @throws Exception
	 * @returnValue : @param exchangeList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 7:36:03
	 * @description : [교환] 완료 상태 변경
	 */
	@Override
	public int updateExchangeByStateComplete(List<AdminExchangeDTO> exchangeList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("exchangeList", exchangeList);
		return ses.update(ns +".updateExchangeByStateComplete", param);
	}

	/**
	 * @methodName : deleteExchangeByDetailedOrderNo
	 * @author : syt
	 * @param detailedOrderList
	 * @returnValue : @param detailedOrderList
	 * @date : 2023. 11. 13. 오후 3:51:17
	 * @description : [주문관리] 신청 취소시 로직(delete)
	 */
	@Override
	public void deleteExchangeByDetailedOrderNo(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("detailedOrderList", detailedOrderList);
		ses.delete(ns + ".deleteExchangeByDetailedOrderNo", param);
	}

	/**
	 * @methodName : insertExchangeList
	 * @author : syt
	 * @param detailedOrderList
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 4:03:37
	 * @description : [주문관리] 교환 일괄신청
	 */
	@Override
	public int insertExchangeList(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();  
		param.put("detailedOrderList", detailedOrderList);
		return ses.insert(ns + ".insertExchangeList", param);
	}
	
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
