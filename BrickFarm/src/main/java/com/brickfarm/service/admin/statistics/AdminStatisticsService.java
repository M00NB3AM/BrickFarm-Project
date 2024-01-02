package com.brickfarm.service.admin.statistics;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaMembers;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaNetSales;
import com.brickfarm.etc.kyj.statistics.SearchCriteriaProducts;
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

public interface AdminStatisticsService {
	/* === index 대시보드 ================================================================================================================================================ */
	// [index 대시보드] 넘겨받은 일 수 기간동안의 구매확정, 교환, 취소/반품확정된 상세주문 건들의 총 순판매액, 총 순반환액, 배송비, 포인트 변동액을 얻어와 반환한다.
	public AdminTotalSalesStatByPeriodVO getTotalSalesStatByPeriod(int period) throws SQLException;
	
	// [index 대시보드] 7일 간의 신규 회원 수, 탈퇴 회원 수, 적립금 적용액을 얻어와 반환한다.
	public List<AdminMemberAndPointsAccrualInfoBy7DaysVO> getMemberAndPointsAccrualInfoBy7Days() throws SQLException;

	/* === 통계 대시보드 ================================================================================================================================================ */
	/**
	 * @methodName : getDashboardInfo
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 7. 오후 7:17:46
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
	public Map<String, Object> getDashboardInfo() throws SQLException;
	
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
	 * @date : 2023. 11. 7. 오후 7:31:46
	 * @description : 매출분석 - 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	public PaginationInfo createNetSalesPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteriaNetSales sc, int period) throws SQLException, Exception;

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
	 * @date : 2023. 11. 7. 오후 7:32:35
	 * @description : 매출분석 - 일별매출에 표현할 정보를 가져와 반환한다. 
	 */
	public List<AdminDailyNetSalesVO> getDailyNetSalesInfo(PaginationInfo pi, SearchCriteriaNetSales sc) throws SQLException;
	
	/**
	 * @methodName : getCurrentWeekOfYear
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 13. 오후 2:55:53
	 * @description : 매출분석 - 주별매출에 표현할 주차 정보를 계산하기 위한 기준 주차를 가져와 반환한다. 
	 */
	public int getCurrentWeekOfYear() throws SQLException;
	
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
	 * @date : 2023. 11. 13. 오전 2:38:38
	 * @description : 매출분석 - 주별매출에 표현할 정보를 가져와 반환한다. 
	 */
	public List<AdminWeeklyNetSalesVO> getWeeklyNetSalesInfo(PaginationInfo pi, SearchCriteriaNetSales sc) throws SQLException;
	
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
	 * @date : 2023. 11. 13. 오후 4:16:42
	 * @description : 매출분석 - 월별매출에 표현할 정보를 가져와 반환한다. 
	 */
	public List<AdminMonthlyNetSalesVO> getMonthlyNetSalesInfo(PaginationInfo pi, SearchCriteriaNetSales sc) throws SQLException;
	
	/* === 상품 분석 =================================================================================================================================================== */
	/**
	 * @methodName : getSortedRecommendAge
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 16. 오후 9:22:47
	 * @description : 상품분석 페이지에서 검색조건:추천연령을 그리기 위한 정보를 가져와 반환한다.
	 */
	public List<String> getSortedRecommendAge() throws SQLException;
	
