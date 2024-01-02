<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="html">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>BrickFarm</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="/resources/user/js/ysh/js/checkmodify.js" type="text/javascript"></script>
<script src="/resources/user/js/ysh/js/login-membercheck.js" type="text/javascript"></script>
<script src="/resources/user/js/ysh/js/portone.js" type="text/javascript"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="/resources/user/js/ysh/js/userminicart.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />

<!--====== Google Font ======-->
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800" rel="stylesheet">
<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">
<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">
<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">

<!-- 추가 -->
<script src="/resources/user/js/ysh/js/find-user-info.js" type="text/javascript"></script>
<script src="/resources/user/js/ysh/js/finduseridpwdtest.js" type="text/javascript"></script>
<link rel="stylesheet" href="/resources/user/css/ysh/css/finduseridpwdtest.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.16.1/TweenMax.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>

<div class="cotn_principal">
      <div class="cont_centrar">
        <div class="cont_login">
          <div class="cont_info_log_sign_up">
            <div class="col_md_login">
              <div class="cont_ba_opcitiy">
                <h2>아이디찾기</h2>
                <button class="btn_login" onclick="change_to_login()">아이디찾기</button>
              </div>
            </div>
            <div class="col_md_sign_up">
              <div class="cont_ba_opcitiy">
                <h2>비밀번호찾기</h2>
                <button class="btn_sign_up" onclick="change_to_sign_up()">비밀번호찾기</button>
              </div>
            </div>
          </div>

          <div class="cont_back_info">
            <div class="cont_img_back_grey">
              <img
                src="/resources/user/images/ysh/lego-3388163_1280.png"
                alt=""
              />
            </div>
          </div>
          <div class="cont_forms">
            <div class="cont_img_back_">
              <img
                src="/resources/user/images/ysh/lego-3388163_1280.png"
                alt=""
              />
            </div>
            
            <!-- 아이디 비밀번호 검색하는 부분. -->
            <form action="findmemberid" name="find_user_id" method="post">
            <div class="cont_form_login">
              <a href="#" onclick="hidden_login_and_sign_up()"><i class="material-icons">X</i></a>
              <h2>아이디검색</h2>
              <input id="id_member_name" name="member_name" type="text" placeholder="이름" readonly/>
              <input id="id_phone_number" name="phone_number" type="text" placeholder="전화번호" readonly/>
              <button id="idbutton" type="button" class="btn_login" onclick="find_userid()">본인인증</button>
              <button type="button" class="btn_login" onclick="availabilityuserid()">검색</button>
            </div>
            </form>

			<form action="finduserpw" name="find_user_password" method="post">
            <div class="cont_form_sign_up">
              <a href="#" onclick="hidden_login_and_sign_up()"><i class="material-icons">X</i></a>
              <h2>비밀번호검색</h2>
              <input id="pwd_member_id" name="member_id" type="text" placeholder="아이디" />
              <input id="pwd_member_name" name="member_name" type="text" placeholder="이름" readonly/>
              <input id="pwd_phone_number" name="phone_number" type="password" placeholder="전화번호" readonly/>
              <button id="pwdbutton" type="button" value="본인인증하기" onclick="find_userpwd()" class="btn_sign_up">본인인증하기</button>
              <button type="button" class="btn_sign_up" onclick="availabilityuserpwd()">검색</button>
            </div>
            </form>
          </div>
        </div>
      </div>
    </div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>