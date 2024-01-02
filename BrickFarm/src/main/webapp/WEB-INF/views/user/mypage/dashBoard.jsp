
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<title>Brick Farm</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="images/favicon.png" rel="shortcut icon" />
<link rel="manifest" href="/site.webmanifest">
<!--====== Google Font ======-->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script type="text/javascript">
window.onload = function() {
	  const currentPageURL = window.location.pathname;
	  const links = document.querySelectorAll(".dash__f-list a");
	  links.forEach(link => {
	    if (link.getAttribute("href") === currentPageURL) {
	      link.classList.add("dash-active");
	    }
	  });
	  
	  $.ajax({
          type: 'GET',
          url: '/mypage/showcnt', // 컨트롤러 URL
          success: function(response) {
        	  drwaCnt(response)
          },
          error: function() {
              console.error('데이터 전송 실패');
          }
      });
	  function drwaCnt(data) {
		console.log(data.orderCnt)
		$("#order_count").html(data.orderCnt);
		$("#cancel_count").html(data.cancelCnt);
		$("#wishlist_count").html(data.wishListCnt);
	}
}

</script>
</head>
<body>
	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<!--====== Dashboard Features ======-->
	<div class="dash__box dash__box--bg-white dash__box--shadow u-s-m-b-30">
		<div class="dash__pad-1">

			<span class="dash__text u-s-m-b-16">안녕하세요 ${loginMemberInfo.member_id} 님!</span>
			<ul class="dash__f-list" id = "dashBoardList">
				<li><a href="/mypage">내 정보</a></li>
				<li><a href="/mypage/profile">내 프로필</a></li>
				<li><a href="/mypage/addresslist">배송지 관리</a></li>
				<li><a href="/mypage/orderlist">주문 내역</a></li>
				<li><a href="/mypage/cancelandrefundlist">취소/교환/반품 내역</a></li>
				<li><a href="/mypage/shoppingcart">장바구니</a></li>
				<li><a href="/mypage/couponhistory">쿠폰 내역</a></li>
				<li><a href="/mypage/pointhistory">적립금 내역</a></li>
			</ul>
		</div>
	</div>
	<div
		class="dash__box dash__box--bg-white dash__box--shadow dash__box--w">
		<div class="dash__pad-1">
			<ul class="dash__w-list">
				<li>
					<div class="dash__w-wrap" style=" cursor: pointer;" onclick="location.href='/mypage/orderlist';">
						<span class="dash__w-icon dash__w-icon-style-1"><i
							class="fas fa-cart-arrow-down"></i></span> <span class="dash__w-text" id="order_count"></span>

						<span class="dash__w-name">주문</span>
					</div>
				</li>
				<li>
					<div class="dash__w-wrap" style=" cursor: pointer;" onclick="location.href='/mypage/cancelandrefundlist';">
						<span class="dash__w-icon dash__w-icon-style-2"><i
							class="fas fa-times"></i></span> <span class="dash__w-text" id="cancel_count"></span> <span
							class="dash__w-name">취소 / 교환 / 반품</span>
					</div>
				</li>
				<li>
					<div class="dash__w-wrap" style=" cursor: pointer;" onclick="location.href='/mypage/wishlist';">
						<span class="dash__w-icon dash__w-icon-style-3"><i
							class="far fa-heart"></i></span> <span class="dash__w-text" id ="wishlist_count"></span> <span
							class="dash__w-name">찜</span>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<!--====== End - Dashboard Features ======-->
</body>
</html>