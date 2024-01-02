package com.brickfarm.controller.admin.statistics.kyj;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaMembers;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaNetSales;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaProducts;
import com.brickfarm.etc.kyj.statistics.constant.ProductsStatisticsClassification;
import com.brickfarm.service.admin.statistics.AdminStatisticsService;
import com.brickfarm.vo.admin.kyj.statistics.members.StatByDaynameVO;
import com.brickfarm.vo.admin.kyj.statistics.members.StatByHourVO;
import com.brickfarm.vo.admin.kyj.statistics.members.StatByMemberGradeVO;
import com.brickfarm.vo.admin.kyj.statistics.members.StatByRecipientAddressVO;
import com.brickfarm.vo.admin.kyj.statistics.members.TotalAccrualStatVO;
import com.brickfarm.vo.admin.kyj.statistics.netsales.AdminDailyNetSalesVO;
import com.brickfarm.vo.admin.kyj.statistics.netsales.AdminMonthlyNetSalesVO;
import com.brickfarm.vo.admin.kyj.statistics.netsales.AdminWeeklyNetSalesVO;
import com.brickfarm.vo.admin.kyj.statistics.products.cart.CartMemberCountTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.cart.CartRegiCountTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.cart.CartStatVO;
import com.brickfarm.vo.admin.kyj.statistics.products.cartdetail.TotalCartDetailStatVO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalcanceled.TotalCanceledProductsStatVO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalcanceled.TotalCanceledQuantityTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalcanceled.TotalCanceledRatioTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalsales.TotalSalesAmountTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalsales.TotalSalesProductsStatVO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalsales.TotalSalesQuantityTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalsalesbycategory.TotalSalesAmountByCategoryTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalsalesbycategory.TotalSalesProductsByCategoryStatVO;
import com.brickfarm.vo.admin.kyj.statistics.products.totalsalesbycategory.TotalSalesQuantityByCategoryTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.wishlist.WishListConfirmedCountTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.wishlist.WishListMemberCountTop10VO;
import com.brickfarm.vo.admin.kyj.statistics.products.wishlist.WishListStatVO;

@Controller
@RequestMapping("/admin/statistics/*")
public class AdminStatisticsController {
	// private static final Logger logger = LoggerFactory.getLogger(AdminStatisticsController.class);
	
	@Inject
	private AdminStatisticsService statService;

	/* === 통계 대시보드 ================================================================================================================================================ */
	@GetMapping("dashboard")
	public String dashboard(Model model) throws SQLException {
		// logger.info("통계 대시보드 페이지 요청");
		
//		Map<String, Object> dashboardInfo = statService.getDashboardInfo();
		
//		model.addAttribute("dashboardInfo", dashboardInfo);
		
		return "/admin/statistics/dashboard";
	}
	
