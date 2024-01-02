<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<title>Brick Farm</title>
<meta name="viewport"
   content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link href="images/favicon.png" rel="shortcut icon" />
<link rel="/manifest" href="/site.webmanifest" />

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
<!-- ===== Alert Custom ===== -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script type="text/javascript">

let shoppingCartListlength = 0;

// ============ 상품 갯수 변동 관련 =================
// 텍스트 온체인지
window.onload = function () {
	// 시작시 데이터 갯수(인덱스) 넣어주기
	shoppingCartListlength = $("input[name=shoppingCartItem]").length;
	$("input[name=productCnt]").on("change", function () {
		checkedBox();
    });
}
// 전체 선택
function allCheckOn() {
	console.log(shoppingCartListlength);
	let checkBoxs = $("input[name=shoppingCartItem]");
	checkBoxs.prop("checked", true);
	checkedBox();
}
// 전체 해제
function allCheckOff() {
	let checkBoxs = $("input[name=shoppingCartItem]");
	checkBoxs.prop("checked", false);
	checkedBox();
}

// 체크된 항목의 금액 
function checkedBox() {
	let checkedTotalPrice = 0;
	$("input[name=shoppingCartItem]:checked").each(function() {
	    let tag = $(this).closest("tr").find("input[name=productCnt]");
		let quantity = tag.val();
		
		const priceElement = $(this).closest("tr").find(".table-p__price");
		const priceText = priceElement.text();
		const price = parseFloat(priceText.replace(/[^0-9]/g, ''));
		console.log("price : " + price)
		console.log("quantity : " + quantity)
		checkedTotalPrice += price * quantity
	})
	$("#shoppingcart-total-Price").html("상품 금액 : " + new Intl.NumberFormat('ko-KR').format(checkedTotalPrice) + "원");
}

//============ 상품 갯수 변동 관련 끝 =================
   
//============ 상품 삭제 관련 =================
// 단일 품목 삭제
function deleteShoppingCart(ShoppingCartNo) {

    $.ajax({
           type: 'POST',
           url: '/mypage/deleteshoppingcart', // 컨트롤러 URL
           data: {
              "shoppingCartNo" : ShoppingCartNo
           },
           success: function(response) {
               console.log('데이터 전송 성공: ' + response);
               $(`#shoppingCartList\${ShoppingCartNo}`).remove();
               checkedBox();
               getHeaderShoppingList(${sessionScope.loginMemberInfo.member_no});
               if($("input[name=shoppingCartItem]").length == 0) { 
               	location.reload();
               }
           },
           error: function() {
               console.error('데이터 전송 실패');
           }
       });
}

// 모든 품목 삭제
function deleteAllShoppingCart() {
    $.ajax({
           type: 'POST',
           url: '/mypage/deleteallshoppingcart', // 컨트롤러 URL
           data: {
              "memberNo" : ${loginMemberInfo.member_no}
           },
           success: function(response) {
               console.log('데이터 전송 성공: ' + response);
               location.reload();
           },
           error: function() {
               console.error('데이터 전송 실패');
           }
       });
}

//============ 상품 삭제 관련 끝 =================

// ================ 데이터 전송 관련 =============

function submit() {
   
   // 예외처리를 위한 체크된 갯수 구해오는것
   const query = document.querySelectorAll('input[name="shoppingCartItem"]:checked');
   
   if(query.length == 0) {
      swal('', "구매할 상품을 선택해주세요.", 'warning')
   } else {   
      // 이게 폼 보내는것================================================
      let form = document.getElementById("orderPageForm");
      form.action = "/user/payment/orderPage";
        form.method = "POST";
        console.log(form);
        form.submit();
        //===================================================================
   }
   
}


// 페이지를 벗어날 때 장바구니 품목 수량 변경 (뒤로가기,구매하기, 마이페이지, 홈으로 이동, 인터넷 종료)
window.addEventListener("beforeunload", function (event) {
	const shoppingCartNoList = [];
	const shoppingCartQuantityList = [];
   for(i = 0 ; i < shoppingCartListlength ; i++) {
	  if($("#shoppingCartItem" + i).val() != null){   
        shoppingCartNoList.push($("#shoppingCartItem" + i).val())
        shoppingCartQuantityList.push($("#productCnt" + i).val())
	  }
   }
   
    $.ajax({
           type: 'POST',
           url: '/mypage/changeShoppingCartProductCnt',
           data: {
              shoppingCartNoList : shoppingCartNoList,
              shoppingCartQuantityList : shoppingCartQuantityList,
           },
           success: function(response) {
               console.log('데이터 전송 성공: ' + response);
           },
           error: function() {
               console.error('데이터 전송 실패');
           },  
       });
});



