<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>BrickFarm - 결제완료</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <link rel="/manifest" href="/site.webmanifest" />
    <!--====== Google Font ======-->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet" />

    <!--====== Vendor Css ======-->
    <link rel="stylesheet" href="/resources/user/css/vendor.css" />

    <!--====== Utility-Spacing ======-->
    <link rel="stylesheet" href="/resources/user/css/utility.css" />

    <!--====== App ======-->
    <link rel="stylesheet" href="/resources/user/css/app.css" />
    
    <!-- syt.css -->
    <link rel="stylesheet" href="/resources/user/css/syt/user.css" />

  </head>

  <body class="config">
    <jsp:include page="../header.jsp"></jsp:include>
    <div class="preloader">
      <img class="lego" src="/resources/user/images/lego.gif" alt="" />
    </div>
    <div id="app">
      <div class="app-content">
        <c:choose>
          <c:when test="${errorCode == 000}">
            <div class="u-s-p-y-10">
              <div class="section__content">
                <div class="container">
                  <div class="breadcrumb">
                    <div class="breadcrumb__wrap">
                      <ul class="breadcrumb__list">
                        <li class="has-separator"><a href="/">Home</a></li>
                        <li class="has-separator"><a href="/mypage/profileinfo">마이페이지</a></li>
                        <li class="has-separator"><a href="/mypage/shoppingcart">장바구니</a></li>
                        <li class="is-marked"><a>결제완료</a></li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- 윗부분 시작 -->
            <div class="u-s-p-b-0">
              <!--====== 시작 ======-->
              <div class="section__intro u-s-m-b-10">
                <div class="container">
                  <div class="row">
                    <div class="col-lg-12">
                      <div class="section__text-wrap">
                        <h1 class="section__heading u-c-secondary">주문이 완료되었습니다.</h1>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!--====== 끝 ======-->
              <c:choose>
                <c:when test="${paymentType == 'card'}">
                  <!-- 시작 -->
                  <div class="section__content">
                    <div class="container">
                      <div class="checkout-f">
                        <div class="row row--center">
                          <div class="col-lg-6 col-md-8 u-s-m-b-10">
                            <div class="l-f-o">
                              <div class="l-f-o__pad-box">
                                <h1 class="gl-h1">주문번호 : ${ordersheet.merchant_uid}</h1>
                                <span class="gl-text u-s-m-b-15">카드사 : ${payment.card_brand}</span>
                                <span class="gl-text u-s-m-b-15">카드번호 : ${payment.card_number}</span>
                                <div class="u-s-m-b-15">
                                  <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/orderdetail?merchant_uid=${ordersheet.merchant_uid}"
                                    >주문내역 보러가기</a
                                  >
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 끝 -->
                </c:when>
                <c:when test="${paymentType == 'vbank'}">
                  <!-- 시작 -->
                  <div class="section__content">
                    <div class="container">
                      <div class="checkout-f">
                        <div class="row row--center">
                          <div class="col-lg-6 col-md-8 u-s-m-b-10">
                            <div class="l-f-o">
                              <div class="l-f-o__pad-box">
                                <h1 class="gl-h1">주문번호 : ${ordersheet.merchant_uid}</h1>
                                <span class="gl-text u-s-m-b-10"
                                  >입금 은행 : ${payment.virtual_account_bank_brand}</span
                                >
                                <span class="gl-text u-s-m-b-10">입금 계좌 : ${payment.virtual_account_number}</span>
                                <span class="gl-text u-s-m-b-10">입금 기한 : ${payment.deposit_deadline} 까지</span>
                                <div class="u-s-m-b-15">
                                  <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/orderdetail?merchant_uid=${ordersheet.merchant_uid}"
                                    >주문조회 보러가기</a
                                  >
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 끝 -->
                </c:when>
                <c:otherwise>
                  <!-- 시작 -->
                  <div class="section__content">
                    <div class="container">
                      <div class="checkout-f">
                        <div class="row row--center">
                          <div class="col-lg-6 col-md-8 u-s-m-b-10">
                            <div class="l-f-o">
                              <div class="l-f-o__pad-box">
                                <h1 class="gl-h1">주문번호 : ${ordersheet.merchant_uid}</h1>
                                <span class="gl-text u-s-m-b-10">전액 포인트 결제</span>
                                <div class="u-s-m-b-15">
                                  <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/orderlist"
                                    >주문내역 보러가기</a
                                  >
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 끝 -->
                </c:otherwise>
              </c:choose>
            </div>

            <!-- 아랫 부분 시작-->
            <div class="u-s-p-b-60">
              <div class="section__content">
                <div class="container">
                  <div class="checkout-f">
                    <div class="row">
                      <!-- 아래 왼쪽-->
                      <div class="col-lg-6">
                        <h1 class="checkout-f__h1">주문 제품 목록</h1>
                        <div class="o-summary">
                          <div class="o-summary__section u-s-m-b-10">
                            <div class="o-summary__item-wrap gl-scroll">
                              <!-- 이부분이 반복중 -->
                              <c:forEach var="i" items="${orderProductList}">
                                <div class="o-card">
                                  <div class="o-card__flex">
                                    <div class="o-card__img-wrap">
                                      <a href="/user/product/productDetail?productCode=${i.product_code}">
                                        <img class="u-img-fluid" src="${i.product_main_image}" alt="" />
                                      </a>
                                    </div>
                                    <div class="o-card__info-wrap">
                                      <span class="o-card__name"> <a href="/user/product/productDetail?productCode=${i.product_code}">${i.product_name}</a> </span>
                                      <span class="o-card__quantity">${i.quantity}개</span>
                                      <span class="w-r__price">${i.event_product_price}원</span>
                                      <c:if test="${i.event_discount_rate != 0}">
                                        <span class="product-m__discount">${i.event_discount_rate}%</span>
                                        <del class="pd-detail__del">${i.product_price}원</del>
                                      </c:if>
                                    </div>
                                  </div>
                                </div>
                              </c:forEach>
                              <!-- 이부분이 반복 끝 -->
                            </div>
                          </div>
                        </div>
                      </div>
                      <!-- 아래 오른쪽-->
                      <div class="col-lg-6">
                        <h1 class="checkout-f__h1">결제 가격</h1>
                        <div class="o-summary__section u-s-m-b-30">
                          <div class="o-summary__box">
                            <table class="o-summary__table">
                              <tbody>
                                <tr>
                                  <td>상품가</td>
                                  <td id="total_price_input">${total_price}</td>
                                </tr>
                                <tr>
                                  <td>이벤트할인</td>
                                  <td id="total_product_price_input">${ordersheet.total_product_price}</td>
                                </tr>
                                <tr>
                                  <td>쿠폰할인</td>
                                  <td id="total_discount_price_input">${coupon_discounted_price}</td>
                                </tr>
                                <tr>
                                  <td>배송비</td>
                                  <td id="post_money_input">${payment.post_money}</td>
                                </tr>
                                <tr>
                                  <td>사용포인트</td>
                                  <td id="point_pay_money_input">${payment.point_pay_money}</td>
                                </tr>
                                <TH COLSPAN="3"><hr /></TH>
                                <tr>
                                  <td>총 결제금액</td>
                                  <td id="pay_money_input">${ordersheet.total_pay_money - payment.point_pay_money}</td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                      <!-- 아래 오른쪽 끝-->
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- 아래 끝-->
          </c:when>

          <c:otherwise>
            <div class="u-s-p-y-10">
              <div class="section__content">
                <div class="container">
                  <div class="breadcrumb">
                    <div class="breadcrumb__wrap">
                      <ul class="breadcrumb__list">
                        <li class="has-separator"><a href="/">Home</a></li>
                        <li class="has-separator"><a href="/mypage/profileinfo">마이페이지</a></li>
                        <li class="has-separator"><a href="/mypage/shoppingcart">장바구니</a></li>
                        <li class="is-marked"><a>결제실패</a></li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="section__content">
              <div class="container">
                <div class="checkout-f">
                  <div class="row row--center">
                    <div class="col-lg-6 col-md-8 u-s-m-b-10">
                      <div class="l-f-o">
                        <div class="l-f-o__pad-box">
                          <c:choose>
                            <c:when test="${errorCode == 999}">
                              <h1 class="gl-h1">${errorResponse.errorMessage} (errorCode: ${errorCode})</h1>
                              <span class="gl-text u-s-m-b-10">주문이 실패하였습니다.</span>
                              <span class="gl-text u-s-m-b-10">다시 시도해주세요.</span>
                              <span class="gl-text u-s-m-b-10">반복적인 문제 발생시</span>
                              <span class="gl-text u-s-m-b-10">상담요청 부탁드립니다.(errorCode: ${errorCode})</span>
                              <div class="u-s-m-b-15">
                                <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/shoppingcart"
                                  >장바구니 돌아가기</a
                                >
                              </div>
                            </c:when>
                            <c:when test="${errorCode == 001}">
                              <h1 class="gl-h1">결제 검증실패(errorCode: ${errorCode})</h1>
                              <span class="gl-text u-s-m-b-10">주문이 실패하였습니다.</span>
                              <span class="gl-text u-s-m-b-10">다시 시도해주세요.</span>
                              <span class="gl-text u-s-m-b-10">반복적인 문제 발생시</span>
                              <span class="gl-text u-s-m-b-10">상담요청 부탁드립니다.(errorCode: ${errorCode})</span>
                              <div class="u-s-m-b-15">
                                <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/shoppingcart"
                                  >장바구니 돌아가기</a
                                >
                              </div>
                            </c:when>
                            <c:otherwise>
                              <h1 class="gl-h1">유효성 검증실패(errorCode: ${errorCode})</h1>
                              <span class="gl-text u-s-m-b-10">주문이 실패하였습니다.</span>
                              <span class="gl-text u-s-m-b-10">다시 시도해주세요.</span>
                              <span class="gl-text u-s-m-b-10">반복적인 문제 발생시</span>
                              <span class="gl-text u-s-m-b-10">상담요청 부탁드립니다.(errorCode: ${errorCode})</span>
                              <div class="u-s-m-b-15">
                                <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/shoppingcart"
                                  >장바구니 돌아가기</a
                                >
                              </div>
                            </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <jsp:include page="../footer.jsp"></jsp:include>
    <!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
    <script>
      window.ga = function () {
        ga.q.push(arguments);
      };
      ga.q = [];
      ga.l = +new Date();
      ga('create', 'UA-XXXXX-Y', 'auto');
      ga('send', 'pageview');
    </script>
    <script src="https://www.google-analytics.com/analytics.js" async defer></script>

	<!-- syt.js -->
	<script src="/resources/user/js/syt/orderCompletePage.js"></script>
	
    <!--====== Vendor Js ======-->
    <script src="/resources/user/js/vendor.js"></script>

    <!--====== jQuery Shopnav plugin ======-->
    <script src="/resources/user/js/jquery.shopnav.js"></script>

    <!--====== App ======-->
    <script src="/resources/user/js/app.js"></script>
    <noscript>
      <div class="app-setting">
        <div class="container">
          <div class="row">
            <div class="col-12">
              <div class="app-setting__wrap">
                <h1 class="app-setting__h1">JavaScript is disabled in your browser.</h1>

                <span class="app-setting__text"
                  >Please enable JavaScript in your browser or upgrade to a JavaScript-capable browser.</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </noscript>
  </body>

  <style>
    .o-summary__item-wrap {
      max-height: 248px;
    }
  </style>
</html>
