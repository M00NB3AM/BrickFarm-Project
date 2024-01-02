<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
    <meta charset="UTF-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="/resources/admin/images/logo/logo-1.png" rel="shortcut icon">
    <title>BrickFarm</title>

    <!--====== Google Font ======-->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet">

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
			let productCode = $('.productCode').val();
			let productImage = $('.productImage').val();
		    let productName = $('.productName').val();
		    let productPrice = $('.productPrice').val();
		    let productQuantity = $('#productQuantity').val();
		     
		    console.log(memberNo);
		    console.log(productCode);
			console.log(productImage);
			console.log(productName);
			console.log(productPrice);
			console.log(productQuantity);

			$.ajax({
    	        url: "/mypage/addToCart", // 컨트롤러 URL로 변경
    	        type: "POST",
    	        data: { "memberNo" : memberNo,
    	        		"productCode" : productCode,
    	        		"productImage" : productImage,
    	        		"productName" : productName,
    	        	    "productPrice" : productPrice,
    	        	    "productQuantity" : productQuantity },
				dataType : "json",
				async : false,
    	        success: function(data) {
    	        	console.log(data);
    	        	drawingModal(data);
    	        	getHeaderShoppingList(memberNo);
    	        }
    	    });
			
		});
   		
   		// 상품 좋아요
   		$('.heart-toggle').on('click', function() {
   			
   			if(${sessionScope.loginMemberInfo == null || sessionScope.loginMemberInfo == ""}) {
   				$(this).attr({
   					'data-modal': 'modal',
   					'data-modal-id': '#add-to-cart-needLogin'
   				});
   				
   			} else if (${sessionScope.loginMemberInfo != null}) {
   				
   				let memberNo = "${sessionScope.loginMemberInfo.member_no}";
   				let productCode = $(this).closest('.pd-detail__inline').find('.productCode').val();
   				let isLiked = $(this).hasClass('fas');
   				let heartElement = $(this);
   				
   				$.ajax({
   	    	        url: "/mypage/addToWishlist",
   	    	        type: "POST",
   	    	        data: { "memberNo" : memberNo,
   	    	        		"productCode" : productCode,
   	    	        		"isLiked": isLiked},
   					dataType : "text",
   					async : false,
   	    	        success: function(data) {
   	    	        	console.log(data);
		   	    		getHeaderWishList(memberNo);
		   	    		heartElement.toggleClass('far fa-heart fas fa-heart');
   	    	        	
   	    	        }
   	    	    });
   				
   				// 1. ajax 통신 성공해야 바뀌어야 되는거 아닌가?
   				// 2. 토글 클래스를 써도 되나? 좋아요 해제 -> DB 좋아요 list에서 삭제 해야하는데 음..
   	    		

   			}
   		});
   		
   		$('#sort-review').on('change', function() {
   			let productCode = $('.productCode').val();
   			let sortReview = $(this).val();
			
			console.log(sortReview);
			
			$.ajax({
	    	        url: "/user/product/changeReview",
	    	        type: "POST",
	    	        data: { "productCode" : productCode,
	    	        		"sortReview" : sortReview
	    	        		},
					dataType : "json",
					async : false,
	    	        success: function(data) {
	    	        	drawingReview(data);
	    	        	console.log(data);
	    	        }
	    	    });
						
		});
   		
   		
   		function drawingReview(dataList) {
 
   	   		let output = "";
   	   		
   	   		$('.drawReview').empty();
   	   		
   	   		$.each(dataList, function(index, data) {
   	   			
   	   			let memberName = data.member_name;
   	   			let createdDate = data.created_date;
   	   			let starCount = data.star_count;
   	   			let content = data.content;
   	   			
   	   			
   	   			let writeDate = new Date(createdDate);
   	   			let formattedDate = writeDate.toISOString().split('T')[0];
   	   			
   	   			console.log(memberName);
   	   			console.log(createdDate);
   	   			console.log(starCount)
   	   			console.log(content);
   	   			
   	   			output += `<div class="review-o u-s-m-b-15">
   					            <div class="review-o__info u-s-m-b-8">
   					
   					                <span class="review-o__name">\${memberName }</span>
   					                <span class="review-o__date">\${formattedDate}</span>
   					            </div>
   					            <div class="review-o__rating gl-rating-style u-s-m-b-8">`;
   					            	for (let i = 1; i <= starCount; i++) {
   				output +=              	`<i class="fas fa-star"></i>`;
   					            	}
   					            	
   				output +=		`<span>\${starCount }</span>
   					            </div>
   					            <p class="review-o__text">\${content }</p>
   					        </div>`;
   	   		});
   	   		
   	   		
   	   		
   	   		$('.drawReview').html(output);
   	   		
   	   	};
   		
   	});
   	
   	function drawingModal(data) {
   		let productCode = data.product.product_code;
   		let productImage = data.product.product_main_image;
   		let productName = data.product.product_name;
   		let productPrice = data.product.product_price;
   		let price = productPrice.toLocaleString();
   		let productQuantity = data.productQuantity;
   		
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
   												<span class="success__quantity">수량 : \${productQuantity}</span>
   												<span class="success__price">\${price} 원</span>
   											</div>
   										</div>
   									</div>
   									<div class="col-lg-6 col-md-12">
   										<div class="s-option">
   											<div class="s-option__link-box">
   												<a class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal">쇼핑 계속하기</a> 
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
    <div class="preloader is-active">
        <div class="preloader__wrap">

            <img class="preloader__img" src="/resources/admin/images/lego.gif" alt=""></div>
    </div>

    <!--====== Main App ======-->
    <div id="app"></div>

        <!--====== Main Header ======-->
        <jsp:include page="../header.jsp"></jsp:include>
        <!--====== End - Main Header ======-->


        <!--====== App Content ======-->
        <div class="app-content">

            <!--====== Section 1 ======-->
            <div class="u-s-p-t-90">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-5">

                            <!--====== Product Breadcrumb ======-->
                            <div class="pd-breadcrumb u-s-m-b-30">
                                <ul class="pd-breadcrumb__list">
                                    <li class="has-separator">

                                        <a href="/user/product/productListAll?category=all&pageNo=1&showCount=12&sortMethod=lastOrderSort">전체 상품</a></li>
                                    <li class="has-separator"><c:choose>
                                    	<c:when test="${product.product_category_no == 5}">
												<a
													href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
											</c:when>
											<c:when test="${product.product_category_no == 7}">
												<a
													href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
											</c:when>
											<c:when test="${product.product_category_no == 15}">
												<a
													href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
											</c:when>
											<c:when test="${product.product_category_no == 14}">
												<a
													href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
											</c:when>
											<c:when test="${product.product_category_no == 6}">
												<a
													href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
											</c:when>
											<c:when test="${product.product_category_no == 13}">
												<a
													href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
											</c:when>
											<c:when test="${product.product_category_no == 8}">
												<a
													href="/user/product/productListAll?category=ships&pageNo=1&showCount=12&sortMethod=lastOrderSort">배 / 보트</a>
											</c:when>
											<c:when test="${product.product_category_no == 9}">
												<a
													href="/user/product/productListAll?category=ships&pageNo=1&showCount=12&sortMethod=lastOrderSort">배 / 보트</a>
											</c:when>
											<c:when test="${product.product_category_no == 10}">
												<a
													href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기 / 우주선</a>
											</c:when>
											<c:when test="${product.product_category_no == 11}">
												<a
													href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기 / 우주선</a>
											</c:when>
											<c:when test="${product.product_category_no == 12}">
												<a
													href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기 / 우주선</a>
											</c:when>
                                    		</c:choose></li>
									<li class="has-separator"><c:choose>
											<c:when test="${product.product_category_no == 5}">
												<a
													href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">스포츠카</a>
											</c:when>
											<c:when test="${product.product_category_no == 7}">
												<a
													href="/user/product/productListAll?category=racingCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">레이싱카</a>
											</c:when>
											<c:when test="${product.product_category_no == 15}">
												<a
													href="/user/product/productListAll?category=sedan&pageNo=1&showCount=12&sortMethod=lastOrderSort">승용차</a>
											</c:when>
											<c:when test="${product.product_category_no == 14}">
												<a
													href="/user/product/productListAll?category=largeCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">대형차</a>
											</c:when>
											<c:when test="${product.product_category_no == 6}">
												<a
													href="/user/product/productListAll?category=suv&pageNo=1&showCount=12&sortMethod=lastOrderSort">오프로드
													& SUV</a>
											</c:when>
											<c:when test="${product.product_category_no == 13}">
												<a
													href="/user/product/productListAll?category=motorCycle&pageNo=1&showCount=12&sortMethod=lastOrderSort">오토바이</a>
											</c:when>
											<c:when test="${product.product_category_no == 8}">
												<a
													href="/user/product/productListAll?category=ship&pageNo=1&showCount=12&sortMethod=lastOrderSort">배</a>
											</c:when>
											<c:when test="${product.product_category_no == 9}">
												<a
													href="/user/product/productListAll?category=boat&pageNo=1&showCount=12&sortMethod=lastOrderSort">보트</a>
											</c:when>
											<c:when test="${product.product_category_no == 10}">
												<a
													href="/user/product/productListAll?category=airplane&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기</a>
											</c:when>
											<c:when test="${product.product_category_no == 11}">
												<a
													href="/user/product/productListAll?category=Helicopter&pageNo=1&showCount=12&sortMethod=lastOrderSort">헬리콥터</a>
											</c:when>
											<c:when test="${product.product_category_no == 12}">
												<a
													href="/user/product/productListAll?category=spacecraft&pageNo=1&showCount=12&sortMethod=lastOrderSort">우주선</a>
											</c:when>
										</c:choose></li>
									<li class="is-marked">

                                        <a href="/user/product/productDetail?productCode=${product.product_code}">${product.product_name }</a></li>
                                </ul>
                            </div>
                            <!--====== End - Product Breadcrumb ======-->


                            <!--====== Product Detail Zoom ======-->
                            <div class="pd u-s-m-b-30">
                                <div class="slider-fouc pd-wrap">
                                    <div id="pd-o-initiate">
                                    <c:choose>
                                    	<c:when test="${fn:length(productImages) < 6}">
                                 		<c:forEach items="${productImages }" var="productImages" begin="0" end="4" step="1">
                                        	<div class="pd-o-img-wrap" data-src="${productImages.product_file_path }">
                                            	<img class="u-img-fluid" src="${productImages.product_file_path}" data-zoom-image="${productImages.product_file_path}" alt="">
                                        	</div>
                                        </c:forEach>
                                        </c:when>
                                    </c:choose>  
                                      
                               
                                    </div>

                                    <span class="pd-text">Click for larger zoom</span>
                                </div>
                                <div class="u-s-m-t-15">
                                    <div class="slider-fouc">
                                        <div id="pd-o-thumbnail">
                                            <c:choose>
                                    			<c:when test="${fn:length(productImages) < 6}">
                                 					<c:forEach items="${productImages }" var="productImages" begin="0" end="4" step="1">
                                        				<div data-src="${productImages.product_file_path }">
                                            				<img class="u-img-fluid" src="${productImages.product_file_path}">
                                        				</div>
                                       				</c:forEach>
                                        		</c:when>
                                    		</c:choose>  
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--====== End - Product Detail Zoom ======-->
                        </div>
                        <div class="col-lg-7">

                            <!--====== Product Right Side Details ======-->
                            <div class="pd-detail">
                                    <span class="pd-detail__name">${product.product_name }</span></div>
                                <div>
                                    <div class="pd-detail__inline">

									<c:if test="${not empty product.product_price}">
										<div class="product-m__price">
											<c:choose>
												<c:when test="${product.discount_rate == 0}">
													<span class="pd-detail__price">
														<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
													</span>
												</c:when>
												
												<c:when test="${product.discount_rate != 0}">
													<c:set var="discountPrice" value="${product.product_price - (product.product_price * product.discount_rate)}" />
													<span class="pd-detail__price">
														<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
													</span>
													<span class="pd-detail__discount" style="text-decoration: line-through;">
														<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
													</span>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${product.discount_rate != 0 }">
													<span class="pd-detail__discount">
														<c:set var="discountRate" value="${product.discount_rate * 100}"></c:set>
														<fmt:parseNumber type="number" value="${discountRate}" integerOnly="true" var="integerDiscountRate" />
															${integerDiscountRate }% 할인
													</span>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>	
										</div>
									</c:if>
                                        
                                        
                                </div>
                                <div class="u-s-m-b-15">
                                    <div class="pd-detail__rating gl-rating-style">
                                    		<c:choose>
                                    			<c:when test="${totalStarCount > 0.1 and totalStarCount < 1}">
			                                    	<i class="fas fa-star-half-alt"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount == 1 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount > 1 and totalStarCount < 2 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star-half-alt"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount == 2 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount > 2 and totalStarCount < 3 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star-half-alt"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount == 3}">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="far fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount > 3 and totalStarCount < 4 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star-half-alt"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount == 4 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="far fa-star"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount > 4 and totalStarCount < 5 }">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star-half-alt"></i>
		                                    	</c:when>
		                                    	<c:when test="${totalStarCount == 5}">
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
			                                    	<i class="fas fa-star"></i>
		                                    	</c:when>
		                                    	<c:otherwise>
		                                    		<i class="far fa-star"></i>
					                                <i class="far fa-star"></i>
					                                <i class="far fa-star"></i>
					                                <i class="far fa-star"></i>
					                                <i class="far fa-star"></i>
		                                    	</c:otherwise>
                                    		</c:choose>
                                        <span class="pd-detail__review u-s-m-l-4">
                                            <a data-click-scroll="#view-review">${totalReview } Reviews</a>
                                        </span>
                                    </div>
                                </div>
                                <div class="u-s-m-b-15">
                                    <div class="pd-detail__inline">
                                        <span class="pd-detail__stock">200 in stock</span>
                                        <span class="pd-detail__left">Only ${stockQuantity } left</span>
                                    </div>
                                </div>
                                <div class="u-s-m-b-15">
                                    <span class="pd-detail__preview-desc">${product.product_short_description }</span>
                                </div>
                                <div class="u-s-m-b-15">
                                    <div class="pd-detail__inline">
                                    	<input type="hidden" class="productCode" value="${product.product_code }">
                                    	
                                    	<c:set var="isLiked" value="false" />
												<c:forEach items="${likeProductList }" var="likeProduct">
													<c:if test="${likeProduct.product_code == product.product_code }">
														<c:set var="isLiked" value="true" />
													</c:if>	
												</c:forEach>
																
											<c:choose>
												<c:when test="${sessionScope.loginMemberInfo == null }">
													<span class="pd-detail__click-wrap">
														<a class="heart-toggle far fa-heart" data-tooltip="tooltip"
														data-placement="top" title="Add to Wishlist" style="color: #FF0000;"
														data-modal="modal" data-modal-id="#add-to-cart-needLogin">
														</a>
														<a data-modal="modal" data-modal-id="#add-to-cart-needLogin">Add to Wishlist</a>
													</span>
												</c:when>
												<c:when test="${isLiked == true and sessionScope.loginMemberInfo != null}">
													<span class="pd-detail__click-wrap">
														<a class="heart-toggle fas fa-heart" data-tooltip="tooltip"
														data-placement="top" title="Add to Wishlist" style="color: #FF0000;">
														</a>
														<a href="/mypage/wishlist">Add to Wishlist</a>
													</span>
												</c:when>
												<c:when test="${isLiked == false and sessionScope.loginMemberInfo != null }">
													<span class="pd-detail__click-wrap">
														<a class="heart-toggle far fa-heart" data-tooltip="tooltip"
														data-placement="top" title="Add to Wishlist" style="color: #FF0000;">
														</a>
														<a href="/mypage/wishlist">Add to Wishlist</a>
													</span>
												</c:when>
											</c:choose>
                                    </div>
                                </div>

                                <div class="u-s-m-b-15">
                                    <ul class="pd-social-list">
                                   		<li id="product-number-f"><img id="product-number-d" src="/resources/user/images/logo/product-number.png">제품 번호
                                   			${product.product_code }</li>
                                        <li id="recommend-age-f"><img id="recommend-age-d" src="/resources/user/images/logo/recommend-age.png">추천 연령
                                            ${product.recommend_age }</li>
                                        <li id="parts-quantity-f" ><img id="parts-quantity-d" src="/resources/user/images/logo/blocks.png">브릭 갯수
											${product.parts_quantity }</li>
                                    </ul>
                                </div>
                                <div class="u-s-m-b-15">
                                    <form class="pd-detail__form">
                                        <div class="pd-detail-inline-2">
                                            <div class="u-s-m-b-15">

                                                <!--====== Input Counter ======-->
                                                <c:choose>
                                                	<c:when test="${stockQuantity == 0 }">
                                                		<img id="soldOutImgDetail" src="/resources/user/images/logo/soldout.png"  alt="" >
                                                	</c:when>
                                                	
                                                	<c:when test="${stockQuantity != 0 }">
                                                		<div class="input-counter">
                                                    		<span class="input-counter__minus fas fa-minus"></span>

                                                    		<input class="input-counter__text input-counter--text-primary-style"
                                                    		id="productQuantity" type="text" value="1" data-min="1" data-max="${stockQuantity }">

                                                    		<span class="input-counter__plus fas fa-plus"></span>
                                                		</div>
                                                	</c:when>
                                                </c:choose>
                                            </div>
                                            	<!--====== End - Input Counter ======-->
                                            <c:if test="${stockQuantity != 0}">
                                            	<c:choose>
                                            		<c:when test="${loginMemberInfo != null }">
			                                            <div class="u-s-m-b-15">
			                                            	<a class="btn--e-brand" data-modal="modal" data-modal-id="#add-to-cart">
																<button class="btn btn--e-brand-b-2 addToCartProduct" type="button">Add to Cart</button>
															</a>
			                                            </div>
			                                            <div class="product-m__add-cart">
															<input type="hidden" class="productCode" value="${product.product_code }">
															<input type="hidden" class="productImage" value="${product.product_main_image }">
					            							<input type="hidden" class="productName" value="${product.product_name }">
					            							<input type="hidden" class="productPrice" value="${product.product_price }">
														</div>
													</c:when>
													<c:when test="${loginMemberInfo == null }">
														<div class="product-m__add-cart">
															<a class="btn--e-brand" data-modal="modal" data-modal-id="#add-to-cart-needLogin">
																<button class="btn btn--e-brand-b-2 addToCartProduct" type="button">Add to Cart</button>
															</a>
														</div>
													</c:when>
												</c:choose>
											</c:if>
                                        </div>
                                    </form>
                                </div>
                                <div class="u-s-m-b-15">

                                        <span class="pd-detail__label u-s-m-b-8">제품 정책 :</span>
                                        <ul class="pd-detail__policy-list">
                                            <li>
                                            	<i class="fas fa-check-circle u-s-m-r-8"></i>
												 <span>구매자 보호</span>
                                             </li>
                                            <li>
                                            	<i class="fas fa-check-circle u-s-m-r-8"></i>
                                                <span>배송을 받지 못하신 경우 환불 가능</span>
                                            </li>
                                            <li>
                                            	<i class="fas fa-check-circle u-s-m-r-8"></i>
                                            	<span>제품이 설명과 다를 시 환불 가능</span>
                                            </li>
                                        </ul>
                                    </div>
                            </div>
                            <!--====== End - Product Right Side Details ======-->
                        </div>
                    </div>
                </div>
            </div>

            <!--====== Product Detail Tab ======-->
            <div class="u-s-p-y-90">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="pd-tab">
                                <div class="u-s-m-b-30">
                                    <ul class="nav pd-tab__list">
                                        <li class="nav-item">
                                            <a class="nav-link active" data-toggle="tab" href="#pd-desc">DESCRIPTION</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="view-review" data-toggle="tab" href="#pd-rev">REVIEWS
                                            	<c:choose>
                                            		<c:when test="${totalReview == null or totalReview == 0 }">
                                            			<span>(0)</span>
                                            		</c:when>
                                            		<c:when test="${totalReview != 0 }">
                                            			<span>(${totalReview })</span>
                                            		</c:when>
                                            	</c:choose>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="tab-content">

                                    <!--====== Tab 1 ======-->
                                    <div class="tab-pane fade show active" id="pd-desc">
                                        <div class="pd-tab__desc">
                                            <div class="u-s-m-b-15">
                                            	<c:choose>
                                            		<c:when test="${product.product_code != 10294 }">
                                            			<img src="${product.product_main_image }" alt="" />
                                            		</c:when>
                                            		<c:when test="${product.product_code == 10294 }">
                                            			<div class="titanic">
                                            				<img src="/resources/user/images/product/titanic_main.jpg" class="titanicMain">
                                            				<div class="text-overlay">신제품 브릭팜® 타이타닉으로 조립해 보는<br>꿈의 여객선</div>
                                            				<img src="/resources/user/images/product/titanic_main2.jpg">
                                            				<div class="text-overlay2">
                                            					<h3>실제 비율대로 재현한 조립 작품</h3>
	                                            				<br>
	                                            				신제품 브릭팜® 타이타닉은 피스 9,000여 개로 전설적인 여객선을
	                                            				<br> 
                                            					1:200 비율로 충실하게 재현해 낸 모델입니다
                                            				</div>
                                            				<img src="/resources/user/images/product/titanic_main3.jpg">
                                            				<div class="text-overlay3">
                                            					<h3>최고의 경험</h3>
                                            					<br>
                                            					바다의 여왕으로 불린 여객선을 여유를 가지고 조립하며
                                            					<br>
                                            					까다롭지만 뿌듯한 시간을 만끽해 보세요.
                                            				</div>
                                            			</div>     
                                            		</c:when>
                                            	</c:choose>
                                                ${product.product_description }
                                            </div>
                                            <div id="detailDescriptionImage">
                                            	<img src="${productImage2.product_file_path }" alt="">
                                            </div>
                                            <%--
                                            <div class="u-s-m-b-30"><iframe src="https://www.youtube.com/watch?v=WBVCNO9L0cc"></iframe></div>
                                             --%>
                                        </div>
                                    </div>
                                    <!--====== End - Tab 1 ======-->

                                    <!--====== Tab 3 ======-->
                                    <div class="tab-pane" id="pd-rev">
                                        <div class="pd-tab__rev">
                                            <div class="u-s-m-b-30">
                                                <form class="pd-tab__rev-f1">
                                                    <div class="rev-f1__group">
                                                        <div class="u-s-m-b-15">
                                                        	<c:choose>
                                                        	    <c:when test="${totalReview eq null }">
                                                            		<h2 style="font-size: 15px; font-weight: 1000;
                                                            		color: #333333; text-align: center;">아직 리뷰가 없습니다. 상품을 구매해 리뷰를 남겨보세요!</h2>
                                                            	</c:when>
                                                        		<c:when test="${totalReview != 0}">
                                                            		<h2>${totalReview } 개의 리뷰가 있습니다.</h2>
                                                            	</c:when>

                                                            </c:choose>
                                                        </div>
                                                        <div class="u-s-m-b-15">
                                                        	<c:if test="${totalReview != 0}">
	                                                            <label for="sort-review">
	                                                            	<select class="select-box select-box--primary-style" id="sort-review">
	                                                                	<option value="highRating" selected>별점 높은 순</option>
	                                                                	<option value="rowRating">별점 낮은 순</option>
	                                                            	</select>
	                                                            </label>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                    <div class="drawReview">
	                                                    <c:forEach items="${productReviewList }" var="productReview">
		                                                    <div class="rev-f1__review">
		                                                        <div class="review-o u-s-m-b-15">
		                                                            <div class="review-o__info u-s-m-b-8">
		
		                                                                <span class="review-o__name">${productReview.member_name }</span>
		                                                                <c:set var="writeDate" value="${productReview.created_date }" />
		                                                                <span class="review-o__date">${fn:substring(writeDate, 0, 10)}</span>
		                                                            </div>
		                                                            <div class="review-o__rating gl-rating-style u-s-m-b-8">
		                                                            	<c:forEach var="i" begin="1" end="${productReview.star_count}">
																	    	<i class="fas fa-star"></i>
																		</c:forEach>
																		<span>${productReview.star_count }</span>
		                                                            </div>
		                                                            <p class="review-o__text">${productReview.content }</p>
		                                                            
		                                                            <c:if test="${not empty productReview.new_file_name}">
																	    <!-- Iterate over the list of image file names -->
																	    <c:forEach items="${productReview.new_file_name}" var="thumbFileName">
																	        <c:set var="convertedPath" value="${fn:replace(thumbFileName, '\', '/')}"/>
																	        <img src="/resources/user/uploads/review${convertedPath}" alt="" style="width:300px; height:300px; object-fit: contain;
																	        padding:15px;">
																	    </c:forEach>
																	</c:if>
		                                                        </div>
		                                                    </div>
	                                                    </c:forEach>
                                                    </div>
                                                    <!-- 
                                                    <ul class="shop-p__pagination">									
														<c:if test="${empty productReviewList}">
															<div id="searchResult">
																
															</div>
														</c:if>
														
															<c:set var="prevBlockStart"
																value="${requestScope.pagingInfo.startNumOfCurrentPagingBlock - 1}" />
					
															<c:if test="${prevBlockStart >= 1 and param.pageNo > 1}">
																<li><a class="fas fa-angle-double-left"
																	href="${prevBlockStart}"></a></li>
															</c:if>
					
															<c:forEach var="i"
																begin="${requestScope.pagingInfo.startNumOfCurrentPagingBlock}"
																end="${requestScope.pagingInfo.endNumOfCurrentPagingBlock}"
																step="1">
																<c:choose>
																	<c:when test="${requestScope.pagingInfo.pageNo == '' or requestScope.pagingInfo.pageNo == null }">
																	</c:when>
																	<c:when test="${requestScope.pagingInfo.pageNo == i }">
																		<li class="is-active">
																			<a class="nav-link" id="view-review" data-toggle="tab" href="#pd-rev">${i}</a>
																		</li>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<a class="nav-link" id="view-review" data-toggle="tab" href="#pd-rev">${i}</a>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>

															
															<c:if
																test="${requestScope.pagingInfo.pageNo < requestScope.pagingInfo.totalPageCount or empty requestScope.pagingInfo.pageNo}">
																<c:set var="nextBlockStart"
																	value="${requestScope.pagingInfo.endNumOfCurrentPagingBlock + 1}" />
					
																<c:if
																	test="${nextBlockStart <= requestScope.pagingInfo.totalPageCount}">
																	<li><a class="{nextBlockStart}"></a></li>
																</c:if>
															</c:if>
														</ul>
														 -->
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <!--====== End - Tab 3 ======-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--====== End - Product Detail Tab ======-->
            <div class="u-s-p-b-90">

                <!--====== Section Intro ======-->
                <div class="section__intro u-s-m-b-46">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section__text-wrap">
                                    <h1 class="section__heading u-c-secondary u-s-m-b-12">함께 본 상품</h1>

                                    <span class="section__span u-c-grey">이 제품의 구매자들이 함께 본 상품</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Intro ======-->


                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="container">
                        <div class="slider-fouc">
                            <div class="owl-carousel product-slider" data-item="4">
                            	<c:forEach items="${alsoViewProductList }" var="alsoViewProduct">
                                <div class="u-s-m-b-30">
                                    <div class="product-o product-o--hover-on">
                                    	<a class="aspect aspect--bg-grey aspect--square u-d-block" href="/user/product/productDetail?productCode=${alsoViewProduct.product_code }">
											<img class="aspect__img" src="${alsoViewProduct.product_main_image }" alt=""></a>
                                        	<span class="product-o__category">
                                            	<c:choose>
															<c:when test="${alsoViewProduct.product_category_no == 5}">
																<a href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">스포츠카</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 7}">
																<a href="/user/product/productListAll?category=racingCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">레이싱카</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 15}">
																<a href="/user/product/productListAll?category=sedan&pageNo=1&showCount=12&sortMethod=lastOrderSort">승용차</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 14}">
																<a href="/user/product/productListAll?category=largeCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">대형차</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 6}">
																<a href="/user/product/productListAll?category=suv&pageNo=1&showCount=12&sortMethod=lastOrderSort">오프로드 & SUV</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 13}">
																<a href="/user/product/productListAll?category=motorCycle&pageNo=1&showCount=12&sortMethod=lastOrderSort">오토바이</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 8}">
																<a href="/user/product/productListAll?category=ship&pageNo=1&showCount=12&sortMethod=lastOrderSort">배</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 9}">
																<a href="/user/product/productListAll?category=boat&pageNo=1&showCount=12&sortMethod=lastOrderSort">보트</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 10}">
																<a href="/user/product/productListAll?category=airplane&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 11}">
																<a href="/user/product/productListAll?category=Helicopter&pageNo=1&showCount=12&sortMethod=lastOrderSort">헬리콥터</a>
															</c:when>
															<c:when test="${alsoViewProduct.product_category_no == 12}">
																<a href="/user/product/productListAll?category=spacecraft&pageNo=1&showCount=12&sortMethod=lastOrderSort">우주선</a>
															</c:when>
												</c:choose>
											</span>
                                        <span class="product-o__name">
                                            <a href="/user/product/productDetail?productCode=${product.product_code }">${alsoViewProduct.product_name }</a>
                                        </span>
                                        <div class="product-o__rating gl-rating-style">
                                        	<c:choose>
												<c:when
													test="${alsoViewProduct.total_star_count > 0.1 and alsoViewProduct.total_star_count < 1}">
													<i class="fas fa-star-half-alt"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when test="${alsoViewProduct.total_star_count == 1 }">
													<i class="fas fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when
													test="${alsoViewProduct.total_star_count > 1 and alsoViewProduct.total_star_count < 2 }">
													<i class="fas fa-star"></i>
													<i class="fas fa-star-half-alt"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when test="${alsoViewProduct.total_star_count == 2 }">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when
													test="${alsoViewProduct.total_star_count > 2 and alsoViewProduct.total_star_count < 3 }">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star-half-alt"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when test="${alsoViewProduct.total_star_count == 3}">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when
													test="${alsoViewProduct.total_star_count > 3 and alsoViewProduct.total_star_count < 4 }">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star-half-alt"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when test="${alsoViewProduct.total_star_count == 4 }">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="far fa-star"></i>
												</c:when>
												<c:when
													test="${alsoViewProduct.total_star_count > 4 and alsoViewProduct.total_star_count< 5 }">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star-half-alt"></i>
												</c:when>
												<c:when test="${alsoViewProduct.total_star_count == 5}">
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
													<i class="fas fa-star"></i>
												</c:when>
												<c:otherwise>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
													<i class="far fa-star"></i>
												</c:otherwise>
											</c:choose>
                                        	<span class="product-o__review">(${alsoViewProduct.review_count })</span>
                                        </div>
											
										<c:choose>
                                        	<c:when test="${not empty alsoViewProduct.product_price and alsoViewProduct.stock_quantity != 0}">
												<div class="product-o__price">
													<c:choose>
														<c:when test="${alsoViewProduct.discount_rate == 0}">
															<fmt:formatNumber type="number" value="${alsoViewProduct.product_price}" pattern="###,###" /> 원
														</c:when>
														
														<c:when test="${alsoViewProduct.discount_rate != 0}">
															<c:set var="discountPrice" value="${alsoViewProduct.product_price - (alsoViewProduct.product_price * (alsoViewProduct.discount_rate / 100))}" />
															<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
															<span class="product-o__discount" style="text-decoration: line-through;">
																<fmt:formatNumber type="number" value="${alsoViewProduct.product_price}" pattern="###,###" /> 원
															</span>
														</c:when>
													</c:choose>
												</div>
											</c:when>
											<c:when test="${alsoViewProduct.stock_quantity == 0}">
												<div class="product-o__price">
													<c:choose>
														<c:when test="${alsoViewProduct.discount_rate == 0}">
															<fmt:formatNumber type="number" value="${alsoViewProduct.product_price}" pattern="###,###" /> 원
														</c:when>
																		
														<c:when test="${alsoViewProduct.discount_rate != 0}">
															<c:set var="discountPrice" value="${alsoViewProduct.product_price - (alsoViewProduct.product_price * (alsoViewProduct.discount_rate / 100))}" />
															<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
															<span class="product-m__discount" style="text-decoration: line-through;">
																<fmt:formatNumber type="number" value="${alsoViewProduct.product_price}" pattern="###,###" /> 원
															</span>
														</c:when>
													</c:choose>
													<br>
													<img id="soldOutImg" src="/resources/user/images/logo/soldout.png"  alt="" >
												</div>
											</c:when>
										</c:choose>
                                    </div>
                                </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 1 ======-->
        </div>
        <!--====== End - App Content ======-->


        <!--====== Main Footer ======-->
		<jsp:include page="../footer.jsp"></jsp:include>        
        <!--====== End - Main Footer ======-->
        
        <!--====== Modal Section ======-->
        <!--====== Add to Cart Modal ======-->
        <c:choose>
			<c:when test="${sessionScope.loginMemberInfo == null}">
				<div class="modal fade" id="add-to-cart-needLogin">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content modal-radius modal-shadow">

							<button class="btn dismiss-button fas fa-times" type="button"
								data-dismiss="modal"></button>
							<div class="modal-body">
									<div id="loginCheckModal">
										<div class="s-option">

											<div class="success__text-wrap" id="loginCheckText">
												<span>로그인이 필요한 항목입니다.
												<br>
												<br>
												로그인 하시겠습니까?</span>
											</div>
											<div class="s-option__link-box" style="display:flex;">
												<a class="s-option__link btn--e-white-brand-shadow" href="/user/member/loginpage" id="checkBox2">로그인</a>
												
												<a class="s-option__link btn--e-white-brand-shadow"	data-dismiss="modal" id="checkBox1">쇼핑 계속하기
												</a>
											</div>
										</div>
									</div>
							</div>
						</div>
					</div>
				</div>
			</c:when>
			<c:when test="${sessionScope.loginMemberInfo != null }">
				<div class="modal fade" id="add-to-cart">
				</div>
			</c:when>
		</c:choose>
        <!--====== End - Add to Cart Modal ======-->
        <!--====== End - Modal Section ======-->
    </div>
    <!--====== End - Main App ======-->


    <!--====== Google Analytics: change UA-XXXXX-Y to be your site's ID ======-->
    <script>
        window.ga = function() {
            ga.q.push(arguments)
        };
        ga.q = [];
        ga.l = +new Date;
        ga('create', 'UA-XXXXX-Y', 'auto');
        ga('send', 'pageview')
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

                            <span class="app-setting__text">Please enable JavaScript in your browser or upgrade to a JavaScript-capable browser.</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </noscript>
</body>
</html>