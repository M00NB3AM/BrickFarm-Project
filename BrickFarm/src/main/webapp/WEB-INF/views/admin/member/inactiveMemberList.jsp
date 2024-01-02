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

.searchBar {
	height: fit-content !important;
}

.table {
	width: 100%;
	padding: 0px 0px !important;
}

.table-hover tbody tr {
	cursor: pointer;
}
#indicator {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}
.actbtn{
	padding : 5px 15px !important;
	
}
</style>
</head>
<body>
	<div id="preloader">
		<img id="indicator" src="${contextPath}/resources/admin/images/lego.gif"
			alt="" />
	</div>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>휴먼 회원 관리</h4>

						</div>
					</div>
					<div
						class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="${contextPath }/admin/member/dashboard">회원</a>
							</li>
							<li class="breadcrumb-item active">
								<a href="${contextPath }/admin/member/memberList">회원 조회</a>
							</li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-xl-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">휴먼 회원 조회</h4>
							</div>
							<jsp:include page="./memberSearchBar.jsp"></jsp:include>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
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
												<th>이름</th>
												<th>아이디</th>
												<th>이메일</th>
												<th class="py-2 pl-5">전화 번호</th>
												<th>휴먼 전환 일시</th>
												<th>활성화 일시</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="customers">
											<c:forEach var="member" items="${inactiveMember }">
												<fmt:formatDate var="conversion_date" value="${member.conversion_date}" pattern="yyyy-MM-dd" />
												<fmt:formatDate var="release_date" value="${member.release_date}" pattern="yyyy-MM-dd" />
												<tr class="btn-reveal-trigger">
													<td class="py-2">
														<div class="custom-control custom-checkbox mx-2">
															<input type="checkbox" class="custom-control-input" id="checkbox1" name="member" value="${member.member_no }">
															<label class="custom-control-label" for="checkbox1"></label>
														</div>
													</td>
													<td class="py-3">${member.member_name}</td>
													<td class="py-2">${member.member_id}</td>
													<td class="py-2">${member.email}</td>
													<td class="py-2 pl-5">${member.phone_number}</td>
													<td class="py-2">${conversion_date}</td>
													<c:choose>
														<c:when test="${empty release_date }">
															<td>
																<button class="btn light btn-primary actbtn" data-toggle="modal" data-target="#activeMember" onclick="writeMember('${member.member_id }', '.activeMember' )">
																	활성화하기
																</button>
															</td>
														</c:when>
														<c:otherwise>														
															<td class="py-2">${release_date}</td>
														</c:otherwise>
													</c:choose>
													<td>
														<div class="dropdown">
															<button class="btn btn-primary tp-btn-light sharp" type="button" data-toggle="dropdown">
																<span class="fs--1">
																	<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="18px" height="18px" viewBox="0 0 24 24" version="1.1">
																		<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
																			<rect x="0" y="0" width="24" height="24"></rect>
																			<circle fill="#000000" cx="5" cy="12" r="2"></circle>
																			<circle fill="#000000" cx="12" cy="12" r="2"></circle>
																			<circle fill="#000000" cx="19" cy="12" r="2"></circle>
																		</g>
																	</svg>
																</span>
															</button>
															<div class="dropdown-menu dropdown-menu-right border py-0">
																<div class="py-2">
																	<a class="dropdown-item" data-toggle="modal" data-target="#deleteMember" onclick="writeMember('${member.member_id }', '.delMember')"> 탈퇴하기 </a>
																</div>
															</div>
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

	<!--회원 탈퇴 Modal -->
	<div class="modal fade" id="deleteMember">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">회원 탈퇴</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#deleteMember')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<h6>
						<span class="delMember"></span>님을 탈퇴시키겠습니까?
					</h6>
					<div>
						<div>
							탈퇴 사유 : 
							<select class="selectDelReason">
								<option selected>관리자에 의한 강제 탈퇴</option>
							</select>
						</div>
						<div>
							메모 : <input type="text" name="reason_memo" id="reason_memo" />
						</div>
					</div>
					지금 탈퇴시킨다면 복구가 어렵다는 것을 인지하고 있습니다. 
					<input type="checkbox" id="doubleCheck" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#deleteMember')">취소</button>
					<button type="button" class="btn btn-primary" onclick="return deleteMember()">탈퇴하기</button>
				</div>
			</div>
		</div>
	</div>
	
	
		<!-- 회원 활성화 시키기 Modal -->
	<div class="modal fade" id="activeMember">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">회원 활성화</h5>
					<button type="button" class="close" data-dismiss="modal" onclick="modalReset('#activeMember')">
						<span>&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<h6>
						<span class="activeMember"></span>님을 활성화시키겠습니까?
					</h6>

					지금 활성화 시킨다면 복구가 어렵다는 것을 인지하고 있습니다.
					<input type="checkbox" id="activeCheck" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger light" data-dismiss="modal" onclick="modalReset('#activeMember')">취소</button>
					<button type="button" class="btn btn-primary" onclick="return activeMember()">활성화하기</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
