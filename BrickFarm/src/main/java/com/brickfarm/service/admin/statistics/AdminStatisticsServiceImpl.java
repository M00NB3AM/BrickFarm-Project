package com.brickfarm.service.admin.statistics;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.brickfarm.dao.statistics.StatisticsDAO;
import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaMembers;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaNetSales;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaProducts;
import com.brickfarm.etc.kyj.statistics.constant.ProductsStatisticsClassification;
import com.brickfarm.vo.admin.kyj.index.AdminMemberAndPointsAccrualInfoBy7DaysVO;
import com.brickfarm.vo.admin.kyj.index.AdminTotalSalesStatByPeriodVO;
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

@Service
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

	@Inject
	private StatisticsDAO statDao;
	
	private final int DAILY = 0;
	private final int WEEKLY = 1;
	private final int MONTHLY = 2;	

	/* === index 대시보드 ================================================================================================================================================ */
	// [index 대시보드] 넘겨받은 일 수 기간동안의 구매확정, 교환, 취소/반품확정된 상세주문 건들의 총 순판매액, 총 순반환액, 배송비, 포인트 변동액을 얻어와 반환한다.
	@Override
	public AdminTotalSalesStatByPeriodVO getTotalSalesStatByPeriod(int period) throws SQLException {
		AdminTotalSalesStatByPeriodVO totalSalesStatByPeriod = statDao.selectTotalSalesStatByPeriod(period);
		totalSalesStatByPeriod.setTotal_sales_amount(statDao.selectTotalSalesAmountByPeriod(period), period);
		totalSalesStatByPeriod.setTotal_sales_amount(statDao.selectTotalReturnAmountByPeriod(period), period);
		totalSalesStatByPeriod.netSalesCalc();
		return totalSalesStatByPeriod;
	}
	
	// [index 대시보드] 7일 간의 신규 회원 수, 탈퇴 회원 수, 적립금 적용액을 얻어와 반환한다.
	@Override
	public List<AdminMemberAndPointsAccrualInfoBy7DaysVO> getMemberAndPointsAccrualInfoBy7Days() throws SQLException {
		return statDao.selectMemberAndPointsAccrualInfoBy7Days();
	}
	
	/* === 통계 대시보드 ================================================================================================================================================ */
	/**
	 * @methodName : getDashboardInfo
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 7. 오후 7:22:33
	 * @description : 통계 대시보드에 표현할 정보를 가져와 반환한다. 필요한 데이터들은 다음과 같다.
	 * todayTotalNetSales : 오늘의 순매출 현황
	 * dailyNetSales : 일별 순매출
	 * weeklyNetSales : 주별 순매출
	 * monthlyNetSales : 월별 순매출
	 * dailySalesCountRank : 오늘 상품 판매량 순위 TOP 10
	 * dailyCartRank : 장바구니 담긴 순위 TOP 10
	 * weeklyCancelCountRank : 주간 취소/반품 순위 TOP 5 (수량)
	 * weeklyCancelRatioRank : 주간 취소/반품 순위 TOP 5 (반품율)
	 * weeklySalesRankPerCategory : 주간 분류별 판매순위 TOP 5
	 * preMonthSalesRankPerCategory : 전월 분류별 판매순위 TOP 5
	 */
	@Override
	public Map<String, Object> getDashboardInfo() throws SQLException {
		Map<String, Object> dashboardInfo = new HashMap<String, Object>();
		
		dashboardInfo.put("todayTotalNetSales", statDao.selectTodayTotalNetSales());
		dashboardInfo.put("dailyNetSales", statDao.selectDailyNetSales());
		dashboardInfo.put("weeklyNetSales", statDao.selectWeeklyNetSales());
		dashboardInfo.put("monthlyNetSales", statDao.selectMonthlyNetSales());
		dashboardInfo.put("dailySalesCountRank", statDao.selectDailySalesCountRank());
		dashboardInfo.put("dailyCartRank", statDao.selectDailyCartRank());
		dashboardInfo.put("weeklyCancelCountRank", statDao.selectWeeklyCancelCountRank());
		dashboardInfo.put("weeklyCancelRatioRank", statDao.selectWeeklyCancelRatioRank());
		dashboardInfo.put("weeklySalesRankPerCategory", statDao.selectWeeklySalesRankPerCategory());
		dashboardInfo.put("preMonthSalesRankPerCategory", statDao.selectPreMonthSalesRankPerCategory());		
		
		return dashboardInfo;
	}

	/* === 매출 분석 =================================================================================================================================================== */
	/**
	 * @methodName : createPaginationInfo
	 * @author : kyj
	 * @param curPageNo
	 * @param rowCountPerPage
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param curPageNo
	 * @returnValue : @param rowCountPerPage
	 * @returnValue : @param sc
	 * @returnValue : @param period
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 7. 오후 7:31:59
	 * @description : 매출분석 - 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	@Override
	public PaginationInfo createNetSalesPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteriaNetSales sc, int period)
			throws SQLException, Exception {
		PaginationInfo pi = new PaginationInfo();
		
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		switch (period) {
			case DAILY:
				pi.setTotalRowCount(statDao.selectDailyNetSalesDetailCount(sc));
				break;
			case WEEKLY:
				pi.setTotalRowCount(statDao.selectWeeklyNetSalesDetailCount(sc));
				break;
			case MONTHLY:
				pi.setTotalRowCount(statDao.selectMonthlyNetSalesDetailCount(sc));
				break;
			default:
				throw new Exception("선택된 구분에 문제가 있습니다. 선택된 기간 구분값 : " + period);
		}
		
		pi.paginationProcess();
		
		return pi;
	}

	/**
	 * @methodName : getDailyNetSalesInfo
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 7. 오후 7:33:03
	 * @description : 매출분석 - 일별매출에 표현할 정보를 가져와 반환한다. 
	 */
	@Override
	public List<AdminDailyNetSalesVO> getDailyNetSalesInfo(PaginationInfo pi, SearchCriteriaNetSales sc)
			throws SQLException {
		return statDao.selectDailyNetSalesDetail(pi, sc);
	}
	
	/**
	 * @methodName : getCurrentWeekOfYear
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 2:57:03
	 * @description : 매출분석 - 주별매출에 표현할 주차 정보를 계산하기 위한 기준 주차를 가져와 반환한다. 
	 */
	@Override
	public int getCurrentWeekOfYear() throws SQLException {
		return statDao.selectCurrentWeekOfYear();
	}

	/**
	 * @methodName : getWeeklyNetSalesInfo
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오전 2:38:11
	 * @description : 매출분석 - 주별매출에 표현할 정보를 가져와 반환한다. 
	 */
	@Override
	public List<AdminWeeklyNetSalesVO> getWeeklyNetSalesInfo(PaginationInfo pi, SearchCriteriaNetSales sc)
			throws SQLException {
		return statDao.selectWeeklyNetSalesDetail(pi, sc);
	}

	/**
	 * @methodName : getMonthlyNetSalesInfo
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 4:19:56
	 * @description : 매출분석 - 월별매출에 표현할 정보를 가져와 반환한다. 
	 */
	@Override
	public List<AdminMonthlyNetSalesVO> getMonthlyNetSalesInfo(PaginationInfo pi, SearchCriteriaNetSales sc)
			throws SQLException {
		return statDao.selectMonthlyNetSalesDetail(pi, sc);
	}
	
	/* === 상품 분석 =================================================================================================================================================== */
	/**
	 * @methodName : getSortedRecommendAge
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 16. 오후 9:23:40
	 * @description : 상품분석 페이지에서 검색조건:추천연령을 그리기 위한 정보를 가져와 반환한다.
	 */
	@Override
	public List<String> getSortedRecommendAge() throws SQLException {
		return statDao.selectSortedRecommendAge();
	}

	/**
	 * @methodName : createProductsPaginationInfo
	 * @author : kyj
	 * @param curPageNo
	 * @param rowCountPerPage
	 * @param sc
	 * @param classification
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 * @returnValue : @param curPageNo
	 * @returnValue : @param rowCountPerPage
	 * @returnValue : @param sc
	 * @returnValue : @param classification
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 15. 오후 7:56:50
	 * @description : 상품분석 - 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	@Override
	public PaginationInfo createProductsPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteriaProducts sc,
			int classification) throws SQLException, Exception {
		PaginationInfo pi = new PaginationInfo();
		
		pi.setCurPageNo(curPageNo);
		pi.setRowCountPerPage(rowCountPerPage);
		switch (classification) {
		case ProductsStatisticsClassification.TOTAL_SALES:
			pi.setTotalRowCount(statDao.selectTotalSalesProductsStatCount(sc));
			break;
		case ProductsStatisticsClassification.TOTAL_SALES_BY_CATEGORY:
			pi.setTotalRowCount(statDao.selectTotalSalesProductsByCategoryStatCount(sc));
			break;
		case ProductsStatisticsClassification.TOTAL_CANCELED:
			pi.setTotalRowCount(statDao.selectTotalCanceledProductsStatCount(sc));
			break;
		case ProductsStatisticsClassification.CART:
			pi.setTotalRowCount(statDao.selectCartStatCount(sc));
			break;
		case ProductsStatisticsClassification.CART_DETAIL:
			pi.setTotalRowCount(statDao.selectTotalCartDetailStatCount(sc));
			break;
		case ProductsStatisticsClassification.WISHLIST:
			pi.setTotalRowCount(statDao.selectWishListStatCount(sc));
			break;
		default:
			throw new Exception("선택된 구분에 문제가 있습니다. 선택된 구분값 : " + classification);
		}

		pi.paginationProcess();
		
		return pi;
	}

	/* --- 판매 상품 순위 --- */
	/**
	 * @methodName : getTotalSalesQuantityTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:56:47
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalSalesQuantityTop10VO> getTotalSalesQuantityTop10(SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectTotalSalesQuantityTop10(sc);
	}

	/**
	 * @methodName : getTotalSalesAmountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:56:44
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalSalesAmountTop10VO> getTotalSalesAmountTop10(SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectTotalSalesAmountTop10(sc);
	}

	/**
	 * @methodName : getTotalSalesProductsStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:56:42
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매상품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalSalesProductsStatVO> getTotalSalesProductsStat(PaginationInfo pi, SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectTotalSalesProductsStat(pi, sc);
	}

	/* --- 판매 분류 순위 --- */
	/**
	 * @methodName : getTotalSalesQuantityByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:56:45
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalSalesQuantityByCategoryTop10VO> getTotalSalesQuantityByCategoryTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectTotalSalesQuantityByCategoryTop10(sc);
	}

	/**
	 * @methodName : getTotalSalesAmountByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:56:43
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalSalesAmountByCategoryTop10VO> getTotalSalesAmountByCategoryTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectTotalSalesAmountByCategoryTop10(sc);
	}

	/**
	 * @methodName : getTotalSalesProductsByCategoryStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:56:41
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매상품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalSalesProductsByCategoryStatVO> getTotalSalesProductsByCategoryStat(PaginationInfo pi,
			SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectTotalSalesProductsByCategoryStat(pi, sc);
	}

	/* --- 취소/반품 순위 --- */
	/**
	 * @methodName : getTotalCanceledQuantityTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:57:13
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalCanceledQuantityTop10VO> getTotalCanceledQuantityTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectTotalCanceledQuantityTop10(sc);
	}

	/**
	 * @methodName : getTotalCanceledRatioTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:57:21
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 비율 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalCanceledRatioTop10VO> getTotalCanceledRatioTop10(SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectTotalCanceledRatioTop10(sc);
	}

	/**
	 * @methodName : getTotalCanceledProductsStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:57:27
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalCanceledProductsStatVO> getTotalCanceledProductsStat(PaginationInfo pi, SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectTotalCanceledProductsStat(pi, sc);
	}

	/* --- 장바구니 상품 분석 --- */
	/**
	 * @methodName : getCartMemberCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:29
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<CartMemberCountTop10VO> getCartMemberCountTop10(SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectCartMemberCountTop10(sc);
	}

	/**
	 * @methodName : getCartRegiCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:26
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<CartRegiCountTop10VO> getCartRegiCountTop10(SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectCartRegiCountTop10(sc);
	}

	/**
	 * @methodName : getCartStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:23
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 장바구니 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<CartStatVO> getCartStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectCartStat(pi, sc);
	}

	/* --- 장바구니 상세 내역 --- */
	/**
	 * @methodName : getTotalCartDetailStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:21
	 * @description : 상품분석 - 장바구니 상세 내역 페이지의 장바구니 상세 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalCartDetailStatVO> getTotalCartDetailStat(PaginationInfo pi, SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectTotalCartDetailStat(pi, sc);
	}

	/* --- 관심 상품 분석 --- */
	/**
	 * @methodName : getWishListMemberCountTop10
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:18
	 * @description : 상품분석 - 관심 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<WishListMemberCountTop10VO> getWishListMemberCountTop10(SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectWishListMemberCountTop10(sc);
	}

	/**
	 * @methodName : getWishListConfirmedCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:16
	 * @description : 상품분석 - 관심 상품 분석 페이지의 결제수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<WishListConfirmedCountTop10VO> getWishListConfirmedCountTop10(SearchCriteriaProducts sc)
			throws SQLException {
		return statDao.selectWishListConfirmedCountTop10(sc);
	}

	/**
	 * @methodName : getWishListStat
	 * @author : kyj
	 * @param pi
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param pi
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:46:14
	 * @description : 상품분석 - 관심 상품 분석 페이지의 관심 상품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<WishListStatVO> getWishListStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException {
		return statDao.selectWishListStat(pi, sc);
	}
	/* === 고객 분석 =================================================================================================================================================== */
	/* --- 요일별 분석 --- */
	/**
	 * @methodName : getStatByDayname
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:26:16
	 * @description : 고객분석 - 요일별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<StatByDaynameVO> getStatByDayname(SearchCriteriaMembers sc) throws SQLException {
		return statDao.selectStatByDayname(sc);
	}

	/* --- 시간별 분석 --- */
	/**
	 * @methodName : getStatByHour
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:26:14
	 * @description : 고객분석 - 시간별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<StatByHourVO> getStatByHour(SearchCriteriaMembers sc) throws SQLException {
		return statDao.selectStatByHour(sc);
	}

	/* --- 회원 등급별 분석 --- */
	/**
	 * @methodName : getStatByMemberGrade
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:26:11
	 * @description : 고객분석 - 회원 등급별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<StatByMemberGradeVO> getStatByMemberGrade(SearchCriteriaMembers sc) throws SQLException {
		return statDao.selectStatByMemberGrade(sc);
	}

	/* --- 배송 지역별 분석 --- */
	/**
	 * @methodName : getStatByRecipientAddress
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:26:08
	 * @description : 고객분석 - 배송 지역별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<StatByRecipientAddressVO> getStatByRecipientAddress(SearchCriteriaMembers sc) throws SQLException {
		return statDao.selectStatByRecipientAddress(sc);
	}

	/* --- 적립금 분석 --- */
	/**
	 * @methodName : getTotalAccrualStat
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 23. 오후 8:26:04
	 * @description : 고객분석 - 적립금 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	@Override
	public List<TotalAccrualStatVO> getTotalAccrualStat(SearchCriteriaMembers sc) throws SQLException {
		return statDao.selectTotalAccrualStat(sc);
	}
}
