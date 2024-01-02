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
    
    <!--====== cart.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/statistics/products/cart.js"></script>

    <script>
      $(function () {
        initOrderByColumnName('regi_member_count');
        printEmptyTable(undefined, 8);
        printOrderBySelect();
        
        $('#inputProductValue').on('change', function (e) {
       	  if ($('#inputProductKey').val() == 'productCode') {
          	setParams('productCode', e.target.value);
          	setParams('productName', null);
       	  } else {  
          	setParams('productCode', null);  
          	setParams('productName', e.target.value);
       	  }
        });

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
              <h2 class="text-black font-w600 mb-0">장바구니 상품 분석</h2>
              <p class="mb-0">검색 조건과 일치하는 장바구니에 등록된 상품들을 회원수/수량 순으로 정렬하여 볼 수 있습니다.</p>
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

                  <div class="col-12 mt-sm-1 mt-md-3">
                    <button type="button" class="btn btn-primary col-12" onclick="search();">검색</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-6">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">회원수 TOP 10</h4>
                </div>
                <div class="card-body">
                  <canvas id="cartMemberCountTop10" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>
            
            <div class="col-6">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">수량 TOP 10</h4>
                </div>
                <div class="card-body">
                  <canvas id="cartRegiCountTop10" class="style-doughnut-chart"></canvas>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">장바구니상품 순위 내역</h4>
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
                          <th class="wd-6">판매가</th>
                          <th class="wd-2">수량</th>
                          <th class="wd-6">오늘결제수량</th>
                          <th class="wd-2">재고</th>
                          <th class="wd-5">등록회원수</th>
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
