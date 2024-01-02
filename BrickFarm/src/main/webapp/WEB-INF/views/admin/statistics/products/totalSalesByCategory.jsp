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

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Toastr Js ======-->
    <script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
    
    <!--====== Toastr call functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/util-toastr.js"></script>

    <!--====== chartJs init functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/util-chartjs.js"></script>
    
    <!--====== 상품 분석 공용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/products/products.js"></script>
    
    <!--====== totalSalesByCategory.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/products/totalSalesByCategory.js"></script>

    <script>
      $(function () {
        initDateRangePicker();
        initDateParams();
        initOrderByColumnName('sales_quantity');
        printEmptyTable(undefined, 8);
        printOrderBySelect();
        getProductCategory();

        $('#dateRangePicker').on('apply.daterangepicker', dateRangePickerChangeHandler);

        $('#rowCountPerPage').on('change', function (e) {
          selectRowCountPerPage(e.target.value);
        });
        
        $("#orderByColumnName").on('change', function (e) {
          setParams('orderByColumnName', e.target.value);
          search();
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
              <h2 class="text-black font-w600 mb-0">판매 분류 순위</h2>
              <p class="mb-0">지정한 기간 내에 조건과 일치하는 상품들을 판매 수량/합계 순으로 정렬하여 볼 수 있습니다.</p>
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
                  
                  <div class="col-12 mb-3">
                    <div class="form-group row flex-wrapper">
                      <div class="col-sm-3 col-form-label">상품분류</div>
                      <div class="col-sm-3 col-md-2" id="wrapperProductLargeCategoryNo">
                        <div class="form-group mb-0">
                          <select class="form-control" id="productLargeCategoryNo"></select>
                        </div>
                      </div>
                      <div class="col-sm-3 col-md-2" id="wrapperProductMediumCategoryNo">
                        <div class="form-group mb-0">
                          <select class="form-control" id="productMediumCategoryNo"></select>
                        </div>
                      </div>
                      <div class="col-sm-3 col-md-2" id="wrapperProductSmallCategoryNo">
                        <div class="form-group mb-0">
                          <select class="form-control" id="productSmallCategoryNo"></select>
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

            <div class="col-6">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">판매 수량 TOP 10</h4>
                </div>
                <div class="card-body">
                  <canvas id="totalSalesQuantityByCategoryTop10" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>
            
            <div class="col-6">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">판매 합계 TOP 10</h4>
                </div>
                <div class="card-body">
                  <canvas id="totalSalesAmountByCategoryTop10" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">판매 분류 순위 내역</h4>
                </div>
                <div class="card-body">
                  <div class=" flex-wrapper flex-direction-row flex-jc-sb">
                    <div class="col-2 mb-3">
                      <select class="form-control" id="rowCountPerPage">
                        <option value="10">10개씩 보기</option>
                        <option value="15">15개씩 보기</option>
                        <option value="20">20개씩 보기</option>
                        <option value="30">30개씩 보기</option>
                      </select>
                    </div>
                  
                    <div class="col-2 mb-3">
                      <select class="form-control" id="orderByColumnName"></select>
                    </div>
                  </div>

                  <div class="table-responsive">
                    <table class="table header-border table-responsive-sm table-search-result">
                      <thead id="resultHeader">
                        <tr>
                          <th class="wd-2">순위</th>
                          <th class="wd-3">대분류</th>
                          <th class="wd-3">중분류</th>
                          <th class="wd-3">소분류</th>
                          <th class="wd-4">주분수량</th>
                          <th class="wd-4">취소/반품수량</th>
                          <th class="wd-4">판매수량</th>
                          <th class="wd-6">판매합계</th>
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
