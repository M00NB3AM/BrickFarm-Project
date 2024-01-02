<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- 제이쿼리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>

    <!-- 달력나오게하기 -->
    <link href="/resources/admin/vendor/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet" />
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="/resources/user/images/logo/logo-1.png" />
    <!-- CSS -->
    <link href="/resources/admin/vendor/bootstrap-select/dist/css/bootstrap-select.min.css" rel="stylesheet" />
    <link href="/resources/admin/css/style.css" rel="stylesheet" />
    <!-- syt css -->
    <link href="/resources/admin/css/syt/admin.css" rel="stylesheet" />
    <!-- Datatable -->
    <link href="/resources/admin/vendor/datatables/css/jquery.dataTables.min.css" rel="stylesheet" />
    <!-- Custom Stylesheet -->
    <link href="/resources/admin/vendor/bootstrap-select/dist/css/bootstrap-select.min.css" rel="stylesheet" />
    <!-- Toastr -->
    <link rel="stylesheet" href="/resources/admin/vendor/toastr/css/toastr.min.css" />

    <title>주문 관리</title>

    <script>
    window.onload = function () {
    	clickReset();

        //라디오(state) 클릭시 행동
        $('#state-select').on('click', function () {
          pageNo = 1;
          getData();
        });

        //페이지네이션 클릭
        $(document).on('click', '.page-item', function (event) {
          if (event.target.attributes.value.value != null) {
            pageNo = event.target.attributes.value.value;
            getData();
          }
        });
        
        // 체크박스 올
        $("#checkAll").on("click", function (event) {
          const allChecked = event.target.checked;
          $(".checkbox").prop("checked", allChecked);
        });
        
        // 체크박스 이벤트 스탑
        $(document).on("click", ".stopEvent", function (event) {
			event.stopPropagation();
		});
        
        // 모달창 열기
        $(document).on("click", ".listAll", function (event) {
        	let detailed_order_no = $(this).closest('tr').find('input[name=checkbox]').val();
	        findDetail(detailed_order_no);
	    });
    };
    </script>
  </head>
  <body>
    <div id="preloader">
      <img id="lego" src="/resources/admin/images/lego.gif" alt="" />
    </div>
    <div id="main-wrapper">
      <jsp:include page="./../header.jsp"></jsp:include>
      <div class="content-body">
        <div class="container-fluid">
          <div class="row page-titles mx-0">
            <div class="col-sm-6 p-md-0">
              <div class="welcome-text">
                <h4>주문 관리</h4>
              </div>
            </div>
            <div class="col-sm-6 p-md-0 justify-content-sm-end mt-2 mt-sm-0 d-flex">
              <ol class="breadcrumb">
                <li class="breadcrumb-item active"><a href="javascript:void(0)">주문</a></li>
                <li class="breadcrumb-item active"><a href="./dashboard">대시보드</a></li>
                <li class="breadcrumb-item"><a href="./manageOrder">주문관리</a></li>
              </ol>
            </div>
          </div>
          <div class="row">
            <div class="col-lg-12">
              <form id="searchForm">
                <div class="card">
                  <div class="card-header">
                    <h4 class="card-title">검색</h4>
                    <div class="form-group mb-0" id="state-select">
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="전체" checked />전체</label
                      >
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="결제대기" />결제대기</label
                      >
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="결제완료" />결제완료</label
                      >
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="취소" />취소</label
                      >
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="교환" />교환</label
                      >
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="반품" />반품</label
                      >
                      <label class="radio-inline mr-3"
                        ><input type="radio" name="stateSelect" value="구매확정" />구매확정</label
                      >
                    </div>
                  </div>
                  <div class="card-body">
                    <div class="table-responsive">
                      <table class="table search-table table-responsive-sm overflow-hidden">
                        <tbody>
                          <tr>
                            <th class="col-2">날짜검색</th>
                            <td class="row">
                              <div class="col-5 searchControll">
                                <select class="form-control form-control-xl" id="date-select" name="dateSelect">
                                  <option value="">날짜기준</option>
                                  <option value="O.order_day">구매날짜</option>
                                  <option value="PM.deposit_time">결제날짜</option>
                                  <option value="O.delivery_waiting_date">배송시작일</option>
                                </select>
                                <div class="col-7">
                                  <input
                                    id="date-range"
                                    class="form-control-sm form-control input-daterange-datepicker"
                                    type="text"
                                    name="dateRange"
                                    value=""
                                  />
                                </div>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <th class="col-2">검색기준</th>
                            <td class="row">
                              <div class="col-3 searchControll">
                                <select id="wordSelect" class="form-control form-control-xl" name="wordSelect">
                                  <option value="">검색기준</option>
                                  <option value="O.merchant_uid">주문번호</option>
                                  <option value="M.member_id">아이디</option>
                                  <option value="M.member_name">주문자</option>
                                  <option value="O.post_no">운송장번호</option>
                                </select>
                              </div>
                              <div class="col-4">
                                <input
                                  id="searchText"
                                  type="text"
                                  class="form-control-sm form-control search-word"
                                  name="searchText"
                                  placeholder="검색어를 입력하세요."
                                  value=""
                                />
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <th>최종상태</th>
                            <td class="row">
                              <div class="form-group mb-0">
                                <label class="radio-inline mr-3"
                                  ><input type="radio" name="totalState" value="" checked />전체</label
                                >
                                <label class="radio-inline mr-3"
                                  ><input type="radio" name="totalState" value="process" />진행중</label
                                >
                                <label class="radio-inline mr-3"
                                  ><input type="radio" name="totalState" value="complete" />완료</label
                                >
                              </div>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div class="d-flex justify-content-center">
                      <button type="button" class="col-1 btn light btn-info btn-sm mr-2" onclick="clickSearch()">
                        검색
                      </button>
                      <button type="button" class="col-1 btn light btn-danger btn-sm" onclick="clickReset()">
                        초기화
                      </button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h4 class="card-title">목록</h4>
                  <div class="form-group mb-0">
                    <button type="button" class="btn light btn-info btn-sm" onclick="changeState('beforePayment')">
                      결제대기
                    </button>
                    <button
                      type="button"
                      class="btn light btn-info btn-sm"
                      data-toggle="modal"
                      data-target="#modal"
                      onclick="openModal('afterPayment')"
                    >
                      결제완료
                    </button>
                    <button
                      type="button"
                      class="btn light btn-info btn-sm"
                      data-toggle="modal"
                      data-target="#modal"
                      onclick="openModal('cancel')"
                    >
                      취소
                    </button>
                    <button
                      type="button"
                      class="btn light btn-info btn-sm"
                      data-toggle="modal"
                      data-target="#modal"
                      onclick="openModal('exchange')"
                    >
                      교환
                    </button>
                    <button
                      type="button"
                      class="btn light btn-info btn-sm"
                      data-toggle="modal"
                      data-target="#modal"
                      onclick="openModal('return')"
                    >
                      반품
                    </button>
                    <button type="button" class="btn light btn-info btn-sm" onclick="changeState('complete')">
                      구매확정
                    </button>
                  </div>
                </div>

                <div class="card-body">
                  <div class="table-responsive">
                    <table class="table table-responsive-md">
                      <thead>
                        <tr>
                          <th class="text-center align-middle wd-1">
                            <div class="custom-control custom-checkbox">
                              <input type="checkbox" class="custom-control-input" id="checkAll" required="" />
                              <label class="custom-control-label" for="checkAll"></label>
                            </div>
                          </th>
                          <th class="text-center align-middle wd-5">주문번호</th>
                          <th class="text-center align-middle wd-4">아이디</th>
                          <th class="text-center align-middle wd-4">주문자</th>
                          <th class="text-center align-middle wd-5">상품명</th>
                          <th class="text-center align-middle wd-4">총구매가</th>
                          <th class="text-center align-middle wd-5">주문날짜</th>
                          <th class="text-center align-middle wd-2">결제상태</th>
                          <th class="text-center align-middle wd-5">결제날짜</th>
                          <th class="text-center align-middle wd-5">완료날짜</th>
                          <th class="text-center align-middle wd-2">배송상태</th>
                          <th class="text-center align-middle wd-5">배송날짜</th>
                          <th class="text-center align-middle wd-5">운송장</th>
                        </tr>
                      </thead>
                      <tbody id="inputOrderList"></tbody>
                    </table>
                  </div>
                </div>
                <!-- 페이지네이션 -->
                <div id="paginationInput"></div>
                <!-- 페이지네이션 종료-->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- modal -->
    <div id="modal" class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">사유를 입력하세요.</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span>&times;</span>
            </button>
          </div>
          <form id="changeStateForm">
            <div class="modal-body">
              <div class="table-responsive">
                <table id="modalInput" class="table table-responsive-md"></table>
              </div>
            </div>
          </form>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-primary light"
              data-dismiss="modal"
              onclick="changeState($('#modalWhat').val())"
            >
              저장
            </button>
            <button type="button" class="btn btn-danger light" data-dismiss="modal">취소</button>
          </div>
        </div>
      </div>
    </div>

    <!-- detailedModal -->
    <div id="detailedModal" class="modal fade bd-example-modal-xl" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">상세정보</h5>
            <button type="button" class="close" data-dismiss="modal">
              <span>&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="table-responsive">
              <table class="table table-responsive-md">
                <thead>
                  <tr>
                    <th class="align-middle">상세번호</th>
                    <th class="align-middle">상품명</th>
                    <th class="align-middle">상품가</th>
                    <th class="align-middle">행사가</th>
                    <th class="align-middle">구매가</th>
                    <th class="align-middle">수량</th>
                    <th class="align-middle">최종상태</th>
                    <th class="align-middle">완료날짜</th>
                    <th class="align-middle">메모</th>
                  </tr>
                </thead>
                <tbody id="detailedModalInput"></tbody>
              </table>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger light" data-dismiss="modal">닫기</button>
          </div>
        </div>
      </div>
    </div>

    <!--**********************************
        Scripts
    ***********************************-->
    <!-- syt.js -->
    <script src="/resources/admin/js/syt/order.js"></script>
    <script src="/resources/admin/js/syt/manageOrder.js"></script>
    
    <!-- 달력나오게하기 -->
    <script src="/resources/admin/vendor/moment/syt/moment.min.js"></script>
    <script src="/resources/admin/vendor/bootstrap-daterangepicker/syt/daterangepicker.js"></script>
    <script src="/resources/admin/js/plugins-init/bs-daterange-picker-init.js"></script>

    <!-- Toastr -->
    <script src="/resources/admin/vendor/toastr/js/toastr.min.js"></script>
  </body>
</html>
