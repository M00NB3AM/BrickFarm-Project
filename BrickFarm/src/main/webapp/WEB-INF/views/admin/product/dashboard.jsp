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
<title>상품 대시보드</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.lineicons.com/2.0/LineIcons.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<style>
.content-body {
	min-height: 0 !important;
}

.d-block {
	color: red;
}

.widget-media .timeline .timeline-panel .media {
	background: none !important;
	border: 1px solid #eee;
}

.col-md-12 {
	height: 580px !important;
}

.items-list-1 {
	padding: 14px 30px !important;
	margin : 0px 30px;
}

#lego {
	position: fixed;
	width: 150px;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	z-index: 999;
}

.fi {
	margin-top: 10px;
	font-size: 40px;
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
		<!--**********************************
            Content body start
        ***********************************-->
		<div class="content-body">
			<!-- row -->
			<div class="container-fluid">
				<div class="form-head d-flex mb-3 align-items-start">
					<div class="mr-auto d-none d-lg-block">
						<h2 class="text-black font-w600 mb-0">Dashboard</h2>
						<p class="mb-0">상품 현황 대시보드</p>
					</div>
				</div>
				<div class="row">
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary"> <i
										class="fi fi-rr-box-open-full"></i>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${productCount}</span>
										</h3>
										<p class="mb-0">전체 상품</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary"> <i
										class="fi fi-rr-dollar"></i>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${saledCount}</span>
										</h3>
										<p class="mb-0">판매 상품</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary"> <i
										class="fi fi-rr-exclamation"></i>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${soldOutCount}</span>
										</h3>
										<p class="mb-0">품절 상품</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-3 col-xxl-3 col-lg-6 col-md-6 col-sm-6">
						<div class="widget-stat card">
							<div class="card-body p-4">
								<div class="media ai-icon">
									<span class="mr-3 bgl-primary text-primary"> <i
										class="fi fi-rr-inbox-out"></i>
									</span>
									<div class="media-body">
										<h3 class="mb-0 text-black">
											<span class="counter ml-0">${carriedOutCount}</span>
										</h3>
										<p class="mb-0">반출 상품</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
						<div class="card">
							<div class="card-header  border-0 pb-0">
								<h4 class="card-title">재고 현황</h4>
							</div>
							<div class="card-body">
								<div id="DZ_W_TimeLine" class="widget-timeline dz-scroll"
									style="height: 440px;">
									<ul class="timeline">
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xl-6 col-xxl-6 col-lg-12 col-md-12">
						<div class="card">
							<div class="card-header border-0 pb-0 d-sm-flex d-block">
								<div>
									<h4 class="card-title mb-1">상품 판매량 순위</h4>
								</div>
							</div>
							<div class="card-body p-0 pt-3">
								<c:forEach var="product" items="${rankingList}"
									varStatus="status">
									<div class="media items-list-1">
										<span
											class="number col-2 px-0 align-self-center font-w600 text-black"><c:out
												value="${status.count}" /> 위</span> <img
											class="img-fluid rounded mr-3" width="85"
											src="${product.product_main_image}" alt="DexignZone">
										<div class="media-body col-sm-4 col-6 col-xxl-6 px-0">
											<h5 class="mt-0 mb-3 text-black">${product.product_name}</h5>
											<small class="text-primary font-w500"><strong
												class="text-secondary mr-2">${String.format("%,d", product.product_price)}원</strong>
											</small>
										</div>
										<div
											class="media-footer ml-auto col-1 px-0 d-flex align-self-center align-items-center">
											<div>
												<h4 class="mb-0">${product.ranking}건</h4>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--**********************************
            Content body end
        ***********************************-->
	</div>

	<script src="/resources/admin/js/custom.min.js"></script>

	<script type="text/javascript">
	function checkStock() {
		$.ajax({
            url: `/admin/product/checkStock`,
            type: 'post',
            // dataType : 'json',
            async: false,
            contentType: 'application/json',
            success: function (data) {
              displayNotification(data.productList);
            },
            error: function (request, error) {
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
	
	function displayNotification(list) {
		let output ='';
		$(list).each(function(index, item){
			if(item.stock_quantity == 0){
				output += `<li>
					  <div class="timeline-badge danger"></div> 
					  <a class="timeline-panel text-muted" href="/admin/product/placeOrder"> 
					  	<h6 class="text-danger font-weight-bold mt-0 mb-2">품절</h6>
					  	<strong>
								\${item.product_name}
						</strong>
					  </a>
				  </li>`;
			} else {
				output += `<li>
					  <div class="timeline-badge warning"></div> 
					  <a class="timeline-panel text-muted" href="/admin/product/placeOrder">
					  		<h6 class="text-warning font-weight-bold mt-0 mb-2">재고 부족</h6>
							<strong>
								\${item.product_name}
							</strong>
							<span>재고 \${item.stock_quantity}개</span>
					  </a>
				  </li>`;
			}
		});
		
		$('.timeline').html(output);
	}
	
	window.onload = function(){ 
		checkStock();
		
		$(".counter").counterUp({
	          delay: 10,
	          time: 1000,
	    });
	};
	</script>

</body>
</html>