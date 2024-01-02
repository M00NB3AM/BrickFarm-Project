package com.brickfarm.dao.statistics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminDailyTotalNetSalesVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminNetSalesByPeriodVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminSalesRankPerCategoryVO;
import com.brickfarm.vo.admin.kyj.statistics.dashboard.AdminWeeklyCancelCountRankVO;
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

@Repository
public class StatisticsDAOImpl implements StatisticsDAO {
	
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.StatisticsMapper.";

	/* === index 대시보드 ============================================================================================================================================== */
	// [index 대시보드] 넘겨받은 일 수 기간 동안 구매확정/교환된 상세주문 건의 순수 상품 판매액합을 데이터베이스에서 가져와 반환한다.
	@Override
	public int selectTotalSalesAmountByPeriod(int period) throws SQLException {
		return ses.selectOne(ns + "selectTotalSalesAmountByPeriod", period);
	}
	
	// [index 대시보드] 넘겨받은 일 수 기간 동안 취소/반품확정된 상세주문 건의 순수 상품 반환액합을 데이터베이스에서 가져와 반환한다.
	@Override
	public int selectTotalReturnAmountByPeriod(int period) throws SQLException {
		return ses.selectOne(ns + "selectTotalReturnAmountByPeriod", period);
	}
	
	// [index 대시보드] 넘겨받은 일 수 기간 동안 구매확정, 교환, 취소/반품확정된 상세주문들의 주문번호로 기록된 배송비합, 포인트 변동액합을 데이터베이스에서 가져와 반환한다.
	@Override
	public AdminTotalSalesStatByPeriodVO selectTotalSalesStatByPeriod(int period) throws SQLException {
		return ses.selectOne(ns + "selectPostMoneyAndUsagePointByPeriod", period);
	}
	
	// [index 대시보드] 7일 간의 신규 회원 수, 탈퇴 회원 수, 적립금 적용액을 데이터베이스에서 가져와 반환한다.
	@Override
	public List<AdminMemberAndPointsAccrualInfoBy7DaysVO> selectMemberAndPointsAccrualInfoBy7Days() throws SQLException {
		return ses.selectList(ns + "selectMemberAndPointsAccrualInfoBy7Days");
	}
	
	/* === 게시판 대시보드 ============================================================================================================================================== */
	// 게시판 대시보드에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	@Override
	public AdminBoardsStatVO selectBoardsStat() throws SQLException {
		return ses.selectOne(ns + "boardsStat");
	}

	// 게시판 대시보드의 문의 현황 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	@Override
	public List<AdminInquiriesGraphDataVO> selectInquiriesGraphData() throws SQLException {
		return ses.selectList(ns + "inquiriesGraphData");
	}

