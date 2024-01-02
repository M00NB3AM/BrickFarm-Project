package com.brickfarm.dao.exchange;

import java.util.List;

import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminExchangeDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.dto.user.psj.UserExchangeOrderDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.etc.syt.Pagination;
import com.brickfarm.vo.admin.syt.AdminExchangeVO;

public interface ExchangeDAO {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// 로그인한 멤버의 주문번호 별 교환 주문 상세 정보 조회(교환)
	List<UserExchangeOrderDTO> selectMemberExchangeOrderInfo(int memberNo, String merchantUid) throws Exception;
	// 교환 신청 시 데이터 추가
	int insertExchangeConfirm(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception;
	
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	int selectTotalDataCount(AdminSearchDTO searchDto) throws Exception;
	List<AdminExchangeVO> selectExchangeList(AdminSearchDTO searchDto, Pagination pagination) throws Exception;
	int updateExchangeByStateCheck(List<AdminExchangeDTO> exchangeList) throws Exception;
	int updateExchangeByStateProcess(List<AdminExchangeDTO> exchangeList) throws Exception;
	int updateExchangeByStateComplete(List<AdminExchangeDTO> exchangeList) throws Exception;
	//반품테이블 삭제(반품취소시 로직)
	void deleteExchangeByDetailedOrderNo(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception;
	// 반품 테이블 일괄추가
	int insertExchangeList(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception;
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
