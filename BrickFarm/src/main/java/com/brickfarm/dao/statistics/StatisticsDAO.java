package com.brickfarm.dao.statistics;

import java.sql.SQLException;
import java.util.List;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaMembers;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaNetSales;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaProducts;
import com.brickfarm.vo.admin.kyj.board.AdminBoardsStatVO;
import com.brickfarm.vo.admin.kyj.board.AdminInquiriesGraphDataVO;
import com.brickfarm.vo.admin.kyj.index.AdminMemberAndPointsAccrualInfoBy7DaysVO;
import com.brickfarm.vo.admin.kyj.index.AdminTotalSalesStatByPeriodVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminDailyCartRankVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminDailySalesCountRankVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminWeeklyCancelRatioRankVO;
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
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminDailyTotalNetSalesVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminNetSalesByPeriodVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminSalesRankPerCategoryVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminWeeklyCancelCountRankVO;

/**
 * @packageName : com.brickfarm.dao.statistics 
 * @fileName :  StatisticsDAO.java
 * @author : kyj
 * @date : 2023. 11. 13.
 * @description : 
 */
/**
 * @packageName : com.brickfarm.dao.statistics 
 * @fileName :  StatisticsDAO.java
 * @author : kyj
 * @date : 2023. 11. 13.
 * @description : 
 */
/**
 * @packageName : com.brickfarm.dao.statistics 
 * @fileName :  StatisticsDAO.java
 * @author : kyj
 * @date : 2023. 11. 13.
 * @description : 
 */
/**
 * @packageName : com.brickfarm.dao.statistics 
 * @fileName :  StatisticsDAO.java
 * @author : kyj
 * @date : 2023. 11. 13.
 * @description : 
 */
/**
 * @packageName : com.brickfarm.dao.statistics 
 * @fileName :  StatisticsDAO.java
 * @author : kyj
 * @date : 2023. 11. 17.
 * @description : 
 */
public interface StatisticsDAO {
	/* === index 대시보드 ============================================================================================================================================== */
	// [index 대시보드] 넘겨받은 일 수 기간 동안 구매확정/교환된 상세주문 건의 순수 상품 판매액합을 데이터베이스에서 가져와 반환한다.
	public int selectTotalSalesAmountByPeriod(int period) throws SQLException;
	
	// [index 대시보드] 넘겨받은 일 수 기간 동안 취소/반품확정된 상세주문 건의 순수 상품 반환액합을 데이터베이스에서 가져와 반환한다.
	public int selectTotalReturnAmountByPeriod(int period) throws SQLException;
	
	// [index 대시보드] 넘겨받은 일 수 기간 동안 구매확정, 교환, 취소/반품확정된 상세주문들의 주문번호로 기록된 배송비합, 포인트 변동액합을 데이터베이스에서 가져와 반환한다. 
	public AdminTotalSalesStatByPeriodVO selectTotalSalesStatByPeriod(int period) throws SQLException;
	
	// [index 대시보드] 7일 간의 신규 회원 수, 탈퇴 회원 수, 적립금 적용액을 데이터베이스에서 가져와 반환한다.
	public List<AdminMemberAndPointsAccrualInfoBy7DaysVO> selectMemberAndPointsAccrualInfoBy7Days() throws SQLException;
	
	/* === 게시판 대시보드 ============================================================================================================================================== */
	// 게시판 대시보드에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	public AdminBoardsStatVO selectBoardsStat() throws SQLException;
	
	// 게시판 대시보드의 문의 현황 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	public List<AdminInquiriesGraphDataVO> selectInquiriesGraphData() throws SQLException;
	
