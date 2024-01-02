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
<title>상품 조회</title>
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
<!-- Toastr -->
<link rel="stylesheet"
	href="${contextPath}/resources/admin/vendor/toastr/css/toastr.min.css">
<style>
#main-wrapper>div.nav-header {
	z-index: 5;
}

.form-control-sm {
	height: calc(1.5em + 0.5rem + 2px) !important;
}

.filter-option-inner-inner, .form-control-sm.form-control {
	font-size: 16px;
}

.row {
	align-items: center;
}

#dTable>thead>tr th, #main-wrapper>div.content-body>div>div:nth-child(2)>div:nth-child(2)>div>div.card-body
	{
	font-size: 14px;
}

table.dataTable thead th, table.dataTable thead td {
	width: 50px !important;
	text-align: center;
	padding: 10px 30px !important;
}

table.dataTable thead th:nth-child(2), table.dataTable thead td:nth-child(2)
	{
	width: 420px !important;
	text-align: left;
}

table.dataTable tbody td {
	padding: 10px !important;
	text-align: center;
}

table.dataTable tbody td:nth-child(2) {
	text-align: left;
}

.product-img {
	width: 70px;
	height: 70px;
	object-fit: contain;
	display: inline-block;
	margin-right: 10px;
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

.pic-delete {
	right: -20px;
	border: none;
	background: none;
	position: absolute;
}

.price {
	position: relative;
}

.price:before {
	content: '원';
	position: absolute;
	right: 10%;
}

.price.active:before {
	right: 25%;
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

.upload-area {
	height: 150px;
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	text-align: center;
	font-weight: 500;
	font-size: 14px;
	color: #cccccc;
	border: 3px dashed #1e38bb;
	border-radius: 10px;
}

.upload-area input {
	position: absolute;
	height: 100%;
	width: 100%;
	opacity: 0;
	top: 0;
	left: 0;
	cursor: pointer;
}

#multiple-container {
	display: flex;
	height: 100%;
}

#multiple-container div {
	position: relative;
	margin: 40px 20px 15px;
}

#multiple-container a {
	position: absolute;
	right: 0;
	top: -20%;
}

