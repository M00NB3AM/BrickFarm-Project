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
<script>
/* document.getElementById("goback").addEventListener("click", () => {
	  history.back();
	});	
 */

</script>

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
                                <c:choose>
                                	<c:when test="${param.success == 'success'}">
                                    <h1 class="section__heading u-c-secondary">탈퇴가 완료되었습니다.</h1>
                                    </c:when>
                                </c:choose>
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
									    <c:when test="${param.success == 'success'}">
									        <h1 class="gl-h1" style="text-align: center;">그동안 이용해 주셔서 감사합니다.</h1>
									        <div style="text-align: center;">
									        <a href="/"><button class="btn btn--e-transparent-brand-b-2" type="button">홈으로</button></a>
									        </div>
									    </c:when>
									    <c:when test="${param.fail == 'fail'}">
									        <h1 class="gl-h1" style="text-align: center;">탈퇴에 실패했습니다.</h1>
									        <div style="text-align: center;">
									        <button class="btn btn--e-transparent-brand-b-2" type="button" onclick="history.back()">다시 시도하기</button>
									        </div>
									    </c:when>                                
									</c:choose>
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