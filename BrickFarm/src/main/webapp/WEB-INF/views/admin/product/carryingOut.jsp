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
<title>반출 관리</title>
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

.product-table td {
	text-align: center;
}

tr>td:nth-child(2) {
	text-align: left;
}

.disabled {
	cursor: inherit;
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
							<h4>반출 관리</h4>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">상품</a></li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">반출 관리</a></li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="table-responsive">
									<table
										class="table table-responsive-sm overflow-hidden search-table">
										<tbody>
											<tr>
												<th class="col-2">검색어</th>
												<td class="row">
													<div class="col-2">
														<select id="inputState"
															class="form-control form-control-sm search-word">
															<option value="all" selected>전체</option>
															<option value="carrying_out_no">반출번호</option>
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
												<th>반출 날짜</th>
												<td class="row">
													<div class="col-4">
														<div class="example">
															<input
																class="form-control form-control-sm input-daterange-datepicker carrying_out_date"
																type="text" name="daterange" autocomplete="off" value="">
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th>상태</th>
												<td class="row">
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input all"
																id="checkStatusAll" name="Status"
																onclick="checkAll('Status')"> <label
																class="custom-control-label" for="checkStatusAll">전체</label>
														</div>
													</div>
													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input wait"
																id="check-normal" name="Status"
																onclick="checkTest('Status')"> <label
																class="custom-control-label" for="check-normal">대기</label>
														</div>
													</div>

													<div class="col-2">
														<div class="custom-control custom-checkbox">
															<input type="checkbox"
																class="custom-control-input complete"
																id="check-sold-out" name="Status"
																onclick="checkTest('Status')"> <label
																class="custom-control-label" for="check-sold-out">완료</label>
														</div>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="d-flex justify-content-center">
									<button type="button" class="col-1 btn btn-primary btn-sm mr-2"
										onclick="getSearchedCarryingout()">검색</button>
									<button type="button"
										class="col-1 btn btn-outline-light btn-sm"
										onclick="resetCondition()">초기화</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">반출 목록</h4>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table
										class="table table-responsive-md product-table carrying-out-table">
										<thead>
											<tr>
												<th class="text-center">반출번호</th>
												<th class="text-center">상품명</th>
												<th class="text-center">반출수량</th>
												<th class="text-center">금액</th>
												<th class="text-center">상태</th>
												<th class="text-center">관리</th>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${carryingOutList.size() > 0}">
													<c:forEach var="co" items="${carryingOutList}">
														<c:set var="price" value="${co.total_price}" />
														<fmt:formatDate var="dateTempParse" pattern="yy-MM-dd"
															value="${co.carrying_out_date}" />
														<tr>
															<td>${co.carrying_out_no}</td>
															<td>${co.product_name}</td>
															<td>${co.quantity}</td>
															<td class="price">${String.format("%,d", price)} 원</td>
															<c:choose>
																<c:when test="${co.is_carried_out == 'Y'}">
																	<td>완료</td>
																	<td>
																		<div class="d-flex justify-content-center">
																			<a href="#"
																				class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
																				onClick="getCarryingOut(${co.carrying_out_no})"
																				data-toggle="modal" data-target=".view-carrying-out"><i
																				class="fab fa-readme"></i></a>
																		</div>
																	</td>
																</c:when>
																<c:otherwise>
																	<td>대기 (${dateTempParse})</td>
																	<td>
																		<div class="d-flex justify-content-center">
																			<a href="#"
																				class="btn btn-primary shadow btn-xs sharp mr-1"
																				onClick="getCarryingOut(${co.carrying_out_no})"
																				data-toggle="modal"
																				data-target=".bd-example-modal-lg"><i
																				class="fa fa-pencil"></i></a>
																		</div>
																	</td>
																</c:otherwise>
															</c:choose>
														</tr>
													</c:forEach>
												</c:when>
												<c:when test="${carryingOutList.size() == 0}">
													<td class="no-result">
														<p>반출 목록이 없습니다.</p>
													</td>
												</c:when>
											</c:choose>
										</tbody>
									</table>
								</div>
								<nav class="paging">
									<c:if test="${carryingOutList.size() > 0}">
										<ul
											class="pagination pagination-gutter justify-content-center mt-3">
											<c:if test="${pagingInfo.pageNo != 1}">
												<li class="page-item page-indicator"><a
													class="page-link"
													href="carryingOut?pageNo=${param.pageNo-1}"><i
														class="la la-angle-left"></i> </a></li>
											</c:if>
											<c:forEach var="i"
												begin="${pagingInfo.startNumOfCurrentPagingBlock}"
												end="${pagingInfo.endNumOfCurrentPagingBlock}">
												<c:choose>
													<c:when test="${pagingInfo.pageNo == i}">
														<li class="page-item active"><a class="page-link"
															href="carryingOut?pageNo=${i}">${i}</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item"><a class="page-link"
															href="carryingOut?pageNo=${i}">${i}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<c:if
												test="${pagingInfo.pageNo < pagingInfo.totalPageCnt}">
												<li class="page-item page-indicator"><a
													class="page-link"
													href="carryingOut?pageNo=${pagingInfo.pageNo+1}"><i
														class="la la-angle-right"></i> </a></li>
											</c:if>
										</ul>
									</c:if>
								</nav>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 반출 수정 -->
	<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content modify-carrying-out-form-valide">
				<div class="modal-header">
					<h5 class="modal-title">반출 수정</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md">
						<thead>
							<tr>
								<th class="text-center">반출 번호</th>
								<th class="text-center">상품명</th>
								<th class="text-center">반출 수량</th>
								<th class="text-center">상품 가격</th>
								<th class="text-center">반출 처리</th>
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
	<!-- 반출 조회 -->
	<div class="modal fade bd-example-modal-lg view-carrying-out"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">반출 조회</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md">
						<thead>
							<tr>
								<th class="text-center">반출 번호</th>
								<th class="text-center">상품명</th>
								<th class="text-center">반출 수량</th>
								<th class="text-center">상품 가격</th>
								<th class="text-center">반출 처리</th>
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

	<!-- Jquery Validation -->
	<script
		src="${contextPath}/resources/admin/vendor/jquery-validation/jquery.validate.min.js"></script>
	<!-- Form validate init -->
	<script
		src="${contextPath}/resources/admin/js/sjy/jquery.validate-init.js"></script>
	<script type="text/javascript">
		let pagingSearch = {};
		function modifyCarryingOut() {
			let modifyCarryingOutList = [];
			$('.quantity').each(function(index, item){
				let carryingOut ={
					carrying_out_no : $('.carrying_out_no').html(),
					product_code : $(item).attr('id'),
					quantity : parseInt($(item).val())
				};
				modifyCarryingOutList.push(carryingOut);
			});
			
			$.ajax({
				url : `/admin/product/modifyCarryingOut`,
				type : 'post',
				data : JSON.stringify(modifyCarryingOutList),
				async : false,
				contentType: "application/json",
				success : function(data) {
					successAlert('반출이 수정');
				},
				error : function(request, error) {
					failAlert('반출 수정 ');
					console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
				}
			});
		}
		
	    function getSearchedCarryingout(pageNo){
			  $.ajax({
					url : `/admin/product/carryingOut?pageNo=\${pageNo}`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(pagingSearch),
					async : false,
					contentType: "application/json",
					success : function(data) {
						pagingSearch = data.searchCondition;
						displayPaging(data.pagingInfo,"getSearchedCarryingout");
						displayResult(data.carryingOutList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		  }
		
		function getSearchedCarryingout(){
			  let min_date = '';
			  let max_date = '';
			  
			  if($("input.carrying_out_date").val() != ''){
				  min_date = ($("input.carrying_out_date").val()).split(' ~')[0];
				  max_date = ($("input.carrying_out_date").val()).split('~ ')[1];
			  }
			  
			  let searchCondition = {
				"search_type" : $("select.search-word option:selected").val(),
				"search_word" : $("input.search-word").val(),
				"carrying_out_date" : {
					"min_date" : min_date,
					"max_date" : max_date,
				},
				"is_carried_out" : {
					"all" : $("input.all").is(":checked") ? "Y" : "N",
					"wait" : $("input.wait").is(":checked") ? "Y" : "N",
					"complete" : $("input.complete").is(":checked") ? "Y" : "N",
				},
			  };
			  
			  $.ajax({
					url : `/admin/product/carryingOut`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(searchCondition),
					async : false,
					contentType: "application/json",
					success : function(data) {
						pagingSearch = data.searchCondition;
						displayPaging(data.pagingInfo,"getSearchedCarryingout");
						displayResult(data.carryingOutList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		}
	
		function displayResult(carryingOutList){
			  let output = ``;
			  $(carryingOutList).each(function(index, item){
				  let price = item.total_price.toLocaleString();
				  var d = new Date(item.carrying_out_date);
				  let date = [d.getFullYear().toString().substr(-2), ('0' + (d.getMonth() + 1)).slice(-2),
					  ('0' + d.getDate()).slice(-2)
					  ].join('-');
				  output += `
						<tr>
							<td>\${item.carrying_out_no}</td>
							<td>\${item.product_name}</td>
							<td>\${item.quantity}</td>
							<td class="price">\${price} 원</td>`;
					if(item.is_carried_out == 'Y'){
						output += `<td>완료</td>
									<td>
										<div class="d-flex justify-content-center">
											<a href="#"
												class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
												onClick="getCarryingOut(\${item.carrying_out_no})"
												data-toggle="modal" data-target=".view-carrying-out"><i
												class="fab fa-readme"></i></a>
										</div>
									</td>
								   </tr>`;
					} else {
						output += `<td>대기 (\${date})</td>
								   <td>
									<div class="d-flex justify-content-center">
										<a href="#"
											class="btn btn-primary shadow btn-xs sharp mr-1"
											onClick="getCarryingOut(\${item.carrying_out_no})"
											data-toggle="modal" data-target=".bd-example-modal-lg"><i
											class="fa fa-pencil"></i></a>
								    </div>
								  </td>
								  </tr>`;
					}
				});
			  if(carryingOutList.length == 0){
				  output +=`<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
				  $('.paging').empty();
			  }
			  $('.carrying-out-table > tbody').html(output);
		  }
		
		function getCarryingOut(no) {
			let carrying_out_no = no;
			$.ajax({
				url : `/admin/product/carryingOut/\${carrying_out_no}`,
				type : 'get',
				dataType : 'json',
				async : false,
				success : function(data) {
					displayDetails(data.carryingOutList);
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
	  			output += `
						   `;
				if(item.is_carried_out == "N"){
					output += `<tr>
								   	<td class="text-center carrying_out_no">\${item.carrying_out_no}</td>
									<td>\${item.product_name}</td>
									<td class="col-2 form-group"><input type="number" value="\${item.quantity}" class="form-control form-control-sm quantity" name="quantity-\${item.product_code}" id="\${item.product_code}" min="1"></input></td>
									<td class="price col-2 text-center">\${price.toLocaleString()} 원</td>
									<td class="text-center">
										<a href="javascript:void(0)" class="btn btn-info shadow btn-xs sharp confirmDelete" onclick="completeCarryingOut(\${item.carrying_out_no}, \${item.product_code})">
								   		<i class="fa fa-check"></i>
								   		</a>
							   	   </td>
							  </tr>`;
				} else {
					output += `<tr>
								   	<td class="text-center carrying_out_no">\${item.carrying_out_no}</td>
									<td>\${item.product_name}</td>
									<td class="col-2 text-center">\${item.quantity}</td>
									<td class="price col-2 text-center">\${price.toLocaleString()} 원</td>
									<td class="text-center">
										<a href="javascript:void(0)" class="btn btn-info shadow btn-xs sharp disabled">
								   		<i class="fa fa-check"></i>
								   		</a>
							   	   </td>
							  </tr>`;
				}
	  		});
	  		$('div.modal-body > table > tbody').html(output);
	  	};
		function resetCondition() {
			$('.search-table input').val('');
			$("select.search-word").val("all").prop("selected", true);
			$(".search-table input").prop("checked", false);
			$('select').selectpicker('refresh');
		};
		
		
		function completeCarryingOut(carrying_out_no, product_code){
			let carryingOut = {carrying_out_no, product_code};
			Swal.fire({
			      type: "info",
			      title: '반출을 완료 처리하시겠습니까?',
			      showCancelButton: true,
			      confirmButtonColor: '#3085d6',
			      cancelButtonColor: '#aaa',
			      confirmButtonText: '확인',
			      cancelButtonText: '취소'
			    }).then((result) => {
			    	if (result.value == true) {
				    	  jQuery.ajax({
				  			type : "POST",
				  			url : `/admin/product/completeCarryingOut`,
				  			data : JSON.stringify(carryingOut),
				  			contentType: "application/json",
				  			success : function(data) {
				  				swal("완료", "반출이 완료되었습니다.", "success").then(() => {
				  			      	location.reload();})
				  			},
				  			error : function(e) {
				  				swal("실패", "반출 처리에 실패하였습니다.", "error");
				  			}, timeout:100000
				  		});  
				      }
			    });
		};
		
		$(document).ready(function() {
			$("input.carrying_out_date").val("");
			
			jQuery.validator.addClassRules("quantity", {
				required: !0,
	            min: 1,
	            digits: !0
			}); 
		
			jQuery.extend(jQuery.validator.messages, {
				required: "수량을 입력해주세요.",
				min: "반출 수량은 1개 이상이어야 합니다.",
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