	@GetMapping("dashboard/info")
	public ResponseEntity<Map<String,Object>> dashboardInfo() {
		// logger.info("통계 대시보드 데이터 api 요청");
		
		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			Map<String, Object> dashboardInfo;
			dashboardInfo = statService.getDashboardInfo();
			result = new ResponseEntity<Map<String,Object>>(dashboardInfo, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		return result;
	}

	/* === 매출 분석 =================================================================================================================================================== */
	@GetMapping("netsales")
	public String netSales() throws SQLException {
		// logger.info("매출 분석 페이지 요청");
		
		return "/admin/statistics/netsales";
	}
	
	@GetMapping("netsales/daily")
	public ResponseEntity<Map<String, Object>> dailyNetSalesInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "isCheckedUsePoint", defaultValue = "false") Boolean isCheckedUsePoint,
			@RequestParam(value = "selectedPeriod") int selectedPeriod) {
		// logger.info("일별 매출 데이터 api 요청");
		
		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaNetSales sc = SearchCriteriaNetSales.builder()
					.startDate(new Timestamp(startDate.getTime()))
					.endDate(new Timestamp(endDate.getTime()))
					.isCheckedUsePoint(isCheckedUsePoint)
					.build();
			PaginationInfo pi = statService.createNetSalesPaginationInfo(curPageNo, rowCountPerPage, sc, selectedPeriod);
			// System.out.println(pi.toString());
			// System.out.println(sc.toString());
			
			List<AdminDailyNetSalesVO> list = statService.getDailyNetSalesInfo(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("dailyNetSales", list);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@GetMapping("netsales/weekly")
	public ResponseEntity<Map<String, Object>> weeklyNetSalesInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "recentWeek") int recentWeek,
			@RequestParam(value = "isCheckedUsePoint", defaultValue = "false") Boolean isCheckedUsePoint,
			@RequestParam(value = "selectedPeriod") int selectedPeriod) {
		// logger.info("주별 매출 데이터 api 요청");
		
		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaNetSales sc = SearchCriteriaNetSales.builder()
					.recentWeek(recentWeek)
					.isCheckedUsePoint(isCheckedUsePoint)
					.build();
			PaginationInfo pi = statService.createNetSalesPaginationInfo(curPageNo, rowCountPerPage, sc, selectedPeriod);
			// System.out.println(pi.toString());
			// System.out.println(sc.toString());
			
			int currentWeekOfYear = statService.getCurrentWeekOfYear();
			List<AdminWeeklyNetSalesVO> list = statService.getWeeklyNetSalesInfo(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("currentWeekOfYear", currentWeekOfYear);
			responseData.put("weeklyNetSales", list);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	@GetMapping("netsales/monthly")
	public ResponseEntity<Map<String, Object>> monthlyNetSalesInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "isCheckedUsePoint", defaultValue = "false") Boolean isCheckedUsePoint,
			@RequestParam(value = "selectedPeriod") int selectedPeriod) {
		// logger.info("월별 매출 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaNetSales sc = SearchCriteriaNetSales.builder()
					.startDate(new Timestamp(startDate.getTime()))
					.endDate(new Timestamp(endDate.getTime()))
					.isCheckedUsePoint(isCheckedUsePoint)
					.build();
			PaginationInfo pi = statService.createNetSalesPaginationInfo(curPageNo, rowCountPerPage, sc, selectedPeriod);
			// System.out.println(pi.toString());
			// System.out.println(sc.toString());
			
			List<AdminMonthlyNetSalesVO> list = statService.getMonthlyNetSalesInfo(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("monthlyNetSales", list);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [selectedPeriod]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}

	/* === 상품 분석 =================================================================================================================================================== */
	@GetMapping("products/totalsales/recommendage")
	public ResponseEntity<Map<String, Object>> getRecommendAge() {
		// logger.info("[상품분석] 추천 연령 목록 데이터 api 요청");
		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			List<String> recommendAgeList = statService.getSortedRecommendAge();
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("recommendAge", recommendAgeList);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");

			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	/* --- 판매 상품 순위 --- */
	@GetMapping("products/totalsales")
	public String productsTotalSales() {
		// logger.info("[상품분석] 판매 상품 순위 페이지 요청");
		return "/admin/statistics/products/totalSales";
	}

	@GetMapping("products/totalsales/data")
	public ResponseEntity<Map<String, Object>> productsTotalSalesInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "productLargeCategoryNo", defaultValue = "-1") int productLargeCategoryNo,
			@RequestParam(value = "productMediumCategoryNo", defaultValue = "-1") int productMediumCategoryNo,
			@RequestParam(value = "productSmallCategoryNo", defaultValue = "-1") int productSmallCategoryNo,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "productName", defaultValue = "") String productName,
			@RequestParam(value = "startPartsQuantity", defaultValue = "-1") int startPartsQuantity,
			@RequestParam(value = "endPartsQuantity", defaultValue = "-1") int endPartsQuantity,
			@RequestParam(value = "recommendAges[]", defaultValue="") List<String> recommendAges,
			@RequestParam(value = "startPrice", defaultValue = "-1") int startPrice,
			@RequestParam(value = "endPrice", defaultValue = "-1") int endPrice,
			@RequestParam(value = "orderByColumnName", defaultValue = "sales_quantity") String orderByColumnName) {
		// logger.info("[상품분석] 판매 상품 순위 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaProducts sc = SearchCriteriaProducts.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.productLargeCategoryNo(productLargeCategoryNo)
					.productMediumCategoryNo(productMediumCategoryNo)
					.productSmallCategoryNo(productSmallCategoryNo)
					.productCode(productCode)
					.productName(productName)
					.startPartsQuantity(startPartsQuantity)
					.endPartsQuantity(endPartsQuantity)
					.recommendAges(recommendAges)
					.startPrice(startPrice)
					.endPrice(endPrice)
					.orderByColumnName(orderByColumnName)
					.build();
			// System.out.println(sc.toString());
					
			PaginationInfo pi = statService.createProductsPaginationInfo(curPageNo, rowCountPerPage, sc, ProductsStatisticsClassification.TOTAL_SALES);
			// System.out.println(pi.toString());
			
			List<TotalSalesQuantityTop10VO> totalSalesQuantityTop10 = statService.getTotalSalesQuantityTop10(sc);
			List<TotalSalesAmountTop10VO> totalSalesAmountTop10 = statService.getTotalSalesAmountTop10(sc);
			List<TotalSalesProductsStatVO> totalSalesProductsStat = statService.getTotalSalesProductsStat(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("totalSalesQuantityTop10", totalSalesQuantityTop10);
			responseData.put("totalSalesAmountTop10", totalSalesAmountTop10);
			responseData.put("totalSalesProductsStat", totalSalesProductsStat);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 판매 분류 순위 --- */	
	@GetMapping("products/totalsales-category")
	public String productsTotalSalesByCategory() {
		// logger.info("[상품분석] 판매 분류 순위 페이지 요청");
		return "/admin/statistics/products/totalSalesByCategory";
	}
	
	@GetMapping("products/totalsales-category/data")
	public ResponseEntity<Map<String, Object>> productsTotalSalesByCategoryInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "productLargeCategoryNo", defaultValue = "-1") int productLargeCategoryNo,
			@RequestParam(value = "productMediumCategoryNo", defaultValue = "-1") int productMediumCategoryNo,
			@RequestParam(value = "productSmallCategoryNo", defaultValue = "-1") int productSmallCategoryNo,
			@RequestParam(value = "orderByColumnName", defaultValue = "sales_quantity") String orderByColumnName) {
		// logger.info("[상품분석] 판매 상품 순위 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaProducts sc = SearchCriteriaProducts.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.productLargeCategoryNo(productLargeCategoryNo)
					.productMediumCategoryNo(productMediumCategoryNo)
					.productSmallCategoryNo(productSmallCategoryNo)
					.orderByColumnName(orderByColumnName)
					.build();
			// System.out.println(sc.toString());
					
			PaginationInfo pi = statService.createProductsPaginationInfo(curPageNo, rowCountPerPage, sc, ProductsStatisticsClassification.TOTAL_SALES_BY_CATEGORY);
			// System.out.println(pi.toString());
			
			List<TotalSalesQuantityByCategoryTop10VO> totalSalesQuantityByCategoryTop10 = statService.getTotalSalesQuantityByCategoryTop10(sc);
			List<TotalSalesAmountByCategoryTop10VO> totalSalesAmountByCategoryTop10 = statService.getTotalSalesAmountByCategoryTop10(sc);
			List<TotalSalesProductsByCategoryStatVO> totalSalesProductsByCategoryStat = statService.getTotalSalesProductsByCategoryStat(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("totalSalesQuantityByCategoryTop10", totalSalesQuantityByCategoryTop10);
			responseData.put("totalSalesAmountByCategoryTop10", totalSalesAmountByCategoryTop10);
			responseData.put("totalSalesProductsByCategoryStat", totalSalesProductsByCategoryStat);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 취소/반품 순위 --- */
	@GetMapping("products/totalcanceled")
	public String productsTotalCanceled() {
		// logger.info("[상품분석] 취소/반품 순위 페이지 요청");
		return "/admin/statistics/products/totalCanceled";
	}
	
	@GetMapping("products/totalcanceled/data")
	public ResponseEntity<Map<String, Object>> productsTotalCanceledInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "productName", defaultValue = "") String productName,
			@RequestParam(value = "orderByColumnName", defaultValue = "return_quantity") String orderByColumnName) {
		// logger.info("[상품분석] 취소/반품 순위 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaProducts sc = SearchCriteriaProducts.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.productCode(productCode)
					.productName(productName)
					.orderByColumnName(orderByColumnName)
					.build();
			// System.out.println(sc.toString());
					
			PaginationInfo pi = statService.createProductsPaginationInfo(curPageNo, rowCountPerPage, sc, ProductsStatisticsClassification.TOTAL_CANCELED);
			// System.out.println(pi.toString());
			
			List<TotalCanceledQuantityTop10VO> totalCanceledQuantityTop10 = statService.getTotalCanceledQuantityTop10(sc);
			List<TotalCanceledRatioTop10VO> totalCanceledRatioTop10 = statService.getTotalCanceledRatioTop10(sc);
			List<TotalCanceledProductsStatVO> totalCanceledProductsStat = statService.getTotalCanceledProductsStat(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("totalCanceledQuantityTop10", totalCanceledQuantityTop10);
			responseData.put("totalCanceledRatioTop10", totalCanceledRatioTop10);
			responseData.put("totalCanceledProductsStat", totalCanceledProductsStat);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 장바구니 상품 분석 --- */
	@GetMapping("products/cart")
	public String cart() {
		// logger.info("[상품분석] 장바구니 상품 분석 페이지 요청");
		return "/admin/statistics/products/cart";
	}
	
	@GetMapping("products/cart/data")
	public ResponseEntity<Map<String, Object>> cartInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "productName", defaultValue = "") String productName,
			@RequestParam(value = "orderByColumnName", defaultValue = "return_quantity") String orderByColumnName) {
		// logger.info("[상품분석] 장바구니 상품 분석 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaProducts sc = SearchCriteriaProducts.builder()
					.productCode(productCode)
					.productName(productName)
					.orderByColumnName(orderByColumnName)
					.build();
			// System.out.println(sc.toString());
					
			PaginationInfo pi = statService.createProductsPaginationInfo(curPageNo, rowCountPerPage, sc, ProductsStatisticsClassification.CART);
			// System.out.println(pi.toString());
			
			List<CartMemberCountTop10VO> cartMemberCountTop10 = statService.getCartMemberCountTop10(sc);
			List<CartRegiCountTop10VO> cartRegiCountTop10 = statService.getCartRegiCountTop10(sc);
			List<CartStatVO> cartStat = statService.getCartStat(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("cartMemberCountTop10", cartMemberCountTop10);
			responseData.put("cartRegiCountTop10", cartRegiCountTop10);
			responseData.put("cartStat", cartStat);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 장바구니 상세 내역 --- */
	@GetMapping("products/cartdetail")
	public String cartDetail() {
		// logger.info("[상품분석] 장바구니 상세 내역 페이지 요청");
		return "/admin/statistics/products/cartDetail";
	}
	
	@GetMapping("products/cartdetail/data")
	public ResponseEntity<Map<String, Object>> cartDetailInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "memberId", defaultValue = "") String memberId,
			@RequestParam(value = "memberName", defaultValue = "") String memberName,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "productName", defaultValue = "") String productName,
			@RequestParam(value = "orderByColumnName", defaultValue = "return_quantity") String orderByColumnName) {
		// logger.info("[상품분석] 장바구니 상세 내역 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaProducts sc = SearchCriteriaProducts.builder()
					.memberId(memberId)
					.memberName(memberName)
					.productCode(productCode)
					.productName(productName)
					.orderByColumnName(orderByColumnName)
					.build();
			// System.out.println(sc.toString());
					
			PaginationInfo pi = statService.createProductsPaginationInfo(curPageNo, rowCountPerPage, sc, ProductsStatisticsClassification.CART_DETAIL);
			// System.out.println(pi.toString());
			
			List<TotalCartDetailStatVO> totalCartDetailStat = statService.getTotalCartDetailStat(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("totalCartDetailStat", totalCartDetailStat);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 관심 상품 분석 --- */
	@GetMapping("products/wishlist")
	public String wishList() {
		// logger.info("[상품분석] 관심 상품 분석 페이지 요청");
		return "/admin/statistics/products/wishList";
	}
	
	@GetMapping("products/wishlist/data")
	public ResponseEntity<Map<String, Object>> wishListInfo(
			@RequestParam(value = "curPageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "productName", defaultValue = "") String productName,
			@RequestParam(value = "orderByColumnName", defaultValue = "return_quantity") String orderByColumnName) {
		// logger.info("[상품분석] 관심 상품 분석 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaProducts sc = SearchCriteriaProducts.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.productCode(productCode)
					.productName(productName)
					.orderByColumnName(orderByColumnName)
					.build();
			// System.out.println(sc.toString());
					
			PaginationInfo pi = statService.createProductsPaginationInfo(curPageNo, rowCountPerPage, sc, ProductsStatisticsClassification.WISHLIST);
			// System.out.println(pi.toString());

			List<WishListMemberCountTop10VO> wishListMemberCountTop10 = statService.getWishListMemberCountTop10(sc);
			List<WishListConfirmedCountTop10VO> wishListConfirmedCountTop10 = statService.getWishListConfirmedCountTop10(sc);
			List<WishListStatVO> wishListStat = statService.getWishListStat(pi, sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("wishListMemberCountTop10", wishListMemberCountTop10);
			responseData.put("wishListConfirmedCountTop10", wishListConfirmedCountTop10);
			responseData.put("wishListStat", wishListStat);
			responseData.put("paginationInfo", pi);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* === 상품 분석 =================================================================================================================================================== */
	/* --- 요일별 분석 --- */    
	@GetMapping("members/day")
	public String statByDayname() {
		// logger.info("[고객분석] 요일별 분석 페이지 요청");
		return "/admin/statistics/members/day";
	}
	
	@GetMapping("members/day/data")
	public ResponseEntity<Map<String, Object>> statByDaynameInfo(
			@RequestParam(value = "recentWeek") int recentWeek) {
		// logger.info("[고객분석] 요일별 분석 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaMembers sc = SearchCriteriaMembers.builder()
					.recentWeek(recentWeek)
					.build();
			// System.out.println(sc.toString());

			List<StatByDaynameVO> statByDayname = statService.getStatByDayname(sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("statByDayname", statByDayname);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 시간별 분석 --- */
	@GetMapping("members/hour")
	public String statByHour() {
		// logger.info("[고객분석] 시간별 분석 페이지 요청");
		return "/admin/statistics/members/hour";
	}
	
	@GetMapping("members/hour/data")
	public ResponseEntity<Map<String, Object>> statByHourInfo(
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate) {
		// logger.info("[고객분석] 시간별 분석 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaMembers sc = SearchCriteriaMembers.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.build();
			// System.out.println(sc.toString());

			List<StatByHourVO> statByHour = statService.getStatByHour(sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("statByHour", statByHour);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 회원 등급별 분석 --- */
	@GetMapping("members/membergrade")
	public String statByMemberGrade() {
		// logger.info("[고객분석] 회원 등급별 분석 페이지 요청");
		return "/admin/statistics/members/memberGrade";
	}
	
	@GetMapping("members/membergrade/data")
	public ResponseEntity<Map<String, Object>> statByMemberGradeInfo(
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate) {
		// logger.info("[고객분석] 회원 등급별 분석 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaMembers sc = SearchCriteriaMembers.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.build();
			// System.out.println(sc.toString());

			List<StatByMemberGradeVO> statByMemberGrade = statService.getStatByMemberGrade(sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("statByMemberGrade", statByMemberGrade);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 배송 지역별 분석 --- */
	@GetMapping("members/recipient-address")
	public String statByrecipientAddress() {
		// logger.info("[고객분석] 배송 지역별 분석 페이지 요청");
		return "/admin/statistics/members/recipientAddress";
	}
	
	@GetMapping("members/recipient-address/data")
	public ResponseEntity<Map<String, Object>> statByrecipientAddressInfo(
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate) {
		// logger.info("[고객분석] 배송 지역별 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaMembers sc = SearchCriteriaMembers.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.build();
			// System.out.println(sc.toString());

			List<StatByRecipientAddressVO> statByRecipientAddress = statService.getStatByRecipientAddress(sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("statByRecipientAddress", statByRecipientAddress);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
	/* --- 적립금 분석 --- */
	@GetMapping("members/accrual")
	public String totalAccrualStat() {
		// logger.info("[고객분석] 적립금 분석 페이지 요청");
		return "/admin/statistics/members/accrual";
	}
	
	@GetMapping("members/accrual/data")
	public ResponseEntity<Map<String, Object>> totalAccrualStatInfo(
			@RequestParam(value = "startDate") Date startDate,
			@RequestParam(value = "endDate") Date endDate) {
		// logger.info("[고객분석] 적립금 분석 데이터 api 요청");

		ResponseEntity<Map<String,Object>> result = null;
		
		try {
			SearchCriteriaMembers sc = SearchCriteriaMembers.builder()
					.startDate((new Timestamp(startDate.getTime())))
					.endDate((new Timestamp(endDate.getTime())))
					.build();
			// System.out.println(sc.toString());

			List<TotalAccrualStatVO> totalAccrualStat = statService.getTotalAccrualStat(sc);
			
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("totalAccrualStat", totalAccrualStat);
			result = new ResponseEntity<Map<String,Object>>(responseData, HttpStatus.OK);
		} catch (SQLException e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "00");
			errResponse.put("errorMessage", "서버 내에 에러가 발생하였습니다.");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> errResponse = new HashMap<String, Object>();
			errResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errResponse.put("errorCode", "10");
			errResponse.put("errorMessage", "요청 값에 문제가 있습니다. [classification]");
			
			result = new ResponseEntity<Map<String,Object>>(errResponse, HttpStatus.BAD_REQUEST);
		}
		
		return result;
	}
	
}