<!-- Datatable -->
<script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>
<!-- Toastr -->
<script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
<script>
	let lang_kor = {
			"decimal" : "",
			"emptyTable" : "데이터가 없습니다.",
			"info" : "_START_ - _END_ (총 _TOTAL_ 명)",
			"infoEmpty" : "0명",
			"infoFiltered" : "(전체 _MAX_ 명 중 검색결과)",
			"infoPostFix" : "",
			"thousands" : ",",
			"lengthMenu" : "_MENU_ 명씩 보기",
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

	function writeMember(id, className) {
		$(className).html(id);

	}
	
	function activeMember(){
		
		if (!$('#activeCheck').prop('checked')) {
			toastError('지금 활성화를 시키면 복구가 어렵다는 설명에 체크해주세요.', '중복 확인 필수');
			return false;
		}
		
		$.ajax({
			url : "./activeMember",
			type : "post",
			data : {
				"member_id" : $('.activeMember').html()
			},
			dataType : "text",
			async : false,
			success : function(data) {

				if(data == 'success'){
					
					toastSuccess('활성화 완료했습니다.', '활성화 완료');
				} else {
					toastError('다시 시도바랍니다.', '활성화 실패');
				}
				location.href = './inactiveMember';
			}
		});
	}

	function deleteMember() {
		let result = false;
		let deleteMember = {
			"member_id" : $('.delMember').html(),
			"reason_memo" : $('#reason_memo').val(),
			"withdraw_reason" : "관리자에 의한 강제 탈퇴"
		};

		if ($('#doubleCheck').prop('checked') && $('#reason_memo').val() != '') {
			result = true;

			$.ajax({
				url : "./deleteMember",
				type : "post",
				contentType : 'application/json',
				data : JSON.stringify(deleteMember),
				dataType : "text",
				async : false,
				success : function(data) {

					toastSuccess('탈퇴 처리를 완료했습니다.', '탈퇴 완료');
					location.href = "./memberList";

				}
			});

		} else if ($('#reason_memo').val() == '') {

			toastError('탈퇴 사유를 작성해주세요.', '필수 사항 확인');
		} else if (!$('#doubleCheck').prop('checked')) {
			toastError('지금 탈퇴하면 복구가 어렵다는 설명에 체크해주세요.', '중복 확인 필수');
		}


		return result;
	}

	function modalReset(modalName) {
		$('#reason_memo').val('');
		$('#doubleCheck').prop('checked', false);
		$(modalName).hide();

	}


	$(function(){
		$('#checkAll').change(function(){
				$( 'input[name="member"]').prop('checked', $('#checkAll').prop('checked'));
			
		});
		
		$('#selectTable').DataTable({
			language : lang_kor
		});
		
		$(document).on('click', '.paginate_button', function(){
       	 $("#checkAll").prop("checked", false);
       	 $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
       });
       
       $(document).on('click', '.dropdown', function(){
       	 $("#checkAll").prop("checked", false);
       	 $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
       });
        
      $(document).on('click', '#selectTable_filter', function(){
     	  $("#checkAll").prop("checked", false);
     	  $('input[name="member"]').prop("checked", $("#checkAll").prop("checked"));
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
</script>
</body>
</html>