	/**
	 * @methodName : createProductsPaginationInfo
	 * @author : kyj
	 * @param curPageNo
	 * @param rowCountPerPage
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param curPageNo
	 * @returnValue : @param rowCountPerPage
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:21:43
	 * @description : 상품분석 - 현재 페이지 번호와 페이지 당 표시할 행 개수 정보, 검색 정보를 통해 페이지네이션에 필요한 정보 객체를 만들어 반환한다.
	 */
	public PaginationInfo createProductsPaginationInfo(int curPageNo, int rowCountPerPage, SearchCriteriaProducts sc, int classification) throws SQLException, Exception;
	
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
	 * @date : 2023. 11. 15. 오후 7:16:21
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalSalesQuantityTop10VO> getTotalSalesQuantityTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : getTotalSalesAmountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 15. 오후 7:16:26
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalSalesAmountTop10VO> getTotalSalesAmountTop10(SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 15. 오후 7:16:29
	 * @description : 상품분석 - 판매 상품 순위 페이지의 판매상품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalSalesProductsStatVO> getTotalSalesProductsStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 17. 오후 3:50:28
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalSalesQuantityByCategoryTop10VO> getTotalSalesQuantityByCategoryTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : getTotalSalesAmountByCategoryTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 17. 오후 3:50:37
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매합계 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalSalesAmountByCategoryTop10VO> getTotalSalesAmountByCategoryTop10(SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 17. 오후 3:50:33
	 * @description : 상품분석 - 판매 분류 순위 페이지의 판매상품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalSalesProductsByCategoryStatVO> getTotalSalesProductsByCategoryStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 19. 오후 10:56:06
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalCanceledQuantityTop10VO> getTotalCanceledQuantityTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : getTotalCanceledRatioTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 19. 오후 10:56:04
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 비율 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalCanceledRatioTop10VO> getTotalCanceledRatioTop10(SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 19. 오후 10:56:02
	 * @description : 상품분석 - 취소/반품 순위 페이지의 취소/반품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalCanceledProductsStatVO> getTotalCanceledProductsStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 20. 오전 12:41:16
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<CartMemberCountTop10VO> getCartMemberCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : getCartRegiCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:41:14
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<CartRegiCountTop10VO> getCartRegiCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 20. 오전 12:41:11
	 * @description : 상품분석 - 장바구니 상품 분석 페이지의 장바구니 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<CartStatVO> getCartStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 20. 오전 12:41:09
	 * @description : 상품분석 - 장바구니 상세 내역 페이지의 장바구니 상세 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalCartDetailStatVO> getTotalCartDetailStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;

	/* --- 관심 상품 분석 --- */
	/**
	 * @methodName : getWishListMemberCountTop10
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:41:07
	 * @description : 상품분석 - 관심 상품 분석 페이지의 회원수 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<WishListMemberCountTop10VO> getWishListMemberCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
	/**
	 * @methodName : getWishListConfirmedCountTop10
	 * @author : kyj
	 * @param sc
	 * @return
	 * @throws SQLException
	 * @returnValue : @param sc
	 * @returnValue : @return
	 * @returnValue : @throws SQLException
	 * @date : 2023. 11. 20. 오전 12:41:05
	 * @description : 상품분석 - 관심 상품 분석 페이지의 결제수량 TOP 10 그래프에 표현할 정보를 가져와 반환한다.
	 */
	public List<WishListConfirmedCountTop10VO> getWishListConfirmedCountTop10(SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 20. 오전 12:41:03
	 * @description : 상품분석 - 관심 상품 분석 페이지의 관심 상품 순위 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<WishListStatVO> getWishListStat(PaginationInfo pi, SearchCriteriaProducts sc) throws SQLException;
	
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
	 * @date : 2023. 11. 23. 오후 8:17:03
	 * @description : 고객분석 - 요일별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<StatByDaynameVO> getStatByDayname(SearchCriteriaMembers sc) throws SQLException;
	
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
	 * @date : 2023. 11. 23. 오후 8:17:13
	 * @description : 고객분석 - 시간별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<StatByHourVO> getStatByHour(SearchCriteriaMembers sc) throws SQLException;
	
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
	 * @date : 2023. 11. 23. 오후 8:17:11
	 * @description : 고객분석 - 회원 등급별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<StatByMemberGradeVO> getStatByMemberGrade(SearchCriteriaMembers sc) throws SQLException;
	
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
	 * @date : 2023. 11. 23. 오후 8:17:09
	 * @description : 고객분석 - 배송 지역별 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<StatByRecipientAddressVO> getStatByRecipientAddress(SearchCriteriaMembers sc) throws SQLException;
	
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
	 * @date : 2023. 11. 23. 오후 8:17:07
	 * @description : 고객분석 - 적립금 분석 페이지의 통계 그래프 및 내역에 표현할 정보를 가져와 반환한다.
	 */
	public List<TotalAccrualStatVO> getTotalAccrualStat(SearchCriteriaMembers sc) throws SQLException;
}
