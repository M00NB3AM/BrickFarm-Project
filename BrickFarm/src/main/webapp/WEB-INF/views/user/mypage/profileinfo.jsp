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
<style type="text/css">
</style>
</head>

<body class="config">
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<jsp:include page="../header.jsp"></jsp:include>


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
								<li class="is-marked"><a>마이페이지</a></li>
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
								<div
									class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30">
									<div class="dash__pad-2">
										<h1 class="dash__h1 u-s-m-b-14">내 정보 </h1>
										<span class="dash__text u-s-m-b-30" id="regist_date">가입일 : ${loginMemberInfo.member_regist_date }
											</span>
										<div class="row">
											<div class="col-lg-4 u-s-m-b-30">
												<div
													class="dash__box dash__box--bg-grey dash__box--shadow-2 u-h-100">
													<div class="dash__pad-3">
														<h2 class="dash__h2 u-s-m-b-8">내 정보</h2>
														<div class="dash__link dash__link--secondary u-s-m-b-8">
														<span class="dash__text">${loginMemberInfo.member_name} <a href="/notice/membership" style="color: #ff4500">(${loginMemberInfo.member_grade_name}회원)</a></span>
														<span class="dash__text">${loginMemberInfo.email}</span>
															<a href="/mypage/editprofile">Edit</a>
														</div>
														<span class="dash__text">적립금 : <fmt:formatNumber value="${loginMemberInfo.accrual_amount}" type="currency" currencySymbol=""/>원
															</span> <span class="dash__text">보유쿠폰 : ${loginMemberInfo.coupon_count}장</span>
													</div>
												</div>
											</div>
											<div class="col-lg-4 u-s-m-b-30">
												<div
													class="dash__box dash__box--bg-grey dash__box--shadow-2 u-h-100">
													<div class="dash__pad-3">
														<h2 class="dash__h2 u-s-m-b-8">배송지 목록</h2>
														<span class="dash__text">
														<c:forEach var="addressBookName" items="${ addressBookNameList}" varStatus="status" >
														<c:choose>
														<c:when test="${status.last}">
														${addressBookName.address_name}
														</c:when>
														<c:otherwise>
														${addressBookName.address_name},
														</c:otherwise>
														</c:choose>
														</c:forEach>
														</span>
														<div class="dash__link dash__link--secondary u-s-m-b-8">

															<a href="/mypage/addresslist">Edit</a>
														</div>

														<span class="dash__text">기본 배송지 :
															${defaultAddressBook.address}</span> 
															
															<span class="dash__text">기본
															전화번호 : ${defaultAddressBook.phone_number}</span>
													</div>
												</div>
											</div>
											<div class="col-lg-4 u-s-m-b-30">
												<div
													class="dash__box dash__box--bg-grey dash__box--shadow-2 u-h-100">
													<div class="dash__pad-3">
														<h2 class="dash__h2 u-s-m-b-8">내 글</h2>

														<span class="dash__text-2 u-s-m-b-8"><a href="customer-service/inquiry/list?isMine=true" style="text-decoration: none; color: #7f7f7f">문의 내역</a></span> 
														<span class="dash__text">답변 완료 문의 : ${answeredInquiryList.size()}건</span>
														<span class="dash__text">답변 대기 중 문의 : ${noAnsweredInquiryList.size()}건</span>
														<br>
														<span class="dash__text-2 u-s-m-b-8">리뷰 내역</span>  
														<span class="dash__text">작성 한 리뷰 : ${reviewCount}건</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div
									class="dash__box dash__box--shadow dash__box--bg-white dash__box--radius">
									<h2 class="dash__h2 u-s-p-xy-20">최근 주문</h2>
									<c:choose>
										<c:when test="${!empty orderList }">
											<div class="dash__table-wrap gl-scroll">
												<table class="dash__table">
													<thead>
														<tr>
															<th>주문번호</th>
															<th>주문일</th>
															<th>상품</th>
															<th>총가격</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="order" items="${orderList}">
															<tr>
																<td>${order.merchant_uid}</td>
																<td>${order.order_day}</td>
																<td>
																	<div class="dash__table-img-wrap">

																		<img class="u-img-fluid"
																			src="${order.product_main_image}" alt="">
																	</div>
																</td>
																<td>
																	<div class="dash__table-total">

																		<span><fmt:formatNumber
																				value="${order.total_pay_money}" pattern="#,###" />원</span>
																		<div class="dash__link dash__link--brand">

																			<a
																				href="/mypage/orderdetail?merchant_uid=${order.merchant_uid }">조회</a>
																		</div>
																	</div>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
										</c:when>
										<c:otherwise>
										<div class="dash__box dash__box--shadow dash__box--bg-white dash__box--radius">
										<h4 class="dash__h4 u-s-p-xy-20" style="color: gray; text-align: center;">주문 내역이 없습니다.</h4>
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
		</div>
		<!--====== End - Section Content ======-->
	</div>
	<!--====== End - Section 2 ======-->
	</div>
	<!--====== End - App Content ======-->

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
