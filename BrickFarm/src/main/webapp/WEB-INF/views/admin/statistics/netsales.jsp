<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />

    <!-- Common Stylesheet -->
    <link href="/resources/admin/css/kyj/common.css" rel="stylesheet" />

    <!-- Daterangepicker -->
    <link href="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet" />
    
    <!-- Toastr Stylesheet -->
    <link href="/resources/admin/vendor/toastr/css/toastr.min.css" rel="stylesheet" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Toastr Js ======-->
    <script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
    
    <!--====== Toastr call functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/util-toastr.js"></script>

    <!--====== chartJs init functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/util-chartjs.js"></script>
    
    <!--====== netsales.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/netSales.js"></script>

    <script>
      $(function () {
        initDateRangePicker();
        initDateParams();
        printEmptyTable();
        printRecentWeekSelect();
        printYearPickerkSelect();
        printMonthPickerSelect();

        $('#dateRangePicker').on('apply.daterangepicker', dateRangePickerChangeHandler);

        $('#checkUsePoint').on('change', function (e) {
          setParams('isCheckedUsePoint', e.target.checked);
        });

        $('#recentWeek').on('change', function (e) {
          setParams('recentWeek', e.target.value);
        });

        $('#startYearPicker').on('change', function (e) {
          selectStartYearAndMonth();
        });
        $('#startMonthPicker').on('change', function (e) {
          selectStartYearAndMonth();
        });
        $('#endYearPicker').on('change', function (e) {
          selectEndYearAndMonth();
        });
        $('#endMonthPicker').on('change', function (e) {
          selectEndYearAndMonth();
        });

        $('#rowCountPerPage').on('change', function (e) {
          selectRowCountPerPage(e.target.value);
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
              <h2 class="text-black font-w600 mb-0">매출 분석</h2>
              <p class="mb-0">조건을 설정하여 매출에 대한 분석이 가능합니다.</p>
            </div>
          </div>
          <div class="row">
            <div class="col-xl-12 col-lg-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">검색 조건</h4>
                </div>
                <div class="card-body">
                  <div class="col-12 mb-3">
                    <ul class="nav nav-pills light">
                      <li class="nav-item">
                        <a
                          href="#navpills-1"
                          onclick="selectPeriod(PERIOD.DAILY);"
                          class="nav-link active"
                          data-toggle="tab"
                          >일별 매출</a
                        >
                      </li>
                      <li class="nav-item">
                        <a href="#navpills-2" onclick="selectPeriod(PERIOD.WEEKLY);" class="nav-link" data-toggle="tab"
                          >주별 매출</a
                        >
                      </li>
                      <li class="nav-item">
                        <a href="#navpills-3" onclick="selectPeriod(PERIOD.MONTHLY);" class="nav-link" data-toggle="tab"
                          >월별 매출</a
                        >
                      </li>
                    </ul>
                  </div>
                  <div class="col-12 mb-3">
                    <div id="navpills-1" class="tab-content tab-pane active">
                      <div class="form-group row flex-wrapper">
                        <div class="col-sm-3 col-form-label">기간</div>
                        <div class="col-sm-9">
                          <input
                            id="dateRangePicker"
                            class="form-control input-daterange-datepicker col-lg-4"
                            type="text"
                            name="daterange"
                            value="01/01/2015 - 01/31/2015"
                          />
                        </div>
                      </div>
                    </div>
                    <div id="navpills-2" class="tab-content tab-pane">
                      <div class="form-group row flex-wrapper">
                        <div class="col-sm-3 col-form-label">기간</div>
                        <div>최근</div>
                        <div class="col-sm-2">
                          <div class="form-group mb-0">
                            <select class="form-control" id="recentWeek"></select>
                          </div>
                        </div>
                        <div>주</div>
                      </div>
                    </div>
                    <div id="navpills-3" class="tab-content tab-pane">
                      <div class="form-group row flex-wrapper">
                        <div class="col-sm-3 col-form-label">기간</div>
                        <div>
                          <div class="form-group mb-0">
                            <select class="form-control" id="startYearPicker"></select>
                          </div>
                        </div>
                        <div class="wd-1 mx-1">년</div>
                        <div>
                          <div class="form-group mb-0">
                            <select class="form-control" id="startMonthPicker"></select>
                          </div>
                        </div>
                        <div class="wd-1 mx-1">월 ~</div>
                        <div>
                          <div class="form-group mb-0">
                            <select class="form-control" id="endYearPicker"></select>
                          </div>
                        </div>
                        <div class="wd-1 mx-1">년</div>
                        <div>
                          <div class="form-group mb-0">
                            <select class="form-control" id="endMonthPicker"></select>
                          </div>
                        </div>
                        <div class="wd-1 mx-1">월</div>
                      </div>
                    </div>
                  </div>

                  <div class="col-12 mb-3">
                    <div class="form-group row">
                      <div class="col-sm-3">적립금 포함 여부</div>
                      <div class="col-sm-9">
                        <div class="form-check">
                          <input id="checkUsePoint" class="form-check-input" type="checkbox" />
                          <label class="form-check-label" for="checkUsePoint">적립금 포함</label>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-12 mt-sm-1 mt-md-3">
                    <button type="button" class="btn btn-primary col-12" onclick="search();">검색</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">통계 그래프</h4>
                </div>
                <div class="card-body">
                  <canvas id="netSalesChart"></canvas>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">일별 매출내역</h4>
                </div>
                <div class="card-body">
                  <div class="col-2 mb-3">
                    <select class="form-control" id="rowCountPerPage">
                      <option value="10">10개씩 보기</option>
                      <option value="15">15개씩 보기</option>
                      <option value="20">20개씩 보기</option>
                      <option value="30">30개씩 보기</option>
                    </select>
                  </div>

                  <div class="table-responsive">
                    <table class="table header-border table-responsive-sm table-search-result">
                      <thead id="resultHeader">
                        <tr class="table-header-group">
                          <th></th>
                          <th colspan="8">구매 확정</th>
                          <th colspan="4">취소/반품 확정</th>
                          <th></th>
                        </tr>

                        <tr>
                          <th class="wd-5">일자</th>
                          <th class="wd-5">확정일자</th>
                          <th class="wd-3">주문수</th>
                          <th class="wd-3">상품수</th>
                          <th class="wd-6">상품구매금액</th>
                          <th class="wd-5">할인된금액</th>
                          <th class="wd-5">사용적립금</th>
                          <th class="wd-4">결제합계</th>
                          <th class="wd-5">확정일자</th>
                          <th class="wd-3">주문수</th>
                          <th class="wd-3">상품수</th>
                          <th class="wd-4">금액합계</th>
                          <th class="wd-3">순매출</th>
                        </tr>
                      </thead>
                      <tbody id="searchResult"></tbody>
                    </table>
                  </div>

                  <div class="pagination_wrapper" id="pagination"></div>
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

    <!--**********************************
        Scripts
    ***********************************-->
    <!-- Daterangepicker -->
    <script src="/resources/admin/vendor/moment/moment.min.js"></script>
    <script src="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>
  </body>
</html>
