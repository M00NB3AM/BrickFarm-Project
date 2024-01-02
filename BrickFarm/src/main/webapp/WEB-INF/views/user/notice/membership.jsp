<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="kr">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - 등급 및 혜택</title>

    <!--====== Google Font ======-->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet" />

    <!--====== Vendor Css ======-->
    <link rel="stylesheet" href="/resources/user/css/vendor.css" />

    <!--====== Utility-Spacing ======-->
    <link rel="stylesheet" href="/resources/user/css/utility.css" />

    <!--====== App ======-->
    <link rel="stylesheet" href="/resources/user/css/app.css" />

    <!--====== Common CSS ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/common.css" />

    <!--====== Membership CSS ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/membership.css" />
  </head>
  <body class="config">
    <!--====== Main Header ======-->
    <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
    <!--====== End - Main Header ======-->

    <!--====== Main App ======-->
    <div id="app">
      <!--====== App Content ======-->
      <div class="app-content">
        <!--====== Section 1 ======-->
        <div class="u-s-p-y-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="breadcrumb">
                <div class="breadcrumb__wrap">
                  <ul class="breadcrumb__list">
                    <li class="has-separator"><a href="/">Home</a></li>
                    <li class="is-marked"><a href="#">등급 및 혜택</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--====== End - Section 1 ======-->

        <!--====== Section 2 ======-->
        <div class="u-s-p-b-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row">
                <div class="col-12 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__icon">
                      <img class="membership__icon" src="/resources/user/images/kyj/normal-member.png" />
                    </div>

                    <span class="contact-o__info-text-1">일반 등급</span>
                    <span class="contact-o__info-text-2">상품 구매 확정 시 구매 금액의 2%가 적립됩니다.</span>
                  </div>
                </div>
                <div class="col-12 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__icon">
                      <img class="membership__icon" src="/resources/user/images/kyj/silver-member.png" />
                    </div>

                    <span class="contact-o__info-text-1">실버 등급</span>
                    <span class="contact-o__info-text-2">6개월 동안 구매 금액이 50만원 이상인 회원 (매월 1일에 갱신)</span>
                    <span class="contact-o__info-text-2">상품 구매 확정 시 구매 금액의 5%가 적립됩니다.</span>
                    <span class="contact-o__info-text-2">첫 실버 등급 달성 시 10% 할인 쿠폰이 지급됩니다.</span>
                  </div>
                </div>
                <div class="col-12 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__icon">
                      <img class="membership__icon" src="/resources/user/images/kyj/gold-member.png" />
                    </div>

                    <span class="contact-o__info-text-1">골드 등급</span>
                    <span class="contact-o__info-text-2">6개월 동안 구매 금액이 100만원 이상인 회원 (매월 1일에 갱신)</span>
                    <span class="contact-o__info-text-2">상품 구매 확정 시 구매 금액의 7%가 적립됩니다.</span>
                    <span class="contact-o__info-text-2">첫 골드 등급 달성 시 10% 할인 쿠폰이 지급됩니다.</span>
                  </div>
                </div>
                <div class="col-12 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__icon">
                      <img class="membership__icon" src="/resources/user/images/kyj/benefit.png" />
                    </div>

                    <span class="contact-o__info-text-1">그 외 혜택</span>
                    <span class="contact-o__info-text-2">20만원 이상 구매하신 고객님께 추후 구매 1회 시 5% 할인 쿠폰이 지급됩니다.</span>
                    <span class="contact-o__info-text-2">고객님의 생일마다 10% 할인 쿠폰이 지급됩니다. (사용 기간은 지급일로부터 한 달입니다.)</span>
                    <span class="contact-o__info-text-2">이벤트 제품의 경우 쿠폰은 사용이 불가능하고 적립금만 사용 가능합니다.</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 2 ======-->
      </div>
      <!--====== End - App Content ======-->
    </div>
    <!--====== End - Main App ======-->

    <!--====== Noscript ======-->
    <noscript>
      <div class="app-setting">
        <div class="container">
          <div class="row">
            <div class="col-12">
              <div class="app-setting__wrap">
                <h1 class="app-setting__h1">JavaScript is disabled in your browser.</h1>

                <span class="app-setting__text">Please enable JavaScript in your browser or upgrade to a JavaScript-capable browser.</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </noscript>
    <!--====== Main Footer ======-->
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
    <!--====== End - Main Footer ======-->
  </body>
</html>
