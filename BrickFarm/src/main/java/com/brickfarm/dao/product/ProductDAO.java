package com.brickfarm.dao.product;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserOrderProductDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.etc.lkm.PagingInfo;
import com.brickfarm.etc.sjy.ProductSearchCondition;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderProductsVO;
import com.brickfarm.vo.admin.sjy.AdminEventVO;
import com.brickfarm.vo.admin.sjy.AdminProductVO;
import com.brickfarm.vo.user.lkm.UserProductImageVO;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.user.lkm.UserProductReviewVO;

public interface ProductDAO {


	// ==[송지영]==========================================================================================================================================
	List<AdminProductVO> selectAllProducts() throws Exception;
	
	List<AdminProductVO> getSearchedProductsPaging(ProductSearchCondition searchCondition, com.brickfarm.etc.sjy.PagingInfo pi) throws Exception;

	List<AdminProductVO> selectAllProductsByCondition(ProductSearchCondition searchCondition) throws Exception;
	
	List<AdminProductVO> selectProductDetails(int product_code) throws Exception;
	
	int insertProduct(AdminProductVO product) throws Exception;

	int updateProductModify(AdminProductVO product) throws Exception;

	int deleteProduct(String product_code) throws Exception;

	List<AdminProductVO> selectProductsByEvtNo(int event_no) throws Exception;
	
	int updateEventProductsCancel(String product_code) throws Exception;

	int updateEventRegist(String product_code) throws Exception;

	int updateAllEvent(int event_no) throws Exception;

	int selectTotalPostCnt() throws Exception;

	int updateEvent(Integer event_no) throws Exception;

	List<AdminProductVO> selectAutoOrder() throws Exception;
	
	int selectProductsCount() throws Exception;
	
	int updateEventScheduler() throws Exception;
	
	List<AdminProductVO> selectProductsByEvtNoFromLog(int event_no) throws Exception;

	List<AdminEventVO> selectAllProductsPaging(com.brickfarm.etc.sjy.PagingInfo pi);
	
	int selectSearchedTotalPostCnt(ProductSearchCondition searchCondition) throws Exception;
	
	List<String> selectReceivingProductCode() throws Exception;

	int selectEventToUpdate() throws Exception;
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
		
		// 주문 상세 내역에 있는 상품 정보 가져오기
		List<AdminDetailOrderProductsVO> selectDetailOrderProducts(String merchant) throws Exception;
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	
	/**
	 * @methodName : selectBestProduct
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:51:47
	 * @description : index 페이지에 보여질 베스트 상품 가져오기
	 */
	List<UserProductVO> selectBestProduct() throws Exception;
	
	/**
	 * @methodName : selectDiscountProduct
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:52:10
	 * @description : index 페이지에 보여질 할인 상품 가져오기
	 */
	List<UserProductVO> selectDiscountProduct() throws Exception;
	
	/**
	 * @methodName : selectNewProduct
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:52:24
	 * @description : index 페이지에 보여질 신상품 가져오기
	 */
	List<UserProductVO> selectNewProduct() throws Exception;
	
	/**
	 * @methodName : selectProductAll
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 11. 오후 2:44:35
	 * @description : index(MainPage)에서 상품 목록을 누르면 전체 상품을 조회하고 조회된 상품들이 보여지도록 응답
	 */
	List<UserProductVO> selectProductAll(PagingInfo pagingInfo, String sortMethod, String searchProduct) throws Exception;
	
	
	/**
	 * @methodName : selectProductByPrice
	 * @author : lkm
	 * @param category
	 * @param pagingInfo
	 * @param sortMethod
	 * @param searchProduct
	 * @param min
	 * @param max
	 * @return
	 * @throws Exception
	 * @returnValue : @param category
	 * @returnValue : @param pagingInfo
	 * @returnValue : @param sortMethod
	 * @returnValue : @param searchProduct
	 * @returnValue : @param min
	 * @returnValue : @param max
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:53:59
	 * @description : 상품 리스트에서 상품을 가격 범위로 검색했을 때 조건에 맞는 상품 가져오기
	 */
	List<UserProductVO> selectProductByPrice(String category, PagingInfo pagingInfo, String sortMethod,
			String searchProduct, int min, int max) throws Exception;
	
	List<UserProductVO> selectRatingProductList(String category, PagingInfo pagingInfo, String sortMethod,
			String searchProduct, int selectedRatingValue);
	
	/**
	 * @methodName : selectProductDetail
	 * @author : lkm
	 * @param productCode
	 * @return
	 * @returnValue : @param productCode
	 * @returnValue : @return
	 * @date : 2023. 10. 17. 오후 5:54:16
	 * @description : 상품을 클릭했을 때 상세 페이지를 보여줄 정보 가져오기
	 */
	Map<String, Object> selectProductDetail(String productCode);
	
	/**
	 * @methodName : selectTotalPostCount
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 11. 오후 7:57:42
	 * @description : 페이지네이션을 위한 상품의 총 갯수를 확인하는 메서드
	 */
	int selectTotalPostCount(String category, String searchProduct, int min, int max) throws Exception;

	
	int selectTotalPostCountForRating(int selectedRatingValue) throws Exception;
			
	int selectTotalReviewCount(String productCode) throws Exception;

	/**
	 * @methodName : selectAlsoViewProduct
	 * @author : lkm
	 * @param productCode
	 * @param productCategoryNo
	 * @return
	 * @throws Exception
	 * @returnValue : @param productCode
	 * @returnValue : @param productCategoryNo
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 12:55:00
	 * @description : 상품 상세 페이지에 보여지는 같이 본 상품 가져오기(기준은 같은 category_no를 가진 상품들)
	 */
	List<UserProductVO> selectAlsoViewProduct(String productCode, int productCategoryNo) throws Exception;
	
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// 결제한 상품 정보전체 가져옴
	List<UserOrderProductDTO> selectProductDataAllList(List<UserPaymentListDTO> paymentList) throws Exception;
	// ==================================================================================================================================================


	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
