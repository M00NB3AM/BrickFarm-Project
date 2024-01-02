<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
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

	<!--====== inquiry.jsp 용 Js ======-->
    <script type="text/javascript" src="/resources/admin/js/kyj/board/inquiry.js"></script>

    <script>
      $(function () {
        printEmptyTable(undefined, 6);
        initCategoryList('INQUIRY');
        initInquiryParams();

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

        $('#isNeedResponse').on('change', function (e) {
          setParams('isNeedResponse', e.target.checked);
        });

        $('#uploadFileArea').on('dragenter', function (evt) {
          evt.preventDefault();
          // console.log('dragenter');
          $('#uploadFileArea.area-file-upload').addClass('drag');
          $('#uploadFileArea.area-file-upload').text('놓아서 사진 업로드');
        });

        $('#uploadFileArea').on('dragleave', function (evt) {
          evt.preventDefault();
          // console.log('dragleave');
          $('#uploadFileArea.area-file-upload').removeClass('drag');
          $('#uploadFileArea.area-file-upload').text('업로드 할 사진을 이곳에 끌어다 놓아주세요');
        });

        $('#uploadFileArea').on('dragover', function (evt) {
          evt.preventDefault();
        });

        $('#uploadFileArea').on('drop', function (evt) {
          evt.preventDefault();
          $('#uploadFileArea.area-file-upload').removeClass('drag');
          $('#uploadFileArea.area-file-upload').text('업로드 할 사진을 이곳에 끌어다 놓아주세요');
          let fileList = evt.originalEvent.dataTransfer.files;
          // console.log('fileList', fileList);

          const availableExt = ['png', 'jpg', 'jpeg', 'gif', 'bmp'];

          for (let file of fileList) {
            let fileType = file.type.split('/')[0];
            let ext = file.type.split('/')[1];
            if (fileType != 'image' && !availableExt.includes(ext)) {
              alert(
                `''\${file.name}' 은(는) 이미지 파일이 아닙니다.\n이미지 파일만 업로드 가능합니다.\n(png, jpg, jpeg, gif, bmp)`
              );
              return;
            }
          }

          for (let file of fileList) {
            let form = new FormData();
            
            let fileListJSONString = JSON.stringify(FILE_LIST);

            form.append('uploadFiles', file);
            form.append('fileListJSONString', fileListJSONString);

            $.ajax({
              url: '/admin/board/inquiry/uploadFile',
              type: 'post',
              data: form,
              dataType: 'json',
              async: false,
              processData: false,
              contentType: false,
              success: function (data) {
                // console.log(`[\${this.url}] request success`);
                // console.log(data);

                if (data != null) {
                  showUploadedFile(data.fileList, 'uploadFiles');
                  FILE_LIST = data.fileList;
                }
              },
              error: function (err) {
                // console.log(`[\${this.url}] request error`);
                // console.log(err);
              },
              complete: function () {
                // console.log(`[\${this.url}] request complete`);
              },
            });
          }
        });

        $('#modifyUploadFileArea').on('dragenter', function (evt) {
          evt.preventDefault();
          $('#modifyUploadFileArea.area-file-upload').addClass('drag');
          $('#modifyUploadFileArea.area-file-upload').text('놓아서 사진 업로드');
        });

        $('#modifyUploadFileArea').on('dragleave', function (evt) {
          evt.preventDefault();
          $('#modifyUploadFileArea.area-file-upload').removeClass('drag');
          $('#modifyUploadFileArea.area-file-upload').text('업로드 할 사진을 이곳에 끌어다 놓아주세요');
        });

        $('#modifyUploadFileArea').on('dragover', function (evt) {
          evt.preventDefault();
        });

        $('#modifyUploadFileArea').on('drop', function (evt) {
          evt.preventDefault();
          $('#modifyUploadFileArea.area-file-upload').removeClass('drag');
          $('#modifyUploadFileArea.area-file-upload').text('업로드 할 사진을 이곳에 끌어다 놓아주세요');
          let fileList = evt.originalEvent.dataTransfer.files;
          // console.log('fileList', fileList);

          const availableExt = ['png', 'jpg', 'jpeg', 'gif', 'bmp'];

          for (let file of fileList) {
            let fileType = file.type.split('/')[0];
            let ext = file.type.split('/')[1];
            if (fileType != 'image' && !availableExt.includes(ext)) {
              alert(
                `''\${file.name}' 은(는) 이미지 파일이 아닙니다.\n이미지 파일만 업로드 가능합니다.\n(png, jpg, jpeg, gif, bmp)`
              );
              return;
            }
          }

          for (let file of fileList) {
            let form = new FormData();
            
            let fileListJSONString = JSON.stringify(FILE_LIST);

            form.append('uploadFiles', file);
            form.append('fileListJSONString', fileListJSONString);

            $.ajax({
              url: '/admin/board/inquiry/uploadFile',
              type: 'post',
              data: form,
              dataType: 'json',
              async: false,
              processData: false,
              contentType: false,
              success: function (data) {
                // console.log(`[\${this.url}] request success`);
                // console.log(data);

                if (data != null) {
                  showUploadedFile(data.fileList, 'modifyUploadFiles');
                  FILE_LIST = data.fileList;
                }
              },
              error: function (err) {
                // console.log(`[\${this.url}] request error`);
                // console.log(err);
              },
              complete: function () {
                // console.log(`[\${this.url}] request complete`);
              },
            });
          }
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
              <h2 class="text-black font-w600 mb-0">문의 관리</h2>
              <p class="mb-0">고객의 문의글을 조회하고 답변하는 등의 관리를 할 수 있습니다.</p>
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
                            <option value="member_id">회원아이디</option>
                            <option value="member_name">회원이름</option>
                            <option value="admin_id">관리자아이디</option>
                            <option value="inquiry_board_no">글번호</option>
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

                  <div class="col-12 mb-3">
                    <div class="form-group row">
                      <div class="col-sm-3">응답 여부</div>
                      <div class="col-sm-9">
                        <div class="form-check">
                          <input id="isNeedResponse" class="form-check-input" type="checkbox" />
                          <label class="form-check-label" for="checkUsePoint">응답대기</label>
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

            <div class="col-12 wrapper-content" id="inquiryDetail">
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
                          <td class="ta-center" id="inquiryBoardNo"></td>
                          <td class="ta-center" id="inquiryCategoryName" data-category-no=""></td>
                          <td class="ta-center" id="inquiryWriter"></td>
                        </tr>
                        <tr>
                          <th class="ta-center">작성일</th>
                          <th class="ta-center">수정일</th>
                          <th class="ta-center">응답상태</th>
                        </tr>
                        <tr>
                          <td class="ta-center" id="inquiryCreatedDate"></td>
                          <td class="ta-center" id="inquiryUpdatedDate"></td>
                          <td class="ta-center" id="inquiryStatus"></td>
                        </tr>
                        <tr>
                          <th class="ta-center" colspan="3">제목</th>
                        </tr>
                        <tr>
                          <td class="ta-left" colspan="3" id="inquiryTitle"></td>
                        </tr>
                        <tr>
                          <th class="ta-center" colspan="3">글내용</th>
                        </tr>
                        <tr>
                          <td class="ta-left" colspan="3" id="inquiryContent"></td>
                        </tr>
                        <tr>
                          <th class="ta-center" colspan="3">첨부된 사진</th>
                        </tr>
                        <tr>
                          <td class="ta-center" colspan="3">
                            <div class="area-file-uploaded" id="inquiryImages"></div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <div class="col-12 mt-sm-1 mt-md-3">
                      <div class="row px-2 flex-jc-sb">
                        <button type="button" class="btn btn-danger col-3" onclick="removeInquiry();">
                          문의글 삭제
                        </button>
                        <button type="button" class="btn btn-primary col-3" onclick="showModifyForm();">
                          문의글 수정
                        </button>
                        <button type="button" class="btn btn-primary col-3" onclick="showWriterForm();">
                          답글 달기
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12 wrapper-content" id="inquiryWrite">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive">
                    <form action="/admin/board/inquiry/write" method="post" id="writeForm">
                      <input type="hidden" name="inquiryCategoryNo" id="responseInquiryCategoryNo" />
                      <input type="hidden" name="ref" id="responseInquiryRef" />
                      <input type="hidden" name="step" id="responseInquiryStep" />
                      <input type="hidden" name="refOrder" id="responseInquiryRefOrder" />
                      <input type="hidden" name="fileListJSONString" class="fileListJSONString" />
                      <table class="table header-border table-responsive-sm">
                        <tbody>
                          <tr>
                            <th class="ta-center">대상글번호</th>
                            <td class="ta-left" id="responseInquiryBoardNo"></td>
                          </tr>
                          <tr>
                            <th class="ta-center">분류</th>
                            <td class="ta-left" id="responseInquiryCategoryName"></td>
                          </tr>
                          <tr>
                            <th class="ta-center">작성자</th>
                            <td class="ta-left">${sessionScope.adminInfo.admin_id}</td>
                          </tr>
                          <tr>
                            <th class="ta-center">제목</th>
                            <td class="ta-center">
                              <input class="form-control" type="text" name="title" />
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center" colspan="2">
                              <textarea
                                class="form-control textarea-inquiry-content"
                                placeholder="글 내용을 입력해주세요."
                                name="content"
                              ></textarea>
                            </th>
                          </tr>
                          <tr>
                            <th class="ta-center" colspan="2">사진 업로드</th>
                          </tr>
                          <tr>
                            <td class="ta-center" colspan="2">
                              <div class="area-file-upload" id="uploadFileArea">
                                업로드 할 사진을 이곳에 끌어다 놓아주세요
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center" colspan="2">첨부된 사진</th>
                          </tr>
                          <tr>
                            <td class="ta-center" colspan="2">
                              <div class="area-file-uploaded" id="uploadFiles"></div>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div class="col-12 mt-sm-1 mt-md-3">
                        <div class="row px-2 flex-jc-sb">
                          <button type="button" class="btn btn-danger col-5" onclick="removeAllFile();">취소</button>
                          <button type="submit" class="btn btn-primary col-5" onclick="return validation();">
                            답글 달기
                          </button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-12 wrapper-content" id="inquiryModify">
              <div class="card">
                <div class="card-body">
                  <div class="table-responsive">
                    <form action="/admin/board/inquiry/modify" method="post" id="modifyForm">
                      <input type="hidden" name="inquiryBoardNo" id="formModifyInquiryBoardNo" />
                      <input type="hidden" name="fileListJSONString" class="fileListJSONString" />
                      <table class="table header-border table-responsive-sm">
                        <tbody>
                          <tr>
                            <th class="ta-center">글번호</th>
                            <td class="ta-left" id="modifyInquiryBoardNo"></td>
                          </tr>
                          <tr>
                            <th class="ta-center">분류</th>
                            <td class="ta-left">
                              <select id="modifyInquiryCategory" name="inquiryCategoryNo"></select>
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center">작성자</th>
                            <td class="ta-left" id="modifyInquiryWriter"></td>
                          </tr>
                          <tr>
                            <th class="ta-center">제목</th>
                            <td class="ta-left">
                              <input class="form-control" type="text" name="title" id="modifyInquiryTitle" />
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center" colspan="2">
                              <textarea
                                class="form-control textarea-inquiry-content"
                                placeholder="글 내용을 입력해주세요."
                                name="content"
                                id="modifyInquiryContent"
                              ></textarea>
                            </th>
                          </tr>
                          <tr>
                            <th class="ta-center" colspan="2">사진 업로드</th>
                          </tr>
                          <tr>
                            <td class="ta-center" colspan="2">
                              <div class="area-file-upload" id="modifyUploadFileArea">
                                업로드 할 사진을 이곳에 끌어다 놓아주세요
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <th class="ta-center" colspan="2">첨부된 사진</th>
                          </tr>
                          <tr>
                            <td class="ta-center" colspan="2">
                              <div class="area-file-uploaded" id="modifyUploadFiles"></div>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div class="col-12 mt-sm-1 mt-md-3">
                        <div class="row px-2 flex-jc-sb">
                          <button type="button" class="btn btn-danger col-5" onclick="toBeRemoveAllFile();">
                            취소
                          </button>
                          <button type="submit" class="btn btn-primary col-5" onclick="return validationModify();">
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
                    <div class="col-2 mb-3">
                      <select class="form-control" id="rowCountPerPage">
                        <option value="10">10개씩 보기</option>
                        <option value="15">15개씩 보기</option>
                        <option value="20">20개씩 보기</option>
                        <option value="30">30개씩 보기</option>
                      </select>
                    </div>
                  </div>

                  <div class="table-responsive">
                    <table class="table table-hover header-border table-responsive-sm table-search-result">
                      <thead id="resultHeader">
                        <tr>
                          <th class="wd-3">글번호</th>
                          <th class="wd-5">분류</th>
                          <th class="wd-8">제목</th>
                          <th class="wd-4">작성자</th>
                          <th class="wd-5">작성일</th>
                          <th class="wd-4">응답상태</th>
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
    <div class="modal fade" id="modal">
      <div class="modal-dialog modal-lg">
        <div class="wrapper-modal-img">
          <img class="icon-controller icon-prev-arrows" id="iconPrev" onclick="handlerPrevImg();" />
          <img id="imgModal" />
          <img class="icon-controller icon-next-arrows" id="iconNext" onclick="handlerNextImg();" />
        </div>
      </div>
    </div>

  </body>
</html>
