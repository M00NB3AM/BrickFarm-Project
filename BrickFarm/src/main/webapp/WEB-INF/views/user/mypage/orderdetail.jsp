<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
<meta charset="UTF-8" />
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<title>Brick Farm</title>
   <meta name="viewport" content="width=device-width, initial-scale=1" />
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
<script type="text/javascript">

$(document).ready(function() {
	$('#totalDiscountInfo').hide();
	$('#totalDiscount').on('click', function() {
		$('#totalDiscountInfo').toggle();
	})
})

function openModal(button) {
			let productName = $(button).data('product-name');
			let productQuantity = parseInt($(button).data('product-quantity'));
			let productPrice = parseInt($(button).data('product-price'));
			let productImg = $(button).data('product-img');
			let totalPrice = (productQuantity * productPrice).toLocaleString('ko-KR');
			let productDetailedOrderNo = $(button).data('product-detailed-order-no');
			let pointPay = $(button).data('point-pay');
			
			let modalProductName = $("#confirmPurchaseModal").find("#modalProductName");
			modalProductName.text(productName + "x" + productQuantity );
			let modalProductPrice = $("#confirmPurchaseModal").find("#modalProductPrice");
			modalProductPrice.text(totalPrice+"원");
			let modalProductImg = $("#confirmPurchaseModal").find("#modalProductImg");
			modalProductImg.attr("src", productImg);
			let modalProductDetailedOrderNo = $("#confirmPurchaseModal").find("#modalProductDetailedOrderNo");
			modalProductDetailedOrderNo.text(productDetailedOrderNo);
	}	 

function asd() {
	let modalProductDetailedOrderNo = $("#confirmPurchaseModal").find("#modalProductDetailedOrderNo").text();
	let modalProductPrice = $("#confirmPurchaseModal").find("#modalProductPrice").text();
	let priceNumber = parseFloat(modalProductPrice.replace(/,/g, ''));
	let merchantUid = $("#merchantUid").text();
	console.log(priceNumber);
	console.log(merchantUid);
}

