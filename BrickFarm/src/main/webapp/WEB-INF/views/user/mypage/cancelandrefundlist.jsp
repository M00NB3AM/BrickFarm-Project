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
<script type="text/javascript">
const params = {
		'curPageNo': ${pagingInfo.curPageNo},
		'rowCountPerPage': ${pagingInfo.rowCountPerPage},
		'searchState': '${searchInfo.searchState}'
}
$(function() {
	   const searchStateSelect = document.getElementById('searchState');
	   for (let i = 0; i < searchStateSelect.options.length; i++) {
	       if (searchStateSelect.options[i].value == params.searchState) {
	           searchStateSelect.selectedIndex = i;
	           break;
	       }
	   }
})


function submitForm(selected) {
	let form = $("#formAction");
	console.log("선택된 value : " + selected.value)
	console.log("stateParam의 value : " +$("#searchState").val())
	params.searchState = $("#searchState").val();
	let url = `/mypage/cancelandrefundlist?searchState=\${params.searchState}&pageNo=1`
	location.href = url;
}
function selectPage(pageNo) {
	params.curPageNo = pageNo;
	console.log(params.curPageNo);
	let url = `/mypage/cancelandrefundlist?searchState=\${params.searchState}&pageNo=\${params.curPageNo}`
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
									<li class="is-marked"><a>취소 / 교환 / 반품 내역</a></li>
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
											<h1 class="dash__h1 u-s-m-b-14">취소 /교환 / 반품 내역</h1>
											
										<form class="m-order u-s-m-b-30" action="/mypage/cancelandrefundlist" id = "formAction">
												<div class="m-order__select-wrapper">
													<!-- 
													<label class="u-s-m-r-8" for="my-order-sort">기간:</label>
													<select class="select-box select-box--primary-style"
														id="searchPeriod" name = "searchPeriod" onchange="submitForm(this)">
														<option selected value = "all">전체</option>
														<option value = "5">5일</option>
														<option value = "15">15일</option>
														<option value = "30">30일</option>
													</select> 
													 -->
													<label class="u-s-m-r-8" for="my-order-sort">&nbsp;&nbsp;상태:</label>
													<select class="select-box select-box--primary-style"
														id="searchState" name = "searchState" onchange="submitForm(this)">
														<option value = "allPaymentState" >전체</option>
														<option value = "cancel">취소</option>
														<option value = "exchange">교환</option>
														<option value = "return">반품</option>
													</select>
												</div>
											</form>
											<div class="m-order__list">
												<c:choose>
													<c:when test="${!empty cancelAndRefundList}">
														<c:forEach var="cancelAndRefundOrder"
															items="${ cancelAndRefundList}">
															<div class="m-order__get">
																<div class="manage-o__header u-s-m-b-30">
																	<div class="dash-l-r">
																		<div>
																			<c:choose>
																				<c:when
																					test="${cancelAndRefundOrder.payment_state == '취소'}">
																					<div class="manage-o__text-2 u-c-secondary">#${cancelAndRefundOrder.payment_state}</div>
																					<div class="manage-o__text u-c-silver">신청일 :
																						${cancelAndRefundOrder.cancel_application_date }</div>
																				</c:when>
																				<c:when
																					test="${cancelAndRefundOrder.payment_state == '교환'}">
																					<div class="manage-o__text-2 u-c-secondary">#${cancelAndRefundOrder.payment_state}</div>
																					<div class="manage-o__text u-c-silver">신청일 :
																						${cancelAndRefundOrder.exchange_application_date }</div>
																				</c:when>
																				<c:when
																					test="${cancelAndRefundOrder.payment_state == '반품'}">
																					<div class="manage-o__text-2 u-c-secondary">#${cancelAndRefundOrder.payment_state}</div>
																					<div class="manage-o__text u-c-silver">신청일 :
																						${cancelAndRefundOrder.cancel_application_date }</div>
																				</c:when>
																			</c:choose>
																		</div>
																		<div>
																			<div class="dash__link dash__link--brand">

																				<a href="./cancelandrefuntdetail?detailed_order_no=${cancelAndRefundOrder.detailed_order_no }">상세조회</a>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="manage-o__description">
																	<div class="description__container">
																		<div class="description__img-wrap">

																			<img class="u-img-fluid"
																				src="${cancelAndRefundOrder.product_main_image }" alt="">
																		</div>
																		
																				<div class="description-title">${cancelAndRefundOrder.product_name }
																				</div>
												
																	</div>
																	<div class="description__info-wrap">

																		<div>

																			<span class="manage-o__text-2 u-c-silver">개수:

																				<span class="manage-o__text-2 u-c-secondary">${cancelAndRefundOrder.quantity}</span>
																			</span>
																		</div>
																		<div>

																			<span class="manage-o__text-2 u-c-silver">총
																				가격: <span class="manage-o__text-2 u-c-secondary"><fmt:formatNumber
																						value="${cancelAndRefundOrder.product_price * cancelAndRefundOrder.quantity}"
																						pattern="#,###" />원</span>
																			</span>
																		</div>
																	</div>
																</div>
															</div>
														</c:forEach>
													</c:when>
												<c:otherwise>
                                                <h4 class="dash__h4 u-s-p-xy-20" style="color: gray; text-align: center;">주문 내역이 없습니다.</h4>
                                                </c:otherwise>
												</c:choose>
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