	/* === 통계 대시보드 ================================================================================================================================================ */
	/**
	 * @methodName : selectTodayTotalNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:01:41
	 * @description : 오늘의 순매출 종합 현황에 대한 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public AdminDailyTotalNetSalesVO selectTodayTotalNetSales() throws SQLException {
		return ses.selectOne(ns + "todayTotalNetSales");
	}

	/**
	 * @methodName : selectDailyNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:01:43
	 * @description : 일별 순매출 간략 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public AdminNetSalesByPeriodVO selectDailyNetSales() throws SQLException {
		return ses.selectOne(ns + "dailyNetSales");
	}

	/**
	 * @methodName : selectWeeklyNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:01:49
	 * @description : 주별 순매출 간략 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public AdminNetSalesByPeriodVO selectWeeklyNetSales() throws SQLException {
		return ses.selectOne(ns + "weeklyNetSales");
	}

	/**
	 * @methodName : selectMonthlyNetSales
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:01:51
	 * @description : 월별 순매출 간략 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public AdminNetSalesByPeriodVO selectMonthlyNetSales() throws SQLException {
		return ses.selectOne(ns + "monthlyNetSales");
	}

	/**
	 * @methodName : selectDailySalesCountRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:01:53
	 * @description : 오늘 판매량 순위 TOP 10 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminDailySalesCountRankVO> selectDailySalesCountRank() throws SQLException {
		return ses.selectList(ns + "dailySalesCountRank");
	}

	/**
	 * @methodName : selectDailyCartRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:01:57
	 * @description : 장바구니 담긴 순위 TOP 10 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminDailyCartRankVO> selectDailyCartRank() throws SQLException {
		return ses.selectList(ns + "dailyCartRank");
	}

	/**
	 * @methodName : selectWeeklyCancelCountRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:02:01
	 * @description : 주간 취소/반품 순위 TOP 5 (수량) 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminWeeklyCancelCountRankVO> selectWeeklyCancelCountRank() throws SQLException {
		return ses.selectList(ns + "weeklyCancelCountRank");
	}

	/**
	 * @methodName : selectWeeklyCancelRatioRank
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:02:06
	 * @description : 주간 취소/반품 순위 TOP 5 (반품율) 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminWeeklyCancelRatioRankVO> selectWeeklyCancelRatioRank() throws SQLException {
		return ses.selectList(ns + "weeklyCancelRatioRank");
	}

	/**
	 * @methodName : selectWeeklySalesRankPerCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:02:09
	 * @description : 주간 분류별 판매순위 TOP 5 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminSalesRankPerCategoryVO> selectWeeklySalesRankPerCategory() throws SQLException {
		return ses.selectList(ns + "weeklySalesRankPerCategory");
	}

	/**
	 * @methodName : selectPreMonthSalesRankPerCategory
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 3. 오후 12:17:48
	 * @description : 전월 분류별 판매순위 TOP 5 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminSalesRankPerCategoryVO> selectPreMonthSalesRankPerCategory() throws SQLException {
		return ses.selectList(ns + "preMonthSalesRankPerCategory");
	}

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
	 * @date : 2023. 11. 7. 오후 7:18:02
	 * @description : 매출분석 - 일별 매출 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectDailyNetSalesDetailCount(SearchCriteriaNetSales sc) throws SQLException {
		return ses.selectOne(ns + "dailyNetSalesDetailCount", sc);
	}

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
	 * @date : 2023. 11. 7. 오후 7:18:06
	 * @description : 매출분석 - 일별 매출 페이지에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminDailyNetSalesVO> selectDailyNetSalesDetail(PaginationInfo pi, SearchCriteriaNetSales sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("isCheckedUsePoint", sc.isCheckedUsePoint());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "dailyNetSalesDetail", params);
	}

	/**
	 * @methodName : selectCurrentWeekOfYear
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 2:54:43
	 * @description : 매출분석 - 주별 매출 페이지에 기간 정보 표현을 위해 기준이 될 현재 날짜의 주차를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectCurrentWeekOfYear() throws SQLException {
		return ses.selectOne(ns + "currentWeekOfYear");
	}

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
	@Override
	public int selectWeeklyNetSalesDetailCount(SearchCriteriaNetSales sc) throws SQLException {
		return ses.selectOne(ns + "weeklyNetSalesDetailCount", sc);
	}

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
	@Override
	public List<AdminWeeklyNetSalesVO> selectWeeklyNetSalesDetail(PaginationInfo pi, SearchCriteriaNetSales sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("recentWeek", sc.getRecentWeek());
		params.put("isCheckedUsePoint", sc.isCheckedUsePoint());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "weeklyNetSalesDetail", params);
	}

	/**
	 * @methodName : selectMonthlyNetSalesDetailCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 4:15:25
	 * @description : 매출분석 - 월별 매출 페이지에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectMonthlyNetSalesDetailCount(SearchCriteriaNetSales sc) throws SQLException {
		return ses.selectOne(ns + "monthlyNetSalesDetailCount", sc);
	}

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
	 * @date : 2023. 11. 13. 오후 4:15:38
	 * @description : 매출분석 - 월별 매출 페이지에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<AdminMonthlyNetSalesVO> selectMonthlyNetSalesDetail(PaginationInfo pi, SearchCriteriaNetSales sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("isCheckedUsePoint", sc.isCheckedUsePoint());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "monthlyNetSalesDetail", params);
	}

	/* === 상품 분석 =================================================================================================================================================== */
	/**
	 * @methodName : selectSortedRecommendAge
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 16. 오후 9:21:55
	 * @description : 상품분석 페이지에서 검색조건:추천연령을 그리기 위한 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<String> selectSortedRecommendAge() throws SQLException {
		return ses.selectList(ns + "selectSortedRecommendAge");
	}

	/* --- 판매 상품 순위 --- */
	/**
	 * @methodName : selectTotalSalesQuantityTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:15:07
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalSalesQuantityTop10VO> selectTotalSalesQuantityTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "totalSalesQuantityTop10", sc);
	}

	/**
	 * @methodName : selectTotalSalesAmountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:15:10
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalSalesAmountTop10VO> selectTotalSalesAmountTop10(SearchCriteriaProducts sc)
			throws SQLException {		
		return ses.selectList(ns + "totalSalesAmountTop10", sc);
	}
	
	/**
	 * @methodName : selectTotalSalesProductsStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:51:10
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매상품 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectTotalSalesProductsStatCount(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectOne(ns + "totalSalesProductsStatCount", sc);
	}

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
	 * @date : 2023. 11. 15. 오후 7:15:12
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매상품 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalSalesProductsStatVO> selectTotalSalesProductsStat(PaginationInfo pi, SearchCriteriaProducts sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("productLargeCategoryNo", sc.getProductLargeCategoryNo());
		params.put("productMediumCategoryNo", sc.getProductMediumCategoryNo());
		params.put("productSmallCategoryNo", sc.getProductSmallCategoryNo());
		params.put("productCode", sc.getProductCode());
		params.put("productName", sc.getProductName());
		params.put("recommendAges", sc.getRecommendAges());
		params.put("startPartsQuantity", sc.getStartPartsQuantity());
		params.put("endPartsQuantity", sc.getEndPartsQuantity());
		params.put("startPrice", sc.getStartPrice());
		params.put("endPrice", sc.getEndPrice());
		params.put("orderByColumnName", sc.getOrderByColumnName());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "totalSalesProductsStat", params);
	}

	/* --- 판매 분류 순위 --- */
	/**
	 * @methodName : selectTotalSalesQuantityByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:45:26
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalSalesQuantityByCategoryTop10VO> selectTotalSalesQuantityByCategoryTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "totalSalesQuantityByCategoryTop10", sc);
	}

	/**
	 * @methodName : selectTotalSalesAmountByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:45:24
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalSalesAmountByCategoryTop10VO> selectTotalSalesAmountByCategoryTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "totalSalesAmountByCategoryTop10", sc);
	}

	/**
	 * @methodName : selectTotalSalesProductsByCategoryStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:45:22
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매분류 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectTotalSalesProductsByCategoryStatCount(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectOne(ns + "totalSalesProductsByCategoryStatCount", sc);
	}

	/**
	 * @methodName : selectTotalSalesProductsByCategoryStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:45:21
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매분류 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalSalesProductsByCategoryStatVO> selectTotalSalesProductsByCategoryStat(PaginationInfo pi,
			SearchCriteriaProducts sc) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("productLargeCategoryNo", sc.getProductLargeCategoryNo());
		params.put("productMediumCategoryNo", sc.getProductMediumCategoryNo());
		params.put("productSmallCategoryNo", sc.getProductSmallCategoryNo());
		params.put("orderByColumnName", sc.getOrderByColumnName());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "totalSalesProductsByCategoryStat", params);
	}

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
	 * @date : 2023. 11. 19. 오후 10:51:59
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalCanceledQuantityTop10VO> selectTotalCanceledQuantityTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "totalCanceledQuantityTop10", sc);
	}

	/**
	 * @methodName : selectTotalCanceledRatioTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:51:57
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 비율 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalCanceledRatioTop10VO> selectTotalCanceledRatioTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "totalCanceledRatioTop10", sc);
	}

	/**
	 * @methodName : selectTotalCanceledProductsStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:51:55
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectTotalCanceledProductsStatCount(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectOne(ns + "totalCanceledProductsStatCount", sc);
	}

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
	 * @date : 2023. 11. 19. 오후 10:51:53
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalCanceledProductsStatVO> selectTotalCanceledProductsStat(PaginationInfo pi,
			SearchCriteriaProducts sc) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("productCode", sc.getProductCode());
		params.put("productName", sc.getProductName());
		params.put("orderByColumnName", sc.getOrderByColumnName());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "totalCanceledProductsStat", params);
	}

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
	 * @date : 2023. 11. 20. 오전 12:40:13
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<CartMemberCountTop10VO> selectCartMemberCountTop10(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectList(ns + "cartMemberCountTop10", sc);
	}

	/**
	 * @methodName : selectCartRegiCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:40:11
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<CartRegiCountTop10VO> selectCartRegiCountTop10(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectList(ns + "cartRegiCountTop10", sc);
	}

	/**
	 * @methodName : selectCartStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:40:10
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 장바구니 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectCartStatCount(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectOne(ns + "cartStatCount", sc);
	}

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
	 * @date : 2023. 11. 20. 오전 12:40:07
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 장바구니 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<CartStatVO> selectCartStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productCode", sc.getProductCode());
		params.put("productName", sc.getProductName());
		params.put("orderByColumnName", sc.getOrderByColumnName());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "cartStat", params);
	}

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
	 * @date : 2023. 11. 20. 오전 12:39:46
	 * @description : 상품분석 - 장바구니 상세 내역 페이지의 장바구니 상세 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectTotalCartDetailStatCount(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectOne(ns + "totalCartDetailStatCount", sc);
	}

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
	 * @date : 2023. 11. 20. 오전 12:39:44
	 * @description : 상품분석 - 장바구니 상세 내역 페이지의 장바구니 상세 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<TotalCartDetailStatVO> selectTotalCartDetailStat(PaginationInfo pi, SearchCriteriaProducts sc)
			throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", sc.getMemberId());
		params.put("memberName", sc.getMemberName());
		params.put("productCode", sc.getProductCode());
		params.put("productName", sc.getProductName());
		params.put("orderByColumnName", sc.getOrderByColumnName());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "totalCartDetailStat", params);
	}

	/* --- 관심 상품 분석 --- */
	/**
	 * @methodName : selectWishListMemberCountTop10
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:39:12
	 * @description : 상품분석 - 관심 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<WishListMemberCountTop10VO> selectWishListMemberCountTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "wishListMemberCountTop10", sc);
	}

	/**
	 * @methodName : selectWishListConfirmedCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:39:10
	 * @description : 상품분석 - 관심 상품 분석 페이지의 결제수량 TOP 10 그래프에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<WishListConfirmedCountTop10VO> selectWishListConfirmedCountTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return ses.selectList(ns + "wishListConfirmedCountTop10", sc);
	}

	/**
	 * @methodName : selectWishListStatCount
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:39:08
	 * @description : 상품분석 - 관심 상품 분석 페이지의 관심 상품 순위 내역에 표현할 정보의 페이지네이션을 위해 조건에 해당하는 전체 row 개수를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public int selectWishListStatCount(SearchCriteriaProducts sc) throws SQLException {
		return ses.selectOne(ns + "wishListStatCount", sc);
	}

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
	 * @date : 2023. 11. 20. 오전 12:39:06
	 * @description : 상품분석 - 관심 상품 분석 페이지의 관심 상품 순위 내역에 표현할 정보를 데이터베이스로부터 얻어와 반환한다.
	 */
	@Override
	public List<WishListStatVO> selectWishListStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", sc.getStartDate());
		params.put("endDate", sc.getEndDate());
		params.put("productCode", sc.getProductCode());
		params.put("productName", sc.getProductName());
		params.put("orderByColumnName", sc.getOrderByColumnName());
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		
		return ses.selectList(ns + "wishListStat", params);
	}

	/* === 고객 분석 =================================================================================================================================================== */
	/* --- 요일별 분석 --- */
	@Override
	public List<StatByDaynameVO> selectStatByDayname(SearchCriteriaMembers sc) throws SQLException {
		return ses.selectList(ns + "statByDayname", sc);
	}

	/* --- 시간별 분석 --- */
	@Override
	public List<StatByHourVO> selectStatByHour(SearchCriteriaMembers sc) throws SQLException {
		return ses.selectList(ns + "statByHour", sc);
	}

	/* --- 회원 등급별 분석 --- */
	@Override
	public List<StatByMemberGradeVO> selectStatByMemberGrade(SearchCriteriaMembers sc) throws SQLException {
		return ses.selectList(ns + "statByMemberGrade", sc);
	}

	/* --- 배송 지역별 분석 --- */
	@Override
	public List<StatByRecipientAddressVO> selectStatByRecipientAddress(SearchCriteriaMembers sc) throws SQLException {
		return ses.selectList(ns + "statByRecipientAddress", sc);
	}

	/* --- 적립금 분석 --- */
	@Override
	public List<TotalAccrualStatVO> selectTotalAccrualStat(SearchCriteriaMembers sc) throws SQLException {
		return ses.selectList(ns + "totalAccrualStat", sc);
	}
}