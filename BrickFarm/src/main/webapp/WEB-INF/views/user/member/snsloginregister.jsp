<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<link href="/resources/user/css/ysh/css/modifypwd.css" rel="stylesheet" />
<script src="/resources/user/js/ysh/js/snsloginregister.js" type="text/javascript"></script>
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<!-- <script src="/resources/user/js/ysh/js/register-membercheck.js" type="text/javascript"></script> -->
<!--====== Google Font ======-->
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet">
<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">
<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">
<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript" src="/resources/user/js/kakaojuso.js"></script>


<title>BrickFarm</title>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
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

                                        <a href="signup.html">Signup</a></li>
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
                                    <h1 class="section__heading u-c-secondary">사이트 이용을 위한 추가사항</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Intro ======-->


                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="container">
                        <div class="row row--center">
                            <div class="col-lg-6 col-md-8 u-s-m-b-30">
                                <div class="l-f-o">
                                    <div class="l-f-o__pad-box">
                                        <h1 class="gl-h1">소셜로그인 추가사항</h1>
                                        <form class="l-f-o__form" name="snsloginregister" method="post" action="/user/member/addinfosnsloginregister">
                                            <div class="gl-inline">
                                            <div class="u-s-m-b-30">
                                                <label class="gl-label" for="member_id">아이디 *</label>
                                                <input class="input-text input-text--primary-style" type="text" id="member_id" placeholder="아이디 : 4 ~ 10자" name="member_id">
                                            </div>
                                                <button class="btn btn--e-transparent-brand-b-2" type="button" id="userId_confirm" onclick="snsLoginCheckMemberid();">중복확인</button>
                                            </div>
                                            <div class="gl-inline">
	                                            <div class="u-s-m-b-30">
	                                                <label class="gl-label">본인인증 *</label>
                                                <input class="importbtn btn btn--e-transparent-brand-b-2" type="button" id="checkPhoneNumber" onclick="snsloginaddphone();" value="본인인증하기"> 
                                                <div class="gl-inline">
	                                                <input class="input-text input-text--primary-style" type="hidden" id="member_name" placeholder="이름" name="member_name" readonly>
	                                                <input class="input-text input-text--primary-style" type="hidden" id="phone_number" placeholder="전화번호" name="phone_number" readonly>
                                                </div>
	                                             </div>
                                             </div>
                                            <div class="gl-inline">
                                                <div class="u-s-m-b-30">
												 <label class="gl-label" for="birth_date">생년월일</label>
                                                   <input class="input-text input-text--primary-style" type="text" id="birth_date" placeholder="생년월일 6자리" name="birth_date" maxlength="6" readonly/>
                                            	</div>
                                                	<div class="u-s-m-b-30">
                                                    	<label class="gl-label" for="gender">성별 *</label>
                                                    	<select class="input-text input-text--primary-style" id="gender" name="gender">
                                                        <option selected>Select</option>
                                                        <option value="M">Male</option>
                                                        <option value="F">Female</option>
                                                    </select>
                                                </div>
                                            </div>
                                         <div class="gl-inline">
                                         	<div class="u-s-m-b-30">
                                                <label class="gl-label" for="address">주소 *</label>
                                                <input class="input-text input-text--primary-style zipcode" type="text" id="zip_code" placeholder="우편번호" name="zip_code" readonly>
                                                <input class="input-text input-text--primary-style" type="text" id="address" placeholder="주소" name="address" readonly>
                                                <input class="input-text input-text--primary-style" type="text" id="detailaddress" placeholder="주소" name="detailaddress">
                                            </div>
                                                <button class="btn btn--e-transparent-brand-b-2" type="button" id="userId_confirm" onclick="findkakaoaddress();">주소검색</button>
                                          </div>
                                            <div class="u-s-m-b-15">
                                                <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="addsnsInfo()">저장하기</button>
                                            </div>

                                            <a class="gl-link" href="/">Return to Store</a>
                                            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}" />
                                            <input type="hidden" id="member_no" name="member_no" value="${member_no}" />
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 2 ======-->
        </div>
    <!--====== Vendor Js ======-->
    <!-- <script src="/resources/user/js/vendor.js"></script> -->

    <!--====== jQuery Shopnav plugin ======-->
    <script src="/resources/user/js/jquery.shopnav.js"></script>

    <!--====== App ======-->
    <script src="/resources/user/js/app.js"></script>

	<jsp:include page="../footer.jsp"></jsp:include>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>
</html>