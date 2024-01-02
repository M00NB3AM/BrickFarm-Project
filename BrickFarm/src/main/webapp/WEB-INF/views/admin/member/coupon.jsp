<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
<!-- Datatable -->
<link
	href="${contextPath }/resources/admin/vendor/datatables/css/jquery.dataTables.min.css"
	rel="stylesheet" />
<!-- Toastr -->
<link rel="stylesheet" href="/resources/admin/vendor/toastr/css/toastr.min.css" />

<style type="text/css">
.radio {
	padding: 20px;
}

table.dataTable thead th {
	font-size: 14px !important;
}

#indicator {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

.coupon {
	padding: 0.55rem 0.8rem !important;
	margin-bottom: 5px;
	font-size: 14px !important;
}
.deletedCoupon{
color : gray;
text-decoration: line-through;
}
</style>
</head>
<body>
	<div id="preloader">
		<img id="indicator"
			src="${contextPath}/resources/admin/images/lego.gif" alt="" />
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>쿠폰 관리</h4>
						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="${contextPath }/admin/member/dashboard">회원</a>
							</li>
							<li class="breadcrumb-item active">
								<a href="${contextPath }/admin/member/coupon">쿠폰 관리</a>
							</li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-xl-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">쿠폰 관리</h4>
							</div>
							<div class="card-body">
								<!-- Nav tabs -->
								<div class="default-tab">
									<ul class="nav nav-tabs" role="tablist">
										<li class="nav-item">
											<a class="nav-link active" data-toggle="tab" href="#makeCoupon">쿠폰 추가</a>
										</li>
										<li class="nav-item">
											<a class="nav-link" data-toggle="tab" href="#giveCoupon">등급별 쿠폰 지급</a>
										</li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane fade show active" id="makeCoupon">
											<div class="pt-4">
												<form action="makeCoupon" method="post">
													<div class="form-group row">
														<label class="col-sm-3 col-form-label">쿠폰 이름</label>
														<div class="col-sm-9">
															<input type="text" class="form-control" placeholder="쿠폰 이름을 입력하세요" name="coupon_name" value="" id="coupon_name">
														</div>
													</div>
													<fieldset class="form-group">
														<div class="row">
															<label class="col-sm-3 col-form-label">적용 대상 등급</label>
															<div class="col-sm-9">
																<label class="form-check-label radio">
																	<input class="form-check-input" type="radio" name="member_grade_name" value="all" checked> 전체회원
																</label>
																<label class="form-check-label radio">
																	<input class="form-check-input" type="radio" name="member_grade_name" value="일반"> 일반회원
																</label>
																<label class="form-check-label radio">
																	<input class="form-check-input" type="radio" name="member_grade_name" value="실버"> 실버회원
																</label>
																<label class="form-check-label radio">
																	<input class="form-check-input" type="radio" name="member_grade_name" value="골드"> 골드회원
																</label>
															</div>
														</div>
													</fieldset>
													<div class="form-group row">
														<label class="col-sm-3 col-form-label">쿠폰 설명</label>
														<div class="col-sm-9">
															<input type="text" class="form-control" placeholder="쿠폰에 대한 설명을 입력하세요" name="coupon_discription" value="" id="coupon_discription">
														</div>
													</div>
													<div class="form-group row">
														<label class="col-sm-3 col-form-label">할인율 (%)</label>
														<div class="col-sm-3">
															<input type="number" class="form-control" min="0" max="100" name="discount_rate" id="discount_rate">
														</div>
													</div>
													<div class="form-group row">
														<label class="col-sm-3 col-form-label">유효기간(일 기준)</label>
														<div class="col-sm-9">
															<input type="number" class="form-control" name="validity_period" value="" id="validity_period">
														</div>
													</div>
													<input type="submit" class="btn light btn-primary" value="추가" onclick="return fillAllinput()" />
												</form>
											</div>
										</div>
										<div class="tab-pane fade" id="giveCoupon">
											<div class="pt-4">
												<form action='giveCoupon'>
													<div class="form-group row">
														<label class="col-sm-3 col-form-label">쿠폰 이름</label>
														<div class="col-sm-9 selectCoupon">
															<select id="choose_coupon" name="coupon_policy_no">
																<option value="0">쿠폰을 선택하세요.</option>
																<c:forEach var="coupon" items="${couponList }">
																	<c:if test="${coupon.member_grade_name eq 'all' and coupon.status eq 'Y'}">
																		<option value="${coupon.coupon_policy_no }">${coupon.coupon_policy_name }</option>
																	</c:if>
																</c:forEach>
															</select>
														</div>
													</div>
													<fieldset class="form-group">
														<div class="row">
															<label class="col-sm-3 col-form-label">적용 대상 등급</label>
															<div class="col-sm-9">
																<label class="form-check-label radio">
																	<input class="form-check-input giveCoupon" type="radio" name="member_grade_name" value="all" checked> 전체회원
																</label>
																<label class="form-check-label radio">
																	<input class="form-check-input giveCoupon" type="radio" name="member_grade_name" value="일반"> 일반회원
																</label>
																<label class="form-check-label radio">
																	<input class="form-check-input giveCoupon" type="radio" name="member_grade_name" value="실버"> 실버회원
																</label>
																<label class="form-check-label radio">
																	<input class="form-check-input giveCoupon" type="radio" name="member_grade_name" value="골드"> 골드회원
																</label>
															</div>
														</div>
													</fieldset>
													<input type="submit" class="btn light btn-primary" onclick="return vaildCoupon()" value="쿠폰 지급"/>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="button">
									<button type="button" class="btn light btn-primary coupon" onclick="return availableCoupon(true)">발급 가능</button>
									<button type="button" class="btn light btn-primary coupon" onclick="return availableCoupon(false)">발급 불가능</button>
									<button type="button" class="btn light btn-primary coupon" data-toggle="modal" data-target="#deleteCoupon" onclick="return delCoupons()">삭제</button>
								</div>
								<div class="table-responsive">
									<table id="selectTable" class="display" style="min-width: 845px">
										<thead>
											<tr>
												<th class=" pr-3">
													<div class="custom-control custom-checkbox mx-2">
														<input type="checkbox" class="custom-control-input" id="checkAll">
														<label class="custom-control-label" for="checkAll"></label>
													</div>
												</th>
												<th>쿠폰 번호</th>
												<th>쿠폰 이름</th>
												<th>적용 등급</th>
												<th>쿠폰 설명</th>
												<th>할인율</th>
												<th>사용 기간(일)</th>
												<th>수동 발급</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="coupon" items="${couponList}">
												<fmt:formatNumber var="discount_rate" value="${coupon.discount_rate}" pattern="###%" />
												<fmt:formatNumber var="validity_period" value="${coupon.validity_period}" pattern="###일" />
												<c:choose>
													<c:when test="${coupon.status != 'D' }">
														<tr>
															<td class=" pr-3">
																<div class="custom-control custom-checkbox mx-2">
																	<input type="checkbox" class="custom-control-input" name="coupon" id="${coupon.coupon_policy_no }" value="${coupon.coupon_policy_no }">
																	<label
																		class="custom-control-label"
																		for="${coupon.coupon_policy_no }"></label>
																</div>
															</td>
															<td>${coupon.coupon_policy_no }</td>
															<td id="coupon${coupon.coupon_policy_no }">${coupon.coupon_policy_name }</td>
															<c:choose>
																<c:when test="${empty coupon.member_grade_name }">
																	<td>전체</td>
																</c:when>
																<c:otherwise>
																	<td>${coupon.member_grade_name }</td>
																</c:otherwise>
															</c:choose>
															<td>${coupon.coupon_discription }</td>
															<td>${discount_rate}</td>
															<td>${validity_period}</td>
															<td>
																<c:choose>
																	<c:when test="${coupon.status == 'Y' }">
																		<span class="badge light badge-success">가능</span>
																	</c:when>
																	<c:otherwise>
																		<span class="badge light badge-warning">불가능</span>
																	</c:otherwise>
																</c:choose>
															</td>
															<td>
																<div class="dropdown ml-auto text-right">
																	<div class="btn-link" data-toggle="dropdown">
																		<svg width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
	                                 										<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
		                                    									<rect x="0" y="0" width="24" height="24"></rect>
		                                    									<circle fill="#000000" cx="5" cy="12" r="2"></circle>
		                                    									<circle fill="#000000" cx="12" cy="12" r="2"></circle>
		                                    									<circle fill="#000000" cx="19" cy="12" r="2"></circle>
	                                   										</g>
                                  										</svg>
																	</div>
																	<div class="dropdown-menu dropdown-menu-right">
																		<a class="dropdown-item" data-toggle="modal" data-target="#deleteCoupon" onclick="return delCoupon('${coupon.coupon_policy_no}')"> 삭제 </a>
																	</div>
																</div>
															</td>
														</tr>
													</c:when>
													<c:otherwise>
														<tr class="deletedCoupon">
															<td class=" pr-3">
																<div class="custom-control custom-checkbox mx-2">
																	<input type="checkbox" class="custom-control-input" name="coupon" id="${coupon.coupon_policy_no }" value="${coupon.coupon_policy_no }">
																	<label class="custom-control-label" for="${coupon.coupon_policy_no }"></label>
																</div>
															</td>
															<td>${coupon.coupon_policy_no }</td>
															<td id="coupon${coupon.coupon_policy_no }">${coupon.coupon_policy_name }</td>
															<c:choose>
																<c:when test="${empty coupon.member_grade_name }">
																	<td>전체</td>
																</c:when>
																<c:otherwise>
																	<td>${coupon.member_grade_name }</td>
																</c:otherwise>
															</c:choose>
															<td>${coupon.coupon_discription }</td>
															<td>${discount_rate}</td>
															<td>${validity_period}</td>
															<td>
																<span class="badge light badge-secondary">삭제된 쿠폰</span>
															</td>
															<td>
																<div class="dropdown ml-auto text-right">
																	<div class="btn-link" data-toggle="dropdown">
																		<svg width="24px" height="24px" viewBox="0 0 24 24" version="1.1">
	                                 										<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
		                                    									<rect x="0" y="0" width="24" height="24"></rect>
		                                    									<circle fill="#000000" cx="5" cy="12" r="2"></circle>
		                                    									<circle fill="#000000" cx="12" cy="12" r="2"></circle>
		                                    									<circle fill="#000000" cx="19" cy="12" r="2"></circle>
                                   											</g>
                                  										</svg>
																	</div>
																	<div class="dropdown-menu dropdown-menu-right">
																		<a class="dropdown-item" data-toggle="modal" data-target="#deleteCoupon" onclick="return availableCoupon(true, '${coupon.coupon_policy_no }')"> 복구시키기 </a>
																	</div>
																</div>
															</td>
														</tr>
													</c:otherwise>
												</c:choose>
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
	<div class="modal fade" id="deleteCoupon">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">쿠폰 삭제</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#deleteCoupon')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<h6>
						<p>
							현재 <span id="availavleCoupon"></span>명의 회원이 이 쿠폰을 갖고 있습니다.
						</p>
						<span id="delCouponNo"></span> 번 "<span id="delCouponName"></span>"
						쿠폰을 삭제하시겠습니까?
					</h6>
					지금 삭제한다면 사용 및 복구가 어렵다는 것을 인지하고 있습니다.
					<input type="checkbox" id="doubleCheck" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#deleteCoupon')">취소</button>
					<button type="button" class="btn btn-primary" onclick="return deleteCoupon()">삭제</button>
				</div>
			</div>
		</div>
	</div>


