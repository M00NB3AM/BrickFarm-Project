<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>BrickFarm - 결제</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <link rel="/manifest" href="/site.webmanifest" />
    <!-- 결제 API -->
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
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

    <!--====== 아임포트 ======-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.4.js"></script>

    <script>
      // 결제 API
      function requestPay(pay_method) {
        let amount = Number($("input[name=pay_money]").val());
        if (pay_method != "point") {
          // 날짜(3일후) 만드는 함수
          let vbank_due = createVbankDue();
          // 상품명 만들기
          let name = createName();

          var IMP = window.IMP;
          IMP.init("${IMP}");
          IMP.request_pay(
            {
              pg: "html5_inicis.INIpayTest", //포트원에서 제공
              pay_method: pay_method,
              merchant_uid: "${merchant_uid}", // 주문번호(내가 채번해야할듯?)
              name: name, // 제품명
              amount: amount, // 금액 amount
              buyer_name: "${member.member_name}", // 사용자 이름
              buyer_tel: "${member.phone_number}", //사용자 번호
              buyer_email: "${member.email}", // 이메일 ${member.email}
              vbank_due: vbank_due,
            },
            function (rsp) {
              if (rsp.success) {
                $("input[name=imp_uid]").attr("value", rsp.imp_uid);
                let form = document.getElementById("completeForm");
                form.action = "/user/payment/orderCompletePage";
                form.method = "POST";
                form.submit();
              } else {
                // 결제실패
                console.log("결제실패");
                alert("결제에 실패하였습니다.");
              }
            }
          );
        } else {
          // 결제 번호 생성 및 부여
          const randomNo = Math.random() + "";
          const randomStr = randomNo.substr(3, 12);
          const imp_uid = "noimp_" + randomStr;
          $("input[name=imp_uid]").attr("value", imp_uid);
          $("input[type=radio][name=paymentType]").prop("disabled", true);
          let form = document.getElementById("completeForm");
          form.action = "/user/payment/orderCompletePage";
          form.method = "POST";
          form.submit();
        }
      }
    </script>
  </head>

  <body class="config">
    <div class="preloader">
      <img class="lego" src="/resources/user/images/lego.gif" alt="" />
    </div>
    <jsp:include page="../header.jsp"></jsp:include>

    <c:choose>
      <c:when test="${empty errorResponse}">
        <!--====== Main App ======-->
        <div id="app">
          <!--====== App Content ======-->
          <!-- <input type="hidden" name="total_product_price" value="0" /> -->
          <form id="completeForm" name="completeForm">
            <input type="hidden" name="imp_uid" value="" />

            <input type="hidden" id="total_evnet_product_price" value="0" />
            <input type="hidden" id="total_discount_price" value="0" />
            <input type="hidden" name="post_money" value="0" />
            <input type="hidden" name="point_pay_money" value="0" />
            <input type="hidden" name="pay_money" value="0" />

            <input type="hidden" name="coupon_held_no" value="-1" />
            <input type="hidden" name="discount_rate" value="0" />
            <input type="hidden" name="make_address" value="off" />
            <div class="app-content">
              <div class="u-s-p-y-10">
                <!--====== Section Content ======-->
                <div class="section__content">
                  <div class="container">
                    <div class="breadcrumb">
                      <div class="breadcrumb__wrap">
                        <ul class="breadcrumb__list">
                          <li class="has-separator"><a href="/">Home</a></li>
                          <li class="has-separator"><a href="/mypage/profileinfo">마이페이지</a></li>
                          <li class="has-separator"><a href="/mypage/shoppingcart">장바구니</a></li>
                          <li class="is-marked"><a>결제</a></li>
                        </ul>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!--====== Section 2 ======-->
              <div class="u-s-p-b-40">
                <div class="section__content">
                  <div class="container">
                    <div class="row">
                      <div class="col-lg-12">
                        <div class="u-s-m-b-10">
                          <h1 class="checkout-f__h1">이용약관</h1>
                          <iframe src="/resources/user/membership.txt" width="100%" height="150px"></iframe>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!--====== Section Content ======-->
                <div class="section__content">
                  <div class="container">
                    <div class="row">
                      <div class="col-lg-12">
                        <div id="have-coupon-group">
                          <div class="msg u-s-m-b-30">
                            <span class="msg__text"
                              >쿠폰을 사용하시겠습니까?
                              <a class="gl-link" href="#have-coupon" data-toggle="collapse">쿠폰 적용하기!</a>
                            </span>
                            <div class="collapse" id="have-coupon" data-parent="#have-coupon-group">
                              <div class="l-f u-s-m-b-16">
                                <div class="gl-inline">
                                  <div class="u-s-m-b-15">
                                    <select class="select-box select-box--primary-style" id="coupon-choces" name="coupon-choces">
                                      <option value="" selected>쿠폰을 선택해주세요.</option>
                                      <c:forEach var="i" items="${list_coupon_held}">
                                        <option value="${i.coupon_held_no}&${i.discount_rate}" label="${i.coupon_policy_name}"></option>
                                      </c:forEach>
                                    </select>
                                    <button id="coupon-choces-btn" class="btn btn--e-transparent-brand-b-2" type="button">적용</button>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!--====== End - Section Content ======-->
                <!--====== Section Content ======-->
                <div class="section__content">
                  <div class="container">
                    <div class="row">
                      <div class="col-lg-12">
                        <div id="have-point-group">
                          <div class="msg">
                            <div id="usedPoint">
                              <span class="msg__text"
                                >포인트를 사용하시겠습니까?
                                <a class="gl-link" href="#have-point" data-toggle="collapse">포인트 사용하기!</a>
                              </span>
                              <span class="msg__text"
                                >&nbsp;&nbsp;(현재 보유 포인트: <span id="accrualAmount">${member.accrual_amount}</span> point)
                              </span>
                            </div>

                            <div class="collapse" id="have-point" data-parent="#have-point-group">
                              <div class="c-f u-s-m-b-16">
                                <div class="u-s-m-b-16">
                                  <div class="u-s-m-b-15">
                                    <div id="usePointContainer">
                                      <input
                                        type="number"
                                        min="0"
                                        max="${member.accrual_amount}"
                                        id="use_point"
                                        class="input-text input-text--primary-style"
                                        value="0"
                                      />
                                      <div class="check-box">
                                        <input name="allUsePoint" type="checkbox" />
                                        <div class="check-box__state check-box__state--primary">
                                          <label class="check-box__label" for="allUsePoint">전부사용</label>
                                        </div>
                                      </div>
                                    </div>

                                    <button id="point-choces-btn" class="btn btn--e-transparent-brand-b-2" type="button">사용</button>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
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
                    <div class="checkout-f">
                      <div class="row">
                        <div class="col-lg-6">
                          <h1 class="checkout-f__h1">배송지</h1>
                          <a
                            class="ship-b__edit btn--e-transparent-platinum-b-2"
                            data-modal="modal"
                            data-modal-id="#edit-ship-address"
                            id="change-delivery-address"
                            >배송지 정보</a
                          >

                          <div class="u-s-m-b-30">
                            <div class="u-s-m-b-15">
                              <!--====== Check Box ======-->
                              <div class="check-box">
                                <input type="checkbox" id="get-address" checked />
                                <div class="check-box__state check-box__state--primary">
                                  <label class="check-box__label" for="get-address">배송지 수정</label>
                                </div>
                              </div>
                              <!--====== End - Check Box ======-->
                            </div>

                            <!--====== First Name, Last Name ======-->

                            <div class="u-s-m-b-15">
                              <label class="gl-label" for="billing-fname">이름 *</label>
                              <input
                                class="input-text input-text--primary-style"
                                type="text"
                                id="billing-fname"
                                data-bill=""
                                name="recipient"
                                value="${addressBookList[0].recipient}"
                                disabled
                              />
                            </div>

                            <!--====== End - First Name, Last Name ======-->

                            <!--====== PHONE ======-->
                            <div class="u-s-m-b-15">
                              <label class="gl-label" for="billing-phone">핸드폰번호 *</label>
                              <input
                                class="input-text input-text--primary-style"
                                type="text"
                                id="billing-phone"
                                data-bill=""
                                name="recipient_phone"
                                value="${addressBookList[0].phone_number}"
                                disabled
                              />
                            </div>
                            <!--====== End - PHONE ======-->
                            <label id="goPopup" class="gl-label" for="goPopupLabel">
                              <div id="goPopupLabel">
                                <!--====== ZIP/POSTAL ======-->
                                <div class="u-s-m-b-15">
                                  <span>우편번호 *</span> <i class="btn--icon fas fa-search"></i>
                                  <input
                                    class="input-text input-text--primary-style"
                                    type="text"
                                    id="billing-zip"
                                    name="recipient_zip_code"
                                    data-bill=""
                                    value="${addressBookList[0].zip_code}"
                                    readonly
                                    disabled
                                  />
                                </div>
                                <!--====== End - ZIP/POSTAL ======-->
                                <!--====== Street Address ======-->
                                <div class="u-s-m-b-15">
                                  <span>주소*</span>
                                  <input
                                    class="input-text input-text--primary-style"
                                    type="text"
                                    id="billing-address"
                                    data-bill=""
                                    name="recipient_address"
                                    value="${addressBookList[0].address}"
                                    readonly
                                    disabled
                                  />
                                </div>
                                <!--====== End - Street Address ======-->
                              </div>
                            </label>
                            <div class="u-s-m-b-15">
                              <label class="gl-label" for="billing-detaile-address">상세주소 *</label>
                              <input
                                class="input-text input-text--primary-style"
                                type="text"
                                id="billing-detaile-address"
                                data-bill=""
                                name="recipient_detaile_address"
                                value="${addressBookList[0].detaile_address}"
                                disabled
                              />
                            </div>
                            <div class="u-s-m-b-10">
                              <!--====== Check Box ======-->
                              <div class="check-box">
                                <input type="checkbox" id="make_address_checked" data-bill="" disabled />
                                <div class="check-box__state check-box__state--primary">
                                  <label class="check-box__label" for="make_address">배송지 정보에 저장하시겠습니까?</label>
                                </div>
                              </div>
                              <!--====== End - Check Box ======-->
                            </div>
                            <div class="u-s-m-b-10">
                              <label class="gl-label" for="order-note">배송시 요청사항</label>
                              <textarea class="text-area text-area--primary-style" id="memo" name="memo"></textarea>
                            </div>
                          </div>
                        </div>

                        <!--====== 오른쪽 부분 시작 ======-->
                        <div class="col-lg-6">
                          <h1 class="checkout-f__h1">제품 목록</h1>

                          <div class="o-summary">
                            <!-- 제품 목록 시작-->
                            <div class="o-summary__section u-s-m-b-30">
                              <div class="o-summary__item-wrap gl-scroll">
                                <!-- 이부분이 반복중 -->
                                <c:forEach var="i" items="${list_detailed_order}">
                                  <div class="o-card">
                                    <input type="hidden" name="product_code" value="${i.product_code}" />
                                    <input type="hidden" class="product_price" value="${i.product_price}" />
                                    <input type="hidden" name="quantity" value="${i.quantity}" />
                                    <div class="o-card__flex">
                                      <div class="o-card__img-wrap">
                                        <a href="/user/product/productDetail?productCode=${i.product_code}">
                                          <img class="u-img-fluid" src="${i.product_main_image}" alt="" />
                                        </a>
                                      </div>
                                      <div class="o-card__info-wrap">
                                        <span class="o-card__name"
                                          ><a href="/user/product/productDetail?productCode=${i.product_code}">${i.product_name}</a></span
                                        >
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
                            <!-- 제품 목록 끝-->

                            <!-- 가격 나오는곳 -->
                            <div class="o-summary__section u-s-m-b-30">
                              <div class="o-summary__box">
                                <table class="o-summary__table">
                                  <tbody>
                                    <tr>
                                      <td>상품가</td>
                                      <td id="total_product_price_input"></td>
                                    </tr>
                                    <tr>
                                      <td>이벤트할인</td>
                                      <td id="total_evnet_product_price_input"></td>
                                    </tr>
                                    <tr>
                                      <td>쿠폰할인</td>
                                      <td id="total_discount_price_input">-0원</td>
                                    </tr>
                                    <tr>
                                      <td>배송비</td>
                                      <td id="post_money_input"></td>
                                    </tr>
                                    <tr>
                                      <td>사용포인트</td>
                                      <td id="point_pay_money_input">0원</td>
                                    </tr>
                                    <th COLSPAN="3">
                                      <hr />
                                      <tr>
                                        <td>총 결제금액</td>
                                        <td id="pay_money_input"></td>
                                      </tr>
                                    </th>
                                  </tbody>
                                </table>
                              </div>
                            </div>
                            <!-- 가격 나오는곳 끝 -->

                            <!-- 결제 방법 선택하는 곳 -->
                            <div class="o-summary__section u-s-m-b-30">
                              <div class="o-summary__box">
                                <h1 class="checkout-f__h1">결제 방법</h1>
                                <div class="u-s-m-b-10">
                                  <!--====== Radio Box ======-->
                                  <div class="radio-box">
                                    <input id="cash-order" type="radio" name="paymentType" value="vbank" />
                                    <div class="radio-box__state radio-box__state--primary">
                                      <label class="radio-box__label" for="cash-order">가상 계좌</label>
                                    </div>
                                  </div>
                                  <!--====== End - Radio Box ======-->
                                  <div class="collapse" id="create-cash-order">
                                    <div class="u-s-m-b-15">
                                      <span class="gl-text u-s-m-t-6"> 입금기한 : 결제후 3일 자정까지 </span>
                                    </div>
                                    <div class="u-s-m-b-15">
                                      <div class="gl-text u-s-m-t-6">환불 받을 계좌번호</div>
                                      <div id="bankContainer">
                                        <select class="select-box select-box--primary-style" id="bank_brand" name="bank_brand">
                                          <option value="">은행선택</option>
                                          <option value="KB국민">KB국민은행</option>
                                          <option value="SC제일">SC제일은행</option>
                                          <option value="기업">기업은행</option>
                                          <option value="농협">농협은행</option>
                                          <option value="신한">신한은행</option>
                                          <option value="하나(외환)">하나(외환)은행</option>
                                          <option value="우리">우리은행</option>
                                          <option value="우체국">우체국은행</option>
                                          <option value="카카오뱅크">카카오뱅크</option>
                                          <option value="케이뱅크">케이뱅크</option>
                                          <option value="토스뱅크">토스뱅크</option>
                                        </select>
                                        <input
                                          type="text"
                                          class="input-text input-text--primary-style"
                                          id="refund_account"
                                          name="refund_account"
                                          value=""
                                          placeholder="계좌번호를 입력하세요."
                                        />
                                      </div>
                                    </div>
                                  </div>
                                </div>

                                <div class="u-s-m-b-10">
                                  <!--====== Radio Box ======-->
                                  <div class="radio-box">
                                    <input id="card-order" type="radio" name="paymentType" value="card" />
                                    <div class="radio-box__state radio-box__state--primary">
                                      <label class="radio-box__label" for="card-order">카드&간편 결제</label>
                                    </div>
                                  </div>

                                  <div>
                                    <button type="button" class="btn btn--e-brand-b-2 u-s-m-t-20" onclick="clickRequestPay()">결제하기</button>
                                  </div>
                                </div>
                              </div>
                              <!-- 결제 방법 선택하는 곳 끝 -->
                            </div>
                            <!--====== End - Order Summary ======-->
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <!--====== End - Section 3 ======-->
              </div>
            </div>
          </form>
          <!--====== End - App Content ======-->

          <!--====== Modal Section ======-->

          <!--====== Shipping Address Add Modal ======-->
          <div class="modal fade" id="edit-ship-address">
            <div class="modal-dialog modal-dialog-centered modal-lg">
              <div class="modal-content">
                <div class="modal-body">
                  <div class="checkout-modal2">
                    <div class="u-s-m-b-30">
                      <div class="dash-l-r">
                        <h1 class="gl-modal-h1">배송지 정보</h1>
                      </div>
                    </div>
                    <form class="checkout-modal2__form">
                      <div class="dash__table-2-wrap u-s-m-b-30 gl-scroll">
                        <table class="dash__table-2">
                          <thead>
                            <tr>
                              <th class="col-1">선택</th>
                              <th class="col-1">주소록명</th>
                              <th class="col-1">이름</th>
                              <th class="col-1">핸드폰번호</th>
                              <th class="col-1">우편번호</th>
                              <th class="col-1">주소</th>
                              <th class="col-1">상세주소</th>
                            </tr>
                          </thead>
                          <tbody>
                            <c:forEach var="i" items="${addressBookList}" varStatus="status">
                              <tr>
                                <td class="col-1">
                                  <!--====== Radio Box ======-->
                                  <div class="radio-box">
                                    <c:if test="${status.index == 0}">
                                      <input type="radio" id="address${status.index}" name="address" checked />
                                    </c:if>
                                    <c:if test="${status.index != 0}">
                                      <input type="radio" id="address${status.index}" name="address" />
                                    </c:if>
                                    <div class="radio-box__state radio-box__state--primary">
                                      <label class="radio-box__label" for="address${status.index}"></label>
                                    </div>
                                  </div>
                                  <!--====== End - Radio Box ======-->
                                </td>
                                <td class="col-1" name="address_name">${i.address_name}</td>
                                <td class="col-1" name="recipient">${i.recipient}</td>
                                <td class="col-1" name="phone_number">${i.phone_number}</td>
                                <td class="col-1" name="zip_code">${i.zip_code}</td>
                                <td class="col-1" name="address">${i.address}</td>
                                <td class="col-1" name="detaile_address">${i.detaile_address}</td>
                              </tr>
                            </c:forEach>
                          </tbody>
                        </table>
                      </div>
                      <div class="gl-modal-btn-group">
                        <button class="btn btn--e-brand-b-2" type="button" data-dismiss="modal" onclick="choiceAddress()">선택</button>
                        <button class="btn btn--e-grey-b-2" type="button" data-dismiss="modal">닫기</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!--====== End - Shipping Address Add Modal ======-->
        </div>
        <!--====== End - Main App ======-->
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
                    <li class="is-marked"><a>오류 페이지</a></li>
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
                      <h1 class="gl-h1">${errorResponse.errorMessage}</h1>
                      <span class="gl-text u-s-m-b-10">결제페이지 이동 실패</span>
                      <span class="gl-text u-s-m-b-10">다시 시도해주세요.</span>
                      <span class="gl-text u-s-m-b-10">반복적인 문제 발생시</span>
                      <span class="gl-text u-s-m-b-10">상담요청 부탁드립니다. (errorCode: ${errorResponse.errorCode})</span>
                      <div class="u-s-m-b-15">
                        <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/mypage/shoppingcart">장바구니 돌아가기</a>
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
    <jsp:include page="../footer.jsp"></jsp:include>

    <!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
    <script>
      window.ga = function () {
        ga.q.push(arguments);
      };
      ga.q = [];
      ga.l = +new Date();
      ga("create", "UA-XXXXX-Y", "auto");
      ga("send", "pageview");
    </script>
    <script src="https://www.google-analytics.com/analytics.js" async defer></script>

    <!-- syt.js
     -->
    <script src="/resources/user/js/syt/orderPage.js"></script>

    <!--====== Vendor Js ======-->
    <!--  
    <script src="/resources/user/js/vendor.js"></script> 
    -->

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

                <span class="app-setting__text">Please enable JavaScript in your browser or upgrade to a JavaScript-capable browser.</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </noscript>
    <!-- 카카오톡 주소API -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  </body>
</html>
