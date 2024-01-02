package com.brickfarm.dao.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserOrderProductDTO;
import com.brickfarm.vo.user.psj.ShoppingCartVO;

@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.ShoppingCartMapper";

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	
	@Override
	public void verificationProductToCart(int memberNo, String productCode, int productQuantity) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_no", memberNo);
		params.put("product_code", productCode);
		params.put("quantity", productQuantity);
		
		List<Object> selectResult = new ArrayList<Object>();
		selectResult = ses.selectList("com.brickfarm.mappers.ShoppingCartMapper.selectHaveTheProduct", params);
		
		if (selectResult != null && !selectResult.isEmpty()) {
			ses.update("com.brickfarm.mappers.ShoppingCartMapper.updateProductToCart", params);
		} else {
			ses.insert("com.brickfarm.mappers.ShoppingCartMapper.insertProductToCart", params);
		}
		
	}
	
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	/**
	 * @methodName : selectShoppingCartList
	 * @author : psj
	 * @param memberNo
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 12:31:30
	 * @description : 장바구니 목록 조회
	 */
	@Override
	public List<UserShoppingCartDTO> selectShoppingCartList(int member_no) throws Exception {
		return ses.selectList(ns + ".selectShoppingCartList", member_no);
	}

	/**
	 * @methodName : updateShoppingCartProductCnt
	 * @author : psj
	 * @param shoppingCartNoList, shoppingCartQuantityList
	 * @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 3:33:30
	 * @description : 장바구니 품목의 수량 변경
	 */
	@Override
	public int updateShoppingCartProductCnt(List<Integer> shoppingCartNoList, List<Integer> shoppingCartQuantityList)
			throws Exception {
		int result = 0;
		
		for(int i = 0; i < shoppingCartNoList.size(); i++ ) {
			int shoppingCartNo = shoppingCartNoList.get(i);
			int shoppingCartQuantity = shoppingCartQuantityList.get(i);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("quantity", shoppingCartQuantity);
			params.put("shoppingCartNo", shoppingCartNo);
			result += ses.update(ns + ".updateShoppingCartProductCnt", params);
		}
		
		return result;
	}


	/**
	 * @methodName : deleteShoppingCart
	 * @author : psj
	 * @param shoppingCartNo
	 * @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 12:31:42
	 * @description : 장바구니 품목 단일 삭제
	 */
	@Override
	public int deleteShoppingCart(int shoppingCartNo) throws Exception {
		
		return ses.delete(ns + ".deleteShoppingCart", shoppingCartNo);
	}


	/**
	 * @methodName : deleteAllShoppingCart
	 * @author : psj
	 * @param memberNo
	 * @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 12:31:53
	 * @description : 장바구니 모든 품목 삭제
	 */
	@Override
	public int deleteAllShoppingCart(int member_no) throws Exception {
		
		return ses.delete(ns + ".deleteAllShoppingCart", member_no);
	}
	
	
	
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : deleteShoppingCartList
	 * @author : syt
	 * @param member_no
	 * @param detailedOrderList
	 * @return
	 * @throws Exception
	 * @returnValue : @param member_no
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 16. 오후 4:21:16
	 * @description : 구매완료후 쇼핑카트에 구매한 제품 삭제
	 */
	@Override
	public int deleteShoppingCartList(int member_no, List<UserDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		param.put("member_no", member_no);
		return ses.delete(ns + ".deleteShoppingCartList", param);
	}
	
	/**
	 * @methodName : selectShoppingCartDataList
	 * @author : syt
	 * @param shoppingCartNoList
	 * @return
	 * @throws Exception
	 * @returnValue : @param shoppingCartNoList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 16. 오후 4:20:51
	 * @description : 쇼핑카트 정보를 통해 데이터 가져옴
	 */
	@Override
	public List<UserOrderProductDTO> selectShoppingCartDataList(List<Integer> shoppingCartNoList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shoppingCartNoList", shoppingCartNoList);
		return ses.selectList(ns + ".selectShoppingCartDataList", param);
	}
	// ==================================================================================================================================================

	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
