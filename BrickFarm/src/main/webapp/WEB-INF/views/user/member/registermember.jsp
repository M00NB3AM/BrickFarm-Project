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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/resources/user/js/ysh/js/registermember.js" type="text/javascript"></script>
<script src="/resources/user/js/kakaojuso.js" type="text/javascript"></script>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
<title>BrickFarm</title>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css" />

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
<link rel="stylesheet" href="/resources/user/css/ysh/css/modalcss.css">
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
                                    <h1 class="section__heading u-c-secondary">회원가입</h1>
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
                                        <h1 class="gl-h1">소셜로그인</h1>
                                        <form class="l-f-o__form" name="registerform" method="post" action="/user/member/registermember">
                                            <div class="gl-s-api"></div>
                                                <div class="u-s-m-b-15">
                                           			<a id="google_login" class="circle google" href="${ google_url }"><img class="googlebtn" alt="googlelogin" src="/resources/user/images/logo/googlelogin.png"></a>
													<a id="naver_login" class="circle naver" href="${ naver_url }"><img class="naverbtn" alt="naverlogin" src="/resources/user/images/logo/naverlogin.png"></a>
													<a id="kakao_login" class="circle kakao" href="#"><img class="kakaobtn" alt="kakaologin" src="/resources/user/images/logo/kakao_login_medium_narrow.png"></a>
                                           		</div>
                                            <div class="u-s-m-b-30">

                                                <label class="gl-label" for="reg-fname">아이디 *</label>

                                                <input class="input-text input-text--primary-style" type="text" id="member_id" placeholder="아이디 : 4 ~ 10자" name="member_id">
                                            <div class="u-s-m-b-15">
                                                <button class="btn btn--e-transparent-brand-b-2" type="button" id="userId_confirm" onclick="checkUserId();">중복확인</button>
                                            </div>
                                                </div>
                                            <div class="u-s-m-b-30">
                                                <label class="gl-label" for="reg-lname">비밀번호 *</label>
                                                <input class="input-text input-text--primary-style" type="password" id="password" name="password" placeholder="비밀번호 : 4 ~ 12자" maxlength="12">
                                             </div>
                                                 <div class="u-s-m-b-30">
                                                <label class="gl-label" for="reg-password">비밀번호 확인 *</label>
                                                <input class="input-text input-text--primary-style" type="password" id="password_confirm" placeholder="Enter Password" maxlength="12">
                                                </div>
                                            <div class="u-s-m-b-30">
                                                <label class="gl-label" for="reg-lname">본인인증 *</label>
                                                <input class="importbtn btn btn--e-transparent-brand-b-2" type="button" id="checkPhoneNumber" onclick="importVerify();" value="본인인증하기"> 
                                                <div>
                                                <input class="input-text input-text--primary-style" type="hidden" id="member_name" placeholder="이름" name="member_name">
                                                <input class="input-text input-text--primary-style" type="hidden" id="phone_number" placeholder="전화번호" name="phone_number">
                                                </div>
                                             </div>
                                             
                                            <div class="gl-inline">
                                                <div class="u-s-m-b-30">
												 <label class="gl-label" for="gender">생년월일</label>
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
                                            <div class="u-s-m-b-30">
                                                <label class="gl-label" for="reg-lname">주소 *</label>
                                                <div>
                                                <input class="importbtn btn btn--e-transparent-brand-b-2" type="button" id="findjuso" value="주소검색" onclick="findkakaoaddress();"> 
                                                <div>
                                                <input class="input-text input-text--primary-style" type="text" id="zip_code" placeholder="우편번호" name="zip_code" readonly>
                                                <input class="input-text input-text--primary-style" type="text" id="address" placeholder="주소" name="address" readonly>
                                                <input class="input-text input-text--primary-style" type="text" id="detailaddress" placeholder="상세주소를 입력해주세요." name="detailaddress">
                                                </div>
                                                </div>
                                             </div>
                                            
                                            
                                            <div class="gl-inline" style="display: inline-flex">
                                                <div class="u-s-m-b-30">
												 <label class="gl-label" for="gender">이메일을 입력해주세요 *</label>
                                                   <input class="input-text input-text--primary-style" type="text" id="email" placeholder="이메일" name="email" readonly/>
                                            	</div>
											<a class="gl-link"><button type="button" class="btn btn--e-transparent-brand-b-2 emailbtn" data-bs-toggle="modal" data-bs-target="#myModal">이메일 인증</button></a>
                                            </div>
                                            <div class="u-s-m-b-15">
                                                <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="registerMember();">회원가입하기</button>
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
 <!-- 모달창 -->
 <form id="modalform">
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
			<div><span id="modalspan1" class="modalspan" style="color: blue">인증코드를 받을 이메일 입력해주세요</span></div>
			<div><span id="modalspan2" class="modalspan" style="color: blue">인증번호를 발송했습니다.</span></div>
			<div id="beforediv">
			<input id="modalemail" name="modalemail" type="email" class="emailinput">
			<button id="emailbtn" name="emailbtn" class="emailmodalbtn" type="button" onclick="emailconfirm();">인증번호받기</button>
			</div>
			<div id="afterdiv">
				<input type="text" id="emailnum" class="emailinput">
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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>
</html>