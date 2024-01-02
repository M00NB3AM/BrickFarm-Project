package com.brickfarm.dao.shoppingcart;

import java.util.List;

import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserOrderProductDTO;
import com.brickfarm.vo.user.psj.ShoppingCartVO;

public interface ShoppingCartDAO {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	
	/**
	 * @methodName : verificationProductToCart
	 * @author : lkm
	 * @param memberNo
	 * @param productCode
	 * @param productQuantity
	 * @throws Exception
	 * @returnValue : @param memberNo
	 * @returnValue : @param productCode
	 * @returnValue : @param productQuantity
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:55:28
	 * @description : 장바구니에 상품 추가
	 */
	void verificationProductToCart(int memberNo, String productCode, int productQuantity) throws Exception;
	
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	// 장바구니 품목(List)조회
	List<UserShoppingCartDTO> selectShoppingCartList(int memberNo) throws Exception;
		
	// 장바구니 품목(단일) 삭제
	int deleteShoppingCart(int shoppingCartNo) throws Exception;
		
	// 장바구니 품목(모두) 삭제
	int deleteAllShoppingCart(int memberNo) throws Exception;
	
	// 장바구니 품목 수량 변경
	int updateShoppingCartProductCnt(List<Integer> shoppingCartNoList,List<Integer> shoppingCartQuantityList) throws Exception;
	
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	int deleteShoppingCartList(int member_no, List<UserDetailedOrderDTO> detailedOrderList) throws Exception;
	// 결제페이지에 결제제품 정보 가져가는곳
	List<UserOrderProductDTO> selectShoppingCartDataList(List<Integer> shoppingCartNoList) throws Exception;
	// ==================================================================================================================================================

	

	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
