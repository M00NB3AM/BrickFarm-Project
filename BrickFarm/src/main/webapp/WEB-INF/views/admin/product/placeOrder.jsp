<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/user/images/logo/logo-1.png" />
<title>발주 관리</title>
<link
	href="${contextPath}/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet">
<!-- Datatable -->
<link
	href="${contextPath}/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
	rel="stylesheet">
<link
	href="${contextPath}/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<style>
.form-control-sm {
	height: calc(1.5em + 0.5rem + 2px) !important;
}

.filter-option-inner-inner, .form-control-sm.form-control {
	font-size: 16px;
}

.row {
	align-items: center;
}

.place-order-table>tbody {
	position: relative;
}

.place-order-table tr th:nth-child(2), .place-order-table tr th:nth-child(4),
	.place-order-table tr th:nth-child(6), .place-order-table tr th:nth-child(7)
	{
	width: 6%;
}

.place-order-table tr th:nth-child(5) {
	width: 10%;
}

.search-result {
	margin-top: 30px;
	font-size: 14px;
	text-align: center;
}

td.product_name {
	display: block;
	width: 250px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	word-break: break-all;
	text-align: left;
	overflow: hidden;
}

.product_name td {
	text-align: left;
}

.product_img {
	width: 70px;
	height: 70px;
	object-fit: contain;
	display: inline-block;
	margin-right: 10px;
}

.modal-body th, .modal-body td {
	border-top: none !important;
}

.modal-body tr {
	border-top: 1px solid #eee;
}

.thumbnail-box {
	margin-right: 30px;
	display: inline-block;
	position: relative;
}

.thumbnail {
	display: inline-block;
	position: relative;
	width: 100px;
	height: 100px;
	object-fit: contain;
}

.search-modal tr th:nth-child(4){
	width: 10%;
}

.search-modal tr th:nth-child(5){
	width: 5%;
}

.search-modal tr th:nth-child(6){
	width: 12%;
}

.search-modal tr th:nth-child(10){
	width: 6%;
}

.result-table td {
	text-align: center;
}

.result-table td:first-child, .result-table td:nth-child(3) {
	text-align: left;
}

.pic-delete {
	right: -20px;
	border: none;
	background: none;
	position: absolute;
}

.no-result {
	height: 200px;
}

.no-result p {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	margin-top: 5%;
}

.search-modal .no-result p {
	top: 70%;
}

.read-btn {
	display: flex !important;
	align-items: center;
	justify-content: center;
}

