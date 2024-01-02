package com.brickfarm.service.user.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.brickfarm.dao.product.ProductDAO;
import com.brickfarm.dao.productcategory.ProductCategoryDAO;
import com.brickfarm.dao.productimage.ProductImageDAO;
import com.brickfarm.dao.productreview.ProductReviewDAO;
import com.brickfarm.dao.productwishlist.ProductWishListDAO;
import com.brickfarm.dao.stock.StockDAO;
import com.brickfarm.etc.lkm.PagingInfo;
import com.brickfarm.vo.user.lkm.UserProductImageVO;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.user.lkm.UserProductReviewVO;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Inject 
	private ProductDAO productDao;
	
	@Inject
	private ProductCategoryDAO productCategoryDao;
	
	@Inject
	private ProductImageDAO productImageDao;
	
	@Inject
	private StockDAO stockDao;
	
	@Inject
	private ProductWishListDAO productWishListDao;
	
	@Inject
	private ProductReviewDAO productReviewDao;

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	

	@Override
	public List<UserProductVO> getBestProductForIndex() throws Exception {
		List<UserProductVO> bestProductList = productDao.selectBestProduct();
		
		return bestProductList;
	}

	@Override
	public List<UserProductVO> getDiscountProductForIndex() throws Exception {
		List<UserProductVO> discountProductList = productDao.selectDiscountProduct();
		
		return discountProductList;
	}
	
	@Override
	public List<UserProductVO> getNewProductForIndex() throws Exception {
		List<UserProductVO> newProductList = productDao.selectNewProduct();
		
		return newProductList;
	}

	
	/**
	 * @methodName : getProductAll
	 * @author : lkm
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 11. 오후 2:44:20
	 * @description : index(MainPage)에서 상품 목록을 누르면 전체 상품을 조회하고 조회된 상품들이 보여지도록 응답
	 */
	@Override
	public Map<String, Object> getProductAll(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception {

		PagingInfo pagingInfo = makePagingInfo(category, pageNo, showCount, searchProduct, min, max);
		List<UserProductVO> productList = productDao.selectProductAll(pagingInfo, sortMethod, searchProduct);
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("pagingInfo", pagingInfo);
		productResult.put("productList", productList);
		productResult.put("sortMethod", sortMethod);
		productResult.put("searchProduct", searchProduct);
		
		return productResult;
	}
	

	@Override
	public Map<String, Object> getBestProduct(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception {

		PagingInfo pagingInfo = makePagingInfo(category, pageNo, showCount, searchProduct, min, max);
		List<UserProductVO> productList = productDao.selectBestProduct();
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("pagingInfo", pagingInfo);
		productResult.put("productList", productList);
		
		return productResult;
	}
	
	
	@Override
	public Map<String, Object> getProductByCaterory(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception {
		PagingInfo pagingInfo = makePagingInfo(category, pageNo, showCount, searchProduct, min, max);
		List<UserProductVO> productList = productCategoryDao.selectProductByCaterory(category, pagingInfo, sortMethod, searchProduct);
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("pagingInfo", pagingInfo);
		productResult.put("productList", productList);
		productResult.put("sortMethod", sortMethod);
		
		return productResult;
	}
	
	@Override
	public Map<String, Object> getProductByPrice(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max) throws Exception {
		PagingInfo pagingInfo = makePagingInfo(category, pageNo, showCount, searchProduct, min, max);
		List<UserProductVO> productList = productDao.selectProductByPrice(category, pagingInfo, sortMethod, searchProduct, min, max);
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("pagingInfo", pagingInfo);
		productResult.put("productList", productList);
		productResult.put("sortMethod", sortMethod);
		
		return productResult;
	}
	
	@Override
	public Map<String, Object> getRatingProductList(String category, int pageNo, int showCount, String sortMethod,
			String searchProduct, int min, int max, int selectedRatingValue) throws Exception {
		
		PagingInfo pagingInfo = makePagingInfo(pageNo, showCount, selectedRatingValue);
		List<UserProductVO> productList = productDao.selectRatingProductList(category, pagingInfo, sortMethod, searchProduct, selectedRatingValue);
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("pagingInfo", pagingInfo);
		productResult.put("productList", productList);
		productResult.put("sortMethod", sortMethod);
		
		return productResult;
	}
	
	private PagingInfo makePagingInfo(String category, int pageNo, int showCount, String searchProduct, int min, int max) throws Exception {
		PagingInfo pagingResult = new PagingInfo(); 
		
		// 현재 페이지 번호 세팅
		pagingResult.setPageNo(pageNo);
		
		// 전체 글의 갯수
		pagingResult.setTotalPostCount(productDao.selectTotalPostCount(category, searchProduct, min, max));
		
		// 페이지 당 보여줄 글의 갯수
		pagingResult.setViewPostCountPerPage(showCount);
		
		// 총 페이지 수 구하기
		pagingResult.setTotalPageCount(pagingResult.getTotalPostCount(), pagingResult.getViewPostCountPerPage());
		
		// 보여주기 시작할 index 번호 조회
		pagingResult.setStartRowIndex();
		
		// ----------------------------------------------------------------------------------------
		
		// 페이지 블럭의 총 갯수 구하기
		pagingResult.setTotalPagingBlockCount();
				
		// 현재 페이지가 속한 페이지블럭 번호 구하기
		pagingResult.setPageBlockOfCurrentPage();
				
		// 현재 블럭에 시작 페이지 번호 구하기
		pagingResult.setStartNumOfCurrentPagingBlock();
		
		// 현재 블럭에 끝 페이지 번호 구하기
		pagingResult.setEndNumOfCurrentPagingBlock();
		
		return pagingResult;
	}	
	
	
	private PagingInfo makePagingInfo(int pageNo, int showCount, int selectedRatingValue) throws Exception {
		PagingInfo pagingResult = new PagingInfo(); 
		
		// 현재 페이지 번호 세팅
		pagingResult.setPageNo(pageNo);
		
		// 전체 글의 갯수
		pagingResult.setTotalPostCount(productDao.selectTotalPostCountForRating(selectedRatingValue));
		
		// 페이지 당 보여줄 글의 갯수
		pagingResult.setViewPostCountPerPage(showCount);
		
		// 총 페이지 수 구하기
		pagingResult.setTotalPageCount(pagingResult.getTotalPostCount(), pagingResult.getViewPostCountPerPage());
		
		// 보여주기 시작할 index 번호 조회
		pagingResult.setStartRowIndex();
		
		// ----------------------------------------------------------------------------------------
		
		// 페이지 블럭의 총 갯수 구하기
		pagingResult.setTotalPagingBlockCount();
				
		// 현재 페이지가 속한 페이지블럭 번호 구하기
		pagingResult.setPageBlockOfCurrentPage();
				
		// 현재 블럭에 시작 페이지 번호 구하기
		pagingResult.setStartNumOfCurrentPagingBlock();
		
		// 현재 블럭에 끝 페이지 번호 구하기
		pagingResult.setEndNumOfCurrentPagingBlock();
		
		return pagingResult;
	}	
	
	private PagingInfo makePagingInfo(int pageNo, String productCode) throws Exception {
		PagingInfo pagingResult = new PagingInfo(); 
		
		// 현재 페이지 번호 세팅
		pagingResult.setPageNo(pageNo);
		
		// 전체 글의 갯수
		pagingResult.setTotalPostCount(productDao.selectTotalReviewCount(productCode));
		
		// 페이지 당 보여줄 글의 갯수
		pagingResult.setViewPostCountPerPage(10);
		
		// 총 페이지 수 구하기
		pagingResult.setTotalPageCount(pagingResult.getTotalPostCount(), pagingResult.getViewPostCountPerPage());
		
		// 보여주기 시작할 index 번호 조회
		pagingResult.setStartRowIndex();
		
		// ----------------------------------------------------------------------------------------
		
		// 페이지 블럭의 총 갯수 구하기
		pagingResult.setTotalPagingBlockCount();
				
		// 현재 페이지가 속한 페이지블럭 번호 구하기
		pagingResult.setPageBlockOfCurrentPage();
				
		// 현재 블럭에 시작 페이지 번호 구하기
		pagingResult.setStartNumOfCurrentPagingBlock();
		
		// 현재 블럭에 끝 페이지 번호 구하기
		pagingResult.setEndNumOfCurrentPagingBlock();
		
		return pagingResult;
	}	

	@Override
	public Map<String, Object> getProductDetail(String productCode) {
		Map<String, Object> productDetail = productDao.selectProductDetail(productCode);
		
		return productDetail;
	}


	@Override
	public List<UserProductImageVO> getProductDetailImages(String productCode) throws Exception {
		List<UserProductImageVO> productImages = productImageDao.selectProductImages(productCode); 
		
		return productImages;
	}


	@Override
	public int getProductStockQuantity(String productCode) throws Exception {
		int stockQuantity = stockDao.selectProductStockQuantity(productCode);

		return stockQuantity;
	}


	@Override
	public List<UserProductVO> getAlsoViewProduct(String productCode, int productCategoryNo) throws Exception {
		List<UserProductVO> alsoViewProductList = productDao.selectAlsoViewProduct(productCode, productCategoryNo);
		
		return alsoViewProductList;
	}

	@Override
	public List<UserLikeProductVO> getLikeProductList(int memberNo) throws Exception {
		List<UserLikeProductVO> likeProductList = productWishListDao.selectCompareWishList(memberNo);
		
		return likeProductList;
		
	}

	@Override
	public Map<String, Object> getProductReviewList(String productCode, String sortReview, int pageNo) throws Exception {
		
		PagingInfo pagingInfo = makePagingInfo(pageNo, productCode);
		List<UserProductReviewVO> productReviewList = productReviewDao.selectProductReviewList(productCode, sortReview, pagingInfo);
		
		Map<String, Object> productReviewResult = new HashMap<String, Object>();
		productReviewResult.put("pagingInfo", pagingInfo);
		productReviewResult.put("productReviewList", productReviewList);

		
		
		return productReviewResult;
	}

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
