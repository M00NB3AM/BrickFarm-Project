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
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link href="/resources/admin/images/logo/logo-1.png" rel="shortcut icon">
<title>BrickFarm</title>

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
<script>
$(document).ready(function() {

		let showCount = $('#showCountSelect').val();
	    let sortMethod = $('#selectOfSort').val();
	    let category = "${requestScope.category}";
	    let pageNo = ${requestScope.pagingInfo.pageNo};
	    let min = "${requestScope.min}";
	    let max = "${requestScope.max}";
	    
	    
		
	    $('.star-checkbox').on('change', function () {
	        if ($(this).prop('checked')) {
	            // 선택된 경우, 다른 모든 checkbox들을 해제
	            $('.star-checkbox').not(this).prop('checked', false);

	            // 선택된 경우에만 map 함수 실행
	            let ratingValues = $('.star-checkbox:checked').map(function () {
	                return this.value;
	            }).get();
				
	            let selectedRatingValue = ratingValues.length > 0 ? ratingValues[0] : null;
	            
	            console.log(selectedRatingValue);
	            
				$.ajax({
	    	        url: "/user/product/productListOfRating", // 컨트롤러 URL로 변경
	    	        type: "POST",
	    	        data: { "selectedRatingValue" : selectedRatingValue },
					dataType : "json",
					async : false,
	    	        success: function(data) {
	    	        	console.log(data);
	    	        	drawingProduct(data);
	    	        }
	    	    });
	        }
	    });
	    
		
		// 선택 상품 수 변경을 감지
		$('#showCountSelect').on('change',function() {
			// 선택된 옵션 값을 가져옴
			showCount = $(this).val();
			updatePageURL(category, pageNo, showCount, sortMethod, min, max);
		});
		
		$('#selectOfSort').on('change', function() {
			sortMethod = $(this).val();
			updatePageURL(category, pageNo, showCount, sortMethod, min, max);
		});
		
		
		
		
	    function updatePageURL(category, pageNo, showCount, sortMethod, min, max) {
	    	let url = "";
	    	let totalPageCount = ${requestScope.pagingInfo.totalPageCount};
	    	
	    	if(pageNo > totalPageCount) {
	    		pageNo = 1;
	    	}
	    	
	    	url = `/user/product/productListAll?category=\${category}&pageNo=\${pageNo}&showCount=\${showCount}&sortMethod=\${sortMethod}&priceMin=\${min }&priceMax=\${max}`;
	    	
	        location.href = url;
		};
	    
		$('.addToCartProduct').on('click', function() {
			let memberNo = "${sessionScope.loginMemberInfo.member_no}";
			let productCode = $(this).closest('.product-m__add-cart').find('.productCode').val();
			let productImage = $(this).closest('.product-m__add-cart').find('.productImage').val();
		    let productName = $(this).closest('.product-m__add-cart').find('.productName').val();
		    let productPrice = $(this).closest('.product-m__add-cart').find('.productPrice').val();
		     
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
    	        	getHeaderShoppingList(memberNo);
    	        	
    	        }
    	    });
			
		});
		
		// 상품 좋아요
		$('.heart-toggle').on('click', function() {
			
			if (${sessionScope.loginMemberInfo.member_no == null}) {
				$(this).attr({
					'data-modal': 'modal',
					'data-modal-id': '#add-to-cart-needLogin'
				});
				
			} else if (${sessionScope.loginMemberInfo != null}) {
				
				let memberNo = "${sessionScope.loginMemberInfo.member_no}";
				let productCode = $(this).closest('.product-m__wishlist').find('.productCode').val();
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
	    	        },	
	    	        error : function(data) {
	    	        	console.log(data);
	    	        }
	    	    });
				
				// 1. ajax 통신 성공해야 바뀌어야 되는거 아닌가?
				// 2. 토글 클래스를 써도 되나? 좋아요 해제 -> DB 좋아요 list에서 삭제 해야하는데 음..
	    		
				
	
			}
		});
    
		
		function drawingProduct(data) {
			let output = "";
			if (data.length == 0) {
				output += `<ul class="shop-p__pagination">
							<div id="searchResult">
								결과가 없습니다.
							</div>
							</ul>
							`;
				$('.u-s-p-y-60').html(output);
			}
		
			$.each(data, function(index, item) {
				let productCode = item.product_code;
				let productMainImage = item.product_main_image;
				let productName = item.product_name;
				let productPrice = item.product_price;
				let price = productPrice.toLocaleString();
				let productCategoryNo = item.product_category_no;
				let productRecommendAge = item.recommend_age;
				let partsQuantity = item.parts_quantity;
				let isNew = item.is_new;
				let totalStarCount = item.total_star_count;
				let reviewCount = item.review_count;
				let stockQuantity = item.stock_quantity;
				let discountRate = item.discount_rate;
				let description = item.product_short_description;


			output += `<div class="col-lg-3 col-md-4 col-sm-6">
						<div class="product-m">
							<div class="product-m__thumb">
								<a class="aspect aspect--bg-grey aspect--square u-d-block"
								href="/user/product/productDetail?productCode=\${productCode}">
								<img class="aspect__img" src="\${productMainImage}" alt="">
								</a>`;
						if (${sessionScope.loginMemberInfo != null}) {
							output += `<div class="product-m__add-cart">
										<input type="hidden" class="productCode" value="\${productCode }">
										<input type="hidden" class="productImage" value="\${productMainImage }">
										<input type="hidden" class="productName" value="\${productName}">
										<input type="hidden" class="productPrice" value="\${productPrice }">
										<a class="btn--e-brand" data-modal="modal" data-modal-id="#add-to-cart">
									    <button type="button" class="addToCartProduct" style="border: none; background: none; 
									    padding: 0; cursor: pointer; color: white; width: 100%; box-sizing: border-box;">Add to Cart</button>
										</a>
									</div>`;
						} else if (${sessionScope.loginMemberInfo == null}) {
							output += `<div class="product-m__add-cart">
											<a class="btn--e-brand" data-modal="modal" data-modal-id="#add-to-cart-needLogin">
											<button type="button" style="border: none; background: none; 
										 	padding: 0; cursor: pointer; color: white; width: 100px;">Add to Cart
											</button>
											</a>
									</div>`;
						}
				output += `</div>
							<div class="product-m__content">
								<div class="product-m__category">`;
									if(productCategoryNo == 5) {
										output += `<a href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">스포츠카</a>`;							
									} else if (productCategoryNo == 7) {
										output += `<a href="/user/product/productListAll?category=racingCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">레이싱카</a>`;						
									} else if (productCategoryNo == 15) {
										output += `<a href="/user/product/productListAll?category=sedan&pageNo=1&showCount=12&sortMethod=lastOrderSort">승용차</a>`;				
									} else if (productCategoryNo == 14) {
										output += `<a href="/user/product/productListAll?category=largeCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">대형차</a>`;						
									} else if (productCategoryNo == 6) {
										output += `<a href="/user/product/productListAll?category=suv&pageNo=1&showCount=12&sortMethod=lastOrderSort">오프로드 & SUV</a>`;						
									} else if (productCategoryNo == 13) {
										output += `<a href="/user/product/productListAll?category=motorCycle&pageNo=1&showCount=12&sortMethod=lastOrderSort">오토바이</a>`;
									} else if (productCategoryNo == 8) {
										output += `<a href="/user/product/productListAll?category=ship&pageNo=1&showCount=12&sortMethod=lastOrderSort">배</a>`;
									} else if (productCategoryNo == 9) {
										output += `<a href="/user/product/productListAll?category=boat&pageNo=1&showCount=12&sortMethod=lastOrderSort">보트</a>`;
									} else if (productCategoryNo == 10) {
										output += `<a href="/user/product/productListAll?category=airplane&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기</a>`;
									} else if (productCategoryNo == 11) {
										output += `<a href="/user/product/productListAll?category=Helicopter&pageNo=1&showCount=12&sortMethod=lastOrderSort">헬리콥터</a>`;
									} else if (productCategoryNo == 12) {
										output += `<a href="/user/product/productListAll?category=spacecraft&pageNo=1&showCount=12&sortMethod=lastOrderSort">우주선</a>`;
									}
					output += `</div> 

							<div class="product-m__category">
								<span class="recommend">
									<img id="recommend-age" src="/resources/user/images/logo/recommend-age.png">\${productRecommendAge }
								</span>
								<span class="parts">
									<img id="parts-quantity" src="/resources/user/images/logo/blocks.png">\${partsQuantity}
								</span>
							</div>

							<div class="product-m__name">
								<a href="/user/product/productDetail?productCode=\${productCode}">`;
								if(isNew == 'Y') {
					output+= `<img id="newImg" src="/resources/user/images/logo/new.png">`;
							    }
					output+= `\${productName}</a>
							</div>
							<div class="product-m__rating gl-rating-style">`;
								if(totalStarCount > 0.1 && totalStarCount < 1) {
									output += `<i class="fas fa-star-half-alt"></i>
												<i class="far fa-star"></i>
												<i class="far fa-star"></i>
												<i class="far fa-star"></i>
												<i class="far fa-star"></i>`;
								} else if (totalStarCount == 1) {
									output += `<i class="fas fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount > 1 && totalStarCount < 2) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star-half-alt"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount == 2) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount > 2 && totalStarCount < 3) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star-half-alt"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount == 3) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount > 3 && totalStarCount < 4) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star-half-alt"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount == 4) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="far fa-star"></i>`;
								} else if (totalStarCount > 4 && totalStarCount < 5) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star-half-alt"></i>`;
								} else if (totalStarCount == 5) {
									output += `<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>
										<i class="fas fa-star"></i>`;
								} else {
									output += `<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>
										<i class="far fa-star"></i>`;
								}
					output += `<span class="product-m__review">(\${reviewCount})</span>
					</div>`;
					
					if(productPrice != null && stockQuantity != 0) {
						output += `<div class="product-m__price">`;
						if (discountRate == 0) {
							output += `\${price} 원`;
						} else if (discountRate != 0) {
							let discountPrice = productPrice - (productPrice * discountRate);
							let dPrice = discountPrice.toLocaleString();
							output += `\${dPrice} 원
										<span class="product-m__discount" style="text-decoration: line-through;">
											\${price} 원
										</span>`;
						}
						output += `</div>`;
					} else if (stockQuantity == 0) {
						output += `<div class="product-m__price">`;
						if (discountRate == 0) {
							output += `\${price} 원`;
						} else if (discountRate != 0) {
							let discountPrice = productPrice - (productPrice * discountRate);
							let dPrice = discountPrice.toLocaleString();
							output += `\${dPrice} 원
										<span class="product-m__discount" style="text-decoration: line-through;">
											\${price} 원
										</span>`;
						}
						output += `<br>
									<img id="soldOutImg" src="/resources/user/images/logo/soldout.png"  alt="" >
									</div>`;
					}
					
					output += `<div class="product-m__hover">
								<div class="product-m__preview-description">

									<span>\${description }</span>
								</div>
								<div class="product-m__wishlist">`;
							let isLiked = false;
					output += `<input type="hidden" class="productCode" value="\${productCode}">
						</div>
					</div>
				</div>
			</div>
		</div>`;

			});

		$('#jsonDataDisplay').html(output);
		$('.u-s-p-y-60').hide();
		

		
		};
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
	<div class="preloader is-active">
		<div class="preloader__wrap">

			<img class="preloader__img" src="/resources/admin/images/lego.gif" alt="">
		</div>
	</div>

	<!--====== Main App ======-->
	<div id="app">

		<!--====== Main Header ======-->
		<jsp:include page="../header.jsp"></jsp:include>
		<!--====== End - Main Header ======-->


		<!--====== App Content ======-->
		<div class="app-content">

			<!--====== Section 1 ======-->
			<div class="u-s-p-y-90">
				<div class="container">
					<div class="row">
						<div class="col-lg-12">
							<div class="shop-p">
							<c:choose>
								<c:when test="${param.category == 'best' }">
									<div class="categorySubject">BrickFarm Best 상품</div>
								</c:when>
								
								<c:when test="${param.category == 'all' }">
									<div class="categorySubject">BrickFarm 전체 상품</div>
								</c:when>
								
								<c:when test="${param.category == 'cars' }">
									<div class="categorySubject">BrickFarm 자동차</div>
								</c:when>
								
								<c:when test="${param.category == 'ships' }">
									<div class="categorySubject">BrickFarm 배 / 보트</div>
								</c:when>
								
								<c:when test="${param.category == 'flyingObject' }">
									<div class="categorySubject">BrickFarm 비행기 / 우주선</div>
								</c:when>
								
								<c:when test="${param.category == 'sportsCar' }">
									<div class="categorySubject">BrickFarm 스포츠카</div>
								</c:when>
								
								<c:when test="${param.category == 'racingCar' }">
									<div class="categorySubject">BrickFarm 레이싱카</div>
								</c:when>
								
								<c:when test="${param.category == 'sedan' }">
									<div class="categorySubject">BrickFarm 승용차</div>
								</c:when>
								
								<c:when test="${param.category == 'largeCar' }">
									<div class="categorySubject">BrickFarm 대형차</div>
								</c:when>
								
								<c:when test="${param.category == 'suv' }">
									<div class="categorySubject">BrickFarm 오프로드 & SUV</div>
								</c:when>
								
								<c:when test="${param.category == 'motorcycle' }">
									<div class="categorySubject">BrickFarm 오토바이</div>
								</c:when>
								
								<c:when test="${param.category == 'ship' }">
									<div class="categorySubject">BrickFarm 배</div>
								</c:when>
								
								<c:when test="${param.category == 'boat' }">
									<div class="categorySubject">BrickFarm 보트</div>
								</c:when>
								
								<c:when test="${param.category == 'airplane' }">
									<div class="categorySubject">BrickFarm 비행기</div>
								</c:when>
								
								<c:when test="${param.category == 'Helicopter' }">
									<div class="categorySubject">BrickFarm 헬리콥터</div>
								</c:when>
								
								<c:when test="${param.category == 'spacecraft' }">
									<div class="categorySubject">BrickFarm 우주선</div>
								</c:when>
								
							</c:choose>
								<div class="shop-p__toolbar u-s-m-b-30">
									<div class="shop-p__tool-style">
										<div class="tool-style__group u-s-m-b-8">

											<span class="js-shop-filter-target" data-side="#side-filter">Filters</span>

											<span class="js-shop-grid-target is-active">Grid</span> <span
												class="js-shop-list-target">List</span>
										</div>
										

										
										<c:if test="${not empty productList and param.category != 'best' }">
										<form action="/user/product/productListAll" method="get">
											<div class="tool-style__form-wrap">
												<div class="u-s-m-b-8">
													<select id="showCountSelect"
														class="select-box select-box--transparent-b-2">
														<c:forEach var="i" begin="8" end="20" step="4">
															<c:choose>
																<c:when test="${param.showCount == i}">
																	<option value="${i }" selected>${i }개씩 보기</option>
																</c:when>
																<c:otherwise>
																	<option value="${i }">${i }개씩 보기</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<input type="hidden" id="pageNo" value="${requestScope.pagingInfo.pageNo}" />
													<input type="hidden" id="totalPageCount" value="${requestScope.pagingInfo.totalPageCount}" />
												</div>
												<div class="u-s-m-b-8">
													<select class="select-box select-box--transparent-b-2"
														id="selectOfSort">
														<c:choose>
															<c:when test="${param.sortMethod == 'lastOrderSort'}">
																<option value="lastOrderSort" selected>최신상품순</option>
																<option value="salesOrderSort">판매량순</option>
																<option value="rowPriceSort">낮은가격순</option>
																<option value="highPriceSort">높은가격순</option>
															</c:when>
															<c:when test="${param.sortMethod == 'salesOrderSort'}">
																<option value="lastOrderSort">최신상품순</option>
																<option value="salesOrderSort" selected>판매량순</option>
																<option value="rowPriceSort">낮은가격순</option>
																<option value="highPriceSort">높은가격순</option>
															</c:when>
															<c:when test="${param.sortMethod == 'rowPriceSort'}">
																<option value="lastOrderSort">최신상품순</option>
																<option value="salesOrderSort">판매량순</option>
																<option value="rowPriceSort" selected>낮은가격순</option>
																<option value="highPriceSort">높은가격순</option>
															</c:when>
															<c:when test="${param.sortMethod == 'highPriceSort'}">
																<option value="lastOrderSort">최신상품순</option>
																<option value="salesOrderSort">판매량순</option>
																<option value="rowPriceSort">낮은가격순</option>
																<option value="highPriceSort" selected>높은가격순</option>
															</c:when>
															<c:otherwise>
																<option value="lastOrderSort">최신상품순</option>
																<option value="salesOrderSort">판매량순</option>
																<option value="rowPriceSort">낮은가격순</option>
																<option value="highPriceSort">높은가격순</option>
															</c:otherwise>
														</c:choose>
													</select>

												</div>
											</div>
										</form>
										</c:if>
									</div>
								</div>
								<div class="shop-p__collection">
									<div class="row is-grid-active" id="jsonDataDisplay">
										<!--  -->
										<c:forEach items="${productList}" var="product">
											<c:if test="${param.category == 'best'}">
												<img id="firstIcon" src="/resources/user/images/logo/first.png" alt="">
												<img id="secondIcon" src="/resources/user/images/logo/second.png"  alt="">
												<img id="thirdIcon" src="/resources/user/images/logo/third.png"  alt="">
											</c:if>
											<div class="col-lg-3 col-md-4 col-sm-6">
												<div class="product-m">
													<div class="product-m__thumb">
														<a class="aspect aspect--bg-grey aspect--square u-d-block"
															href="/user/product/productDetail?productCode=${product.product_code }">
															<img class="aspect__img"
															src="${product.product_main_image }" alt="">
														</a>
														<c:choose>
															<c:when test="${loginMemberInfo != null }">
																<div class="product-m__add-cart">
																	<input type="hidden" class="productCode" value="${product.product_code }">
																	<input type="hidden" class="productImage" value="${product.product_main_image }">
			            											<input type="hidden" class="productName" value="${product.product_name }">
			            											<input type="hidden" class="productPrice" value="${product.product_price }">
																	<a class="btn--e-brand" data-modal="modal" data-modal-id="#add-to-cart" style="display: inline-block; width: 100%;">
																	    <button type="button" class="addToCartProduct" style="border: none; background: none; 
																	    padding: 12px; cursor: pointer; color: white; width: 100%;">Add to Cart</button>
																	</a>
																</div>
															</c:when>
															<c:when test="${loginMemberInfo == null }">
																<div class="product-m__add-cart">
																	<a class="btn--e-brand" data-modal="modal" data-modal-id="#add-to-cart-needLogin" style="display: inline-block; width: 100%;">
																		<button type="button" style="border: none; background: none; 
																	 	padding: 12px; cursor: pointer; color: white; width: 100px;">Add to Cart
																		</button>
																	</a>
																</div>
															</c:when>
														</c:choose>
													</div>
													<div class="product-m__content">
														<div class="product-m__category">
														<c:choose>
															<c:when test="${product.product_category_no == 5}">
																<a href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">스포츠카</a>
															</c:when>
															<c:when test="${product.product_category_no == 7}">
																<a href="/user/product/productListAll?category=racingCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">레이싱카</a>
															</c:when>
															<c:when test="${product.product_category_no == 15}">
																<a href="/user/product/productListAll?category=sedan&pageNo=1&showCount=12&sortMethod=lastOrderSort">승용차</a>
															</c:when>
															<c:when test="${product.product_category_no == 14}">
																<a href="/user/product/productListAll?category=largeCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">대형차</a>
															</c:when>
															<c:when test="${product.product_category_no == 6}">
																<a href="/user/product/productListAll?category=suv&pageNo=1&showCount=12&sortMethod=lastOrderSort">오프로드 & SUV</a>
															</c:when>
															<c:when test="${product.product_category_no == 13}">
																<a href="/user/product/productListAll?category=motorCycle&pageNo=1&showCount=12&sortMethod=lastOrderSort">오토바이</a>
															</c:when>
															<c:when test="${product.product_category_no == 8}">
																<a href="/user/product/productListAll?category=ship&pageNo=1&showCount=12&sortMethod=lastOrderSort">배</a>
															</c:when>
															<c:when test="${product.product_category_no == 9}">
																<a href="/user/product/productListAll?category=boat&pageNo=1&showCount=12&sortMethod=lastOrderSort">보트</a>
															</c:when>
															<c:when test="${product.product_category_no == 10}">
																<a href="/user/product/productListAll?category=airplane&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기</a>
															</c:when>
															<c:when test="${product.product_category_no == 11}">
																<a href="/user/product/productListAll?category=Helicopter&pageNo=1&showCount=12&sortMethod=lastOrderSort">헬리콥터</a>
															</c:when>
															<c:when test="${product.product_category_no == 12}">
																<a href="/user/product/productListAll?category=spacecraft&pageNo=1&showCount=12&sortMethod=lastOrderSort">우주선</a>
															</c:when>
														</c:choose>

														</div> 

														<div class="product-m__category">
															<span class="recommend"><img id="recommend-age"
																src="/resources/user/images/logo/recommend-age.png">${product.recommend_age }</span>
															<span class="parts"><img id="parts-quantity"
																src="/resources/user/images/logo/blocks.png">${product.parts_quantity }</span>
														</div>

														<div class="product-m__name">

															<a href="/user/product/productDetail?productCode=${product.product_code }">
															<c:if test="${product.is_new == 'Y'}">
																<img id="newImg" src="/resources/user/images/logo/new.png">
															</c:if>
															${product.product_name}</a>
														</div>
														<div class="product-m__rating gl-rating-style">
															<c:choose>
																<c:when
																	test="${product.total_star_count > 0.1 and product.total_star_count < 1}">
																	<i class="fas fa-star-half-alt"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when test="${product.total_star_count == 1 }">
																	<i class="fas fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when
																	test="${product.total_star_count > 1 and product.total_star_count < 2 }">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star-half-alt"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when test="${product.total_star_count == 2 }">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when
																	test="${product.total_star_count > 2 and product.total_star_count < 3 }">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star-half-alt"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when test="${product.total_star_count == 3}">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="far fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when
																	test="${product.total_star_count > 3 and product.total_star_count < 4 }">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star-half-alt"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when test="${product.total_star_count == 4 }">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="far fa-star"></i>
																</c:when>
																<c:when
																	test="${product.total_star_count > 4 and product.total_star_count< 5 }">
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star"></i>
																	<i class="fas fa-star-half-alt"></i>
																</c:when>
																<c:when test="${product.total_star_count == 5}">
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
															<span class="product-m__review">(${product.review_count })</span>
														</div>
														<c:choose>
															<c:when test="${not empty product.product_price and product.stock_quantity != 0}">
																<div class="product-m__price">
																	<c:choose>
																		<c:when test="${product.discount_rate == 0}">
																			<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
																		</c:when>
																		
																		<c:when test="${product.discount_rate != 0}">
																			<c:set var="discountPrice" value="${product.product_price - (product.product_price * product.discount_rate)}" />
																			<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
																			<span class="product-m__discount" style="text-decoration: line-through;">
																				<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
																			</span>
																		</c:when>
																	</c:choose>
																</div>
															</c:when>
															
															<c:when test="${product.stock_quantity == 0}">
																<div class="product-m__price">
																	<c:choose>
																		<c:when test="${product.discount_rate == 0}">
																			<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
																		</c:when>
																		
																		<c:when test="${product.discount_rate != 0}">
																			<c:set var="discountPrice" value="${product.product_price - (product.product_price * product.discount_rate)}" />
																			<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
																			<span class="product-m__discount" style="text-decoration: line-through;">
																				<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
																			</span>
																		</c:when>
																	</c:choose>
																	<br>
																	<img id="soldOutImg" src="/resources/user/images/logo/soldout.png"  alt="" >
																</div>
															</c:when>
														</c:choose>
														<div class="product-m__hover">
															<div class="product-m__preview-description">

																<span>${product.product_short_description }</span>
															</div>
															<div class="product-m__wishlist">
															<c:set var="isLiked" value="false" />
																<input type="hidden" class="productCode" value="${product.product_code }">
																<c:forEach items="${likeProductList }" var="likeProduct">
																	<c:if test="${likeProduct.product_code == product.product_code }">
																		<c:set var="isLiked" value="true" />
																	</c:if>	
																</c:forEach>
																
																<c:choose>
																	<c:when test="${sessionScope.loginMemberInfo == null }">
																		<a class="heart-toggle far fa-heart" data-tooltip="tooltip" id="emptyHeart"
																		data-placement="top" title="Add to Wishlist" style="color: #FF0000;"
																		data-modal="modal" data-modal-id="#add-to-cart-needLogin">
																		</a>
																	</c:when>
																	<c:when test="${isLiked == true and sessionScope.loginMemberInfo != null }">
																		<a class="heart-toggle fas fa-heart" data-tooltip="tooltip"
																		data-placement="top" title="Add to Wishlist" style="color: #FF0000;">
																		</a>
																	</c:when>
																	<c:when test="${isLiked == false and sessionScope.loginMemberInfo != null }">
																		<a class="heart-toggle far fa-heart" data-tooltip="tooltip" id="emptyHeart"
																		data-placement="top" title="Add to Wishlist" style="color: #FF0000;">
																		</a>
																	</c:when>
																</c:choose>
															</div>
														</div>
													</div>
												</div>
											</div>

										</c:forEach>

									</div>
								</div>
								<div class="u-s-p-y-60">

									<!--====== Pagination ======-->
									<ul class="shop-p__pagination">
									<c:if test="${param.category != 'best' }">										
									<c:if test="${empty productList}">
										<div id="searchResult">
											검색 결과가 없습니다.
										</div>
									</c:if>
									
										<c:set var="prevBlockStart"
											value="${requestScope.pagingInfo.startNumOfCurrentPagingBlock - 1}" />

										<c:if test="${prevBlockStart >= 1 and param.pageNo > 1 and param.category != 'best'}">
											<li><a class="fas fa-angle-double-left"
												href="productListAll?category=${requestScope.category}&pageNo=${prevBlockStart}
												&showCount=${requestScope.pagingInfo.viewPostCountPerPage}&sortMethod=${param.sortMethod}&priceMin=${min }&priceMax=${max}"></a></li>
										</c:if>

										<!-- 
										<c:if test="${param.pageNo > 1}">
											<li><a class="fas fa-angle-left"
												href="productListAll?pageNo=${param.pageNo - 1}"></a></li>
										</c:if>
										 -->
										 
										<c:forEach var="i"
											begin="${requestScope.pagingInfo.startNumOfCurrentPagingBlock}"
											end="${requestScope.pagingInfo.endNumOfCurrentPagingBlock}"
											step="1">
											<c:choose>
												<c:when test="${param.pageNo == '' or param.pageNo == null }">
												</c:when>
												<c:when test="${param.pageNo == i}">
													<li class="is-active"><a
														href="productListAll?category=${requestScope.category}&pageNo=${i}&showCount=${requestScope.pagingInfo.viewPostCountPerPage}&sortMethod=${param.sortMethod}&priceMin=${min }&priceMax=${max}">${i}</a></li>
												</c:when>
												<c:otherwise>
													<li><a
														href="productListAll?category=${requestScope.category}&pageNo=${i}&showCount=${requestScope.pagingInfo.viewPostCountPerPage}&sortMethod=${param.sortMethod}&priceMin=${min }&priceMax=${max}">${i}</a></li>
												</c:otherwise>
											</c:choose>
										</c:forEach>

										<c:if
											test="${param.pageNo < requestScope.pagingInfo.totalPageCount or empty param.pageNo}">

											<!--
											<li><a class="fas fa-angle-right"
												href="productListAll?pageNo=${param.pageNo + 1}"></a></li>
											 -->

											<c:set var="nextBlockStart"
												value="${requestScope.pagingInfo.endNumOfCurrentPagingBlock + 1}" />

											<c:if
												test="${nextBlockStart <= requestScope.pagingInfo.totalPageCount}">
												<li><a class="fas fa-angle-double-right"
													href="productListAll?category=${requestScope.category}&pageNo=${nextBlockStart}&showCount=${requestScope.pagingInfo.viewPostCountPerPage}&sortMethod=${param.sortMethod}&priceMin=${min }&priceMax=${max}"></a></li>
											</c:if>
										</c:if>
									</c:if>
									</ul>
									<!--====== End - Pagination ======-->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--====== End - Section 1 ======-->
		</div>
		<!--====== End - App Content ======-->


		<!--====== Main Footer ======-->
		<jsp:include page="../footer.jsp"></jsp:include>
		<!--====== End - Main Footer ======-->

		<!--====== Side Filters ======-->
		<div class="shop-a" id="side-filter">
			<div class="shop-a__wrap">
				<div class="shop-a__inner gl-scroll">
					<div class="shop-w-master">
						<h1 class="shop-w-master__heading u-s-m-b-30">
							<i class="fas fa-filter u-s-m-r-8"></i> <span>FILTERS</span>
						</h1>
						<div class="shop-w-master__sidebar">
							<div class="u-s-m-b-30">
								<div class="shop-w">
									<div class="shop-w__intro-wrap">
										<h1 class="shop-w__h">CATEGORY</h1>

										<span class="fas fa-minus shop-w__toggle"
											data-target="#s-category" data-toggle="collapse"></span>
									</div>
									<div class="shop-w__wrap collapse show" id="s-category">
										<ul class="shop-w__category-list gl-scroll">
											<li class="has-list"><a
												href="/user/product/productListAll?category=all&pageNo=1&showCount=12&sortMethod=lastOrderSort">전체
													상품</a> <span class="category-list__text u-s-m-l-6"></span> <span
												class="js-shop-category-span is-expanded fas fa-plus u-s-m-l-6"></span>
												<ul style="display: block">
													<li class="has-list"><a
														href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">자동차</a>
														<span class="js-shop-category-span fas fa-plus u-s-m-l-6"></span>
														<ul>
															<li><a
																href="/user/product/productListAll?category=sportsCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">스포츠카</a></li>
															<li><a
																href="/user/product/productListAll?category=racingCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">레이싱카</a></li>
															<li><a
																href="/user/product/productListAll?category=sedan&pageNo=1&showCount=12&sortMethod=lastOrderSort">승용차</a></li>
															<li><a
																href="/user/product/productListAll?category=largeCar&pageNo=1&showCount=12&sortMethod=lastOrderSort">대형차</a></li>
															<li><a
																href="/user/product/productListAll?category=suv&pageNo=1&showCount=12&sortMethod=lastOrderSort">오프로드
																	& SUV</a></li>
															<li><a
																href="/user/product/productListAll?category=motorcycle&pageNo=1&showCount=12&sortMethod=lastOrderSort">오토바이</a></li>
														</ul></li>
													<li class="has-list"><a href="/user/product/productListAll?category=ships&pageNo=1&showCount=12&sortMethod=lastOrderSort">배 / 보트</a> <span
														class="js-shop-category-span fas fa-plus u-s-m-l-6"></span>
														<ul>
															<li><a href="/user/product/productListAll?category=ship&pageNo=1&showCount=12&sortMethod=lastOrderSort">배</a></li>
															<li><a href="/user/product/productListAll?category=boat&pageNo=1&showCount=12&sortMethod=lastOrderSort">보트</a></li>
														</ul></li>
													<li class="has-list"><a
														href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기
															/ 우주선</a> <span
														class="js-shop-category-span fas fa-plus u-s-m-l-6"></span>
														<ul>
															<li><a
																href="/user/product/productListAll?category=airplane&pageNo=1&showCount=12&sortMethod=lastOrderSort">비행기</a></li>
															<li><a
																href="/user/product/productListAll?category=Helicopter&pageNo=1&showCount=12&sortMethod=lastOrderSort">헬리콥터</a></li>
															<li><a
																href="/user/product/productListAll?category=spacecraft&pageNo=1&showCount=12&sortMethod=lastOrderSort">우주선</a></li>
														</ul></li>
												</ul></li>
										</ul>
									</div>
								</div>
							</div>
							
							<div class="u-s-m-b-30">
								<div class="shop-w">
									<div class="shop-w__intro-wrap">
										<h1 class="shop-w__h">PRICE</h1>

										<span class="fas fa-minus shop-w__toggle"
											data-target="#s-price" data-toggle="collapse"></span>
									</div>
									<div class="shop-w__wrap collapse show" id="s-price">
										<form class="shop-w__form-p" action="/user/product/productListAll" method="get">
											<div class="shop-w__form-p-wrap">
												<input type="hidden" name="category" value="all">
            									<input type="hidden" name="pageNo" value="1">
            									<input type="hidden" name="showCount" value="12">
            									<input type="hidden" name="sortMethod" value="lastOrderSort">
												<div>

													<label for="price-min"></label> <input
														class="input-text input-text--primary-style" type="text"
														id="price-min" placeholder="Min" name="priceMin">
												</div>
												<div>

													<label for="price-max"></label> <input
														class="input-text input-text--primary-style" type="text"
														id="price-max" placeholder="Max" name="priceMax">
												</div>
												<div>
													<button
														class="btn btn--icon fas fa-angle-right btn--e-transparent-platinum-b-2"
														type="submit"></button>
												</div>
											</div>	
										</form>
									</div>
								</div>
							</div>
							<!-- 
							<div class="u-s-m-b-30">
								<div class="shop-w">
									<div class="shop-w__intro-wrap">
										<h1 class="shop-w__h">SIZE</h1>

										<span class="fas fa-minus collapsed shop-w__toggle"
											data-target="#s-size" data-toggle="collapse"></span>
									</div>
									<div class="shop-w__wrap collapse" id="s-size">
										<ul class="shop-w__list gl-scroll">
											<li>
												
												<div class="check-box">

													<input type="checkbox" id="xs">
													<div class="check-box__state check-box__state--primary">

														<label class="check-box__label" for="xs">XS</label>
													</div>
												</div> 
												
												<span class="shop-w__total-text">(2)</span>
											</li>
											<li>
												
												<div class="check-box">

													<input type="checkbox" id="small">
													<div class="check-box__state check-box__state--primary">

														<label class="check-box__label" for="small">Small</label>
													</div>
												</div>
												<span
												class="shop-w__total-text">(4)</span>
											</li>
											<li>
												
												<div class="check-box">

													<input type="checkbox" id="medium">
													<div class="check-box__state check-box__state--primary">

														<label class="check-box__label" for="medium">Medium</label>
													</div>
												</div> 
												<span
												class="shop-w__total-text">(6)</span>
											</li>
											<li>
												
												<div class="check-box">

													<input type="checkbox" id="large">
													<div class="check-box__state check-box__state--primary">

														<label class="check-box__label" for="large">Large</label>
													</div>
												</div>
												<span
												class="shop-w__total-text">(8)</span>
											</li>
											<li>
												<div class="check-box">

													<input type="checkbox" id="xl">
													<div class="check-box__state check-box__state--primary">

														<label class="check-box__label" for="xl">XL</label>
													</div>
												</div>
												 <span
												class="shop-w__total-text">(10)</span>
											</li>
											<li>
												
												<div class="check-box">

													<input type="checkbox" id="xxl">
													<div class="check-box__state check-box__state--primary">

														<label class="check-box__label" for="xxl">XXL</label>
													</div>
												</div>
												<span class="shop-w__total-text">(12)</span>
											</li>
										</ul>
									</div>
								</div>
							</div>
							-->
							<div class="u-s-m-b-30">
								<div class="shop-w">
									<div class="shop-w__intro-wrap">
										<h1 class="shop-w__h">RATING</h1>

										<span class="fas fa-minus shop-w__toggle"
											data-target="#s-rating" data-toggle="collapse"></span>
									</div>
									<div class="shop-w__wrap collapse show" id="s-rating">
										<ul class="shop-w__list gl-scroll">
											<li>
												<div class="rating__check">
													<input type="checkbox" class="star-checkbox" value='5'>
													<div class="rating__check-star-wrap">
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
													</div>
												</div> 
												<span class="shop-w__total-text"></span>
											</li>
											<li>
												<div class="rating__check">
													<input type="checkbox" class="star-checkbox" value='4'>
													<div class="rating__check-star-wrap">
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="far fa-star"></i>
														<span>& UP</span>
													</div>
												</div> 
												<span class="shop-w__total-text"></span>
											</li>
											<li>
												<div class="rating__check">
													<input type="checkbox" class="star-checkbox" value='3'>
													<div class="rating__check-star-wrap">
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="far fa-star"></i>
														<i class="far fa-star"></i>
														<span>& UP</span>
													</div>
												</div> 
												<span class="shop-w__total-text"></span>
											</li>
											<li>
												<div class="rating__check">
													<input type="checkbox" class="star-checkbox" value='2'>
													<div class="rating__check-star-wrap">
														<i class="fas fa-star"></i>
														<i class="fas fa-star"></i>
														<i class="far fa-star"></i>
														<i class="far fa-star"></i>
														<i class="far fa-star"></i>
														<span>& UP</span>
													</div>
												</div> 
												<span class="shop-w__total-text"></span>
											</li>
											<li>
												<div class="rating__check">
													<input type="checkbox" class="star-checkbox" value='1'>
													<div class="rating__check-star-wrap">
														<i class="fas fa-star"></i>
														<i class="far fa-star"></i>
														<i class="far fa-star"></i>
														<i class="far fa-star"></i>
														<i class="far fa-star"></i> 
														<span>& UP</span>
													</div>
												</div> 
												<span class="shop-w__total-text"></span>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--====== End - Side Filters ======-->


		<!--====== Modal Section ======-->


		<!--====== Add to Cart Modal ======-->
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
				
				<div class="modal fade" id="add-to-cart">
				</div>

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
</body>
</html>