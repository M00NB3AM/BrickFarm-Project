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
</style>
</head>
<body>
	<div id="main-wrapper">
		<jsp:include page="../header.jsp"></jsp:include>
		<div class="content-body">
			<div class="container-fluid">
				<div class="row page-titles mx-0">
					<div class="col-sm-6 p-md-0">
						<div class="welcome-text">
							<h4>쿠폰 이력 조회</h4>

						</div>
					</div>
					<div class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="${contextPath }/admin/member/dashboard">회원</a>
							</li>
							<li class="breadcrumb-item active">
								<a href="${contextPath }/admin/member/manageMember">회원 관리</a>
							</li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-xl-12">
						<div class="card">
							<div class="card-header">
								<h4 class="card-title">쿠폰 이력 조회</h4>
							</div>
							<jsp:include page="./manageMemberSearchBar.jsp"></jsp:include>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="card">
							<div class="card-body">
								<div class="table-responsive">
									<table id="selectTable" class="display" style="min-width: 845px">
										<thead>
											<tr>
												<th>쿠폰 번호</th>
												<th>아이디</th>
												<th>이름</th>
												<th>쿠폰 이름</th>
												<th>발급일</th>
												<th>만료일</th>
												<th>사용가능여부</th>
												<th>사용주문번호</th>
												<th>사용일</th>
												<th>상태</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="coupon" items="${couponLogList }">
												<fmt:formatDate var="published_date" value="${coupon.published_date}" pattern="yyyy-MM-dd" />
												<fmt:formatDate var="expiration_date" value="${coupon.expiration_date}" pattern="yyyy-MM-dd" />
												<fmt:formatDate var="usage_date" value="${coupon.usage_date}" pattern="yyyy-MM-dd HH:mm:ss" />
												<tr>
													<td>${coupon.coupon_held_no }</td>
													<td>${coupon.member_id }</td>
													<td>${coupon.member_name }</td>
													<td>${coupon.coupon_policy_name }</td>
													<td>${published_date}</td>
													<td>${expiration_date}</td>
													<td>
													<c:choose>
														<c:when test="${coupon.available_coupon == 'Y' }">
															<span class="badge light badge-success">가능</span>
														</c:when>
														<c:otherwise>
															<span class="badge light badge-warning">불가능</span>
														</c:otherwise>
													</c:choose>
													</td>
													<td>${coupon.merchant_uid }</td>
													<td>${usage_date }</td>
													<td>${coupon.usage_reason }</td>
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
	
	<!-- Datatable -->
<script src="${contextPath }/resources/admin/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="${contextPath }/resources/admin/js/plugins-init/datatables.init.js"></script>
<script type="text/javascript">
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
	$(function() {
		$('#selectTable').DataTable({
			language : lang_kor
		});
	})
	</script>
</body>
</html>
