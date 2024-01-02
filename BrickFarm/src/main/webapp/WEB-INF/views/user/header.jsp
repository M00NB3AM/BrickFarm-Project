<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />

    <title>헤더</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="manifest" href="/site.webmanifest" />
    <!--====== Vendor Css ======-->
    <link rel="stylesheet" href="/resources/user/css/vendor.css" />

    <!--====== Utility-Spacing ======-->
    <link rel="stylesheet" href="/resources/user/css/utility.css" />

    <!--====== App ======-->
    <link rel="stylesheet" href="/resources/user/css/app.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script>

      function validSearchCheck() {
        let isValid = false;
       let searchKeword = $('#main-search').val();

          if (searchKeword.length < 2 || searchKeword === '') {
            alert('검색어를 2자 이상 입력해주세요.');
          } else {
            isValid = true;
          }
          return isValid;
        }

            $(document).ready(function() {
            	if(${sessionScope.loginMemberInfo != null}){
            		getHeaderShoppingList(${loginMemberInfo.member_no});
            		getHeaderWishList(${loginMemberInfo.member_no});
            	}

        	// 좋아요 리스트 클릭시 로직
                  $('.likeList').on('click', function() {
            	      if (${sessionScope.loginMemberInfo == null || sessionScope.loginMemberInfo == ""}) {
            	    	  $(this).attr({
            	    		  'data-modal': 'modal',
            				  'data-modal-id': '#add-to-cart-needLogin'
            			  });
            		  } else if (${sessionScope.loginMemberInfo != null || sessionScope.loginMemberInfo != ""}) {
            			  location.href = "/mypage/wishlist";
            		  }
                  });



                });

                //미니카트에서 지우기
                function deleteHeaderShoppingCart(ShoppingCartNo) {
                  $.ajax({
                    type: "POST",
                    url: "/mypage/deleteshoppingcart", // 컨트롤러 URL
                    data: {
                      shoppingCartNo: ShoppingCartNo,
                    },
                    success: function (data) {
                      console.log("데이터 전송 성공: ");
                      getHeaderShoppingList(${sessionScope.loginMemberInfo.member_no});
        		if($(`#shoppingCartList\${ShoppingCartNo}`) != null) {
        			$(`#shoppingCartList\${ShoppingCartNo}`).remove();
        			if($("input[name=shoppingCartItem]").length == 0) {
                       	location.reload();
                    }
        		}
                    },
                    error: function () {
                      console.error("데이터 전송 실패");
                    },
                  });
                }


        // 헤더 좋아요 리스트 표시
                function getHeaderWishList(member_no){
                  $.ajax({
                    url: "/user/member/headerwishlist",
                    type: "post",
                    data: {member_no : member_no},
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        headerWishOutput(data.headerWishList);
                    },
                    error: function(error){
                    	console.log(error);
                    }
                  });

                }

                function headerWishOutput(wishcount){
                	let wishCount = wishcount;
                	let wishoutput = "";
                		if(wishCount == 0){
            	    		wishoutput += `<a href="/mypage/wishlist" class="likeList" data-modal="modal" data-modal-id="#add-to-cart-needLogin"><i class="far fa-heart"></i></a>`;
                		}else if(wishCount > 0){
            				wishoutput += `<a href="/mypage/wishlist" class="likeList" data-modal="modal" data-modal-id="#add-to-cart-needLogin"><i class="fas fa-heart" style="color:red"></i></a>`;
                		}
                	$("#headerwishlist").html(wishoutput);
                }



                function getHeaderShoppingList(member_no){
                  $.ajax({
                    url: "/user/member/headershoplist",
                    type: "post",
                    data: {member_no : member_no},
                    async: false,
                    dataType: "json",
                    success: function (data) {
                       headerListOutput(data.list);
                    },
                    error: function(error){
                    	console.log(error);
                    }
                  });
                  }
                function headerListOutput(list){
                	let headerCount = list.length;
                	let output = "<div class='mini-product-container gl-scroll u-s-m-b-5'>";
                	if(list.length == 0){
            			output += `<div class="card-mini-product" style="display : block; text-align : center; border : 3px solid #FF4500; color : #FF4500; font-weight:bolder;"><span>장바구니가 비어있습니다.</span></div>`
                	}else{
                	$.each(list, function(i, item){
                		let product_price = item.product_price.toLocaleString("ko-KR");
                		let discount_rate = (item.discount_rate * 100);
                		let DC_product_price = (item.product_price*(1-item.discount_rate)).toLocaleString("ko-KR");

            			output += `<div class="card-mini-product"><!-- 여기에서 부터 상품 출력 -->
                            <div class="mini-product">
                            <div class="mini-product__image-wrapper">
                              <a class="mini-product__link" href="/user/product/productDetail?productCode=\${item.product_code}">
                                <img class="u-img-fluid" src="\${item.product_main_image}" alt="product_main_image"/>
                              </a>
                            </div>
                            <div class="mini-product__info-wrapper">
                              <span class="mini-product__category">
            							<a href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">\${item.product_category_name}</a>
                                </span>
                              <span class="mini-product__name">
                                <a href="/user/product/productDetail?productCode=\${item.product_code}">\${item.product_name}</a>
                              <span class="mini-product__quantity">x \${item.quantity}</span>
                                </span>`
                              if(item.event_no == 0) {
                              	output += `<span class="mini-product__price"><span>&#8361;\${product_price}</span></span>`;
                              }
                              
                              if(item.event_no > 0) {
                              	output += `<span class="mini-product__price" style="text-decoration:line-through"><span>&#8361;\${product_price}</span></span>
                              	<span><span style="color: red; font-size: 12px">&nbsp;\${discount_rate}%</span></span>
                              	<span class="mini-product__price" style="color: red"><span>&#8361;\${DC_product_price}원</span></span>`
                              }
                              output += `</span>
                            </div>
                          </div>
                          <a class="mini-product__delete-link far fa-trash-alt" onclick="deleteHeaderShoppingCart(\${item.shopping_cart_no})"></a>
                        </div>`

                	})
                	}
                		output+= `</div>`;
                		$("#shoplist").html(output);
                		$(".total-item-round").html(headerCount);
                }
    </script>
  </head>
  <body>
    <c:set var="contextPath" value="<%=request.getContextPath()%>" />
    <header class="header--box-shadow">
      <!--====== Nav 1 ======-->
      <nav class="primary-nav primary-nav-wrapper--border">
        <div class="container">
          <!--====== Primary Nav ======-->
          <div class="primary-nav">
            <!--====== Main Logo ======-->

            <a class="main-logo" href="/">
              <img src="/resources/user/images/logo/logo-1.png" alt="" />
              <span class="main-title table-p__info">Brick Farm</span>
            </a>
            <!--====== End - Main Logo ======-->

            <!--====== Search Form ======-->
            <form class="main-form" action="/user/product/productListAll" method="get">
              <label for="main-search"></label>
              <input
                class="input-text input-text--border-radius input-text--style-1"
                type="text"
                id="main-search"
                name="searchProduct"
                placeholder="Search"
              />

              <button
                class="btn btn--icon fas fa-search main-search-button"
                onclick="return validSearchCheck();"
                type="submit"
              ></button>
            </form>
            <!--====== End - Search Form ======-->

            <!--====== Dropdown Main plugin ======-->
            <div class="menu-init" id="navigation">
              <button class="btn btn--icon toggle-button toggle-button--secondary fas fa-cogs" type="button"></button>

              <!--====== Menu ======-->

              <div class="ah-lg-mode">
                <span class="ah-close">✕ Close</span>

                <!--====== List ======-->
                <c:choose>
                  <c:when test="${empty loginMemberInfo }">
                    <ul class="ah-list ah-list--design1 ah-list--link-color-secondary">
                      <li class="has-dropdown" data-tooltip="tooltip" data-placement="left" title="">
                        <a><i class="far fa-user-circle"></i></a>
                        <span class="js-menu-toggle"></span>
                        <ul style="width: fit-content">
                          <li>
                            <a href="/user/member/loginpage">
                              <!-- <a href="/user/member/snsloginregisterTest"> -->
                              <i class="fas fa-user-plus u-s-m-r-6"></i>
                              <span>로그인</span></a
                            >
                          </li>
                          <li>
                            <a href="/user/member/registermember">
                              <i class="fas fa-user-plus u-s-m-r-6"></i>
                              <span>회원가입</span></a
                            >
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </c:when>
                  <c:when test="${!empty loginMemberInfo }">
                    <ul class="ah-list ah-list--design1 ah-list--link-color-secondary">
                      <li
                        class="has-dropdown"
                        data-tooltip="tooltip"
                        data-placement="left"
                        title="${loginMemberInfo.member_id}"
                      >
                        <a><i class="far fa-user-circle"></i></a>
                        <span class="js-menu-toggle"></span>
                        <ul style="width: fit-content">
                          <li>
                            <a href="/mypage">
                              <i class="fas fa-user-circle u-s-m-r-6"></i>
                              <span>내 정보</span></a
                            >
                          </li>
                          <li>
                            <a href="/user/member/logout">
                              <i class="fas fa-lock-open u-s-m-r-6"> </i><span>로그아웃</span></a
                            >
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </c:when>
                </c:choose>
                <!--====== End - List ======-->
              </div>

              <!--====== End - Menu ======-->
            </div>
            <!--====== End - Dropdown Main plugin ======-->
          </div>
          <!--====== End - Primary Nav ======-->
        </div>
      </nav>
      <!--====== End - Nav 1 ======-->

      <!--====== Nav 2 ======-->
      <nav class="secondary-nav-wrapper">
        <div class="container">
          <!--====== Secondary Nav ======-->
          <div class="secondary-nav">
            <!--====== Dropdown Main plugin ======-->
            <div class="menu-init" id="navigation1">
              <!--====== Menu ======-->
              <div class="ah-lg-mode">
                <span class="ah-close">✕ Close</span>
              </div>
              <!--====== End - Menu ======-->
            </div>
            <!--====== End - Dropdown Main plugin ======-->

            <!--====== Dropdown Main plugin ======-->
            <div class="menu-init" id="navigation2">
              <button class="btn btn--icon toggle-button toggle-button--secondary fas fa-cog" type="button"></button>

              <!--====== Menu ======-->
              <div class="ah-lg-mode">
                <span class="ah-close">✕ Close</span>

                <!--====== List ======-->
                <ul class="ah-list ah-list--design2 ah-list--link-color-secondary">
                  <li><a href="/">홈</a></li>

                  <!--====== Dropdown ======-->
                  <li class="has-dropdown">
                    <a>공지사항<i class="fas fa-angle-down u-s-m-l-6"></i></a>
                    <span class="js-menu-toggle"></span>
                    <ul style="width: 170px">
                      <li><a href="/notice/list">공지사항 및 이벤트</a></li>
                      <li><a href="/notice/membership">등급 및 혜택</a></li>
                    </ul>
                  </li>
                  <!--====== End - Dropdown ======-->

                  <!--====== Dropdown ======-->
                  <li class="has-dropdown">
                    <a>상품<i class="fas fa-angle-down u-s-m-l-6"></i></a>
                    <span class="js-menu-toggle"></span>
                    <ul style="width: 170px">
                      <li><a href="/user/product/productListAll?category=best&pageNo=1">Best 상품</a></li>
                      <li>
                        <a
                          href="/user/product/productListAll?category=all&pageNo=1&showCount=12&sortMethod=lastOrderSort"
                          >전체 상품</a
                        >
                      </li>
                      <li>
                        <a
                          href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort"
                          >자동차</a
                        >
                      </li>
                      <li>
                        <a
                          href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort"
                          >비행기 / 우주선</a
                        >
                      </li>
                      <li>
                        <a
                          href="/user/product/productListAll?category=ships&pageNo=1&showCount=12&sortMethod=lastOrderSort"
                          >배 / 보트</a
                        >
                      </li>
                    </ul>
                  </li>

                  <!--====== Dropdown ======-->
                  <li class="has-dropdown">
                    <a>고객지원<i class="fas fa-angle-down u-s-m-l-6"></i></a>
                    <span class="js-menu-toggle"></span>
                    <ul style="width: 170px">
                      <!-- <li><a href="/customer-service/buildinginstructions">조립 설명서 검색</a></li> -->
                      <li><a href="/customer-service/contact">찾아오시는 길</a></li>
                      <li><a href="/customer-service/inquiry/list">문의하기</a></li>
                      <li><a href="/customer-service/faq">자주묻는 질문</a></li>
                    </ul>
                  </li>
                  <!--====== End - Dropdown ======-->
                </ul>
                <!--====== End - List ======-->
              </div>
              <!--====== End - Menu ======-->
            </div>
            <!--====== End - Dropdown Main plugin ======-->

            <!--====== Dropdown Main plugin ======-->
            <div class="menu-init" id="navigation3">
              <button
                class="btn btn--icon toggle-button toggle-button--secondary fas fa-shopping-bag toggle-button-shop"
                type="button"
              ></button>

              <span class="total-item-round"></span>
              <!-- 장바구니 전체 수량 가져오기 -->

              <!--====== Menu ======-->
              <div class="ah-lg-mode">
                <span class="ah-close">✕ Close</span>

                <!--====== List ======-->
                <ul class="ah-list ah-list--design1 ah-list--link-color-secondary">
                  <!--<li><a href="index.html"><i class="fas fa-home u-c-brand"></i></a></li>-->
                  <c:catch>
                    <c:choose>
                      <c:when test="${!empty loginMemberInfo}"
                        ><!-- session이 있다면 -->
                        <li id="headerwishlist">
                          <a href="/mypage/wishlist"></a>
                        </li>
                      </c:when>
                    </c:choose>
                  </c:catch>
                  <li class="has-dropdown">
                    <c:catch>
                      <c:choose>
                        <c:when test="${!empty loginMemberInfo}">
                          <a class="mini-cart-shop-link">
                            <i class="fas fa-shopping-bag"></i>
                            <span class="total-item-round"></span>
                            <!-- 장바구니의 개수 가져오기 -->
                          </a>
                        </c:when>
                      </c:choose>
                    </c:catch>

                    <!--====== Dropdown ======-->
                    <!-- 장바구니에 있는 목록 불러오기. -->
                    <span class="js-menu-toggle"></span>
                    <div class="mini-cart">
                      <div id="shoplist"></div>
                      <!--====== Mini Product Container ======-->
                      <div class="mini-product-container gl-scroll u-s-m-b-5" style="padding: 0px">
                        <!--====== Card for mini cart ======-->
                      </div>
                      <!--====== End - Mini Product Container ======-->

                      <!--====== Mini Product Statistics ======-->
                      <div class="mini-product-stat">
                        <div class="mini-action">
                          <a class="mini-link btn--e-transparent-secondary-b-2" href="/mypage/shoppingcart">장바구니</a>
                        </div>
                      </div>
                      <!--====== End - Mini Product Statistics ======-->
                    </div>
                    <!--====== End - Dropdown ======-->
                  </li>
                </ul>
                <!--====== End - List ======-->
              </div>
              <!--====== End - Menu ======-->
            </div>
            <!--====== End - Dropdown Main plugin ======-->
          </div>
          <!--====== End - Secondary Nav ======-->

          <!--====== Modal Section ======-->
          <!--====== Add to Cart Modal ======-->
          <div class="modal fade" id="add-to-cart-needLogin">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content modal-radius modal-shadow">
                <button class="btn dismiss-button fas fa-times" type="button" data-dismiss="modal"></button>
                <div class="modal-body">
                  <div id="loginCheckModal">
                    <div class="s-option">
                      <div class="success__text-wrap" id="loginCheckText">
                        <span
                          >로그인이 필요한 항목입니다.
                          <br />
                          <br />
                          로그인 하시겠습니까?
                        </span>
                      </div>
                      <div class="s-option__link-box" style="display: flex">
                        <a class="s-option__link btn--e-white-brand-shadow" href="/user/member/loginpage" id="checkBox2"
                          >로그인</a
                        >
                        <a class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal" id="checkBox1"
                          >쇼핑 계속하기</a
                        >
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Add to Cart Modal ======-->
          <!--====== End - Modal Section ======-->
        </div>
      </nav>
      <!--====== End - Nav 2 ======-->
    </header>
    <!--====== End - Main Header ======-->
    <script>
      window.ga = function () {
        ga.q.push(arguments);
      };
      ga.q = [];
      ga.l = +new Date();
      ga('create', 'UA-XXXXX-Y', 'auto');
      ga('send', 'pageview');
    </script>
    <script src="https://www.google-analytics.com/analytics.js" async defer></script>

    <!--====== Vendor Js ======-->
    <script src="/resources/user/js/vendor.js"></script>

    <!--====== jQuery Shopnav plugin ======-->
    <script src="/resources/user/js/jquery.shopnav.js"></script>

    <!--====== App ======-->
    <script src="/resources/user/js/app.js"></script>

    <!--====== Noscript ======-->
    <noscript>
      <div class="app-setting">
        <div class="container">
          <div class="row">
            <div class="col-12">
              <div class="app-setting__wrap">
                <h1 class="app-setting__h1">JavaScript is disabled in your browser.</h1>

                <span class="app-setting__text"
                  >Please enable JavaScript in your browser or upgrade to a JavaScript-capable browser.</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </noscript>
  </body>
</html>
