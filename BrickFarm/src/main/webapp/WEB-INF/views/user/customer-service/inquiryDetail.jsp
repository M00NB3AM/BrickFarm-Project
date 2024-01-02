<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - [문의] ${inquiry.title}</title>

    <!--====== Bootstrap4 Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/bootstrap.min.css" />

    <!--====== Common Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/common.css" />

    <!--====== Inquiry Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/inquiry.css" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Common Js ======-->
    <script src="/resources/user/js/kyj/common.js"></script>

    <script>
      function viewDetailImg(imgSrc, imgAlt) {
        // console.log(imgSrc, imgAlt);
        $("#imgModal").attr("src", imgSrc);
        $("#imgModal").attr("alt", imgAlt);
        $("#modal").modal();
      }

      function handlerPrevImg() {
        const images = $(".wrapper-img").children();
        const currentSrc = $("#imgModal").attr("src");

        let currentIndex;
        for (i = 0; i < images.length; i++) {
          // console.log($(images[i]).attr("data-detail-src"));
          // console.log(currentSrc);
          if ($(images[i]).attr("data-detail-src") == currentSrc) {
            currentIndex = i;
            break;
          }
        }

        // console.log(currentIndex)
        if (currentIndex > 0) {
          $("#imgModal").attr("src", $(images[currentIndex - 1]).attr("data-detail-src"));
        } else {
          $("#imgModal").attr("src", $(images[images.length - 1]).attr("data-detail-src"));
        }
      }

      function handlerNextImg() {
        const images = $(".wrapper-img").children();
        const currentSrc = $("#imgModal").attr("src");

        let currentIndex;
        for (i = 0; i < images.length; i++) {
          // console.log($(images[i]).attr("data-detail-src"));
          // console.log(currentSrc);
          if ($(images[i]).attr("data-detail-src") == currentSrc) {
            currentIndex = i;
            break;
          }
        }

        // console.log(currentIndex)
        if (currentIndex < images.length - 1) {
          $("#imgModal").attr("src", $(images[currentIndex + 1]).attr("data-detail-src"));
        } else {
          $("#imgModal").attr("src", $(images[0]).attr("data-detail-src"));
        }
      }
      
      function redirectModifyInquiry(inquiryNo) {
      	location.href = "/customer-service/inquiry/modify?no=" + inquiryNo;
      }
      
      function removeInquiry(inquiryNo) {
    	const form = document.createElement("form");
    	form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "post");
        form.setAttribute("action", "/customer-service/inquiry/remove");
        
        const hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "inquiryBoardNo");
        hiddenField.setAttribute("value", inquiryNo);
        
        form.appendChild(hiddenField);
        document.body.appendChild(form);
        form.submit();
      }
    </script>
  </head>
  <body class="config">
    <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
    <c:choose>
   	  <c:when test='${param.status == "noPermission" && empty sessionScope.loginMemberInfo}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">로그인 후 이용해주세요</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
   	  </c:when>
   	  <c:when test='${param.status == "removeFailed"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">삭제 과정에서 에러가 발생했습니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
   	  </c:when>
      <c:when test='${param.status == "serverError"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">일시적인 서버 에러입니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
      </c:when>
    </c:choose>
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
                    <li class="has-separator"><a href="/customer-service/inquiry/list">문의하기</a></li>
                    <li class="is-marked"><a>${inquiry.title}</a></li>
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
              
              	<c:if test="${not empty sessionScope.loginMemberInfo && inquiry.member_no == sessionScope.loginMemberInfo.member_no}">
              	  <div class="col-12 u-s-m-b-10 u-s-m-x-auto flex-wrapper flex-jc-end gap-1">
              	    <button class="btn btn-inquiry" onclick="redirectModifyInquiry(${inquiry.inquiry_board_no});">문의수정</button>
              	    <button class="btn btn-inquiry" onclick="removeInquiry(${inquiry.inquiry_board_no});">문의삭제</button>
              	  </div>
              	</c:if>
              	
                <div class="border-content">
                  <c:choose>
                    <c:when test="${inquiry.is_delete.equals('N')}">
                      <div class="bp-detail__stat">
                        <span class="bp-detail__stat-wrap">
                          <span class="bp-detail__publish-date">
                            <a class="cursor-default">
                              <span>작성일 : ${inquiry.created_date}</span>
                            </a>
                          </span>
                        </span>
                      </div>

                      <span class="bp-detail__h1">
                        <a class="cursor-default">${inquiry.title}</a>
                      </span>

                      <div class="blog-t-w">
                        <a class="gl-tag btn--e-transparent-hover-brand-b-2">${inquiry.inquiry_category_name}</a>
                      </div>

                      <div class="detail-content">
                        <p class="bp-detail__p">${fn:replace(inquiry.content, newLineChar, "<br/>")}</p>
                      </div>

                      <c:if test="${inquiryImages.size() > 0 }">
                        <div class="post-center-wrap">
                          <div class="area-file-uploaded" id="uploadFiles">
                            <div class="wrapper-img">
                              <c:forEach var="inquiryImage" items="${inquiryImages}">
                                <c:set var="newFileName" value='${inquiryImage.new_file_name.replace("\\\\", "/")}' />
                                <img
                                  src='/resources/user/uploads/inquiry${inquiryImage.thumbnail_file_name.replace("\\", "/")}'
                                  data-detail-src="/resources/user/uploads/inquiry${newFileName}"
                                  id="img${inquiryImage.inquiry_images_no}"
                                  class="img-uploaded-file img-clickable"
                                  onclick="viewDetailImg('/resources/user/uploads/inquiry${newFileName}', '${inquiryImage.original_file_name}');"
                                />
                              </c:forEach>
                            </div>
                          </div>
                        </div>
                      </c:if>
                    </c:when>
                    <c:otherwise>
                      <div>이미 삭제되었거나 존재하지 않는 문의글입니다.</div>
                    </c:otherwise>
                  </c:choose>
                </div>

                <div class="gl-l-r bp-detail__postnp">
                  <div>
                    <a href="/customer-service/inquiry/list">목록 보기</a>
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
    <div class="modal fade" id="modal">
      <div class="modal-dialog modal-lg">
        <div class="wrapper-modal-img">
          <img class="icon-controller icon-prev-arrows" id="iconPrev" onclick="handlerPrevImg();" />
          <img id="imgModal" />
          <img class="icon-controller icon-next-arrows" id="iconNext" onclick="handlerNextImg();" />
        </div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
  </body>
</html>