//================ 데이터 전송 관련 끝 =============	
	
</script>


<style type="text/css">
#shoppingcart-total-Price {
   font-size: 16px;
    font-weight: 700;
    color: #ff4500;
    display: flex;
    justify-content: end;
    margin: 10px 10px;
}

</style>
</head>
<body class="config">
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
   <jsp:include page="../header.jsp"></jsp:include>
   <div class="preloader is-active">
      <div class="preloader__wrap">
         <img class="preloader__img"
            src="/resources/user/images/preloader.png" alt="" />
      </div>
   </div>
    <!--====== Main App ======-->
    <div id="app">
        <div class="app-content">
            <!--====== Section 1 ======-->
            <div class="u-s-p-y-10">
                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="container">
                        <div class="breadcrumb">
                            <div class="breadcrumb__wrap">
                                <ul class="breadcrumb__list">
                                    <li class="has-separator">

                                        <a href="/">Home</a></li>
                                          <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
                                    <li class="is-marked"><a>장바구니</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--====== End - Section 1 ======-->


            <!--====== Section 2 ======-->
            <div class="u-s-p-b-10">
                <!--====== Section Intro ======-->
                <div class="section__intro u-s-m-b-10">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section__text-wrap">
                                    <h1 class="section__heading u-c-secondary">장바구니</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Intro ======-->

                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12 col-md-12 col-sm-12 u-s-m-b-30">
                                <div class="u-s-m-b-10">
									<button style="cursor: pointer;" class="address-book-edit btn--e-transparent-platinum-b-2" type="button" onclick="allCheckOn()">전체 선택</button>
									<button style="cursor: pointer;" class="address-book-edit btn--e-transparent-platinum-b-2" type="button" onclick="allCheckOff()">전체 취소</button>
								</div>
                                <div class="table-responsive" style="max-height:700px">
                                <div id="formContainer">
                                    <form id="orderPageForm">
                                    <table class="table-p">
                                        <tbody>
                                            <!--====== 반복 Row ======-->
                                            <c:forEach var="shoppingCart" items="${shoppingCartList}" varStatus = "status"  >
                                            <tr id="shoppingCartList${shoppingCart.shopping_cart_no}">      
                                                  <td>
                                                      <!--====== Check Box ======-->
                                                        <div class="check-box">
                                                        <input id="shoppingCartItem${status.index}" type="checkbox" name="shoppingCartItem" value="${shoppingCart.shopping_cart_no}" onclick="checkedBox(this)">
                                                        <div class="check-box__state check-box__state--primary">
                                                        <label class="check-box__label" for="free-shipping"></label>
                                                        </div>
                                                        </div>
                                                        </td>
                                                        <!--====== End - Check Box ======-->
                                                <td>
                                                    <div class="table-p__box">
                                                        <div class="table-p__img-wrap">

                                                            <img class="u-img-fluid" src="${shoppingCart.product_main_image}" alt=""></div>
                                                        <div class="table-p__info">

                                                            <span class="table-p__name" style="display: flex">
                                                                <a href="/user/product/productDetail?productCode=${shoppingCart.product_code }" style="padding-top: 4px;">${shoppingCart.product_name}&nbsp;</a>
                                                <c:if test="${shoppingCart.event_no > 0 }">
                                                                <img alt="" src="/resources/user/images/psj/sale.png" style="width: 30px; height: 30px;">
                                                                </c:if>
                                                            </span>

                                                            <span class="table-p__category">
                                                            <c:choose>
                                                            <c:when test="${shoppingCart.upper_category_no == 2 }">
                                                                <a href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">${shoppingCart.product_category_name}</a>
                                                            </c:when>
                                                             <c:when test="${shoppingCart.upper_category_no == 3 }">
                                                                <a href="/user/product/productListAll?category=ships&pageNo=1&showCount=12&sortMethod=lastOrderSort">${shoppingCart.product_category_name}</a>
                                                            </c:when>
                                                            <c:when test="${shoppingCart.upper_category_no == 4 }">
                                                                <a href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort">${shoppingCart.product_category_name}</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                               <a href="/user/product/productListAll?category=all&pageNo=1&showCount=12&sortMethod=lastOrderSort">${shoppingCart.product_category_name}</a>
                                                            </c:otherwise>
                                                            </c:choose>    
                                                            </span>
                                                            <ul class="table-p__variant-list">
                                                                <li>

                                                                    <span>부품수: ${shoppingCart.parts_quantity}</span></li>
                                                                <li>

                                                                    <span>연령: ${shoppingCart.recommend_age}</span></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                   
                                               
                                       <c:if test="${shoppingCart.event_no > 0 }">
                                       <del class="pd-detail__del">
                                                    <fmt:formatNumber value="${shoppingCart.product_price}" type="currency" currencySymbol="" />
                                                    원
                                                    </del>
                                                    <span class=""><span style="color: red; font-size: 12px">&nbsp;<fmt:formatNumber value="${shoppingCart.discount_rate * 100}" type="number"/>%</span></span>
                                                    <span class="table-p__price" id="product_price" style="color: red">
                                                    <fmt:formatNumber value="${shoppingCart.product_price - (shoppingCart.product_price  * shoppingCart.discount_rate)}" type="currency" currencySymbol="" />
                                                    원
                                                    </span>
                                                    </c:if>
                                                    <c:if test="${shoppingCart.event_no == 0 }">
                                                    <span class="table-p__price" id="product_price">
                                                    <fmt:formatNumber value="${shoppingCart.product_price - (shoppingCart.product_price  * shoppingCart.discount_rate)}" type="currency" currencySymbol="" />
                                                    원
                                                    </span>
                                                    </c:if>
                                                
                                                </td>
                                                <td>
                                                    <div class="table-p__input-counter-wrap">

                                                        <!--====== 상품 수량 ======-->
                                                        <div class="input-counter">
                                             
                                                            <span class="input-counter__minus fas fa-minus"></span>
                                             
                                                            <input name="productCnt" id="productCnt${status.index}" class="input-counter__text input-counter--text-primary-style" type="text" value="${shoppingCart.quantity}" data-min="1" data-max="${shoppingCart.stock_quantity }">

                                                            <span class="input-counter__plus fas fa-plus"></span></div>
                                                        <!--====== End - Input Counter ======-->
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="table-p__del-wrap">

                                                        <a class="far fa-trash-alt table-p__delete-link" onclick="deleteShoppingCart(${shoppingCart.shopping_cart_no})"></a></div>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                            <!--====== 반복 RoW - End ======-->
                                        </tbody>
                                    </table>
                                  </form>
                                  </div>
                                </div>
                            <div id="shoppingcart-total-Price" style="font-size: 16px;font-weight:700; color: #ff4500;">상품 금액 : <fmt:formatNumber
                                                                  value="0"
                                                                  type="currency" currencySymbol="" />원</div>
                                                                    <div class="u-s-m-b-30" style="display: flex; justify-content: center; margin-top: 20px">
                        <button class="btn btn--e-brand-b-2" type="button" onclick="submit()" style="width: 250px; height: 60px">구매하기</button>
                        </div>
                            </div>
                            <div class="col-lg-12">
                                <div class="route-box">
                                    <div class="route-box__g1">

                                        <a class="route-box__link" href="/user/product/productListAll"><i class="fas fa-long-arrow-alt-left"></i>

                                            <span>계속 쇼핑하기</span></a></div>
                                
                                    <div class="route-box__g2">

                                        <a class="route-box__link" ><i class="fas fa-trash"></i>

                                            <span onclick="deleteAllShoppingCart()">장바구니 비우기</span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                      
                </div>
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 2 ======-->


        </div>
        <!--====== End - App Content ======-->
        </div>
   <jsp:include page="../footer.jsp"></jsp:include>

   <!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
   <script>
      window.ga = function() {
         ga.q.push(arguments);
      };
      ga.q = [];
      ga.l = +new Date();
      ga("create", "UA-XXXXX-Y", "auto");
      ga("send", "pageview");
   </script>
   <script src="https://www.google-analytics.com/analytics.js" async defer></script>

   <!--====== Vendor Js ======-->
   <script src="/resources/user/js/vendor.js"></script>

   <!--====== jQuery Shopnav plugin ======-->
   <script src="/resources/user/js/jquery.shopnav.js"></script>

   <!--====== App ======-->
   <script src="/resources/user/js/app.js"></script>
   <noscript>
      <div class="app-setting">
         <div class="container">
            <div class="row">
               <div class="col-12">
                  <div class="app-setting__wrap">
                     <h1 class="app-setting__h1">JavaScript is disabled in your
                        browser.</h1>

                     <span class="app-setting__text">Please enable JavaScript
                        in your browser or upgrade to a JavaScript-capable browser.</span>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </noscript>
   
	<script>

 
	</script>
        
    </div>
</body>
</html>