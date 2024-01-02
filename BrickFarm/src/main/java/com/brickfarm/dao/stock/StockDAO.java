package com.brickfarm.dao.stock;

import java.util.List;

import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.vo.admin.sjy.AdminProductVO;
import com.brickfarm.vo.admin.sjy.AdminStockVO;

public interface StockDAO {

	// ==[송지영]==========================================================================================================================================
	// 재고 등록
	int insertProductStock(AdminStockVO stock) throws Exception;
	
	// 재고 수정
	void updateProductStockModify(AdminStockVO stock) throws Exception;
	
	// 재고 삭제
	int deleteStock(String product_code) throws Exception;
	
	// 재고 수량 확인
	List<AdminProductVO> selectStockCheck() throws Exception;
	
	// 품절 확인
	int selectSoldOutCount() throws Exception;
	
	// 스케쥴러로 입고에서 재고 증가
	boolean updateStock(String product_code);
	
	// 수동으로 재고 증가
	int updateStockManually(AdminReceivingDTO receiving);
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	
	/**
	 * @methodName : selectProductStockQuantity
	 * @author : lkm
	 * @param productCode
	 * @return
	 * @throws Exception
	 * @returnValue : @param productCode
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:54:52
	 * @description : 상품의 현재 남아있는 재고 가져오기
	 */
	int selectProductStockQuantity(String productCode) throws Exception;
	
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// 재고 수량 업데이트
	int updateStockOnStockQuantity(List<UserDetailedOrderDTO> detailedOrderList) throws Exception;
	// 재고 검증
	int isVerifyStock(List<UserPaymentListDTO> paymentList) throws Exception;
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
