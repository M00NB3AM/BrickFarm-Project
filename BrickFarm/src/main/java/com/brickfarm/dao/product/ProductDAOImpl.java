package com.brickfarm.dao.product;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.ProductMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminProductVO> selectAllProducts() throws Exception {
		return ses.selectList(ns + ".selectAllProducts");
	}

	@Override
	public List<AdminProductVO> selectProductDetails(int product_code) throws Exception {
		return ses.selectList(ns + ".selectProductDetails", product_code);
	}

	@Override
	public List<AdminProductVO> selectAllProductsByCondition(ProductSearchCondition searchCondition) throws Exception {
		return ses.selectList(ns + ".selectAllProductsByCondition", searchCondition);
	}
	
	
	@Override
	public List<AdminProductVO> selectProductsByEvtNo(int event_no) throws Exception {
		return ses.selectList(ns + ".selectProductsByEvtNo", event_no);
	}
	
	@Override
	public int insertProduct(AdminProductVO product) throws Exception {
		return ses.insert(ns + ".insertProduct", product);
	}

	@Override
	public int updateProductModify(AdminProductVO product) throws Exception {
		return ses.update(ns + ".updateProductModify", product);
	}
	

	@Override
	public int deleteProduct(String product_code) throws Exception {
		return ses.delete(ns + ".deleteProduct", product_code);
	}
	

	@Override
	public int updateEventProductsCancel(String product_code) throws Exception {
		return ses.update(ns + ".updateEventProductsCancel", product_code);
	}
	
	@Override
	public int updateEventRegist(String product_code) throws Exception {
		return ses.update(ns + ".updateEventRegist", product_code);
	}
	
	@Override
	public int updateAllEvent(int event_no) throws Exception {
		return ses.update(ns + ".updateAllEvent", event_no);
	}
	
	@Override
	public int selectTotalPostCnt() throws Exception {
		return ses.selectOne(ns + ".selectTotalPostCnt");
	}
	
	@Override
	public int updateEvent(Integer event_no) throws Exception {
		return ses.update(ns + ".updateEvent", event_no);
	}
	
	@Override
	public List<AdminProductVO> selectAutoOrder() throws Exception {
		return ses.selectList(ns + ".selectAutoOrder");
	}
	
	@Override
	public int selectProductsCount() throws Exception {
		return ses.selectOne(ns + ".selectProductCount");
	}
	
	@Override
	public int updateEventScheduler() throws Exception {
		return ses.update(ns + ".updateEventScheduler");
	}
	
	@Override
	public List<AdminProductVO> selectProductsByEvtNoFromLog(int event_no) throws Exception {
		return ses.selectList(ns + ".selectProductsByEvtNoFromLog", event_no);
	}
	

	@Override
	public List<AdminEventVO> selectAllProductsPaging(com.brickfarm.etc.sjy.PagingInfo pi) {
		return ses.selectList(ns + ".selectAllProductsPaging", pi);
	}
	

	@Override
	public List<AdminProductVO> getSearchedProductsPaging(ProductSearchCondition searchCondition,
			com.brickfarm.etc.sjy.PagingInfo pi) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("all", searchCondition.getAll());
		map.put("display", searchCondition.getDisplay());
		map.put("event_no", searchCondition.getEvent_no());
		map.put("is_new", searchCondition.getIs_new());
		map.put("not_display", searchCondition.getNot_display());
		map.put("search_word", searchCondition.getSearch_word());
		map.put("product_category_no", searchCondition.getProduct_category_no());
		map.put("min_date", searchCondition.getMin_date());
		map.put("max_date", searchCondition.getMax_date());
		map.put("minimum_price", searchCondition.getMinimum_price());
		map.put("maximum_price", searchCondition.getMaximum_price());
		map.put("minimum_stock", searchCondition.getMinimum_stock());
		map.put("maximum_stock", searchCondition.getMaximum_stock());
		map.put("stock_search_type", searchCondition.getStock_search_type());
		map.put("search_type", searchCondition.getSearch_type());
		map.put("safety_stock_quantity", searchCondition.getSafety_stock_quantity());
		map.put("startRowIndex", pi.getStartRowIndex());
		map.put("viewPostCntPerPage", pi.getViewPostCntPerPage());
		return ses.selectList(ns + ".selectAllProductsByConditionPaging", map);
	}

	@Override
	public int selectSearchedTotalPostCnt(ProductSearchCondition searchCondition) throws Exception {
		return ses.selectOne(ns + ".selectSearchedTotalPostCnt", searchCondition);
	}
	
	@Override
	public List<String> selectReceivingProductCode() throws Exception {
		return ses.selectList(ns + ".selectReceivingProductCode");
	}
	
	@Override
	public int selectEventToUpdate() throws Exception {
		return ses.selectOne(ns + ".selectEventToUpdate");
	}
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================

		/**
		 * @methodName : selectDetailOrderProducts
		 * @author : kmh
		 * @param merchant
		 * @return
		 * @throws Exception
		 * @returnValue : @param merchant
		 * @returnValue : @return
		 * @returnValue : @throws Exception
		 * @date : 2023. 11. 23. 오후 6:14:38
		 * @description : 주문 내역 상품 정보 가져오기
		 */
		@Override
		public List<AdminDetailOrderProductsVO> selectDetailOrderProducts(String merchant) throws Exception {
			return ses.selectList(ns + ".findDetailOrderProducts", merchant);
		}
	
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	
	@Override
	public List<UserProductVO> selectBestProduct() throws Exception {
		
		return ses.selectList(ns + ".selectBestProduct");
	}
	
	@Override
	public List<UserProductVO> selectDiscountProduct() throws Exception {
		
		return ses.selectList(ns + ".selectDiscountProduct");
	}
	
	@Override
	public List<UserProductVO> selectNewProduct() throws Exception {
		
		return ses.selectList(ns + ".selectNewProduct");
	}
	
	/**
	 * @methodName : selectProductAll
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 11. 오후 2:44:44
	 * @description : index(MainPage)에서 상품 목록을 누르면 전체 상품을 조회하고 조회된 상품들이 보여지도록 응답
	 */
	@Override
	public List<UserProductVO> selectProductAll(PagingInfo pagingInfo, String sortMethod, String searchProduct) throws Exception {

		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("startRowIndex", pagingInfo.getStartRowIndex());
		productResult.put("viewPostCountPerPage", pagingInfo.getViewPostCountPerPage());
		productResult.put("sortMethod", sortMethod);
		productResult.put("searchProduct", searchProduct);
	
//		System.out.println(sortMethod);
//		= ses.selectList(ns + ".selectProductAll");
//		for (Product product : productList) {
//		    System.out.println(product);
//		}
		return ses.selectList(ns + ".selectProductAll", productResult);

	}
	
	@Override
	public List<UserProductVO> selectProductByPrice(String category, PagingInfo pagingInfo, String sortMethod,
			String searchProduct, int min, int max) throws Exception {
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("category", category);
		productResult.put("startRowIndex", pagingInfo.getStartRowIndex());
		productResult.put("viewPostCountPerPage", pagingInfo.getViewPostCountPerPage());
		productResult.put("sortMethod", sortMethod);
		productResult.put("min", min);
		productResult.put("max", max);
		
		return ses.selectList(ns + ".selectProductByPrice", productResult);
	}
	
	@Override
	public List<UserProductVO> selectRatingProductList(String category, PagingInfo pagingInfo, String sortMethod,
			String searchProduct, int selectedRatingValue) {
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("startRowIndex", pagingInfo.getStartRowIndex());
		productResult.put("viewPostCountPerPage", pagingInfo.getViewPostCountPerPage());
		productResult.put("selectedRatingValue", selectedRatingValue);
		
		return ses.selectList(ns + ".selectProductByRating", productResult);
	}
	
	/**
	 * @methodName : selectProductDetail
	 * @author : lkm
	 * @param productCode
	 * @return
	 * @returnValue : @param productCode
	 * @returnValue : @return
	 * @date : 2023. 10. 17. 오후 5:54:42
	 * @description :
	 */
	@Override
	public Map<String, Object> selectProductDetail(String productCode) {
//		List<UserProduct> productDetail = new ArrayList<UserProduct>();

		Map<String, Object> productDetailResult = new HashMap<String, Object>();
		productDetailResult.put("product", ses.selectOne(ns + ".selectProductDetail", productCode));

		return productDetailResult;

	}

	/**
	 * @methodName : selectTotalPostCount
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 11. 오후 7:38:16
	 * @description : 페이지네이션을 위한 전체 상품의 갯수 조회
	 */
	@Override
	public int selectTotalPostCount(String category, String searchProduct, int min, int max) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category", category);
		params.put("searchProduct", searchProduct);
		params.put("min", min);
		params.put("max", max);
		
		return ses.selectOne(ns + ".selectProductCount", params);
	}

	@Override
	public int selectTotalPostCountForRating(int selectedRatingValue) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selectedRatingValue", selectedRatingValue);
		
		return ses.selectOne(ns + ".selectProductCountForRating", params);
	}
	
	@Override
	public int selectTotalReviewCount(String productCode) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productCode", productCode);
		
		return ses.selectOne(ns + ".selectTotalReviewCount", params);
	}
	
	@Override
	public List<UserProductVO> selectAlsoViewProduct(String productCode, int productCategoryNo) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product_category_no", productCategoryNo);
		params.put("product_code", productCode);
		
		return ses.selectList(ns + ".selectAlsoViewProduct", params);
	
	}	
	
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : selectProductDataAllList
	 * @author : syt
	 * @param product_code
	 * @param quantity
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param product_code
	 * @returnValue : @param quantity
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 17. 오후 12:46:41
	 * @description : 결제상품 전체정보 가져옴
	 */
	@Override
	public List<UserOrderProductDTO> selectProductDataAllList(List<UserPaymentListDTO> paymentList) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("paymentList", paymentList);	
		return ses.selectList(ns + ".selectProductDataAllList", params);
	}
	// ==================================================================================================================================================



	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
