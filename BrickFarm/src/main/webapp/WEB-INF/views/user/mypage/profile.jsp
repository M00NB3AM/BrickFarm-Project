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
<link rel="stylesheet" href="/resources/user/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
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
                  
                    <li class="has-separator">
                      <a href="/">Home</a>
                    </li>
                      <li class="has-separator"><a href="profileinfo">마이페이지</a></li>
                    <li class="is-marked">
                      <a>내 프로필</a>
                    </li>
                    
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
                    <div
                      class="dash__box dash__box--shadow dash__box--radius dash__box--bg-white u-s-m-b-30"
                    >
                      <div class="dash__pad-2">
                     <%--  <div>${sessionScope.loginMemberInfo}</div> --%>
                        <h1 class="dash__h1 u-s-m-b-14">My Profile</h1>
                        <div class="row">
                          <div class="col-lg-4 u-s-m-b-30">
                            <h2 class="dash__h2 u-s-m-b-8">이름</h2>

                            <span class="dash__text">${loginMember.member_name}</span>
                          </div>
                          <div class="col-lg-4 u-s-m-b-30">
                            <h2 class="dash__h2 u-s-m-b-8">E-mail</h2>

                            <span class="dash__text">${loginMember.email}</span>
                           
                          </div>
                          <div class="col-lg-4 u-s-m-b-30">
                            <h2 class="dash__h2 u-s-m-b-8">전화번호</h2>

                            <span class="dash__text">${loginMember.phone_number}</span>
                            
                          </div>
                          <div class="col-lg-4 u-s-m-b-30">
                            <h2 class="dash__h2 u-s-m-b-8">생년월일</h2>

                            <span class="dash__text">${loginMember.birth_date}</span>
                          </div>
                          <div class="col-lg-4 u-s-m-b-30">
                            <h2 class="dash__h2 u-s-m-b-8">성별</h2>
	
	 					<c:choose>
	 					<c:when test="${loginMember.gender eq 'M'}">
                            <span class="dash__text">남</span>	 					
	 					</c:when>
	 					<c:when test="${loginMember.gender eq 'F'}">
                            <span class="dash__text">여</span>	 					
	 					</c:when>
               			</c:choose>                                                 
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-lg-12">
                            <div class="dash__link dash__link--secondary u-s-m-b-30">
                            
                            </div>
                            <div class="u-s-m-b-16">
                              <a
                                class="dash__custom-link btn--e-transparent-brand-b-2"
                                href="/mypage/editprofile"
                                >정보 수정</a
                              >
                            </div>
                            <c:choose>
                            	<c:when test="${loginMemberInfo.social_check == null}">
                            		<div>
                              			<a class="dash__custom-link btn--e-brand-b-2" href="/user/member/deletemember">회원 탈퇴</a>
                            		</div>
                            	</c:when>
                            	<c:when test="${loginMemberInfo.social_check != null}">
                            		<div>
                              			<a class="dash__custom-link btn--e-brand-b-2" href="/user/member/snsdeletemember">회원 탈퇴</a>
		                            </div>
                            	</c:when>
                            </c:choose>
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
    <script
      src="https://www.google-analytics.com/analytics.js"
      async
      defer
    ></script>

	<!--====== Vendor Js ======-->
	<script src="/resources/user/js/vendor.js"></script>

	<!--====== jQuery Shopnav plugin ======-->
	<script src="/resources/user/js/jquery.shopnav.js"></script>

	<!--====== App ======-->
	<script src="/resources/user/js/app.js"></script>


    <jsp:include page="../footer.jsp"></jsp:include>
  </body>
</html>
