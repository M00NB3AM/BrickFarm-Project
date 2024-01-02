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

	<!--====== 게시판 관리 공용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/board/board.js"></script>

	<!--====== faq.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/board/faq.js"></script>

    <script>
      $(function () {
        printEmptyTable(undefined, 4);
        initCategoryList('FAQ');
        initFaqParams();

        $('#summernoteWrite').summernote({
          height: 300,
        });
        $('#summernoteModify').summernote({
          height: 300,
        });

        $('#searchKey').on('change', function (e) {
          setParams('searchKey', e.target.value);
          setParams('searchValue', '');
          $('#searchValue').val('');
        });

        $('#searchValue').on('change', function (e) {
          setParams('searchValue', e.target.value);
        });

        $('#searchValue').on('keyup', function (e) {
          if (e.keyCode == 13) {
            search();
          }
        });

        $('#rowCountPerPage').on('change', function (e) {
          selectRowCountPerPage(e.target.value);
        });

        search();
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
              <h2 class="text-black font-w600 mb-0">자주묻는질문 관리</h2>
              <p class="mb-0">자주묻는 질문 목록을 조회하고 작성, 삭제 등의 관리를 할 수 있습니다.</p>
            </div>
          </div>
          <div class="row">
            <div class="col-xl-12 col-lg-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">검색 조건</h4>
                </div>
                <div class="card-body">
                  <div class="col-12 mb-3">
                    <div class="form-group row flex-wrapper">
                      <div class="col-sm-3 col-form-label">분류</div>
                      <div class="col-lg-4 col-md-6">
                        <div class="form-group mb-0">
                          <select class="form-control" id="categoryNo"></select>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-12 mb-3">
                    <div class="form-group row flex-wrapper">
                      <div class="col-sm-3 col-form-label">검색조건</div>
                      <div class="col-sm-3 col-md-2">
                        <div class="form-group mb-0">
                          <select class="form-control" id="searchKey">
                            <option value="title">제목</option>
                            <option value="content">글내용</option>
                          </select>
                        </div>
                      </div>
                      <div class="col-sm-4 col-md-3">
                        <div class="form-group mb-0">
                          <input class="form-control" type="text" id="searchValue" />
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="col-12 mt-sm-1 mt-md-3">
                    <button type="button" class="btn btn-primary col-12" onclick="search();">검색</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12 wrapper-content" id="faqDetail">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table header-border table-responsive-sm">
                      <tbody>
                        <tr>
                          <th class="ta-center">글번호</th>
                          <th class="ta-center">분류</th>
                          <th class="ta-center">작성자</th>
                        </tr>
                        <tr>
                          <td class="ta-center" id="faqBoardNo"></td>
                          <td class="ta-center" id="faqCategoryName" data-category-no=""></td>
                          <td class="ta-center" id="faqWriter"></td>
                        </tr>
                        <tr>
                          <th class="ta-center">작성일</th>
                          <th class="ta-center">수정일</th>
                        </tr>
                        <tr>
                          <td class="ta-center" id="faqCreatedDate"></td>
                          <td class="ta-center" id="faqUpdatedDate"></td>
                        </tr>
                        <tr>
                          <th class="ta-center" colspan="3">제목</th>
                        </tr>
                        <tr>
                          <td class="ta-left" colspan="3" id="faqTitle"></td>
                        </tr>
                        <tr>
                          <th class="ta-center" colspan="3">글내용</th>
                        </tr>
                        <tr>
                          <td class="ta-left" colspan="3" id="faqContent"></td>
                        </tr>
                      </tbody>
                    </table>
                    <div class="col-12 mt-sm-1 mt-md-3">
                      <div class="row px-2 flex-jc-sb">
                        <button type="button" class="btn btn-danger col-5" onclick="removeFaq();">게시글 삭제</button>
                        <button type="button" class="btn btn-primary col-5" onclick="showModifyForm();">
                          게시글 수정
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12 wrapper-content" id="faqWrite">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive">
                    <form id="writeForm">
                      <table class="table header-border table-responsive-sm">
                        <tbody>
                          <tr>
                            <th class="ta-center">분류</th>
                            <td class="ta-left">
                              <select id="writeFaqCategory" name="faqCategoryNo"></select>
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center">작성자</th>
                            <td class="ta-left">${sessionScope.adminInfo.admin_id}</td>
                          </tr>
                          <tr>
                            <th class="ta-center">제목</th>
                            <td class="ta-center">
                              <input class="form-control" type="text" name="title" id="writeFaqTitle" />
                            </td>
                          </tr>
                          <tr>
                            <td class="ta-left" colspan="2">
                              <div id="summernoteWrite"></div>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div class="col-12 mt-sm-1 mt-md-3">
                        <div class="row px-2 flex-jc-sb">
                          <button type="button" class="btn btn-danger col-5" onclick="closeWriteForm();">취소</button>
                          <button type="button" class="btn btn-primary col-5" onclick="writeFaqHandler();">
                            작성하기
                          </button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12 wrapper-content" id="faqModify">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive">
                    <form action="/admin/board/faq/modify" method="post" id="modifyForm">
                      <table class="table header-border table-responsive-sm">
                        <tbody>
                          <tr>
                            <th class="ta-center">글번호</th>
                            <td class="ta-left" id="modifyFaqBoardNo"></td>
                          </tr>
                          <tr>
                            <th class="ta-center">분류</th>
                            <td class="ta-left">
                              <select id="modifyFaqCategory" name="faqCategoryNo"></select>
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center">작성자</th>
                            <td class="ta-left" id="modifyFaqWriter"></td>
                          </tr>
                          <tr>
                            <th class="ta-center">제목</th>
                            <td class="ta-left">
                              <input class="form-control" type="text" name="title" id="modifyFaqTitle" />
                            </td>
                          </tr>
                          <tr>
                            <td class="ta-left" colspan="2">
                              <div id="summernoteModify"></div>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div class="col-12 mt-sm-1 mt-md-3">
                        <div class="row px-2 flex-jc-sb">
                          <button type="button" class="btn btn-danger col-5" onclick="closeModifyForm();">취소</button>
                          <button type="button" class="btn btn-primary col-5" onclick="modifyFaqHandler();">
                            수정하기
                          </button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">검색 결과</h4>
                </div>
                <div class="card-body">
                  <div class="flex-wrapper flex-direction-row flex-jc-sb">
                    <div class="col-3 mb-3">
                      <select class="form-control" id="rowCountPerPage">
                        <option value="10">10개씩 보기</option>
                        <option value="15">15개씩 보기</option>
                        <option value="20">20개씩 보기</option>
                        <option value="30">30개씩 보기</option>
                      </select>
                    </div>
                    <button type="button" class="btn btn-primary col-3 mb-3" onclick="showWriteForm();">
                      게시글 작성
                    </button>
                  </div>

                  <div class="table-responsive">
                    <table class="table table-hover header-border table-responsive-sm table-search-result">
                      <thead id="resultHeader">
                        <tr>
                          <th class="wd-3">글번호</th>
                          <th class="wd-5">분류</th>
                          <th class="wd-8">제목</th>
                          <th class="wd-5">작성일</th>
                        </tr>
                      </thead>
                      <tbody id="searchResult"></tbody>
                    </table>
                  </div>

                  <div class="pagination_wrapper" id="pagination"></div>
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

    <!--**********************************
        Scripts
    ***********************************-->
    <!-- summernote-bs4 Stylesheet -->
    <link href="/resources/admin/css/kyj/summernote-bs4.min.css" rel="stylesheet" />
    <style>
      .note-editor .card-header {
        border-radius: 20px 20px 0 0 !important;
        padding: 5px 10px !important;
      }

      .note-editor .note-statusbar {
        border-radius: 0 0 20px 20px !important;
      }
    </style>

    <!--====== summernote-bs4 Js ======-->
    <script src="/resources/admin/js/kyj/board/summernote-bs4.min.js"></script>
  </body>
</html>