	/* === 통계 대시보드 ================================================================================================================================================ */
	/**
	 * @methodName : selectTodayTotalNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:11:50
	 * @description : 오늘의 순매출 종합 현황에 대한 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public AdminDailyTotalNetSalesVO selectTodayTotalNetSales() throws SQLException;
	
	/**
	 * @methodName : selectDailyNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:06
	 * @description : 일별 순매출 간략 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public AdminNetSalesByPeriodVO selectDailyNetSales() throws SQLException;
	
	/**
	 * @methodName : selectWeeklyNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:10
	 * @description : 주별 순매출 간략 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public AdminNetSalesByPeriodVO selectWeeklyNetSales() throws SQLException;
	
	/**
	 * @methodName : selectMonthlyNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:14
	 * @description : 월별 순매출 간략 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public AdminNetSalesByPeriodVO selectMonthlyNetSales() throws SQLException;
	
	/**
	 * @methodName : selectDailySalesCountRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:16
	 * @description : 오늘 판매량 순위 TOP 10 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminDailySalesCountRankVO> selectDailySalesCountRank() throws SQLException;
	
	/**
	 * @methodName : selectDailyCartRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:18
	 * @description : 장바구니 담긴 순위 TOP 10 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminDailyCartRankVO> selectDailyCartRank() throws SQLException;
	
	/**
	 * @methodName : selectWeeklyCancelRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:21
	 * @description : 주간 취소/반품 순위 TOP 5 (수량) 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminWeeklyCancelCountRankVO> selectWeeklyCancelCountRank() throws SQLException;
	
	/**
	 * @methodName : selectWeeklyCancelRatioRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:41:25
	 * @description : 주간 취소/반품 순위 TOP 5 (반품율) 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminWeeklyCancelRatioRankVO> selectWeeklyCancelRatioRank() throws SQLException;
	
	/**
	 * @methodName : selectWeeklySalesRankPerCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:22
	 * @description : 주간 분류별 판매순위 TOP 5 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminSalesRankPerCategoryVO> selectWeeklySalesRankPerCategory() throws SQLException;
	
	/**
	 * @methodName : selectPreMonthSalesRankPerCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오전 11:15:24
	 * @description : 전월 분류별 판매순위 TOP 5 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다. 
	 */
	public List<AdminSalesRankPerCategoryVO> selectPreMonthSalesRankPerCategory() throws SQLException;
	
	/* === 매출 분석 =================================================================================================================================================== */
	/**
	 * @methodName : selectDailyNetSalesDetailCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 7. 오후 7:10:09
	 * @description : 매출분석 - 일별 매출 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectDailyNetSalesDetailCount(SearchCriteriaNetSales sc) throws SQLException;
	
	/**
	 * @methodName : selectDailyNetSalesDetail
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 7. 오후 7:08:40
	 * @description : 매출분석 - 일별 매출 페이지에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminDailyNetSalesVO> selectDailyNetSalesDetail(PaginationInfo pi, SearchCriteriaNetSales sc) throws SQLException;

	/**
	 * @methodName : selectCurrentWeekOfYear
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 2:53:08
	 * @description : 매출분석 - 주별 매출 페이지에 기간 정보 표현을 위해 기준이 될 현재 날짜의 주차를 데이터베이스로부터 얻어와 반환한다. 
	 */
	public int selectCurrentWeekOfYear() throws SQLException;
	
