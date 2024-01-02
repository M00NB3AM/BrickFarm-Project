<%@page import="com.brickfarm.vo.user.ysh.UserMemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.servlet.http.HttpSession" %>
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
</head>
<body class="config">
    <div class="preloader is-active">
        <div class="preloader__wrap">
        	<img class="preloader__img" src="/resources/admin/images/lego.gif" alt="">
       	</div>
    </div>

    <!--====== Main Header ======-->
    <jsp:include page="./header.jsp"></jsp:include>
    <!--====== End - Main Header ======-->
    
    <!--====== Main App ======-->
    <div id="app">

        <!--====== App Content ======-->
        <div class="app-content">

            <!--====== Primary Slider ======-->
            <div class="s-skeleton s-skeleton--h-600 s-skeleton--bg-grey">
                <div class="owl-carousel primary-style-1" id="hero-slider">
                    <div class="hero-slide hero-slide--1">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <div class="slider-content slider-content--animation">

                                        <span class="content-span-1 u-c-white">자랑스럽게 보여주고 싶은 전시용 모델</span>

                                        <span class="content-span-2 u-c-white">차원이 다른 레벨의 브릭팜 자동차</span>

                                        <span class="content-span-3 u-c-white">페라리, 애스턴 마틴, 지프®, 맥라렌 등 유명 브랜드의 자동차들이 들어 있고 코딩까지 지원하는 STEM 장난감을 아시나요? <br>
                                        그뿐만이 아니에요. 사실적인 엔진부터 정교한 디테일까지, 전시용 모델로도 두말할 필요 없다니까요.</span>

                                        <span class="content-span-4 u-c-white">너도나도 이걸 보려고 고개를 핸들 돌리듯이 돌리는 게 보이나요?

                                            <span class="u-c-brand"></span></span>

                                        <a class="shop-now-link btn--e-brand" href="/user/product/productListAll?category=cars&pageNo=1&showCount=12&sortMethod=lastOrderSort">SHOP NOW</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hero-slide hero-slide--2">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <div class="slider-content slider-content--animation">

                                        <span class="content-span-1 u-c-black">물에서도 가지고 놀 수 있는 방수 모델</span>

                                        <span class="content-span-2 u-c-black">브릭팜의 수상 탑승기계 장난감</span>

                                        <span class="content-span-3 u-c-black">요트, 쌍동선, 선박, 노란 잠수함, 그냥 잠수함… 심지어 못갈 데가 없는 수륙 양용 차량까지, 없는 게 없답니다. <br>
                                        드넓은 바다를 좋아하세요?</span>

                                        <span class="content-span-4 u-c-black">보트가 물에 뜨는 순간 상상력도 둥실둥실 떠오르겠죠!

                                            <span class="u-c-brand"></span></span>

                                        <a class="shop-now-link btn--e-brand" href="/user/product/productListAll?category=ships&pageNo=1&showCount=12&sortMethod=lastOrderSort">SHOP NOW</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hero-slide hero-slide--3">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <div class="slider-content slider-content--animation">

                                        <span class="content-span-1 u-c-white">완전히 새로운 방식의 비행 놀이</span>

                                        <span class="content-span-2 u-c-white">브릭팜 우주 장난감과 비행기 세트</span>

                                        <span class="content-span-3 u-c-white">가장 깊고 어두운 우주 공간에 뭐가 있을지 누가 알겠어요? <br>
                                        	둥둥 떠다니는 암소? 보라색 하늘? 노란색 우주비행사? 알아보는 방법은 하나 뿐이겠죠!
                                        </span>

                                        <span class="content-span-4 u-c-white">누구나 브릭팜 우주 비행사가 될 수 있어요! 

                                            <span class="u-c-brand"></span></span>

                                        <a class="shop-now-link btn--e-brand" href="/user/product/productListAll?category=flyingObject&pageNo=1&showCount=12&sortMethod=lastOrderSort">SHOP NOW</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--====== End - Primary Slider ======-->

            <!--====== Section 2 ======-->
            <div class="u-s-p-b-60">

                <!--====== Section Intro ======-->
                <div class="section__intro u-s-m-b-16">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section__text-wrap">
                                    <h1 class="section__heading u-c-secondary u-s-m-b-12">BrickFarm Best 상품</h1>

                                    <span class="section__span u-c-silver">카테고리</span>
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
                            <div class="col-lg-12">
                                <div class="filter-category-container">
                                    <div class="filter__category-wrapper">

                                        <button class="btn filter__btn filter__btn--style-1 js-checked" type="button" data-filter="*">전체 상품</button></div>
                                    <div class="filter__category-wrapper">

                                        <button class="btn filter__btn filter__btn--style-1" type="button" data-filter=".cars">자동차</button></div>
                                    <div class="filter__category-wrapper">

                                        <button class="btn filter__btn filter__btn--style-1" type="button" data-filter=".ships">배/보트</button></div>
                                    <div class="filter__category-wrapper">

                                        <button class="btn filter__btn filter__btn--style-1" type="button" data-filter=".flyingObject">비행기/우주선</button></div>

                                </div>
                                <div class="filter__grid-wrapper u-s-m-t-30">
                                    <div class="row">
										<c:forEach items="${bestProductList }" var="product">
											<c:choose>
												<c:when
													test="${product.product_category_no == 5 or product.product_category_no == 6 
												or product.product_category_no == 7 or product.product_category_no == 13
												or product.product_category_no == 14 or product.product_category_no == 15 }">
													<div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 u-s-m-b-30 filter__item cars">
														<div class="product-o product-o--hover-on product-o--radius">
															<a class="aspect aspect--bg-grey aspect--square u-d-block"
															href="/user/product/productDetail?productCode=${product.product_code }">
																<img class="aspect__img" src="${product.product_main_image }" alt="">
															</a>
															<span class="product-o__category"> 
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
															</span>
															<span class="product-o__name"> 
																<a href="/user/product/productDetail?productCode=${product.product_code }">${product.product_name }</a>
															</span>
															<div class="product-o__rating gl-rating-style">
																<c:choose>
					                                    			<c:when test="${product.total_star_count > 0.1 and product.total_star_count < 1}">
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
							                                    	<c:when test="${product.total_star_count > 1 and product.total_star_count < 2 }">
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
							                                    	<c:when test="${product.total_star_count > 2 and product.total_star_count < 3 }">
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
							                                    	<c:when test="${product.total_star_count > 3 and product.total_star_count < 4 }">
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
							                                    	<c:when test="${product.total_star_count > 4 and product.total_star_count< 5 }">
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
																<span class="product-o__review">(${product.review_count })</span>
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
														</div>
													</div>
												</c:when>
												
												<c:when
													test="${product.product_category_no == 8 or product.product_category_no == 9 }">
													<div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 u-s-m-b-30 filter__item ships">
														<div class="product-o product-o--hover-on product-o--radius">
															<a class="aspect aspect--bg-grey aspect--square u-d-block"
															href="/user/product/productDetail?productCode=${product.product_code}"> 
																<img class="aspect__img" src="${product.product_main_image }" alt="">
															</a>
															<span class="product-o__category"> 
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
															</span>
															<span class="product-o__name">
																<a href="/user/product/productDetail?productCode=${product.product_code }">${product.product_name }</a>
															</span>
															<div class="product-o__rating gl-rating-style">
																<c:choose>
					                                    			<c:when test="${product.total_star_count > 0.1 and product.total_star_count < 1}">
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
							                                    	<c:when test="${product.total_star_count > 1 and product.total_star_count < 2 }">
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
							                                    	<c:when test="${product.total_star_count > 2 and product.total_star_count < 3 }">
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
							                                    	<c:when test="${product.total_star_count > 3 and product.total_star_count < 4 }">
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
							                                    	<c:when test="${product.total_star_count > 4 and product.total_star_count< 5 }">
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
																<span class="product-o__review">(${product.review_count })</span>
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
														</div>
													</div>
												</c:when>
												
												<c:when
													test="${product.product_category_no == 11 or product.product_category_no == 12 
													or product.product_category_no == 13 }">
													<div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 u-s-m-b-30 filter__item flyingObject">
														<div class="product-o product-o--hover-on product-o--radius">
															<a class="aspect aspect--bg-grey aspect--square u-d-block"
															href="/user/product/productDetail?productCode=${product.product_code}">
																<img class="aspect__img" src="${product.product_main_image }" alt="">
															</a>
															<span class="product-o__category">
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
															</span>
															<span class="product-o__name">
																<a href="/user/product/productDetail?productCode=${product.product_code }">${product.product_name }</a>
															</span>
															<div class="product-o__rating gl-rating-style">
																<c:choose>
					                                    			<c:when test="${product.total_star_count > 0.1 and product.total_star_count < 1}">
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
							                                    	<c:when test="${product.total_star_count > 1 and product.total_star_count < 2 }">
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
							                                    	<c:when test="${product.total_star_count > 2 and product.total_star_count < 3 }">
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
							                                    	<c:when test="${product.total_star_count > 3 and product.total_star_count < 4 }">
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
							                                    	<c:when test="${product.total_star_count > 4 and product.total_star_count< 5 }">
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
																<span class="product-o__review">(${product.review_count })</span>
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
														</div>
													</div>
												</c:when>
											</c:choose>
										</c:forEach>
                                    </div>
                                </div>
                            </div>
							<div class="col-lg-12">
								<div class="load-more">
									<a class="shop-now-link btn--e-brand"
										href="/user/product/productListAll?category=best&pageNo=1">Load
										More</a>
								</div>
							</div>
						</div>
                    </div>
                </div>
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 2 ======-->


            <!--====== Section 3 ======-->
            <div class="u-s-p-b-60">

                <!--====== Section Intro ======-->
                <div class="section__intro u-s-m-b-46">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section__text-wrap">
                                    <h1 class="section__heading u-c-secondary u-s-m-b-12">이번 달의 특별 할인!</h1>

                                    <span class="section__span u-c-silver">이번 달의 특별 할인 상품들<br>
                                    서두르세요. 특별 할인 상품들은 곧 매진됩니다.
                                    </span>

                                    <span class="section__span u-c-silver">특별 할인 상품들을 장바구니에 추가해보세요.</span>
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
                        	<c:forEach items="${discountProductList }" var="product">
	                            <div class="col-lg-6 col-md-6 u-s-m-b-30">
	                                <div class="product-o product-o--radius product-o--hover-off u-h-100">
	
	                                        <a class="aspect aspect--bg-grey aspect--square u-d-block" href="/user/product/productDetail?productCode=${product.product_code}">
	
	                                            <img class="aspect__img" src="${product.product_main_image }" alt=""></a>
	                                            <div class="countdown countdown--style-special" data-countdown="${product.event_end }"></div>
	
	
	                                    <span class="product-o__category">
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
	                                    </span>
	
	                                    <span class="product-o__name">
	
	                                        <a href="/user/product/productDetail?productCode=${product.product_code}">${product.product_name }</a></span>
	                                    <div class="product-o__rating gl-rating-style">
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
											<span class="product-o__review">(${product.review_count })</span>
										</div>
	
	                                    <c:choose>
											<c:when test="${not empty product.product_price and product.stock_quantity != 0}">
												<div class="product-o__price">
													<c:choose>
														<c:when test="${product.discount_rate == 0}">
															<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
														</c:when>
																			
														<c:when test="${product.discount_rate != 0}">
															<c:set var="discountPrice" value="${product.product_price - (product.product_price * product.discount_rate)}" />
															<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
															<span class="product-o__discount" style="text-decoration: line-through;">
																<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
															</span>
														</c:when>
													</c:choose>
												</div>
											</c:when>
																
											<c:when test="${product.stock_quantity == 0}">
												<div class="product-o__price">
													<c:choose>
														<c:when test="${product.discount_rate == 0}">
															<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
														</c:when>
																			
														<c:when test="${product.discount_rate != 0}">
															<c:set var="discountPrice" value="${product.product_price - (product.product_price * product.discount_rate)}" />
															<fmt:formatNumber type="number" value="${discountPrice}" pattern="###,###" /> 원
															<span class="product-o__discount" style="text-decoration: line-through;">
																<fmt:formatNumber type="number" value="${product.product_price}" pattern="###,###" /> 원
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
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 3 ======-->


            <!--====== Section 4 ======-->
            <div class="u-s-p-b-60">

                <!--====== Section Intro ======-->
                <div class="section__intro u-s-m-b-46">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section__text-wrap">

                                    <h1 class="section__heading u-c-secondary u-s-m-b-12">BrickFarm 신제품</h1>
                                    <span class="section__span u-c-silver">BrickFarm에 새로운 상품을 만나보세요.</span>
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
                                <c:forEach items="${newProductList}" var="product">
                                <div class="u-s-m-b-30">
                                    <div class="product-o product-o--hover-on">
                                        <a class="aspect aspect--bg-grey aspect--square u-d-block" href="/user/product/productDetail?productCode=${product.product_code}">
                                        	<img class="aspect__img" src="${product.product_main_image }" alt="">
                                        </a>
										<span class="product-o__category">
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
											</span>
											<div class="product-o__name">
                                            	<a href="/user/product/productDetail?productCode=${product.product_code }">${product.product_name}</a>
                                            </div>
                                        	<div class="product-o__rating gl-rating-style">
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
                                        		<span class="product-o__review">(${product.review_count })</span>
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
									</div>
                                </div>
                               	</c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Content ======-->
            </div>
                
            <!--====== Section 9 ======-->
            <div class="u-s-p-b-60">

                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-4 col-md-6 u-s-m-b-30">
                                <div class="service u-h-100">
                                    <div class="service__icon"><i class="fas fa-truck"></i></div>
                                    <div class="service__info-wrap">

                                        <span class="service__info-text-1">5만원 이상 구매 시 무료 배송</span>

                                        <span class="service__info-text-2">BrickFarm 상품을 5만원 이상 구매 시<br>
                                        대한민국 전 지역 무료로 배송합니다.</span></div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-6 u-s-m-b-30">
                                <div class="service u-h-100">
                                    <div class="service__icon"><i class="fas fa-redo"></i></div>
                                    <div class="service__info-wrap">

                                        <span class="service__info-text-1">안전한 쇼핑</span>

                                        <span class="service__info-text-2">BrickFarm은 고객님의 구매부터 배송까지 모두 책임집니다.</span></div>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-6 u-s-m-b-30">
                                <div class="service u-h-100">
                                    <div class="service__icon"><i class="fas fa-headphones-alt"></i></div>
                                    <div class="service__info-wrap">

                                        <span class="service__info-text-1">친절하고 빠른 고객 상담</span>

                                        <span class="service__info-text-2">BrickFarm은 고객님의 문의 및 문제 사항을 친절하고 빠르게 해결합니다.</span></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 9 ======-->
            
        <!--====== End - App Content ======-->

        <!--====== Modal Section ======-->

        <!--====== Add to Cart Modal ======-->
        <div class="modal fade" id="add-to-cart">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content modal-radius modal-shadow">

                    <button class="btn dismiss-button fas fa-times" type="button" data-dismiss="modal"></button>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-6 col-md-12">
                                <div class="success u-s-m-b-30">
                                    <div class="success__text-wrap"><i class="fas fa-check"></i>

                                        <span>Item is added successfully!</span></div>
                                    <div class="success__img-wrap">

                                        <img class="u-img-fluid" src="resources/user/images/product/electronic/product1.jpg" alt=""></div>
                                    <div class="success__info-wrap">

                                        <span class="success__name">Beats Bomb Wireless Headphone</span>

                                        <span class="success__quantity">Quantity: 1</span>

                                        <span class="success__price">$170.00</span></div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-12">
                                <div class="s-option">

                                    <span class="s-option__text">1 item (s) in your cart</span>
                                    <div class="s-option__link-box">

                                        <a class="s-option__link btn--e-white-brand-shadow" data-dismiss="modal">CONTINUE SHOPPING</a>

                                        <a class="s-option__link btn--e-white-brand-shadow" href="cart.html">VIEW CART</a>

                                        <a class="s-option__link btn--e-brand-shadow" href="checkout.html">PROCEED TO CHECKOUT</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--====== End - Main App ======-->
	</div>


    <!--====== Vendor Js ======-->
    <script src="resources/user/js/vendor.js"></script>

    <!--====== jQuery Shopnav plugin ======-->
    <script src="resources/user/js/jquery.shopnav.js"></script>

    <!--====== App ======-->
    <script src="resources/user/js/app.js"></script>

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
    
    <!--====== Main Footer ======-->
    <jsp:include page="./footer.jsp"></jsp:include>
    <!--====== End - Main Footer ======-->
</body>
</html>