package com.brickfarm.dao.productwishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;




@Repository
public class ProductWishListDAOImpl implements ProductWishListDAO {
	
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.ProductWishListMapper";

	
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	
	@Override
	public List<UserLikeProductVO> selectCompareWishList(int memberNo) throws Exception {
		List<UserLikeProductVO> selectResult = ses.selectList(ns + ".selectCompareWishlist", memberNo);
		
		return selectResult;
	}
	
	@Override
	public boolean verificationProductToWishlist(int memberNo, String productCode, boolean isLiked) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_no", memberNo);
		params.put("product_code", productCode);
		
		List<UserLikeProductVO> selectResult = new ArrayList<UserLikeProductVO>();
		selectResult = ses.selectList(ns + ".selectCompareWishlist", memberNo);
		
		boolean result = false;
		boolean status = false;
		
		try {
			if (isLiked == false) {			
				for (UserLikeProductVO item : selectResult) {
					if (item.getProduct_code().equals(productCode)) {
						result = true;
						status = false;
						break;
						
					}	
				}
				
				if (result == false) {
					ses.insert(ns + ".insertAddProductToWishlist", params);
					status = true;
				}
				
			} else if (isLiked == true) {
				ses.delete(ns + ".deleteProductToWishList", params);
				status = true;
				
			}
			
			return status;
			
		} catch (Exception e) {
			
			return status;
			
		}
		
		
	}
	
	@Override
	public List<UserProductVO> selectLikeProductList(int memberNo) throws Exception {
		
		List<UserProductVO> likeProductList = ses.selectList(ns + ".selectLikeProduct", memberNo);
		
		return likeProductList;
	}


	@Override
	public void deleteAllProductToWishlist(int memberNo) throws Exception {
		
		ses.delete(ns + ".deleteAllProductToWishList", memberNo);
		
	}
	
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
	
	//로그인시 wishList 가져오기
	@Override
	public List<UserLikeProductVO> getHeaderWishList(int member_no) throws Exception {
			
		List<UserLikeProductVO> list = ses.selectList(ns + ".getheaderwishlist", member_no);
		
		return list;
	}
	
	
	
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
