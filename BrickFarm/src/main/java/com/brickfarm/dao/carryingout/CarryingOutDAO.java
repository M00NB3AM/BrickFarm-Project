package com.brickfarm.dao.carryingout;

import java.util.List;

import com.brickfarm.dto.admin.sjy.AdminCarryingOutDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.etc.sjy.CarryingOutSearchCondition;
import com.brickfarm.etc.sjy.PagingInfo;

public interface CarryingOutDAO {

	// ==[송지영]==========================================================================================================================================
	List<AdminCarryingOutDTO> selectAllCarryingOut(PagingInfo pi) throws Exception;

	List<AdminCarryingOutDTO> selectCarryingOutByNo(int carrying_out_no) throws Exception;

	List<AdminCarryingOutDTO> selectAllCarryingOutByCondition(CarryingOutSearchCondition searchCondition, PagingInfo pi) throws Exception;

	int updateCarryingOutComplete(AdminCarryingOutDTO carryingOut) throws Exception;

	int updateCarryingOutModify(AdminCarryingOutDTO carryingOut) throws Exception;

	int updateCarryingOutConfirm() throws Exception;
	
	int selectCarriedOutCount() throws Exception;
	
	int selectTotalPostCnt() throws Exception; 
	
	int selectSearchedTotalPostCnt(CarryingOutSearchCondition searchCondition);
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
	// 반출에 데이터가 이미 있는지 없는지
	int selectHasCarryingOut(String product_code) throws Exception;
	int updateCarryingOutData(UserPaymentListDTO carryinOutData) throws Exception;
	int insertCarryingOutData(UserPaymentListDTO carryinOutData) throws Exception;
	// ==================================================================================================================================================


	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
