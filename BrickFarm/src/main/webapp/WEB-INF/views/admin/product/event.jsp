<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/user/images/logo/logo-1.png" />
<title>이벤트 관리</title>
<link
	href="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet" />
<!-- Datatable -->
<link
	href="/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
	rel="stylesheet" />
<link
	href="/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<script type="text/javascript" src="/resources/admin/js/sjy/common.js"></script>
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

.row {
	align-items: center;
}

.search-result {
	margin-top: 30px;
	font-size: 14px;
	text-align: center;
}

td.product_name {
	display: block;
	width: 20vw;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	word-break: break-all;
	text-align: left;
	overflow: hidden;
}

th.product_name {
	width: 20vw;
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

.event-table tbody {
	position: relative;
}

.no-result {
	height: 200px;
	position: relative;
}

.no-result p {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	margin-top: 5%;
}

.event-rate .invalid-feedback {
	text-align: center;
}

.read-btn {
	display: flex;
	align-items: center;
	justify-content: center;
}

.search-modal tr th:nth-child(4) {
	width: 10%;
}

.search-modal tr th:nth-child(5) {
	width: 5%;
}

.search-modal tr th:nth-child(6) {
	width: 12%;
}

.search-modal tr th:nth-child(10) {
	width: 6%;
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
		<img id="lego" src="/resources/admin/images/lego.gif" alt="" />
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>이벤트 관리</h4>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">상품</a>
							</li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">이벤트 관리</a></li>
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
											data-toggle="tab" href="#home1">이벤트 관리</a></li>
										<li class="nav-item"><a class="nav-link"
											data-toggle="tab" href="#profile1">이벤트 등록</a></li>
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
																		<option value="event_no">이벤트 번호</option>
																		<option value="event_name">이벤트 이름</option>
																	</select>
																</div>
																<div class="col-6">
																	<input type="text"
																		class="form-control-sm form-control search-word" />
																</div>
															</td>
														</tr>
														<tr>
															<th>이벤트 기간</th>
															<td class="row">
																<div class="col-3">
																	<div class="example">
																		<input
																			class="form-control form-control-sm input-daterange-datepicker event-date"
																			type="text" name="daterange" autocomplete="off"
																			value="" />
																	</div>
																</div>
															</td>
														</tr>
														<tr>
															<th>할인율</th>
															<td class="row">
																<div class="col-4 flex-nowrap d-flex align-items-center">
																	<input
																		class="form-control form-control-sm col-2 mr-1 min_rate"
																		type="number" value="" /> ~ <input
																		class="form-control form-control-sm col-2 ml-1 max_rate"
																		type="number" value="" />
																</div>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
											<div class="d-flex justify-content-center">
												<button type="button"
													class="col-1 btn btn-primary btn-sm mr-2"
													onclick="getSearchedEvent()">검색</button>
												<button type="button"
													class="col-1 btn btn-outline-light btn-sm"
													onclick="resetSearchCondition()">초기화</button>
											</div>
											<div class="card-header">
												<h4 class="card-title">이벤트 목록</h4>
											</div>
											<div class="card-body">
												<div class="table-responsive">
													<table class="table table-responsive-md event-table">
														<thead>
															<tr>
																<th class="text-center">이벤트 번호</th>
																<th class="text-center">이벤트 이름</th>
																<th class="text-center">이벤트 기간</th>
																<th class="text-center">할인율</th>
																<th class="text-center">관리</th>
															</tr>
														</thead>
														<tbody>
															<c:choose>
																<c:when test="${eventList.size() > 0}">
																	<c:forEach var="event" items="${eventList}"
																		varStatus="status">
																		<c:if test="${event.event_no != 0}">
																			<c:set var="now" value="<%=new java.util.Date()%>" />
																			<fmt:formatDate var="start" pattern="yy-MM-dd"
																				value="${event.event_start}" />
																			<fmt:formatDate var="end" pattern="yy-MM-dd"
																				value="${event.event_end}" />
																			<fmt:formatDate var="date" value="${now}"
																				pattern="yyyy-MM-dd hh:mm:ss" />
																			<fmt:formatNumber var="discount_rate"
																				value="${event.discount_rate}" pattern="###%" />
																			<tr>
																				<td class="text-center">${event.event_no}</td>
																				<td>${event.event_name}</td>
																				<td class="text-center">${start}~${end}</td>
																				<td class="text-center">${discount_rate}</td>
																				<c:choose>
																					<c:when test="${event.event_end < date}">
																						<td>
																							<div class="d-flex justify-content-center">
																								<a href="#" data-toggle="modal"
																									data-target=".view-detail"
																									class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
																									onclick="getEventLog(${event.event_no})"><i
																									class="fab fa-readme"></i></a>
																							</div>
																						</td>
																					</c:when>
																					<c:when test="${event.event_start > date}">
																						<td>
																							<div class="d-flex justify-content-center">
																								<a href="#" data-toggle="modal"
																									data-target=".detail-modal"
																									class="btn btn-primary shadow btn-xs sharp mr-1"
																									onclick="getEventLog(${event.event_no})"><i
																									class="fa fa-pencil"></i></a>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td>
																							<div class="d-flex justify-content-center">
																								<a href="#" data-toggle="modal"
																									data-target=".detail-modal"
																									class="btn btn-primary shadow btn-xs sharp mr-1"
																									onclick="getEvent(${event.event_no})"><i
																									class="fa fa-pencil"></i></a>
																							</div>
																						</td>
																					</c:otherwise>
																				</c:choose>
																			</tr>
																		</c:if>
																	</c:forEach>
																</c:when>
																<c:when test="${eventList.size() == 0}">
																	<td class="no-result">
																		<p>이벤트 목록이 없습니다.</p>
																	</td>
																</c:when>
															</c:choose>
														</tbody>
													</table>
												</div>
												<nav class="paging">
													<c:if test="${eventList.size() > 0}">
														<ul
															class="pagination pagination-gutter justify-content-center mt-3">
															<c:if test="${pagingInfo.pageNo != 1}">
																<li class="page-item page-indicator"><a
																	class="page-link" href="event?pageNo=${param.pageNo-1}"><i
																		class="la la-angle-left"></i> </a></li>
															</c:if>
															<c:forEach var="i"
																begin="${pagingInfo.startNumOfCurrentPagingBlock}"
																end="${pagingInfo.endNumOfCurrentPagingBlock}">
																<c:choose>
																	<c:when test="${pagingInfo.pageNo == i}">
																		<li class="page-item active"><a class="page-link"
																			href="event?pageNo=${i}">${i}</a></li>
																	</c:when>
																	<c:otherwise>
																		<li class="page-item"><a class="page-link"
																			href="event?pageNo=${i}">${i}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<c:if
																test="${pagingInfo.pageNo < pagingInfo.totalPageCnt}">
																<li class="page-item page-indicator"><a
																	class="page-link" href="event?pageNo=${pagingInfo.pageNo+1}"><i
																		class="la la-angle-right"></i> </a></li>
															</c:if>
														</ul>
													</c:if>
												</nav>
											</div>
										</div>
										<div class="tab-pane fade" id="profile1">
											<form class="pt-2 event-form-valide">
												<div class="card-body">
													<div class="table-responsive">
														<table
															class="table table-responsive-sm overflow-hidden add-event-table">
															<tbody>
																<tr>
																	<th class="col-2">이벤트 이름</th>
																	<td class="row">
																		<div class="col-3 form-group">
																			<input type="text"
																				class="form-control-sm form-control event_name"
																				id="event_name" name="event_name" />
																		</div>
																	</td>
																</tr>
																<tr>
																	<th>이벤트 기간</th>
																	<td class="row event-row">
																		<div class="col-3 form-group">
																			<input
																				class="form-control form-control-sm input-daterange-datepicker event-date"
																				id="event-date" type="text" autocomplete="off"
																				name="event-date" />
																		</div>
																	</td>
																</tr>
																<tr>
																	<th class="col-2">할인율</th>
																	<td class="row">
																		<div class="col-3 form-group">
																			<input type="number"
																				class="form-control-sm form-control discount_rate"
																				id="discount_rate" name="discount_rate" min="5" />
																		</div>
																	</td>
																</tr>
																<tr>
																	<th class="col-2">이벤트 상품 등록</th>
																	<td class="row">
																		<div class="col-3">
																			<button type="button" class="btn btn-primary btn-sm"
																				data-toggle="modal" data-target=".search-modal"
																				onclick="getProductList(1)">상품 검색</button>
																		</div>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
												<div class="card-body">
													<button type="button"
														class="btn btn-outline-light btn-sm mr-2"
														onclick="deleteProducts()">선택 삭제</button>
													<div class="table-responsive">
														<table
															class="table table table-responsive-md result-table"
															style="min-width: 845px"></table>
													</div>
												</div>
												<div class="d-flex justify-content-center">
													<button type="submit"
														class="col-1 btn btn-primary btn-sm mr-2">등록</button>
													<button type="button"
														class="col-1 btn btn-outline-light btn-sm"
														onclick="resetAddEventCondition()">초기화</button>
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
	<!-- 이벤트 수정 모달 -->
	<div class="modal fade bd-example-modal-xl detail-modal" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<form class="modal-content modify-event-form-valide">
				<div class="modal-header">
					<h5 class="modal-title">이벤트 수정</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md modify-table">
						<thead>
							<tr>
								<th class="text-center">이벤트 번호</th>
								<th class="text-center">이벤트명</th>
								<th class="text-center">이벤트 기간</th>
								<th class="text-center">할인율</th>
								<th class="text-center">이벤트 취소</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<table class="table table-responsive-md event-product-table mt-5"></table>
					<div class="d-flex justify-content-center event-cancle-box"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">수정</button>
				</div>
			</form>
		</div>
	</div>
	<!-- 이벤트 조회 모달 -->
	<div class="modal fade bd-example-modal-xl view-detail" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<form class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">이벤트 조회</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md modify-table">
						<thead>
							<tr>
								<th class="text-center">이벤트 번호</th>
								<th class="text-center">이벤트명</th>
								<th class="text-center">이벤트 기간</th>
								<th class="text-center">할인율</th>
								<th class="text-center">이벤트 취소</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<table class="table table-responsive-md event-product-table mt-5"></table>
					<div class="d-flex justify-content-center event-cancle-box"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal">닫기</button>
				</div>
			</form>
		</div>
	</div>
	<!-- 상품 검색 모달 -->
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
											class="form-control-sm form-control search-word" />
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
												type="text" autocomplete="off" name="daterange" value="" />
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
												id="checkStatusAll" onclick="checkAll('Status')" /> <label
												class="custom-control-label" for="checkStatusAll">전체</label>
										</div>
									</div>
									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input display"
												id="check-normal" name="Status"
												onclick="checkTest('Status')" /> <label
												class="custom-control-label" for="check-normal">판매 중</label>
										</div>
									</div>

									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input is-event"
												id="check-sold-out" name="Status"
												onclick="checkTest('Status')" /> <label
												class="custom-control-label" for="check-sold-out">이벤트 중</label>
										</div>
									</div>

									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input is-new"
												id="check-in-stock" name="Status"
												onclick="checkTest('Status')" /> <label
												class="custom-control-label" for="check-in-stock">신상품</label>
										</div>
									</div>
									<div class="col-2">
										<div class="custom-control custom-checkbox">
											<input type="checkbox"
												class="custom-control-input not-display"
												id="check-stop-selling" name="Status"
												onclick="checkTest('Status')" /> <label
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
											class="d-inline form-control-sm form-control minimum-price" />
									</div> ₩ -
									<div class="col-1">
										<input type="text"
											class="d-inline form-control-sm form-control maximum-price" />
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
											class="d-inline form-control-sm form-control minimum-stock" />
									</div> 개 -
									<div class="col-1">
										<input type="text"
											class="d-inline form-control-sm form-control maximum-stock" />
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
						<tbody></tbody>
					</table>
					<nav class="paging"></nav>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal" onclick="resetProductsCondition()">
						닫기</button>
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
	<script src="/resources/admin/vendor/moment/moment.min.js"></script>
	<script
		src="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>

	<!-- Daterangepicker -->
	<script
		src="/resources/admin/js/plugins-init/bs-daterange-picker-init.js"></script>
	<script
		src="/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.js"></script>
	<script src="/resources/admin/js/plugins-init/sweetalert.init.js"></script>

	<!-- Jquery Validation -->
	<script
		src="/resources/admin/vendor/jquery-validation/jquery.validate.min.js"></script>
	<!-- Form validate init -->
	<script src="/resources/admin/js/sjy/jquery.validate-init.js"></script>

	<script>
      let categoryList = [];
      let searchedProductList = [];
      let eventProductList = [];
      let eventList = [];
      let pagingSearch = {};
      let pagingSearchProduct = {};

      function getEvent(no) {
        let event_no = no;
        $.ajax({
          url: `/admin/product/event/\${event_no}`,
          type: 'get',
          dataType: 'json',
          async: false,
          success: function (data) {
            displayDetails(data.event[0]);
            displayEventProducts(data.productList, data.event[0]);
          },
          error: function () {
            alert('error 발생');
          },
        });
      }

      function getEventLog(no) {
        let event_no = no;
        $.ajax({
          url: `/admin/product/eventLog/\${event_no}`,
          type: 'get',
          dataType: 'json',
          async: false,
          success: function (data) {
            displayDetails(data.event[0]);
            displayEventProducts(data.productList, data.event[0]);
          },
          error: function () {
            alert('error 발생');
          },
        });
      }

      function getEventAll() {
        $.ajax({
          url: `/admin/product/event`,
          type: 'post',
          dataType: 'json',
          async: false,
          success: function (data) {
            eventList = data.eventList;
          },
          error: function () {
            alert('error 발생');
          },
        });
      }

      function dateFormat(timestamp) {
        let date = new Date(timestamp);
        let year = date.getFullYear().toString();
        let month = date.getMonth() + 1;
        let day = date.getDate();

        month = month >= 10 ? month : '0' + month;
        day = day >= 10 ? day : '0' + day;

        return year.substring(2, 4) + '-' + month + '-' + day;
      }

      function getSearchedEvent() {
        let searchCondition = {
          search_type: $(
            '.search-table select.search-word option:selected'
          ).val(),
          search_word: $('.search-table input.search-word').val(),
          event_start: $('.search-table input.event-date').val().split(' ~')[0],
          event_end: $('.search-table input.event-date').val().split('~ ')[1],
          discount_rate: {
            min_rate: parseInt($('.search-table input.min_rate').val()) / 100,
            max_rate: parseInt($('.search-table input.max_rate').val()) / 100,
          },
        };

        $.ajax({
          url: `/admin/product/getSearchedEvent`,
          type: 'post',
          // dataType : 'json',
          data: JSON.stringify(searchCondition),
          async: false,
          contentType: 'application/json',
          success: function (data) {
            pagingSearch = data.searchCondition;
            displayPaging(data.pagingInfo, 'getSearchedEventByPage');
            displayResult(data.eventList);
          },
          error: function (request, error) {
            alert('error 발생');
            console.log(
              'code:' +
                request.status +
                '\n' +
                'message: ' +
                request.responseText +
                '\n' +
                'error : ' +
                error
            );
          },
        });
      }

      function getSearchedEventByPage(pageNo) {
        $.ajax({
          url: `/admin/product/getSearchedEvent?pageNo=\${pageNo}`,
          type: 'post',
          // dataType : 'json',
          data: JSON.stringify(pagingSearch),
          async: false,
          contentType: 'application/json',
          success: function (data) {
            displayPaging(data.pagingInfo, 'getSearchedEventByPage');
            displayResult(data.eventList);
          },
          error: function (request, error) {
            alert('error 발생');
            console.log(
              'code:' +
                request.status +
                '\n' +
                'message: ' +
                request.responseText +
                '\n' +
                'error : ' +
                error
            );
          },
        });
      }

      function displayResult(eventList) {
        let output = `<thead>
							<tr>
								<th class="text-center">이벤트 번호</th>
								<th class="text-center">이벤트 이름</th>
								<th class="text-center">이벤트 기간</th>
								<th class="text-center">할인율</th>
								<th class="text-center">관리</th>
							</tr>
						 </thead>
						 <tbody>`;
        $(eventList).each(function (index, item) {
          if (item.event_no != 0) {
            let start = dateFormat(item.event_start);
            let end = dateFormat(item.event_end);
            let date = new Date();
            output += `
		  				<tr>
						<td class="text-center">\${item.event_no}</td>
						<td>\${item.event_name}</td>
						<td class="text-center">\${start}~\${end}</td>
						<td class="text-center">\${Math.trunc(item.discount_rate * 100)}
							%</td>`;
            if (item.event_end < date) {
              output += `<td>
							<div class="d-flex justify-content-center">
							<a href="#" data-toggle="modal"
								data-target=".view-detail"
								class="btn btn-warning shadow btn-xs sharp mr-1"
								onclick="getEventLog(\${item.event_no})"><i
								class="fab fa-readme"></i></a>
							</div>
						</td></tr></tbody>`;
            } else if (item.event_start > date) {
              output += `<td>
							<div class="d-flex justify-content-center">
							<a href="#" data-toggle="modal"
								data-target=".detail-modal"
								class="btn btn-primary shadow btn-xs sharp mr-1"
								onclick="getEventLog(\${item.event_no})"><i
								class="fa fa-pencil"></i></a>
							</div>
						</td></tr></tbody>`;
            } else {
              output += `<td>
										<div class="d-flex justify-content-center">
										<a href="#" data-toggle="modal"
											data-target=".detail-modal"
											class="btn btn-primary shadow btn-xs sharp mr-1"
											onclick="getEvent(\${item.event_no})"><i
											class="fa fa-pencil"></i></a>
										</div>
									</td></tr></tbody>`;
            }
          } else if (item.event_no == 0 && eventList.length == 1) {
            output = `<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
          }
        });
        if (eventList.length == 0) {
          output = `<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
          $('.paging').empty();
        }
        $('.event-table').html(output);
      }

      function displayDetails(event) {
        let output = ``;
        let price = 0;
        let date = new Date();
        let start = dateFormat(event.event_start);
        let end = dateFormat(event.event_end);
        if (event.event_start > date) {
          output += `<tr>
				   	<td class="text-center event_no">\${event.event_no}</td>
					<td class="form-group"><input type="text" value="\${event.event_name}" class="form-control form-control-sm event_name" id="event_name" name="event_name"></input></td>
					<td class="col-4 text-center">
						<input
							class="form-control form-control-sm input-daterange-datepicker event-date"
							type="text" name="daterange" autocomplete="off">
					</td>
					<td class="form-group event-rate">
						<div class="row justify-content-center flex-nowrap">
							<input type="number" value="\${Math.trunc(event.discount_rate * 100)}" class="form-control form-control-sm discount-rate col-4" id="discount_rate" name="discount_rate" min="5"></input> %
						</div>
					</td>
					<td class="text-center">
						<a href="javascript:void(0)" class="btn btn-danger shadow btn-xs sharp confirmDelete" onclick="deleteEvent(\${event.event_no})">
						<i class="fa fa-trash"></i>
						</a>
					</td>
				   </tr>`;
        } else if (event.event_end > date) {
          output += `<tr>
				   	<td class="text-center event_no">\${event.event_no}</td>
					<td class="form-group"><input type="text" value="\${event.event_name}" class="form-control form-control-sm event_name" id="event_name" name="event_name"></input></td>
					<td class="col-4 text-center">
						<input
							class="form-control form-control-sm input-daterange-datepicker event-date"
							type="text" name="daterange" autocomplete="off">
					</td>
					<td class="form-group event-rate">
						<div class="row justify-content-center flex-nowrap">
							\${Math.trunc(event.discount_rate * 100)} %
						</div>
					</td>
					<td class="text-center">
						<a href="javascript:void(0)" class="btn btn-danger shadow btn-xs sharp confirmDelete" onclick="deleteEvent(\${event.event_no})">
						<i class="fa fa-trash"></i>
						</a>
					</td>
				   </tr>`;
        } else {
          output += `<tr>
				   	<td class="text-center event_no">\${event.event_no}</td>
					<td class="form-group">\${event.event_name}</td>
					<td class="col-4 text-center">
						\${start} ~ \${end}
					</td>
					<td class="form-group event-rate">
						<div class="row justify-content-center flex-nowrap">
							\${Math.trunc(event.discount_rate * 100)} %
						</div>
					</td>
					<td class="text-center">
						<a href="javascript:void(0)" class="btn btn-danger shadow btn-xs sharp disabled">
						<i class="fa fa-trash"></i>
						</a>
					</td>
				   </tr>`;
        }

        $('.modify-table > tbody').html(output);
        if (event.event_end > date) {
          $('.modify-table .event-date').daterangepicker({
            locale: {
              format: 'YYYY-MM-DD',
              separator: ' ~ ',
              applyLabel: '확인',
              cancelLabel: '취소',
              fromLabel: 'From',
              toLabel: 'To',
              customRangeLabel: 'Custom',
              weekLabel: 'W',
              daysOfWeek: ['월', '화', '수', '목', '금', '토', '일'],
              monthNames: [
                '1월',
                '2월',
                '3월',
                '4월',
                '5월',
                '6월',
                '7월',
                '8월',
                '9월',
                '10월',
                '11월',
                '12월',
              ],
            },
            minDate: new Date(event.event_start),
            startDate: new Date(event.event_start),
            endDate: new Date(event.event_end),
            firstDay: 1,
            drops: 'down',
            parentEl: $('.modal'),
          });
          $('.modify-table .event-date')
            .data('daterangepicker')
            .setStartDate(event.event_start);
        }
      }

      function displayEventProducts(productList, event) {
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
					<th class="text-center product_name">상품명</th>
					<th class="text-center">판매가</th>
					<th class="text-center">재고</th>
					<th class="text-center">행사가</th>
					<th class="text-center">등록일</th>
				</tr>
		  	</thead>
		  	<tbody>`;
        $(productList).each(function (index, item) {
          let price = item.product_price;
          let sale_price = price * (1 - event.discount_rate);
          let equal = 0;
          let product = {
            product_code: item.product_code,
            product_name: item.product_name,
            stock_quantity: item.stock_quantity,
            product_main_image: item.product_main_image,
            product_price: price,
          };
          let date = new Date(item.product_regist_date);
          let month = date.getMonth() + 1;
          let day = date.getDate();

          month = month >= 10 ? month : '0' + month;
          day = day >= 10 ? day : '0' + day;

          let regist_date = date.getFullYear() + '-' + month + '-' + day;
          searchedProductList.push(product);
          output += `
					  <tr id="\${item.product_code}">
						<td class="text-center">
							<div class="custom-control custom-checkbox ml-2">
								<input type="checkbox" class="custom-control-input product_code \${item.product_code}"
									id="check-box-\${item.product_code}" name="SearchedProduct"
									onclick="checkTest('SearchedProduct')"> <label
									class="custom-control-label"
									for="check-box-\${item.product_code}"></label>
							</div>
						</td>
						<td class="text-center">\${item.product_code}</td>
						<td class="product_name"><img class="product_img"
							src="\${item.product_main_image}">\${item.product_name}</td>
						<td class="price text-center">\${price.toLocaleString()}원</td>
						<td class="stock_quatity text-center">\${item.stock_quantity}</td>
						<td class="sale_price text-center">\${sale_price.toLocaleString()}원</td>
						<td class="text-center">\${regist_date}</td>
						</tr>
						`;
        });
        output += `</tbody>`;
        if (productList.length == 0) {
          output = `<div class="no-result"><p>이벤트에 해당되는 상품이 없습니다.</p></div>`;
          $('.event-cancle-box').empty();
        } else {
          $('.detail-modal .event-cancle-box').html(`<button type="button"
									class="btn btn-outline-light btn-sm mr-2"
									onclick="cancleEventProducts()">선택 상품 이벤트 해제</button>`);
        }
        $('.event-product-table').html(output);
      }

      function deleteProducts() {
        $('.result-table .product_code:checked').each(function (index, item) {
          let product_code = $(item).attr('id').split('check-box-')[1];

          $(eventProductList).each(function (index, item) {
            if (item == product_code) {
              eventProductList.splice(index, 1);
              $(`#product-\${item}`).remove();
            }
          });
        });
        if (eventProductList.length == 0) {
          $('.result-table').empty();
        }
      }

      function addEvent() {
        let event = {
          event_name: $('.add-event-table .event_name').val(),
          event_start: $('.add-event-table input.event-date')
            .val()
            .split(' ~')[0],
          event_end: $('.add-event-table input.event-date')
            .val()
            .split('~ ')[1],
          discount_rate: $('.add-event-table .discount_rate').val(),
        };

        let data = { event, eventProductList };

        $.ajax({
          url: `/admin/product/addEvent`,
          type: 'post',
          data: JSON.stringify(data),
          async: false,
          contentType: 'application/json',
          success: function (data) {
            successAlert('이벤트가 등록');
          },
          error: function (request, error) {
            failAlert('이벤트 등록 ');
            console.log(
              'code:' +
                request.status +
                '\n' +
                'message: ' +
                request.responseText +
                '\n' +
                'error : ' +
                error
            );
          },
        });
      }

      function cancleEventProducts() {
        let cancleProducts = [];
        let event_no = $('.modify-table .event_no').html();
        if ($('.detail-modal .product_code:checked').length > 0) {
          $('.detail-modal .product_code:checked').each(function (index, item) {
            let product_code = $(item).attr('id').split('check-box-')[1];

            cancleProducts.push(product_code);
          });

          let data = { cancleProducts, event_no };

          Swal.fire({
            type: 'info',
            title: `선택하신 상품들을 이벤트에서 <br/> 제외하시겠습니까?`,
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#aaa',
            confirmButtonText: '확인',
            cancelButtonText: '닫기',
          }).then((result) => {
            if (result.value == true) {
              jQuery.ajax({
                type: 'POST',
                url: `/admin/product/cancleEventProducts`,
                data: JSON.stringify(data),
                contentType: 'application/json',
                success: function (data) {
                  swal('완료', '해제가 완료되었습니다.', 'success').then(() => {
                    location.reload();
                  });
                },
                error: function (e) {
                  swal('실패', '해제 처리에 실패하였습니다.', 'error');
                },
                timeout: 100000,
              });
            }
          });
        } else {
          swal('실패', '상품을 선택해 주세요.', 'error');
        }
      }

      function deleteEvent(event_no) {
        Swal.fire({
          type: 'warning',
          title: `이벤트를 취소하시겠습니까?`,
          text: '이벤트를 취소하시면 다시 되돌릴 수 없습니다.',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#aaa',
          confirmButtonText: '확인',
          cancelButtonText: '닫기',
        }).then((result) => {
          if (result.value == true) {
            $.ajax({
              type: 'POST',
              url: `/admin/product/deleteEvent`,
              data: JSON.stringify(event_no),
              contentType: 'application/json',
              success: function (data) {
                swal('완료', '취소가 완료되었습니다.', 'success').then(() => {
                  location.reload();
                });
              },
              error: function (e) {
                swal('실패', '취소 처리에 실패하였습니다.', 'error');
              },
              timeout: 100000,
            });
          }
        });
      }

      function modifyEvent() {
        let event = {
          event_no: $('.modify-table .event_no').html(),
          event_name: $('.modify-table .event_name').val(),
          event_start: $('.modify-table input.event-date').val().split(' ~')[0],
          event_end: $('.modify-table input.event-date').val().split('~ ')[1],
          discount_rate: $('input.discount-rate').val() / 100,
        };

        $.ajax({
          url: `/admin/product/modifyEvent`,
          type: 'post',
          data: JSON.stringify(event),
          async: false,
          contentType: 'application/json',
          success: function (data) {
            successAlert('이벤트가 수정');
          },
          error: function (request, error) {
            failAlert('이벤트 수정 ');
            console.log(
              'code:' +
                request.status +
                '\n' +
                'message: ' +
                request.responseText +
                '\n' +
                'error : ' +
                error
            );
          },
        });
      }
      
      // 이벤트 상품 출력
      function displayProducts(choiceProducts) {
        let output = ``;
        if (eventProductList.length == 0) {
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
	                  <th class="text-center">금액</th>
	                  </tr>
	                </thead>
	                <tbody>`;
          $(choiceProducts).each(function (index, item) {
            eventProductList.push(item.product_code);
            output += `<tr id="product-\${item.product_code}">
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
								<td>\${item.product_price.toLocaleString()} 원</td>
							</tr>`;
          });
          output += '</tbody>';
          $('.result-table').append(output);
        } else {
          $(choiceProducts).each(function (index, item) {
            eventProductList.push(item.product_code);
            output += `<tr id="product-\${item.product_code}">
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
								<td>\${item.product_price.toLocaleString()} 원</td>
							</tr>`;
          });
          $('.result-table>tbody').append(output);
        }
      }

      function resetSearchCondition() {
        $('.search-table select.search-word').val('all').prop('selected', true);
        $('select').selectpicker('refresh');
        $('.search-table input.search-word').val('');
        $('.search-table .event-date')
          .data('daterangepicker')
          .setStartDate(new Date());
        $('.search-table .event-date')
          .data('daterangepicker')
          .setEndDate(new Date());
        $('.search-table input.event-date').val('');
        $('.min_rate').val('');
        $('.max_rate').val('');
      }

      function resetAddEventCondition() {
        $('.add-event-table .event_name').val('');
        $('.add-event-table .event-date')
          .data('daterangepicker')
          .setStartDate(new Date());
        $('.add-event-table .event-date')
          .data('daterangepicker')
          .setEndDate(new Date());
        $('.add-event-table input.event-date').val('');
        $('.discount_rate').val('');
        $('.result-table').empty();
        eventProductList = [];
      }

      // 검색 상품 출력
      function displaySearchedResult(productList, pagingInfo) {
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
									<th class="text-center">판매가</th>
									<th class="text-center">재고</th>
									<th class="text-center">[안전재고]</th>
									<th class="text-center">진열 상태</th>
									<th class="text-center">이벤트 여부</th>
									<th class="text-center">판매 여부</th>
									<th class="text-center">등록일</th>
								</tr>
						  	</thead>
						  	<tbody>`;
        $(productList).each(function (index, item) {
          let price = item.product_price;
          let equal = 0;
          let event_name = '';
          $(eventList).each(function (index, evt) {
            if (item.event_no == evt.event_no) {
              event_name = evt.event_name;
            }
          });
          let product = {
            product_code: item.product_code,
            product_name: item.product_name,
            stock_quantity: item.stock_quantity,
            product_main_image: item.product_main_image,
            product_price: price,
          };
          let date = new Date(item.product_regist_date);
          let month = date.getMonth() + 1;
          let day = date.getDate();

          month = month >= 10 ? month : '0' + month;
          day = day >= 10 ? day : '0' + day;

          let regist_date = date.getFullYear() + '-' + month + '-' + day;
          searchedProductList.push(product);

          if (!eventProductList.includes(item.product_code)) {
            output += `<tr id="\${item.product_code}">
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
            if (item.display == 'Y') {
              output += `<td>
										<span class="badge light badge-info"> <i
										class="fa fa-circle text-info mr-1"></i> 진열 중
									  	</span>
									  </td>`;
            } else {
              output += `<td>
										<span class="badge light badge-danger"> <i
										class="fa fa-circle text-danger mr-1"></i> 진열 중지
									  	</span>
									  </td>`;
            }
            if (item.event_no != 0) {
              output += `<td>
										<span class="badge light badge-success"> <i
											class="fa fa-circle text-success mr-1"></i> \${event_name}
										</span>
									  </td>`;
            } else {
              output += `<td>
										<span class="badge light badge-secondary"> <i
											class="fa fa-circle text-secondary mr-1"></i> 일반 판매
										</span>
									  </td>`;
            }
            if (item.is_new == 'Y') {
              output += `<td>
										<span class="badge light badge-primary"> <i
											class="fa fa-circle text-primary mr-1"></i> 신상품
										</span>
									  </td>`;
            } else {
              output += `<td>
										<span class="badge light badge-secondary"> <i
											class="fa fa-circle text-secondary mr-1"></i> 일반 상품
										</span>
									  </td>`;
            }
            output += `<td>\${regist_date}</td>
								</tr>`;
          } else {
            output += `<tr class="table-secondary" id="\${item.product_code}">
							<td>
								<div class="custom-control custom-checkbox ml-2">
									<input type="checkbox" class="custom-control-input product_code \${item.product_code}"
											id="check-box-\${item.product_code}" name="Product"
											disabled> <label
											class="custom-control-label"
											for="check-box-${item.product_code}"></label>
								</div>
							</td>
							<td>\${item.product_code}</td>
							<td class="product_name"><img class="product_img"
									src="\${item.product_main_image}">\${item.product_name}</td>
							<td class="price">\${price}원</td>
							<td class="stock_quatity">\${item.stock_quantity}</td>
							<td>[\${item.safety_stock_quantity}]</td>`;
            if (item.display == 'Y') {
              output += `<td>
              				<span class="badge light badge-success"> 
              					<i class="fa fa-circle text-success mr-1"></i> 진열 중
							</span>
										  </td>`;
            } else {
              output += `<td>
											<span class="badge light badge-danger"> <i
											class="fa fa-circle text-danger mr-1"></i> 진열 중지
										  	</span>
										  </td>`;
            }
            if (item.event_no != 0) {
              output += `<td>
											<span class="badge light badge-success"> <i
												class="fa fa-circle text-success mr-1"></i> \${event_name}
											</span>
										  </td>`;
            } else {
              output += `<td>
											<span class="badge light badge-danger"> <i
												class="fa fa-circle text-danger mr-1"></i> 일반 판매
											</span>
										  </td>`;
            }
            if (item.is_new == 'Y') {
              output += `<td>
											<span class="badge light badge-success"> <i
												class="fa fa-circle text-success mr-1"></i> 신상품
											</span>
										  </td>`;
            } else {
              output += `<td>
											<span class="badge light badge-danger"> <i
												class="fa fa-circle text-danger mr-1"></i> 일반 상품
											</span>
										  </td>`;
            }
            output += `<td>\${regist_date}</td></tr>`;
          }
        });
        output += `</tbody>`;

        let paging = `<ul class="pagination pagination-gutter justify-content-center mt-3">`;
        if (pagingInfo.pageNo != 1) {
          paging += `<li class="page-item page-indicator"><a class="page-link"
						href='javascript:void(0)' onclick="getProductList(\${pagingInfo.pageNo - 1})"><i
							class="la la-angle-left"></i></a></li>`;
        }
        for (let i = pagingInfo.startNumOfCurrentPagingBlock; i <= pagingInfo.endNumOfCurrentPagingBlock; i++) {
          if (pagingInfo.pageNo == i) {
            paging += `<li class="page-item active"><a class="page-link"
							 href='javascript:void(0)' onclick="getProductList(\${i})">\${i}</a></li>`;
          } else {
            paging += `<li class="page-item"><a class="page-link"
							 href='javascript:void(0)' onclick="getProductList(\${i})">\${i}</a></li>`;
          }
        }
        if (pagingInfo.pageNo < pagingInfo.totalPageCnt) {
          paging += `<li class="page-item page-indicator"><a class="page-link"
						href='javascript:void(0)' onclick="getProductList(\${pagingInfo.pageNo + 1})"><i
						class="la la-angle-right"></i> </a></li>`;
        }
        paging += `</ul>`;
        $('.search-modal .paging').html(paging);
        if (productList.length == 0) {
          output = `<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
          $('.search-modal .paging').empty();
        }
        $('.search-result').html(output);
        console.log(output);
      }

      $(document).ready(function () {
        getCategory();
        getEventAll();
        displayCategory('.search-category');

        $('.search-table .event-date').daterangepicker({
          locale: {
            format: 'YYYY-MM-DD',
            separator: ' ~ ',
            applyLabel: '확인',
            cancelLabel: '취소',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            weekLabel: 'W',
            daysOfWeek: ['월', '화', '수', '목', '금', '토', '일'],
            monthNames: [
              '1월',
              '2월',
              '3월',
              '4월',
              '5월',
              '6월',
              '7월',
              '8월',
              '9월',
              '10월',
              '11월',
              '12월',
            ],
          },
          startDate: new Date(),
          endDate: new Date(),
          firstDay: 1,
          drops: 'down',
        });

        $('.add-event-table .event-date').daterangepicker({
          locale: {
            format: 'YYYY-MM-DD',
            separator: ' ~ ',
            applyLabel: '확인',
            cancelLabel: '취소',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            weekLabel: 'W',
            daysOfWeek: ['월', '화', '수', '목', '금', '토', '일'],
            monthNames: [
              '1월',
              '2월',
              '3월',
              '4월',
              '5월',
              '6월',
              '7월',
              '8월',
              '9월',
              '10월',
              '11월',
              '12월',
            ],
          },
          minDate: new Date(),
          startDate: new Date(),
          endDate: new Date(),
          firstDay: 1,
          drops: 'down',
        });

        $('.event-date').val('');

        $('.product_regist_date').daterangepicker({
          locale: {
            format: 'YYYY-MM-DD',
            separator: ' ~ ',
            applyLabel: '확인',
            cancelLabel: '취소',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            weekLabel: 'W',
            daysOfWeek: ['월', '화', '수', '목', '금', '토', '일'],
            monthNames: [
              '1월',
              '2월',
              '3월',
              '4월',
              '5월',
              '6월',
              '7월',
              '8월',
              '9월',
              '10월',
              '11월',
              '12월',
            ],
          },
          startDate: new Date(),
          endDate: new Date(),
          firstDay: 1,
          drops: 'down',
          parentEl: $('.modal'),
        });
      });
    </script>
</body>
</html>
