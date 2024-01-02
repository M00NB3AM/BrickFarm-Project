<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
<meta charset="UTF-8" />
<title>Brick Farm</title>
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
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
.a{
padding: 5px;
}
</style>
<script type="text/javascript">
function openModal(button) {
	let addressBookNo = $(button).data('address-book-no');
	let addressBookIsDefault = $(button).data('address-is-default');
	$('input[name=addressBookNo]').attr('value', addressBookNo);
	$('input[name=addressBookIsdefault]').attr('value', addressBookIsDefault);
}
</script>
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
									<li class="is-marked"><a>배송지 관리</a></li>
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
											<div class="dash__address-header">
												<h1 class="dash__h1">배송지 관리</h1>
												<div>

												</div>
											</div>
										</div>
									</div>
									<div class="dash__box dash__box--shadow dash__box--bg-white dash__box--radius u-s-m-b-30">
										<div class="dash__table-wrap gl-scroll" style="height: 650px">
											<table class="dash__table-2">
												<thead>
													<tr>
														<th style="text-align: center;">배송지</th>
														<th style="text-align: center;">주소</th>
														<th style="text-align: center;">연락처</th>
														<th style="text-align: center;">상태</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="addressBook" items="${addressBookList }">
													<tr>
														<!-- 
														<c:choose>
														<c:when test='${addressBook.is_default == "Y" }'>
														<td style="text-align: center;">
															<div class="gl-text">기본 배송지</div>
														</td>
														</c:when>
														<c:otherwise>
														<td></td>
														</c:otherwise>
														</c:choose>
														 -->
														<td style="text-align: center;">
															<h2 class="dash__h2 u-s-m-b-8">${addressBook.recipient}</h2>
															<span class="dash__text-2">${addressBook.address_name }</span>
														</td>
														<td style="text-align: center;">${addressBook.address}</td>
														<td style="text-align: center;">${addressBook.phone_number}</td>
														
															<td style="text-align: center;">
															<c:choose>
															<c:when test='${addressBook.is_default == "Y" }'>
																<div class="gl-text"><span style="color: #ff4500"><i class="fas fa-truck u-s-m-r-5"></i>기본 배송지</span></div>
															</c:when>
															<c:otherwise>
															</c:otherwise>
															</c:choose>
															<form action="/mypage/editaddresslistform" method="post">
															<input type="hidden" value="${addressBook.member_address_book_no}" name="member_address_book_no" />
																<button type="submit" style="cursor: pointer;"
																	class="address-book-edit btn--e-transparent-platinum-b-2"
																	>수정</button>
																&nbsp
															<c:choose>	
															<c:when test= '${addressBook.is_default == "Y" }'>
															<button type="button" style="cursor: pointer;"
															class="address-book-edit btn--e-transparent-platinum-b-2"
															data-modal="modal" data-modal-id="#notDeleteModal"
															data-address-book-no = "${addressBook.member_address_book_no}"
															data-address-is-default = "${addressBook.is_default}"
															><span style="color: red">삭제</span></button>
															</c:when>
															<c:otherwise>
															<button type="button" style="cursor: pointer;"
															class="address-book-edit btn--e-transparent-platinum-b-2"
															data-modal="modal" data-modal-id="#confirmDeleteModal"
															data-address-book-no = "${addressBook.member_address_book_no}"
															data-address-is-default = "${addressBook.is_default}"
															onclick="openModal(this)"
															><span style="color: red">삭제</span></button>
															</c:otherwise>
															</c:choose>
															
													</form>
													</td>
														
													</tr>
													
													</c:forEach>
													
												</tbody>
											</table>
										</div>
									</div>
									<div style="display: flex; justify-content: flex-end;">
										<a class="dash__custom-link btn--e-brand-b-2"
											href="/mypage/addaddresslist"><i class="fas fa-plus u-s-m-r-8"></i>

											<span>배송지 등록</span></a>
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
				
		<!--====== Add to Cart Modal ======-->
        <div class="modal fade" id="confirmDeleteModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content modal-radius modal-shadow" style="border-radius: 2rem;">

                    <!-- <button class="btn dismiss-button fas fa-times" type="button" data-dismiss="modal"></button> -->
                    <div class="modal-body">
                        <h3 style="text-align: center; padding-bottom: 50px ">배송지를 삭제하시겠습니까?</h3>
                        <div class="row">
                            <div class="col-lg-6 col-md-12">
                                <div class="s-option">
									<form action="/mypage/deleteaddresslist" method="post">
									<input id="addressBookNo" type="hidden" name = "addressBookNo">
									<input id="addressBookIsdefault" type="hidden" name = "addressBookIsdefault">
                                    <div class="s-option__link-box">
                                    <button type="submit" class="s-option__link btn--e-white-brand-shadow" style="cursor: pointer;">삭제</button>
                                	</div>
									</form>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-12">
                        <div class="s-option">
                        <div class="s-option__link-box">
                        <button class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal" style="cursor: pointer;" >취소</button>
                        </div>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		</div>
		
		 <div class="modal fade" id="notDeleteModal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content modal-radius modal-shadow" style="border-radius: 2rem;">

                    <!-- <button class="btn dismiss-button fas fa-times" type="button" data-dismiss="modal"></button> -->
                    <div class="modal-body">
                        <h3 style="text-align: center; padding-bottom: 50px ">
                        	다른 배송지를<br>
							기본 배송지로 변경 후
							삭제해주세요.</h3>
                        <div class="col-lg-6 col-md-6" style="display: contents">
                        <div class="s-option">
                        <div class="s-option__link-box">
                        <a class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal" >확인</a>
                        </div>
                        </div>
                        </div>
                </div>
            </div>
        </div>
		</div>
			<!--====== End - Modal Section ======-->
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
		<script src="https://www.google-analytics.com/analytics.js" async
			defer></script>

	<!--====== Vendor Js ======-->
	<script src="/resources/user/js/vendor.js"></script>

	<!--====== jQuery Shopnav plugin ======-->
	<script src="/resources/user/js/jquery.shopnav.js"></script>

	<!--====== App ======-->
	<script src="/resources/user/js/app.js"></script>


		<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