function sendDetailedOrderNo() {
	let modalProductDetailedOrderNo = $("#confirmPurchaseModal").find("#modalProductDetailedOrderNo").text();
	let modalProductPrice = $("#confirmPurchaseModal").find("#modalProductPrice").text();
	let priceNumber = parseFloat(modalProductPrice.replace(/,/g, ''));
	let merchantUid = $("#merchantUid").text();
	console.log(modalProductDetailedOrderNo);
	console.log(priceNumber);
	 $.ajax({
	        type: 'POST',
	        url: '/mypage/order/confirmorder', // 컨트롤러 URL
	        data: {
	        	"modalProductDetailedOrderNo" : modalProductDetailedOrderNo,
	        	"priceNumber" : priceNumber,
	        	"merchantUid" : merchantUid
	        },
	        success: function(response) {
	            console.log('데이터 전송 성공: ' + response);
	            location.reload();
	        },
	        error: function() {
	            console.error('데이터 전송 실패');
	        }
	    });

}	 
</script>
</head>
<body class="config">
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<jsp:include page="../header.jsp"></jsp:include>
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
									  <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
									<li class="has-separator"><a href="orderlist">주문 내역</a></li>
									<li class="is-marked"><a>상세 조회</a></li>
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
					<div class="dash">
						<div class="container">
							<div class="row">
								<div class="col-lg-3 col-md-12">

									<!--====== Dashboard Features ======-->
									<jsp:include page="./dashBoard.jsp"/>
									<!--====== End - Dashboard Features ======-->
								</div>
								<div class="col-lg-9 col-md-12">
								
									<h1 class="dash__h1 u-s-m-b-30">주문조회</h1>
									<!-- 주문 번호, 주문 날짜, 총 가격 start -->
									<div class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
										<div class="dash__pad-2">
											<div class="dash-l-r">
												<div>
													<div class="manage-o__text-2 u-c-secondary" id ="merchantUid">${orderInfo.merchant_uid }</div>
													<div class="manage-o__text u-c-silver">${orderInfo.order_day}</div>
												</div>
												<div>
													<div class="manage-o__text-2 u-c-silver">
														결제 금액: 
														<span class="manage-o__text-2 u-c-secondary">
														<fmt:formatNumber value="${orderInfo.total_pay_money-(orderInfo.point_pay_money + orderInfo.cancel_money)}" type="currency" currencySymbol="" />원
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- 주문 번호, 주문 날짜, 총 가격 end -->
									
									<c:if test="${!empty orderList}">
									
									<div class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
										<!-- 주문된 상품 출력 영역 start -->
										<div class="dash__pad-2">
											<div class="manage-o">
												<div class="manage-o__header u-s-m-b-30">
													<div class="manage-o__icon">
														<i class="fas fa-box u-s-m-r-5"></i> 
														<span class="manage-o__text">${orderInfo.delivery_state}  ${orderList.size()}</span>
													</div>
												</div>
												<!-- 배송 상태(delivery_state)에 따른 주문일 or 배송 시작,완료 일  출력 start -->
												<c:choose>
													<c:when test="${orderInfo.delivery_state == '배송중'}">
												<div class="dash-l-r">
													<div class="manage-o__text u-c-secondary">배송 시작일 : ${orderInfo.delivery_waiting_date }</div>
													<div class="manage-o__icon">
													</div>
												</div>
												</c:when>
												<c:when test="${orderInfo.delivery_state == '배송중' || orderInfo.delivery_state == '배송대기중' || orderInfo.delivery_state == '배송완료'}">
												<div class="dash-l-r">
													<c:if test="${orderInfo.post_no != null}">
													<div class="manage-o__text u-c-secondary">운송장 번호 : ${orderInfo.post_no}</div>
													</c:if>
													<div class="manage-o__icon">
													</div>
												</div>
												</c:when>
												<c:otherwise>
														<div class="dash-l-r">
													<div class="manage-o__text u-c-secondary">상품 주문일 : ${orderInfo.order_day}</div>
													</div>
												</c:otherwise>
												</c:choose>
												<!-- 배송 상태(delivery_state)에 따른 주문일 or 배송 시작,완료 일  출력 end -->
												
												<!-- 배송 상태(delivery_state)에 따른 진행상황 bar 출력 start -->
												<c:choose>
												<c:when test="${orderInfo.delivery_state == '배송준비중'}">
												<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송준비중</span>
															</div>
														</div>
														
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송대기중</span>
															</div>
														</div>
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송중</span>
															</div>
														</div>
															<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송완료</span>
															</div>
														</div>
													</div>
												</div>
												</c:when>
												<c:when test="${orderInfo.delivery_state == '배송대기중'}">
												<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송준비중</span>
															</div>
														</div>
														
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송대기중</span>
															</div>
														</div>
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송중</span>
															</div>
														</div>
															<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송완료</span>
															</div>
														</div>
													</div>
												</div>
												</c:when>
												<c:when test="${orderInfo.delivery_state == '배송중'}">
												<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송준비중</span>
															</div>
														</div>
														
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송대기중</span>
															</div>
														</div>
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송중</span>
															</div>
														</div>
															<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송완료</span>
															</div>
														</div>
													</div>
												</div>
												</c:when>
												<c:when test="${orderInfo.delivery_state == '배송완료'}">
													<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송준비중</span>
															</div>
														</div>
														
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송대기중</span>
															</div>
														</div>
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송중</span>
															</div>
														</div>
															<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송완료</span>
															</div>
														</div>
													</div>
												</div>
												</c:when>
												<c:otherwise>
												<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송준비중</span>
															</div>
														</div>
														
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송대기중</span>
															</div>
														</div>
														<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송중</span>
															</div>
														</div>
															<div class="col-lg-3 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">배송완료</span>
															</div>
														</div>
													</div>
												</div>
												</c:otherwise>
												</c:choose>
												<!-- 배송 상태(delivery_state)에 따른 진행상황 bar 출력 start -->
												
												<!-- 주문 된 상품 출력 start  -->
												<c:forEach var="order" items="${orderList}">
												<div class="manage-o__description">
													<div class="description__container">
														<div class="description__img-wrap">

															<img class="u-img-fluid" id="productImg"
																src="${order.product_main_image } " alt="orderItemImg">
														</div>
														<div class="description-title"><span id="productName">${order.product_name }</span> x <span id="productQuantity">${order.quantity }</span>
														<div class="manage-o__text u-c-silver">${order.payment_state } </div>
														<c:choose>
												<c:when test="${order.payment_state == '결제대기' or order.payment_state == '결제완료' and orderInfo.delivery_state == '배송준비중'}">
														<div class="manage-o__text u-c-silver"><a style="color: red" href="./cancelandrefundapplication?detailed_order_no=${order.detailed_order_no}&what=cancel">취소</a></div>
												</c:when>
												<c:when test="${orderInfo.delivery_state == '배송완료' and order.payment_state == '결제완료'}">
														<div class="manage-o__text u-c-silver"><a style="color: red" href="./cancelandrefundapplication?detailed_order_no=${order.detailed_order_no}&what=exchange">교환 / 반품</a></div>
												</c:when>
												<c:when test="${orderInfo.delivery_state == '배송완료' and order.payment_state == '구매확정'}">
												</c:when>
												</c:choose>
														</div>
														
													</div>
													<div class="description__info-wrap">
														<div>
														</div>
														<div>

															<span class="manage-o__text-2 u-c-silver">가격: <span
																class="manage-o__text-2 u-c-secondary" id="productPrice"><fmt:formatNumber value="${order.product_price * order.quantity}" type="number"/>원 </span></span>
														</div>
															<c:choose>
															<c:when test="${order.payment_state == '구매확정' }">
																<div class="manage-o__text u-c-silver">
																<span style="color: green">확정일 : ${order.complete_date }</span>
																</div>
																<c:if test="${order.review_detailed_order_no == 0 }">
																<div class="manage-o__text u-c-silver">
																<a class="has-dropdwon" href="/mypage/writereview?detailed_order_no=${order.detailed_order_no }"><span style="color: #ff4500">리뷰 작성</span></a>
																</div>
																</c:if>
																<c:if test="${order.review_detailed_order_no > 0 }">
																<div class="manage-o__text u-c-silver">
																<a class="has-dropdwon" href="/mypage/modifyreviewform?detailed_order_no=${order.detailed_order_no }"><span style="color: #ff4500">리뷰 수정</span></a>
																</div>
																</c:if>
																
															</c:when>
															<c:when test="${order.payment_state == '결제완료' and orderInfo.delivery_state == '배송완료' }">
															<a class="dash__custom-link btn--e-transparent-brand-b-2" data-modal="modal" data-modal-id="#confirmPurchaseModal" onclick="openModal(this)"
															data-product-name="${order.product_name}" data-product-quantity = "${order.quantity }"
															data-product-price = "${order.discounted_price }" data-product-img = "${order.product_main_image}"
															data-product-detailed-order-no = "${order.detailed_order_no}" data-point-pay ="${orderInfo.point_pay_money }"
															style="color: #ff4500; border: 1px solid #ff4500; width: 7rem; margin: 1px -5px; padding: 3px; border-radius: 50px" id="confirmPurchase">구매확정</a>
															</c:when>
															</c:choose>
													</div>
													
												</div>
												<hr>
												
												</c:forEach>
												<!-- 주문 된 상품 출력 end -->
												
												<!-- 배송상태(delivery_state)가 배송 완료일 경우 구매 확정 버튼 출력 start -->
												<c:if test="${orderInfo.delivery_state == '배송완료' }">
												<div class="u-s-m-b-16">
												<span class="dash__text-2" style="color: green">배송 완료일 : ${orderInfo.delivery_done_date }</span>
												<!-- 
													<a class="dash__custom-link btn--e-transparent-brand-b-2"
														href="#"
														style="color: green; border: 2px solid green;" id="confirmPurchase">구매확정</a>
														 -->
												</div>
												</c:if>
												<!-- 배송상태(delivery_state)가 배송 완료일 경우 구매 확정 버튼 출력 end -->
											</div>
										</div>
										<!-- 주문된 상품 출력 영역 end -->
									</div>
									</c:if>
									<!-- 교환 상품 표시 영역 start -->
									<c:if test="${!empty orderExchangeList }">
									<div class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30" >
										<div class="dash__pad-2">
											<div class="manage-o">
												<div class="manage-o__header u-s-m-b-30">
													<div class="manage-o__icon">
														<i class="fas fa-box u-s-m-r-5"></i> <span
															class="manage-o__text">교환  ${orderExchangeList.size()}</span>
													</div>
												</div>
												
												<!-- 교환 상품 표시 start -->
												<c:forEach var="orderExchange" items="${orderExchangeList }">
												<div class="manage-o__description">
													<div class="description__container">
														<div class="description__img-wrap">
															<img class="u-img-fluid" src="${orderExchange.product_main_image } " alt="orderItemImg">
														</div>
														<div class="description-title">${orderExchange.product_name }
														<!-- 교환 진행 상태 및 일시 출력 start -->
														<c:choose>
														<c:when test="${orderExchange.exchange_state == '신청' }">
														<div class="manage-o__text u-c-silver">${orderExchange.payment_state} ${orderExchange.exchange_state }</div>
														<div class="manage-o__text u-c-silver">신청일 : ${orderExchange.exchange_application_date}</div>
														</c:when>
														<c:when test="${orderExchange.exchange_state == '확인' }">
														<div class="manage-o__text u-c-silver">${orderExchange.payment_state} ${orderExchange.exchange_state }</div>
														<div class="manage-o__text u-c-silver">접수일 : ${orderExchange.exchange_check_date }</div>
														</c:when>
														<c:when test="${orderExchange.exchange_state == '완료' }">
														<div class="manage-o__text u-c-silver">${orderExchange.payment_state} ${orderExchange.exchange_state }</div>
														<div class="manage-o__text u-c-silver"><span style="color: #57da74">완료일 : ${orderExchange.exchange_complete_date }</span></div>
														</c:when>
														</c:choose>
														<!-- 교환 진행 상태 및 일시 출력 end -->
														</div>
														
													</div>
													<div class="description__info-wrap">
															<div class="dash__link dash__link--brand">
																<a href="./cancelandrefuntdetail?detailed_order_no=${orderExchange.detailed_order_no}">상세조회</a>
															</div>
														<div>
															<span class="manage-o__text-2 u-c-silver">개수 : <span
																class="manage-o__text-2 u-c-secondary">${orderExchange.quantity}개 </span></span>
														</div>
														<div>

															<span class="manage-o__text-2 u-c-silver">가격: <span
																class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${orderExchange.product_price * orderExchange.quantity}" type="number"/>원</span></span>
														</div>
													</div>
												</div>
												<hr>
												</c:forEach>
												<!-- 교환 상품 표시 end -->
											</div>
										</div>
									</div>
									</c:if>
									<!-- 교환 상품 표시 영역 end -->
									
									
									<!-- 취소 반품 상품 표시 영역 start -->
									<c:if test="${!empty orderCancelAndReturnList}">
									<div class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
										<div class="dash__pad-2">
											<div class="manage-o">
												<div class="manage-o__header u-s-m-b-30">
													<div class="manage-o__icon">
														<i class="fas fa-box u-s-m-r-5"></i> <span
															class="manage-o__text">취소 / 반품  ${orderCancelAndReturnList.size()}</span>
													</div>
												</div>
												
												<!-- 취소 반품 상품 표시 start -->
												<c:forEach var="orderCancelAndReturn" items="${orderCancelAndReturnList }">
												<div class="manage-o__description">
													<div class="description__container">
														<div class="description__img-wrap">
															<img class="u-img-fluid" src="${orderCancelAndReturn.product_main_image } " alt="orderItemImg">
														</div>
														<div class="description-title">${orderCancelAndReturn.product_name }
														<!-- 취소 반품 진행 상태 및 일시 출력 start -->
														<c:choose>
														<c:when test="${orderCancelAndReturn.cancel_state == '신청' }">
														<div class="manage-o__text u-c-silver">${orderCancelAndReturn.what} ${orderCancelAndReturn.cancel_state }</div>
														<div class="manage-o__text u-c-silver">신청일 : ${orderCancelAndReturn.cancel_application_date }</div>
														</c:when>
														<c:when test="${orderCancelAndReturn.cancel_state == '확인' }">
														<div class="manage-o__text u-c-silver">${orderCancelAndReturn.what} ${orderCancelAndReturn.cancel_state }</div>
														<div class="manage-o__text u-c-silver">접수일 : ${orderCancelAndReturn.cancel_check_date }</div>
														</c:when>
														<c:when test="${orderCancelAndReturn.cancel_state == '완료' }">
														<div class="manage-o__text u-c-silver">${orderCancelAndReturn.what} ${orderCancelAndReturn.cancel_state }</div>
														<div class="manage-o__text u-c-silver"><span style="color: #57da74">완료일 : ${orderCancelAndReturn.cancel_complete_date }</span></div>
														</c:when>
														<c:otherwise>
														<div class="manage-o__text u-c-silver">${orderCancelAndReturn.what} ${orderCancelAndReturn.cancel_state }</div>
														</c:otherwise>
														</c:choose>
														<!-- 취소 반품 진행 상태 및 일시 출력 end -->
														</div>
														
													</div>
													<div class="description__info-wrap">
														<div>
															<div class="dash__link dash__link--brand">
																<a href="./cancelandrefuntdetail?detailed_order_no=${orderCancelAndReturn.detailed_order_no}">상세조회</a>
															</div>
															<span class="manage-o__text-2 u-c-silver">개수: <span
																class="manage-o__text-2 u-c-secondary">${orderCancelAndReturn.quantity}개 </span></span>
														</div>
														<div>

															<span class="manage-o__text-2 u-c-silver">가격: <span
																class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${orderCancelAndReturn.product_price * orderCancelAndReturn.quantity}" type="number"/>원</span></span>
														</div>
													</div>
												</div>
												<hr>
												</c:forEach>
												<!-- 취소 반품 상품 표시 end -->
											</div>
										</div>
									</div>
									</c:if>
									<!-- 취소 반품 상품 표시 영역 end -->
									<div class="row">
										<div class="col-lg-6">
											<div class="dash__box dash__box--bg-white dash__box--shadow u-s-m-b-30">
												<div class="dash__pad-3">
													<h2 class="dash__h2 u-s-m-b-8">배송지 정보</h2>
													<span class="dash__text-2">수신자 : ${orderInfo.recipient }</span>
													<span class="dash__text-2">연락처 : ${orderInfo.recipient_phone} </span> 
													<span class="dash__text-2">배송지 : ${orderInfo.recipient_address} (${orderInfo.recipient_zip_code})</span> 
												</div>
											</div>
											<div
												class="dash__box dash__box--bg-white dash__box--shadow dash__box--w">
												<div class="dash__pad-3">
													<h2 class="dash__h2 u-s-m-b-8">결제 정보</h2>
													<c:choose>
													<c:when test="${orderInfo.total_pay_money == orderInfo.point_pay_money }">
													<span class="dash__text-2">포인트 사용</span>
													</c:when>
													<c:when test="${orderInfo.card_brand == null }">
													<!-- 가상 계좌 -->
													<!-- 입금 전 -->
													<c:if test="${orderInfo.depositor_name == null && orderInfo.deposit_time == null }">
													<span class="dash__text-2">결제 수단 : 가상계좌결제(무통장입금)</span>
													<span class="dash__text-2">입금 은행 : ${orderInfo.virtual_account_bank_brand}</span>
													<span class="dash__text-2">입금 계좌번호 : ${orderInfo.virtual_account_number}</span>
													<span class="dash__text-2">입금 기한 : ${orderInfo.deposit_deadline }</span>
													</c:if>
													<!-- 입금 후 -->
													<c:if test="${orderInfo.depositor_name != null && orderInfo.deposit_time !=null }">
													<span class="dash__text-2">결제 수단 : 가상계좌결제</span>
													<span class="dash__text-2">입금 은행 : ${orderInfo.virtual_account_bank_brand}</span>
													<span class="dash__text-2">입금자명 : ${orderInfo.depositor_name }</span>
													<span class="dash__text-2">입금 계좌번호 : ${orderInfo.virtual_account_number}</span>
													<span class="dash__text-2">입금 시간 : ${orderInfo.deposit_time }</span>
													</c:if>
													</c:when>
													<c:when test="${orderInfo.card_brand != null }">
													<!-- 카드 & 간편결제 -->
													<span class="dash__text-2">결제 수단 : 카드 & 간편결제</span>
													<span class="dash__text-2">카드 번호 : ${orderInfo.card_number}(${orderInfo.card_brand})</span>
													<span class="dash__text-2">결제 일시 : ${orderInfo.order_day}</span>
													</c:when>
												
													
													</c:choose>
												</div>
											</div>
										</div>
									<div class="col-lg-6">
                                 <div class="dash__box dash__box--bg-white dash__box--shadow u-h-100">
                                    <div class="dash__pad-3">
                                       <h2 class="dash__h2 u-s-m-b-8">영수증</h2>
                                       <br/>
                                       
                                       <div class="dash-l-r u-s-m-b-8">
                                          <div class="manage-o__text-2 u-c-secondary">물품 가격</div>
                                          <div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${orderInfo.total_product_price}" type="number"/>원</div>
                                       </div>
                                       <div class="dash-l-r u-s-m-b-8">
                                          <div class="manage-o__text-2 u-c-secondary">배송비</div>
                                          <div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${orderInfo.post_money}" type="number"/>원</div>
                                       </div>
                                        <div class="dash-l-r u-s-m-b-8" id="totalDiscount" style="cursor: pointer;">
                                          <div class="manage-o__text-2 u-c-secondary">
                                             <span style="color: red">할인 합계</span>
                                          </div>
                                          <div class="manage-o__text-2 u-c-secondary"">
                                          <c:if test="${orderInfo.post_money > 0 }">
                                             <span style="color: red">-<fmt:formatNumber value="${(orderInfo.total_product_price - (orderInfo.total_pay_money - orderInfo.post_money)) + orderInfo.point_pay_money }" type="number"/>원</span>
                                          </c:if>
                                           <c:if test="${orderInfo.post_money == 0 }">
                                             <span style="color: red">-<fmt:formatNumber value="${(orderInfo.total_product_price - orderInfo.total_pay_money) + orderInfo.point_pay_money }" type="number"/>원</span>
                                          </c:if>
                                          </div>
                                       </div>
                                       <div id="totalDiscountInfo">
                                       <div class="dash-l-r u-s-m-b-8">
                                          <div class="manage-o__text-2 u-c-secondary">
                                             <span style="color: red">쿠폰 할인</span>
                                          </div>
                                          <div class="manage-o__text-2 u-c-secondary"">
                                          	<c:if test="${orderInfo.post_money > 0 }">
	                                          	<c:set var="totalDiscountPrice" value="${(orderInfo.total_product_price - (orderInfo.total_pay_money - orderInfo.post_money)) + orderInfo.point_pay_money }"/>
                                          	</c:if>
                                          	 <c:if test="${orderInfo.post_money == 0 }">
                                          	 	<c:set var="totalDiscountPrice" value="${(orderInfo.total_product_price - orderInfo.total_pay_money) + orderInfo.point_pay_money }"/>
                                          	 </c:if>
                                             <span style="color: red">-<fmt:formatNumber value="${totalDiscountPrice - orderInfo.point_pay_money   }" type="currency" currencySymbol=""/>원</span>
                                          </div>
                                       </div>
                                       <div class="dash-l-r u-s-m-b-8">
                                          <div class="manage-o__text-2 u-c-secondary">
                                             <span style="color: red">적립금</span>
                                          </div>
                                          <div class="manage-o__text-2 u-c-secondary"">
                                             <span style="color: red">-<fmt:formatNumber value="${orderInfo.point_pay_money}" type="number"/>원</span>
                                          </div>
                                       </div>
                                       
                                     
                                       </div>
                                       <c:if test="${orderInfo.cancel_money > 0}">
                                       <div class="dash-l-r u-s-m-b-8">
                                          <div class="manage-o__text-2 u-c-secondary">
                                             <span style="color: red">환불</span>
                                          </div>
                                          <div class="manage-o__text-2 u-c-secondary"">
                                             <span style="color: red">-<fmt:formatNumber value="${orderInfo.cancel_money}" type="number"/>원</span>
                                          </div>
                                       </div>
                                       </c:if>
                                       <hr/>
                                       <div class="dash-l-r u-s-m-b-8">
                                          <div class="manage-o__text-2 u-c-secondary total_price" style="font-size: 15px">결제 금액</div>
                                          <div class="manage-o__text-2 u-c-secondary total_price" style="font-size: 15px"><fmt:formatNumber value="${orderInfo.total_pay_money - orderInfo.point_pay_money - orderInfo.cancel_money   }" type="currency" currencySymbol=""/>원</div>
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
		</div>
		</div>
		<!--====== End - App Content ======-->
		
		<!--====== Modal Section ======-->
				
		<!--====== Add to Cart Modal ======-->
        <div class="modal fade" id="confirmPurchaseModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content modal-radius modal-shadow">

                    <!-- <button class="btn dismiss-button fas fa-times" type="button" data-dismiss="modal"></button> -->
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-6 col-md-12">
                                <div class="success u-s-m-b-30">
                                    <div class="success__text-wrap"><i class="fas fa-check"></i>

                                        <span>구매 확정</span></div>
                                    <div class="success__img-wrap">

                                        <img class="u-img-fluid"  id="modalProductImg" src="" alt=""></div>
                                    <div class="success__info-wrap">
	
                                        <span class="success__name" id="modalProductName">상품명</span>

                                       <!--  <span class="success__quantity">Quantity: 1</span> -->

                                        <span class="success__price" id="modalProductPrice">$170.00</span></div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-12">
                                <div class="s-option">

                                    <div class="s-option__link-box" style="padding-top: 25px">
										
                                    <span class="s-option__text">※구매 확정 후 변경 불가.※</span>
                                    	<div id="modalProductDetailedOrderNo" hidden="true"></div>
                                        <a class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal" id="confirmPurchaseButton" onclick="sendDetailedOrderNo()">확정</a>
                                        <a class="s-option__link btn--e-white-brand-shadow" href="cart.html" data-dismiss="modal" >취소</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!--====== End - Add to Cart Modal ======-->
		
		
		<!--====== End - Modal Section ======-->
	</div>
	<!--====== End - Main App ======-->

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


	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
