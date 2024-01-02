package com.brickfarm.dao.placeorder;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.sjy.AdminPlaceOrderDTO;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.etc.sjy.PlaceOrderCondition;
import com.brickfarm.vo.admin.sjy.AdminProductVO;

public interface PlaceOrderDAO {

	// ==[송지영]==========================================================================================================================================
	List<AdminPlaceOrderDTO> selectPlaceOrder(PagingInfo pi) throws Exception;
	
	List<AdminPlaceOrderDTO> selectPlaceOrderByNo(int place_order_no) throws Exception;
	
	List<AdminPlaceOrderDTO> selectPlaceOrderByCondition(PlaceOrderCondition searchCondition, PagingInfo pi) throws Exception;
	
	int insertPlaceOrder(List<AdminPlaceOrderDTO> products) throws Exception;
	
	int updatePlaceOrder(AdminPlaceOrderDTO product) throws Exception;
	
	int deleteOrder(String product_code) throws Exception;
	
	int deleteSelectedOrder(String place_order_no) throws Exception;
	
	int deleteOrderByCode(int place_order_no, String product_code) throws Exception;
	
	int updateOrderConfirm() throws Exception;
	
	List<AdminPlaceOrderDTO> selectPastThreeDaysOrder() throws Exception;
	
	int insertPlaceOrderAuto(List<AdminProductVO> productList) throws Exception;

	int selectIsNotPlaced(AdminProductVO product) throws Exception;

	int selectTotalPostCnt() throws Exception;

	int selectSearchedTotalPostCnt(PlaceOrderCondition searchCondition) throws Exception;

	int selectMaxPlaceOrderNo() throws Exception;
	
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
