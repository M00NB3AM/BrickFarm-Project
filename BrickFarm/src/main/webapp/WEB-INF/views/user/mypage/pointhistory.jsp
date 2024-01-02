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
.hr1 {
	margin-top: 20px;
	margin-bottom: 20px;
	border: 0;
	height: 1px;
	background-color: #ff4500;
}
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
								  <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
								<li class="is-marked"><a>적립금 내역</a></li>
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
									class="dash__box dash__box--shadow dash__box--bg-white dash__box--radius ">
									<h1 class="dash__h1 u-s-p-xy-20">포인트 적립 내역</h1>
									<c:if test="${loginMemberInfo.accrual_amount > 0}">
									<h2 class="dash__h2 u-s-p-xy-20">보유 포인트 : <fmt:formatNumber value="${loginMemberInfo.accrual_amount }" type="currency" currencySymbol=""/>원</h2>
									</c:if>
									<c:choose>
									<c:when test="${!empty pointsAccrualLog}">
									<div class="dash__table-wrap gl-scroll">
										<table class="dash__table">
											<thead>
												<tr>
													<th>적립 사유</th>
													<th>적립일</th>
													<th>적립 금액</th>
													<th>상세보기</th>
												</tr>
											</thead>
											<tbody>
									
											<c:forEach var="pointsAccrualLog" items="${pointsAccrualLog }">
												<tr>
													<td>${pointsAccrualLog.points_accrual_reason }</td>
													<td>${pointsAccrualLog.accrual_date}</td>
													<td><fmt:formatNumber value="${pointsAccrualLog.accrual_log_amount }" type="currency" currencySymbol=""/>원</td>
													<td>
														<div class="dash__table-total">
															<div class="dash__link dash__link--brand">
																<a href="./orderdetail?merchant_uid=${pointsAccrualLog.merchant_uid }">조회</a>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
									</div>
									</c:when>
											
											<c:otherwise>
											<br>
											<div style="text-align: center;"><b>포인트 적립 내역이 없습니다.</b></div>
											<br>										
											</c:otherwise>
											</c:choose>
								</div>
								<hr class="hr1">
								<div
									class="dash__box dash__box--shadow dash__box--bg-white dash__box--radius ">
									<h1 class="dash__h1 u-s-p-xy-20">포인트 사용/반환 내역</h1>
									<c:if test="${pointsUsageLog[0].total_usage_amount > 0 }">
									<h2 class="dash__h2 u-s-p-xy-20">사용 포인트 : <fmt:formatNumber value="${pointsUsageLog[0].total_usage_amount }" type="currency" currencySymbol=""/>원</h2>
									</c:if>
									<c:choose>
									<c:when test="${!empty pointsUsageLog}">
									<div class="dash__table-wrap gl-scroll">
										<table class="dash__table">
											<thead>
												<tr>
													<th>주문 번호</th>
													<th>일자</th>
													<th>금액</th>
													<th>사용/반환</th>
													<th>상세보기</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach var="pointsUsageLog" items="${pointsUsageLog}">
												<tr>
													<td>${pointsUsageLog.merchant_uid }</td>
													<td>${pointsUsageLog.usage_date}</td>
													<c:if test="${pointsUsageLog.usage_amount > 0}">
													<td><fmt:formatNumber value="${pointsUsageLog.usage_amount }" type="currency" currencySymbol="-"/>원</td>
														<td>사용</td>
													</c:if>
													<c:if test="${pointsUsageLog.usage_amount < 0}">
													<td><span style="color: green"><fmt:formatNumber value="${-pointsUsageLog.usage_amount }" type="currency" currencySymbol="+"/>원</span></td>
														<td style="color: green">반환</td>
													</c:if>
													
													<td>
														<div class="dash__table-total">
															<div class="dash__link dash__link--brand">
																<a href="./orderdetail?merchant_uid=${pointsUsageLog.merchant_uid }">조회</a>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
												
											</tbody>
										</table>
									</div>
									</c:when>
									<c:otherwise>
											<br>
											<div style="text-align: center;"><b>포인트 사용/반환 내역이 없습니다.</b></div>
											<br>										
											</c:otherwise>
									</c:choose>
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
