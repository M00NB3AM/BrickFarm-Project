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
<title>BrickFarm</title>

<!--====== Google Font ======-->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800"
	rel="stylesheet">

<!--====== Vendor Css ======-->
<link rel="stylesheet" href="/resources/user/css/vendor.css">

<!--====== Utility-Spacing ======-->
<link rel="stylesheet" href="/resources/user/css/utility.css">

<!--====== App ======-->
<link rel="stylesheet" href="/resources/user/css/app.css">
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<div class="modifypwddiv">
		<div class="modifypwd">
			<h1 class="modifyh1">비밀번호가 변경되었습니다.</h1>
			<div>다시 로그인 해주시기 바랍니다.</div>
			<a href="/user/member/loginregistermodel"><button type="button">로그인하러가기</button></a>
		</div>
	</div>



	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>