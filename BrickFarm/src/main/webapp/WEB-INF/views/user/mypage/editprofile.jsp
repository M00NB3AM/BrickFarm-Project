<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm</title>
    <!--====== Google Font ======-->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="/resources/user/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="/resources/user/js/ysh/js/modalscript.js" type="text/javascript"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="/resources/user/js/ysh/js/mypagemodifyprofile.js" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/user/css/ysh/css/modalcss.css">
<script type="text/javascript" src="/resources/user/js/kakaojuso.js"></script>



<script type="text/javascript">
$(function () {
	console.log(result)
})
</script>

<style>
.addrbtn{
	border-radius: 5px;
    background-color: #fa4400;
    color: white;
    border-style: none;
}
.phonebtn{
	border-radius: 5px;
    background-color: #fa4400;
    color: white;
    border-style: none;
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
  
  <body class="config">
  	<c:set var="contextPath" value="<%=request.getContextPath()%>" />
	<jsp:include page="../header.jsp"></jsp:include>
    <!--====== Main App ======-->
    <div id="app">

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
                                    <li class="has-separator"><a href="/">Home</a></li>
                                      <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
                                    <li class="has-separator"><a href="profile">내 프로필</a></li>
                                    <li class="is-marked"><a href="#">프로필 수정</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--====== End - Section 1 ======-->


            <!--====== Section 2 ======-->
            <div class="u-s-p-b-60">

                <!--====== Section Content ======-->
                <div class="section__content">
                    <div class="dash">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-3 col-md-12">

                                    <!--====== Dashboard Features ======-->
                                    <jsp:include page="./dashBoard.jsp"/>
                                    <!--====== End - Dashboard Features ======-->
                                </div>
                                <div class="col-lg-9 col-md-12">
                                    <div class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white">
                                        <div class="dash__pad-2">
                                            <h1 class="dash__h1 u-s-m-b-14">회원 정보 수정</h1>

                                           
                                            <div class="dash__link dash__link--secondary u-s-m-b-30">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <form class="dash-edit-p" name="modifyMemberInfo" action="/user/member/mypageinfomodify" method="post">
                                                        <div class="gl-inline">
                                                            <div class="u-s-m-b-30">

                                                                <label class="gl-label" for="reg-fname">아이디</label>

                                                                <input class="input-text input-text--primary-style" type="text" id="reg-fname" placeholder="${loginMemberInfo.member_id}" readonly></div>
                                                           
                                                            <div class="u-s-m-b-30">

                                                                <label class="gl-label" for="reg-lname">이름</label>

                                                                <input class="input-text input-text--primary-style" type="text" name="member_name" id="member_name" placeholder="${loginMemberInfo.member_name}" value="${loginMemberInfo.member_name}" readonly></div>
                                                        </div>
                                                        <div class="gl-inline">
                                                            <div class="u-s-m-b-30">

                                                                <!--====== Date of Birth Select-Box ======-->

                                                                <span class="gl-label">BIRTHDAY</span>
                                                                <div class="gl-dob">
                                                                <input class="input-text input-text--primary-style" type="text" placeholder="${loginMemberInfo.birth_date}"readonly>
                                                                    </div>
                                                                <!--====== End - Date of Birth Select-Box ======-->
                                                            </div>
                                                            <div class="u-s-m-b-30">

                                                                <label class="gl-label" for="gender">GENDER</label>
                                                                <c:choose>
                                                                <c:when test="${loginMemberInfo.gender eq 'M'}">
                                                                    <input class="input-text input-text--primary-style" type="text" placeholder="남성"readonly>
                                                                </c:when>
                                                                <c:when test="${loginMemberInfo.gender eq 'F'}">
                                                                    <input class="input-text input-text--primary-style" type="text" placeholder="여성"readonly>
                                                                </c:when>
                                                                <c:otherwise>
                                                                	<input class="input-text input-text--primary-style" type="text" placeholder=""readonly>
                                                                </c:otherwise>
                                                                </c:choose>
                                                                </div>
                                                        </div>
                                                        <div class="gl-inline">
                                                            <div class="u-s-m-b-30">
                                                                <h2 class="dash__h2 u-s-m-b-8">E-mail</h2>

                                                                <input id="email" name="email" class="input-text input-text--primary-style" type="text"  placeholder="${loginMemberInfo.email }" value="${loginMemberInfo.email}" readonly>
                                                                <div class="dash__link dash__link--secondary">
																<c:choose>
                                                                <c:when test="${loginMemberInfo.social_check == null}">
                                                                    <a><button type="button" class="btn btn-primary emailbtn" data-bs-toggle="modal" data-bs-target="#myModal">이메일 인증후 수정하기</button></a>
                                                                </c:when>
                                                                <c:when test="${loginMemberInfo.social_check != null}">
                                                                    <span>SNS로그인은 이메일을 수정 할 수 없습니다.</span>
                                                                </c:when>
                                                                </c:choose>
                                                                    
                                                                </div>
                                                            </div>
                                                            <div class="u-s-m-b-30">
                                                                <h2 class="dash__h2 u-s-m-b-8">Phone</h2>

                                                                <input id="phone_number" class="input-text input-text--primary-style" type="text" name="phone_number"  placeholder="" value="${loginMemberInfo.phone_number}" readonly>
                                                                <div class="dash__link dash__link--secondary">
                                                                   <button type="button" onclick="importVerify();" class="phonebtn">본인인증</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="gl-inline">
                                                            <div class="u-s-m-b-30">
                                                                <h2 class="dash__h2 u-s-m-b-8">우편번호</h2>

                                                                <input id="zip_code" name="zip_code" class="input-text input-text--primary-style" type="text"  placeholder="${loginMemberInfo.zip_code }" value="${loginMemberInfo.zip_code}"readonly>
                                                                <div class="dash__link dash__link--secondary">

                                                                    <p>주소변경버튼을 클릭해주세요.</p></div>
                                                            </div>
                                                            <div class="u-s-m-b-30">
                                                                <h2 class="dash__h2 u-s-m-b-8">주소</h2>

                                                                <input id="address" name="address" class="input-text input-text--primary-style" type="text"  placeholder="${loginMemberInfo.address}" value="${loginMemberInfo.address}" readonly>
                                                                <div class="dash__link dash__link--secondary">
                                                                    <button type="button" onclick="findkakaoaddress();" class="addrbtn" id="addrbtn">주소 변경하기</button>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <button class="btn btn--e-brand-b-2" type="button" onclick="mypagemodify()">저장하기</button>
                                                        <input type="hidden" id="member_no" name="member_no" value="${loginMemberInfo.member_no}">
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
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
      <!--====== End - App Content ======-->

      <!--====== Modal Section ======-->

     
      <!--====== End - Modal Section ======-->
    </div>
    <!--====== End - Main App ======-->

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
    <script src="https://www.google-analytics.com/analytics.js"
      async
      defer
    ></script>

	<!--====== Vendor Js ======-->
	<script src="/resources/user/js/vendor.js"></script>

	<!--====== jQuery Shopnav plugin ======-->
	<script src="/resources/user/js/jquery.shopnav.js"></script>

	<!--====== App ======-->
	<script src="/resources/user/js/app.js"></script>

</div>
    <jsp:include page="../footer.jsp"></jsp:include>
    
    <!-- 모달창 -->
    <div class="modal" id="myModal">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">이메일 인증</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
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
				<input type="text" id="emailnum" class="emailnum">
				<button id="confirmbtn" type="button" onclick="confirm()">인증하기</button>
			
			</div>
	      </div>
	      <!-- Modal footer -->
	      <div class="modal-footer">
	      	
	      <div class="footerdiv" id="footmsg1" style="color: red; font-size: large;">인증번호가 일치하지 않습니다.</div>
	      <div class="footerdiv" id="footmsg2" style="color: blue; font-size: large;" >인증번호가 일치합니다.</div>
	        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="background-color: #FF4500;" onclick="complite()">완료</button>
	        <button type="button" class="btn btn-danger" data-bs-dismiss="modal" style="background-color: red;">닫기</button>
	      </div>
	    </div>
	  </div>
	</div>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  </body>
</html>