#lego {
	position: fixed;
	width: 150px;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	z-index: 999;
}
</style>
</head>
<body>
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<div id="preloader">
		<img id="lego" src="${contextPath}/resources/admin/images/lego.gif"
			alt="" />
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>발주 관리</h4>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">상품</a></li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">발주 관리</a></li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<!-- Nav tabs -->
								<div class="custom-tab-1">
									<ul class="nav nav-tabs">
										<li class="nav-item"><a class="nav-link active"
											data-toggle="tab" href="#home1">발주 관리</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#profile1">발주 등록</a></li>
									</ul>
									<div class="tab-content pt-4">
										<div class="tab-pane fade show active" id="home1"
											role="tabpanel">
											<div class="table-responsive">
												<table
													class="table table-responsive-sm overflow-hidden search-table">
													<tbody>
														<tr>
															<th class="col-2">검색어</th>
															<td class="row">
																<div class="col-2">
																	<select id="inputState search-word"
																		class="form-control form-control-sm search-word">
																		<option value="all" selected>전체</option>
																		<option value="place_order_no">발주 번호</option>
																		<option value="product_name">상품</option>
																	</select>
																</div>
																<div class="col-6">
																	<input type="text"
																		class="form-control-sm form-control search-word">
																</div>
															</td>
														</tr>
														<tr>
															<th>발주 날짜</th>
															<td class="row">
																<div class="col-3">
																	<div class="example">
																		<input
																			class="form-control form-control-sm input-daterange-datepicker place-order-date"
																			type="text" name="daterange" autocomplete="off"
																			value="">
																	</div>
																</div>
															</td>
														</tr>
														<tr>
															<th>상태</th>
															<td class="row">
																<div class="col-2">
																	<div class="custom-control custom-checkbox">
																		<input type="checkbox"
																			class="custom-control-input all" id="checkStatusAll"
																			name="Status" onclick="checkAll('Status')"> <label
																			class="custom-control-label" for="checkStatusAll">전체</label>
																	</div>
																</div>
																<div class="col-2">
																	<div class="custom-control custom-checkbox">
																		<input type="checkbox"
																			class="custom-control-input wait" id="wait"
																			name="Status" onclick="checkTest('Status')">
																		<label class="custom-control-label" for="wait">발주
																			대기</label>
																	</div>
																</div>

																<div class="col-2">
																	<div class="custom-control custom-checkbox">
																		<input type="checkbox"
																			class="custom-control-input complete" id="complete"
																			name="Status" onclick="checkTest('Status')">
																		<label class="custom-control-label" for="complete">발주
																			완료</label>
																	</div>
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
											<div class="d-flex justify-content-center">
												<button type="button"
													class="col-1 btn btn-primary btn-sm mr-2"
													onclick="getSearchedPlaceOrders()">검색</button>
												<button type="button"
													class="col-1 btn btn-outline-light btn-sm"
													onclick="resetCondition()">초기화</button>
											</div>
											<div class="card-header">
												<h4 class="card-title">발주 목록</h4>
											</div>
											<div class="card-body">
												<div class="table-responsive">
													<div class="d-flex mb-3">
														<button type="button"
															class="btn btn-outline-light btn-sm mr-2"
															onclick="deleteSelectedOrder()">선택 삭제</button>
													</div>
													<table class="table table-responsive-md place-order-table">
														<thead>
															<tr>
																<th style="width: 50px;">
																	<div
																		class="custom-control custom-checkbox check-lg mr-3">
																		<input type="checkbox" class="custom-control-input"
																			id="checkPlaceOrderAll"
																			onclick="checkAll('PlaceOrder')"> <label
																			class="custom-control-label" for="checkPlaceOrderAll"></label>
																	</div>
																</th>
																<th class="text-center">발주번호</th>
																<th class="text-center">상품명</th>
																<th class="text-center">발주수량</th>
																<th class="text-center">발주가액</th>
																<th class="text-center">상태</th>
																<th class="text-center">관리</th>
															</tr>
														</thead>
														<tbody>
															<c:choose>
																<c:when test="${placeOrderList.size() > 0}">
																	<c:forEach var="order" items="${placeOrderList}"
																		varStatus="status">
																		<c:set var="price" value="${order.total_price}" />
																		<fmt:formatDate var="dateTempParse" pattern="yy-MM-dd"
																			value="${order.placed_date}" />
																		<tr>
																			<td>
																				<div
																					class="custom-control custom-checkbox  check-lg mr-3">
																					<input type="checkbox"
																						class="custom-control-input place_order_no"
																						id="checkbox-${order.product_code}-${order.place_order_no}"
																						name=PlaceOrder onclick="checkTest('PlaceOrder')">
																					<label class="custom-control-label"
																						for="checkbox-${order.product_code}-${order.place_order_no}"></label>
																				</div>
																			</td>
																			<td class="text-center">${order.place_order_no}</td>
																			<td>${order.product_name}</td>
																			<td class="text-center">${order.quantity}</td>
																			<td class="price text-center">${String.format("%,d", price)}
																				원</td>
																			<c:choose>
																				<c:when test="${order.is_placed == 'Y'}">
																					<td class="text-center">완료</td>
																					<td>
																						<div class="d-flex justify-content-center">
																							<a href="#" data-toggle="modal"
																								data-target=".view-detail"
																								class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
																								onclick="getPlaceOrder(${order.place_order_no})"><i
																								class="fab fa-readme"></i></a>
																						</div>
																					</td>
																				</c:when>
																				<c:otherwise>
																					<td class="text-center">대기 (${dateTempParse})</td>
																					<td>
																						<div class="d-flex justify-content-center">
																							<a href="#" data-toggle="modal"
																								data-target=".bd-example-modal-lg"
																								class="btn btn-primary shadow btn-xs sharp mr-1"
																								onclick="getPlaceOrder(${order.place_order_no})"><i
																								class="fa fa-pencil"></i></a>
																						</div>
																					</td>
																				</c:otherwise>
																			</c:choose>
																		</tr>
																	</c:forEach>
																</c:when>
																<c:when test="${placeOrderList.size() == 0}">
																	<td class="no-result">
																		<p>발주 목록이 없습니다.</p>
																	</td>
																</c:when>
															</c:choose>
														</tbody>
													</table>
												</div>
												<nav class="paging">
													<c:if test="${placeOrderList.size() > 0}">
														<ul
															class="pagination pagination-gutter justify-content-center mt-3">
															<c:if test="${pagingInfo.pageNo != 1}">
																<li class="page-item page-indicator"><a
																	class="page-link"
																	href="placeOrder?pageNo=${param.pageNo-1}"><i
																		class="la la-angle-left"></i> </a></li>
															</c:if>
															<c:forEach var="i"
																begin="${pagingInfo.startNumOfCurrentPagingBlock}"
																end="${pagingInfo.endNumOfCurrentPagingBlock}">
																<c:choose>
																	<c:when test="${pagingInfo.pageNo == i}">
																		<li class="page-item active"><a class="page-link"
																			href="placeOrder?pageNo=${i}">${i}</a></li>
																	</c:when>
																	<c:otherwise>
																		<li class="page-item"><a class="page-link"
																			href="placeOrder?pageNo=${i}">${i}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<c:if
																test="${pagingInfo.pageNo < pagingInfo.totalPageCnt}">
																<li class="page-item page-indicator"><a
																	class="page-link"
																	href="placeOrder?pageNo=${pagingInfo.pageNo+1}"><i
																		class="la la-angle-right"></i> </a></li>
															</c:if>
														</ul>
													</c:if>
												</nav>
											</div>
										</div>
										<div class="tab-pane fade" id="profile1">
											<form class="pt-2 result-form-valide">
												<div class="d-flex">
													<button type="button"
														class="btn btn-outline-light btn-sm mr-2"
														onclick="deleteProducts()">선택 삭제</button>
													<button type="button" class="btn btn-primary btn-sm"
														data-toggle="modal" data-target=".search-modal"
														onclick="getProductList(1)">상품 검색</button>
												</div>
												<div class="card-body">
													<div class="table-responsive">
														<table
															class="table table table-responsive-md result-table"
															style="min-width: 845px">
														</table>
													</div>
												</div>
												<div class="row order-recipe flex-column">
													<div class="col-lg-4 col-sm-5"></div>
													<div class="col-lg-4 col-sm-5 ml-auto pr-5">
														<table class="table table-clear">
															<tbody>
																<tr>
																	<td class="left"><strong>수량</strong></td>
																	<td class="right text-center total-quantity">0</td>
																</tr>
																<tr>
																	<td class="left"><strong>총액</strong></td>
																	<td class="right text-center total-price">0</td>
																</tr>
															</tbody>
														</table>
													</div>
													<button type="submit" class="btn btn-primary btn-sm">발주
														상품 등록</button>
												</div>
											</form>
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
	<!-- 발주 수정 모달 -->
	<div class="modal fade bd-example-modal-lg detail-modal" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content modify-order-form-valide">
				<div class="modal-header">
					<h5 class="modal-title">발주 수정</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md modify-table">
						<thead>
							<tr>
								<th class="text-center">발주 번호</th>
								<th class="text-center">상품명</th>
								<th class="text-center">발주 수량</th>
								<th class="text-center">발주 가격</th>
								<th class="text-center">발주 취소</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">수정</button>
				</div>
			</form>
		</div>
	</div>
	<!-- 발주 조회 -->
	<div class="modal fade bd-example-modal-lg view-detail" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">발주 조회</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md modify-table">
						<thead>
							<tr>
								<th class="text-center">발주 번호</th>
								<th class="text-center">상품명</th>
								<th class="text-center">발주 수량</th>
								<th class="text-center">상품 가격</th>
								<th class="text-center">발주 취소</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal">닫기</button>
				</div>
			</form>
		</div>
	</div>
	<!-- 상품 검색 모달 -->
	<!-- Modal -->
	<div class="modal fade bd-example-modal-xl search-modal" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">상품 검색</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md">
						<tbody>
							<tr>
								<th class="col-2">검색어</th>
								<td class="row">
									<div class="col-2">
										<select id="inputState"
											class="form-control form-control-sm inputState search-word">
											<option value="all">전체</option>
											<option value="product_code" selected>상품코드</option>
											<option value="product_name">상품명</option>
										</select>
									</div>
									<div class="col-6">
										<input type="text"
											class="form-control-sm form-control search-word">
									</div>
								</td>
							</tr>
							<tr>
								<th>카테고리</th>
								<td class="row search-category">
									<div class="col-2">
										<select class="first-category form-control form-control-sm">
											<option value="" selected>1차 분류</option>
											<option value="1">이동수단</option>
										</select>
									</div>
									<div class="col-2">
										<select class="second-category form-control form-control-sm">
											<option value="" selected>2차 분류</option>
										</select>
									</div>
									<div class="col-2">
										<select class="third-category form-control form-control-sm">
											<option value="" selected>3차 분류</option>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th>등록일</th>
								<td class="row">
									<div class="col-4">
										<div class="example">
											<input
												class="form-control form-control-sm input-daterange-datepicker product_regist_date"
												type="text" name="daterange" autocomplete="off" value="">
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>판매 상태</th>
								<td class="row" id="sale-status">
									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input all"
												id="checkStatusAll" onclick="checkAll('Status')"> <label
												class="custom-control-label" for="checkStatusAll">전체</label>
										</div>
									</div>
									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input display"
												id="check-normal" name="Status"
												onclick="checkTest('Status')"> <label
												class="custom-control-label" for="check-normal">판매 중</label>
										</div>
									</div>

									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input is-event"
												id="check-sold-out" name="Status"
												onclick="checkTest('Status')"> <label
												class="custom-control-label" for="check-sold-out">이벤트 중</label>
										</div>
									</div>

									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input is-new"
												id="check-in-stock" name="Status"
												onclick="checkTest('Status')"> <label
												class="custom-control-label" for="check-in-stock">신상품</label>
										</div>
									</div>
									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox"
												class="custom-control-input not-display"
												id="check-stop-selling" name="Status"
												onclick="checkTest('Status')"> <label
												class="custom-control-label" for="check-stop-selling">판매
												중지</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>가격</th>
								<td class="row" id="price">
									<div class="col-1">
										<input type="text"
											class="d-inline form-control-sm form-control minimum-price">

									</div> ₩ -
									<div class="col-1">
										<input type="text"
											class="d-inline form-control-sm form-control maximum-price">

									</div> ₩
								</td>
							</tr>
							<tr>
								<th>재고</th>
								<td class="row">
									<div class="col-2">
										<select id="inputState"
											class="form-control form-control-sm stock">
											<option value="stock_quantity" selected>재고</option>
											<option value="safety_stock_quantity">안전재고</option>
										</select>
									</div>
									<div class="col-1">
										<input type="text"
											class="d-inline form-control-sm form-control minimum-stock">

									</div> 개 -
									<div class="col-1">
										<input type="text"
											class="d-inline form-control-sm form-control maximum-stock">

									</div> 개
								</td>
							</tr>
						</tbody>
					</table>
					<div class="d-flex justify-content-center search-btns">
						<button type="button"
							class="col-1 btn btn-primary btn-sm mr-2 submit"
							onclick="getSearchedProducts()">검색</button>
						<button type="button"
							class="col-1 btn btn-outline-light btn-sm reset"
							onclick="resetProductsCondition()">초기화</button>
					</div>
					<table class="table table-responsive-md search-result">
						<tbody>
						</tbody>
					</table>
					<nav class="paging"></nav>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal" onclick="resetProductsCondition()">닫기</button>
					<button type="button" class="btn btn-primary"
						onclick="choiceProduct()">선택</button>
				</div>
			</div>
		</div>
	</div>
	<!--**********************************
        Scripts
    ***********************************-->
	<!-- Daterangepicker -->
	<!-- momment js is must -->
	<script
		src="${contextPath}/resources/admin/vendor/moment/moment.min.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>
	<!-- clockpicker -->
	<script
		src="${contextPath}/resources/admin/vendor/clockpicker/js/bootstrap-clockpicker.min.js"></script>
	<!-- asColorPicker -->
	<script
		src="${contextPath}/resources/admin/vendor/jquery-asColor/jquery-asColor.min.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/jquery-asGradient/jquery-asGradient.min.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/jquery-asColorPicker/js/jquery-asColorPicker.min.js"></script>
	<!-- Material color picker -->
	<script
		src="${contextPath}/resources/admin/vendor/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
	<!-- pickdate -->
	<script src="${contextPath}/resources/admin/vendor/pickadate/picker.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/pickadate/picker.time.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/pickadate/picker.date.js"></script>



	<!-- Daterangepicker -->
	<script
		src="${contextPath}/resources/admin/js/plugins-init/bs-daterange-picker-init.js"></script>
	<!-- Datatable -->
	<script
		src="${contextPath}/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${contextPath}/resources/admin/js/plugins-init/datatables.init.js"></script>

	<!-- Jquery Validation -->
	<script
		src="${contextPath}/resources/admin/vendor/jquery-validation/jquery.validate.min.js"></script>
	<!-- Form validate init -->
	<script
		src="${contextPath}/resources/admin/js/sjy/jquery.validate-init.js"></script>

	<script type="text/javascript">
	let categoryList = [];
	let searchedProductList = [];
	let orderList = [];
	let eventList = [];
	let pagingSearch = {};
	let pagingSearchProduct = {};
	
	function deleteSelectedOrder() {
  		let selectedOrder = [];
  		if($('.place-order-table .place_order_no:checked').length > 0){
  			$('.place-order-table .place_order_no:checked').each(function(index, item){
				let place_order_no = $(item).attr('id').split('checkbox-')[1].split('-')[1];
				
				selectedOrder.push(place_order_no);
			});
	  		
			Swal.fire({
			      type: "info",
			      title: `선택한 발주를 취소하시겠습니까?`,
			      html: `선택한 발주 : \${selectedOrder} <br/> 취소한 발주는 되돌릴 수 없습니다.`,
			      showCancelButton: true,
			      confirmButtonColor: '#3085d6',
			      cancelButtonColor: '#aaa',
			      confirmButtonText: '확인',
			      cancelButtonText: '닫기'
			    }).then((result) => {
			      if (result.value == true) {
			    	  jQuery.ajax({
			  			type : "POST",
			  			url : `/admin/product/deleteSelectedOrder`,
			  			data : JSON.stringify(selectedOrder),
			  			contentType: "application/json",
			  			success : function(data) {
			  				swal("완료", "취소가 완료되었습니다.", "success").then(() => {
			  			      	location.reload();})
			  			},
			  			error : function(e) {
			  				swal("실패", "발주 취소 실패하였습니다.", "error");
			  			}, timeout:100000
			  		});  
			      }
			    })
  		} else{
  			swal("실패", "삭제할 발주를 선택해 주세요.", "error");
  		}
  		
  	}
	
	function deleteOrder(placeOrderNo, productCode){
		let order = {
				place_order_no : placeOrderNo,
				product_code : productCode
		};
		Swal.fire({
		      type: "warning",
		      title: '발주를 취소 하시겠습니까?',
		      text: "취소한 발주는 되돌릴 수 없습니다.",
		      showCancelButton: true,
		      confirmButtonColor: 'rgb(221, 107, 85)',
		      cancelButtonColor: '#aaa',
		      confirmButtonText: '삭제',
		      cancelButtonText: '닫기'
		    }).then((result) => {
			      if (result.value == true) {
			    	  jQuery.ajax({
			  			type : "POST",
			  			url : `/admin/product/deleteOrder`,
			  			data : JSON.stringify(order),
			  			contentType: "application/json",
			  			success : function(data) {
			  				swal("완료", "취소가 완료되었습니다.", "success").then(() => {
			  			      	location.reload();})
			  			},
			  			error : function(e) {
			  				swal("실패", "발주 취소 실패하였습니다.", "error");
			  			}, timeout:100000
			  		});  
			      }
			    })
	};
	
	function modifyPlaceOrder() {
		let modifyOrderList = [];
		$('.quantity').each(function(index, item){
			let order ={
				place_order_no : $(item).parent().parent().attr('id'),
				product_code : $(item).attr('id'),
				quantity : parseInt($(item).val())
			};
			modifyOrderList.push(order);
		});
		
		$.ajax({
			url : `/admin/product/modifyPlaceOrder`,
			type : 'post',
			data : JSON.stringify(modifyOrderList),
			async : false,
			contentType: "application/json",
			success : function(data) {
				successAlert('발주가 수정');
			},
			error : function(request, error) {
				failAlert('발주 수정 ');
				console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
			}
		});
	}
	
	function addPlaceOrder() {
		let products = [];
		$(orderList).each(function(index, item){
			let product =  {
					"product_code" : item,
					"quantity" : $(`#product-\${item} input.quantity`).val(),
			};
			if(product.quantity <= 0){
				failAlert('발주 등록 ');
				return;
			} else{
				products.push(product);
			}
		});
		
		$.ajax({
			url : `/admin/product/addPlaceOrder`,
			type : 'post',
			data : JSON.stringify(products),
			async : false,
			contentType: "application/json",
			success : function(data) {
				successAlert('발주가 등록');
			},
			error : function(request, error) {
				failAlert('발주 등록 ');
				console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
			}
		});
	}
	
	function deleteProducts() {
		$('.result-table .product_code:checked').each(function(index, item){
			let product_code = $(item).attr('id').split('check-box-')[1];
			
			$(orderList).each(function(index, item){
				if(item == product_code){
					orderList.splice(index, 1);
					$(`#product-\${item}`).remove();
				}
			});
		});
		if(orderList.length == 0){
			$('.result-table').empty();
			$('.order-recipe').empty();
		}
    }
	
	function displayProducts(choiceProducts) {
		let output = ``;
		if(orderList.length == 0){
			output += `<thead>
                  <tr>
                  <th class="col-1">
                    <div class="custom-control custom-checkbox">
                      <input type="checkbox" class="custom-control-input"
                        id="checkProductAll" onclick="checkAll('Product')">
                      <label class="custom-control-label" for="checkProductAll"></label>
                    </div>
                  </th>
                  <th class="text-center col-1">상품 번호</th>
                  <th class="text-center col-5">상품명</th>
                  <th class="text-center">재고</th>
                  <th class="text-center col-1">수량</th>
                  <th class="text-center">금액</th>
                  </tr>
                </thead>
                <tbody>`;
			$(choiceProducts).each(function(index, item) {
				orderList.push(item.product_code);
				output +=`<tr id="product-\${item.product_code}">
							<td>
								<div class="custom-control custom-checkbox ml-2">
									<input type="checkbox" class="custom-control-input product_code \${item.product_code}"
										id="check-box-\${item.product_code}" name="Product"
										onclick="checkTest('Product')"> <label
										class="custom-control-label"
										for="check-box-\${item.product_code}"></label>
								</div>
							</td>
							<td>\${item.product_code}</td>
							<td><img class="product_img"
								src="\${item.product_main_image}">\${item.product_name}
							</td>
							<td>\${item.stock_quantity}</td>
							<td class="form-group"><input type="number" value="0" class="form-control form-control-sm quantity" name="quantity-\${item.product_code}" onkeyup="sumQuantity()"  oninput="sumQuantity()" min="1"/></td>
							<td>\${item.product_price.toLocaleString()}</td>
						</tr>`;
				});
				output += '</tbody>'
		$('.result-table').append(output);
		} else {
			$(choiceProducts).each(function(index, item) {
				orderList.push(item.product_code);
				output +=`<tr id="product-\${item.product_code}">
							<td>
								<div class="custom-control custom-checkbox ml-2">
									<input type="checkbox" class="custom-control-input product_code \${item.product_code}"
										id="check-box-\${item.product_code}" name="Product"
										onclick="checkTest('Product')"> <label
										class="custom-control-label"
										for="check-box-\${item.product_code}"></label>
								</div>
							</td>
							<td>\${item.product_code}</td>
							<td><img class="product_img"
								src="\${item.product_main_image}">\${item.product_name}
							</td>
							<td>\${item.stock_quantity}</td>
							<td><input type="number" value="0" class="form-control form-control-sm quantity" onkeyup="sumQuantity()"  oninput="sumQuantity()"/></td>
							<td>\${item.product_price.toLocaleString()}</td>
						</tr>`;
				});
			$('.result-table>tbody').append(output);
		}
		$('.order-recipe').show();
	}
	
	function displaySearchedResult(productList, pagingInfo){
		  let output = `<thead>
							<tr>
								<th class="text-center">
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											id="checkSearchedProductAll" onclick="checkAll('SearchedProduct')">
										<label class="custom-control-label" for="checkSearchedProductAll"></label>
									</div>
								</th>
								<th class="text-center">코드</th>
								<th class="text-center">상품명</th>
								<th class="text-center">발주가</th>
								<th class="text-center">재고</th>
								<th class="text-center">[안전재고]</th>
								<th class="text-center">진열 상태</th>
								<th class="text-center">이벤트 여부</th>
								<th class="text-center">판매 여부</th>
								<th class="text-center">등록일</th>
							</tr>
					  	</thead>
					  	<tbody>`;
		  $(productList).each(function(index, item){
			  let price = item.stock_price;
			  let equal = 0;
			  let product = {
					  	"product_code" : item.product_code,
						"product_name" : item.product_name,
						"stock_quantity" : item.stock_quantity,
						"product_main_image" : item.product_main_image,
						"product_price" : price
					  };
			  searchedProductList.push(product);		
			  if(!orderList.includes(item.product_code)){
				  output += `
					  <tr id="\${item.product_code}">
						<td>
							<div class="custom-control custom-checkbox ml-2">
								<input type="checkbox" class="custom-control-input product_code \${item.product_code}"
									id="check-box-\${item.product_code}" name="SearchedProduct"
									onclick="checkTest('SearchedProduct')"> <label
									class="custom-control-label"
									for="check-box-\${item.product_code}"></label>
							</div>
						</td>
						<td>\${item.product_code}</td>
						<td class="product_name"><img class="product_img"
							src="\${item.product_main_image}">\${item.product_name}</td>
						<td class="price">\${price.toLocaleString()}원</td>
						<td class="stock_quatity">\${item.stock_quantity}</td>
						<td>[\${item.safety_stock_quantity}]</td>
						`;
					if(item.display == 'Y') {
						output +=`<td>
									<span class="badge light badge-info"> <i
									class="fa fa-circle text-info mr-1"></i> 진열 중
								  	</span>
								  </td>`;
					} else {
						output +=`<td>
									<span class="badge light badge-danger"> <i
									class="fa fa-circle text-danger mr-1"></i> 진열 중지
								  	</span>
								  </td>`;
					}
					if(item.event_no != 0) {
						let event_name = '';
						$(eventList).each(function(index, event){
							if(item.event_no == event.event_no) {
								event_name = event.event_name;
							}
						});
						output +=`<td>
									<span class="badge light badge-success"> <i
										class="fa fa-circle text-success mr-1"></i> \${event_name}
									</span>
								  </td>`;
					} else {
						output +=`<td>
									<span class="badge light badge-secondary"> <i
										class="fa fa-circle text-secondary mr-1"></i> 일반 판매
									</span>
								  </td>`;
					}
					if(item.is_new == 'Y') {
						output +=`<td>
									<span class="badge light badge-primary"> <i
										class="fa fa-circle text-primary mr-1"></i> 신상품
									</span>
								  </td>`;
					} else {
						output +=`<td>
									<span class="badge light badge-secondary"> <i
										class="fa fa-circle text-secondary mr-1"></i> 일반 상품
									</span>
								  </td>`;
					}
				output += `<td>${dateTempParse}</td>
							</tr>`;
			  	} else {
			  		output += `
						  <tr class="table-secondary" id="\${item.product_code}">
							<td>
								<div class="custom-control custom-checkbox ml-2">
									<input type="checkbox" class="custom-control-input product_code \${item.product_code}"
										id="check-box-\${item.product_code}" name="Product"
										disabled> <label
										class="custom-control-label"
										for="check-box-\${item.product_code}"></label>
								</div>
							</td>
							<td>\${item.product_code}</td>
							<td class="product_name"><img class="product_img"
								src="\${item.product_main_image}">\${item.product_name}</td>
							<td class="price">\${price}원</td>
							<td class="stock_quatity">\${item.stock_quantity}</td>
							<td>[\${item.safety_stock_quantity}]</td>
							`;
						if(item.display == 'Y') {
							output +=`<td>
										<span class="badge light badge-success"> <i
										class="fa fa-circle text-success mr-1"></i> 진열 중
									  	</span>
									  </td>`;
						} else {
							output +=`<td>
										<span class="badge light badge-danger"> <i
										class="fa fa-circle text-danger mr-1"></i> 진열 중지
									  	</span>
									  </td>`;
						}
						if(item.event_no != 0) {
							let event_name = '';
							$(eventList).each(function(index, event){
								if(item.event_no == event.event_no) {
									event_name = event.event_name;
								}
							});
							output +=`<td>
										<span class="badge light badge-success"> <i
											class="fa fa-circle text-success mr-1"></i> \${event_name}
										</span>
									  </td>`;
						} else {
							output +=`<td>
										<span class="badge light badge-danger"> <i
											class="fa fa-circle text-danger mr-1"></i> 일반 판매
										</span>
									  </td>`;
						}
						if(item.is_new == 'Y') {
							output +=`<td>
										<span class="badge light badge-success"> <i
											class="fa fa-circle text-success mr-1"></i> 신상품
										</span>
									  </td>`;
						} else {
							output +=`<td>
										<span class="badge light badge-danger"> <i
											class="fa fa-circle text-danger mr-1"></i> 일반 상품
										</span>
									  </td>`;
						}
					output += `<td>${dateTempParse}</td>
								</tr>`;
			  	}
			});
		  output += `</tbody>`;
		  let paging = `
	  			<ul
				class="pagination pagination-gutter justify-content-center mt-3">`;
			if(pagingInfo.pageNo != 1){
				paging += `<li class="page-item page-indicator"><a class="page-link"
					href='javascript:void(0)' onclick="getProductList(\${pagingInfo.pageNo-1})"><i
						class="la la-angle-left"></i></a></li>`;
			}
			for (let i = pagingInfo.startNumOfCurrentPagingBlock; i <= pagingInfo.endNumOfCurrentPagingBlock; i++) {
				 if(pagingInfo.pageNo == i) {
					 paging += `<li class="page-item active"><a class="page-link"
						 href='javascript:void(0)' onclick="getProductList(\${i})">\${i}</a></li>`
				 } else {
					 paging += `<li class="page-item"><a class="page-link"
						 href='javascript:void(0)' onclick="getProductList(\${i})">\${i}</a></li>`
				 }
			};
			if(pagingInfo.pageNo < pagingInfo.totalPageCnt){
				paging += `<li class="page-item page-indicator"><a class="page-link"
					href='javascript:void(0)' onclick="getProductList(\${pagingInfo.pageNo+1})"><i
					class="la la-angle-right"></i> </a></li>`;
			}
			paging += `</ul>`;
	  	  $('.search-modal .paging').html(paging);
	  	  if(productList.length == 0){
			  output =`<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
		  }
		  $('.search-result').html(output);
	  }
	
		function getSearchedPlaceOrders(){
			  let min_date = '';
			  let max_date = '';
			  
			  if($("input.place-order-date").val() != ''){
				  min_date = ($("input.place-order-date").val()).split(' ~')[0];
				  max_date = ($("input.place-order-date").val()).split('~ ')[1];
			  }
			  
			  let searchCondition = {
				"search_type" : $("select.search-word option:selected").val(),
				"search_word" : $("input.search-word").val(),
				"placed_date" : {
					"min_date" : min_date,
					"max_date" : max_date,
				},
				"is_placed" : {
					"all" : $("input.all").is(":checked") && "Y" ,
					"wait" : $("input.wait").is(":checked") && "Y",
					"complete" : $("input.complete").is(":checked") && "Y",
				},
			  };
			  
			  $.ajax({
					url : `/admin/product/placeOrder`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(searchCondition),
					async : false,
					contentType: "application/json",
					success : function(data) {
						pagingSearch = data.searchCondition;
						displayPaging(data.pagingInfo, "getSearchedPlaceOrdersByPage");
						displayResult(data.placeOrderList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		}
		
		function getSearchedPlaceOrdersByPage(pageNo){
			  $.ajax({
					url : `/admin/product/placeOrder?pageNo=\${pageNo}`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(pagingSearch),
					async : false,
					contentType: "application/json",
					success : function(data) {
						displayPaging(data.pagingInfo, "getSearchedPlaceOrdersByPage");
						displayResult(data.placeOrderList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		}
	
		function displayResult(placeOrderList){
			let output = ``;
			$(placeOrderList).each(function(index, item){
				let price = item.total_price.toLocaleString();
				let d = new Date(item.placed_date);
				let date = [d.getFullYear().toString().substr(-2), ('0' + (d.getMonth() + 1)).slice(-2),
					('0' + d.getDate()).slice(-2)
					].join('-');
				output += `
						<tr>
							<td>
								<div
									class="custom-control custom-checkbox check-lg mr-3">
									<input type="checkbox"
										class="custom-control-input place_order_no"
										id="checkbox-\${item.product_code}-\${item.place_order_no}"
										name=PlaceOrder onclick="checkTest('PlaceOrder')">
									<label class="custom-control-label"
										for="checkbox-\${item.product_code}-\${item.place_order_no}"></label>
								</div>
							</td>
							<td class="text-center">\${item.place_order_no}</td>
							<td>\${item.product_name}</td>
							<td class="text-center">\${item.quantity}</td>
							<td class="price text-center">\${price} 원</td>`;
					if(item.is_placed == 'Y'){
						output += `<td class="text-center">완료</td>
								   <td>
								   	<div class="d-flex justify-content-center">
										<a href="#" data-toggle="modal"
											data-target=".view-detail"
											class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
											onclick="getPlaceOrder(\${item.place_order_no})"><i
											class="fab fa-readme"></i></a>
									</div>
								  </td>
		                      </tr>`;
					} else {
						output += `<td class="text-center">대기 (\${date})</td>
                      <td class="text-center">
                    <div class="d-flex justify-content-center">
                      <a href="#"
                        class="btn btn-primary shadow btn-xs sharp mr-1"
                        onClick="getPlaceOrder(\${item.place_order_no})"
                        data-toggle="modal" data-target=".bd-example-modal-lg"><i
                        class="fa fa-pencil"></i></a>
                      </div>
                    </td>
                    </tr>`;
					}
				});
          if(placeOrderList.length == 0){
            output +=`<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
            $('.paging').empty();
          }
          
          $('.place-order-table > tbody').html(output);
		}
	
		function resetCondition() {
			$('.search-table input').val('');
			$("select.search-word").val("all").prop("selected", true);
			$(".search-table input").prop("checked", false);
			$('select').selectpicker('refresh');
			$('input.place-order-date').data('daterangepicker').setStartDate(new Date());
			$('input.place-order-date').data('daterangepicker').setEndDate(new Date());
			$("input.place-order-date").val("");
		};
		
		function getPlaceOrder(no) {
			let place_order_no = no;
			$.ajax({
				url : `/admin/product/placeOrder/\${place_order_no}`,
				type : 'get',
				dataType : 'json',
				async : false,
				success : function(data) {
					displayDetails(data.placeOrderList);
				},
				error : function() {
					alert("error 발생");
				}
			});
	  	};
	  	
	  	function displayDetails(list){
	  		let output = ``;
	  		let price = 0;
	  		$(list).each(function(index, item){
	  			price = item.total_price / item.quantity;
				if(item.is_placed == "N"){
					output += `<tr id="\${item.place_order_no}">
									<td class="text-center">\${item.place_order_no}</td>
									<td>\${item.product_name}</td>
									<td class="col-2 form-group"><input type="number" value="\${item.quantity}" class="form-control form-control-sm quantity" name="quantity-\${item.product_code}" id="\${item.product_code}" min="1"></input></td>
									<td class="price col-2 text-center" min="0">\${price.toLocaleString()} 원</td>
									<td class="text-center">
										<a href="javascript:void(0)" class="btn btn-danger shadow btn-xs sharp confirmDelete" onclick="deleteOrder(\${item.place_order_no}, \${item.product_code})">
										<i class="fa fa-trash"></i>
										</a>
									</td>
								</tr>`;
				} else {
					output += `<tr id="\${item.place_order_no}">
									<td class="text-center">\${item.place_order_no}</td>
									<td>\${item.product_name}</td>
									<td class="col-2 text-center">\${item.quantity}</td>
									<td class="price col-2 text-center" min="0">\${price.toLocaleString()} 원</td>
									<td class="text-center">
										<a href="javascript:void(0)" class="btn btn-danger shadow btn-xs sharp disabled">
										<i class="fa fa-trash"></i>
										</a>
									</td>
								</tr>`;
				}
				output += ``;
	  		});
	  		
	  		$('.modify-table > tbody').html(output);
	  	};
		
		function sumQuantity(){
			let totQuantity = 0;
			let totPrice = 0;
			
			$('input.quantity').each(function(index, item){
				totQuantity += parseInt($(this).val());
				totPrice += parseInt($(this).val()) * parseInt(($(this).parent('td').next().html()).replace(/,/g, ""));
			})
			
			$('.total-quantity').html(totQuantity + ' 개');
			$('.total-price').html(totPrice.toLocaleString() + ' 원');
		};

		$(document).ready(function() {
			$('.order-recipe').hide();
			getCategory();
			getEvent();
			displayCategory('.search-category');
			
			$("input.place-order-date").val("");
			
			$('.product_regist_date').daterangepicker({
	            "locale": {
	                "format": "YYYY-MM-DD",
	                "separator": " ~ ",
	                "applyLabel": "확인",
	                "cancelLabel": "취소",
	                "fromLabel": "From",
	                "toLabel": "To",
	                "customRangeLabel": "Custom",
	                "weekLabel": "W",
	                "daysOfWeek": ["월", "화", "수", "목", "금", "토", "일"],
	                "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
	            },
	            startDate: new Date(), 
	            endDate: new Date(),
	            "firstDay": 1,
	            "drops": "down",
	            "parentEl": $('.modal') ,
	        });
			
			jQuery.validator.addClassRules("quantity", {
					required: !0,
		            min: 1,
		            digits: !0
				}); 
			
			jQuery.extend(jQuery.validator.messages, {
				required: "수량을 입력해주세요.",
				min: "발주 수량은 1개 이상이어야 합니다.",
	            digits : "숫자를 입력해주세요.",
			});
				
		});
	</script>
	<script
		src="${contextPath}/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.js"></script>
	<script
		src="${contextPath}/resources/admin/js/plugins-init/sweetalert.init.js"></script>
	<script type="text/javascript"
		src="${contextPath}/resources/admin/js/sjy/common.js"></script>
</body>
</html>