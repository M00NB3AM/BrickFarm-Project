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
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<link href="/resources/images/favicon.png" rel="shortcut icon">
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
<title>BrickFarm</title>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css" />
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/resources/user/js/ysh/js/deletemember.js" type="text/javascript"></script>

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
                                    <h1 class="section__heading u-c-secondary">정말로 탈퇴하시겠습니까?</h1>
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
                                        <h1 class="gl-h1" style="text-align: center;">회원탈퇴</h1>
                                        <form class="l-f-o__form" method="post" action="/user/member/deletemember" name="delete-form">
                                            <div class="u-s-m-b-30">
                                                <label class="gl-label" for="login-password">비밀번호 *</label>
                                                <input class="input-text input-text--primary-style" type="password" name="password" id="delete_password" placeholder="Enter Password"></div>
                                                <label class="gl-label" for="login-password">탈퇴사유 *</label>
                                                <select class="input-text input-text--primary-style" name="selectreason" id="selectreason">
													<option selected="selected">탈퇴사유</option>
													<option value="1">상품종류가 부족하다</option>
													<option value="2">상품가격이 비싸다</option>
													<option value="3">상품가격에 비해 품질이 떨어진다</option>
													<option value="4">배송이 느리다</option>
													<option value="5">반품 / 교환이 불만이다</option>
													<option value="6">상담원 고객응대 서비스가 불만이다</option>
													<option value="7">쇼핑몰 혜택이 부족하다(쿠폰, 적립금, 할인 등)</option>
													<option value="8">이용빈도가 낮다</option>
													<option value="9">개인정보 유출이 염려된다.</option>
													<option value="10">기타(직접입력)</option>
												</select>
											<div><input class="input-text input-text--primary-style" type="text" name="deletereason" id="deletereason" placeholder="사유를 작성해주세요." maxlength=30></div>
										<div><h5 style="color: red; text-align: center;">탈퇴를 하게 되면 보유하신 포인트와 쿠폰은 모두 삭제됩니다.</h5></div>
                                            <div class="gl-inline">
                                                <div class="u-s-m-b-30" style="margin-left: 39%;">
                                                    <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="deleteMember();">탈퇴하기</button></div>
                                               </div>
                                         <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}" />
                                         <input type="hidden" id="member_no" name="member_no" value="${loginMemberInfo.member_no}">
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