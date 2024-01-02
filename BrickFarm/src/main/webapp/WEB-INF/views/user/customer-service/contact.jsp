<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="kr">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - 찾아오시는 길</title>

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

    <!--====== Contact CSS ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/contact.css" />
  </head>
  <body class="config">
    <div class="preloader is-active">
      <div class="preloader__wrap">
        <img class="preloader__img" src="/resources/user/images/preloader.png" alt="" />
      </div>
    </div>

    <!--====== Main App ======-->
    <div id="app">
      <!--====== Main Header ======-->
      <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
      <!--====== End - Main Header ======-->

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
                    <li class="is-marked"><a href="/customer-service/contact">찾아오시는 길</a></li>
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
                <div class="col-lg-12">
                  <div class="g-map">
                    <div id="map"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 2 ======-->

        <!--====== Section 3 ======-->
        <div class="u-s-p-b-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row">
                <div class="col-lg-4 col-md-6 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__wrap">
                      <div class="contact-o__icon">
                        <i class="fas fa-phone-volume"></i>
                      </div>

                      <span class="contact-o__info-text-1">LET'S HAVE A CALL</span>
                      <span class="contact-o__info-text-2">02-600-6000</span> <span class="contact-o__info-text-2">010-6000-6000</span>
                    </div>
                  </div>
                </div>
                <div class="col-lg-4 col-md-6 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__wrap">
                      <div class="contact-o__icon">
                        <i class="fas fa-map-marker-alt"></i>
                      </div>

                      <span class="contact-o__info-text-1">OUR LOCATION</span>
                      <span class="contact-o__info-text-2">서울 구로구 시흥대로163길 33</span>
                      <span class="contact-o__info-text-2">주호타워 2, 3층 (우)08393</span>
                    </div>
                  </div>
                </div>
                <div class="col-lg-4 col-md-6 u-s-m-b-30">
                  <div class="contact-o u-h-100">
                    <div class="contact-o__wrap">
                      <div class="contact-o__icon">
                        <i class="far fa-clock"></i>
                      </div>

                      <span class="contact-o__info-text-1">WORK TIME</span>
                      <span class="contact-o__info-text-2">월요일 ~ 금요일</span>
                      <span class="contact-o__info-text-2">10:00 ~ 17:00</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 3 ======-->
      </div>
      <!--====== End - App Content ======-->

      <!--====== Main Footer ======-->
      <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
      <!--====== End - Main Footer ======-->
    </div>
    <!--====== End - Main App ======-->

    <!--====== Vendor Js ======-->
    <script src="/resources/user/js/vendor.js"></script>

    <!--====== jQuery Shopnav plugin ======-->
    <script src="/resources/user/js/jquery.shopnav.js"></script>

    <!--====== App ======-->
    <script src="/resources/user/js/app.js"></script>

    <!--====== KakaoMap API ======-->
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=99c631d983ada6fadf3ae45787bde678"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=99c631d983ada6fadf3ae45787bde678&libraries=services,clusterer,drawing"></script>
    <script type="text/javascript" src="/resources/user/js/kyj/kakaoMap.js"></script>

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
  </body>
</html>