.buttons {
	justify-content: space-evenly;
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
							<h4>상품 조회</h4>
							<p class="mb-0">상품 조회/수정/삭제 페이지입니다.</p>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">상품</a></li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">상품 조회</a></li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="table-responsive">
									<table
										class="table search-table table-responsive-sm overflow-hidden">
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
														<select
															class="first-category form-control form-control-sm">
															<option value="" selected>1차 분류</option>
															<option value="1">이동수단</option>
														</select>
													</div>
													<div class="col-2">
														<select
															class="second-category form-control form-control-sm">
															<option value="" selected>2차 분류</option>
														</select>
													</div>
													<div class="col-2">
														<select
															class="third-category form-control form-control-sm">
															<option value="" selected>3차 분류</option>
														</select>
													</div>
												</td>
											</tr>
											<tr>
												<th>등록일</th>
												<td class="row">
													<div class="col-3">
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
																id="checkStatusAll" onclick="checkAll('Status')">
															<label class="custom-control-label" for="checkStatusAll">전체</label>
														</div>
													</div>
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input display" id="check-normal"
																name="Status" onclick="checkTest('Status')"> <label
																class="custom-control-label" for="check-normal">판매
																중</label>
														</div>
													</div>

													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input is-event"
																id="check-sold-out" name="Status"
																onclick="checkTest('Status')"> <label
																class="custom-control-label" for="check-sold-out">이벤트
																중</label>
														</div>
													</div>

													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input is-new" id="check-in-stock"
																name="Status" onclick="checkTest('Status')"> <label
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
													<div class="col-2">
														<input type="number"
															class="d-inline form-control-sm form-control minimum-price"
															min="0">

													</div> ₩ -
													<div class="col-2">
														<input type="number"
															class="d-inline form-control-sm form-control maximum-price"
															min="0">

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
														<input type="number"
															class="d-inline form-control-sm form-control minimum-stock"
															min="0">

													</div> 개 -
													<div class="col-1">
														<input type="number"
															class="d-inline form-control-sm form-control maximum-stock"
															min="0">

													</div> 개
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="d-flex justify-content-center">
									<button type="button"
										class="col-1 btn btn-primary btn-sm mr-2 submit"
										onclick="getSearchedProducts()">검색</button>
									<button type="button"
										class="col-1 btn btn-outline-light btn-sm reset"
										onclick="resetCondition()">초기화</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">상품 목록</h4>
							</div>
							<div class="card-body">
								<div class="table-responsive main-table">
									<table id="dTable" class="display" style="min-width: 845px">
										<thead>
											<tr>
												<th>코드</th>
												<th>상품명</th>
												<th>판매가</th>
												<th>재고</th>
												<th class="col-1">[안전재고]</th>
												<th>진열</th>
												<th>이벤트</th>
												<th>판매</th>
												<th class="col-1">등록일</th>
												<th>관리</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="product" items="${productList}"
												varStatus="status">
												<c:set var="price" value="${product.product_price}" />
												<fmt:formatDate var="dateTempParse" pattern="yy-MM-dd"
													value="${product.product_regist_date}" />
												<tr>
													<td>${product.product_code}</td>
													<td><img class="product-img"
														src="${product.product_main_image}">
														${product.product_name}</td>
													<td class="price">${String.format("%,d", price)}</td>
													<td>${product.stock_quantity}</td>
													<td>[${product.safety_stock_quantity}]</td>
													<td><c:choose>
															<c:when test="${product.display == 'Y'}">
																<span class="badge light badge-info"> <i
																	class="fa fa-circle text-info mr-1"></i> 진열 중
																</span>
															</c:when>
															<c:otherwise>
																<span class="badge light badge-danger"> <i
																	class="fa fa-circle text-danger mr-1"></i> 진열 중지
																</span>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${product.event_no == '0'}">
																<span class="badge light badge-secondary"> <i
																	class="fa fa-circle text-secondary mr-1"></i> 일반 판매
																</span>
															</c:when>
															<c:otherwise>
																<c:forEach var="event" items="${eventList}">
																	<c:if test="${product.event_no == event.event_no}">
																		<span class="badge light badge-success"> <i
																			class="fa fa-circle text-success mr-1"></i>
																			${event.event_name}
																		</span>
																	</c:if>
																</c:forEach>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${product.is_new == 'Y'}">
																<span class="badge light badge-primary"> <i
																	class="fa fa-circle text-primary mr-1"></i> 신상품
																</span>
															</c:when>
															<c:otherwise>
																<span class="badge light badge-secondary"> <i
																	class="fa fa-circle text-secondary mr-1"></i> 일반 상품
																</span>
															</c:otherwise>
														</c:choose></td>
													<td>${dateTempParse}</td>
													<td>
														<div class="d-flex buttons">
															<button type="button" data-toggle="modal"
																data-target=".bd-example-modal-lg"
																onclick="getProductDetail(${product.product_code})"
																class="btn btn-primary shadow btn-xs sharp mr-1">
																<i class="fa fa-pencil"></i>
															</button>
															<a href="javascript:void(0)"
																class="btn btn-danger shadow btn-xs sharp confirmDelete"
																onclick="deleteProduct(`${product.product_name}`, `${product.product_code}`)"><i
																class="fa fa-trash"></i></a>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content modify-product-valide">
				<div class="modal-header">
					<h5 class="modal-title">상품 수정</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light"
						data-dismiss="modal">취소</button>
					<button type="submit" class="btn btn-primary submit">수정</button>
				</div>
			</form>
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
	<!-- Toastr -->
	<script
		src="${contextPath}/resources/admin/vendor/toastr/js/toastr.min.js"></script>

	<!-- All init script -->
	<script
		src="${contextPath}/resources/admin/js/plugins-init/toastr-init.js"></script>

	<!-- Daterangepicker -->
	<script
		src="${contextPath}/resources/admin/js/plugins-init/bs-daterange-picker-init.js"></script>
	<!-- Datatable -->
	<script
		src="${contextPath}/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${contextPath}/resources/admin/js/plugins-init/datatables.init.js"></script>
	<script
		src="${contextPath}/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.js"></script>
	<script
		src="${contextPath}/resources/admin/js/plugins-init/sweetalert.init.js"></script>

	<!-- Jquery Validation -->
	<script
		src="${contextPath}/resources/admin/vendor/jquery-validation/jquery.validate.min.js"></script>
	<!-- Form validate init -->
	<script
		src="${contextPath}/resources/admin/js/sjy/jquery.validate-init.js"></script>
	<script type="text/javascript"
		src="${contextPath}/resources/admin/js/sjy/common.js"></script>
	<script type="text/javascript">
		let img_files = [];
	    let images = [];
		let categoryList = [];
		let eventList = [];
		
		window.onload = function(){ 
			let list = [];
			getCategory();
			getEvent();
			displayCategory('.search-category');
			
			$('.nav-control').on('click', function(){
				$('.price').toggleClass('active');
			});
			
			$('#dTable').DataTable({
				language : lang_kor
			});
			
			window.addEventListener('dragenter dragover', (e) => {
				e.stopPropagation();
				e.preventDefault();
				}, false);
          
           $('.uploadImg').on('change', handleImgFileSelect);
           $('.uploadImg').on('drop', function(){
        	   handleImgFileSelect;
           });
           
          $.validator.addMethod("imgValidation", function() {
        	   if($('.thumbnail').length) {
        		   return true;
        	   } else {
        		   return false;
        	   }
        	}, "상품 사진을 등록해주세요."); 
           
          $('.bd-example-modal-lg').on('hidden.bs.modal', function () {
        	  images = [];
      		});
		};
		
		function modifyProduct() {
			let min_date = '';
		    let max_date = '';
	        let display = 'N';
	        let is_new = 'N';
	        let is_auto_order = 'N';
	        let categoryNo = 0;
	        if (
	          $('.modal select.first-category').val() == '' ||
	          $('.modal select.second-category').val() == ''
	        ) {
	          categoryNo = $('.modal select.first-category').val();
	        } else if (
	          $('.modal select.second-category').val() != '' &&
	          $('.modal select.third-category').val() == ''
	        ) {
	          categoryNo = $('.modal select.second-category').val();
	        } else {
	          categoryNo = $('.modal select.third-category').val();
	        }

	          if ($('.modal input.check-display').is(':checked')) {
	            display = 'Y';
	          }
	          if ($('.modal input.check-new').is(':checked')) {
	            is_new = 'Y';
	          }
	          if ($('.modal input.check-event').is(':checked')) {
	        	event_no = 9999;
	          }
	          if ($('.modal input.check-auto').is(':checked')) {
	              is_auto_order = 'Y';
	            }

	          let product = {
	            product_code: $('.modal #product_code').val(),
	            product_category_no: categoryNo,
	            product_name: $('.modal #product_name').val(),
	            product_price: $('.modal #product_price').val(),
	            product_description: $('.modal #product_description').val(),
	            parts_quantity: $('.modal #parts_quantity').val(),
	            stock_quantity: $('.modal #stock_quantity').val(),
	            safety_stock_quantity: $('.modal #safety_stock_quantity').val(),
	            recommend_age: $('.modal #recommend_age').val() + '+',
	            product_main_image : images[0],
	            product_short_description: $('.modal #product_short_description').val(),
	            display: display,
	            is_new: is_new,
	            event_no: $(".modal select.event option:selected").val(),
	            is_auto_order : is_auto_order
	          };
				
				let data = {product, images}
				
				$.ajax({
	                url: `/admin/product/modifyProduct`,
	                type: 'post',
	                // dataType : 'json',
	                data: JSON.stringify(data),
	                async: false,
	                contentType: 'application/json',
	                success: function (data) {
	                  successAlert('상품이 수정');
	                },
	                error: function (request, error) {
	                	failAlert('상품 수정 ');
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
		
		function modifyProductFail(){
			failAlert('상품 수정 ');
		}
		
		let lang_kor = {
			"decimal" : "",
			"emptyTable" : "데이터가 없습니다.",
			"info" : "_START_ - _END_ (총 _TOTAL_ 개)",
			"infoEmpty" : "0개",
			"infoFiltered" : "(전체 _MAX_ 개 중 검색결과)",
			"infoPostFix" : "",
			"thousands" : ",",
			"lengthMenu" : "_MENU_ 개씩 보기",
			"loadingRecords" : "로딩중...",
			"processing" : "처리중...",
			"search" : "검색 : ",
			"zeroRecords" : "검색된 데이터가 없습니다.",
			"paginate" : {
				"first" : "첫 페이지",
				"last" : "마지막 페이지",
				"next" : "다음",
				"previous" : "이전"
			},
			"aria" : {
				"sortAscending" : " :  오름차순 정렬",
				"sortDescending" : " :  내림차순 정렬"
			}
		};
		
		function resetCondition() {
			$('.search-table input').val('');
			$("select.search-word").val("product_code").prop("selected", true);
			$("select.first-category").val("").prop("selected", true);
			$('select.second-category').html(`<option value="" selected>2차 분류</option>`);
			$('select.third-category').html(`<option value="" selected>3차 분류</option>`);
			displayCategory('.search-category');
			$(".search-table input").prop("checked", false);
			$("select.stock_quantity").val("stock").prop("selected", true);
			$('select').selectpicker('refresh');
		}
		
		function deleteProduct(name, product_code){
			Swal.fire({
				      type: "warning",
				      title: name + `(를)을 삭제하시겠습니까?`,
				      text: "삭제한 상품은 되돌릴 수 없습니다.",
				      showCancelButton: true,
				      confirmButtonColor: 'rgb(221, 107, 85)',
				      cancelButtonColor: '#aaa',
				      confirmButtonText: '삭제',
				      cancelButtonText: '취소'
				    }).then((result) => {
				    	if (result.value == true) {
				    		$.ajax({
				    			type : 'get',
								async : false,
					  			url : `/admin/product/deleteProduct/\${product_code}`,
					  			success : function(data) {
					  				swal("완료", "상품이 삭제되었습니다.", "success").then(() => {
					  			      	location.reload();})
					  			},
					  			error : function(e) {
					  				swal("실패", "삭제 처리에 실패하였습니다.", "error");
					  			}, timeout:100000
					  		});  
					      }
				    });
		  };
		  
		  function getSearchedProducts(){
			  let categoryNo = 0;
			  if($("select.first-category").val() == '' || $("select.second-category").val() == ''){
				  categoryNo = $("select.first-category").val();			  
			  } else if($("select.second-category").val() != '' && $("select.third-category").val() == ''){
				  categoryNo = $("select.second-category").val();	  
			  } else {
				  categoryNo = $("select.third-category").val();	  
			  }
			  let searchCondition = {
				"search_type" : $("select.search-word option:selected").val(),
				"search_word" : $("input.search-word").val(),
				"product_category_no" : categoryNo,
				"product_regist_date" : {
					"min_date" : ($("input.product_regist_date").val()).split(' ~')[0],
					"max_date" : ($("input.product_regist_date").val()).split('~ ')[1],
				},
				"sales_status" : {
					"all" : $("input.all").is(":checked") && "Y" ,
					"display" : $("input.display").is(":checked") && "Y",
					"event_no" : $("input.is-event").is(":checked") ? 9999 : 0,
					"is_new" : $("input.is-new").is(":checked") && "Y",
					"not_display" : $("input.not-display").is(":checked") && "N",
				},
				"product_price" : {
					"minimum_price" : $("input.minimum-price").val(),
					"maximum_price" : $("input.maximum-price").val(),
				},
				"stock_search_type" : $("select.stock option:selected").val(),
				"stock_quantity" : {
					"minimum_stock" : $("input.minimum-stock").val(),
					"maximum_stock" : $("input.maximum-stock").val(),
				}
			  };
			  
			  $.ajax({
					url : `/admin/product/listAll`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(searchCondition),
					async : false,
					contentType: "application/json",
					success : function(data) {
						displayResult(data.productList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		  }
		  
		  function displayResult(productList){
			  let output = `<table id="dTable" class="display" style="min-width: 845px">
								<thead>
								<tr>
									<th>코드</th>
									<th>상품명</th>
									<th>판매가</th>
									<th>재고</th>
									<th>[안전재고]</th>
									<th>진열 상태</th>
									<th>이벤트 여부</th>
									<th>판매 여부</th>
									<th>등록일</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>`;
			  $(productList).each(function(index, item){
				  let price = item.product_price;
				  output += `
					  <tr>
						<td>\${item.product_code}</td>
						<td><img class="product-img"
							src="\${item.product_main_image}">
							\${item.product_name}</td>
						<td class="price">\${price}</td>
						<td>\${item.stock_quantity}</td>
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
								<td>
									<div class="d-flex buttons">
										<button type="button" data-toggle="modal"
											data-target=".bd-example-modal-lg"
											onclick="getProductDetail(\${item.product_code})"
											class="btn btn-primary shadow btn-xs sharp mr-1">
											<i class="fa fa-pencil"></i>
										</button>
										<a href="javascript:void(0)"
											class="btn btn-danger shadow btn-xs sharp confirmDelete"
											onclick="deleteProduct('\${item.product_name}', '\${item.product_code}')"><i
											class="fa fa-trash"></i></a>
									</div>
								</td>
								</tr>`;
				});
			  output +=`</tbody></table>`;
			  if(productList.length == 0){
				  output =`<div class="no-result"><p>검색 결과가 없습니다.</p></div>`
			  }
			  $('.main-table').html(output);
			  $('#dTable').DataTable({
					language : lang_kor
				});
		  }
		  
		  function getProductDetail(code) {
				let product_code = code;
				$.ajax({
					url : `/admin/product/listAll/\${product_code}`,
					type : 'get',
					dataType : 'json',
					async : false,
					success : function(data) {
						displayDetails(data.product[0],data.imgList);
					},
					error : function() {
						alert("error 발생");
					}
				});
		  };
		  
		  function handleImgFileSelect(e) {	
        	  $('.empty-image').remove();

        	  let files = e.target.files;
        	  let filesArr = Array.prototype.slice.call(files);

        	  let index = 0;
        	  filesArr.forEach(function (f) {
        	    if (!f.type.match('image.*')) {
        	      alert('이미지 파일만 업로드할 수 있습니다.');
        	      return;
        	    }

        	    img_files.push(f);

        	    let reader = new FileReader();
        	    reader.onload = function (e) {
        	      let html = `<div id="img_id_\${index}" data-file="\${f.name}">
        	      				<a href="javascript:void(0)" onclick="deleteImageAction(\${index})">
        	      					✖
        	      				</a>
        	      				<img src="\${e.target.result}" data-file="\${f.name}" class="thumbnail">
        	      			  </div>`;
        	      $('#multiple-container').append(html);
        	      images.push(e.target.result);
        	      index++;
        	    };
        	    reader.readAsDataURL(f);
        	  });
        }
        
        function deleteImageAction(index) {
	        img_files.splice(index, 1);
	        images.splice(index, 1);
	
	        let img_id = `#img_id_\${index}`;
	        $(img_id).remove();
        }
		  
		  function displayDetails(e, imgList) {
			  let imgHtml = '';
			  let event = `
				  <select id="inputState"
					class="form-control form-control-sm event">
			  `;
			  $(eventList).each(function(index, item){
					if(e.event_no == item.event_no) {
						event += `<option value="\${item.event_no}" selected>\${item.event_name}</option>`;
					} else if(item.event_end > new Date()){
						event += `<option value="\${item.event_no}">\${item.event_name}</option>`;
					} else if(item.event_no == 0){
						event += `<option value="\${item.event_no}">\${item.event_name}</option>`;
					}
				});
			  event +=`</select>`
			  
			  let age = e.recommend_age.split('+')[0];
			  
			  let category = `<div class="col-3 form-group">
									<select
									class="first-category form-control form-control-sm" id="first-category" name="first-category">
										<option>1차 분류</option>
										<option value="1" selected>이동수단</option>
									</select>
							  </div>
							  <div class="col-3 form-group"></>`;
			  
			  if(imgList.length != 0){
				  $.each(imgList, function(index, item){
					  imgHtml +=`
					  <div id="img_id_\${index}">
	      				<a href="javascript:void(0)" onclick="deleteImageAction(\${index})">
	      					✖
	      				</a>
	      				<img src="\${item.product_file_path}" class="thumbnail">
	      			  </div>
					  `;
					  images.push(item.product_file_path);
				  })
			  } else {
				  imgHtml += `<p class="empty-image">등록된 상품 사진이 없습니다.</p>`
			  }
			  
			  	let selected = [];
			  	$(categoryList).each(function(index, item){
					if(e.product_category_no == item.product_category_no){
						selected = item;
					}
				});
			  	category += `<select class="second-category form-control form-control-sm" id="second-category" name="second-category"><option>2차 분류</option>`
				$(categoryList).each(function(index, item){
					if(item.category_level == 'Medium'){
						if(selected.upper_category_no == item.product_category_no){
							category += `<option value="\${item.product_category_no}" selected>\${item.product_category_name}</option>`;
						} else {
							category += `<option value="\${item.product_category_no}">\${item.product_category_name}</option>`;
						}
					}
				});
			  	category += `</select>`
				
				category += '</div><div class="col-3 form-group"><select class="third-category form-control form-control-sm" id="third-category" name="third-category"><option>3차 분류</option>';
				
				$(categoryList).each(function(index, item){
					if(selected.upper_category_no == item.upper_category_no && item.category_level == 'Small'){
						if(selected.product_category_no == item.product_category_no){
							category += `<option value="\${item.product_category_no}" selected>\${item.product_category_name}</option>`;
						} else {
							category += `<option value="\${item.product_category_no}">\${item.product_category_name}</option>`;
						}
					}
				});
				
				category += '<select></div>';
				
			  $('.modal .modal-body').html(
				    ` <div class="d-flex p-2">
				    	<div class="mb-5 form-group col-2 p-0">
					      <p>상품 코드</p>
					      <input
					        type="text"
					        class="d-inline form-control-sm col-10 form-control"
					        id="product_code"
					        name="product_code"
					        value="\${e.product_code}"
					        readonly
					      />
					    </div>
					    <div class="mb-5 form-group col-10">
					      <p>상품명</p>
					      <input
					        type="text"
					        class="d-inline form-control-sm form-control"
					        id="product_name"
					        name="product_name"
					        value="\${e.product_name}"
					      />
					    </div>
					    </div>
					    
					    <div class="mb-5 d-flex flex-column">
					      <p>카테고리</p>
					      <div class="row">\${category}</div>
					    </div>

					    <div class="d-flex p-2">
					    
						    <div class="mb-5 form-group">
						      <p>판매가</p>
						      <input
						        type="number"
						        class="d-inline col-10 form-control-sm form-control"
						        id="product_price"
						        name="product_price"
						        value="\${e.product_price}"
						        min="1000"
						      />
						    </div>
					    
						    <div class="mb-5 form-group">
						      <p>재고</p>
						      <input
						        type="number"
						        class="d-inline col-10 form-control-sm form-control"
						        id="stock_quantity"
						        name="stock_quantity"
						        value="\${e.stock_quantity}"
						        min="10"
						      />
						    </div>
						    
						    <div class="mb-5 form-group">
						      <p>안전 재고</p>
						      <input
						        type="number"
						        class="d-inline col-10 form-control-sm form-control"
						        id="safety_stock_quantity"
						        name="safety_stock_quantity"
						        value="\${e.safety_stock_quantity}"
						        min="50"
						      />
						    </div>
					    
					    </div>
					    
					    <div class="d-flex p-2">
					    
					      <div class="mb-5 col-3 p-0">
					        <p>진열 상태</p>
					        <div class="row mt-3">
					          <div class="col-10">
						            <div class="custom-control custom-checkbox">
						              <input
						                type="checkbox"
						                class="custom-control-input"
						                id="check-display"
						                name="display"
						                onclick="checkOnlyOne(this)"
						              />
						              <label class="custom-control-label" for="check-display"
						                >진열 O</label
						              >
						            </div>
					            </div>
					            <div class="col-10">
					              <div class="custom-control custom-checkbox">
					                <input
					                  type="checkbox"
					                  class="custom-control-input"
					                  id="check-not-display"
					                  name="display"
					                  onclick="checkOnlyOne(this)"
					                />
					                <label class="custom-control-label" for="check-not-display"
					                  >진열 X</label
					                >
					              </div>
					            </div>
					          </div>
					      </div>
					      
					      <div class="mb-5 col-3 p-0">
					        <p>신상품</p>
					        <div class="row mt-3">
					          <div class="col-10">
					            <div class="custom-control custom-checkbox">
					              <input
					                type="checkbox"
					                class="custom-control-input"
					                id="check-new"
					                name="new"
					                onclick="checkOnlyOne(this)"
					              />
					              <label class="custom-control-label" for="check-new"
					                >신상품 O</label
					              >
					            </div>
					          </div>
					          <div class="col-10">
					            <div class="custom-control custom-checkbox">
					              <input
					                type="checkbox"
					                class="custom-control-input"
					                id="check-not-new"
					                name="new"
					                onclick="checkOnlyOne(this)"
					              />
					              <label class="custom-control-label" for="check-not-new"
					                >신상품 X</label
					              >
					            </div>
					          </div>
					        </div>
					      </div>
					      
					      
					      	<div class="mb-5 col-3 p-0">
						      <p>자동 발주</p>
						      <div class="row mt-3">
						        <div class="col-10">
						          <div class="custom-control custom-checkbox">
						            <input
						              type="checkbox"
						              class="custom-control-input"
						              id="check-auto"
						              name="auto"
						              onclick="checkOnlyOne(this)"
						            />
						            <label class="custom-control-label" for="check-auto"
						              >자동 발주 O</label
						            >
						          </div>
						        </div>
						        <div class="col-10">
						          <div class="custom-control custom-checkbox">
						            <input
						              type="checkbox"
						              class="custom-control-input"
						              id="check-not-auto"
						              name="auto"
						              onclick="checkOnlyOne(this)"
						            />
						            <label class="custom-control-label" for="check-not-auto"
						              >자동 발주 X</label
						            >
						          </div>
						        </div>
						      </div>
						    </div>
					    
					    </div>
					    <div class="mb-5">
					      <p>이벤트</p>
					      \${event}
					    </div>
					    
					    <div class="d-flex p-2">
					    <div class="mb-5 form-group">
					      <p>부품 개수</p>
					      <input
					        type="number"
					        class="d-inline col-4 form-control-sm form-control"
					        id="parts_quantity"
					        name="parts_quantity"
					        value="\${e.parts_quantity}"
					        min="10"
					      /> 개
					    </div>
					
					    <div class="mb-5 form-group">
					      <p>권장 연령</p>
					      <input
					        type="number"
					        class="d-inline col-5 form-control-sm form-control"
					        id="recommend_age"
					        name="recommend_age"
					        value="\${age}"
					        min="1"
					      />
					      +
					    </div>
					    </div>
					    
					    <div class="mb-5 form-group">
					      <p>상품 설명</p>
					      <textarea
					        class="form-control"
					        rows="4"
					        id="product_description"
					        name="product_description"
					      >\${e.product_description}</textarea
					      >
					    </div>
					      
					    <div class="mb-5 form-group">
					      <p>상품 사진</p>
					      <div class="col-12">
					        <div class="upload-area">
					          <span>여기를 클릭하거나 이미지를 드래그 해주세요.</span>
					          <input
					            type="file"
					            class="uploadImg"
					            id="input-multiple-image"
					            name="input-multiple-image"
					            accept="image/*"
					            multiple
					          />
					        </div>
					      </div>
					      <div class="row col-12" id="multiple-container">\${imgHtml}</div>
					    </div>`);
			  
			if(e.display == 'Y') {
				$('#check-display').prop('checked',true);
			}else {
				$('#check-not-display').prop('checked',true);
			}
			if(e.is_new == 'Y') {
				$('#check-new').prop('checked',true);
			}else {
				$('#check-not-new').prop('checked',true);
			}
			if(e.is_auto_order == 'Y') {
				$('#check-auto').prop('checked',true);
			}else {
				$('#check-not-auto').prop('checked',true);
			}
			$('select').selectpicker('refresh');
			
			displayCategory('.modal');
			$('.uploadImg').on('change', handleImgFileSelect);
	        $('.uploadImg').on('drop', function(){
	        	   handleImgFileSelect;
	         });
		};
		
		function createDaterangepicker(){
			$('.event-date').daterangepicker({
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
                "firstDay": 1,
                "drops": "down",
                "parentEl": $('.modal') 
            });
		}
		
		function checkOnlyOne(element) {
			const name = element.name;
			const checkboxes = document.getElementsByName(name);
			checkboxes.forEach((cb) => {
				cb.checked = false;
			});
					  
			element.checked = true;
		};
				
		 $('.input-daterange-datepicker').daterangepicker({
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
	                "firstDay": 1,
	                "drops": "down"
	     });
	</script>
</body>
</html>