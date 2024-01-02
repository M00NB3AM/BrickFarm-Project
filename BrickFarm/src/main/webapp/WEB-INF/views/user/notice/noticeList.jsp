<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - 공지사항 및 이벤트</title>

    <!--====== Bootstrap4 Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/bootstrap.min.css" />

    <!--====== Common Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/common.css" />

    <!--====== Notice Css ======-->
    <link rel="stylesheet" href="/resources/user/css/kyj/notice.css" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Common Js ======-->
    <script src="/resources/user/js/kyj/common.js"></script>

    <script>
      const params = {
      	'curPageNo': ${pagingInfo.curPageNo},
      	'rowCountPerPage': ${pagingInfo.rowCountPerPage},
      	'searchType': '${searchInfo.searchType}',
      	'searchWord': '${searchInfo.searchWord}',
      	'searchCategory': ${searchInfo.searchCategory}
      }

      $(function() {
      	$("#searchWord").on("keyup", function(e) {
      		if(e.keyCode == 13) {
      			search();
      		}
      	})

      	$("#searchType").change(function(e) {
      		if(e.target.value == "notice_category_no") {
      			$("#searchWord").removeClass("form-invisible");
      			$("#searchCategory").removeClass("form-invisible");

      			$("#searchWord").addClass("form-invisible");
      			$("#searchWord").val("");
      		} else {
      			$("#searchWord").removeClass("form-invisible");
      			$("#searchCategory").removeClass("form-invisible");

      			$("#searchCategory").addClass("form-invisible");
      			$("#searchCategory").val("");
      		}
      	})
      })

      function requestNoticeList() {
      	let form = document.createElement('form');
      	form.action = '/notice/list';
      	form.method = 'GET';

      	$(form).addClass('form-invisible');

      	let inputTags = `<input name="pageNo" value="\${params.curPageNo}" />`;
      	inputTags += `<input name="rowCountPerPage" value="\${params.rowCountPerPage}" />`;
      	if(params.searchType != ""){
      		if((params.searchWord == "" || params.searchWord.length < 2) && params.searchType != "notice_category_no") {
      			alert("검색어는 최소 두 글자 이상이어야 합니다.");
      			return;
      		}
      		inputTags += `<input name="searchType" value="\${params.searchType}" />`;
      		inputTags += `<input name="searchWord" value="\${params.searchWord}" />`;
      		inputTags += `<input name="searchCategory" value="\${params.searchCategory}" />`;
      	}
      	$(form).html(inputTags);

      	document.body.append(form);

      	form.submit();
      }

      function selectPage(pageNo) {
      	params.curPageNo = pageNo;

      	requestNoticeList();
      }

      function search() {
      	if($("#searchType").val() == "" && $("#searchWord").val().length > 0) {
      		alert("검색조건을 선택해주세요.");
      		return;
      	}
      	if($("#searchType").val() == "notice_category_no" && $("#searchCategory").val() == "") {
      		alert("분류를 선택해주세요.");
      		return;
      	}

      	params.searchType = $("#searchType").val();
      	params.searchWord = $("#searchWord").val();
      	params.searchCategory = $("#searchCategory").val();

      	requestNoticeList();
      }

      function redirectDetail(no) {
      	location.href = "/notice/detail?no=" + no;
      }
    </script>
  </head>
  <body class="config">
    <jsp:include page="/WEB-INF/views/user/header.jsp"></jsp:include>
    <c:choose>
      <c:when test='${param.status == "notFound"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">존재하지 않는 게시글입니다</div>
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
        <div class="u-s-p-y-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="breadcrumb overwrite-breadcrumb">
                <div class="breadcrumb__wrap overwrite-breadcrumb__wrap">
                  <ul class="breadcrumb__list">
                    <li class="has-separator"><a href="/">Home</a></li>
                    <li class="is-marked"><a href="/notice/list">공지사항</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--====== End - Section 1 ======-->
        
        <!--====== Section 2 ======-->
        <div class="u-s-p-y-60">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row">
                <div class="col-lg-10 col-md-12 u-s-m-b-30 u-s-m-x-auto">
                  <div class="notice">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>글번호</th>
                          <th>분류</th>
                          <th>제목</th>
                          <th>작성일</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="fixedNotice" items="${fixedNoticeList}">
                          <tr class="list-fixed" onclick="redirectDetail(${fixedNotice.notice_board_no});">
                            <td>${fixedNotice.notice_board_no}</td>
                            <td>${fixedNotice.notice_category_name}</td>
                            <td>${fixedNotice.title}</td>
                            <td>${fixedNotice.created_date}</td>
                          </tr>
                        </c:forEach>
                        <c:forEach var="notice" items="${noticeList}">
                          <tr onclick="redirectDetail(${notice.notice_board_no});">
                            <td>${notice.notice_board_no}</td>
                            <td>${notice.notice_category_name}</td>
                            <td>${notice.title}</td>
                            <td>${notice.created_date}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>

                  <div class="paging">
                    <ul class="shop-p__pagination">
                      <c:choose>
                        <c:when test="${pagingInfo.curPageNo > 1 }">
                          <li><a class="fas fa-angle-left" href="javascript:selectPage(${pagingInfo.curPageNo - 1})"></a></li>
                        </c:when>
                      </c:choose>

                      <c:forEach var="i" begin="${pagingInfo.startPageIndex}" end="${pagingInfo.endPageIndex}" step="1">
                        <c:choose>
                          <c:when test="${pagingInfo.curPageNo == i}">
                            <li class="is-active"><a href="javascript:selectPage(${i})">${i}</a></li>
                          </c:when>
                          <c:otherwise>
                            <li><a href="javascript:selectPage(${i})">${i}</a></li>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>

                      <c:choose>
                        <c:when test="${pagingInfo.totalPageCount > 1 && pagingInfo.curPageNo < pagingInfo.totalPageCount}">
                          <li><a class="fas fa-angle-right" href="javascript:selectPage(${pagingInfo.curPageNo + 1})"></a></li>
                        </c:when>
                      </c:choose>
                    </ul>
                  </div>

                  <div class="search-area">
                    <div class="search-wrapper">
                      <select class="form-select" id="searchType">
                        <option value="">검색조건</option>
                        <option value="title">제목</option>
                        <option value="content">본문</option>
                        <option value="notice_category_no">분류</option>
                      </select>
                      <select class="form-select form-invisible" id="searchCategory">
                        <option value="">분류선택</option>
                        <c:forEach var="category" items="${noticeCategoryList}">
                          <option value="${category.notice_category_no}">${category.notice_category_name}</option>
                        </c:forEach>
                      </select>
                      <input type="text" class="form-control" id="searchWord" />
                      <button class="btn btn-search" onclick="search();">검색</button>
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
    </div>
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
  </body>
</html>
