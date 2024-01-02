<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript">
const params = {
		'curPageNo': ${pagingInfo.curPageNo},
		'rowCountPerPage': ${pagingInfo.rowCountPerPage},
		'totalPageCount': ${pagingInfo.totalPageCount},
		'searchOrder': '${searchInfo.searchOrder}',
		'searchState': '${searchInfo.searchState}'
	}


$(function() {
	   const searchOrderSelect = document.getElementById('searchOrder');
	   const searchStateSelect = document.getElementById('searchState');
	   for (let i = 0; i < searchOrderSelect.options.length; i++) {
	       if (searchOrderSelect.options[i].value == params.searchOrder) {
	    	   searchOrderSelect.selectedIndex = i;
	           break;
	       }
	   }
   })

function submitForm(selected) {
	let form = $("#formAction");
	let currentURL = window.location.href;

    let urlParams = new URLSearchParams(currentURL);

   	let searchState = urlParams.get("searchState")
    
	console.log("선택된 value : " + selected.value)
	console.log("orderParam의 value : " + $("#searchOrder").val())
	console.log("stateParam의 value : " +$("#searchState").val())
	params.searchOrder = $("#searchOrder").val();
	params.searchState = $("#searchState").val();
	let url = `/mypage/couponhistory?searchOrder=\${params.searchOrder}&searchState=\${searchState}&pageNo=1`
	location.href = url;
}

function selectState(selected) {
	console.log(selected)
	params.searchState = selected.id;
	console.log(params.searchState)
	let url = `/mypage/couponhistory?searchOrder=\${params.searchOrder}&searchState=\${params.searchState}&pageNo=1`
	location.href = url;
}

function selectPage(pageNo) {
	params.curPageNo = pageNo;
	console.log(params.curPageNo);
	let url = `/mypage/couponhistory?searchOrder=\${params.searchOrder}&searchState=\${params.searchState}&pageNo=\${params.curPageNo}`
	location.href = url;
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
									<li class="is-marked"><a>쿠폰 내역</a></li>
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
											<h1 class="dash__h1 u-s-m-b-14">쿠폰 내역</h1>
										
											<form class="m-order u-s-m-b-30">
												<div class="m-order__select-wrapper">
												
													<label class="u-s-m-r-8" for="my-order-sort"></label>
													<select class="select-box select-box--primary-style"
														id="searchOrder" name = "searchOrder" onchange="submitForm(this)" style="margin-left: -7px">
														<option selected value = "orderByRecent">최신순</option>
														<option value = "orderByDiscountRate">할인순</option>
													</select> 
													<p>&nbsp&nbsp</p>
													<div class="select badge" id="searchState" >
														<span class="manage-o__badge badge--shipped" style="cursor:pointer;" id="available" onclick="selectState(this)">사용가능</span>
														<span class="manage-o__badge badge--delivered" style="cursor:pointer;" id="notAvailable" onclick="selectState(this)">사용불가</span> 
													</div>

												</div>

											</form>
											
											<c:choose>
											<c:when test="${!empty couponList}">

											<div class="m-order__list">
												
													<c:forEach var="couponInfo" items="${couponList }">
														<div class="m-order__get">
															<div class="manage-o__header">
																<div class="dash-l-r">
																	<div>
																		<div class="manage-o__text-2 u-c-secondary">${couponInfo.coupon_policy_name }</div>
																		<div class="manage-o__text u-c-silver">발급일 : ${couponInfo.published_date }</div>
																	</div>
																	
																	
																	<c:choose>
																	<c:when test="${fn:contains(couponInfo.available_coupon, 'Y') }">
																	<div class="description__info-wrap">
																	<span class="manage-o__badge badge--shipped">사용가능</span>
																		<div class="manage-o__text u-c-silver">만료일 : ${couponInfo.expiration_date}</div>
																	</div>
																	
																	</c:when>
																	
																	<c:when test="${fn:contains(couponInfo.available_coupon, 'N') }">
																	<div class="description__info-wrap">
																	<c:if test="${couponInfo.usage_reason == '사용' }">
																	<span class="manage-o__badge badge--delivered">사용불가</span>
																	<div class="manage-o__text u-c-silver">사용일 ${couponInfo.usage_date }</div>
																	</c:if>
																	<c:if test="${couponInfo.usage_reason == '만료' }">
																	<span class="manage-o__badge badge--processing">기간만료</span>
																	<div class="manage-o__text u-c-silver">만료일 ${couponInfo.expiration_date }</div>
																	</c:if>
																	</div>
																	</c:when>
																		
																	 </c:choose>
																</div>
															</div>
														</div>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<h4 class="dash__h4 u-s-p-xy-20" style="color: gray; text-align: center;">보유한 쿠폰이 없습니다.</h4>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
												<!--====== Pagination ======-->
								<div class="paging">
                    <ul class="shop-p__pagination">
                      <c:choose>
                        <c:when test="${pagingInfo.curPageNo > 1 }">
                          <li><a class="fas fa-angle-left" href="javascript:selectPage(${pagingInfo.curPageNo - 1})"></a></li>
                        </c:when>
                      </c:choose>

                      <c:forEach var="i" begin="${pagingInfo.startPageIndex}" end="${pagingInfo.endPageIndex}" step="1">
                        <c:choose>
                          <c:when test="${pagingInfo.curPageNo == i}">
                            <li class="is-active"><a href="javascript:selectPage(${i})">${i}</a></li>
                          </c:when>
                          <c:otherwise>
                            <li><a href="javascript:selectPage(${i})">${i}</a></li>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>

                      <c:choose>
                        <c:when test="${pagingInfo.totalPageCount > 1 && pagingInfo.curPageNo < pagingInfo.totalPageCount}">
                          <li><a class="fas fa-angle-right" href="javascript:selectPage(${pagingInfo.curPageNo + 1})"></a></li>
                        </c:when>
                      </c:choose>
                    </ul>
                  </div>
									<!--====== End - Pagination ======-->
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
