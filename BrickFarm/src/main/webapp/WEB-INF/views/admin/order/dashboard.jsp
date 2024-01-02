<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <!-- 제이쿼리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <link href="/resources/admin/vendor/jqvmap/css/jqvmap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/resources/admin/vendor/chartist/css/chartist.min.css" />
    <!-- CSS -->
    <link href="/resources/admin/vendor/bootstrap-select/dist/css/bootstrap-select.min.css" rel="stylesheet" />
    <link href="/resources/admin/css/style.css" rel="stylesheet" />

    <link href="https://cdn.lineicons.com/2.0/LineIcons.css" rel="stylesheet" />
    <title>주문 대시보드</title>

    <script></script>

    <style>
      #lego {
        position: fixed;
        width: 150px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 999;
      }
    </style>
  </head>
  <body>
    <div id="preloader">
      <img id="lego" src="/resources/admin/images/lego.gif" alt="" />
    </div>
    <jsp:include page="./../header.jsp"></jsp:include>
    
    <c:choose>
    	<c:when test="${empty errorResponse}">
    	
    <div class="content-body">
      <!-- row -->
      <div class="container-fluid">
        <div class="form-head d-flex mb-3 align-items-start">
          <div class="mr-auto d-none d-lg-block row page-titles" >
            <h2 class="text-black font-w600 mb-10">대시보드</h2>
            <div class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
              <ol class="breadcrumb">
                <li class="breadcrumb-item active"><a href="javascript:void(0)">주문</a></li>
                <li class="breadcrumb-item"><a href="./dashboard">대시보드</a></li>
              </ol>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
            <div class="widget-stat card">
              <div class="card-body p-4">
                <div class="media ai-icon">
                  <span class="mr-3 bgl-primary text-primary">
                    <i class="fi fi-rr-box-open-full"></i>
                  </span>
                  <div class="media-body">
                    <h3 class="mb-0 text-black"><span class="counter ml-0">${dashboardCount}</span></h3>
                    <p class="mb-0">총 주문건수(당일)</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
            <div class="widget-stat card">
              <div class="card-body p-4">
                <div class="media ai-icon">
                  <span class="mr-3 bgl-primary text-primary">
                    <i class="fi fi-rr-dollar"></i>
                  </span>
                  <div class="media-body">
                    <h3 class="mb-0 text-black">&#8361;<span class="counter ml-0">${dashboardSales}</span></h3>
                    <p class="mb-0">총 판매액(당일)</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
            <div class="widget-stat card">
              <div class="card-body p-4">
                <div class="media ai-icon">
                  <span class="mr-3 bgl-primary text-primary">
                    <i class="fi fi-rr-inbox-out"></i>
                  </span>
                  <div class="media-body">
                    <h3 class="mb-0 text-black"><span class="counter ml-0">${dashboardDeliveryCount}</span></h3>
                    <p class="mb-0">배송 준비중</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
            <div class="widget-stat card">
              <div class="card-body p-4">
                <div class="media ai-icon">
                  <span class="mr-3 bgl-primary text-primary">
                    <i class="fi fi-rr-exclamation"></i>
                  </span>
                  <div class="media-body">
                    <h3 class="mb-0 text-black"><span class="counter ml-0">${dashboardStateCount}</span></h3>
                    <p class="mb-0">취소/교환/반품</p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
            <div class="card">
              <div class="card-header border-0 pb-0 d-sm-flex d-block">
                <div>
                  <h4 class="card-title mb-1">주문 정보</h4>
                  <small class="mb-0">주문 및 취소/교환/반품 정보</small>
                </div>
                <div class="card-action card-tabs mt-3 mt-sm-0">
                  <ul class="nav nav-tabs" role="tablist">
                    <li class="nav-item">
                      <a class="nav-link active" data-toggle="tab" href="#session-duration" role="tab"> 1일 </a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" data-toggle="tab" href="#bounce" role="tab"> 1주 </a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" data-toggle="tab" href="#user" role="tab"> 1달 </a>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="card-body orders-summary">
                <div class="d-flex order-manage p-3 align-items-center mb-4">
                  <a href="/admin/order/manageOrder" class="btn fs-22 py-1 btn-info px-4 mr-3">25</a>
                  <h4 class="mb-0">새로운 주문 <i class="fa fa-circle text-info light ml-1 fs-15"></i></h4>
                  <a href="/admin/order/manageOrder" class="ml-auto text-primary font-w500"
                    >주문 관리 <i class="ti-angle-right ml-1"></i
                  ></a>
                </div>
                <div class="row">
                  <div class="col-sm-4 mb-4">
                    <div class="border px-3 py-3 rounded-xl">
                      <h2 class="fs-32 font-w600 counter">25</h2>
                      <p class="fs-16 mb-0">배달중</p>
                    </div>
                  </div>
                  <div class="col-sm-4 mb-4">
                    <div class="border px-3 py-3 rounded-xl">
                      <h2 class="fs-32 font-w600 counter">60</h2>
                      <p class="fs-16 mb-0">배달완료</p>
                    </div>
                  </div>
                  <div class="col-sm-4 mb-4">
                    <div class="border px-3 py-3 rounded-xl">
                      <h2 class="fs-32 font-w600 counter">7</h2>
                      <p class="fs-16 mb-0">취소/반품</p>
                    </div>
                  </div>
                </div>
                <div class="widget-timeline-icon">
                  <div class="row align-items-center mx-0">
                    <div class="col-xl-3 col-lg-4 col-xxl-4 col-sm-4 px-0 my-2 text-center text-sm-left">
                      <span
                        class="donut"
                        data-peity='{ "fill": ["#2F4CDD","#B519EC", "rgb(62, 73, 84)"]}'
                        >20,3,2</span
                      >
                    </div>
                    <div class="col-xl-9 col-lg-8 col-xxl-8 col-sm-8 px-0">
                      <div class="d-flex align-items-center mb-3">
                        <p class="mb-0 fs-14 mr-2 col-4 px-0">주문확정</p>
                        <div class="progress mb-0" style="height: 8px; width: 100%">
                          <div
                            class="progress-bar progress-animated"
                            style="width: 80%; height: 8px; background-color: #2F4CDD;"
                            role="progressbar"
                          >
                            <span class="sr-only">60% Complete</span>
                          </div>
                        </div>
                        <span class="pull-right ml-auto col-1 px-0 text-right">20</span>
                      </div>
                      <div class="d-flex align-items-center mb-3">
                        <p class="mb-0 fs-14 mr-2 col-4 px-0">교환</p>
                        <div class="progress mb-0" style="height: 8px; width: 100%">
                          <div
                            class="progress-bar progress-animated"
                            style="width: 12%; height: 8px; background-color: #B519EC;"
                            role="progressbar"
                          >
                            <span class="sr-only">60% Complete</span>
                          </div>
                        </div>
                        <span class="pull-right ml-auto col-1 px-0 text-right">03</span>
                      </div>
                      <div class="d-flex align-items-center">
                        <p class="mb-0 fs-14 mr-2 col-4 px-0">취소/반품</p>
                        <div class="progress mb-0" style="height: 8px; width: 100%">
                          <div
                            class="progress-bar progress-animated"
                            style="width: 8%; height: 8px; background-color: rgb(62, 73, 84);"
                            role="progressbar"
                          >
                            <span class="sr-only">60% Complete</span>
                          </div>
                        </div>
                        <span class="pull-right ml-auto col-1 px-0 text-right">02</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
          <div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
            <div class="card">
              <div class="card-header border-0 pb-0 d-sm-flex d-block">
                <div>
                  <h4 class="card-title mb-1">판매액</h4>
                  <small class="mb-0">요일별 판매액</small>
                </div>
              </div>
              <div class="card-body revenue-chart px-3">
                <div class="d-flex align-items-end pr-3 pull-right revenue-chart-bar">
                  <div class="d-flex align-items-end mr-4">
                    <img src="/resources/admin/images/svg/ic_stat2.svg" height="22" width="22" class="mr-2 mb-1" alt="" />
                    <div>
                      <small class="text-dark fs-14">평균판매액</small>
                      <h3 class="font-w600 mb-0">&#8361;<span class="counter">821,800</span></h3>
                    </div>
                  </div>
                  <div class="d-flex align-items-end">
                    <img src="/resources/admin/images/svg/ic_stat1.svg" height="22" width="22" class="mr-2 mb-1" alt="" />
                    <div>
                      <small class="text-dark fs-14">평균취소/판매액</small>
                      <h3 class="font-w600 mb-0">&#8361;<span class="counter">82,500</span></h3>
                    </div>
                  </div>
                </div>
                <div id="chartBar"></div>
              </div>
            </div>
          </div>
          
          <div class="col-xl-12 col-xxl-12 col-lg-12 col-md-12">
            <div id="user-activity" class="card">
              <div class="card-header border-0 pb-0 d-sm-flex d-block">
                <div>
                  <h4 class="card-title mb-1">당월 일판매액</h4>
                  <small class="mb-0">당월 1일간의 판매액</small>
                </div>
              </div>
              <div class="card-body">
                <div class="tab-content" id="myTabContent">
                  <div class="tab-pane fade show active" id="user" role="tabpanel">
                    <canvas id="activity" class="chartjs"></canvas>
                  </div>
                </div>
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
    
    	</c:when>
    	<c:otherwise>
    		<div class="content-body">
            	<div class="container-fluid">
    				<div class="row">
                      <div class="col-lg-12 col-xl-6">
                        <div class="card">
                        	<div class="card-header">
                               <h1 class="card-title"><span class="badge badge-danger light">에러 발생</span></h1>
                        	</div>
                            <div class="card-body">
                                <div class="bootstrap-media">
                                    <div class="media">
                                        <div class="media-body">
                                            <p class="mt-0">코드 : ${errorResponse.errorCode}</p>
                                            <p class="mt-0">메세지 : ${errorResponse.errorMessage}</p>
                                            <p class="mt-0">발생시간 : ${errorResponse.createdAt}</p>
                                            <h4 class="mt-0">반복 발생시 관리자에게 문의 바랍니다.</h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
					  </div>
    				</div>
    			</div>
    		</div>
    	</c:otherwise>
    </c:choose>


    <!--**********************************
        Scripts
    ***********************************-->
    <!-- Counter Up -->
    <script src="/resources/admin/vendor/waypoints/jquery.waypoints.min.js"></script>
    <script src="/resources/admin/vendor/jquery.counterup/jquery.counterup.min.js"></script>

    <!-- Apex Chart -->
    <script src="/resources/admin/vendor/apexchart/apexchart.js"></script>

    <!-- Chart piety plugin files -->
    <script src="/resources/admin/vendor/peity/jquery.peity.min.js"></script>

    <!-- Dashboard 1 -->
    <script src="/resources/admin/js/dashboard/dashboard-1.js"></script>
  </body>
</html>
