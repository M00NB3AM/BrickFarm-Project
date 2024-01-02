<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<title>Brick Farm</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link href="images/favicon.png" rel="shortcut icon" />
<link rel="/manifest" href="/site.webmanifest" />
<!-- 결제 API -->
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>

<!--====== Google Font ======-->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>

<script>
		window.onload = function() {
			$("#recipientInfo").hide();
			 $('#exchange-or-return').change(function () {
		            var selectedValue = $(this).val();
					console.log(selectedValue)
		            var changeMindDivId = 'create-change-mind-div';
		            if (selectedValue === 'exchange') {
		                $('#' + changeMindDivId).hide();
		                $("#priceDiv").hide();
		                $("#accountInfo").hide();
		            	$("#recipientInfo").show();
		            } else {
		                $('#' + changeMindDivId).show();
		                $("#priceDiv").show();
		                $("#recipientInfo").hide();
		                $("#accountInfo").show();
		            }
		        });
			
			
		$("input[name=reason]").change(function() {
			if ($("input[name=reason]:checked").val() == "product-defective") {
				$("#create-etc").hide();
				$("#create-change-mind").hide();
				$("#create-detail-reason").show();
			} else if ($("input[name=reason]:checked").val() == "change-mind") {
				$("#create-etc").hide();
				$("#create-change-mind").show();
				$("#create-detail-reason").hide();
			} else if ($("input[name=reason]:checked").val() == "etc") {
				$("#create-etc").show();
				$("#create-change-mind").hide();
				$("#create-detail-reason").hide();
			}
		});
	};
	
function requestCancel() {
	let reason = "";
	let what = new URLSearchParams(window.location.search).get("what");
	let negligence = 'N';
	console.log(what)
	if(what == 'cancel') {
		what = '취소';
	} else if(what == 'exchange') {
		if($("#exchange-or-return").val() == 'exchange') {
			what = '교환';
		} else if ($("#exchange-or-return").val() == 'return') {
			what = '반품';
		}  
	}
	console.log(what)
	if ($("input[name=reason]:checked").val() == "product-defective") {
		reason = $("#product-wrong").text() + "-" + $("#detail-reason").val();
		negligence = 'Y';
	} else if ($("input[name=reason]:checked").val() == "change-mind") {
		reason = '단순변심'
	} else if ($("input[name=reason]:checked").val() == "etc") {
		reason = $("#memo").val();
		 if (reason.length === 0) {
		      $("#memo-warning").text("기타 사유를 작성해주세요.");
		    } else {
		      $("#memo-warning").text("");
		    }
	}
	let requestData = {
		detailed_order_no : ${withrawalApplyDTO.detailed_order_no},
    	reason : reason,
    	cancel_money : ${withrawalApplyDTO.discounted_price * withrawalApplyDTO.quantity},
    	what : what,
    	negligence : negligence
	}
	 $.ajax({
	        type: 'POST',
	        contentType: 'application/json',
	        url: '/mypage/order/cancelandrefundapplication',
	        data: JSON.stringify(requestData),
	        success: function(response) {
	            console.log('데이터 전송 성공: ' + response);
	            location.href=`/mypage/orderdetail?merchant_uid=${withrawalApplyDTO.merchant_uid}`;
	        },
	        error: function() {
	            console.error('데이터 전송 실패');
	        }
	    });
}
	
</script>

<style>
#change-delivery-address {
	float: right;
}

#coupon-choces {
	display: inline-flex;
}

#usePointContainer {
	display: inline-flex;
}

#usedPoint, #accrualAmount {
	display: inline-flex;
}

#use_point {
	width: 200px;
}
</style>
</head>
<!-- 금액 CSS <span class="pd-detail__price">$6.99</span><span class="pd-detail__discount">(76% OFF)</span><del class="pd-detail__del">$28.97</del></div> -->

