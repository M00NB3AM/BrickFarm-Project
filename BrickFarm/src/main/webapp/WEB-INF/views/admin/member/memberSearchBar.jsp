<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
<!-- Toastr -->
<link rel="stylesheet" href="/resources/admin/vendor/toastr/css/toastr.min.css" />
<style type="text/css">
.radio {
	padding: 20px;
}
.sub-title {
	font-size: 12px;
	color: 'gray';
}
</style>
</head>
<body>

	<div class="card-body">
		<!-- Nav tabs -->
		<div class="default-tab">
			<ul class="nav nav-tabs" role="tablist">
				<li class="nav-item">
					<a class="nav-link active" data-toggle="tab" href="#member">회원 조회</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="tab" href="#orderMember">주문 회원 조회</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="tab" href="#inactiveMember">휴먼 회원 조회</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="tab" href="#withdrawMember">탈퇴 회원 조회</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane fade show active" id="member" role="tabpanel">
					<div class="pt-4">
						<form action="searchMember" method="post">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">이름</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="이름을 입력하세요" value="" name="member_name">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">아이디</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="아이디를 입력하세요" value="" name="member_id">
								</div>
							</div>
							<fieldset class="form-group">
								<div class="row">
									<label class="col-sm-3 col-form-label">회원등급</label>
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
							<input type="submit" class="btn light btn-primary" value="조회"></input>
						</form>
					</div>
				</div>
				<div class="tab-pane fade" id="orderMember">
					<div class="pt-4">
						<form action="orderMember" method="post">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">이름</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="이름을 입력하세요" name="member_name" value="">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">아이디</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="아이디를 입력하세요" name="member_id" value="">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">최근 주문 기간</label>
								<div class="col-sm-3">
									<input type="date" class="form-control dateStart startOrder" name="date_start" value="" onchange="minDate('Order')">
								</div>
								<div class="col-sm-3">
									<input type="date" class="form-control dateEnd endOrder" name="date_end" value="" onchange="maxDate('Order')">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">총 주문 금액</label>
								<div class="col-sm-3">
									<input type="number" class="form-control" id="money_range_start" name="money_range_start" value="0" min="0">
								</div>

								<div class="col-sm-3">
									<input type="number" class="form-control" id="money_range_end" name="money_range_end" value="0" min="0">
								</div>
							</div>
							<fieldset class="form-group">
								<div class="row">
									<label class="col-sm-3 col-form-label">회원 등급</label>
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
							<input type="submit" class="btn light btn-primary" value="조회" onclick="return valid('Order')" />
						</form>
					</div>
				</div>
				<div class="tab-pane fade" id="inactiveMember">
					<div class="pt-4">
						<form action="inactiveMember" method="post">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">이름</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="이름을 입력하세요" name="member_name" value="">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">아이디</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="아이디를 입력하세요" name="member_id" value="">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">검색 기간<span class="sub-title">(휴먼 전환일 기준)</span></label>
								<div class="col-sm-3">
									<input type="date" class="form-control dateStart startInactive" name="date_start" value="" onchange="minDate('Inactive')">
								</div>

								<div class="col-sm-3">
									<input type="date" class="form-control dateEnd endInactive" name="date_end" value="" onchange="maxDate('Inactive')">
								</div>
							</div>
								<div class="form-group row">
								<label class="col-sm-9">
									<input type="checkbox" name="inactive_status"> 현재 휴먼상태인 회원 대상으로만 찾기
								</label>
							</div>
							<input type="submit" class="btn light btn-primary" value="조회" onclick="return valid('Inactive')" />
						</form>
					</div>
				</div>
				<div class="tab-pane fade" id="withdrawMember">
					<div class="pt-4">
						<form action="withdrawMember" method="post">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">이름</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="이름을 입력하세요" name="member_name" value="">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">아이디</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" placeholder="아이디를 입력하세요" name="member_id" value="">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">검색 기간</label>
								<div class="col-sm-3">
									<input type="date" class="form-control dateStart startWithdraw" name="date_start" value="" onchange="minDate('Withdraw')">
								</div>

								<div class="col-sm-3">
									<input type="date" class="form-control dateEnd endWithdraw" name="date_end" value="" onchange="maxDate('Withdraw')">
								</div>
							</div>
							<fieldset class="form-group">
								<div class="row">
									<label class="col-sm-3 col-form-label">탈퇴 이유</label>
									<div class="col-sm-9">
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="withdraw_reason" value="all" checked> 전체
										</label>
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="withdraw_reason" value="자진"> 자진탈퇴
										</label>
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="withdraw_reason" value="강제"> 관리자 강제 탈퇴
										</label>
									</div>
								</div>
							</fieldset>
							<input type="submit" class="btn light btn-primary" value="조회" onclick="return valid('Withdraw')" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
 <!-- Toastr -->
<script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
<script>
$(function (){
	let endDate = new Date();
	
	$('.dateStart').prop('max', new Date().toISOString().split('T')[0]);
	$('.dateEnd').prop('max', new Date().toISOString().split('T')[0]);

})

function valid(table){
	let dateStart = '.start' + table;
	let dateEnd = '.end' + table;
	
	console.log(dateStart, dateEnd);
	
	if($(dateStart).val() == '' && $(dateEnd).val() != ''){
		toastError('기간 시작일을 선택해주세요.', '필수 사항 확인');
		return false;
	}
	
	if($(dateStart).val() != '' && $(dateEnd).val() == ''){
		toastError('기간 마감일을 선택해주세요.', '필수 사항 확인');
		return false;
	}
	if(table == 'Order'){
		console.log(typeof $('#money_range_start').val());
		
		if(parseInt($('#money_range_start').val()) > parseInt($('#money_range_end').val())){
			toastError('최소 주문 금액이 최대 금액보다 클 순 없습니다.', '금액 범위 오류');
			return false;
		}
	}
}

function minDate(table) {
	let dateStart = '.start' + table;
	let dateEnd = '.end' + table;
		console.log( $(dateStart).val());
		$(dateEnd).prop('min', $(dateStart).val());
	
}

function maxDate(table) {
	let dateStart = '.start' + table;
	let dateEnd = '.end' + table;
	console.log($(dateEnd).val());
	$(dateStart).prop('max', $(dateEnd).val());
}

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
