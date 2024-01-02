<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <!-- 
	<link href="/resources/admin/vendor/jqvmap/css/jqvmap.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="/resources/admin/vendor/chartist/css/chartist.min.css" />
	<link href="/resources/admin/vendor/bootstrap-select/dist/css/bootstrap-select.min.css" rel="stylesheet" />
	<link href="/resources/admin/css/style.css" rel="stylesheet" />
	<link href="https://cdn.lineicons.com/2.0/LineIcons.css" rel="stylesheet" />
	-->
	
    <!-- Common Stylesheet -->
    <link href="/resources/admin/css/kyj/common.css" rel="stylesheet" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Toastr Js ======-->
    <script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
    
    <!--====== Toastr call functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/util-toastr.js"></script>

    <!--====== chartJs init functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/util-chartjs.js"></script>
    
    <!--====== dashboard.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/dashboard.js"></script>

    <script>
      $(function () {
        getDashboardInfo();
        
        $(".counter").counterUp({
            delay: 30,
            time: 3000,
          });
      });
    </script>
  </head>
  <body>
    <div id="preloader">
      <img id="lego" src="/resources/admin/images/lego.gif" alt="" />
    </div>
    <div id="main-wrapper">
      <jsp:include page="/WEB-INF/views/admin/header.jsp"></jsp:include>
      <!--**********************************
            Content body start
        ***********************************-->
      <div class="content-body">
        <!-- row -->
        <div class="container-fluid">
          <div class="form-head d-flex mb-3 align-items-start">
            <div class="mr-auto d-none d-lg-block">
              <h2 class="text-black font-w600 mb-0">통계 대시보드</h2>
              <p class="mb-0">오늘 하루에 대한 전체적인 통계 현황을 확인할 수 있습니다.</p>
            </div>
          </div>
          <div class="row">
            <div class="col-xl-4 col-xxl-4 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-stats-up"></i>
                    </span>
                    <div class="media-body">
                      <h2 class="fs-28 font-w600">
                      	￦<span class="counter ml-0" id="dailyNetSales">
                        </span>
                      </h2>
                      <p class="mb-0">오늘의 순매출 현황</p>
                      <small>적립금을 제한 금액</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-4 col-xxl-4 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-stats-down"></i>
                    </span>
                    <div class="media-body">
                      <h2 class="fs-28 font-w600">
                        ￦<span class="counter ml-0" id="dailyRefundAmount">
                        </span>
                      </h2>
                      <p class="mb-0">취소/반품 금액</p>
                      <small>오늘 취소/반품이 확정된 건</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-4 col-xxl-4 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-credit-card"></i>
                    </span>
                    <div class="media-body">
                      <h2 class="fs-28 font-w600">
                        <span class="counter ml-0" id="dailyPaymentCount">
                        </span>건
                      </h2>
                      <p class="mb-0">결제건수</p>
                      <small>오늘 구매확정이 된 건</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-4 col-xxl-4 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-back-left"></i>
                    </span>
                    <div class="media-body">
                      <h2 class="fs-28 font-w600">
                        <span class="counter ml-0" id="dailyCancelExchangeCount">
                        </span>건
                      </h2>
                      <p class="mb-0">취소/반품/교환 수</p>
                      <small>오늘 취소/반품/교환이 확정된 건</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-4 col-xxl-4 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-truck"></i>
                    </span>
                    <div class="media-body">
                      <h2 class="fs-28 font-w600">
                        <span class="counter ml-0" id="dailyDeliveryWaitingCount">
                        </span>건
                      </h2>
                      <p class="mb-0">배송처리</p>
                      <small>오늘 배송대기 처리가 된 건</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-4 col-xxl-4 col-lg-6 col-md-6 col-sm-6">
              <div class="widget-stat card">
                <div class="card-body p-4">
                  <div class="media ai-icon">
                    <span class="mr-3 bgl-primary text-primary">
                      <i class="ti-package"></i>
                    </span>
                    <div class="media-body">
                      <h2 class="fs-28 font-w600">
                        <span class="counter ml-0" id="dailyDeliveryCompleteCount">
                        </span>건
                      </h2>
                      <p class="mb-0">배송완료</p>
                      <small>오늘 배송완료 처리가 된 건</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-xl-12 col-xxl-12 col-lg-12 col-md-12">
              <div class="card">
                <div class="card-header border-0 pb-0 d-sm-flex d-block">
                  <div>
                    <h4 class="card-title mb-1">기간별 순매출</h4>
                    <small class="mb-0">일별/주별/월별 순매출 요약</small>
                  </div>
                  <div class="card-action card-tabs mt-3 mt-sm-0">
                    <ul class="nav nav-tabs" role="tablist">
                      <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#daily" role="tab"> 일별 </a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#weekly" role="tab"> 주별 </a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#monthly" role="tab"> 월별 </a>
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="card-body">
                  <div class="tab-content active" id="daily">
                    <div class="row">
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="recentPeriodAverageDaily"></span></h2>
                          <p class="fs-16 mb-0">최근 7일 평균</p>
                        </div>
                      </div>
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="lastPeriodDaily"></span></h2>
                          <p class="fs-16 mb-0">어제</p>
                        </div>
                      </div>
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="thisPeriodDaily"></span></h2>
                          <p class="fs-16 mb-0">오늘</p>
                        </div>
                      </div>
                    </div>

                    <div>
                      <canvas id="chartNetSalesDaily"></canvas>
                    </div>
                  </div>

                  <div class="tab-content" id="weekly">
                    <div class="row">
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="recentPeriodAverageWeekly"></span></h2>
                          <p class="fs-16 mb-0">최근 4주 평균</p>
                        </div>
                      </div>
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="lastPeriodWeekly"></span></h2>
                          <p class="fs-16 mb-0">지난주</p>
                        </div>
                      </div>
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="thisPeriodWeekly"></span></h2>
                          <p class="fs-16 mb-0">이번주</p>
                        </div>
                      </div>
                    </div>

                    <div>
                      <canvas id="chartNetSalesWeekly"></canvas>
                    </div>
                  </div>

                  <div class="tab-content" id="monthly">
                    <div class="row">
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="recentPeriodAverageMonthly"></span></h2>
                          <p class="fs-16 mb-0">최근 3개월 평균</p>
                        </div>
                      </div>
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="lastPeriodMonthly"></span></h2>
                          <p class="fs-16 mb-0">지난달</p>
                        </div>
                      </div>
                      <div class="col-sm-4 mb-4">
                        <div class="border px-3 py-3 rounded-xl">
                          <h2 class="fs-24 font-w600">￦<span class="counter" id="thisPeriodMonthly"></span></h2>
                          <p class="fs-16 mb-0">이번달</p>
                        </div>
                      </div>
                    </div>

                    <div>
                      <canvas id="chartNetSalesMonthly"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
              <div class="card">
                <div class="card-header border-0 pb-0 d-sm-flex d-block">
                  <div>
                    <h4 class="card-title mb-1">오늘 상품 판매량 순위 TOP 10</h4>
                    <small class="mb-0">오늘 구매확정된 상품들의 판매량 총 합</small>
                  </div>
                </div>
                <div class="card-body">
                  <canvas id="chartDailySalesCountRank"></canvas>
                </div>
              </div>
            </div>

            <div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
              <div class="card">
                <div class="card-header border-0 pb-0 d-sm-flex d-block">
                  <div>
                    <h4 class="card-title mb-1">장바구니 담긴 순위 TOP 10</h4>
                    <small class="mb-0">장바구니에 담긴 상품 수량의 총 합</small>
                  </div>
                </div>
                <div class="card-body">
                  <canvas id="chartDailyCartRank" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>

            <!-- Stalked Bar Chart 로 변형 고려 -->
            <div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
              <div class="card">
                <div class="card-header border-0 pb-0 d-sm-flex d-block">
                  <div>
                    <h4 class="card-title mb-1">주간 취소/반품 순위 TOP 5</h4>
                    <small class="mb-0">이번주의 일요일~토요일 사이</small>
                  </div>
                  <div class="card-action card-tabs mt-3 mt-sm-0">
                    <ul class="nav nav-tabs" role="tablist">
                      <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#weeklyCancelCount" role="tab"> 수량 </a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#weeklyCancelRatio" role="tab"> 반품율 </a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="card-body">
                  <div class="tab-content active" id="weeklyCancelCount">
                    <div>
                      <canvas id="chartWeeklyCancelCountRank" class="style-doughnut-chart"></canvas>
                    </div>
                  </div>
                  <div class="tab-content" id="weeklyCancelRatio">
                    <div>
                      <canvas id="chartWeeklyCancelRatioRank" class="style-doughnut-chart"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
              <div class="card">
                <div class="card-header border-0 pb-0 d-sm-flex d-block">
                  <div>
                    <h4 class="card-title mb-1">분류별 판매순위 TOP 5</h4>
                    <small class="mb-0">주간/전월 상품분류별 판매순위</small>
                  </div>
                  <div class="card-action card-tabs mt-3 mt-sm-0">
                    <ul class="nav nav-tabs" role="tablist">
                      <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#weeklyPerCategory" role="tab"> 주간 </a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#preMonthPerCategory" role="tab"> 전월 </a>
                      </li>
                    </ul>
                  </div>
                </div>

                <div class="card-body">
                  <div class="tab-content active" id="weeklyPerCategory">
                    <canvas id="chartWeeklySalesRankPerCategory" class="style-doughnut-chart"></canvas>
                  </div>
                  <div class="tab-content" id="preMonthPerCategory">
                    <canvas id="chartPreMonthSalesRankPerCategory" class="style-doughnut-chart"></canvas>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--**********************************
            Content body end
        ***********************************-->
    </div>
  </body>
</html>
