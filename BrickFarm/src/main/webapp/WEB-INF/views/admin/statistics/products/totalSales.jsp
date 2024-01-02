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
    
    <!--====== totalSales.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/products/totalSales.js"></script>

    <script>
      $(function () {
        initDateRangePicker();
        initDateParams();
        initOrderByColumnName('sales_quantity');
        printEmptyTable(undefined, 9);
        printOrderBySelect();
        getProductCategory();
        getProductRecommendAge();

        $('#dateRangePicker').on('apply.daterangepicker', dateRangePickerChangeHandler);

        $('#rowCountPerPage').on('change', function (e) {
          selectRowCountPerPage(e.target.value);
        });
        
        $('#inputProductValue').on('change', function (e) {
       	  if ($('#inputProductKey').val() == 'productCode') {
          	setParams('productCode', e.target.value);
          	setParams('productName', null);
       	  } else {  
          	setParams('productCode', null);  
          	setParams('productName', e.target.value);
       	  }
        });
        
        $('#startPartsQuantity').on('change', function (e) {
          setParams('startPartsQuantity', e.target.value);
        });
        $('#endPartsQuantity').on('change', function (e) {
          setParams('endPartsQuantity', e.target.value);
        });
        
        $('.recommendAges').on('change', function (e) {
          let checkedList = [];
          $('.recommendAges').each(function(index, item) {
        	  if($(item).is(':checked')) {
        		  checkedList.push($(item).val());
        	  }
          });
          setParams('recommendAges', checkedList);
        });
        
        $('#startPrice').on('change', function (e) {
          setParams('startPrice', e.target.value);
        });
        $('#endPrice').on('change', function (e) {
          setParams('endPrice', e.target.value);
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
              <h2 class="text-black font-w600 mb-0">판매 상품 순위</h2>
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
                    
                  <div class="col-12 mb-3">
                    <div class="form-group row flex-wrapper">
                      <div class="col-sm-3 col-form-label">상품</div>
                      <div class="col-sm-3 col-md-2">
                        <div class="form-group mb-0">
                          <select class="form-control" id="inputProductKey">
                          	<option value="productName">상품명</option>
                          	<option value="productCode">상품코드</option>
                          </select>
                        </div>
                      </div>
                      <div class="col-sm-4 col-md-3">
                        <div class="form-group mb-0">
                          <input class="form-control" type="text" id="inputProductValue" />
                        </div>
                      </div>
                    </div>
                  </div>
                    
                  <div class="col-12 mb-3">
                    <div class="form-group row flex-wrapper">
                      <div class="col-sm-3 col-form-label">부품수</div>
                      <div class="col-sm-3 col-md-2">
                        <div class="form-group mb-0">
                          <input class="form-control form-control-sm" type="number" id="startPartsQuantity" />
                        </div>
                      </div>
                      <div class="col-sm-1 flex-wrapper flex-jc-center">~</div>
                      <div class="col-sm-3 col-md-2">
                        <div class="form-group mb-0">
                          <input class="form-control form-control-sm" type="number" id="endPartsQuantity" />
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-12 mb-3">
                    <div class="form-group row">
                      <div class="col-sm-3">추천 연령</div>
                        <div class="col-sm-9 flex-wrapper flex-direction-row flex-wrap gap-col-4 gap-row-1" id="recommendAgeGroup">
                      </div>
                    </div>
                  </div>
                  
                  <div class="col-12 mb-3">
                    <div class="form-group row flex-wrapper">
                      <div class="col-sm-3 col-form-label">판매가</div>
                      <div class="col-sm-3 col-md-2">
                        <div class="form-group mb-0">
                          <input class="form-control form-control-sm" type="number" id="startPrice" />
                        </div>
                      </div>
                      <div class="col-sm-1 flex-wrapper flex-jc-center">~</div>
                      <div class="col-sm-3 col-md-2">
                        <div class="form-group mb-0">
                          <input class="form-control form-control-sm" type="number" id="endPrice" />
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
                  <canvas id="totalSalesQuantityTop10" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>
            
            <div class="col-6">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">판매 합계 TOP 10</h4>
                </div>
                <div class="card-body">
                  <canvas id="totalSalesAmountTop10" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">판매 상품 순위 내역</h4>
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
                          <th class="wd-4">상품코드</th>
                          <th class="wd-3">상품명</th>
                          <th class="wd-3">주문수</th>
                          <th class="wd-6">판매가</th>
                          <th class="wd-2">재고</th>
                          <th class="wd-4">환불수량</th>
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
