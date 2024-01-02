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
<title>입고 관리</title>
<link
	href="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet">
<!-- Datatable -->
<link
	href="/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
	rel="stylesheet">
<link
	href="/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.css"
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

.product-table td {
	text-align: center;
}

.receiving-table tr>th:nth-child(1), tr>td:nth-child(3) {
	width: 6%;
}

.receiving-table tr>td:nth-child(1), tr>td:nth-child(5), tr>td:nth-child(6)
	{
	width: 5%;
}

.receiving-table tr>td:nth-child(2) {
	text-align: left;
}

.receiving-table tr>td:nth-child(4) {
	width: 10%;
}

.view-receiving tr>th:nth-child(1), .view-receiving tr>th:nth-child(3),
	.view-receiving tr>th:nth-child(5) {
	width: 15%;
}

.view-receiving tr>th:nth-child(2) {
	width: 40%;
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
		<img id="lego" src="/resources/admin/images/lego.gif"
			alt="" />
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>입고 관리</h4>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="javascript:void(0)">상품</a></li>
							<li class="breadcrumb-item active"><a
								href="javascript:void(0)">입고 관리</a></li>
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
															<option value="receiving_no">입고번호</option>
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
												<th>입고 날짜</th>
												<td class="row">
													<div class="col-4">
														<div class="example">
															<input
																class="form-control form-control-sm input-daterange-datepicker receiving_date"
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
										onclick="getSearchedReceivings()">검색</button>
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
								<h4 class="card-title">입고 목록</h4>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table
										class="table table-responsive-md product-table receiving-table">
										<thead>
											<tr>
												<th class="text-center">입고번호</th>
												<th class="text-center">상품명</th>
												<th class="text-center">입고수량</th>
												<th class="text-center">금액</th>
												<th class="text-center">상태</th>
												<th class="text-center">관리</th>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${receivingList.size() > 0}">
													<c:forEach var="receiving" items="${receivingList}">
														<c:set var="price" value="${receiving.total_price}" />
														<fmt:formatDate var="dateTempParse" pattern="yy-MM-dd"
															value="${receiving.receiving_date}" />
														<tr>
															<td>${receiving.receiving_no}</td>
															<td>${receiving.product_name}</td>
															<td>${receiving.quantity}</td>
															<td class="price">${String.format("%,d", price)}원</td>
															<c:choose>
																<c:when test="${receiving.is_received == 'Y'}">
																	<td>완료</td>
																	<td>
																		<div class="d-flex justify-content-center">
																			<a href="#"
																				class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
																				onClick="getReceiving(${receiving.receiving_no})"
																				data-toggle="modal" data-target=".view-receiving"><i
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
																				onClick="getReceiving(${receiving.receiving_no})"
																				data-toggle="modal" data-target=".modify-receiving"><i
																				class="fa fa-pencil"></i></a>
																		</div>
																	</td>
																</c:otherwise>
															</c:choose>
														</tr>
													</c:forEach>
												</c:when>
												<c:when test="${receivingList.size() == 0}">
													<td class="no-result">
														<p>입고 목록이 없습니다.</p>
													</td>
												</c:when>
											</c:choose>
										</tbody>
									</table>
								</div>
								<nav class="paging">
									<c:if test="${receivingList.size() > 0}">
										<ul
											class="pagination pagination-gutter justify-content-center mt-3">
											<c:if test="${pagingInfo.pageNo != 1}">
												<li class="page-item page-indicator"><a
													class="page-link" href="receiving?pageNo=${param.pageNo-1}"><i
														class="la la-angle-left"></i> </a></li>
											</c:if>
											<c:forEach var="i"
												begin="${pagingInfo.startNumOfCurrentPagingBlock}"
												end="${pagingInfo.endNumOfCurrentPagingBlock}">
												<c:choose>
													<c:when test="${pagingInfo.pageNo == i}">
														<li class="page-item active"><a class="page-link"
															href="receiving?pageNo=${i}">${i}</a></li>
													</c:when>
													<c:otherwise>
														<li class="page-item"><a class="page-link"
															href="receiving?pageNo=${i}">${i}</a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<c:if
												test="${pagingInfo.pageNo < pagingInfo.totalPageCnt}">
												<li class="page-item page-indicator"><a
													class="page-link" href="receiving?pageNo=${pagingInfo.pageNo+1}"><i
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
	<!-- 입고 수정 모달 -->
	<div class="modal fade bd-example-modal-lg modify-receiving"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content modify-receiving-form-valide">
				<div class="modal-header">
					<h5 class="modal-title">입고 수정</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md">
						<thead>
							<tr>
								<th class="text-center">입고 번호</th>
								<th class="text-center">상품명</th>
								<th class="text-center">입고 수량</th>
								<th class="text-center">상품 가격</th>
								<th class="text-center">입고 완료</th>
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
	<!-- 입고 조회 모달 -->
	<div class="modal fade bd-example-modal-lg view-receiving"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<form class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">입고 조회</h5>
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table table-responsive-md">
						<thead>
							<tr>
								<th class="text-center">입고 번호</th>
								<th class="text-center">상품명</th>
								<th class="text-center">입고 수량</th>
								<th class="text-center">상품 가격</th>
								<th class="text-center">입고 완료</th>
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
		src="/resources/admin/vendor/moment/moment.min.js"></script>
	<script
		src="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.js"></script>
	<!-- clockpicker -->
	<script
		src="/resources/admin/vendor/clockpicker/js/bootstrap-clockpicker.min.js"></script>
	<!-- asColorPicker -->
	<script
		src="/resources/admin/vendor/jquery-asColor/jquery-asColor.min.js"></script>
	<script
		src="/resources/admin/vendor/jquery-asGradient/jquery-asGradient.min.js"></script>
	<script
		src="/resources/admin/vendor/jquery-asColorPicker/js/jquery-asColorPicker.min.js"></script>
	<!-- Material color picker -->
	<script
		src="/resources/admin/vendor/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
	<!-- pickdate -->
	<script src="/resources/admin/vendor/pickadate/picker.js"></script>
	<script
		src="/resources/admin/vendor/pickadate/picker.time.js"></script>
	<script
		src="/resources/admin/vendor/pickadate/picker.date.js"></script>



	<!-- Daterangepicker -->
	<script
		src="/resources/admin/js/plugins-init/bs-daterange-picker-init.js"></script>
	<!-- Datatable -->
	<script
		src="/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="/resources/admin/js/plugins-init/datatables.init.js"></script>

	<!-- Jquery Validation -->
	<script
		src="/resources/admin/vendor/jquery-validation/jquery.validate.min.js"></script>
	<!-- Form validate init -->
	<script
		src="/resources/admin/js/sjy/jquery.validate-init.js"></script>
	<script type="text/javascript">
		let pagingSearch = {};
		function modifyReceiving() {
			let modifyReceivingList = [];
			$('.quantity').each(function(index, item){
				let receiving ={
					receiving_no : $('.receiving_no').html(),
					product_code : $(item).attr('id'),
					quantity : parseInt($(item).val())
				};
				modifyReceivingList.push(receiving);
			});
			
			$.ajax({
				url : `/admin/product/modifyReceiving`,
				type : 'post',
				data : JSON.stringify(modifyReceivingList),
				async : false,
				contentType: "application/json",
				success : function(data) {
					successAlert('입고가 수정');
				},
				error : function(request, error) {
					failAlert('입고 수정 ');
					console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
				}
			});
		}
	    
	    function getSearchedReceivingsPage(pageNo){
			  $.ajax({
					url : `/admin/product/receiving?pageNo=\${pageNo}`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(pagingSearch),
					async : false,
					contentType: "application/json",
					success : function(data) {
						pagingSearch = data.searchCondition;
						displayPaging(data.pagingInfo,"getSearchedReceivingsPage");
						displayResult(data.receivingList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		}
		
		function getSearchedReceivings(){
			  let min_date = '';
			  let max_date = '';
			  
			  if($("input.receiving_date").val() != ''){
				  min_date = ($("input.receiving_date").val()).split(' ~')[0];
				  max_date = ($("input.receiving_date").val()).split('~ ')[1];
			  }
			  
			  let searchCondition = {
				"search_type" : $("select.search-word option:selected").val(),
				"search_word" : $("input.search-word").val(),
				"receiving_date" : {
					"min_date" : min_date,
					"max_date" : max_date,
				},
				"is_received" : {
					"all" : $("input.all").is(":checked") && "Y" ,
					"wait" : $("input.wait").is(":checked") && "Y",
					"complete" : $("input.complete").is(":checked") && "Y",
				},
			  };
			  
			  $.ajax({
					url : `/admin/product/receiving`,
					type : 'post',
					// dataType : 'json',
					data : JSON.stringify(searchCondition),
					async : false,
					contentType: "application/json",
					success : function(data) {
						pagingSearch = data.searchCondition;
						displayPaging(data.pagingInfo,"getSearchedReceivingsPage");
						displayResult(data.receivingList);
						},
					error : function(request, error) {
						alert("error 발생");
						console.log("code:" + request.status + "\n" + "message: " + request.responseText +"\n" + "error : " + error);
					}
				});
		}
		
		function displayResult(receivingList){
			  let output = ``;
			  $(receivingList).each(function(index, item){
				  let price = item.total_price.toLocaleString();
				  var d = new Date(item.receiving_date);
				  let date = [d.getFullYear().toString().substr(-2), ('0' + (d.getMonth() + 1)).slice(-2),
					  ('0' + d.getDate()).slice(-2)
					  ].join('-');
				  output += `
						<tr>
							<td>\${item.receiving_no}</td>
							<td>\${item.product_name}</td>
							<td>\${item.quantity}</td>
							<td class="price">\${price} 원</td>`;
					if(item.is_received == 'Y'){
						output += `<td>완료</td>
									<td>
										<div class="d-flex justify-content-center">
											<a href="#"
												class="btn btn-warning shadow btn-xs sharp mr-1 read-btn"
												onClick="getReceiving(\${item.receiving_no})"
												data-toggle="modal" data-target=".view-receiving"><i
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
											onClick="getReceiving(\${item.receiving_no})"
											data-toggle="modal" data-target=".bd-example-modal-lg"><i
											class="fa fa-pencil"></i></a>
								    </div>
								  </td>
								  </tr>`;
					}
				});
			  if(receivingList.length == 0){
				  output +=`<div class="no-result"><p>검색 결과가 없습니다.</p></div>`;
				  $('.paging').empty();
			  }
			  $('.receiving-table > tbody').html(output);
		  }
	
		function getReceiving(no) {
			let receiving_no = no;
			$.ajax({
				url : `/admin/product/receiving/\${receiving_no}`,
				type : 'get',
				dataType : 'json',
				async : false,
				success : function(data) {
					displayDetails(data.receivingList);
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
	  			
				if(item.is_received == 'Y'){
					output += `<tr>
					   	<td class="text-center receiving_no">\${item.receiving_no}</td>
						<td>\${item.product_name}</td>
						<td class="col-2 text-center">\${item.quantity}</td>
						<td class="col-2 text-center">\${price.toLocaleString()} 원</td>
						<td class="text-center">
							<button href="javascript:void(0)" id="" class="btn btn-info shadow btn-xs sharp disabled" disabled>
						   		<i class="fa fa-check"></i>
						   	</button>
					   </td>
				   </tr>`;
				} else {
					output += `<tr>
					   	<td class="text-center receiving_no">\${item.receiving_no}</td>
						<td>\${item.product_name}</td>
						<td class="col-2 form-group"><input type="number" value="\${item.quantity}" class="form-control form-control-sm quantity" name="quantity-\${item.product_code}" id="\${item.product_code}" min="1"></input></td>
						<td class="col-2 text-center">\${price.toLocaleString()} 원</td>
						<td class="text-center">
							<a href="javascript:void(0)" id="" class="btn btn-info shadow btn-xs sharp confirmDelete" onclick="completeReceiving(\${item.receiving_no}, \${item.product_code})">
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
		
		function completeReceiving(receiving_no, product_code){
			let receiving = {receiving_no, product_code};
			Swal.fire({
			      type: "info",
			      title: '입고를 완료 처리하시겠습니까?',
			      showCancelButton: true,
			      confirmButtonColor: '#3085d6',
			      cancelButtonColor: '#aaa',
			      confirmButtonText: '확인',
			      cancelButtonText: '닫기'
			    }).then((result) => {
			      if (result.value == true) {
			    	  jQuery.ajax({
			  			type : "POST",
			  			url : `/admin/product/completeReceiving`,
			  			data : JSON.stringify(receiving),
			  			contentType: "application/json",
			  			success : function(data) {
			  				swal("완료", "입고가 완료되었습니다.", "success").then(() => {
			  			      	location.reload();})
			  			},
			  			error : function(e) {
			  				swal("실패", "완료 처리에 실패하였습니다.", "error");
			  			}, timeout:100000
			  		});  
			      }
			    })
		};
		
		$(document).ready(function() {
			$("input.receiving_date").val("");

			jQuery.validator.addClassRules("quantity", {
					required: !0,
		            min: 1,
		            digits: !0
				}); 
			
			jQuery.extend(jQuery.validator.messages, {
				required: "수량을 입력해주세요.",
				min: "입고 수량은 1개 이상이어야 합니다.",
	            digits : "숫자를 입력해주세요.",
			});
		});
	</script>
	<script
		src="/resources/admin/vendor/sweetalert2/dist/sweetalert2.min.js"></script>
	<script
		src="/resources/admin/js/plugins-init/sweetalert.init.js"></script>
	<script type="text/javascript"
		src="/resources/admin/js/sjy/common.js"></script>
</body>
</html>