<body class="config">
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<jsp:include page="../header.jsp"></jsp:include>
	<div class="preloader is-active">
		<div class="preloader__wrap">
			<img class="preloader__img"
				src="/resources/user/images/preloader.png" alt="" />
		</div>
	</div>

	<!--====== Main App ======-->
	<div id="app">
		<!--====== App Content ======-->
		<form id="completeForm" action="/user/payment/orderPage/complete"
			method="POST">
			<div class="app-content">
			<div class="u-s-p-y-60">
				<!--====== Section Content ======-->
				<div class="section__content">
					<div class="container">
						<div class="breadcrumb">
							<div class="breadcrumb__wrap">
								<ul class="breadcrumb__list">
									<li class="has-separator"><a href="/">Home</a></li>
									 <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
									 <li class="has-separator"><a href="orderlist">주문 내역</a></li>
									 <li class="has-separator"><a href="orderdetail?merchant_uid=${withrawalApplyDTO.merchant_uid}">상세 조회</a></li>
									<li class="is-marked"><a>취소 / 교환 / 반품 신청</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
				<!--====== Section 3 ======-->
				<div class="u-s-p-b-60">
					<!--====== Section Content ======-->
					<div class="section__content">
						<div class="container">
							<div class="checkout-f">
								<div class="row">

									<div class="col-lg-6" style="margin: auto">
										<c:choose>
											<c:when test="${what == 'cancel' }">
												<h1 class="checkout-f__h1">취소 신청</h1>
											</c:when>
											<c:when test="${what == 'exchange' }">
												<h1 class="checkout-f__h1">교환 및 반품 신청</h1>
											</c:when>
										</c:choose>
										<div class="o-summary">

											<div class="o-summary__section u-s-m-b-30">
												<div class="o-summary__item-wrap gl-scroll">
													<div class="o-card">
														<input type="hidden" value="-1" name="shopping_cart_no" />
														<div class="o-card__flex">
															<div class="o-card__img-wrap">
																<img class="u-img-fluid"
																	src="${withrawalApplyDTO.product_main_image }"
																	alt="" />
															</div>
															<div class="o-card__info-wrap">
																<span class="o-card__name"> 
																<a href="">${withrawalApplyDTO.product_name } x ${withrawalApplyDTO.quantity}</a>
																</span> 
																<span class="o-card__price"><fmt:formatNumber value="${withrawalApplyDTO.discounted_price * withrawalApplyDTO.quantity}"
																		type="currency" currencySymbol="" />원</span>
															</div>
														</div>
													</div>
												</div>
											</div>



											<div class="o-summary__section u-s-m-b-30" id="priceDiv">
												<div class="o-summary__box">
													<table class="o-summary__table">
														<tbody>
															<tr>
															<td>상품가격</td>
															<td><fmt:formatNumber
																		value="${withrawalApplyDTO.discounted_price}"
																		type="currency" currencySymbol="" />원</td>
															</tr>
															<tr>
																<td>개수</td>
																<td>${withrawalApplyDTO.quantity } 개</td>
															</tr>
															
																				
															
															<tr style="border-top: 1px solid #ff4500 ">
															<c:if test="${withrawalApplyDTO.payment_state == '결제대기' }">
																<td>총 취소금액</td>
															</c:if>
															<c:if test="${withrawalApplyDTO.payment_state == '결제완료' }">
																<td>총 환불금액</td>
															</c:if>
																<td><fmt:formatNumber
																		value="${withrawalApplyDTO.discounted_price * withrawalApplyDTO.quantity}"
																		type="currency" currencySymbol="" />원</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class="dash__box dash__box--bg-white dash__box--shadow u-s-m-b-30" id="accountInfo">
											<div class="dash__pad-3">
													<h2 class="dash__h2 u-s-m-b-8">환불 계좌 정보</h2>
													<span class="dash__text-2">환불 은행 : ${withrawalApplyDTO.bank_brand }</span>
													<span class="dash__text-2">환불 계좌번호 : ${withrawalApplyDTO.refund_account} </span> 
												</div>
											</div>
											<div class="dash__box dash__box--bg-white dash__box--shadow u-s-m-b-30" id="recipientInfo">
											<div class="dash__pad-3">
													<h2 class="dash__h2 u-s-m-b-8">교환 배송지 정보</h2>
													<span class="dash__text-2">수신자 : ${withrawalApplyDTO.recipient }</span>
													<span class="dash__text-2">연락처 : ${withrawalApplyDTO.recipient_phone} </span> 
													<span class="dash__text-2">배송지 : ${withrawalApplyDTO.recipient_address} (${withrawalApplyDTO.recipient_zip_code})</span> 
												</div>
											</div>


											<div class="o-summary__section u-s-m-b-30">
												<div class="o-summary__box">
												<c:if test="${what == 'cancel' }">
													<h1 class="checkout-f__h1">취소 사유</h1>
												</c:if>
												<c:if test="${what == 'exchange' }">
													<h1 class="checkout-f__h1">교환 / 반품 사유</h1>
												<div class="u-s-m-b-10">
															<select class="select-box select-box--primary-style"
																id="exchange-or-return">
																<option value="exchange">교환</option>
																<option value="return" selected="selected">반품</option>
															</select>
												</div>		
												</c:if>
													<div class="u-s-m-b-10">
														<!--====== Radio Box ======-->
														<c:if test="${what == 'exchange' }">
														<div class="radio-box">
															<input type="radio" name="reason" value="product-defective"/>
															<div class="radio-box__state radio-box__state--primary">
																<label class="radio-box__label" for="product-defective" id="product-wrong">상품이상</label>
															</div>
														</div>
														</c:if>
														<!--====== End - Radio Box ======-->
														<div class="collapse" id="create-detail-reason">
															<select class="select-box select-box--primary-style"
																id="detail-reason">
																<option value="구성품 없음">상품의 구성품이 없음</option>
																<option value="상품 파손">상품이 파손되어 배송됨</option>
																<option value="상품 결함/기능 이상">상품 결함/기능 이상</option>
																<option value="설명과 다름">상품이 설명과 다름</option>
															</select>
														</div>
													</div>
												

													<div class="u-s-m-b-10" id="create-change-mind-div">
														<!--====== Radio Box ======-->
														<div class="radio-box">
															<input type="radio" name="reason" value="change-mind" />
															<div class="radio-box__state radio-box__state--primary">
																<label class="radio-box__label" for="reason">단순변심</label>
															</div>
														</div>
														<!--====== End - Radio Box ======-->
														<div class="collapse" id="create-change-mind">
															<span class="gl-text u-s-m-t-6">배송비가 청구될 수 있습니다.</span>
														</div>
													</div>
													<div class="u-s-m-b-10">
														<!--====== Radio Box ======-->
														<div class="radio-box">
															<input type="radio" name="reason" value="etc" />
															<div class="radio-box__state radio-box__state--primary">
																<label class="radio-box__label" for="etc">기타</label>
															</div>
														</div>
														<!--====== End - Radio Box ======-->
														<div class="collapse" id="create-etc">
															<div class="gl-text u-s-m-t-6">
																<textarea class="text-area text-area--primary-style" 
																	id="memo" name="memo" maxlength="66"></textarea>
																<div id ="memo-warning" style="color: red"></div>
															</div>
														</div>
													</div>
													<div>
														<button type="button" class="btn btn--e-brand-b-2"
															onclick="requestCancel()">신청하기</button>
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
				<!--====== End - Section 3 ======-->
			</div>
		</form>
		<!--====== End - App Content ======-->

		<!--====== Modal Section ======-->


	</div>
	<!--====== End - Main App ======-->

	<jsp:include page="../footer.jsp"></jsp:include>

	<!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
	<script>
		window.ga = function() {
			ga.q.push(arguments);
		};
		ga.q = [];
		ga.l = +new Date();
		ga("create", "UA-XXXXX-Y", "auto");
		ga("send", "pageview");
	</script>
	<script src="https://www.google-analytics.com/analytics.js" async defer></script>

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
							<h1 class="app-setting__h1">JavaScript is disabled in your
								browser.</h1>

							<span class="app-setting__text">Please enable JavaScript
								in your browser or upgrade to a JavaScript-capable browser.</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</noscript>
</body>
</html>
