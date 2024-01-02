<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap" rel="stylesheet" />
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <link href="/resources/admin/vendor/jqvmap/css/jqvmap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/resources/admin/vendor/chartist/css/chartist.min.css" />
    <link href="/resources/admin/vendor/bootstrap-select/dist/css/bootstrap-select.min.css" rel="stylesheet" />
    <link href="/resources/admin/css/style.css" rel="stylesheet" />
    <link href="https://cdn.lineicons.com/2.0/LineIcons.css" rel="stylesheet" />
    <link
      rel="stylesheet"
      href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css"
    />
    <script type="text/javascript" src="/resources/admin/js/ysh/adminlogin.js"></script>
  </head>
  <body>
    <!--**********************************
            Nav header start
        ***********************************-->
    <div class="nav-header">
      <a href="/admin" class="brand-logo">
        <img class="logo-abbr" src="/resources/user/images/logo/logo-1.png" alt="" />
        <img class="logo-compact" src="/resources/admin/images/logo-text-brickfarm.png" alt="" />
        <img class="brand-title" src="/resources/admin/images/logo-text-brickfarm.png" alt="" />
      </a>

      <div class="nav-control">
        <div class="hamburger">
          <span class="line"></span><span class="line"></span>
          <span class="line"></span>
        </div>
      </div>
    </div>
    <!--**********************************
            Nav header end
        ***********************************-->
    <!--**********************************
            Header start
        ***********************************-->
    <div class="header">
      <div class="header-content">
        <nav class="navbar navbar-expand">
          <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav header-right">
              <li class="nav-item dropdown header-profile">
                <a class="nav-link" href="#" role="button" data-toggle="dropdown">
                  <div class="header-info">
                    <span><strong>admin</strong></span>
                  </div>
                  <img src="/resources/admin/images/logo/logo-1.png" width="20" alt="" />
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                  <a href="/admin/logout" class="dropdown-item ai-icon">
                    <svg
                      id="icon-logout"
                      xmlns="http://www.w3.org/2000/svg"
                      class="text-danger"
                      width="18"
                      height="18"
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="currentColor"
                      stroke-width="2"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    >
                      <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                      <polyline points="16 17 21 12 16 7"></polyline>
                      <line x1="21" y1="12" x2="9" y2="12"></line>
                    </svg>
                    <span class="ml-2">로그아웃</span>
                  </a>
                </div>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </div>
    <!--**********************************
            Header end ti-comment-alt
    ***********************************-->
    <!--**********************************
            Sidebar start
    ***********************************-->
    <div class="deznav">
      <div class="deznav-scroll">
        <ul class="metismenu" id="menu">
          <li>
            <a class="ai-icon" href="/admin" aria-expanded="false">
              <i class="flaticon-381-home-2"></i>
              <span class="nav-text font-weight-bold">대시보드</span>
            </a>
          </li>
          <li>
            <a class="has-arrow ai-icon" href="javascript:void()" aria-expanded="false">
              <i class="fi fi-rr-shopping-cart"></i>
              <span class="nav-text font-weight-bold">주문</span>
            </a>
            <ul aria-expanded="false">
              <li><a href="/admin/order/dashboard">대시보드</a></li>
              <li><a href="/admin/order/manageOrder">주문 관리</a></li>
              <li><a href="/admin/order/delivery">배송 관리</a></li>
              <li>
                <a class="has-arrow ai-icon" aria-expanded="false">취소/교환/반품</a>
                <ul>
                  <li><a href="/admin/order/cancel">취소관리</a></li>
                  <li><a href="/admin/order/exchange">교환관리</a></li>
                  <li><a href="/admin/order/return">반품관리</a></li>
                </ul>
              </li>
            </ul>
          </li>
          <li>
            <a class="has-arrow ai-icon" href="javascript:void()" aria-expanded="false">
              <i class="fi fi-rr-boxes"></i>
              <span class="nav-text font-weight-bold">상품</span>
            </a>
            <ul aria-expanded="false">
              <li><a href="/admin/product/dashboard">대시보드</a></li>
              <li><a href="/admin/product/listAll">상품 조회</a></li>
              <li><a href="/admin/product/addProduct">상품 등록</a></li>
              <li><a href="/admin/product/placeOrder">발주 관리</a></li>
              <li><a href="/admin/product/receiving">입고 관리</a></li>
              <li><a href="/admin/product/carryingOut">반출 관리</a></li>
              <li><a href="/admin/product/event">이벤트 관리</a></li>
            </ul>
          </li>
          <li>
            <a class="has-arrow ai-icon" href="javascript:void()" aria-expanded="false">
              <i class="fi fi-rr-users-alt"></i>
              <span class="nav-text font-weight-bold">고객</span>
            </a>
            <ul aria-expanded="false">
              <li><a href="/admin/member/dashboard">대시보드</a></li>
              <li><a href="/admin/member/memberList">회원 조회</a></li>
              <li><a href="/admin/member/manageMember">회원 관리</a></li>
              <li><a href="/admin/member/coupon">쿠폰 관리</a></li>
            </ul>
          </li>
          <li>
            <a class="has-arrow ai-icon" href="javascript:void()" aria-expanded="false">
              <i class="fi fi-rr-poll-h"></i>
              <span class="nav-text font-weight-bold">게시판</span>
            </a>
            <ul aria-expanded="false">
              <li><a href="/admin/board/dashboard">대시보드</a></li>
              <li><a href="/admin/board/notice">공지사항 관리</a></li>
              <li><a href="/admin/board/inquiry">문의 관리</a></li>
              <li><a href="/admin/board/faq">자주묻는 질문 관리</a></li>
              <li><a href="/admin/board/category">분류 관리</a></li>
            </ul>
          </li>
          <li>
            <a href="widget-basic.html" class="has-arrow ai-icon" aria-expanded="false">
              <i class="fi fi-rr-chart-histogram"></i>
              <span class="nav-text font-weight-bold">통계</span>
            </a>
            <ul aria-expanded="false">
              <li><a href="/admin/statistics/dashboard">대시보드</a></li>
              <li><a href="/admin/statistics/netsales">매출 분석</a></li>
              <li>
                <a class="has-arrow ai-icon" aria-expanded="false">상품 분석</a>
                <ul>
                  <li><a href="/admin/statistics/products/totalsales">판매 상품 순위</a></li>
                  <li><a href="/admin/statistics/products/totalsales-category">판매 분류 순위</a></li>
                  <li><a href="/admin/statistics/products/totalcanceled">취소/반품 순위</a></li>
                  <li><a href="/admin/statistics/products/cart">장바구니 상품 분석</a></li>
                  <li><a href="/admin/statistics/products/cartdetail">장바구니 상세 내역</a></li>
                  <li><a href="/admin/statistics/products/wishlist">관심 상품 분석</a></li>
                </ul>
              </li>
              <li>
                <a class="has-arrow ai-icon" aria-expanded="false">고객 분석</a>
                <ul>
                  <li><a href="/admin/statistics/members/day">요일별 분석</a></li>
                  <li><a href="/admin/statistics/members/hour">시간별 분석</a></li>
                  <li><a href="/admin/statistics/members/membergrade">회원 등급별 분석</a></li>
                  <li><a href="/admin/statistics/members/recipient-address">배송 지역별 분석</a></li>
                  <li><a href="/admin/statistics/members/accrual">적립금 분석</a></li>
                </ul>
              </li>
            </ul>
          </li>
        </ul>

        <div class="copyright">
          <p><strong>Eatio - Restaurant Admin Dashboard</strong> © 2020 All Rights Reserved</p>
        </div>
      </div>
    </div>
    <!--**********************************
            Sidebar end
        ***********************************-->
    <!-- Required vendors 
    -->
    <script src="/resources/admin/vendor/global/global.min.js"></script>
    <script src="/resources/admin/vendor/bootstrap-select/dist/js/bootstrap-select.min.js"></script>
    <script src="/resources/admin/vendor/chart.js/Chart.bundle.min.js"></script>
    <script src="/resources/admin/js/custom.min.js"></script>
    <script src="/resources/admin/js/deznav-init.js"></script>

    <!-- Counter Up -->
    <script src="/resources/admin/vendor/waypoints/jquery.waypoints.min.js"></script>
    <script src="/resources/admin/vendor/jquery.counterup/jquery.counterup.min.js"></script>

    <!-- Apex Chart -->
    <!-- <script src="/resources/admin/vendor/apexchart/apexchart.js"></script> -->

    <!-- Chart piety plugin files -->
    <script src="/resources/admin/vendor/peity/jquery.peity.min.js"></script>

    <!-- Dashboard 1 -->
    <!-- <script src="/resources/admin/js/dashboard/dashboard-1.js"></script> -->
  </body>
</html>
