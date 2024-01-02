<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - [공지] ${notice.title}</title>

    <!--====== Bootstrap4 Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/bootstrap.min.css" />

    <!--====== Common Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/common.css" />

    <!--====== Notice Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/notice.css" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
  </head>
  <body class="config">
    <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
    <div id="app">
      <!--====== App Content ======-->
      <div class="app-content">
      
      	<!--====== Section 1 ======-->
        <div class="u-s-p-t-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="breadcrumb overwrite-breadcrumb">
                <div class="breadcrumb__wrap overwrite-breadcrumb__wrap">
                  <ul class="breadcrumb__list">
                    <li class="has-separator"><a href="/">Home</a></li>
                    <li class="has-separator"><a href="/notice/list">공지사항</a></li>
                    <li class="is-marked"><a>${notice.title}</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--====== End - Section 1 ======-->
        
        <!--====== Section 2 ======-->
        <div class="u-s-p-y-90">
          <!--====== Detail Post ======-->
          <div class="detail-post">
            <div class="bp-detail">
              <div class="bp-detail__info-wrap">
                <div class="border-content">
                  <c:choose>
                    <c:when test="${notice != null}">
                      <div class="bp-detail__stat">
                        <span class="bp-detail__stat-wrap">
                          <span class="bp-detail__publish-date">
                            <a class="cursor-default">
                              <span>작성일 : ${notice.created_date}</span>
                            </a>
                          </span>
                        </span>
                      </div>

                      <span class="bp-detail__h1">
                        <a class="cursor-default">${notice.title}</a>
                      </span>

                      <div class="blog-t-w">
                        <a class="gl-tag btn--e-transparent-hover-brand-b-2">${notice.notice_category_name}</a>
                      </div>

                      <div class="detail-content">
                        <p class="bp-detail__p">${notice.content}</p>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <div>이미 삭제되었거나 존재하지 않는 게시글입니다.</div>
                    </c:otherwise>
                  </c:choose>
                </div>

                <div class="gl-l-r bp-detail__postnp">
                  <div>
                    <a href="/notice/list">목록 보기</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Detail Post ======-->
        </div>
        <!--====== End - Section 2 ======-->
      </div>
      <!--====== End - App Content ======-->
    </div>
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
  </body>
</html>
