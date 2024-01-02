<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en" class="h-100">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>BrickFarm</title>
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="./images/favicon.png">
    <link href="/resources/admin/css/style.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="/resources/admin/js/ysh/adminlogin.js" type="text/javascript"></script>
	
	<script>
		$(function() {			
			$("#password").on("keyup", function(e) {
				if(e.keyCode == 13) {
					login();
				}
			});
		});
	</script>
</head>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<body class="h-100">
    <div class="authincation h-100">
        <div class="container h-100">
            <div class="row justify-content-center h-100 align-items-center">
                <div class="col-md-6">
                    <div class="authincation-content">
                        <div class="row no-gutters">
                            <div class="col-xl-12">
                                <div class="auth-form">
                                    <h4 class="text-center mb-4">BrickFarm 관리자 로그인</h4>
                                    <form action="/admin/login" name="loginform" method="post">
                                        <div class="form-group">
                                            <label class="mb-1"><strong>ID</strong></label>
                                            <input type="text" id="adminid" class="form-control" placeholder="관리자 아이디를 입력해주세요" name = "admin_id">
                                        </div>
                                        <div class="form-group">
                                            <label class="mb-1"><strong>Password</strong></label>
                                            <input type="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요" name="admin_password">
                                        </div>
                                        <div class="form-row d-flex justify-content-between mt-4 mb-2">
                                            <div class="form-group">
                                               <div class="custom-control custom-checkbox ml-1">
													<input type="checkbox" class="custom-control-input" id="basic_checkbox_1">
												</div>
                                            </div>
                                        </div>
                                        <div class="text-center">
                                            <button type="button" class="btn btn-primary btn-block" onclick="login()">로그인 하기</button> 
                                        </div>
                                            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token}" />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>