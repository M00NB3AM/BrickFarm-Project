<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
<meta charset="UTF-8" />
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<script src="https://kit.fontawesome.com/95bfda382d.js"
	crossorigin="anonymous"></script>
<title>Brick Farm</title>
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
									<li class="has-separator"><a href="cancelandrefundlist">취소 / 교환 / 반품 내역</a></li>
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
									<h1 class="dash__h1 u-s-m-b-30">${UserOrderWithdrawal.payment_state }
										조회</h1>
									
									<!-- 주문 번호, 주문 날짜, 총 가격 start -->
									<div
										class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
										<div class="dash__pad-2">
											<div class="dash-l-r">
												<div>
													<div class="manage-o__text-2 u-c-secondary">
														${UserOrderWithdrawal.merchant_uid }
													</div>
													<div class="manage-o__text u-c-silver">
														${UserOrderWithdrawal.order_day}
													</div>
												</div>
												<div>
													<div class="manage-o__text-2 u-c-silver">
														총가격: <span class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
																value="${UserOrderWithdrawal.discounted_price * UserOrderWithdrawal.quantity}"
																type="currency" currencySymbol="" />원</span>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- 주문 번호, 주문 날짜, 총 가격 end -->

									<div
										class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
										<!-- 취소된 상품 출력 영역 start -->
										<div class="dash__pad-2">
											<div class="manage-o">
												<div class="manage-o__header u-s-m-b-30">
													<div class="manage-o__icon">
														<i class="fas fa-box u-s-m-r-5"></i> 
														<span class="manage-o__text">
														${UserOrderWithdrawal.payment_state}
														${UserOrderWithdrawal.quantity}
														</span>
													</div>
													
												</div>
											

												<!-- 배송 상태(delivery_state)에 따른 진행상황 bar 출력 start -->
												
												<c:choose>
												<c:when test="${UserOrderWithdrawal.exchange_state == '신청' || UserOrderWithdrawal.cancel_state == '신청'}">
												<div class="manage-o__timeline">
													<div class="timeline-row">
														
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">
																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">신청</span>
															</div>
														</div>
														
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">접수</span>
															</div>
														</div>
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">완료</span>
															</div>
														</div>
														
													</div>
												</div>
												</c:when>
													<c:when test="${UserOrderWithdrawal.exchange_state == '확인' || UserOrderWithdrawal.cancel_state == '확인'}">
												<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">신청</span>
															</div>
														</div>
														
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">접수</span>
															</div>
														</div>
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">완료</span>
															</div>
														</div>
														
													</div>
												</div>
												</c:when>
												<c:when test="${UserOrderWithdrawal.exchange_state == '완료' || UserOrderWithdrawal.cancel_state == '완료'}">
												<div class="manage-o__timeline">
													<div class="timeline-row">
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">신청</span>
															</div>
														</div>
														
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">접수</span>
															</div>
														</div>
														<div class="col-lg-4 u-s-m-b-30">
															<div class="timeline-step">
																<div class="timeline-l-i timeline-l-i--finish">

																	<span class="timeline-circle"></span>
																</div>

																<span class="timeline-text">완료</span>
															</div>
														</div>
														
													</div>
												</div>
												</c:when>
												</c:choose>
												
												<!-- 진행 상태(cancel_state, exchange_state)에 따른 진행상황 bar 출력 start -->

												<!-- 주문 된 상품 출력 start  -->

												<div class="manage-o__description">
													<div class="description__container">
														<div class="description__img-wrap">

															<img class="u-img-fluid"
																src="${UserOrderWithdrawal.product_main_image } "
																alt="orderItemImg">
														</div>
														<div class="description-title">${UserOrderWithdrawal.product_name }
															<div class="manage-o__text u-c-silver">${UserOrderWithdrawal.payment_state }
															</div>

														</div>

													</div>
													<div class="description__info-wrap">
														<div>

															<span class="manage-o__text-2 u-c-silver">개수: <span
																class="manage-o__text-2 u-c-secondary">${UserOrderWithdrawal.quantity}개
															</span></span>
														</div>
														<div>

															<span class="manage-o__text-2 u-c-silver">가격: <span
																class="manage-o__text-2 u-c-secondary"> <fmt:formatNumber
																		value="${UserOrderWithdrawal.discounted_price * UserOrderWithdrawal.quantity}"
																		type="number" />원
															</span></span>
														</div>
													</div>
												</div>
												<hr>

													
												<!-- 주문 된 상품 출력 end -->
													<!-- 취소 상태(cancel_state)에 따른 주문일 or 배송 시작,완료 일  출력 start -->
												<c:choose>
													<c:when
														test="${UserOrderWithdrawal.payment_state == '취소' || UserOrderWithdrawal.payment_state == '반품'}">
														<c:choose>
															<c:when
																test="${UserOrderWithdrawal.cancel_state == '신청'}">
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary">신청일 :
																		${UserOrderWithdrawal.cancel_application_date }</div>
																</div>
															</c:when>
															<c:when
																test="${UserOrderWithdrawal.cancel_state == '확인'}">
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary">접수일 :
																		${UserOrderWithdrawal.cancel_check_date}</div>
																</div>
															</c:when>
															<c:otherwise>
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary"><span style="color: green">완료일 :
																		${UserOrderWithdrawal.cancel_complete_date }</span></div>
																</div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when
														test="${UserOrderWithdrawal.payment_state == '교환'}">
														<c:choose>
															<c:when
																test="${UserOrderWithdrawal.exchange_state == '신청'}">
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary">신청일 :
																		${UserOrderWithdrawal.exchange_application_date }</div>
																</div>
															</c:when>
															<c:when
																test="${UserOrderWithdrawal.exchange_state == '확인'}">
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary">접수일 :
																		${UserOrderWithdrawal.exchange_check_date}</div>
																</div>
															</c:when>
															<c:when
																test="${UserOrderWithdrawal.exchange_state == '진행중'}">
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary">접수일 :
																		${UserOrderWithdrawal.exchange_check_date}</div>
																</div>
															</c:when>
															<c:otherwise>
																<div class="dash-l-r">
																	<div class="manage-o__text u-c-secondary" ><span style="color: green">완료일 :
																		${UserOrderWithdrawal.exchange_complete_date }</span></div>
																</div>
															</c:otherwise>
														</c:choose>
													</c:when>
												</c:choose>
												<!-- 배송 상태(delivery_state)에 따른 주문일 or 배송 시작,완료 일  출력 end -->

											</div>
										</div>
										<!-- 주문된 상품 출력 영역 end -->
									</div>
									<div class="row">
										<div class="col-lg-6">
											<div class="dash__box dash__box--bg-white dash__box--shadow u-s-m-b-30">
												<div class="dash__pad-3">
												<c:choose>
												<c:when test="${UserOrderWithdrawal.payment_state == '교환' }">
													<h2 class="dash__h2 u-s-m-b-8">배송지 정보</h2>
													<span class="dash__text-2">수신자 : ${UserOrderWithdrawal.recipient }</span>
													<span class="dash__text-2">연락처 : ${UserOrderWithdrawal.recipient_phone} </span> 
													<span class="dash__text-2">배송지 : ${UserOrderWithdrawal.recipient_address} (우편번호 : ${UserOrderWithdrawal.recipient_zip_code})</span>
													<c:if test="${UserOrderWithdrawal.exchange_post_number != null}">
													<span class="dash__text-2">운송장 번호 : ${UserOrderWithdrawal.exchange_post_number}</span>
													</c:if>
												</c:when> 
												<c:otherwise>
													<h2 class="dash__h2 u-s-m-b-8">환불 계좌 정보</h2>
													<span class="dash__text-2">환불 은행 : ${UserOrderWithdrawal.bank_brand }</span>
													<span class="dash__text-2">환불 계좌번호 : ${UserOrderWithdrawal.refund_account} </span> 
												</c:otherwise>
												</c:choose>
												</div>
												</div>
											</div>
											
											<c:if test="${UserOrderWithdrawal.payment_state != '교환' }">
											<div class="col-lg-6">
											<div
												class="dash__box dash__box--bg-white dash__box--shadow u-h-100">
												<div class="dash__pad-3">
													<h2 class="dash__h2 u-s-m-b-8">영수증</h2>
													<br/>
													<div class="dash-l-r u-s-m-b-8">
														<div class="manage-o__text-2 u-c-secondary">물품 가격</div>
														<div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${UserOrderWithdrawal.product_price * UserOrderWithdrawal.quantity}" type="number"/>원</div>
													</div>
													<!-- 
													<div class="dash-l-r u-s-m-b-8">
														<div class="manage-o__text-2 u-c-secondary">배송비</div>
														<div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${UserOrderWithdrawal.add_post_money}" type="number"/>원</div>
													</div>
													 -->
													<div class="dash-l-r u-s-m-b-8">
														<div class="manage-o__text-2 u-c-secondary"><span style="color: red">쿠폰 할인</span></div>
														<div class="manage-o__text-2 u-c-secondary"><span style="color: red">-<fmt:formatNumber value="${UserOrderWithdrawal.product_price - UserOrderWithdrawal.discounted_price}" type="number"/>원</span></div>
													</div>
													<hr>
													<div class="dash-l-r u-s-m-b-8">
														<div class="manage-o__text-2 u-c-secondary total_price" style="font-size: 15px">취소 금액 : </div>
														<div class="manage-o__text-2 u-c-secondary"><fmt:formatNumber value="${UserOrderWithdrawal.cancel_cancel_money}" type="number"/>원</div>
													</div>
												</div>
											</div>
										</div>
										</c:if>
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
		<!--====== End - App Content ======-->

		<!--====== Modal Section ======-->


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
