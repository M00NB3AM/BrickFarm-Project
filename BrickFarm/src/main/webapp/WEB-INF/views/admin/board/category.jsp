<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>관리자 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />

    <!-- Common Stylesheet -->
    <link href="/resources/admin/css/kyj/common.css" rel="stylesheet" />

    <!-- Board Stylesheet -->
    <link href="/resources/admin/css/kyj/board.css" rel="stylesheet" />
    
    <!-- Toastr Stylesheet -->
    <link href="/resources/admin/vendor/toastr/css/toastr.min.css" rel="stylesheet" />

    <!--====== jQuery 3.6.4 Js ======-->
    <script src="/resources/user/js/kyj/jquery-3.6.4.js"></script>
    
    <!--====== Toastr Js ======-->
    <script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
    
    <!--====== Toastr call functions Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/util-toastr.js"></script>

	<!--====== category.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/board/category.js"></script>

    <script>
      $(function () {
        initAllCategoryList();
      });
    </script>
  </head>
  <body>
    <div id="preloader">
      <img id="lego" src="/resources/admin/images/lego.gif" alt="" />
    </div>
    <div id="main-wrapper">
      <jsp:include page="/WEB-INF/views/admin/header.jsp"></jsp:include>
      <!--**********************************
            Content body start
        ***********************************-->
      <div class="content-body">
        <!-- row -->
        <div class="container-fluid">
          <div class="form-head d-flex mb-3 align-items-start">
            <div class="mr-auto d-none d-lg-block">
              <h2 class="text-black font-w600 mb-0">분류 관리</h2>
              <p class="mb-0">공지사항, 문의, 자주묻는 질문 게시판의 분류를 관리할 수 있습니다.</p>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">분류 관리</h4>
                </div>

                <div class="card-body">
                  <div class="row">
                    <div class="col-xl-3">
                      <div class="nav flex-column nav-pills mb-3">
                        <a
                          href="#v-pills-notice"
                          data-toggle="pill"
                          class="nav-link active show"
                          onclick="setInputClassification('NOTICE');"
                          >공지사항</a
                        >
                        <a
                          href="#v-pills-inquiry"
                          data-toggle="pill"
                          class="nav-link"
                          onclick="setInputClassification('INQUIRY');"
                          >문의</a
                        >
                        <a
                          href="#v-pills-faq"
                          data-toggle="pill"
                          class="nav-link"
                          onclick="setInputClassification('FAQ');"
                          >자주묻는 질문</a
                        >
                      </div>
                    </div>
                    <div class="col-xl-9">
                      <div class="tab-content active">
                        <div id="v-pills-notice" class="tab-pane fade active show">
                          <div class="basic-list-group">
                            <ul class="list-group" id="noticeCategoryList"></ul>
                          </div>
                        </div>
                        <div id="v-pills-inquiry" class="tab-pane fade">
                          <div class="basic-list-group">
                            <ul class="list-group" id="inquiryCategoryList"></ul>
                          </div>
                        </div>
                        <div id="v-pills-faq" class="tab-pane fade">
                          <div class="basic-list-group">
                            <ul class="list-group" id="faqCategoryList"></ul>
                          </div>
                        </div>

                        <div class="wrapper-content" id="inputForm">
                          <input type="hidden" id="inputNo" />
                          <div class="input-group mt-3">
                            <input type="text" class="form-control" id="inputName" />
                            <div class="input-group-append">
                              <button class="btn btn-primary" type="button" onclick="acceptHandler();">✔</button>
                              <button class="btn btn-danger btn-addon-end" type="button" onclick="cancelHandler();">
                                ❌
                              </button>
                            </div>
                          </div>
                        </div>

                        <div class="wrapper-content pop" id="createBtn">
                          <button
                            type="button"
                            class="btn btn-outline-primary col-12 mt-3 btn-list-radius"
                            onclick="createHandler();"
                          >
                            +
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--**********************************
            Content body end
        ***********************************-->
    </div>
  </body>
</html>
