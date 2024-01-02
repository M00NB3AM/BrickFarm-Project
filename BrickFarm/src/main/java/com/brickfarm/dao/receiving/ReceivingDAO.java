package com.brickfarm.dao.receiving;

import java.util.List;

import com.brickfarm.dto.admin.sjy.AdminPlaceOrderDTO;
import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.etc.sjy.ReceivingSearchCondition;

public interface ReceivingDAO {

	// ==[송지영]==========================================================================================================================================
	/**
	 * @methodName : getAllReceiving
	 * @author : sjy
	 * @param pi 
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 13. 오후 5:34:14
	 * @description : 입고 리스트 가져오기
	 */
	List<AdminReceivingDTO> selectReceiving(PagingInfo pi) throws Exception;

	List<AdminReceivingDTO> selectReceivingByNo(int receiving_no) throws Exception;
	
	List<AdminReceivingDTO> selectAllReceivingByCondition(ReceivingSearchCondition searchCondition, PagingInfo pi) throws Exception;
	
	int updateReceivingComplete(AdminReceivingDTO receiving) throws Exception;
	
	int updateReceivingModify(AdminReceivingDTO receiving) throws Exception;
	
	int deleteReceiving(String product_code) throws Exception;
	
	int insertReceiving(List<AdminPlaceOrderDTO> list) throws Exception;
	
	int updateReceivingConfirm() throws Exception;
	
	int selectTotalPostCnt() throws Exception;
	
	int selectSearchedTotalPostCnt(ReceivingSearchCondition searchCondition);
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
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
