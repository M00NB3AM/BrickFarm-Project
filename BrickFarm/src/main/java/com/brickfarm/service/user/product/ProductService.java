package com.brickfarm.service.user.product;

import java.util.List;
import java.util.Map;

import com.brickfarm.vo.user.lkm.UserProductImageVO;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.user.lkm.UserProductReviewVO;

/**
 * @packageName : com.brickfarm.service.user.product
 * @fileName : ProductService.java
 * @author : lkm
 * @date : 2023. 10. 26.
 * @description :
 */
/**
 * @packageName : com.brickfarm.service.user.product 
 * @fileName :  ProductService.java
 * @author : lkm
 * @date : 2023. 10. 30.
 * @description : 
 */
public interface ProductService {
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================

	List<UserProductVO> getBestProductForIndex() throws Exception;
	
	List<UserProductVO> getDiscountProductForIndex() throws Exception;
	
	List<UserProductVO> getNewProductForIndex() throws Exception;
	
	/**
	 * @methodName : getProductAll
	 * @author : lkm
	 * @param pageNo
	 * @param showCount
	 * @param sortMethod
	 * @param searchProduct
	 * @return
	 * @throws Exception
	 * @returnValue : @param pageNo
	 * @returnValue : @param showCount
	 * @returnValue : @param sortMethod
	 * @returnValue : @param searchProduct
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 26. 오후 4:06:15
	 * @description : 페이지네이션을 위한 pageNo, 보기 기준에 필요한 showCount, 정렬 기준에 필요한
	 *              sortMethod, 검색을 위한 searchProduct를 매개 변수로 받고 index(MainPage)에서 상품
	 *              목록을 누르면 전체 상품을 조회하고 조회된 상품들이 보여지도록 응답
	 */
	Map<String, Object> getProductAll(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception;
	
	Map<String, Object> getBestProduct(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception;
	
	/**
	 * @methodName : getProductCars
	 * @author : lkm
	 * @param pageNo
	 * @param showCount
	 * @param sortMethod
	 * @return
	 * @returnValue : @param pageNo
	 * @returnValue : @param showCount
	 * @returnValue : @param sortMethod
	 * @returnValue : @return
	 * @date : 2023. 10. 26. 오후 4:04:54
	 * @description : 페이지네이션을 위한 pageNo, 보기 기준에 필요한 showCount, 정렬 기준에 필요한
	 *              sortMethod를 매개 변수로 받고 자동차 분류에 해당하는 모든 상품을 가져오도록 하는 메서드
	 */
	Map<String, Object> getProductByCaterory(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception;

	/**
	 * @methodName : getProductByPrice
	 * @author : lkm
	 * @param category
	 * @param pageNo
	 * @param showCount
	 * @param sortMethod
	 * @param searchProduct
	 * @param min
	 * @param max
	 * @return
	 * @returnValue : @param category
	 * @returnValue : @param pageNo
	 * @returnValue : @param showCount
	 * @returnValue : @param sortMethod
	 * @returnValue : @param searchProduct
	 * @returnValue : @param min
	 * @returnValue : @param max
	 * @returnValue : @return
	 * @date : 2023. 10. 27. 오후 12:36:21
	 * @description :
	 */
	Map<String, Object> getProductByPrice(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int priceMin, int priceMax) throws Exception;

	
	Map<String, Object> getRatingProductList(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max, int selectedRatingValue) throws Exception;
	
	
	/**
	 * @methodName : getProductDetail
	 * @author : lkm
	 * @param productCode
	 * @return
	 * @throws Exception
	 * @returnValue : @param productCode
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 26. 오후 4:04:46
	 * @description :
	 */
	Map<String, Object> getProductDetail(String productCode) throws Exception;

	
	/**
	 * @methodName : getProductDetailImages
	 * @author : lkm
	 * @param productCode
	 * @return
	 * @returnValue : @param productCode
	 * @returnValue : @return
	 * @date : 2023. 10. 30. 오후 4:07:41
	 * @description : 
	 */
	List<UserProductImageVO> getProductDetailImages(String productCode) throws Exception;

	int getProductStockQuantity(String productCode) throws Exception;

	List<UserProductVO> getAlsoViewProduct(String productCode, int productCategoryNo) throws Exception;

	List<UserLikeProductVO> getLikeProductList(int memberNo) throws Exception;

	Map<String, Object> getProductReviewList(String productCode, String sortReview, int pageNo) throws Exception;

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
