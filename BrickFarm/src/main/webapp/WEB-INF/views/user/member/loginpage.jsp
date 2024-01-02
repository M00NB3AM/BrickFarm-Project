<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<link href="/resources/user/css/ysh/css/loginregistermodel.css" rel="stylesheet" />
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
<title>BrickFarm</title>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css" />
<script src="/resources/user/js/ysh/js/login.js" type="text/javascript"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
<style>
.naverbtn{
	width: 160px;
	height: 37px;
}
.googlebtn{
	width: 160px;
	height: 37px;
}
.kakaobtn{
	width: 160px;
	height: 37px;
}
.snsspan{
	text-align: center;
    font-weight: 900;
}
</style>
</head>
<body class="loginbody">
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

                                        <a href="/">홈</a></li>
                                    <li class="is-marked">

                                        <a href="./loginpage">로그인</a></li>
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
                                    <h1 class="section__heading u-c-secondary">ALREADY REGISTERED?</h1>
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
                                        <h1 class="gl-h1">I'M NEW CUSTOMER</h1>

                                        
                                        <div class="u-s-m-b-15">

                                            <a class="l-f-o__create-link btn--e-transparent-brand-b-2" href="/user/member/registermember">회원가입하기</a></div>
                                        <h1 class="gl-h1">로그인</h1>

                                        <form class="l-f-o__form" method="post" action="/user/member/loginMember" name="login-form">
                                            
                                               <div class="gl-inline">
                                                <div class="u-s-m-b-15">
                                           			<a id="google_login" class="circle google" href="${ google_url }"><img class="googlebtn" alt="googlelogin" src="/resources/user/images/logo/googlelogin.png"></a>
													<a id="naver_login" class="circle naver" href="${ naver_url }"><img class="naverbtn" alt="naverlogin" src="/resources/user/images/logo/naverlogin.png"></a>
													<a id="kakao_login" class="circle kakao" href="${ kakao_url }"><img class="kakaobtn" alt="kakaologin" src="/resources/user/images/logo/kakao_login_medium_narrow.png"></a>													
                                           		</div>
                                           	  </div>

                                           	 	<div class="u-s-m-b-30">
                                                <label class="gl-label" for="login-email">아이디 *</label>

                                                <input class="input-text input-text--primary-style" type="text" id="login_userId" placeholder="Enter ID" name="member_id"></div>
                                            <div class="u-s-m-b-30">

                                                <label class="gl-label" for="login-password">비밀번호 *</label>

                                                <input class="input-text input-text--primary-style" type="password" name="password" id="login_password" placeholder="Enter Password"></div>
                                            <div class="gl-inline">
                                                <div class="u-s-m-b-30">

                                                    <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="loginMember();">LOGIN</button></div>
                                                <div class="u-s-m-b-30">

                                                    <a class="gl-link" href="finduserid">아이디 / 비밀번호 찾기</a></div>
                                            </div>
                                            <a class="gl-link" href="/">Return to Store</a>
                                         <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}" />
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
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>