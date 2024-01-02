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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
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
.emailbtn{
	width: 265px;
    margin: -55px;
    padding: 0px;
    background: none;
    color: darkorange;
    border: none;
    height: 55px;
    --bs-btn-hover-bg: none;
    --bs-btn-hover-color: pink;
    --bs-btn-hover-border-color: none;
    --bs-link-hover-color: none;
    --bs-btn-active-border-color: none;
    --bs-btn-active-bg: none;
}
.emailmodalbtn{
	border: 3px solid orange;
    background: white;
    border-radius: 10px;
    padding: 5px;
    margin: 15px;
    font-weight: bold;
}
.emailbtn{
	width: 165px;
    margin-left: 0px;
}
.emailinput{
    border: 3px solid pink;
    border-radius: 10px;
    padding: 6px;
    text-align: center;
}
.modalclose{
	border-radius: 30px;
    border: 3px solid green;
    background: yellow;
    font-size: 15px;
    font-weight: bold;
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
                                        <h1 class="gl-h1" style="text-align: center;">소셜가입자 회원탈퇴</h1>
                                        <form class="l-f-o__form" method="post" action="/user/member/deletemember" name="delete-form">
                                            <div class="u-s-m-b-30">
                                                <label class="gl-label" for="login-password">이메일 *</label>
                                            	<input class="input-text input-text--primary-style" type="text" id="email" placeholder="이메일" name="email" readonly/>
                                            </div>
                                                <a class="gl-link"><button type="button" class="btn btn--e-transparent-brand-b-2 emailbtn" data-bs-toggle="modal" data-bs-target="#myModal">이메일 인증</button></a>
                                                <label class="gl-label" style="margin-top: 30px;" for="login-password">탈퇴사유 *</label>
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
                                                    <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="snsdeleteMember();">탈퇴하기</button></div>
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
<form id="modalform" name="emailModel">
    <div class="modal" id="myModal">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title modaltitle">이메일 인증</h4>
	        <button type="button" class="btn-close modalclose" data-bs-dismiss="modal">X</button>
	        
	      </div>
	
	      <!-- 이메일인증 전 Modal body -->
	      <div class="modal-body" id="modalbody1">
			<div name="modalspan1"><span id="modalspan1" class="modalspan" style="color: blue">인증코드를 받을 이메일 입력해주세요</span></div>
			<div><span id="modalspan2" class="modalspan" style="color: blue">인증번호를 발송했습니다.</span></div>
			<div id="beforediv">
			<input id="modalemail" name="modalemail" type="email" class="emailinput">
			<button id="emailbtn" name="emailbtn" class="emailmodalbtn" type="button" onclick="emailconfirm();">인증번호받기</button>
			</div>
			<div id="afterdiv">
				<input type="text" id="emailnum" class="emailinput" name="emailnum">
				<button id="confirmbtn" type="button" onclick="confirm()" class="emailmodalbtn">인증하기</button>
			
			</div>
	      </div>
	      <!-- Modal footer -->
	      <div class="modal-footer">
	      <div class="footerdiv" id="footmsg1" style="color: red; font-size: large;">인증번호가 일치하지 않습니다.</div>
	      <div class="footerdiv" id="footmsg2" style="color: blue; font-size: large;" >인증번호가 일치합니다.</div>
	        <button type="button" class="btn btn-danger success" data-bs-dismiss="modal" style="background-color: white; padding:10px; border:3px solid orange; border-radius: 5px; font-weight:bold;" onclick="complite()">완료</button>
	        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="background-color: white; padding:10px; border:3px solid red; border-radius: 5px; font-weight:bold;" id="modal_close" onclick="reset()">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
	</form>
</body>
</html>