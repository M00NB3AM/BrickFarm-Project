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
<link href="/resources/user/css/ysh/css/modifypwd.css" rel="stylesheet" />
<script src="/resources/user/js/ysh/js/checkmodify.js" type="text/javascript"></script>
<link href="/resources/images/favicon.png" rel="shortcut icon" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<title>BrickFarm</title>
<style>
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
								<h1 class="section__heading u-c-secondary">ALREADY
									REGISTERED?</h1>
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
                                    <c:choose>
                                    	<c:when test="${memberInfo.social_check != null }">
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
                                    	<c:otherwise>
                                        <h1 class="gl-h1">새로운 비밀번호 설정</h1>
                                        <div class="u-s-m-b-15">
                                        <form class="l-f-o__form" method="post" action="/user/member/loginMember" name="login-form">
                                           	 	<div class="u-s-m-b-30">
                                                <label class="gl-label" for="login-email">새로운 비밀번호 *</label>
                                                <input class="input-text input-text--primary-style" type="password" id="password" name="password" maxlength="10" placeholder="4자리 ~ 10자리"></div>
                                            <div class="u-s-m-b-30">

                                                <label class="gl-label" for="login-password">비밀번호 확인 *</label>

                                                <input class="input-text input-text--primary-style" type="password" name="password_confirm" id="password_confirm" maxlength="10" placeholder="Enter Password"></div>
                                            <div class="gl-inline">
                                                <div class="u-s-m-b-30">
                                                    <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="checkmodifypwd();">변경하기</button></div>
                                            </div>
                                            <a class="gl-link" href="/">Return to Store</a>
                                        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}">
										<input type="hidden" id="member_id" name="member_id" value="${member_id}">
                                        </form>                                  	
										</div>
                                    	</c:otherwise>
                                    </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
		</div>
	</div>

	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>














<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
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
<link href="/resources/user/css/ysh/css/modifypwd.css" rel="stylesheet" />
<script src="/resources/user/js/ysh/js/checkmodify.js" type="text/javascript"></script>
<script src="/resources/user/js/ysh/js/portone.js" type="text/javascript"></script>
<script src="/resources/user/js/ysh/js/findjuso.js" type="text/javascript"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="/resources/user/js/ysh/js/userminicart.js"></script>
<link href="/resources/images/favicon.png" rel="shortcut icon">
<link href="/resources/user/css/ysh/css/loginregistermodel.css" rel="stylesheet" />
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
<title>BrickFarm</title>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css" />
<script src="/resources/user/js/ysh/js/login.js" type="text/javascript"></script>

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

                
                <!--====== End - Section Content ======-->
            </div>
            <!--====== End - Section 2 ======-->
        </div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html> --%>