<!-- Datatable -->
<script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>

<!-- Toastr -->
<script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
</body>
<script>
	let lang_kor = {
			"decimal" : "",
			"emptyTable" : "데이터가 없습니다.",
			"info" : "_START_ - _END_ (총 _TOTAL_ 개)",
			"infoEmpty" : "0명",
			"infoFiltered" : "(전체 _MAX_ 명 중 검색결과)",
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
		
	function fillAllinput() {

		// 쿠폰 이름 작성 확인
		if ($("#coupon_name").val() == '') {
			toastError('쿠폰 이름을 작성해주세요.', '필수 사항 확인');
			return false;
		}

		// 쿠폰 설명 작성 확인
		if ($("#coupon_discription").val() == '') {
			toastError('쿠폰 설명을 작성해주세요.', '필수 사항 확인');
			return false;
		}

		// 할인율 설정 확인
		if ($("#discount_rate").val() == '') {
			toastError('할인율을 설정해주세요.', '필수 사항 확인');
			return false;
		}

		// 유효기간 설정 확인
		if ($("#validity_period").val() == '') {
			toastError('유효기간을 설정해주세요.', '필수 사항 확인');
			return false;
		}

	}


	function delCoupon(coupon_number, result) {
		let couponNo = coupon_number;
		let delCouponNo = "#coupon" + coupon_number;
		let delCouponName = $(delCouponNo).html();
		let availableCouponDTO = {
				"coupon_policy_no" : coupon_number,
				"member_no" : result
		}
		
		$.ajax({
			url : "./countAvailableCoupon",
			type : "post",
			contentType: 'application/json; charset=utf-8',
			data :JSON.stringify(availableCouponDTO),
			dataType : "text",
			async : false,
			success : function(data) {
				$('#availavleCoupon').html(data);

			},
			error : function(data) {
			}
		});

		if(coupon_number == 0){
			coupon_number = '';
			delCouponName = '';
			$.each(result, function(e, i ){
				if(e != (result.length - 1)) {
				coupon_number += i + ', ';
				delCouponName +=  $("#coupon" + i).html() + ', ';			
				} else {
				coupon_number += i;
				delCouponName +=  $("#coupon" + i).html();			
					
				}
			})
			
		}
		$('#delCouponNo').html(coupon_number);
		$('#delCouponName').html(delCouponName);
		
	}
	
	// 체크된 쿠폰 목록 가져오기
	function selectCoupons(){
		let classresult = [];
		let result = [];
		
		  // 선택된 목록 가져오기
		  const query = 'input[name="coupon"]:checked';
		  const selectedEls = 
		      document.querySelectorAll(query);
		
		  selectedEls.forEach((el) => {
			  if(!classresult.includes(el.className.split(' ')[1])){
				  let coupon_policy_no = el.className.split(' ')[1];
				  classresult.push(coupon_policy_no);
			  }
			    result.push(el.value)
			  });
		  
		  if(result.length == 0){
			  toastError("쿠폰을 선택하세요.", "오류");
				return false;
			}
		 
		 return result;
		  
	}
	
	function delCoupons(){
		let result = selectCoupons();
		if(result.length > 0){
			delCoupon(0, result);
		}
	}
	
	function availableCoupon(bool, couponNo){
		let result = [couponNo];

		if(couponNo == null){
			result = selectCoupons();
		}
		if(result.length > 0){
			let changeAvailableCoupon = {
					"change_coupon_no" : result,
					"isAvailable" : bool

			};
			$.ajax({
				url : "./changeAvailableCoupon",
				type : "post",
				contentType: 'application/json; charset=utf-8',
				data :JSON.stringify(changeAvailableCoupon),
				dataType : "text",
				async : false,
				success : function(data) {
					toastSuccess('쿠폰 상태 변경 완료했습니다.', '변경 완료');
					location.href = "/admin/member/coupon";

				},
				error : function(data) {
					console.log(data.responseText);
				}
			});
		}
	}
	function modalReset(modalName) {
		$('#doubleCheck').prop('checked', false);
		$(modalName).hide();

	}

	function deleteCoupon() {
		let delCouponNo = $('#delCouponNo').html();

		if (!$('#doubleCheck').prop('checked')) {
			toastError('지금 삭제하면 복구가 어렵다는 설명에 체크해주세요.', '중복 확인 필수');
			return false;
		}

		$.ajax({
			url : "./deleteCoupon",
			type : "post",
			data : {
				"delCouponNo" : delCouponNo,
			},
			dataType : "text",
			async : false,
			success : function(data) {
				toastSuccess('삭제 처리를 완료했습니다.');
				location.href = './coupon';

			}
		});
	}

	$(function() {
		$(".giveCoupon").change(function(e) {

			let coupon = {
				"member_grade_name" : e.target.value
			}
			$.ajax({
				url : "./coupon",
				type : "post",
				contentType : 'application/json',
				data : JSON.stringify(coupon),
				dataType : "text",
				async : false,
				success : function(data) {
					parsingData(data);

				}
			});

		})
		
		$(document).on('click', '.paginate_button', function(){
       	 $("#checkAll").prop("checked", false);
       	 $('input[name="coupon"]').prop("checked", $("#checkAll").prop("checked"));
       });
       
       $(document).on('click', '.dropdown', function(){
       	 $("#checkAll").prop("checked", false);
       	 $('input[name="coupon"]').prop("checked", $("#checkAll").prop("checked"));
       });
        
      $(document).on('click', '#selectTable_filter', function(){
     	  $("#checkAll").prop("checked", false);
     	  $('input[name="coupon"]').prop("checked", $("#checkAll").prop("checked"));
      });
	})

	function parsingData(data) {

		let output = "<select id='choose_coupon' name='coupon_policy_no'>";
		output += "<option value='0'>쿠폰을 선택하세요.</option>";
		$.each(JSON.parse(data),function(index, coupon) {
			output += `<option value="\${coupon.coupon_policy_no}">\${coupon.coupon_policy_name}</option>`;

		})

		output += "</select>";

		$('.selectCoupon').html(output);
		$('#choose_coupon').selectpicker('refresh');
	}
	
	$(function(){
		if(${param.status == 'success'}){
			toastSuccess('쿠폰 지급을 완료했습니다.', '성공');
		} else if(${param.status == 'fail'}){
			toastError('쿠폰 지급에 실패했습니다.','실패');
		}
		$('#checkAll').change(function(){
				$( 'input[name="coupon"]').prop('checked', $('#checkAll').prop('checked'));
			
		})
		
		$('#selectTable').DataTable({
			language : lang_kor
		});
	})
	
	 function toastSuccess(message, title) {
        toastr.success(message, title, {
            positionClass: "toast-top-center",
            timeOut: 5e3,
            closeButton: !0,
            debug: !1,
            newestOnTop: !0,
            progressBar: !0,
            preventDuplicates: !0,
            onclick: null,
            showDuration: "300",
            hideDuration: "1000",
            extendedTimeOut: "1000",
            showEasing: "swing",
            hideEasing: "linear",
            showMethod: "fadeIn",
            hideMethod: "fadeOut",
            tapToDismiss: !1
        })
	}
	
	function toastError(message, title) {

        toastr.error(message, title, {
            positionClass: "toast-top-center",
            timeOut: 5e3,
            closeButton: !0,
            debug: !1,
            newestOnTop: !0,
            progressBar: !0,
            preventDuplicates: !0,
            onclick: null,
            showDuration: "300",
            hideDuration: "1000",
            extendedTimeOut: "1000",
            showEasing: "swing",
            hideEasing: "linear",
            showMethod: "fadeIn",
            hideMethod: "fadeOut",
            tapToDismiss: !1
        })
    }
	
	function vaildCoupon(){
		if($("#choose_coupon").val() == 0){	
			toastError("쿠폰을 선택해주세요.", "쿠폰 선택");
			return false;
		}
		
	}
</script>

</html>