	/**
	 * @methodName : selectWeeklyNetSalesDetailCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오전 12:49:47
	 * @description : 매출분석 - 주별 매출 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectWeeklyNetSalesDetailCount(SearchCriteriaNetSales sc) throws SQLException;

	/**
	 * @methodName : selectWeelkyNetSalesDetail
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오전 12:49:52
	 * @description : 매출분석 - 주별 매출 페이지에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminWeeklyNetSalesVO> selectWeeklyNetSalesDetail(PaginationInfo pi, SearchCriteriaNetSales sc) throws SQLException;
	
	/**
	 * @methodName : selectMonthlyNetSalesDetailCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 3:59:33
	 * @description : 매출분석 - 월별 매출 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectMonthlyNetSalesDetailCount(SearchCriteriaNetSales sc) throws SQLException;

	/**
	 * @methodName : selectMonthlyNetSalesDetail
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 4:00:03
	 * @description : 매출분석 - 월별 매출 페이지에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<AdminMonthlyNetSalesVO> selectMonthlyNetSalesDetail(PaginationInfo pi, SearchCriteriaNetSales sc) throws SQLException;


	/* === 상품 분석 =================================================================================================================================================== */
	/**
	 * @methodName : selectSortedRecommendAge
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 16. 오후 9:20:44
	 * @description : 상품분석 페이지에서 검색조건:추천연령을 그리기 위한 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<String> selectSortedRecommendAge() throws SQLException;

	/* --- 판매 상품 순위 --- */
	/**
	 * @methodName : selectTotalSalesQuantityTop10
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 4:20:26
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalSalesQuantityTop10VO> selectTotalSalesQuantityTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectTotalSalesAmountTop10
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 4:21:04
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalSalesAmountTop10VO> selectTotalSalesAmountTop10(SearchCriteriaProducts sc) throws SQLException;

	/**
	 * @methodName : selectTotalSalesProductsStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:49:38
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매상품 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectTotalSalesProductsStatCount(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectTotalSalesProductsStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 4:21:01
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매상품 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalSalesProductsStatVO> selectTotalSalesProductsStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;

	/* --- 판매 분류 순위 --- */
	/**
	 * @methodName : totalSalesQuantityByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:38:59
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalSalesQuantityByCategoryTop10VO> selectTotalSalesQuantityByCategoryTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : totalSalesAmountByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:39:06
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalSalesAmountByCategoryTop10VO> selectTotalSalesAmountByCategoryTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : totalSalesProductsByCategoryStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:39:04
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매분류 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectTotalSalesProductsByCategoryStatCount(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : totalSalesProductsByCategoryStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:39:02
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매분류 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalSalesProductsByCategoryStatVO> selectTotalSalesProductsByCategoryStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
	/* --- 취소/반품 순위 --- */
	/**
	 * @methodName : selectTotalCanceledQuantityTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:44:45
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalCanceledQuantityTop10VO> selectTotalCanceledQuantityTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectTotalCanceledRatioTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:44:44
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 비율 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalCanceledRatioTop10VO> selectTotalCanceledRatioTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectTotalCanceledProductsStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:44:42
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectTotalCanceledProductsStatCount(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectTotalCanceledProductsStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:44:39
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalCanceledProductsStatVO> selectTotalCanceledProductsStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;

	/* --- 장바구니 상품 분석 --- */
	/**
	 * @methodName : selectCartMemberCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:34:49
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<CartMemberCountTop10VO> selectCartMemberCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectCartRegiCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:34:51
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<CartRegiCountTop10VO> selectCartRegiCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectCartStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:34:53
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 장바구니 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectCartStatCount(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectCartStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:34:55
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 장바구니 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<CartStatVO> selectCartStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;

	/* --- 장바구니 상세 내역 --- */
	/**
	 * @methodName : selectTotalCartDetailStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:34:58
	 * @description : 상품분석 - 장바구니 상세 내역 페이지의 장바구니 상세 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectTotalCartDetailStatCount(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectTotalCartDetailStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:35:00
	 * @description : 상품분석 - 장바구니 상세 내역 페이지의 장바구니 상세 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<TotalCartDetailStatVO> selectTotalCartDetailStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
	/* --- 관심 상품 분석 --- */
	/**
	 * @methodName : selectWishListMemberCountTop10
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:35:02
	 * @description : 상품분석 - 관심 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<WishListMemberCountTop10VO> selectWishListMemberCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectWishListConfirmedCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:35:04
	 * @description : 상품분석 - 관심 상품 분석 페이지의 결제수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<WishListConfirmedCountTop10VO> selectWishListConfirmedCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectWishListStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:35:06
	 * @description : 상품분석 - 관심 상품 분석 페이지의 관심 상품 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	public int selectWishListStatCount(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : selectWishListStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:35:08
	 * @description : 상품분석 - 관심 상품 분석 페이지의 관심 상품 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	public List<WishListStatVO> selectWishListStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
	/* === 고객 분석 =================================================================================================================================================== */
	/* --- 요일별 분석 --- */
	public List<StatByDaynameVO> selectStatByDayname(SearchCriteriaMembers sc) throws SQLException;
	
	/* --- 시간별 분석 --- */
	public List<StatByHourVO> selectStatByHour(SearchCriteriaMembers sc) throws SQLException;
	
	/* --- 회원 등급별 분석 --- */
	public List<StatByMemberGradeVO> selectStatByMemberGrade(SearchCriteriaMembers sc) throws SQLException;
	
	/* --- 배송 지역별 분석 --- */
	public List<StatByRecipientAddressVO> selectStatByRecipientAddress(SearchCriteriaMembers sc) throws SQLException;
	
	/* --- 적립금 분석 --- */
	public List<TotalAccrualStatVO> selectTotalAccrualStat(SearchCriteriaMembers sc) throws SQLException;
}
