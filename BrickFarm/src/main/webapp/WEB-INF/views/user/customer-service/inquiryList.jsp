<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="UTF-8" />
    <link href="/resources/user/images/logo/logo-1.png" rel="shortcut icon" />
    <title>Brick Farm - 문의하기</title>

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
      const params = {
      	'curPageNo': ${pagingInfo.curPageNo},
      	'rowCountPerPage': ${pagingInfo.rowCountPerPage},
      	'searchType': '${searchInfo.searchType}',
      	'searchWord': '${searchInfo.searchWord}',
      	'isMine': false
      }

      $(function() {
   	    if (new URLSearchParams(document.location.search).has('isMine') && new URLSearchParams(document.location.search).get('isMine') == 'true') {
       	  params.isMine = true;
       	}

      	$("#searchWord").on("keyup", function(e) {
      		if(e.keyCode == 13) {
      			search();
      		}
      	})
      })

      function requestInquiryList() {
      	let form = document.createElement('form');
      	form.action = '/customer-service/inquiry/list';
      	form.method = 'GET';

      	$(form).addClass('form-invisible');

      	let inputTags = `<input name="pageNo" value="\${params.curPageNo}" />`;
      	inputTags += `<input name="rowCountPerPage" value="\${params.rowCountPerPage}" />`;
      	if(params.searchType != ""){
      		if(params.searchWord == "" || params.searchWord.length < 2) {
      			alert("검색어는 최소 두 글자 이상이어야 합니다.");
      			return;
      		}
      		inputTags += `<input name="searchType" value="\${params.searchType}" />`;
      		inputTags += `<input name="searchWord" value="\${params.searchWord}" />`;
      	}
      	if(params.isMine){
      		inputTags += `<input name="isMine" value="\${params.isMine}" />`;
      	}
      	$(form).html(inputTags);

      	document.body.append(form);

      	form.submit();
      }

      function selectPage(pageNo) {
      	params.curPageNo = pageNo;

      	requestInquiryList();
      }

      function search() {
      	if($("#searchType").val() == "" && $("#searchWord").val().length > 0) {
      		alert("검색조건을 선택해주세요.");
      		return;
      	}

      	params.searchType = $("#searchType").val();
      	params.searchWord = $("#searchWord").val();

      	requestInquiryList();
      }

      function redirectDetail(no) {
      	location.href = "/customer-service/inquiry/detail?no=" + no;
      }
      
      function redirectWriteInquiry() {
    	location.href = "/customer-service/inquiry/write";
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
   	  <c:when test='${param.status == "removeSuccess"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">삭제가 완료되었습니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
   	  </c:when>
      <c:when test='${param.status == "notFound"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">존재하지 않는 문의글입니다</div>
   	  	  <div class="modal-msg__close" onclick="closeToast();">
   	  	  <img src="/resources/user/images/kyj/x-30.png" />
   	  	  </div>
   	  	</div>
      </c:when>
      <c:when test='${param.status == "notWriter"}'>
   	  	<div class="modal-msg">
   	  	  <div class="modal-msg__content">해당 문의글의 작성자가 아닙니다</div>
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
    <!--====== Main App ======-->
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
                    <li class="is-marked"><a href="/customer-service/inquiry/list">문의하기</a></li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--====== End - Section 1 ======-->
        
        <!--====== Section 2 ======-->
        <div class="u-s-p-y-50">
          <!--====== Section Content ======-->
          <div class="section__content">
            <div class="container">
              <div class="row">
              
              	<c:if test="${not empty sessionScope.loginMemberInfo}">
              	  <div class="col-lg-10 col-md-12 u-s-m-b-10 u-s-m-x-auto flex-wrapper flex-jc-end">
              	    <button class="btn btn-inquiry" onclick="redirectWriteInquiry();">문의작성</button>
              	  </div>
              	</c:if>
              	
                <div class="col-lg-10 col-md-12 u-s-m-b-30 u-s-m-x-auto">
                  <div class="inquiry">
                    <table class="table table-striped table-hover">
                      <thead>
                        <tr>
                          <th>글번호</th>
                          <th>분류</th>
                          <th>제목</th>
                          <th>작성자</th>
                          <th>작성일</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="inquiry" items="${inquiryList}">
                          <c:choose>
                            <c:when test="${inquiry.is_delete.equals('N')}">
                              <tr onclick="redirectDetail(${inquiry.inquiry_board_no});">
                                <td>${inquiry.inquiry_board_no}</td>
                                <td>${inquiry.inquiry_category_name}</td>
                                <td>
                                  <c:forEach var="i" begin="1" end="${inquiry.step}" step="1">
                                    <span>RE: </span>
                                  </c:forEach>
                                  ${inquiry.title}
                                </td>
                                <td>${inquiry.member_no != 0 ? inquiry.member_id : inquiry.admin_id }</td>
                                <td>${inquiry.created_date}</td>
                              </tr>
                            </c:when>
                            <c:otherwise>
                              <tr>
                                <td colspan="5">삭제된 문의글입니다.</td>
                              </tr>
                            </c:otherwise>
                          </c:choose>
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
    <!--====== End - Main App ======-->
    <jsp:include page="/WEB-INF/views/user/footer.jsp"></jsp:include>
  </body>
</html>
