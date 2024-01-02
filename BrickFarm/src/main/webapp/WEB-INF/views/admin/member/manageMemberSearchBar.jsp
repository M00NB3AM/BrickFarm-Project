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
</style>
</head>
<body>
	<div class="card-body">
		<!-- Nav tabs -->
		<div class="default-tab">
			<ul class="nav nav-tabs" role="tablist">
				<li class="nav-item">
					<a class="nav-link active" data-toggle="tab" href="#loginLog">접속 기록 조회</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="tab" href="#aboutPoint">적립금 사용 / 적립 조회</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-toggle="tab" href="#aboutCoupon">쿠폰 사용 / 발급 조회</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane fade show active" id="loginLog" role="tabpanel">
					<div class="pt-4">
						<form action="loginLogList" method="post">
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
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">접속 기간</label>
								<div class="col-sm-3">
									<input type="date" class="form-control dateStart startloginLog" name="date_start" value="" onchange="minDate('loginLog')">
								</div>
								<div class="col-sm-3">
									<input type="date" class="form-control dateEnd endloginLog" name="date_end" value="" onchange="maxDate('loginLog')">
								</div>
							</div>
							<input type="submit" class="btn light btn-primary" value="조회" onclick="return valid('loginLog')"></input>
						</form>
					</div>
				</div>
				<div class="tab-pane fade" id="aboutPoint">
					<div class="pt-4">
						<form action="pointAccrualList" method="post">
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
							<fieldset class="form-group">
								<div class="row">
									<label class="col-sm-3 col-form-label">조회 대상</label>
									<div class="col-sm-9">
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="useORaccrual" value="all" checked> 전체
										</label>
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="useORaccrual" value="use"> 사용
										</label>
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="useORaccrual" value="accrual"> 적립
										</label>
									</div>
								</div>
							</fieldset>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">기간</label>
								<div class="col-sm-3">
									<input type="date" class="form-control dateStart startpoint" name="date_start" value="" onchange="minDate('point')">
								</div>
								<div class="col-sm-3">
									<input type="date" class="form-control dateEnd endpoint" name="date_end" value="" onchange="maxDate('point')">
								</div>
							</div>
							<input type="submit" class="btn light btn-primary" value="조회" onclick="return valid('point')" />
						</form>
					</div>
				</div>
				<div class="tab-pane fade" id="aboutCoupon">
					<div class="pt-4">
						<form action="couponList" method="post">
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
							<fieldset class="form-group">
								<div class="row">
									<label class="col-sm-3 col-form-label">조회 대상</label>
									<div class="col-sm-9">
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="useORaccrual" value="all" checked> 전체
										</label>
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="useORaccrual" value="use"> 사용
										</label>
										<label class="form-check-label radio">
											<input class="form-check-input" type="radio" name="useORaccrual" value="accrual"> 발급
										</label>
									</div>
								</div>
							</fieldset>
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">기간</label>
								<div class="col-sm-2">
									<select name="dateOption" class="dateOption">
										<option value="0">기준을 선택해주세요.</option>
										<option value="accrual">발급일</option>
										<option value="usage">만료일</option>
									</select>
								</div>
								<div class="col-sm-3">
									<input type="date" class="form-control dateStart startcoupon" name="date_start" value="" onchange="minDate('coupon')" hidden>
								</div>
								<div class="col-sm-3">
									<input type="date" class="form-control dateEnd endcoupon" name="date_end" value="" onchange="maxDate('coupon')" hidden>
								</div>
							</div>
							<input type="submit" class="btn light btn-primary" value="조회" onclick="return valid('coupon')" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- Toastr -->
<script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
<script>
	$(function() {
		let endDate = new Date();

		$('.dateStart').prop('max', new Date().toISOString().split('T')[0]);
		$('.dateEnd').prop('max', new Date().toISOString().split('T')[0]);
		
		$('.dateOption').change(function (e) {
			if(e.target.value == "0"){
				$('.startcoupon').prop('hidden', true);
				$('.endcoupon').prop('hidden', true);
			} else{
				if(e.target.value == 'usage'){
					$('.startcoupon').prop('max', false);
					$('.endcoupon').prop('max', false);
				} else {
					$('.startcoupon').prop('max', new Date().toISOString().split('T')[0]);
					$('.endcoupon').prop('max', new Date().toISOString().split('T')[0]);
				}
				$('.startcoupon').prop('hidden', false);
				$('.endcoupon').prop('hidden', false);
			}
		})

	})

	function valid(table) {
		let dateStart = '.start' + table;
		let dateEnd = '.end' + table;

		if ($(dateStart).val() == '' && $(dateEnd).val() != '') {
			toastError('기간 시작일을 선택해주세요.', '필수 사항 확인');
			return false;
		}

		if ($(dateStart).val() != '' && $(dateEnd).val() == '') {
			toastError('기간 마감일을 선택해주세요.', '필수 사항 확인');
			return false;
		}
	}

	function minDate(table) {
		let dateStart = '.start' + table;
		let dateEnd = '.end' + table;
		$(dateEnd).prop('min', $(dateStart).val());

	}

	function maxDate(table) {
		let dateStart = '.start' + table;
		let dateEnd = '.end' + table;
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
