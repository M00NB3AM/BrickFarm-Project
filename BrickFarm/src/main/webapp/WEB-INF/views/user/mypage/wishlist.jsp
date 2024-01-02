<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>myPage</title>
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
    <script>
    
    $(document).ready(function() {
    	
    	$('.addToCartProduct').on('click', function() {
			let memberNo = "${sessionScope.loginMemberInfo.member_no}";
			let productCode = $(this).closest('.w-r__wrap-2').find('.productCode').val();
			let productImage = $(this).closest('.w-r__wrap-2').find('.productImage').val();
		    let productName = $(this).closest('.w-r__wrap-2').find('.productName').val();
		    let productPrice = $(this).closest('.w-r__wrap-2').find('.productPrice').val();
		     
		    console.log(memberNo);
		    console.log(productCode);
			console.log(productImage);
			console.log(productName);
			console.log(productPrice);

			$.ajax({
    	        url: "/mypage/addToCart", // 컨트롤러 URL로 변경
    	        type: "POST",
    	        data: { "memberNo" : memberNo,
    	        		"productCode" : productCode,
    	        		"productImage" : productImage,
    	        		"productName" : productName,
    	        	    "productPrice" : productPrice},
				dataType : "json",
				async : false,
    	        success: function(data) {
    	        	console.log(data);
    	        	drawingModal(data);
    	        }
    	    });
			
		});
    	
    	$('.deleteAllProduct').on('click', function() {
    		
    		let memberNo = "${sessionScope.loginMemberInfo.member_no}";
    		
    		$.ajax({
    			url: "/mypage/deleteProductAllToWishlist", // 컨트롤러 URL로 변경
    	        type: "POST",
    	        data: {"memberNo" : memberNo},
				dataType : "json",
				async : false,
    	        success: function(data) {
    	        	console.log(data);
    	        }
    		});
    		
    		location.reload();
    		
    	});
    	
		$('.deleteProduct').on('click', function() {
    		
    		let memberNo = "${sessionScope.loginMemberInfo.member_no}";
    		let productCode = $(this).closest('.w-r__wrap-2').find('.productCode').val();
    		let isLiked = true;
    		
    		console.log(memberNo);
    		console.log(productCode);
    		
    		$.ajax({
    			url: "/mypage/addToWishlist", // 컨트롤러 URL로 변경
    	        type: "POST",
    	        data: { "memberNo" : memberNo,
    	        		"productCode" : productCode,
    	        		"isLiked" : isLiked},
				dataType : "json",
				async : false,
    	        success: function(data) {
    	        	console.log(data);
    	        	
    	        }
    		});
    		location.reload();
		});
    });
    
    function drawingModal(data) {
    	let productCode = data.product.product_code;
    	let productImage = data.product.product_main_image;
    	let productName = data.product.product_name;
    	let productPrice = data.product.product_price;
    	let price = productPrice.toLocaleString();
    	
    	console.log(data);
    	
    	let output = "";
    	output += `<div class="modal-dialog modal-dialog-centered">
    					<div class="modal-content modal-radius modal-shadow">
    						<button class="btn dismiss-button fas fa-times" type="button" data-dismiss="modal"></button>
    						<div class="modal-body">
    							<div class="row">
    								<div class="col-lg-6 col-md-12">
    									<div class="success u-s-m-b-30">
    										<div class="success__text-wrap">
    											<i class="fas fa-check"></i> 
    											<span>상품을 장바구니에 추가했습니다!</span>
    										</div>
    										<div class="success__img-wrap">
    											<img class="u-img-fluid" src="\${productImage}" alt="">
    										</div>
    										<div class="success__info-wrap">
    											<span class="success__name">\${productName}</span>
    											<span class="success__quantity">수량 : 1</span>
    											<span class="success__price">\${price} 원</span>
    										</div>
    									</div>
    								</div>
    								<div class="col-lg-6 col-md-12">
    									<div class="s-option">
    										<div class="s-option__link-box">
    											<a class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal">쇼핑 계속하기</a> 
    											<a class="s-option__link btn--e-white-brand-shadow" href="/user/product/productDetail?productCode=\${productCode }">상품 상세 보기</a> 
    											<a class="s-option__link btn--e-brand-shadow" href="/mypage/shoppingcart">장바구니 가기</a>
    										</div>
    									</div>
    								</div>
    							</div>
    						</div>
    					</div>
    				</div>`;
    	
    	$('#add-to-cart').html(output);
    };
    </script>
  </head>
  
  <body class="config">
  	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<jsp:include page="../header.jsp"></jsp:include>
    <!--====== App Content ======-->
        <div class="app-content">

            <!--====== Section 1 ======-->
            <div class="u-s-p-y-60">

                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="container">
                        <div class="breadcrumb">
                            <div class="breadcrumb__wrap">
                                <ul class="breadcrumb__list">
                                    <li class="has-separator">

                                        <a href="index.html">Home</a></li>
                                    <li class="is-marked">

                                        <a href="wishlist.html">Wishlist</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--====== End - Section 1 ======-->


            <!--====== Section 2 ======-->
            <div class="u-s-p-b-60">

                <!--====== Section Intro ======-->
                <div class="section__intro u-s-m-b-60">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section__text-wrap">
                                    <h1 class="section__heading u-c-secondary">찜 목록</h1>
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
                        		<c:if test="${empty likeProductList}">
                        			<div class="col-lg-12 col-md-12 col-sm-12" id="noneLikeProductList">
                        				아직 상품이 없습니다. 마음에 드는 상품을 찾아 좋아요 버튼을 눌러보세요!
                        			</div> 
                        		</c:if>
                            	<c:forEach items="${likeProductList }" var="likeProduct">
                            		<div class="col-lg-12 col-md-12 col-sm-12">
                            		
                                		<!--====== Wishlist Product ======-->
                                		<div class="w-r u-s-m-b-30">
                                    		<div class="w-r__container">
                                        		<div class="w-r__wrap-1">
                                            		<div class="w-r__img-wrap">
                                            			<img class="u-img-fluid" src="${likeProduct.product_main_image }" alt="">
                                            		</div>
                                            		<div class="w-r__info">                               	
                                                		<span class="w-r__name">
                                                			<a href="/user/product/productDetail?productCode=${likeProduct.product_code}">${likeProduct.product_name }</a>
                                                		</span>
		                                                <span class="w-r__category">
		                                                	<c:choose>
																<c:when test="${likeProduct.product_category_no == 5}">
																	<a href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">스포츠카</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 7}">
																	<a href="/user/product/productListAll?category=racingCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">레이싱카</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 15}">
																	<a href="/user/product/productListAll?category=sedan&pageNo=1&showCount=12&sortMethod=lastOrderSort">승용차</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 14}">
																	<a href="/user/product/productListAll?category=largeCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">대형차</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 6}">
																	<a href="/user/product/productListAll?category=suv&pageNo=1&showCount=12&sortMethod=lastOrderSort">오프로드 & SUV</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 13}">
																	<a href="/user/product/productListAll?category=motorCycle&pageNo=1&showCount=12&sortMethod=lastOrderSort">오토바이</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 8}">
																	<a href="/user/product/productListAll?category=ship&pageNo=1&showCount=12&sortMethod=lastOrderSort">배</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 9}">
																	<a href="/user/product/productListAll?category=boat&pageNo=1&showCount=12&sortMethod=lastOrderSort">보트</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 10}">
																	<a href="/user/product/productListAll?category=airplane&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 11}">
																	<a href="/user/product/productListAll?category=Helicopter&pageNo=1&showCount=12&sortMethod=lastOrderSort">헬리콥터</a>
																</c:when>
																<c:when test="${likeProduct.product_category_no == 12}">
																	<a href="/user/product/productListAll?category=spacecraft&pageNo=1&showCount=12&sortMethod=lastOrderSort">우주선</a>
																</c:when>
															</c:choose>
		                                                </span>
                                                    		
                                                    	<c:choose>
															<c:when test="${not empty likeProduct.product_price and likeProduct.stock_quantity != 0}">
																<c:choose>
																	<c:when test="${likeProduct.discount_rate == 0}">
																		<span class="w-r__price"><fmt:formatNumber type="number" value="${likeProduct.product_price}" pattern="###,###" /> 원</span>
																	</c:when>
																		
																	<c:when test="${likeProduct.discount_rate != 0}">
																		<c:set var="discountPrice" value="${likeProduct.product_price - (likeProduct.product_price * likeProduct.discount_rate)}" />
																		<span class="w-r__price"><fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
																			<span class="w-r__discount" style="text-decoration: line-through;">
																				<fmt:formatNumber type="number" value="${likeProduct.product_price}" pattern="###,###" /> 원
																			</span>
																		</span>
																	</c:when>
																</c:choose>
															</c:when>
															
															<c:when test="${likeProduct.stock_quantity == 0}">
																	<c:choose>
																		<c:when test="${likeProduct.discount_rate == 0}">
																			<span class="w-r__price"><fmt:formatNumber type="number" value="${likeProduct.product_price}" pattern="###,###" /> 원</span>
																		</c:when>
																		
																		<c:when test="${likeProduct.discount_rate != 0}">
																			<c:set var="discountPrice" value="${likeProduct.product_price - (likeProduct.product_price * likeProduct.discount_rate)}" />
																			<span class="w-r__price"><fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
																				<span class="w-r__discount" style="text-decoration: line-through;">
																					<fmt:formatNumber type="number" value="${likeProduct.product_price}" pattern="###,###" /> 원
																				</span>
																			</span>
																		</c:when>
																	</c:choose>
																	<br>
																	<img id="soldOutImg" src="/resources/user/images/logo/soldout.png"  alt="" >
															</c:when>
														</c:choose>
                                                    </div>
                                        		</div>
                                        
                                        		<div class="w-r__wrap-2">
                                        			<c:choose>
                                        				<c:when test="${likeProduct.stock_quantity == 0 }">
                                        					<a class="w-r__link btn--e-brand-b-2"
                                        					style="width: 165px; text-align: center; background-color:#E70012; ">SOLD OUT</a>
                                        					<input type="hidden" class="productCode" value="${likeProduct.product_code }">
                                        				</c:when>
                                        				<c:otherwise>
                                        					<input type="hidden" class="productCode" value="${likeProduct.product_code }">
															<input type="hidden" class="productImage" value="${likeProduct.product_main_image }">
		            										<input type="hidden" class="productName" value="${likeProduct.product_name }">
		            										<input type="hidden" class="productPrice" value="${likeProduct.product_price }">
                                        					<a class="w-r__link btn--e-brand-b-2" data-modal="modal" data-modal-id="#add-to-cart" >
	                                        					<button type="button" class="addToCartProduct" 
	                                        					style="border: none; background: none; 
																padding: 0; cursor: pointer; color: white; width: 100px;">장바구니에 추가
																</button>
                                        					</a>
                                        				</c:otherwise>
                                        			</c:choose>
                                        			<a class="w-r__link btn--e-transparent-platinum-b-2" href="/user/product/productDetail?productCode=${likeProduct.product_code}">상품 상세 보기</a>
                                            		<a class="w-r__link btn--e-transparent-platinum-b-2 deleteProduct">삭제</a>
                                            	</div>
                                    		</div>
                                		</div>
                                		<!--====== End - Wishlist Product ======-->

                            		</div>
                            	</c:forEach>
                            <div class="col-lg-12">
                                <div class="route-box">
                                    <div class="route-box__g">
                                    	<a class="route-box__link" href="/user/product/productListAll?category=all&pageNo=1&showCount=12&sortMethod=lastOrderSort">
                                    		<i class="fas fa-long-arrow-alt-left"></i>
                                    		<span>쇼핑하러 가기</span>
                                    	</a>
                                    </div>
                                    <div class="route-box__g">
                                    	<a class="route-box__link" href="/mypage/shoppingcart"><i class="fas fa-shopping-cart"></i>
                                    		<span>장바구니 가기</span>
                                    	</a>
                                        <a class="route-box__link" data-modal="modal" data-modal-id="#wish-list-deleteCheck"><i class="fas fa-trash"></i>
                                        	<span>찜목록 비우기</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 2 ======-->
            <div class="modal fade" id="wish-list-deleteCheck">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content modal-radius modal-shadow">

							<button class="btn dismiss-button fas fa-times" type="button"
								data-dismiss="modal"></button>
							<div class="modal-body">
									<div id="loginCheckModal">
										<div class="s-option">

											<div class="success__text-wrap" id="loginCheckText">
												<span>삭제를 누르시면 목록이 모두 삭제됩니다.
												<br>
												<br>
												정말 모두 삭제 하시겠습니까?</span>
											</div>
											<div class="s-option__link-box" style="display:flex;">

												<a class="s-option__link btn--e-white-brand-shadow deleteAllProduct" id="checkBox2">삭제</a>
												
												<a class="s-option__link btn--e-white-brand-shadow"	data-dismiss="modal" id="checkBox1">취소
												</a>
											</div>
										</div>
									</div>
							</div>
						</div>
					</div>
				</div>
            
            <div class="modal fade" id="add-to-cart">
			</div>
        </div>
        <!--====== End - App Content ======-->

    <!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
    <script>
      window.ga = function () {
        ga.q.push(arguments);
      };
      ga.q = [];
      ga.l = +new Date();
      ga("create", "UA-XXXXX-Y", "auto");
      ga("send", "pageview");
    </script>
    <script
      src="https://www.google-analytics.com/analytics.js"
      async
      defer
    ></script>

    <!--====== Vendor Js ======-->
    <script src="/resources/user/js/vendor.js"></script>

    <!--====== jQuery Shopnav plugin ======-->
    <script src="/resources/user/js/jquery.shopnav.js"></script>

    <!--====== App ======-->
    <script src="/resources/user/js/app.js"></script>


    <jsp:include page="../footer.jsp"></jsp:include>
  </body>
</html>
