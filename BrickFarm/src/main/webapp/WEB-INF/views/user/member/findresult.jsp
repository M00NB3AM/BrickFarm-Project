<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
<link href="/resources/user/css/ysh/css/button.css" rel="stylesheet" />

<title>BrickFarm</title>
<head>
<meta charset="UTF-8" />
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge" /><![endif]-->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<link href="/resources/images/favicon.png" rel="shortcut icon" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<title>BrickFarm</title>

<!--====== Google Font ======-->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet" />

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css" />

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css" />

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css" />


<style>
.goback {
	display: flex;
	justify-content: center;
	margin: 30px;
}

.firstbtn {
	
}

.secondbtn {
	
}

.naverbtn {
	width: 160px;
	height: 37px;
}

.googlebtn {
	width: 160px;
	height: 37px;
}

.kakaobtn {
	width: 160px;
	height: 37px;
}

.snsspan {
	text-align: center;
	font-weight: 900;
}
h2{
text-align: center;
}
.found-id{
	text-align: center;
    border: 5px solid lawngreen;
    margin: 0px 160px;
    color: black;
    font-weight: bold;
}
.foundlogin{
	display: flex;
	place-content: center;
}
.loginbtn{
	margin: 30px;	
}

</style>
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
								<li class="has-separator"><a href="/">홈</a></li>
								<li class="is-marked"><a href="./loginpage">로그인</a></li>
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
								<h1 class="section__heading u-c-secondary">아이디 / 비밀번호 찾기</h1>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--====== End - Section Intro ======-->
			<div class="section__content">
				<div class="container">
					<div class="row row--center">
						<div class="col-lg-6 col-md-8 u-s-m-b-30">
							<div class="l-f-o">
								<div class="l-f-o__pad-box">

									<span class="gl-text u-s-m-b-30 snsspan">SNS 로그인 하기</span>
									

										<div class="gl-inline">
											<div class="u-s-m-b-15">
												<a id="google_login" class="circle google"
													href="${ google_url }"><img class="googlebtn"
													alt="googlelogin"
													src="/resources/user/images/logo/googlelogin.png"></a> <a
													id="naver_login" class="circle naver" href="${ naver_url }"><img
													class="naverbtn" alt="naverlogin"
													src="/resources/user/images/logo/naverlogin.png"></a> <a
													id="kakao_login" class="circle kakao" href="#"><img
													class="kakaobtn" alt="kakaologin"
													src="/resources/user/images/logo/kakao_login_medium_narrow.png"></a>
											</div>
										</div>

										<form>
											<c:choose>
												<c:when test="${memberVO.social_check != null || member_no == 0}">
													<div class="container">
														<div class="found-success">
															<h2>회원님은</h2> 
															<div class="found-id">소셜로그인 유저</div>
															<h2>입니다</h2>
														</div>
														<div class="foundlogin">
															<a href="/user/member/loginpage"><input type="button" id="btnLogin"
																value="로그인" class="custom-btn btn-14 loginbtn" /></a>
														</div>
													</div>
												</c:when>
												<c:when test="${memberVO.social_check == null && memberVO.member_id != null}">
													<div class="container">
														<div class="found-success">
															<h2>회원님의 아이디는</h2>
															<div class="found-id">${memberVO.member_id}</div>
															<h2>입니다</h2>
														</div>
														<div class="foundlogin">
															<a href="/user/member/loginpage"><input type="button" id="btnLogin"
																value="로그인" class="custom-btn btn-14 loginbtn" /></a>
														</div>
													</div>
												</c:when>
												<c:otherwise>
													<div class="container">
														<div class="found-fail">
															<h2>등록된 정보가 없습니다</h2>
														</div>
														<div class="goback">
															<div class="firstbtn">
																<button type="button" class="custom-btn btn-12"	onClick="history.back()">
																<span>Click!</span><span>다시찾기</span>
																</button>
															</div>
															<div class="secondbtn">
																<a href="./loginregistermodal">
																<button type="button" class="custom-btn btn-14">
																<span>회원가입</span>
																</button>
																</a>
															</div>
														</div>
													</div>
												</c:otherwise>
											</c:choose>
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
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>





