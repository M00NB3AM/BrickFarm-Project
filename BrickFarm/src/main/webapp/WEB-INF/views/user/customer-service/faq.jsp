<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - 자주 묻는 질문</title>

    <!--====== Bootstrap4 Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/bootstrap.min.css" />

    <!--====== Common Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/common.css" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>

    <script>
      $(function () {
        $('#faq-category-1').addClass('selected');
      });

      function selectCategory(targetTag, categoryNo) {
        $('.breadcrumb__list > li').each(function (i, e) {
          $(e).removeClass('is-marked');
        });
        $(targetTag).parent().addClass('is-marked');

        $('.faq-category-wrapper').removeClass('selected');
        $('#faq-category-' + categoryNo).addClass('selected');
      }
    </script>
    <style>
      .faq-category-wrapper {
        display: none;
      }
      .faq-category-wrapper.selected {
        display: block;
      }
    </style>
  </head>
  <body class="config">
    <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
    <div id="app">
      <!--====== App Content ======-->
      <div class="app-content">
      
        <!--====== Section 1 ======-->
        <div class="u-s-p-t-60 u-s-p-b-30">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="breadcrumb overwrite-breadcrumb">
                <div class="breadcrumb__wrap overwrite-breadcrumb__wrap">
                  <ul class="breadcrumb__list">
                    <li class="has-separator"><a href="/">Home</a></li>
                    <li class="is-marked"><a href="/customer-service/faq">자주묻는 질문</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--====== End - Section 1 ======-->
        
        <!--====== Section 2 ======-->
        <div class="u-s-p-y-10">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="breadcrumb flex-jc-center">
                <div class="breadcrumb__wrap">
                  <ul class="breadcrumb__list">
                    <c:forEach var="faqCategory" items="${faqCategoryList}">
                      <c:choose>
                        <c:when test="${faqCategory.faq_category_no == 1}">
                          <li class="has-separator is-marked">
                            <a onclick="selectCategory(this, '${faqCategory.faq_category_no}');">${faqCategory.faq_category_name}</a>
                          </li>
                        </c:when>
                        <c:when test="${faqCategory.faq_category_no == 6}">
                          <li>
                            <a onclick="selectCategory(this, '${faqCategory.faq_category_no}');">${faqCategory.faq_category_name}</a>
                          </li>
                        </c:when>
                        <c:otherwise>
                          <li class="has-separator">
                            <a onclick="selectCategory(this, '${faqCategory.faq_category_no}');">${faqCategory.faq_category_name}</a>
                          </li>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 2 ======-->

        <!--====== Section 3 ======-->
        <div class="u-s-p-b-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row">
                <div class="col-lg-12">
                  <div class="faq">
                    <h3 class="faq__heading">자주 묻는 질문</h3>
                    <h3 class="faq__heading">질문 주제를 선택해 원하는 답변을 찾아보실 수 있습니다.</h3>
                    <p class="faq__text">
                      이곳에 없는 질문사항에 대해 궁금하실 때에는 1:1 문의하기를 통해 문의해주시면 답변해드립니다.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 3 ======-->

        <!--====== Section 4 ======-->
        <div class="u-s-p-b-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row">
                <div class="col-lg-12">
                  <div id="faq-accordion-group">
                    <c:forEach var="faqCategory" items="${faqCategoryList}">
                      <div id="faq-category-${faqCategory.faq_category_no}" class="faq-category-wrapper">
                        <c:forEach var="faq" items="${faqList}">
                          <c:if test="${faq.faq_category_no == faqCategory.faq_category_no}">
                            <div class="faq__list">
                              <a class="faq__question collapsed" href="#faq-${faq.faq_board_no}" data-toggle="collapse">${faq.title}</a>
                              <div
                                class="faq__answer collapse"
                                id="faq-${faq.faq_board_no}"
                                data-parent="#faq-accordion-group"
                              >
                                <p class="faq__text">${faq.content}</p>
                              </div>
                            </div>
                          </c:if>
                        </c:forEach>
                      </div>
                    </c:forEach>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--====== End - Section Content ======-->
        </div>
        <!--====== End - Section 4 ======-->
      </div>
      <!--====== End - App Content ======-->
    </div>
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
  </body>
</html>
